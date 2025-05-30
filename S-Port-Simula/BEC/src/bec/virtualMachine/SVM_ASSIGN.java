package bec.virtualMachine;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

/**
 * Remove two items on the Runtime-Stack and push the value (SOS + TOS)
 */
public class SVM_ASSIGN extends SVM_Instruction {
	private boolean update; // false: ASSIGN, true: UPDATE
	private RTAddress rtAddr;
	private int size;

	private final boolean DEBUG = false;

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
	public SVM_ASSIGN(boolean update, RTAddress rtAddr, int size) {
		this.opcode = SVM_Instruction.iASSIGN;
		this.update = update;
		this.rtAddr = rtAddr;
		this.size = size;
	}

	@Override
	public void execute() {
		if(DEBUG) {
			System.out.println("\nSVM_ASSIGN: BEGIN DEBUG INFO ++++++++++++++++++++++øø++++++++++++++++++++++++++++++++++++++++++++++");
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
		
		if(DEBUG) for(int i=0;i<size;i++) System.out.println("SVM_ASSIGN: BEFORE: sos.store: " + i + " " + values.get(i));
		int n = size;
		for(int i=0;i<size;i++) {
//			addr.store(offset + i, values.get((--n)), "");
			addr.store(i, values.get((--n)), "");
		}
		if(DEBUG) for(int i=0;i<size;i++) System.out.println("SVM_ASSIGN: AFTER: sos.store: " + i + " " + values.get(i));
		
		if(update) {
			for(int i=0;i<size;i++) {
				RTStack.pushx(values, "SVM_ASSIGN");
			}
			if(DEBUG) {
				RTUtil.printCurins();
				RTStack.dumpRTStack("SVM_ASSIGN: ");
				System.out.println("SVM_ASSIGN: END DEBUG INFO\n");
			}
//			Util.IERR("NOT IMPL");
		}
//		RTUtil.printCurins();
//		Segment.lookup("POOL_1").dump(null, 717, 717+13+13);
		
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

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		if(rtAddr == null) Util.IERR("");
		oupt.writeOpcode(opcode);
		oupt.writeBoolean(update);
		rtAddr.write(oupt);
		oupt.writeShort(size);
	}

	public static SVM_ASSIGN read(AttributeInputStream inpt) throws IOException {
		SVM_ASSIGN instr = new SVM_ASSIGN(inpt.readBoolean(), RTAddress.read(inpt), inpt.readShort());
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;			
	}

}
