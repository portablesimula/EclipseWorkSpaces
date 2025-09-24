package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.Reg;
import bec.util.Global;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.Value;
import bec.value.dataAddress.DataAddress;

/**
 * Operation LOAD reg value
 * 
 * A value is stored in the register 'reg'.
 */
public class SVM_LOADC extends SVM_Instruction {
	int reg;
	Value value;
	
	private static final boolean DEBUG = false;
	
	public SVM_LOADC(int reg, Value value) {
		this.opcode = SVM_Instruction.iLOADC;
		this.reg = reg;
		this.value = value;
		Reg.writes("SVM_LOADC: ", reg);
	}
	
	@Override
	public void execute() {
		Util.IERR("NOT IMPL");
		if(DEBUG) {
			IO.println(this.toString());
			RTStack.dumpRTStack("SVM_LOAD: ");
		}
//		if(value != null) IO.println("SVM_LOAD: "+RTRegister.edReg(reg)+" value="+value.getClass().getSimpleName()+"  "+value);
		if(value == null) {
			RTRegister.putIntValue(reg, 0);
		} else if(value instanceof IntegerValue ival) {
			RTRegister.putIntValue(reg, ival.value);
		} else if(value instanceof DataAddress addr) {
			RTRegister.putAddrValue(reg, addr);				
		} else Util.IERR(""+value);
		if(DEBUG) {
			IO.println("SVM_LOAD: "+RTRegister.toLine());
		}
		
		Global.PSC.addOfst(1);
	}
	
	public String toString() {
		return "LOADC    " + Reg.edReg(reg) + " " + value;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_LOADC(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iLOADC;
		this.reg = inpt.readShort();
		this.value = Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(reg);
		value.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_LOADC(inpt);
	}

}
