/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.virtualMachine;

import java.io.IOException;

import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.Value;

public class SVM_RETURN extends SVM_Instruction {
	private final String rutID;
	private final ObjectAddress returAddr;

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
		if(Option.CALL_TRACE_LEVEL > 0) {
			IO.println("SVM_RETURN.execute: RETURN From "+ rutID + " and Continue at " + padr);
			RTStack.printCallTrace("SVM_RETURN.execute: RETURN From "+ rutID + " and Continue at " + padr);
//			if(rutID.equals("CONCAT")) {
//				RTStack.dumpRTStack("SVM_RETURN.execute: ");
//				int idx = 0;
//				Value txtent = RTStack.load(idx);
//				IO.println("SVM_RETURN.execute: TOS="+txtent);
//				ObjectAddress obj = (ObjectAddress) txtent.value();
//				IO.println("SVM_RETURN.execute: obj="+obj);
//				RTUtil.dumpEntity(obj);
//				Util.IERR("");
//			}
		}
		RTStack.checkStackEmpty();
		CallStackFrame callStackTop = RTStack.callStack_TOP();
		if(Option.EXEC_TRACE > 0) {
			ProgramAddress.printInstr(this,false);
		}
		
		int n = RTStack.size() - callStackTop.rtStackIndex - callStackTop.exportSize;
		for(int i=0;i<n;i++) RTStack.pop();	
		
		RTStack.callStack.pop();
		
//		if(Option.EXEC_TRACE > 1) {
		if(Option.EXEC_TRACE > 0) {
//			if(rutID.equals("GTINTA")) {
//				RTUtil.dumpCurins();
//				Util.IERR("");
//			}
//			IO.println("SVM_RETURN.execute: RETURN From "+ rutID + " and Continue at " + padr);
			if(Option.EXEC_TRACE > 1) {
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
		this.returAddr = (ObjectAddress) Value.read(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeByte(opcode);
		oupt.writeString(rutID);
		returAddr.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_RETURN(inpt);
	}

}
