package bec.virtualMachine;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

/**
 * Remove two items on the Runtime-Stack and push the value (SOS + TOS)
 */
public class SVM_ASSIGN extends SVM_Instruction {
	private final boolean update; // false: ASSIGN, true: UPDATE
	private final ObjectAddress rtAddr;
	private final int size;
	private final boolean indexed;

	/**
	 * assign_instruction ::= assign | update
	 * 
	 * assign (dyadic)
	 * 
	 * - force TOS value;
	 * - check SOS ref; check types identical;
	 * - pop; pop;
	 * 
	 * Code is generated to transfer the value described by TOS to the location designated by SOS.
	 * This implies that the stack elements must be evaluated, and that any code generation involving
	 * TOS or SOS, that has been deferred for optimisation purposes, must take place before the
	 * assignment code is generated. SOS and TOS are popped from the stack.
	 */
	/**
	 * Operation LOADA objadr offset indexed
	 * 
	 * Case indexed:
	 * 		Runtime Stack
	 * 		   ..., stacked(?), index →
	 * 		   ..., resadr, ofst
	 *
	 * The 'index' on the top of the Runtime Stack is popped off and added to 'offset' to form the new 'ofst'.
	 * Then Force 'objadr' unstacked. IE. pop off stacked part and form the resulting address 'resadr'
	 * Then the resulting address and 'ofst' are pushed onto the Runtime Stack.
	 * 
	 * Case not indexed:
	 * 		Runtime Stack
	 * 		   ..., stacked(?) →
	 * 		   ..., resadr, offset
	 *
	 * Force 'objadr' unstacked. IE. pop off stacked part and form the resulting address 'resadr'
	 * The resulting address and 'offset' are pushed onto the Runtime Stack.
	 */
	// Force address unstacked. IE. pop off stacked part and form the resulting address 'resadr'
	public SVM_ASSIGN(boolean update, ObjectAddress rtAddr, boolean indexed, int size) {
		this.opcode = SVM_Instruction.iASSIGN;
		this.update = update;
		this.rtAddr = rtAddr;
		this.indexed = indexed;
		this.size = size;
	}

	@Override
	public void execute() {
		Vector<Value> values = RTStack.pop(size);
		int idx = (! indexed)? 0 : RTStack.popInt();
		ObjectAddress addr = rtAddr.toRTMemAddr();
		int n = size;
		for(int i=0;i<size;i++)
			addr.store(idx + i, values.get((--n)), "");
		if(update) 
			for(int i=0;i<size;i++)
				RTStack.pushx(values, "SVM_ASSIGN");
				
		
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		String id = (update)? "UPDATE   " : "ASSIGN   ";
		return id + rtAddr + ", size=" + size;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_ASSIGN(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iSTORE;
		this.update = inpt.readBoolean();
		this.rtAddr = ObjectAddress.read(inpt);
		this.indexed = inpt.readBoolean();
		this.size = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		if(rtAddr == null) Util.IERR("");
		oupt.writeOpcode(opcode);
		oupt.writeBoolean(update);
		rtAddr.writeBody(oupt);
		oupt.writeBoolean(indexed);
		oupt.writeShort(size);
	}
	
	public static SVM_ASSIGN read(AttributeInputStream inpt) throws IOException {
		return new SVM_ASSIGN(inpt);
	}

}
