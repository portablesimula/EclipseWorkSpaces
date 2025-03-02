package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.compileTimeStack.Temp;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.SVM_ADD;
import bec.virtualMachine.SVM_POP2REG;
import bec.virtualMachine.SVM_PUSHC;
import bec.virtualMachine.SVM_PUSHR;

public abstract class DEREF extends Instruction {
	
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
	public static void ofScode() {
		CTStack.dumpStack("DEREF: ");

		CTStack.checkTosRef();
//        adr:=TOS;
//%+S        if SYSGEN <> 0
//%+S        then if adr.size <> (TTAB(adr.type).nbyte)
//%+S             then WARNING("DEREF on parameter") endif;
//%+S        endif;

//		CTStack.assertAtrStacked();
//		assertAtrStacked();
		
		AddressItem TOS = (AddressItem) CTStack.TOS;
		System.out.println("DEREF.ofScode: TOS="+TOS.getClass().getSimpleName());
		System.out.println("DEREF.ofScode: TOS.objadr="+TOS.objadr);
		System.out.println("DEREF.ofScode: TOS.objreg="+TOS.objReg);
		
		boolean CASE2 = true;
		
		if(CASE2) {
			Global.PSEG.emit(new SVM_PUSHC(TOS.objadr), "DEREF'objadr: ");
			if(TOS.objReg > 0) {
				Global.PSEG.emit(new SVM_PUSHR(Type.T_INT, TOS.objReg), "DEREF'objReg: ");
				if(TOS.offset > 0) {
					Global.PSEG.emit(new SVM_PUSHC(new IntegerValue(Type.T_INT, TOS.offset)), "DEREF'offset:   ");
					Global.PSEG.emit(new SVM_ADD(), "DEREF'objadr+offset: ");	
//					if(TOS.offset == 32) Util.IERR("");
				}
			} else if(TOS.atrReg > 0) {
				Util.IERR("NOT IMPL");
			} else {
				Global.PSEG.emit(new SVM_PUSHC(new IntegerValue(Type.T_INT, TOS.offset)), "DEREF'offset:   ");				
			}
//			Global.PSEG.emit(new SVM_POP2REG(RTRegister.qEBX, 2), "DEREF'objadr+offset: ");
		} else {
			Global.PSEG.emit(new SVM_PUSHC(TOS.objadr), "DEREF'objadr: ");
			if(TOS.offset > 0) {
				Global.PSEG.emit(new SVM_PUSHC(new IntegerValue(Type.T_INT, TOS.offset)), "DEREF'offset:   ");
				Global.PSEG.emit(new SVM_ADD(), "DEREF'objadr+offset: ");
			}
			Global.PSEG.emit(new SVM_POP2REG(RTRegister.qEBX, 1), "DEREF'objadr+offset: ");
		}

		CTStack.pop();
//		CTStack.pushTemp(Type.T_GADDR, "DEREF: ");
		CTStack.pushTemp(Type.T_GADDR, RTRegister.qEBX, 2, "DEREF: ");

		CTStack.dumpStack("DEREF: ");
		Global.PSEG.dump("DEREF: ");
//		Util.IERR("");
	}

	private static void assertAtrStacked() {
	      assertObjStacked();
//	      if TOS qua Address.AtrState=NotStacked
//	      then TOS qua Address.AtrState:=FromConst;
//	           Qf2(qPUSHC,0,FreePartReg,cVAL,TOS qua Address.Offset);
//	      elsif TOS qua Address.AtrState=Calculated
//	      then if TOS qua Address.Offset <> 0
//	           then
//	                Qf2(qLOADC,0,qEAX,cVAL,TOS qua Address.Offset);
//	                Qf1(qPOPR,qEBX,cVAL);
//	                Qf2(qDYADR,qADD,qEAX,cVAL,qEBX); Qf1(qPUSHR,qEAX,cVAL);
//	                TOS qua Address.Offset:=0;
//	           endif;
//	      endif;
	}

	private static void assertObjStacked() {
//	      infix(MemAddr) adr;
//	%+C   if TOS.kind <> K_Address
//	%+C   then IERR("CODER.AssertObjStacked-1") endif;
//	      if TOS qua Address.ObjState=NotStacked
//	      then TOS qua Address.ObjState:=FromConst;
//	           adr:=TOS qua Address.Objadr;
//	           case 0:adrMax (adr.kind)
//	           when reladr,locadr: 
//	%+E             Qf3(qPUSHA,0,qEBX,cOBJ,adr);
//	           when segadr,fixadr,extadr:
//	%+E             Qf2b(qPUSHC,0,qEBX,cOBJ,0,adr);
//	%+C        otherwise IERR("CODER.AssertObjStacked-2")
//	           endcase;
//	      endif;
	}

}
