package bec.virtualMachine;

import java.io.IOException;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;

public class SVM_PRECALL extends SVM_Instruction {
	String rutIdent;
	int exportSize;
	int importSize;

	public SVM_PRECALL(String rutIdent, int exportSize, int importSize) {
		this.opcode = SVM_Instruction.iPRECALL;
		this.rutIdent = rutIdent;
		this.exportSize = exportSize;
		this.importSize = importSize;
	}

	@Override
	public void execute() {
		RTStack.precallStack.push(new CallStackFrame(rutIdent, RTStack.size(), exportSize, importSize));
		for(int i=0;i<exportSize;i++) {
			RTStack.push(null, "EXPORT"); // Export slots		
		}
		Global.PSC.ofst++;
	}
	
	@Override	
	public String toString() {
		return "PRECALL   ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeString(rutIdent);
		oupt.writeShort(exportSize);
		oupt.writeShort(importSize);
	}

	public static SVM_PRECALL read(AttributeInputStream inpt) throws IOException {
		String rutIdent = inpt.readString();
		int exportSize = inpt.readShort();
		int importSize = inpt.readShort();
		SVM_PRECALL instr = new SVM_PRECALL(rutIdent, exportSize, importSize);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
