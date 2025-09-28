package svm.virtualMachine;

import java.io.IOException;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;

public class SVM_PRECALL extends SVM_Instruction {
	String rutIdent;
	int nParSlots;
	int exportSize;
	int importSize;

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
					RTStack.push(null, "EXPORT"); // Export slots		
				}
			}
		}
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "PRECALL   " + rutIdent + ", nParSlots=" + nParSlots + ", exportSize=" + exportSize + ", importSize=" + importSize;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
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
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
