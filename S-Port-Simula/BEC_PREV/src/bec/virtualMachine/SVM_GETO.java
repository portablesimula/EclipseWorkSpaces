package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.BecGlobal;
import bec.value.ObjectAddress;

/**
 * 
 * Code is generated, which in case SAVE-INDEX refers to the "last" pointer of the save object
 * refered by SAVE-OBJECT or no pointer exists in the object, the value onone is returned to
 * signal that the scan of the object should be terminated. Otherwise SAVE-INDEX is updated to
 * describe the "next" pointer of the save object. In case the value of the "next" pointer is onone,
 * the pointer is skipped, i.e. iterate this description, otherwise the value of the refered pointer is
 * returned.
 */
public class SVM_GETO extends SVM_Instruction {

	public SVM_GETO() {
		this.opcode = SVM_Instruction.iGETO;
	}

	@Override
	public void execute() {
		ObjectAddress next = SVM_INITO.get();
		RTStack.push(next, "GETO: ");
		BecGlobal.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "GETO     ";
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_GETO instr = new SVM_GETO();
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
