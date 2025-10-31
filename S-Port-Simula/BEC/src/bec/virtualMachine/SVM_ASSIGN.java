/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.virtualMachine;

import java.io.IOException;
import java.util.Vector;

import bec.Global;
import bec.Option;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

/// SVM-INSTRUCTION: ASSIGN objadr size
/// 
///	  Runtime Stack
///		..., oaddr(?), offset(?), index(?), value1, value2, ... , value'size →
///		...
///
/// SVM-INSTRUCTION: UPDATE objadr size
/// 
///   Runtime Stack
///		..., oaddr(?), offset(?), index(?), value1, value2, ... , value'size →
///		..., value1, value2, ... , value'size
///
///
/// First, the values are popped off the Runtime stack and remembered.
/// Then, if the 'objadr.indexed' flag is set, the 'index' is popped off and added to 'objadr'.
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
/// Finally, the remembered values are successively stored in address resadr, resadr+1, ... and upwards
/// and, if UPDATE operation, the values are pushed back onto the Runtime stack.
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_ASSIGN.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_ASSIGN extends SVM_Instruction {
	private final boolean update; // false: ASSIGN, true: UPDATE
	private final ObjectAddress objadr;
	private final int size;

	public SVM_ASSIGN(boolean update, ObjectAddress objadr, int size) {
		this.opcode = SVM_Instruction.iASSIGN;
		this.update = update;
		this.objadr = objadr;
		this.size = size;
	}

	@Override
	public void execute() {
//		try {
		Vector<Value> values = RTStack.pop(size);
		int idx = (! objadr.indexed)? 0 : RTStack.popInt();
		ObjectAddress addr = objadr.toRTMemAddr();
		int n = size;
		for(int i=0;i<size;i++)
			addr.store(idx + i, values.get((--n)));
		if(update) RTStack.pushx(values);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
				
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		String id = (update)? "UPDATE   " : "ASSIGN   ";
		return id + objadr + ", size=" + size;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_ASSIGN(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iSTORE;
		this.update = inpt.readBoolean();
		this.objadr = ObjectAddress.read(inpt);
		this.size = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		if(objadr == null) Util.IERR("");
		oupt.writeByte(opcode);
		oupt.writeBoolean(update);
		objadr.writeBody(oupt);
		oupt.writeShort(size);
	}
	
	public static SVM_ASSIGN read(AttributeInputStream inpt) throws IOException {
		return new SVM_ASSIGN(inpt);
	}

}
