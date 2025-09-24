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
 * Operation POPR reg
 * 
 * Runtime Stack
 *    ..., value →
 *    ...
 *    
 * A value is poped off the Runtime Stack and stored in the register 'reg'.
 */
public class SVM_POP2REG extends SVM_Instruction {
	int reg;
	
	private static final boolean DEBUG = false;
	
	public SVM_POP2REG(int reg) {
		this.opcode = SVM_Instruction.iPOP2REG;
		this.reg = reg;
		Reg.writes("SVM_POP2REG: ", reg);
	}
	
	@Override
	public void execute() {
		if(DEBUG) {
			IO.println(this.toString());
			RTStack.dumpRTStack("SVM_POP2REG: ");
		}
		Value value = RTStack.pop();
//		if(value != null) IO.println("SVM_POP2REG: "+RTRegister.edReg(reg)+" value="+value.getClass().getSimpleName()+"  "+value);
		if(value == null) {
			RTRegister.putIntValue(reg, 0);
		} else if(value instanceof IntegerValue ival) {
			RTRegister.putIntValue(reg, ival.value);
		} else if(value instanceof DataAddress addr) {
			RTRegister.putAddrValue(reg, addr);				
		} else Util.IERR(""+value);
		if(DEBUG) {
			IO.println("SVM_POP2REG: "+RTRegister.toLine());
		}
		
		Global.PSC.addOfst(1);
	}
	
	public String toString() {
		return "POP2REG  " + Reg.edReg(reg);
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_POP2REG(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPOP2REG;
		this.reg = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(reg);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_POP2REG(inpt);
	}

}
