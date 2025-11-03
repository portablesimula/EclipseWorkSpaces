/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm.instruction;

import java.io.IOException;

import bec.Global;
import bec.Option;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import svm.CallStackFrame;
import svm.RTStack;

/// SVM-INSTRUCTION: PRECALL rutIdent nParSlots exportSize importSize
/// 
///	  Runtime Stack
///		..., arg1, arg2, ... , arg'n →
///		..., value1, value2, ... , value'size
///
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/svm/instruction/SVM_AND.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_PRECALL extends SVM_Instruction {
	private final String rutIdent;
	private final int nParSlots;
	private final int exportSize;
	private final int importSize;

	public SVM_PRECALL(String rutIdent, int nParSlots, int exportSize, int importSize) {
		this.opcode = SVM_Instruction.iPRECALL;
		this.rutIdent = rutIdent;
		this.nParSlots = nParSlots;
		this.exportSize = exportSize;
		this.importSize = importSize;
	}

	@Override
	public void execute() {
		RTStack.precallStack.push(new CallStackFrame(rutIdent, RTStack.size() - nParSlots, exportSize, importSize));
		if(exportSize > 0) {
			if(nParSlots > 0) {
//				RTStack.dumpRTStack("SVM_PRECALL.execute-1");
				RTStack.addExport(nParSlots, exportSize);
//				RTStack.dumpRTStack("SVM_PRECALL.execute-2");
//				Util.IERR("");
			} else {
				for(int i=0;i<exportSize;i++) {
					RTStack.push(null); // Export slots		
				}
			}
		}
		Global.PSC.ofst++;
	}
	
	@Override	
	public String toString() {
		return "PRECALL   " + rutIdent + ", nParSlots=" + nParSlots + ", exportSize=" + exportSize + ", importSize=" + importSize;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeByte(opcode);
		oupt.writeString(rutIdent);
		oupt.writeShort(nParSlots);
		oupt.writeShort(exportSize);
		oupt.writeShort(importSize);
	}

	public static SVM_PRECALL read(AttributeInputStream inpt) throws IOException {
		String rutIdent = inpt.readString();
		int nParSlots = inpt.readShort();
		int exportSize = inpt.readShort();
		int importSize = inpt.readShort();
		SVM_PRECALL instr = new SVM_PRECALL(rutIdent, nParSlots, exportSize, importSize);
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
