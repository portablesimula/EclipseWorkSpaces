package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;

/**
 * Operation LOADA objadr offset indexed
 * 
 * Case indexed:
 * 		Runtime Stack
 * 		   ..., stacked(?), index →
 * 		   ..., resadr, ofst
 *
 * The 'index' on the top of the Runtime Stack is popped off and added to 'offset' to form the new 'ofst'.
 * Then Force 'objadr' unstacked. IE. pop off stacked part and form the resulting address 'resadr'
 * Then the resulting address and 'ofst' are pushed onto the Runtime Stack.
 * 
 * Case not indexed:
 * 		Runtime Stack
 * 		   ..., stacked(?) →
 * 		   ..., resadr, offset
 *
 * Force 'objadr' unstacked. IE. pop off stacked part and form the resulting address 'resadr'
 * The resulting address and 'offset' are pushed onto the Runtime Stack.
 */

public class SVM_LOADA extends SVM_Instruction {
	private final ObjectAddress objadr;
	private final int offset;
	private final boolean indexed;
	
	private final boolean DEBUG = false;

	public SVM_LOADA(ObjectAddress objadr, int offset, boolean indexed) {
		this.opcode = SVM_Instruction.iLOADA;
		this.objadr = objadr;
		this.offset = offset;
		this.indexed = indexed;
	}

	@Override
	public void execute() {
		if(DEBUG) IO.println("SVM_LOADA.execute: " + this);
		int idx = (! indexed)? 0 : RTStack.popInt();
		if(DEBUG) IO.println("SVM_LOADA.execute: objadr="+objadr+", offset="+offset+", indexed="+indexed+", idx="+idx);
		
		// Force address unstacked. IE. pop off stacked part and form the resulting address 'resadr'
		ObjectAddress resadr = null;
		switch(objadr.kind) {
			case ObjectAddress.SEGMNT_ADDR:
				// RTStack: ...
				resadr = objadr;
				if(DEBUG) IO.println("SVM_LOADA.execute: SEGMENT_ADDR: resadr="+resadr);
				break;
			case ObjectAddress.REMOTE_ADDR:
				// RTStack: ..., objadr
				resadr = (ObjectAddress) RTStack.pop();
				if(DEBUG) IO.println("SVM_LOADA.execute: REMOTE_ADDR: resadr="+resadr);
				break;
			case ObjectAddress.REFER_ADDR:
				// RTStack: ..., objadr, ofst
				int ofst = RTStack.popInt();
				resadr = RTStack.popOADDR().addOffset(ofst);
				if(DEBUG) IO.println("SVM_LOADA.execute: REFER_ADDR: resadr="+resadr);
				break;
//			case ObjectAddress.REL_ADDR:
//			case ObjectAddress.STACK_ADDR:
//				Util.IERR("SVM_LOADA.execute: NOTE: CREATE AN ADDRESS TO A VALUE ON THE RTSTACK: " + objadr);
//				break;
			default: Util.IERR(""+objadr);
		}

		RTStack.push(resadr, "SVM_LOADA: ");
		RTStack.push(IntegerValue.of(Type.T_INT, offset + idx), null);
		
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		String tail = "";
	    if(objadr.kind == ObjectAddress.REMOTE_ADDR) tail = tail +" withRemoteBase";
	    tail += " stackedPart";
		return "LOADA    " + objadr + tail;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	private SVM_LOADA(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iLOADA;
		this.objadr = ObjectAddress.read(inpt);
		this.offset = inpt.readShort();
		this.indexed = inpt.readBoolean();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		objadr.writeBody(oupt);
		oupt.writeShort(offset);
		oupt.writeBoolean(indexed);
	}

	public static SVM_LOADA read(AttributeInputStream inpt) throws IOException {
		SVM_LOADA instr = new SVM_LOADA(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
