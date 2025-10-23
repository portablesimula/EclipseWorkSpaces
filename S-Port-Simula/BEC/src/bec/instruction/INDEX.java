package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.Temp;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.virtualMachine.SVM_LOADC;
import bec.virtualMachine.SVM_MULT;

public abstract class INDEX extends Instruction {
	int instr; // INDEX | INDEXV

	/**
	 * addressing_instruction ::= ::= index | indexv
	 * 
	 * force TOS value; check TOS type(INT);
	 * check SOS ref;
	 * pop;
	 * 
	 * TOS.OFFSET := SOS.OFFSET + "SOS.SIZE * value(TOS)"
	 * 
	 * SOS is considered to describe an element of a repetition, and the purpose of the instruction is to
	 * select one of the components of the repetition by indexing relative to the current position. The
	 * effect may perhaps best be understood by considering an infinite array A with elements of
	 * SOS.TYPE. The array is placed so that element A(0) is the quantity described by SOS. After
	 * index the stack top will describe A(N), where N is the value of TOS. No bounds checking should
	 * be performed.
	 */
	public static void ofScode(int instr) {
		CTStack.forceTosValue();			
		CTStack.checkTosInt(); CTStack.checkSosRef();
		if(! (CTStack.TOS() instanceof Temp)) Util.IERR("");
		CTStack.pop();
		AddressItem adr = (AddressItem) CTStack.TOS();
		int size = adr.size;
		if(adr.getIndexed()) {
//			if(size > 1) {
//				Global.PSEG.emit(new SVM_LOADC(Type.T_INT, IntegerValue.of(Type.T_INT, size)), "INDEX.ofScode: ");
//				Global.PSEG.emit(new SVM_MULT(), "INDEX.ofScode: ");
//			}
//			Global.PSEG.emit(new SVM_ADD(), "INDEX.ofScode: ");
			Util.IERR("NOT IMPL");
		} else {
			if(size > 1) {
				Global.PSEG.emit(new SVM_LOADC(Type.T_INT, IntegerValue.of(Type.T_INT, size)));
				Global.PSEG.emit(new SVM_MULT());
			}
			adr.setIndexed(true);
		}			
		if(instr == Scode.S_INDEXV) CTStack.forceTosValue();
	}

	@Override
	public void print(final String indent) {
		IO.println(indent + toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr);
	}
	

}
