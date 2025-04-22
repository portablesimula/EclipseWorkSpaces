package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.ObjectAddress;

/**
 * The two top elements are replaced by the object address.
 *    RESULT = new ObjectAddress(segID, sos - tos)
 */
public class SVM_DECO extends SVM_Instruction {

	private final boolean DEBUG = false;

	public SVM_DECO() {
		this.opcode = SVM_Instruction.iDECO;
	}

	@Override
	public void execute() {
		int tos = RTStack.popInt();
		ObjectAddress sos = RTStack.popOADDR();
		if(DEBUG) {
			System.out.println("SVM_DECO: TOS: " + tos);
			System.out.println("SVM_DECO: SOS: " + sos);
			System.out.println("SVM_DECO: " + sos + " - " + tos);
		}
		ObjectAddress res = (sos == null)? new ObjectAddress(null, -tos) : sos.addOffset(-tos);
		if(DEBUG) System.out.println("SVM_DECO: " + sos + " - " + tos + " ==> " + res);
		RTStack.push(res, "SVM_DECO: " + tos + " - " + sos + " = " + res);
		Global.PSC.ofst++;
	}

	@Override	
	public String toString() {
		return "DECO     ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_DECO read(AttributeInputStream inpt) throws IOException {
		SVM_DECO instr = new SVM_DECO();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
