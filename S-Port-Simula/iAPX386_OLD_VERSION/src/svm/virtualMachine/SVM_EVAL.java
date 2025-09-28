package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import svm.value.IntegerValue;
import svm.value.dataAddress.SegmentAddress;

/**
 * Operation EVAL xReg
 *
 * Runtime Stack
 *    ..., tos →
 *    ..., result
 *
 * Pop one DataAddress value from the Runtime Stack and
 * push the result (tos + xReg) onto the Runtime Stack.
 * 
 * The register xReg should contain an integer value; otherwise error.
 * This value is added to the DataAddress to form the result.
 */
public class SVM_EVAL extends SVM_Instruction {
	int xReg;

	private final boolean DEBUG = false;

	public SVM_EVAL(int xReg) {
		this.opcode = SVM_Instruction.iEVAL;
		this.xReg = xReg;
		if(xReg < 1) Util.IERR("Illegal use of SVM_EVAL");
	}

	@Override
	public void execute() {
		if(DEBUG) IO.println("SVM_EVAL.execute: "+this);
		SegmentAddress tos = (SegmentAddress) RTStack.pop();
		IntegerValue reg = (IntegerValue) RTRegister.getValue(xReg);
		if(DEBUG) {
			IO.println("SVM_EVAL.execute: tos="+tos);
			IO.println("SVM_EVAL.execute: reg="+reg);
		}
		int ofst = (reg == null)? 0 : reg.value;
		SegmentAddress upd = tos.addOffset(ofst);
		RTStack.push(upd, "EVAL: "+tos+"+"+ofst);
		
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "EVAL     " + RTRegister.edReg(xReg);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(xReg);
	}

	public static SVM_EVAL read(AttributeInputStream inpt) throws IOException {
		SVM_EVAL instr = new SVM_EVAL(inpt.readReg());
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
