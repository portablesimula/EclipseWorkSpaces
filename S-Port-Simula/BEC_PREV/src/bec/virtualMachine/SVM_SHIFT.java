package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.BecGlobal;
import bec.util.Scode;
import bec.util.Util;
import bec.value.Value;

/**
 * Bitwise Shift
 * 
 * Remove two items on the Runtime-Stack and push the value (SOS opr TOS)
 * 
 * 
 * Signed Left Shift	<<	The left shift operator moves all bits by a given number of bits to the left.
 * Signed Right Shift	>>	The right shift operator moves all bits by a given number of bits to the right.
 * Unsigned Right Shift	>>>	It is the same as the signed right shift, but the vacant leftmost position is filled with 0 instead of the sign bit.
 */
public class SVM_SHIFT extends SVM_Instruction {
	int instr;
	
	private final boolean DEBUG = false;

	public SVM_SHIFT(int instr) {
		this.opcode = SVM_Instruction.iSHIFT;
		this.instr = instr;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		if(DEBUG) {
			if(tos != null)	System.out.println("SVM_SHIFT: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			if(sos != null)	System.out.println("SVM_SHIFT: SOS: " + sos.getClass().getSimpleName() + "  " + sos);
			System.out.println("SVM_SHIFT: " + sos + " " + Scode.edInstr(instr) + " " + tos);
		}
		Value res = (sos == null)? null : sos.shift(instr, tos);
		if(DEBUG)
			System.out.println("SVM_SHIFT: " + sos + " " + Scode.edInstr(instr)+ " " + tos + " ==> " + res);
		RTStack.push(res, "SVM_SHIFT: " + tos + " " + Scode.edInstr(instr)+ " " + sos + " = " + res);
		BecGlobal.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		switch(instr) {
		case Scode.S_LSHIFTA: return("LSHIFTA");
		case Scode.S_LSHIFTL: return("LSHIFTL");
		case Scode.S_RSHIFTA: return("RSHIFTA");
		case Scode.S_RSHIFTL: return("RSHIFTL");
		}
		return "SHIFT    ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeKind(instr);
	}

	public static SVM_SHIFT read(AttributeInputStream inpt) throws IOException {
		SVM_SHIFT instr = new SVM_SHIFT(inpt.readKind());
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
