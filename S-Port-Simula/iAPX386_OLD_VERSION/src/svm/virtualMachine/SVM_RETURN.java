package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import svm.value.ProgramAddress;
import svm.value.Value;
import svm.value.dataAddress.FrameAddress;

public class SVM_RETURN extends SVM_Instruction {
	public String rutID;
	public FrameAddress returAddr;

	public SVM_RETURN(String rutID, FrameAddress returSlot) {
		this.opcode = SVM_Instruction.iRETURN;
		this.rutID = rutID;
		this.returAddr = returSlot;
		if(rutID == null) Util.IERR("");
		if(returSlot == null) Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "RETURN   " + rutID + " " + returAddr;
	}
	
	@Override	
	public void execute() {
//		RTStack.printCallTrace("SVM_RETURN.execute: RETURN From "+ rutID);
		ProgramAddress padr = (ProgramAddress) returAddr.load();
		if(Global.CALL_TRACE_LEVEL > 0) {
			IO.println("SVM_RETURN.execute: RETURN From "+ rutID + " and Continue at " + padr);
			RTStack.printCallTrace("SVM_RETURN.execute: RETURN From "+ rutID + " and Continue at " + padr);
//			if(rutID.equals("EVTIME")) {
				RTStack.dumpRTStack("SVM_RETURN.execute: ");
//				int idx = 0;
//				Value txtent = RTStack.load(idx);
//				IO.println("SVM_RETURN.execute: TOS="+txtent);
//				DataAddress obj = (DataAddress) txtent.value();
//				IO.println("SVM_RETURN.execute: obj="+obj);
//				RTUtil.dumpEntity(obj);
//				Util.IERR("");
//			}
		}
		
//		RTStack.checkStackEmpty(); // TODO: MYHTEST
		CallStackFrame callStackTop = RTStack.callStack_TOP();
		if(Global.EXEC_TRACE > 0) {
			ProgramAddress.printInstr(this,false);
		}
		
		int n = RTStack.size() - callStackTop.rtStackIndex - callStackTop.exportSize;
//		IO.println("SVM_RETURN.execute: RTStack.size=" + RTStack.size() + ", rtStackIndex=" + callStackTop.rtStackIndex + ", exportSize=" + callStackTop.exportSize);
//		IO.println("SVM_RETURN.execute: n=" + n);
		
		for(int i=0;i<n;i++) RTStack.pop();	
		
//		IO.println("SVM_RETURN.execute: RTStack.size=" + RTStack.size());
//		RTStack.checkStackEmpty();  // TODO: MYHTEST
//		Util.IERR("");
		
		RTStack.callStack.pop();
		
//		if(Global.EXEC_TRACE > 1) {
		if(Global.EXEC_TRACE > 0) {
//			if(rutID.equals("GTINTA")) {
//				RTUtil.dumpCurins();
//				Util.IERR("");
//			}
//			IO.println("SVM_RETURN.execute: RETURN From "+ rutID + " and Continue at " + padr);
			if(Global.EXEC_TRACE > 1) {
				callStackTop = RTStack.callStack_TOP();
				if(callStackTop == null) {
					RTStack.dumpRTStack("SVM_RETURN: From "+ rutID + " and Continue at " + padr);
				} else {
					callStackTop.dump("SVM_RETURN: From "+ rutID + " and Continue at " + padr);
				}
			}
		}
		Global.PSC = padr.copy();
	}

	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_RETURN(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iRETURN;
		this.rutID = inpt.readString();
		this.returAddr = (FrameAddress) Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeString(rutID);
		returAddr.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_RETURN(inpt);
	}

}
