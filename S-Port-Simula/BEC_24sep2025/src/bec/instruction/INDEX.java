package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ConstItem;
import bec.compileTimeStack.Reg;
import bec.compileTimeStack.TempItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_ADDREG;
import bec.virtualMachine.SVM_MULREG;
import bec.virtualMachine.SVM_PUSHC;
import bec.virtualMachine.SVM_PUSHR;
import bec.virtualMachine.SVM_MULT;
import bec.virtualMachine.SVM_POP2REG;

public abstract class INDEX extends Instruction {
	int instr; // INDEX | INDEXV

	private static final boolean DEBUG = false;

	/**
	 * addressing_instruction : =  : =  index | indexv
	 * 
	 * force TOS value; check TOS type(INT);
	 * check SOS ref;
	 * pop;
	 * 
	 * TOS.OFFSET  =  SOS.OFFSET ++ "SOS.SIZE * value(TOS)"
	 * 
	 * SOS is considered to describe an element of a repetition, and the purpose of the instruction is to
	 * select one of the components of the repetition by indexing relative to the current position. The
	 * effect may perhaps best be understood by considering an infinite array A with elements of
	 * SOS.TYPE. The array is placed so that element A(0) is the quantity described by SOS. After
	 * index the stack top will describe A(N), where N is the value of TOS. No bounds checking should
	 * be performed.
	 */
	public static void ofScode(int instr) {
			
		boolean TESTING = true;
		
		if(TESTING) {
			CTStack.checkTosInt(); CTStack.checkSosRef();
//	           adr:=TOS.suc; repdist:=adr.repdist;
			AddressItem adr = (AddressItem) CTStack.SOS();
			int size = adr.size;
			if(size == 0) Util.IERR("PARSE.INDEX: Not info type");
//	           if TOS.kind=K_Coonst
//	           then itm:=TOS qua Coonst.itm;
			
			if(CTStack.SOS() instanceof ConstItem itm) {
//	                adr.Offset:=adr.Offset+(repdist*itm.wrd);
				IntegerValue ival = (IntegerValue) itm.value;
				adr.offset = adr.offset + (size * ival.value);
				POP.GQpop();
//	                if adr.AtrState=FromConst
//	                then adr.AtrState:=NotStacked;
//	                     qPOPKill(AllignFac);
//	                endif;
				if(adr.atrState != AddressItem.FromConst) {
					adr.atrState = AddressItem.NotStacked;
					POP.qPOPKill(1);
				}
				Util.IERR("STOP");
			} else {
//	%+E             if TOS.type <> T_WRD4 then GQconvert(T_WRD4) endif;
				Reg.getTosAdjustedIn86(Reg.qEAX);
				CTStack.pop(); AddressItem.assertObjStacked();
				
				if(size > 1) {
//	%+E             GQeMultc(repdist); -- EAX:=EAX*repdist
					Global.PSEG.emit(new SVM_MULREG(Reg.qEAX, size), "INDEX: ");
				}
				if(adr.atrState == AddressItem.FromConst) {
					POP.qPOPKill(1);
				} else if(adr.atrState == AddressItem.Calculated) {
//	%+E             Qf1(qPOPR,qEBX,cVAL);
//	%+E             Qf2(qDYADR,qADDF,qEAX,cVAL,qEBX);
					Global.PSEG.emit(new SVM_POP2REG(Reg.qEBX), "INDEX: ");
					Global.PSEG.emit(new SVM_ADDREG(Reg.qEAX, Reg.qEBX), "INDEX: ");
				}
//	%+E         Qf1(qPUSHR,qEAX,cVAL);
				Global.PSEG.emit(new SVM_PUSHR(Reg.qEAX), "INDEX: ");
				adr.atrState = AddressItem.Calculated;
			}
//	        if CurInstr=S_INDEXV then GQfetch endif;
			if(instr == Scode.S_INDEXV) FETCH.GQfetch("INDEXV");
			
		} else {
//			CTStack.forceTosValue();			
//			CTStack.checkTosInt(); CTStack.checkSosRef();
//			if(! (CTStack.TOS() instanceof TempItem)) Util.IERR("");
//			CTStack.pop();
//			AddressItem adr = (AddressItem) CTStack.TOS();
//			int size = adr.size;
//				
//	//		IO.println("INDEX.ofScode: adr.offset="+adr.offset);
//	//		IO.println("INDEX.ofScode: adr.size="+adr.size);
//	//		IO.println("INDEX.ofScode: adr="+adr+"                 R"+adr.xReg);
//			
//			if(adr.getIndexReg() > 0) {
//				if(size > 1) {
//					Global.PSEG.emit(new SVM_PUSHC(Type.T_INT, IntegerValue.of(Type.T_INT, size)), "INDEX.ofScode: ");
//					Global.PSEG.emit(new SVM_MULT(), "INDEX.ofScode: ");
//	//				Util.IERR("NOT IMPL");
//				}
//				Global.PSEG.emit(new SVM_ADDREG(adr.getIndexReg()), "INDEX.ofScode: ");
//			} else {
//				adr.useIndexReg(RTRegister.getFreeIndexReg());				
//				if(size > 1) {
//					Global.PSEG.emit(new SVM_PUSHC(Type.T_INT, IntegerValue.of(Type.T_INT, size)), "INDEX.ofScode: ");
//					Global.PSEG.emit(new SVM_MULT(), "INDEX.ofScode: ");
//				}
//				Global.PSEG.emit(new SVM_POP2REG(adr.getIndexReg()), "INDEX.ofScode: ");
//			}
//			if(DEBUG) Global.PSEG.dump("INDEX.ofScode: ");
//			if(instr == Scode.S_INDEXV) FETCH.doFetch("INDEXV");
		}
		
		
//		Global.PSEG.dump("INDEX.ofScode: ");
//		CTStack.dumpStack("INDEX.ofScode: ");
//		Util.IERR("INDEX.ofScode: ");
	}

	@Override
	public void print(final String indent) {
		IO.println(indent + toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr);
	}
	

}
