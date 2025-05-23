package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.Temp;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_ADDREG;
import bec.virtualMachine.SVM_LOADC;
import bec.virtualMachine.SVM_MULT;
import bec.virtualMachine.SVM_STORE2REG;

public abstract class INDEX extends Instruction {
	int instr; // INDEX | INDEXV

	private static final boolean DEBUG = false;

	/**
	 * addressing_instruction ::= ::= index | indexv
	 * 
	 * force TOS value; check TOS type(INT);
	 * check SOS ref;
	 * pop;
	 * 
	 * TOS.OFFSET := SOS.OFFSET ++ "SOS.SIZE * value(TOS)"
	 * 
	 * SOS is considered to describe an element of a repetition, and the purpose of the instruction is to
	 * select one of the components of the repetition by indexing relative to the current position. The
	 * effect may perhaps best be understood by considering an infinite array A with elements of
	 * SOS.TYPE. The array is placed so that element A(0) is the quantity described by SOS. After
	 * index the stack top will describe A(N), where N is the value of TOS. No bounds checking should
	 * be performed.
	 */
	public static void ofScode(int instr) {
		CTStack.checkTosInt(); CTStack.checkSosRef();
		if(! (CTStack.TOS() instanceof Temp)) Util.IERR("");
		
		CTStack.pop();
		AddressItem adr = (AddressItem) CTStack.TOS();
		int size = adr.size;
		
//		System.out.println("INDEX.ofScode: adr.offset="+adr.offset);
//		System.out.println("INDEX.ofScode: adr.size="+adr.size);
//		System.out.println("INDEX.ofScode: adr="+adr+"                 R"+adr.xReg);
		
		if(adr.xReg > 0) {
			if(size > 1) {
				Global.PSEG.emit(new SVM_LOADC(Type.T_INT, IntegerValue.of(Type.T_INT, size)), "INDEX.ofScode: ");
				Global.PSEG.emit(new SVM_MULT(), "INDEX.ofScode: ");
//				Util.IERR("NOT IMPL");
			}
			Global.PSEG.emit(new SVM_ADDREG(adr.xReg), "INDEX.ofScode: ");
		} else {
			adr.xReg = RTRegister.getFreeReg();
			if(size > 1) {
				Global.PSEG.emit(new SVM_LOADC(Type.T_INT, IntegerValue.of(Type.T_INT, size)), "INDEX.ofScode: ");
				Global.PSEG.emit(new SVM_MULT(), "INDEX.ofScode: ");
			}
			Global.PSEG.emit(new SVM_STORE2REG(adr.xReg), "INDEX.ofScode: ");
		}
		if(DEBUG) Global.PSEG.dump("INDEX.ofScode: ");
		
		if(instr == Scode.S_INDEXV) FETCH.doFetch("INDEXV");

//		Global.PSEG.dump("INDEX.ofScode: ");
//		CTStack.dumpStack("INDEX.ofScode: ");
//		Util.IERR("INDEX.ofScode: ");
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr);
	}
	

}
