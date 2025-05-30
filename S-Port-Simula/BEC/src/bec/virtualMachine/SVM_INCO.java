package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.ObjectAddress;

/**
 * The two top elements are replaced by the object address.
 *    RESULT = new ObjectAddress(segID, sos + tos)
 */
public class SVM_INCO extends SVM_Instruction {

	private final boolean DEBUG = false;

	public SVM_INCO() {
		this.opcode = SVM_Instruction.iINCO;
	}

	@Override
	public void execute() {
		int tos = RTStack.popInt();
		ObjectAddress sos = RTStack.popOADDR();
		if(DEBUG) {
			System.out.println("SVM_INCO: TOS: " + tos);
			System.out.println("SVM_INCO: SOS: " + sos);
			System.out.println("SVM_INCO: " + sos + " + " + tos);
		}
		ObjectAddress res = (sos == null)? new ObjectAddress(null, tos) : sos.addOffset(tos);
		if(DEBUG) System.out.println("SVM_INCO: " + sos + " + " + tos + " ==> " + res);
		RTStack.push(res, "SVM_INCO: " + tos + " + " + sos + " = " + res);
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "INCO     ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_INCO read(AttributeInputStream inpt) throws IOException {
		SVM_INCO instr = new SVM_INCO();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
