package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;


/**
 * addressing_instruction ::= deref
 * 
 * 
 * check TOS ref;
 * TOS.MODE := VAL; TOS.TYPE := GADDR;
 * TOS is modified to describe the address of the area.
 * 
 *     (TOS) ------------------------------------------,
 *                              REF                     |
 *                                                      |
 *                                                      |
 *  The resulting           .==================.        V
 *       TOS ---------------|--> GADDR VALUE --|------->.========,
 *  after deref             '=================='        |        |
 *                                                      |        |
 *                                                      '========'
 */
public class SVM_DEREF extends SVM_Instruction {
	ObjectAddress objadr;
	int offset;
	int xReg;
	
	private final boolean DEBUG = false;

	public SVM_DEREF(ObjectAddress objadr, int offset, int xReg) {
		this.objadr = objadr;
		this.offset = offset;
		this.xReg = xReg;
		this.opcode = SVM_Instruction.iDEREF;
	}

	@Override
	public void execute() {
		if(DEBUG) System.out.println("SVM_DEREF.execute: " + this);
		switch(objadr.kind) {
			case ObjectAddress.SEGMNT_ADDR: RTStack.push(objadr, "SVM_DEREF"); break;
			case ObjectAddress.REMOTE_ADDR: break; // Nothing
			case ObjectAddress.REFER_ADDR: 	break; // Nothing
			case ObjectAddress.REL_ADDR:
			case ObjectAddress.STACK_ADDR:
				Util.IERR("SVM_DEREF.execute: NOTE: CREATE AN ADDRESS TO A VALUE ON THE RTSTACK: " + objadr);	    		
		}

		if(xReg > 0) {
			Value val = RTRegister.getValue(xReg);
			if(val == null) {
				RTStack.push(IntegerValue.of(Type.T_INT, offset), "SVM_DEREF");
			} else if(val instanceof IntegerValue ival) {
				RTStack.push(IntegerValue.of(Type.T_INT, ival.value + offset), "SVM_DEREF");
			} else if(val instanceof GeneralAddress gaddr) {
				
				if(this.objadr.kind == ObjectAddress.REMOTE_ADDR) Util.IERR("TRODDE IKKE DETTE VAR MULIG: "+this.objadr);
				
				RTStack.push(gaddr.base, "SVM_DEREF");
				RTStack.push(IntegerValue.of(Type.T_INT, gaddr.ofst), "SVM_DEREF");
			} else Util.IERR(""+val.getClass().getSimpleName());
		} else {
			RTStack.push(IntegerValue.of(Type.T_INT, offset), "SVM_DEREF");
		}

		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		String tail = "";
	    if(objadr.kind == ObjectAddress.REMOTE_ADDR) tail = tail +" withRemoteBase";
		if(xReg > 0) tail = tail + " R" + xReg + '(' + RTRegister.getValue(xReg) + ')';
		return "DEREF      " + objadr + tail;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		objadr.writeBody(oupt);
		oupt.writeShort(offset);
		oupt.writeReg(xReg);
	}

	public static SVM_DEREF read(AttributeInputStream inpt) throws IOException {
		ObjectAddress objadr = null;
		objadr = ObjectAddress.read(inpt);
		int offset = inpt.readShort();
		int xReg = inpt.readReg();
		SVM_DEREF instr = new SVM_DEREF(objadr, offset, xReg);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
