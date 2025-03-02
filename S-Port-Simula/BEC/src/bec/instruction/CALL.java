package bec.instruction;

import bec.compileTimeStack.ProfileItem;
import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.descriptor.ProfileDescr;
import bec.descriptor.RoutineDescr;
import bec.descriptor.Variable;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.virtualMachine.RTAddress;
import bec.virtualMachine.RTFrame;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_CALL;
import bec.virtualMachine.SVM_CALL_DSEG;
import bec.virtualMachine.SVM_NOOP;
import bec.virtualMachine.SVM_NOT_IMPL;
import bec.virtualMachine.SVM_POP2MEM;
import bec.virtualMachine.SVM_PRECALL;
import bec.virtualMachine.SVM_PUSH;
import bec.virtualMachine.SVM_CALLSYS;

public abstract class CALL extends Instruction {
	
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
	public static void ofScode(int nParStaced) {
		int profileTag = Scode.ofScode();
		Scode.inputInstr();
		
//		if(Scode.inputTrace > 3) System.out.println("CallInstruction: n="+n+", Curinstr="+Scode.edInstr(Scode.curinstr));
		ProfileDescr spec = (ProfileDescr) Global.DISPL.get(profileTag);
		if(spec == null) Util.IERR(""+Scode.edTag(profileTag));
//		System.out.println("Call.ofScode-1: BEGIN CALL "+Scode.edTag(profileTag)+" ================================================================================");
//		spec.print("Call.ofScode-1: ");
		ProfileItem pitem = new ProfileItem(Type.T_VOID,spec);
		pitem.nasspar = nParStaced;
		CTStack.push(pitem);
//	    CTStack.dumpStack("Call.ofScode-1: ");
//	    Util.IERR("");
	    
		if(CALL.USE_FRAME_ON_STACK) {
			if(spec.pKind == 0) {
				int exportSize = (spec.getExport() == null)? 0 : spec.getExport().type.size();
				RTFrame frame = new RTFrame(exportSize, spec.frameSize-exportSize-1);
//				Global.PSEG.emit(new SVM_PRECALL(exportSize), ""+Scode.edTag(profileTag));
				Global.PSEG.emit(new SVM_PRECALL(frame), ""+Scode.edTag(profileTag));
			}
    	}
		
//		Vector<Instruction> CALL_TOS_Instructions = null;
		boolean CALL_TOS = false;
		
		LOOP:while(Scode.curinstr != Scode.S_CALL) {
			Instruction.inInstructions();
			if(Scode.curinstr == Scode.S_ASSPAR) {
				Scode.inputInstr();
				putPar(pitem,1);
		      	Global.PSEG.emit(new SVM_NOOP(), "ASSPAR ");
				System.out.println("CallInstruction: ASSPAR: nasspar="+pitem.nasspar);
//			    CTStack.dumpStack("Call.ofScode-2: ");
//			    Util.IERR("");
			}
			else if(Scode.curinstr == Scode.S_ASSREP) {
				int nRep = Scode.inByte();
				Scode.inputInstr();
				putPar(pitem,nRep);
		      	Global.PSEG.emit(new SVM_NOOP(), "ASSREP " + nRep);
//			    CTStack.dumpStack("Call.ofScode-3: ");
//			    Util.IERR("");
//				System.out.println("CallInstruction: ASSREP: NextInstr="+Scode.edInstr(Scode.nextByte()));
			}
			else if(Scode.curinstr == Scode.S_CALL_TOS) {
				CALL_TOS = true;
				break LOOP;
			}
			else Util.IERR("Syntax error in call Instruction");
		}
//	    Util.IERR("");
//	    ---------  Final Actions  ---------
		System.out.println("CallInstruction: FINAL: " + pitem.spc);
	    if(pitem.nasspar != pitem.spc.params.size())
	    	Util.IERR("Wrong number of Parameters: got " + pitem.nasspar + ", required" + +pitem.spc.params.size());
//	    ---------  Call Routine  ---------
		if(CALL.USE_FRAME_ON_STACK) {
//			System.out.println("CALL.ofScode: export="+spec.export);
//			Util.IERR("");
		    if(CALL_TOS) {
//		    	Util.IERR("NOT IMPL");
		    	Global.PSEG.emit(new SVM_NOT_IMPL(), "");

		    	Global.PSEG.emit(SVM_CALL.ofTOS(spec.returSlot), "");
		    	CTStack.pop();
		    } else {
				int bodyTag = Scode.ofScode();
		    	if(spec.pKind > 0) {
		    		Global.PSEG.emit(new SVM_CALLSYS(spec.pKind), "");
		    	} else {
		    		RoutineDescr rut = (RoutineDescr) Global.DISPL.get(bodyTag);
		    		if(rut == null) Util.IERR("Unknown Routine: " + Scode.edTag(bodyTag));
		    		Global.PSEG.emit(new SVM_CALL(rut.getAddress(), spec.returSlot), ""+rut);
		    	}
		    }
		} else {
			ObjectAddress prfAddr = ObjectAddress.ofSegAddr(spec.DSEG, 0);
		    if(CALL_TOS) {
		    	Global.PSEG.emit(SVM_CALL_DSEG.ofTOS(prfAddr), "");
		    	CTStack.pop();
		    } else {
				int bodyTag = Scode.ofScode();
		    	if(spec.pKind > 0) {
		    		Global.PSEG.emit(new SVM_CALLSYS(spec.pKind), "");
		    	} else {
		    		RoutineDescr rut = (RoutineDescr) Global.DISPL.get(bodyTag);
		    		if(rut == null) Util.IERR("Unknown Routine: " + Scode.edTag(bodyTag));
		    		Global.PSEG.emit(new SVM_CALL_DSEG(rut.getAddress(), prfAddr), ""+rut);
		    	}
		    }
		}
//	    repeat while npop<>0 do Pop; npop:=npop-1 endrepeat;
//	    CTStack.dumpStack("PARSE.CallSYS-3");
	    if(CTStack.TOS != pitem) Util.IERR("PARSE.CallSYS-3");
	    CTStack.pop();
		
		// Routines return values on the RT-Stack
		Variable export = spec.getExport();
		if(export != null) {
			Type returnType = export.type;
			System.out.println("CallInstructil.callSYS: returnType="+returnType);
			CTStack.pushTemp(returnType, RTRegister.qEAX, 1, "EXPORT: ");
			if(! CALL.USE_FRAME_ON_STACK) {
				Global.PSEG.emit(new SVM_PUSH(new RTAddress(export.address), returnType.size()), "CallInstruction: EXPORT " + spec);
//				Global.PSEG.dump("END CallInstruction.doCode: ");
			}
//			CTStack.dumpStack("END CallInstruction.doCode: ");
//			Util.IERR("");
		}
//		Global.PSEG.dump("END CALL: ");
//		Util.IERR("");
	}
	
	private static int putPar(ProfileItem pItm, int nrep) { // export range(0:255) npop;
		int npop = 0;
		Variable param = (Variable) pItm.spc.params.get(pItm.nasspar).getMeaning();
		Type parType = param.type;
		int repCount = param.repCount;
//	      n:=TTAB(parType).nbyte; i:=nrep;
		if(nrep>repCount) Util.IERR("Too many values in repeated param: Got "+nrep+", expect "+repCount);
		pItm.nasspar = pItm.nasspar+1;
		StackItem tos = CTStack.TOS;
		Type st = tos.type;
		//--- First: Treat TOS ---
//		System.out.println("CallInstruction.putPar: "+st.tag + " ===> " + parType.tag);
		if(st != parType) CONVERT.GQconvert(parType);
		else if(tos instanceof AddressItem) tos.type = st;
		else CONVERT.GQconvert(parType);
		
		if(CTStack.TOS instanceof AddressItem) Util.GQfetch("putPar: ");
		CTStack.pop();
		
		if(! CALL.USE_FRAME_ON_STACK) {
			int parSize = parType.size();
			ObjectAddress parAddr = param.address;
			Global.PSEG.emit(new SVM_POP2MEM(parAddr, parSize), "putPar: ");
		}
		
//		pItm.spc.printTree(2);
//		Global.PSEG.dump("putPar: ");
//		pItm.spc.DSEG.dump("putPar: ");
//		CTStack.dumpStack("ZZZZZZZZZZZZ putPar: ");
//		Util.IERR("");
		
		if(nrep > 1) { // Then: Treat rest of rep-par ---
			CTStack.dumpStack("putPar: ");
			for(int i=nrep-1;i>0;i--) {
				StackItem TOS = CTStack.takeTOS();
				System.out.println("CALL.putPar: "+TOS);
				if(TOS instanceof AddressItem) Util.IERR("MODE mismatch below TOS");
				if(TOS.type != parType) Util.IERR("TYPE mismatch below TOS -- ASSREP");
				if(! CALL.USE_FRAME_ON_STACK) {
					int parSize = parType.size();
					ObjectAddress parAddr = param.address;
					parAddr = parAddr.ofset(parSize);
					Global.PSEG.emit(new SVM_POP2MEM(parAddr, parSize), "putPar: ASSREP: ");
				}
			}
//			CTStack.dumpStack("putPar: ");
//			Global.PSEG.dump("putPar: ");
//			Util.IERR("Parse.XXX: NOT IMPLEMENTED: nrep="+nrep);
		}
		npop = nrep;
		return npop;
	}

}
