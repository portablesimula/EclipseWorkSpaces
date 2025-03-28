package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.ProgramAddress;

/**
 * Enter a Routine by pushing local variables onto the runtime stack.
 */
public class SVM_ENTER extends SVM_Instruction {
	String rutIdent;
	int localSize;

	public SVM_ENTER(String rutIdent, int localSize) {
		this.opcode = SVM_Instruction.iENTER;
		this.rutIdent = rutIdent;
		this.localSize = localSize;
	}

	@Override
	public void execute() {
//		RTStack.dumpRTStack("SVM_ENTER.execute: ");
//		RTStack.callStackTop = RTStack.PRECALL.frame;

		CallStackFrame callStackItem = RTStack.precallStack.pop();
//		System.out.println("SVM_ENTER.execute: returnAddr="+callStackItem.returnAddr);
		
		ProgramAddress curAddr = Global.PSC.copy();
		callStackItem.curAddr = curAddr;
		callStackItem.localSize = localSize;
		RTStack.callStack.push(callStackItem);
//		System.out.println("SVM_ENTER.execute: returnAddr="+returnAddr);
//		System.out.println("SVM_ENTER.execute: returnAddr="+callStackItem.returnAddr);
//		callStackItem.dump("SVM_ENTER.execute: ");
		
//		RTStack.printCallStack("SVM_ENTER.execute: TESTING");

		for(int i=0;i<localSize;i++) {
			RTStack.push(null, "LOCAL");
		}
//		System.out.println("SVM_ENTER.execute: RTStack.callStackTop="+RTStack.callStackTop);
		
		if(Global.EXEC_TRACE > 2) {
//			RTStack.dumpRTStack(": ENTER: ");
//			RTStack.callStackTop.dump(""+RTStack.callStackTop.rutAddr+": ENTER: ");
			callStackItem.dump(""+curAddr+": ENTER: ");
//			Util.IERR("");
		}
		
		if(Global.CALL_TRACE_LEVEL > 0)
			RTStack.printCallTrace("SVM_ENTER.execute: ", "CALL");

		Global.PSC.ofst++;
	}

	@Override	
	public String toString() {
		return "ENTER    nLOCAL=" + localSize;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeString(rutIdent);
		oupt.writeShort(localSize);
	}

	public static SVM_ENTER read(AttributeInputStream inpt) throws IOException {
		SVM_ENTER instr = new SVM_ENTER(inpt.readString(), inpt.readShort());
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
