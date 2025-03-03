package PREV.syntaxClass.programElement;

import PREV.syntaxClass.SyntaxClass;
import PREV.syntaxClass.instruction.PREV_Instruction;
import PREV.syntaxClass.programElement.routine.PREV_PROFILE;
import PREV.syntaxClass.programElement.routine.PREV_ROUTINE;
import bec.util.Scode;

public class ProgramElement extends SyntaxClass {

	public static void programElements() {
		while(inProgramElement() != null) ;
	}

	
	public static ProgramElement inProgramElement() {
		Scode.inputInstr();
		PREV_Instruction elt = PREV_Instruction.inInstruction();
		if(elt != null) return elt;
		switch(Scode.curinstr) {
			case Scode.S_LABELSPEC: return new LABELSPEC();
			case Scode.S_LABEL:     return new LABEL();
			case Scode.S_PROFILE:   return new PREV_PROFILE();
			case Scode.S_ROUTINE:   return PREV_ROUTINE.ofDef();
			case Scode.S_IF:        return new IF();
			case Scode.S_SKIPIF:    return new SKIPIF();
			case Scode.S_SAVE:      return new SAVE();
			case Scode.S_INSERT:    return new INSERT(false);
			case Scode.S_SYSINSERT: return new INSERT(true);
			default: return null;
		}
	}

}
