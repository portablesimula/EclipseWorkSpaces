package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.value.ProgramAddress;
import bec.value.Value;

/// Operation SWITCH destab
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
		oupt.writeOpcode(opcode);
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
