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
import bec.value.ObjectAddress;
import bec.virtualMachine.SVM_CALL;
import bec.virtualMachine.SVM_NOOP;
import bec.virtualMachine.SVM_STORE;
import bec.virtualMachine.SVM_PRECALL;
import bec.virtualMachine.SVM_LOADC;
import bec.virtualMachine.SVM_CALL_SYS;
import bec.virtualMachine.SVM_CALL_TOS;

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
		
//		if(Scode.inputTrace > 3) IO.println("CallInstruction: n="+n+", Curinstr="+Scode.edInstr(Scode.curinstr));
		ProfileDescr spec = (ProfileDescr) Global.DISPL.get(profileTag);
		if(spec == null) Util.IERR(""+Scode.edTag(profileTag));
//		IO.println("Call.ofScode-1: BEGIN CALL "+Scode.edTag(profileTag)+" ================================================================================");
//		spec.print("Call.ofScode-1: ");
		ProfileItem pitem = new ProfileItem(Type.T_VOID,spec);
		pitem.nasspar = nParStacked;
		
//		IO.println("CALL.ofScode: nParStacked="+nParStacked);
		int nParSlots = 0;
		for(int i=0;i<nParStacked;i++) {
			FETCH.doFetch(null);
			CTStackItem par = CTStack.pop();
			nParSlots = nParSlots + par.type.size();
//			IO.println("CALL.ofScode: par="+par+", nParSlots="+nParSlots);
		}
		
		CTStack.push(pitem);
	    
//		IO.println("CALL.ofScode: "+Scode.edTag(profileTag)+", spec.pKind="+SVM_CALL_SYS.edKind(spec.pKind)+':'+spec.pKind);
		if(spec.pKind == 0) {
			int exportSize = (spec.getExport() == null)? 0 : spec.getExport().type.size();
			int importSize = spec.frameSize-exportSize-1;
//			RTFrame frame = new RTFrame(exportSize, spec.frameSize-exportSize-1);
//			Global.PSEG.emit(new SVM_PRECALL(frame), ""+Scode.edTag(profileTag));
			Global.PSEG.emit(new SVM_PRECALL(spec.getSimpleName(), nParSlots, exportSize, importSize), ""+Scode.edTag(profileTag));
		}
		
//		Vector<Instruction> CALL_TOS_Instructions = null;
		boolean CALL_TOS = false;
		
		LOOP:while(Scode.curinstr != Scode.S_CALL) {
			Instruction.inInstructions();
			if(Scode.curinstr == Scode.S_ASSPAR) {
				Scode.inputInstr();
				putPar(pitem,1);
		      	Global.PSEG.emit(new SVM_NOOP(), "ASSPAR ");
		      	if(DEBUG) {
		      		IO.println("CallInstruction: ASSPAR: nasspar="+pitem.nasspar);
//			    	CTStack.dumpStack("Call.ofScode-2: ");
//			    	Util.IERR("");
		      	}
			}
			else if(Scode.curinstr == Scode.S_ASSREP) {
				int nRep = Scode.inByte();
				Scode.inputInstr();
				putPar(pitem,nRep);
		      	Global.PSEG.emit(new SVM_NOOP(), "ASSREP " + nRep);
//			    CTStack.dumpStack("Call.ofScode-3: ");
//			    Util.IERR("");
//				IO.println("CallInstruction: ASSREP: NextInstr="+Scode.edInstr(Scode.nextByte()));
			}
			else if(Scode.curinstr == Scode.S_CALL_TOS) {
				CALL_TOS = true;
				break LOOP;
			}
			else Util.IERR("Syntax error in call Instruction");
		}
//	    Util.IERR("");
//	    ---------  Final Actions  ---------
		if(DEBUG) IO.println("CallInstruction: FINAL: " + pitem.spc);
	    if(pitem.nasspar != pitem.spc.params.size())
	    	Util.IERR("Wrong number of Parameters: got " + pitem.nasspar + ", required" + +pitem.spc.params.size());
//	    ---------  Call Routine  ---------
//		IO.println("CALL.ofScode: export="+spec.exportSize);
	    if(CALL_TOS) {
	    	Global.PSEG.emit(SVM_CALL.ofTOS(spec.returSlot), "");
//    		Global.PSEG.emit(new SVM_CALL_TOS(spec.ident, spec.returSlot, spec.nParSlots, spec.exportSize, spec.importSize), ""+Scode.edTag(profileTag));
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
//	    if(CTStack.TOS() != pitem) {
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
			CTStack.pushTempVAL(returnType, 1, "EXPORT: ");
//			CTStack.dumpStack("END CallInstruction.doCode: ");
//			Util.IERR("");
		}
//		Global.PSEG.dump("END CALL: ");
//		Util.IERR("");
	}
	
	private static int putPar(ProfileItem pItm, int nrep) { // export range(0:255) npop;
//		int npop = 0;
		Variable param = (Variable) pItm.spc.params.get(pItm.nasspar).getMeaning();
		Type parType = param.type;
		int repCount = param.repCount;
//	      n:=TTAB(parType).nbyte; i:=nrep;
		if(nrep>repCount) Util.IERR("Too many values in repeated param: Got "+nrep+", expect "+repCount);
		pItm.nasspar = pItm.nasspar+1;
		CTStackItem tos = CTStack.TOS();
		Type st = tos.type;
		
		//--- First: Treat TOS ---
		if(st != parType) {
			CONVERT.GQconvert(parType);
		} else if(tos instanceof AddressItem) {
			tos.type = st;
		} else {
			CONVERT.GQconvert(parType);
		}
		
		if(CTStack.TOS() instanceof AddressItem) FETCH.doFetch("putPar: ");
		CTStack.pop();
		
//		pItm.spc.printTree(2);
//		Global.PSEG.dump("putPar: ");
//		pItm.spc.DSEG.dump("putPar: ");
//		CTStack.dumpStack("ZZZZZZZZZZZZ putPar: ");
//		Util.IERR("");
		
		if(nrep > 1) { // Then: Treat rest of rep-par ---
//			CTStack.dumpStack("putPar: ");
			for(int i=nrep-1;i>0;i--) {
//				CTStackItem TOS = CTStack.takeTOS();
				CTStackItem TOS = CTStack.pop();
//				IO.println("CALL.putPar: "+TOS);
				if(TOS instanceof AddressItem) Util.IERR("MODE mismatch below TOS");
				if(TOS.type != parType) {
					if(TOS.type.tag == Scode.TAG_INT && parType.tag == Scode.TAG_SINT) ; // OK
					else if(TOS.type.tag == Scode.TAG_SINT && parType.tag == Scode.TAG_INT) ; // OK
					else Util.IERR("TYPE mismatch below TOS -- ASSREP: TOS.type="+TOS.type+"  parType="+parType);
				}
			}
//			CTStack.dumpStack("putPar: ");
//			Global.PSEG.dump("putPar: ");
//			Util.IERR("Parse.XXX: NOT IMPLEMENTED: nrep="+nrep);
		}
		
		
//		IO.println("CallInstruction.putPar: nrep="+nrep+", repCount="+repCount);
		if(repCount > nrep) {
			int parSize = parType.size();
			int n = parSize * (repCount - nrep);
//			IO.println("CallInstruction.putPar: nrep="+nrep+", repCount="+repCount+", n="+n+", parSize="+parSize);
			for(int i=0;i<n;i++) {
				Global.PSEG.emit(new SVM_LOADC(Type.T_INT, null), "putPar: ASSREP'fill: ");
				
			}
//			Util.IERR("");
		}
		
		
//		npop = nrep;
//		return npop;
		return nrep;
	}

}
