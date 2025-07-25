package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.BecGlobal;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.Value;

public class SVM_RETURN extends SVM_Instruction {
	public String rutID;
	public ObjectAddress returAddr;

	public SVM_RETURN(String rutID, ObjectAddress returAddr) {
		this.opcode = SVM_Instruction.iRETURN;
		this.rutID = rutID;
		this.returAddr = returAddr;
		if(rutID == null) Util.IERR("");
		if(returAddr == null) Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "RETURN   " + rutID + " " + returAddr;
	}
	
	@Override	
	public void execute() {
//		RTStack.printCallTrace("SVM_RETURN.execute: RETURN From "+ rutID);
		ProgramAddress padr = (ProgramAddress) returAddr.load();
		if(BecGlobal.CALL_TRACE_LEVEL > 0) {
			System.out.println("SVM_RETURN.execute: RETURN From "+ rutID + " and Continue at " + padr);
			RTStack.printCallTrace("SVM_RETURN.execute: RETURN From "+ rutID + " and Continue at " + padr);
//			if(rutID.equals("CONCAT")) {
//				RTStack.dumpRTStack("SVM_RETURN.execute: ");
//				int idx = 0;
//				Value txtent = RTStack.load(idx);
//				System.out.println("SVM_RETURN.execute: TOS="+txtent);
//				ObjectAddress obj = (ObjectAddress) txtent.value();
//				System.out.println("SVM_RETURN.execute: obj="+obj);
//				RTUtil.dumpEntity(obj);
//				Util.IERR("");
//			}
		}
		RTStack.checkStackEmpty();
		CallStackFrame callStackTop = RTStack.callStack_TOP();
		if(BecGlobal.EXEC_TRACE > 0) {
			ProgramAddress.printInstr(this,false);
		}
		
		int n = RTStack.size() - callStackTop.rtStackIndex - callStackTop.exportSize;
		for(int i=0;i<n;i++) RTStack.pop();	
		
		RTStack.callStack.pop();
		
//		if(Global.EXEC_TRACE > 1) {
		if(BecGlobal.EXEC_TRACE > 0) {
//			if(rutID.equals("GTINTA")) {
//				RTUtil.dumpCurins();
//				Util.IERR("");
//			}
//			System.out.println("SVM_RETURN.execute: RETURN From "+ rutID + " and Continue at " + padr);
			if(BecGlobal.EXEC_TRACE > 1) {
				callStackTop = RTStack.callStack_TOP();
				if(callStackTop == null) {
					RTStack.dumpRTStack("SVM_RETURN: From "+ rutID + " and Continue at " + padr);
				} else {
					callStackTop.dump("SVM_RETURN: From "+ rutID + " and Continue at " + padr);
				}
			}
		}
		BecGlobal.PSC = padr.copy();
	}

	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_RETURN(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iRETURN;
		this.rutID = inpt.readString();
		this.returAddr = (ObjectAddress) Value.read(inpt);
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeString(rutID);
		returAddr.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_RETURN(inpt);
	}

}
