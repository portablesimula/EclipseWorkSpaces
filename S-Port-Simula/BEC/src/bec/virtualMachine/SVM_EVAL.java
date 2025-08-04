package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;

/**
 * The two top elements are replaced by the object address.
 *    RESULT = new ObjectAddress(segID, sos + tos)
 */
public class SVM_EVAL extends SVM_Instruction {
	int xReg;

	private final boolean DEBUG = false;

	public SVM_EVAL(int xReg) {
		this.opcode = SVM_Instruction.iEVAL;
		this.xReg = xReg;
	}

	@Override
	public void execute() {
		if(DEBUG) System.out.println("SVM_EVAL.execute: "+this);
		if(xReg > 0) {
			ObjectAddress tos = (ObjectAddress) RTStack.pop();
			IntegerValue reg = (IntegerValue) RTRegister.getValue(xReg);
			if(DEBUG) {
				System.out.println("SVM_EVAL.execute: tos="+tos);
				System.out.println("SVM_EVAL.execute: reg="+reg);
			}
			int ofst = (reg == null)? 0 : reg.value;
			ObjectAddress upd = tos.addOffset(ofst);
			RTStack.push(upd, "EVAL: "+tos+"+"+ofst);
		}
//		Util.IERR("NOT IMPL");
		
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		String s = "EVAL     ";
		if(xReg > 0) s += RTRegister.edReg(xReg);
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(xReg);
	}

	public static SVM_EVAL read(AttributeInputStream inpt) throws IOException {
		SVM_EVAL instr = new SVM_EVAL(inpt.readReg());
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
