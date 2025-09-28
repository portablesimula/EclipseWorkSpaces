package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;

public class REMOVE_RTIndex {
	public int size;
	public int reg;
	
	public REMOVE_RTIndex(int size, int reg) {
		this.size = size;
		this.reg = reg;
	}

	public String toString() {
		String s = RTRegister.edReg(reg);
		if(size > 1) s += "*" + size;
		return s;
	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private REMOVE_RTIndex(AttributeInputStream inpt) throws IOException {
		this.size = inpt.readShort();
		this.reg = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

//	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeShort(size);
		oupt.writeShort(reg);
	}

	public static REMOVE_RTIndex read(AttributeInputStream inpt) throws IOException {
		return new REMOVE_RTIndex(inpt);
	}

}
