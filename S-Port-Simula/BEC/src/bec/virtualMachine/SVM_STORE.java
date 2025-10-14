package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.ObjectAddress;
import bec.value.Value;

/// Operation STORE objadr size
/// 
///	  Runtime Stack
///		..., value1, value2, ... , value'size, oaddr(?), offset(?), index(?) →
///		..., value1, value2, ... , value'size
///
///
/// First, the values are popped off the Runtime stack and remembered.
/// If the 'objadr.indexed' flag is set, the 'index' is popped off and added to 'objadr'.
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
/// Finally, the values are successively stored in address resadr, resadr+1, ... and upwards
/// without removing them from the Runtime stack.
public class SVM_STORE extends SVM_Instruction {
	private final ObjectAddress objadr;
	private final int size;
	
	public SVM_STORE(ObjectAddress objadr, int size) {
		this.opcode = SVM_Instruction.iSTORE;
		this.objadr = objadr;
		this.size = size;
	}
	
	@Override
	public void execute() {
		int idx =(! objadr.indexed)? 0 :RTStack.popInt();
		ObjectAddress addr = objadr.toRTMemAddr();
		int n = RTStack.size()-1;
		idx = idx + size - 1;
		for(int i=0;i<size;i++) {
			Value item = RTStack.load(n-i);
			addr.store(idx--, item, "");
		}
		Global.PSC.addOfst(1);		
	}
	
	public String toString() {
		String s = "";
		if(size > 1) s = ", size=" + size;
		return "STORE    " + objadr + s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_STORE(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iSTORE;
		this.objadr = ObjectAddress.read(inpt);
		this.size = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		objadr.writeBody(oupt);
		oupt.writeShort(size);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_STORE(inpt);
	}

}
