package bec.statement;

import bec.S_Module;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.virtualMachine.SVM_JUMP;
import bec.virtualMachine.SVM_JUMPIF;
import bec.virtualMachine.SVM_NOOP;
import bec.virtualMachine.SVM_NOT_IMPL;

public abstract class IfConstrction {

	private IfConstrction() {
	}

	/**
	 *	if_statement
	 *		::= if relation <program_element>* else_part
	 *
	 *		relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
	 *
	 *		else_part
	 *			::= else <program_element>* endif
	 *			::= endif
	 *
	 *	if_instruction
	 *		::= if relation <instruction>* i_else_part
	 *
	 *
	 *		i_else_part
	 *			::= else <instruction>* endif
	 *			::= endif
	 */
	public static void ofScode() {
		// if relation
		// * force TOS value; force SOS value;
		// * check relation;
		// * pop; pop;
		// * remember stack as "if-stack";
		//
		// The generated code will compute the value of the relation, and transfer control to an "else-label" (to be
		// defined later) if the relation is false. A copy of the complete state of the S-compiler's stack is saved as
		// the "if-stack".
		CTStack.dumpStack("IfConstruction.ofScode: ");
		
		CTStack.checkTypesEqual();
		CTStack.checkSosValue();
		Relation relation = Relation.ofScode();
		Global.ifDepth++;
		
//		int cond = Util.GQrelation();
		// Check Relation
		CTStack.pop();
		CTStack.pop();
//	      old_SAV:=SAV; SAV:=TOS;
		StackItem old_SAV = CTStack.SAV;
		CTStack.SAV = CTStack.TOS;

		CTStack.dumpStack("IfConstruction.ofScode: ");
//		Util.IERR("");
		
		ProgramAddress IF_LABEL = Global.PSEG.nextAddress();
		ProgramAddress ELSE_LABEL = null;
		Global.PSEG.emit(new SVM_JUMPIF(relation.not(), null), "GOTOIF["+Global.ifDepth+"] NOT_" + relation + ':');
//		Global.PSEG.dump();

//		Relation relation = Relation.ofScode();
		System.out.println("IfConstruction.ofScode: CurInstr="+Scode.edInstr(Scode.curinstr));
		
		Scode.inputInstr();
		S_Module.programElements();
		if(Scode.curinstr == Scode.S_ELSE) {
			// else
			// 	* force TOS value;
			// 	* remember stack as "else-stack";
			// 	* reestablish stack saved as "if-stack";
			//
			// 	An unconditional forward branch is generated to an "end-label" (to be defined later). A copy is made of
			// 	the complete state of the stack and this is saved as the "else-stack", then the stack is restored to the state
			// 	saved as the "if-stack". Finally the "else-label" (used by if) is located at the current program point.
			CTStack.TOS = CTStack.SAV;
			CTStack.SAV = old_SAV;
			CTStack.dumpStack("IfConstruction.ofScode: ");
//			Util.IERR("");
			
			ELSE_LABEL = Global.PSEG.nextAddress();
			Global.PSEG.emit(new SVM_JUMP(null), "GOTO_ENDIF["+Global.ifDepth+"]:");
			
			// FIXUP:
			SVM_JUMP instr = (SVM_JUMP) Global.PSEG.instructions.get(IF_LABEL.ofst);
			instr.destination = Global.PSEG.nextAddress();
	      	Global.PSEG.emit(new SVM_NOOP(), "ELSE["+Global.ifDepth+"]:");
	      	
//	      	Global.PSEG.dump("IfConstruction.ofScode: ELSE: ");
//			Util.IERR("");

			Scode.inputInstr();
			S_Module.programElements();
		}
		if(Scode.curinstr != Scode.S_ENDIF) Util.IERR("Missing ENDIF: " + Scode.edInstr(Scode.curinstr));
		// endif
		// * force TOS value;
		// * merge current stack with "else-stack" if it exists, otherwise "if-stack";
		//
		// The current stack and the saved stack are merged. The saved stack will be the "if-stack" if no else-part
		// has been processed, otherwise it will be the "else-stack". The merge takes each corresponding pair of
		// stack items and forces them to be identical by applying fetch operations when necessary - this process
		// will generally involve inserting code sequences into the if-part and the else-part. It is an error if the two
		// stacks do not contain the same number of elements or if any pair of stack items cannot be made
		// identical. After the merge the saved stack is deleted.
		// If no else-part was processed the "else-label", otherwise the "end-label", is located at the current
		// program point.		
		if(ELSE_LABEL != null) {
			// FIXUP:
			SVM_JUMP instr = (SVM_JUMP) Global.PSEG.instructions.get(ELSE_LABEL.ofst);
			instr.destination = Global.PSEG.nextAddress();
	      	Global.PSEG.emit(new SVM_NOOP(), "ENDIF["+Global.ifDepth+"]:");		
		} else {
			// FIXUP:
			SVM_JUMP instr = (SVM_JUMP) Global.PSEG.instructions.get(IF_LABEL.ofst);
			instr.destination = Global.PSEG.nextAddress();
	      	Global.PSEG.emit(new SVM_NOOP(), "ENDIF["+Global.ifDepth+"]:");			
		}
		
      	Global.PSEG.dump("IfConstruction.ofScode: ELSE: ");
		CTStack.dumpStack("IfConstruction.ofScode: ");
//		Util.IERR("");
		
//		Scode.inputInstr();  // ????
		Global.ifDepth--;
	
//		if(Scode.inputTrace > 3) printTree(0);
//		Global.PSEG.emit(new SVM_NOT_IMPL(), "IF Statement");
//		Util.IERR("SJEKK DETTE");
	}

}
