/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.virtualMachine;

import java.io.IOException;

import bec.Global;
import bec.Option;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.value.ProgramAddress;
import bec.value.Value;

/// SVM-INSTRUCTION: SWITCH destab
/// 
/// 	Runtime Stack
/// 	   index →
/// 	   - empty
///
/// The 'index' is popped of the Runtim stack.
/// Then Program Sequence Control PCS := destab[index]
///
/// The 'destab' is an array of Program Addresses.
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_SWITCH.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_SWITCH extends SVM_Instruction {
	private final ProgramAddress[] DESTAB;

	public SVM_SWITCH(ProgramAddress[] DESTAB) {
		this.opcode = SVM_Instruction.iSWITCH;
		this.DESTAB = DESTAB;
	}

	@Override
	public void execute() {
		int idx = RTStack.popInt();
		Global.PSC = DESTAB[idx].copy();
	}
	
	@Override	
	public String toString() {
		String s = "SWITCH   " + DESTAB.length;
		for(int i=0;i<DESTAB.length;i++) {
			s = s + " " + DESTAB[i];
		}
		return s;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_SWITCH(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iSWITCH;
		int n = inpt.readShort();
		DESTAB = new ProgramAddress[n];
		for(int i=0;i<n;i++) {
			DESTAB[i] = (ProgramAddress) Value.read(inpt);
		}		
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeByte(opcode);
		int n = DESTAB.length;
		oupt.writeShort(n);
		for(int i=0;i<n;i++) {
			DESTAB[i].write(oupt);
		}
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_SWITCH(inpt);
	}

}
