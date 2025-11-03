/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm.instruction;

import java.io.IOException;

import bec.Global;
import bec.Option;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import svm.RTStack;
import svm.value.ObjectAddress;
import svm.value.Value;

/// SVM-INSTRUCTION: STORE objadr size
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
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/svm/instruction/SVM_STORE.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
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
			addr.store(idx--, item);
		}
		Global.PSC.ofst++;		
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
		oupt.writeByte(opcode);
		objadr.writeBody(oupt);
		oupt.writeShort(size);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_STORE(inpt);
	}

}
