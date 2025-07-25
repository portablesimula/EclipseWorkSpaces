package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.BecGlobal;
import bec.util.Util;
import bec.value.ProgramAddress;
import bec.value.Value;

/**
 * 
 * BESKRIVELSE ....
 * Jump to switch case 'tos' in DESTAB.
 */
public class SVM_SWITCH extends SVM_Instruction {
	ProgramAddress[] DESTAB;

	public SVM_SWITCH(ProgramAddress[] DESTAB) {
		this.opcode = SVM_Instruction.iSWITCH;
		this.DESTAB = DESTAB;
	}

	@Override
	public void execute() {
		int idx = RTStack.popInt();
		BecGlobal.PSC = DESTAB[idx].copy();
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
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
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
