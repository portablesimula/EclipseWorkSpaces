package bec.instruction;

import bec.S_Module;
import bec.compileTimeStack.CTStack;
import bec.descriptor.Kind;
import bec.segment.ProgramSegment;
import bec.util.BecGlobal;
import bec.util.Scode;
import bec.util.Util;

public abstract class BSEG extends Instruction {
	private static int SEQU;
	
	private static final boolean DEBUG = false;
	
	/**
	 * segment_instruction ::= bseg <program_element>* eseg
	 * 
	 * End-Condition: Scode'nextByte = First byte after ESEG
	 */
	public static void ofScode() {
		Scode.inputInstr();
		
		ProgramSegment prevPSEG = BecGlobal.PSEG;
		BecGlobal.PSEG = new ProgramSegment(BecGlobal.getSourceID()+"_BSEG_" + SEQU++, Kind.K_SEG_CODE);

		if(DEBUG) CTStack.dumpStack("BSEG.ofScode: SAVE: " + CTStack.ident());
		CTStack.BSEG("BSEG");
			if(DEBUG) CTStack.dumpStack("BSEG.ofScode: AFTER SAVE: " + CTStack.ident());
			S_Module.programElements();
//			Scode.inputInstr();
			if(Scode.curinstr != Scode.S_ESEG) Util.IERR("Missing ESEG, Got " + Scode.edInstr(Scode.curinstr));
			if(DEBUG) CTStack.dumpStack("BSEG.ofScode: AFTER ESEG");
		CTStack.ESEG();
		if(DEBUG) CTStack.dumpStack("BSEG.ofScode: RESTORED: " + CTStack.ident());
		
		BecGlobal.routineSegments.add(BecGlobal.PSEG);
		BecGlobal.PSEG = prevPSEG;
		
//		Util.IERR("SJEKK DETTE");	
//		if(Scode.inputTrace > 3) print();
	}

}
