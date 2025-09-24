package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;

/**
 * DEBUG Operation REGUSE xReg on
 * 
 */
public class SVM_CLEAR_REGUSE extends SVM_Instruction {
	int xReg;

//	private final boolean DEBUG = false;

	public SVM_CLEAR_REGUSE(int xReg) {
		this.opcode = SVM_Instruction.iREGUSE;
		this.xReg = xReg;
		++
	}
	
	@Override
	public void execute() {
		RTRegister.clearRTUse(xReg);
		Global.PSC.addOfst(1);
	}
	
	@Override
	public String toString() {
		String s = "CLEAR_REGUSE " + RTRegister.edRegVal(xReg);
		if(Global.duringEXEC()) {
			
		} else {
			s += "   " + RTRegister.edRTRegused("RegUsed: ");
		}
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_CLEAR_REGUSE(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iREGUSE;
		this.xReg = inpt.readReg();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(xReg);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_CLEAR_REGUSE(inpt);
	}

}
