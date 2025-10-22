package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.Value;

/// Operation CALL rutadr returSlot
/// 
///	  Runtime Stack
///		..., value1, value2, ... , value'n →
///		..., value1, value2, ... , value'size
///
///
public class SVM_CALL extends SVM_Instruction {
	private final ProgramAddress rutAddr;
	private final ObjectAddress returSlot;

	public SVM_CALL(ProgramAddress rutAddr, ObjectAddress returSlot) {
		this.opcode = SVM_Instruction.iCALL;
		this.rutAddr = rutAddr;
		this.returSlot = returSlot;
	}
	
	public static SVM_CALL ofTOS(ObjectAddress returSlot) {
		return new SVM_CALL(null, returSlot);
	}
	
	@Override	
	public void execute() {
		ProgramAddress retur = Global.PSC.copy();
		retur.addOfst(1);
		if(Option.EXEC_TRACE > 0) {
			ProgramAddress.printInstr(this,false);
		}
		if(rutAddr == null) {
			// CALL-TOS			
			Global.PSC = (ProgramAddress) RTStack.pop().copy();
		} else {
			Global.PSC = rutAddr.copy();
		}
		RTStack.push(retur);
	}
	
	@Override	
	public String toString() {
		String tail = " Return=" + returSlot;
		if(rutAddr == null)
		return "CALL     TOS" + tail;
		return "CALL     " + rutAddr + tail;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_CALL(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iCALL;
		this.returSlot = (ObjectAddress) Value.read(inpt);
		boolean present = inpt.readBoolean();
		this.rutAddr = (!present)? null : (ProgramAddress) Value.read(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		returSlot.write(oupt);
		if(rutAddr != null) {
			oupt.writeBoolean(true);
			rutAddr.write(oupt);
		} else oupt.writeBoolean(false);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_CALL(inpt);
	}


}
