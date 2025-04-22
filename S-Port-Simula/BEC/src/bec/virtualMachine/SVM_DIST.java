package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;

/**
 * TOS and SOS are replaced by a description of the signed distance from TOS to SOS.
 */
public class SVM_DIST extends SVM_Instruction {

	private final boolean DEBUG = false;

	public SVM_DIST() {
		this.opcode = SVM_Instruction.iDIST;
	}

	@Override
	public void execute() {
		ObjectAddress tos = RTStack.popOADDR();
		ObjectAddress sos = RTStack.popOADDR();
		if(DEBUG) {
			System.out.println("SVM_DIST: TOS: " + tos);
			System.out.println("SVM_DIST: SOS: " + sos);
			System.out.println("SVM_DIST: " + sos + " - " + tos);
		}
		int tval = (tos == null)? 0 : tos.getOfst();
		int sval = (sos == null)? 0 : sos.getOfst();
		IntegerValue res = IntegerValue.of(Type.T_SIZE, sval - tval);
		if(DEBUG) System.out.println("SVM_DIST: " + sos + " - " + tos + " ==> " + res);
		RTStack.push(res, "SVM_DIST: " + sos + " - " + tos + " = " + res);
		Global.PSC.ofst++;
	}

	@Override	
	public String toString() {
		return "DIST     ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_DIST read(AttributeInputStream inpt) throws IOException {
		SVM_DIST instr = new SVM_DIST();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
