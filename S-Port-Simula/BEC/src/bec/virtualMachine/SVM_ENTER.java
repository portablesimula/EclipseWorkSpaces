package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;

/**
 * Enter a Routine by pushing local variables onto the runtime stack.
 */
public class SVM_ENTER extends SVM_Instruction {
	int localSize;

	public SVM_ENTER(int localSize) {
		this.opcode = SVM_Instruction.iENTER;
		this.localSize = localSize;
	}

	@Override
	public void execute() {
//		RTStack.dumpRTStack("SVM_ENTER.execute: ");
		RTStack.curFrame = RTStack.PRECALL.frame;
		RTStack.curFrame.rutAddr = Global.PSC;
		for(int i=0;i<localSize;i++) {
			RTStack.push(null, "LOCAL");
		}
		RTStack.curFrame.localSize = localSize;
//		System.out.println("SVM_ENTER.execute: RTStack.curFrame="+RTStack.curFrame);
		
		if(Global.EXEC_TRACE > 2) {
			RTStack.curFrame.dump(""+RTStack.curFrame.rutAddr+": ENTER: ");
//			Util.IERR("");
		}
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
		oupt.writeKind(opcode);
		oupt.writeShort(localSize);
	}

	public static SVM_ENTER read(AttributeInputStream inpt) throws IOException {
		SVM_ENTER instr = new SVM_ENTER(inpt.readShort());
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
