package bec.virtualMachine;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.BecGlobal;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.ObjectAddress;
import bec.value.Value;

/**
 * Remove two items on the Runtime-Stack and push the value (SOS + TOS)
 */
public class SVM_ASSIGN extends SVM_Instruction {
	private boolean update; // false: ASSIGN, true: UPDATE
	private ObjectAddress rtAddr;
	private int xReg;
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
	public SVM_ASSIGN(boolean update, ObjectAddress rtAddr, int xReg, int size) {
		this.opcode = SVM_Instruction.iASSIGN;
		this.update = update;
		this.rtAddr = rtAddr;
		this.xReg = xReg;
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
		
		ObjectAddress addr = this.rtAddr;
		switch(rtAddr.kind) {
			case ObjectAddress.SEGMNT_ADDR: doAssign(addr, xReg, values); break;
			case ObjectAddress.REL_ADDR:    doAssign(addr, xReg, values); break;
			case ObjectAddress.STACK_ADDR:  Util.IERR(""); doAssign(addr, xReg, values); break;
			case ObjectAddress.REMOTE_ADDR:
				// this.addr is Stack Relative Address
				ObjectAddress oaddr = RTStack.popOADDR();
				addr = oaddr.addOffset(addr.getOfst());
				doAssign(addr, xReg, values);
				break;
				
			case ObjectAddress.REFER_ADDR:
				GeneralAddress gaddr = (GeneralAddress) RTRegister.getValue(xReg);
				addr = gaddr.base.addOffset(rtAddr.getOfst() + gaddr.ofst);
				doAssign(addr, 0, values);
				break;
	
			default: Util.IERR("");
		}
		
		BecGlobal.PSC.addOfst(1);
	}
	
	private void doAssign(ObjectAddress addr, int xReg, Vector<Value> values) {
		if(DEBUG) for(int i=0;i<size;i++) System.out.println("SVM_ASSIGN: BEFORE: sos.store: " + i + " " + values.get(i));
		int idx = size;
		int rx = (xReg == 0)? 0 : RTRegister.getIntValue(xReg);
		for(int i=0;i<size;i++) {
			addr.store(rx + i, values.get((--idx)), "");
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
		}		
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
		this.xReg = inpt.readReg();
		this.size = inpt.readShort();
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		if(rtAddr == null) Util.IERR("");
		oupt.writeOpcode(opcode);
		oupt.writeBoolean(update);
		rtAddr.writeBody(oupt);
		oupt.writeReg(xReg);
		oupt.writeShort(size);
	}
	
	public static SVM_ASSIGN read(AttributeInputStream inpt) throws IOException {
		return new SVM_ASSIGN(inpt);
	}

}
