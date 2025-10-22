package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Type;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;

/// Operation LOADA objadr size
/// 
///	  Runtime Stack
///		..., oaddr(?), offset(?), index(?) →
///		..., resadr, ofst
///
/// First, if the 'objadr.indexed' flag is set, the 'index' is popped off the Runtime stack and added to 'offset'.
///
/// Then Force 'objadr' unstacked. Ie. pop off any stacked part and form the resulting address 'resadr'.
/// 
/// The unstacking of the 'objadr' depend on its address kind:
///
/// - REMOTE_ADDR: object address 'oaddr' is popped of the Runtime stack.
///                resadr := oaddr + objadr.offset
/// - REFER_ADDR:  'offset' and object address 'oaddr' is popped of the Runtime stack.
///                resadr := oaddr + objadr.offset + offset
/// - Otherwise:   resadr := objadr
/// 
/// Finally, 'resadr' and 'offset' are pushed onto the Runtime stack.
///
public class SVM_LOADA extends SVM_Instruction {
	private final ObjectAddress objadr;
	private final int offset;

	public SVM_LOADA(ObjectAddress objadr, int offset) {
		this.opcode = SVM_Instruction.iLOADA;
		this.objadr = objadr;
		this.offset = offset;
	}

	@Override
	public void execute() {
		int ofst = (! objadr.indexed)? offset : offset + RTStack.popInt();
		ObjectAddress resadr = objadr.toRTMemAddr();
		RTStack.push(resadr);
		RTStack.push(IntegerValue.of(Type.T_INT, ofst));
		
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
//		this.indexed = inpt.readBoolean();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		objadr.writeBody(oupt);
		oupt.writeShort(offset);
//		oupt.writeBoolean(indexed);
	}

	public static SVM_LOADA read(AttributeInputStream inpt) throws IOException {
		SVM_LOADA instr = new SVM_LOADA(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
