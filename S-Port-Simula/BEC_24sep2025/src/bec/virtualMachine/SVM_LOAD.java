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
 * Operation LOAD reg rtAddr
 * 
 * A value is loaded from 'rtAddr' and stored in the register 'reg'.
 */
public class SVM_LOAD extends SVM_Instruction {
	int reg;
	DataAddress adr;
	
	private static final boolean DEBUG = false;
	
	public SVM_LOAD(int reg, DataAddress adr) {
		this.opcode = SVM_Instruction.iLOAD;
		this.reg = reg;
		this.adr = adr;
		Reg.writes(reg);
	}
	
	@Override
	public void execute() {
		Util.IERR("NOT IMPL");
		if(DEBUG) {
			IO.println(this.toString());
			RTStack.dumpRTStack("SVM_LOAD: ");
		}
		Value value = adr.load();
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
		return "LOAD     " + RTRegister.edReg(reg) + " " + adr;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_LOAD(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iLOAD;
		this.reg = inpt.readShort();
		this.adr = (DataAddress) Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(reg);
		adr.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_LOAD(inpt);
	}

}
