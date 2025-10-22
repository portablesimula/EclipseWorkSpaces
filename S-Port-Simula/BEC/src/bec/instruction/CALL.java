package bec.instruction;

import bec.compileTimeStack.ProfileItem;
import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.descriptor.ProfileDescr;
import bec.descriptor.RoutineDescr;
import bec.descriptor.Variable;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_CALL;
import bec.virtualMachine.SVM_NOOP;
import bec.virtualMachine.SVM_PRECALL;
import bec.virtualMachine.SVM_LOADC;
import bec.virtualMachine.SVM_CALL_SYS;

public abstract class CALL extends Instruction {
	private static final boolean DEBUG = false;
	
	public static final boolean USE_FRAME_ON_STACK = true;
	/**
	 * call_instruction
	 * 		::= connect_profile <parameter_eval>*
	 * 				connect_routine
	 * 
	 * 		connect_profile
	 * 			::= precall profile:tag
	 * 			::= asscall profile:tag
	 * 			::= repcall n:byte profile:tag
	 * 
	 * 		connect_routine ::= call body:tag | <instruction>+ call-tos
	 * 
	 * 		parameter_eval
	 * 			::= <instruction>+ asspar
	 * 			::= <instruction>+ assrep n:byte
	 */
	public static void ofScode(int nParStacked) {
		int profileTag = Scode.ofScode();
		Scode.inputInstr();
		
		ProfileDescr spec = (ProfileDescr) Global.DISPL.get(profileTag);
		if(spec == null) Util.IERR(""+Scode.edTag(profileTag));
		ProfileItem pitem = new ProfileItem(Type.T_VOID,spec);
		pitem.nasspar = nParStacked;
		
		int nParSlots = 0;
		for(int i=0;i<nParStacked;i++) {
			CTStack.forceTosValue();
			CTStackItem par = CTStack.pop();
			nParSlots = nParSlots + par.type.size();
		}
		
		CTStack.push(pitem);
	    
		if(spec.pKind == 0) {
			int exportSize = (spec.getExport() == null)? 0 : spec.getExport().type.size();
			int importSize = spec.frameSize-exportSize-1;
			Global.PSEG.emit(new SVM_PRECALL(spec.getSimpleName(), nParSlots, exportSize, importSize), ""+Scode.edTag(profileTag));
		}
		
		boolean CALL_TOS = false;
		
		LOOP:while(Scode.curinstr != Scode.S_CALL) {
			Instruction.inInstructions();
			if(Scode.curinstr == Scode.S_ASSPAR) {
				Scode.inputInstr();
				putPar(pitem,1);
		      	Global.PSEG.emit(new SVM_NOOP(), "ASSPAR ");
		      	if(DEBUG) {
		      		IO.println("CallInstruction: ASSPAR: nasspar="+pitem.nasspar);
		      	}
			}
			else if(Scode.curinstr == Scode.S_ASSREP) {
				int nRep = Scode.inByte();
				Scode.inputInstr();
				putPar(pitem,nRep);
		      	Global.PSEG.emit(new SVM_NOOP(), "ASSREP " + nRep);
			}
			else if(Scode.curinstr == Scode.S_CALL_TOS) {
				CALL_TOS = true;
				break LOOP;
			}
			else Util.IERR("Syntax error in call Instruction");
		}
//	    ---------  Final Actions  ---------
		if(DEBUG) IO.println("CallInstruction: FINAL: " + pitem.spc);
	    if(pitem.nasspar != pitem.spc.params.size())
	    	Util.IERR("Wrong number of Parameters: got " + pitem.nasspar + ", required" + +pitem.spc.params.size());
//	    ---------  Call Routine  ---------
	    if(CALL_TOS) {
	    	Global.PSEG.emit(SVM_CALL.ofTOS(spec.returSlot), "");
	    	CTStack.pop();
	    } else {
			int bodyTag = Scode.ofScode();
	    	if(spec.pKind > 0) {
	    		Global.PSEG.emit(new SVM_CALL_SYS(spec.pKind), "");
	    	} else {
	    		RoutineDescr rut = (RoutineDescr) Global.DISPL.get(bodyTag);
	    		if(rut == null) Util.IERR("Unknown Routine: " + Scode.edTag(bodyTag));
	    		Global.PSEG.emit(new SVM_CALL(rut.getAddress(), spec.returSlot), ""+rut);
	    	}
	    }
		Global.PSEG.emit(new SVM_NOOP(), "Return Point");
	    if(! (CTStack.TOS() instanceof ProfileItem)) {
			CTStack.dumpStack("CALL.ofScode: ");
	    	Util.IERR("CALL.ofScode: Missing ProfileItem on TOS");
	    }
	    CTStack.pop();
		
		// Routines return values on the RT-Stack
		Variable export = spec.getExport();
		if(export != null) {
			Type returnType = export.type;
			if(DEBUG) IO.println("CallInstruction.callSYS: returnType="+returnType);
			CTStack.pushTempVAL(returnType, 1);
		}
	}
	
	private static int putPar(ProfileItem pItm, int nrep) { // export range(0:255) npop;
		Variable param = (Variable) pItm.spc.params.get(pItm.nasspar).getMeaning();
		Type parType = param.type;
		int repCount = param.repCount;
		if(nrep>repCount) Util.IERR("Too many values in repeated param: Got "+nrep+", expect "+repCount);
		pItm.nasspar = pItm.nasspar+1;
		CTStackItem tos = CTStack.TOS();
		Type st = tos.type;
		
		//--- First: Treat TOS ---
		if(st != parType) {
			CONVERT.doConvert(parType);
		} else if(tos instanceof AddressItem) {
			tos.type = st;
		} else {
			CONVERT.doConvert(parType);
		}
		
		if(CTStack.TOS() instanceof AddressItem) CTStack.forceTosValue();
		CTStack.pop();
		
		if(nrep > 1) { // Then: Treat rest of rep-par ---
			for(int i=nrep-1;i>0;i--) {
				CTStackItem TOS = CTStack.pop();
				if(TOS instanceof AddressItem) Util.IERR("MODE mismatch below TOS");
				if(TOS.type != parType) {
					if(TOS.type.tag == Scode.TAG_INT && parType.tag == Scode.TAG_SINT) ; // OK
					else if(TOS.type.tag == Scode.TAG_SINT && parType.tag == Scode.TAG_INT) ; // OK
					else Util.IERR("TYPE mismatch below TOS -- ASSREP: TOS.type="+TOS.type+"  parType="+parType);
				}
			}
		}
		
		if(repCount > nrep) {
			int parSize = parType.size();
			int n = parSize * (repCount - nrep);
			for(int i=0;i<n;i++) {
				Global.PSEG.emit(new SVM_LOADC(Type.T_INT, null), "putPar: ASSREP'fill: ");
				
			}
		}
		return nrep;
	}

}
