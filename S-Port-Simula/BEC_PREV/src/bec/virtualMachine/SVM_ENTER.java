package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.BecGlobal;
import bec.util.Util;
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
		CallStackFrame callStackItem = RTStack.precallStack.pop();
		ProgramAddress curAddr = BecGlobal.PSC.copy();
		callStackItem.curAddr = curAddr;
		callStackItem.localSize = localSize;
		RTStack.callStack.push(callStackItem);
		for(int i=0;i<localSize;i++) {
			RTStack.push(null, "LOCAL:");
		}
		if(BecGlobal.EXEC_TRACE > 2)
			callStackItem.dump(""+curAddr+": ENTER: " + rutIdent + ", localSize=" + localSize);
		if(BecGlobal.CALL_TRACE_LEVEL > 0) {
			RTStack.printCallTrace("SVM_ENTER.execute: ENTER " + rutIdent + ", localSize=" + localSize);
		}

//		if(rutIdent.equalsIgnoreCase("PTLFXA")) {
//			RTUtil.dumpCurins();
//			Segment.lookup("PSEG_EDIT_PTLFXA:BODY").dump("ENTER: +++++++++++++++++++++++++++++++++++++++++++++", 0, 10);
////			Util.IERR("");
//		}
//		if(rutIdent.equalsIgnoreCase("B_SUB")) {
//			((DataSegment)Segment.lookup("POOL_1")).addGuard(748);
////			Util.IERR("");
//		}


		BecGlobal.PSC.addOfst(1);
//		if(rutIdent.equalsIgnoreCase("OUTTXT")) {
//			RTUtil.printCurins();
//			RTUtil.printPool("POOL_1");
//			Util.IERR("");
//		}
//		RTStack.dumpRTStack("SVM_ENTER.execute: ");
	}

	@Override	
	public String toString() {
		return "ENTER    nLOCAL=" + localSize;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeString(rutIdent);
		oupt.writeShort(localSize);
	}

	public static SVM_ENTER read(AttributeInputStream inpt) throws IOException {
		SVM_ENTER instr = new SVM_ENTER(inpt.readString(), inpt.readShort());
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
