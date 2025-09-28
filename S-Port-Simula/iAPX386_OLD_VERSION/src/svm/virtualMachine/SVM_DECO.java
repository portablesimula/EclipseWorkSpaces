package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import svm.value.dataAddress.FrameAddress;
import svm.value.dataAddress.DataAddress;
import svm.value.dataAddress.SegmentAddress;

/**
 * Operation DECO
 *
 * Runtime Stack
 *    ..., sos, tos →
 *    ..., result
 *
 * Pop two values from the Runtime Stack and
 * push the result (sos - tos) onto the Runtime Stack.
 * 
 * The tos should be INT and sos an DataAddress; otherwise error.
 * The result is calculated as:
 * 
 *    result = new DataAddress(sos'segID, sos'offset - tos)
 */
public class SVM_DECO extends SVM_Instruction {

	private final boolean DEBUG = false;

	public SVM_DECO() {
		this.opcode = SVM_Instruction.iDECO;
	}

	@Override
	public void execute() {
		int tos = RTStack.popInt();
		SegmentAddress sos = (SegmentAddress) RTStack.popOADDR();
		if(DEBUG) {
			IO.println("SVM_DECO: TOS: " + tos);
			IO.println("SVM_DECO: SOS: " + sos);
			IO.println("SVM_DECO: " + sos + " - " + tos);
		}
//		DataAddress res = (sos == null)? new DataAddress(null, -tos) : sos.addOffset(-tos);
//		DataAddress res = (sos == null)? DataAddress.ofRelFrameAddr( -tos) : sos.addOffset(-tos);
		DataAddress res = (sos == null)? (new FrameAddress( -tos)) : sos.addOffset(-tos);
		if(DEBUG) IO.println("SVM_DECO: " + sos + " - " + tos + " ==> " + res);
		RTStack.push(res, "SVM_DECO: " + tos + " - " + sos + " = " + res);
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "DECO     ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_DECO read(AttributeInputStream inpt) throws IOException {
		SVM_DECO instr = new SVM_DECO();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
