package PREV.syntaxClass.programElement.routine;

import java.io.IOException;
import java.util.Vector;

import PREV.syntaxClass.SyntaxClass;
import PREV.syntaxClass.instruction.PREV_Instruction;
import PREV.syntaxClass.programElement.PREV_Variable;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.CTStack;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_RETURN;

public class PREV_ROUTINE extends PREV_Instruction {
	int bodyTag;
	int profileTag;
	Vector<PREV_Variable> localQuantities;
	Vector<PREV_Instruction> instructions;
	
	boolean inHead;
	
//	public DataSegment DSEG;
	public ProgramSegment PSEG;
	
	private PREV_ROUTINE() {
	}
	
	/**
	 * routine_specification ::= routinespec body:newtag profile:tag
	 */
	public static PREV_ROUTINE ofSpec(boolean inHead) {
		PREV_ROUTINE rut = new PREV_ROUTINE();
		rut.bodyTag = Scode.inTag();
		rut.profileTag = Scode.inTag();
		rut.inHead = inHead;
		return rut;
	}

	/**
	 * 	routine_definition
	 * 		::= routine body:spectag profile:tag
	 * 				<local_quantity>* <instruction>*
	 * 			endroutine
	 */
	public static PREV_ROUTINE ofDef() {
		PREV_ROUTINE rut = new PREV_ROUTINE();
		rut.localQuantities = new Vector<PREV_Variable>();
		rut.instructions = new Vector<PREV_Instruction>();
		rut.bodyTag = Scode.inTag();
		rut.profileTag = Scode.inTag();
		while(Scode.accept(Scode.S_LOCAL)) rut.localQuantities.add(new PREV_Variable(Scode.S_LOCAL));
		Scode.inputInstr();
		rut.instructions = PREV_Instruction.inInstructionSet();
		if(Scode.curinstr != Scode.S_ENDROUTINE)
			Util.IERR("Missing ENDROUTINE - Got " + Scode.edInstr(Scode.curinstr));
		
		if(Global.SCODE_INPUT_TRACE) rut.printTree(2);
		return rut;
	}
	
//	@Override
//	public void doCode() {
//		String id = Global.moduleID + '_' + Scode.TAGTABLE[profileTag];
////		DSEG = new DataSegment("DSEG_" + id, Segment.SEG_DATA);
//		PSEG = new ProgramSegment("PSEG_" + id, Segment.SEG_CODE);
//		ProgramSegment prevPSEG = Global.PSEG; Global.PSEG = PSEG;
//		System.out.println("\n-------------------------------------------------- BEGIN PRINT ROUTINE Definition");
//		printTree(2);
//		System.out.println("-------------------------------------------------- ENDOF PRINT ROUTINE Definition");
//		PREV_PROFILE prf = (PREV_PROFILE) Global.getMeaning(profileTag);
////		if(prf.exit != null)
////			 prf.exit.emit(DSEG, "EXIT");
////		else DSEG.emit(null, "RETUR");
////
////		for(Variable var:prf.imports)
////			var.emit(DSEG, "IMPORT");
////		if(prf.export != null) prf.export.emit(DSEG, "EXPORT");
//		for(Variable var:localQuantities) {
//			var.emit(prf.DSEG, "LOCAL");
//		}
//		for(PREV_Instruction instr:instructions) {
////			System.out.println("=========================================================================="+instr);
//			instr.doCode();
//		}
////		PROFILE prf = (PROFILE) Global.getMeaning(profileTag);
//		Global.PSEG.emit(new SVM_RETURN(prf), "");
//		CTStack.checkStackEmpty();
//
//		prf.DSEG.dump("ROUTINE.doCode: ");
//		PSEG.dump("ROUTINE.doCode: ");
//		Global.PSEG = prevPSEG;
////		Util.IERR("");
//	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
		for(PREV_Variable quant:localQuantities)
			sLIST(indent+1, quant.toString());
//		System.out.println("instructions "+instructions.size());
		for(PREV_Instruction instr:instructions)
			sLIST(indent+1, instr.toString());
	}
	
	public String toString() {
		String s = "ROUTINE";
		if(instructions == null) s = s+"SPEC";
		s = s + " " + Scode.edTag(bodyTag) + " " + Scode.edTag(profileTag);
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	public PREV_ROUTINE(AttributeInputStream inpt) throws IOException {
		bodyTag = inpt.readTag();
		profileTag = inpt.readTag();
		
		String ident = inpt.readString();
		PSEG = (ProgramSegment) Segment.lookup(ident);
		if(PSEG == null) Util.IERR("NEW ROUTINE: Unknown segment " + ident);

		if(inpt.curinstr != Scode.S_ENDROUTINE) Util.IERR("IMPOSSIBLE: " + Scode.edTag(inpt.curinstr));

		if(Global.ATTR_INPUT_TRACE) this.printTree(2);
	}
	
//	int bodyTag;
//	int profileTag;
//	Vector<Variable> localQuantities;
//	Vector<Instruction> instructions;
//	
//	public ProgramSegment PSEG;


	public void write(AttributeOutputStream oupt) throws IOException {
		Util.TRACE_OUTPUT("BEGIN Write PROFILE: " + Scode.edTag(profileTag)); // + ", Declared in: " + declaredIn);
		// Write Segments
		PSEG.write(oupt);
		//CSEG.write(oupt);

		oupt.writeKind(Scode.S_ROUTINE); // Mark: This is a ROUTINE
		oupt.writeTag(bodyTag);
		oupt.writeTag(profileTag);
		
		oupt.writeString((PSEG==null)?null:PSEG.ident);

		oupt.writeInstr(Scode.S_ENDROUTINE);		
	}

	public static SyntaxClass readObject(AttributeInputStream inpt) throws IOException {
		return new PREV_ROUTINE(inpt);
	}


}
