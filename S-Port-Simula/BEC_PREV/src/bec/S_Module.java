package bec;

import bec.descriptor.LabelDescr;
import bec.descriptor.ProfileDescr;
import bec.descriptor.RoutineDescr;
import bec.descriptor.Variable;
import bec.instruction.Instruction;
import bec.statement.IfConstrction;
import bec.statement.InsertStatement;
import bec.statement.ProtectConstruction;
import bec.statement.SkipifConstruction;
import bec.util.BecGlobal;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.virtualMachine.SVM_LINE;

public abstract class S_Module {


	/**
	 * 	program_element
	 * 		::= instruction
	 * 		::= label_declaration
	 * 		::= routine_profile | routine_definition
	 * 		::= skip_statement | if_statement
	 * 		::= protect_statement
	 * 		::= goto_statement | insert_statement
	 * 		::= delete_statement
	 */
	public static void programElements() {
		LOOP: while(true) {
//			System.out.println("S_Module.programElements: CurInstr="+Scode.edInstr(Scode.curinstr));
			switch(Scode.curinstr) {
				case Scode.S_LABELSPEC ->	LabelDescr.ofLabelSpec();
				case Scode.S_LABEL ->		LabelDescr.ofLabelDef(Tag.ofScode());
				case Scode.S_PROFILE ->		ProfileDescr.ofScode();
				case Scode.S_ROUTINE ->		RoutineDescr.ofRoutineDef();
				case Scode.S_IF ->			IfConstrction.ofScode();
				case Scode.S_SKIPIF ->		SkipifConstruction.ofScode();
				case Scode.S_SAVE ->		ProtectConstruction.ofStatement();  // ProtectConstruction(true)
				case Scode.S_INSERT ->		new InsertStatement(false);
				case Scode.S_SYSINSERT ->	new InsertStatement(true);
				case Scode.S_LOCAL ->		Variable.ofGlobal(BecGlobal.DSEG);
				default -> { if(! Instruction.inInstruction()) break LOOP; }
			}
			Scode.inputInstr();
		}
//		System.out.println("S_Module.programElements: Terminated by: " + Scode.edInstr(Scode.curinstr));
//		Util.IERR("");
	}

	public static void setSwitch() {
		Util.IERR("NOT IMPL");
	}

	public static void setLine(int type) {
		BecGlobal.curline = Scode.inNumber();
		BecGlobal.PSEG.emit(new SVM_LINE(type, BecGlobal.curline), "MONITOR.setLine: ");
	}

}
