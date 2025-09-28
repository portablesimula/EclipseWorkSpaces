package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import svm.value.IntegerValue;
import svm.value.dataAddress.DataAddress;
import svm.value.dataAddress.SegmentAddress;

/**
 * Operation DIST
 *
 * Runtime Stack
 *    ..., sos, tos →
 *    ..., result
 *
 * Pop two DataAddress values from the Runtime Stack and
 * push the result size(sos - tos) onto the Runtime Stack.
 * 
 * Both Object Addresses should belong to the same Segment; otherwise error.
 * 
 * The result is calculated as:
 * 
 *    result = new SIZE(sos'offset - tos'offset)
 */
public class SVM_DIST extends SVM_Instruction {

	private final boolean DEBUG = false;

	public SVM_DIST() {
		this.opcode = SVM_Instruction.iDIST;
	}

	@Override
	public void execute() {
//		RTStack.dumpRTStack("SVM_DIST: ");
		DataAddress tos = (SegmentAddress) RTStack.popOADDR();
		DataAddress sos = RTStack.popOADDR();
		if(DEBUG) {
			IO.println("SVM_DIST: TOS: " + tos);
			IO.println("SVM_DIST: SOS: " + sos);
			IO.println("SVM_DIST: " + sos + " - " + tos);
		}
		int tval = (tos == null)? 0 : tos.ofst;
		int sval = (sos == null)? 0 : sos.ofst;
		IntegerValue res = IntegerValue.of(Type.T_SIZE, sval - tval);
		if(DEBUG) IO.println("SVM_DIST: " + sos + " - " + tos + " ==> " + res);
		RTStack.push(res, "SVM_DIST: " + sos + " - " + tos + " = " + res);
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "DIST     ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_DIST read(AttributeInputStream inpt) throws IOException {
		SVM_DIST instr = new SVM_DIST();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
