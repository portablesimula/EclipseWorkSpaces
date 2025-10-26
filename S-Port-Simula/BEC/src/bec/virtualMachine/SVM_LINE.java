/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;

/// SVM-INSTRUCTION: LINE lineType lineNumber
/// 
/// 	Runtime Stack
/// 	   ... →
/// 	   ...
///
/// The Global variable sourceLineNumber := sourceLine;
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_LINE.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_LINE extends SVM_Instruction {
	private final int type; // 0, DCL, STM
	private final int sourceLine;

	public SVM_LINE(int type, int sourceLine) {
		this.type = type;
		this.sourceLine = sourceLine;
	}

	@Override
	public void execute() {
		Global.sourceLineNumber = sourceLine;
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "LINE     " + sourceLine;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeByte(iLINE);
		oupt.writeByte(type+1);
		oupt.writeShort(sourceLine);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		int type = inpt.readUnsignedByte() - 1;
		int sourceline = inpt.readShort();
		SVM_LINE instr = new SVM_LINE(type, sourceline);
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
