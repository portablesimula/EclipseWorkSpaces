package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;

public class RTIndex {
	public int size;
	public int reg;
	
	public RTIndex(int size, int reg) {
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
	private RTIndex(AttributeInputStream inpt) throws IOException {
		this.size = inpt.readShort();
		this.reg = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

//	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeShort(size);
		oupt.writeShort(reg);
	}

	public static RTIndex read(AttributeInputStream inpt) throws IOException {
		return new RTIndex(inpt);
	}

}
