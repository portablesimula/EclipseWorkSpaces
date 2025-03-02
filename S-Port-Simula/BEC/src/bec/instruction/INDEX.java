package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ConstItem;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.virtualMachine.RTRegister;

public abstract class INDEX extends Instruction {
	int instr; // INDEX | INDEXV
	
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
		CTStack.getTosValueIn86(RTRegister.qEAX);
		CTStack.pop();
		AddressItem adr = (AddressItem) CTStack.TOS;
		adr.objReg = RTRegister.qEAX;
		
		if(instr == Scode.S_INDEXV) Util.GQfetch("INDEXV");

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
