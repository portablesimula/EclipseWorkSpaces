package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_ADD;
import bec.virtualMachine.SVM_NOOP;
import bec.virtualMachine.SVM_NOT_IMPL;
import bec.virtualMachine.SVM_PUSHC;
import bec.virtualMachine.SVM_PUSHR;

public abstract class DEREF extends Instruction {
	
	private static final boolean DEBUG = false;
	private static final boolean TESTING = true;

	/**
	 * addressing_instruction ::= deref
	 * 
	 * 
	 * check TOS ref;
	 * TOS.MODE := VAL; TOS.TYPE := GADDR;
	 * TOS is modified to describe the address of the area.
	 * 
	 *     (TOS) ------------------------------------------,
     *                              REF                     |
     *                                                      |
     *                                                      |
     *  The resulting           .==================.        V
     *       TOS ---------------|--> GADDR VALUE --|------->.========,
     *  after deref             '=================='        |        |
     *                                                      |        |
     *                                                      '========'
	 */
//    when S_DEREF:
//%+C        CheckTosRef;
//         adr:=TOS;
//%+S        if SYSGEN <> 0
//%+S        then if adr.repdist <> (TTAB(adr.type).nbyte)
//%+S             then WARNING("DEREF on parameter") endif;
//%+S        endif;
//         AssertAtrStacked; Pop; pushTemp(T_GADDR);
	public static void ofScode() {
		if(DEBUG) CTStack.dumpStack("DEREF: ");
		if(TESTING) {
			CTStack.checkTosRef();
			assertAtrStacked();
			CTStack.pop();
//			CTStack.pushTemp(Type.T_GADDR, "DEREF: ");
			CTStack.pushTemp(Type.T_GADDR, RTRegister.qEBX, 2, "DEREF: ");
			return;
		}

//		CTStack.checkTosRef();
//		assertAtrStacked();
//		
//		AddressItem TOS = (AddressItem) CTStack.TOS;
//		if(DEBUG) {
//			System.out.println("DEREF.ofScode: TOS="+TOS.getClass().getSimpleName());
//			System.out.println("DEREF.ofScode: TOS.objadr="+TOS.objadr);
//			System.out.println("DEREF.ofScode: TOS.objreg="+TOS.objReg);
//		}
//		
//		boolean CASE2 = true;
//		if(CASE2) {
//			Global.PSEG.emit(new SVM_PUSHC(TOS.objadr), "DEREF'objadr: ");
//			if(TOS.objReg > 0) {
//				Global.PSEG.emit(new SVM_PUSHR(Type.T_INT, TOS.objReg), "DEREF'objReg: ");
//				if(TOS.offset > 0) {
//					Global.PSEG.emit(new SVM_PUSHC(new IntegerValue(Type.T_INT, TOS.offset)), "DEREF'offset:   ");
//					Global.PSEG.emit(new SVM_ADD(), "DEREF'objadr+offset: ");	
//				}
//			} else if(TOS.atrReg > 0) {
//				Util.IERR("NOT IMPL");
//			} else {
//				Global.PSEG.emit(new SVM_PUSHC(new IntegerValue(Type.T_INT, TOS.offset)), "DEREF'offset:   ");				
//			}
//		} else {
//			Global.PSEG.emit(new SVM_PUSHC(TOS.objadr), "DEREF'objadr: ");
//			if(TOS.offset > 0) {
//				Global.PSEG.emit(new SVM_PUSHC(new IntegerValue(Type.T_INT, TOS.offset)), "DEREF'offset:   ");
//				Global.PSEG.emit(new SVM_ADD(), "DEREF'objadr+offset: ");
//			}
//			Global.PSEG.emit(new SVM_POP2REG(RTRegister.qEBX, 1), "DEREF'objadr+offset: ");
//		}
//
//		CTStack.pop();
////		CTStack.pushTemp(Type.T_GADDR, "DEREF: ");
//		CTStack.pushTemp(Type.T_GADDR, RTRegister.qEBX, 2, "DEREF: ");
//
//		if(DEBUG) {
//			CTStack.dumpStack("DEREF: ");
//			Global.PSEG.dump("DEREF: ");
////			Util.IERR("");
//		}
	}

	
//	Visible Routine AssertAtrStacked;
//	begin
//	%+D   RST(R_AssertAtrStacked);
//	      AssertObjStacked;
//	      if TOS qua Address.AtrState=NotStacked
//	      then TOS qua Address.AtrState:=FromConst;
//	           Qf2(qPUSHC,0,FreePartReg,cVAL,TOS qua Address.Offset);
//	      elsif TOS qua Address.AtrState=Calculated
//	      then if TOS qua Address.Offset <> 0
//	           then
//	%-E             Qf2(qLOADC,0,qAX,cVAL,TOS qua Address.Offset);
//	%-E             Qf1(qPOPR,qBX,cVAL);
//	%-E             Qf2(qDYADR,qADD,qAX,cVAL,qBX); Qf1(qPUSHR,qAX,cVAL);
//	%+E             Qf2(qLOADC,0,qEAX,cVAL,TOS qua Address.Offset);
//	%+E             Qf1(qPOPR,qEBX,cVAL);
//	%+E             Qf2(qDYADR,qADD,qEAX,cVAL,qEBX); Qf1(qPUSHR,qEAX,cVAL);
//	                TOS qua Address.Offset:=0;
//	           endif;
//	      endif;
//	end;
	
	private static void assertAtrStacked() {
		boolean NYTEST = true;
		assertObjStacked();
		AddressItem tos = (AddressItem) CTStack.TOS;
		System.out.println("DEREF.assertAtrStacked: tos.atrState="+tos.atrState);
		if(tos.atrState==AddressItem.State.NotStacked) {
			tos.atrState=AddressItem.State.FromConst;
			// Qf2(qPUSHC,0,FreePartReg,cVAL,TOS qua Address.Offset);
			Global.PSEG.emit(new SVM_PUSHC(new IntegerValue(Type.T_INT,tos.offset)), "DEREF'offset: ");
//	      	Global.PSEG.emit(new SVM_NOOP(), "HVA HER ? " + tos);
//			Util.IERR("");
		} else if(tos.atrState==AddressItem.State.Calculated) {
			System.out.println("DEREF.assertAtrStacked: tos="+tos);
			System.out.println("DEREF.assertAtrStacked: tos.objReg="+tos.objReg);
			if(NYTEST) {
				if(tos.objReg > 0) {
//					Global.PSEG.emit(new SVM_PUSHR(Type.T_INT, tos.objReg), "DEREF'objReg: ");
					Global.PSEG.emit(new SVM_PUSHR(Type.T_INT, tos.objReg), "DEREF'objReg: ");
					Global.PSEG.emit(new SVM_ADD(), "DEREF'objadr+offset: ");	
					if(tos.offset != 0) {
						Global.PSEG.emit(new SVM_PUSHC(new IntegerValue(Type.T_INT,tos.offset)), "DEREF'offset: ");
						Global.PSEG.emit(new SVM_ADD(), "DEREF'objadr+offset: ");	
//						Util.IERR("");
					}
				}
			} else {
				if(tos.offset != 0) {
					// Qf2(qLOADC,0,qEAX,cVAL,TOS qua Address.Offset);
					// Qf1(qPOPR,qEBX,cVAL);
					// Qf2(qDYADR,qADD,qEAX,cVAL,qEBX); Qf1(qPUSHR,qEAX,cVAL);
					Global.PSEG.emit(new SVM_NOOP(), "HVA HER-1 ? " + tos);
					Global.PSEG.emit(new SVM_NOT_IMPL("HVA HER-2 ? "), "HVA HER ? " + tos);
					tos.offset=0;
//					Util.IERR("");
				}
			}
		}
	}

	private static void assertObjStacked() {
		AddressItem tos = (AddressItem) CTStack.TOS;
		System.out.println("DEREF.assertObjStacked: tos.objState="+tos.objState);
	    if(tos.objState==AddressItem.State.NotStacked) {
	    	tos.objState=AddressItem.State.FromConst;
	    	ObjectAddress objadr = tos.objadr;
			System.out.println("DEREF.assertObjStacked: tos.objadr="+objadr);
	    	if(objadr.segID == null) {
	    		// when reladr,locadr: 
	    		// Qf3(qPUSHA,0,qEBX,cOBJ,adr);
//				Global.PSEG.emit(new SVM_PUSHC(null), "DEREF'objadr: ");
//				Global.PSEG.emit(new SVM_PUSHC(objadr), "DEREF'objadr: ");
		      	Global.PSEG.emit(new SVM_NOOP(), "HVA HER-3 ? " + tos);
		      	Global.PSEG.emit(new SVM_NOT_IMPL("HVA HER-4 ? "), "HVA HER ? " + tos);
	    		Util.IERR("");
	    	} else {
	            // when segadr,fixadr,extadr:
	            // Qf2b(qPUSHC,0,qEBX,cOBJ,0,adr);
				System.out.println("DEREF.assertObjStacked: tos="+tos);
				Global.PSEG.emit(new SVM_PUSHC(objadr), "DEREF'objadr: ");
//		    	Util.IERR("");
	    	}
//	    } else if(tos.objState==AddressItem.State.Calculated) {
//			if(tos.objReg > 0) {
//				Global.PSEG.emit(new SVM_PUSHR(Type.T_INT, tos.objReg), "DEREF'objReg: ");
//				Global.PSEG.emit(new SVM_ADD(), "DEREF'objadr+offset: ");	
//			}
	    }
	}
	
//	Visible Routine AssertObjStacked;
//	begin infix(MemAddr) adr;
//	%-E   range(0:nregs) segreg;
//	%+D   RST(R_AssertObjStacked);
//	%+C   if TOS.kind <> K_Address
//	%+C   then IERR("CODER.AssertObjStacked-1") endif;
//	      if TOS qua Address.ObjState=NotStacked
//	      then TOS qua Address.ObjState:=FromConst;
//	           adr:=TOS qua Address.Objadr;
//	           case 0:adrMax (adr.kind)
//	           when reladr,locadr: 
//	%-E             segreg:=GetSreg(adr);
//	%-E             PreMindMask:=wOR(PreMindMask,uMask(segreg));
//	%-E             Qf1(qPUSHR,segreg,cOBJ);
//	%-E             Qf3(qPUSHA,0,qBX,cOBJ,adr);
//	%+E             Qf3(qPUSHA,0,qEBX,cOBJ,adr);
//	           when segadr,fixadr,extadr:
//	%-E             Qf2b(qPUSHC,0,qBX,cOBJ,F_BASE,adr);
//	%-E             Qf2b(qPUSHC,0,qBX,cOBJ,F_OFFSET,adr);
//	%+E             Qf2b(qPUSHC,0,qEBX,cOBJ,0,adr);
//	%+C        otherwise IERR("CODER.AssertObjStacked-2")
//	           endcase;
//	      endif;
//	end;
	

}
