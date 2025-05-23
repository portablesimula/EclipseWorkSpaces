package bec.virtualMachine;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

/**
 * Remove two items on the Runtime-Stack and push the value (SOS + TOS)
 */
public class SVM_ASSIGN extends SVM_Instruction {
	private RTAddress rtAddr;  // REMOVE IF  Global.TESTING_ASSIGN = false
	private int size;
	private int offset;        // REMOVE IF  Global.TESTING_ASSIGN = true

	private final boolean DEBUG = false;

	/**
	 * assign_instruction ::= assign | update | rupdate
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
	public SVM_ASSIGN(RTAddress rtAddr, int size) {
		this.opcode = SVM_Instruction.iASSIGN;
		this.rtAddr = rtAddr;
		this.size = size;
	}

	@Override
	public void execute() {
		if(DEBUG) {
			RTUtil.printCurins();
			RTStack.dumpRTStack("SVM_ASSIGN: ");
		}
		Vector<Value> values = RTStack.pop(size);
		if(DEBUG) for(int i=0;i<size;i++) System.out.println("SVM_ASSIGN: values["+i+"] = " + values.get(i));
		
		RTAddress addr = this.rtAddr;
		if(addr.withRemoteBase) {
			// this.addr is Stack Relative Address
			ObjectAddress oaddr = RTStack.popOADDR();
			addr = new RTAddress(oaddr, addr.offset);
			addr.xReg = this.rtAddr.xReg;
		}
		
		if(DEBUG) for(int i=0;i<size;i++) System.out.println("SVM_ASSIGN: BEFORE: sos.store: " + (offset+i) + " " + values.get(i));
		int n = size;
		for(int i=0;i<size;i++) {
			addr.store(offset + i, values.get((--n)), "");
		}
		if(DEBUG) for(int i=0;i<size;i++) System.out.println("SVM_ASSIGN: AFTER: sos.store: " + (offset+i) + " " + values.get(i));
		Global.PSC.ofst++;
	}

	@Override	
	public String toString() {
		return "ASSIGN      " + rtAddr + ", size=" + size;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		if(rtAddr == null) Util.IERR("");
		oupt.writeOpcode(opcode);
		rtAddr.write(oupt);
		oupt.writeShort(size);
	}

	public static SVM_ASSIGN read(AttributeInputStream inpt) throws IOException {
		SVM_ASSIGN instr = new SVM_ASSIGN(RTAddress.read(inpt), inpt.readShort());
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;			
	}

}
