package bec.instruction;

import bec.S_Module;
import bec.util.Scode;
import bec.util.Util;

public abstract class BSEG extends Instruction {

	/**
	 * segment_instruction ::= bseg <program_element>* eseg
	 * 
	 * End-Condition: Scode'nextByte = First byte after ESEG
	 */
	public static void ofScode() {
		// BSEGInstruction
//		Instructions = new Vector<Instruction>();
//		while(Scode.nextByte() != Scode.S_ESEG) {
//			Instruction elt = (Instruction) Instruction.inInstruction();
//			if(elt == null) Util.IERR("Inconsistent instruction in BSE-ESEG");
//			Instructions.add(elt);
//		}
		S_Module.programElements();
		Scode.inputInstr();
		if(Scode.curinstr != Scode.S_ESEG) Util.IERR("Missing ESEG");
		Util.IERR("SJEKK DETTE");
		
//		if(Scode.inputTrace > 3) print();
	}

}
