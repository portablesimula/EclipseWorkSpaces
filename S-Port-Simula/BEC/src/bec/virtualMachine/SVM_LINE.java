package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;

/**
 * 
 * 
 */
public class SVM_LINE extends SVM_Instruction {
	int type; // 0, DCL, STM
	int sourceLine;

	public SVM_LINE(int type, int sourceLine) {
		this.type = type;
		this.sourceLine = sourceLine;
	}

	@Override
	public void execute() {
		Global.PSC.ofst++;
	}
	
	@Override	
	public String toString() {
		return "LINE     " + sourceLine;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(iLINE);
		oupt.writeKind(type+1);
		oupt.writeShort(sourceLine);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		int type = inpt.readKind() - 1;
		int sourceline = inpt.readShort();
		SVM_LINE instr = new SVM_LINE(type, sourceline);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
