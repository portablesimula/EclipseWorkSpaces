package bec.statement;

import bec.S_Module;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;
import bec.value.ProgramAddress;
import bec.virtualMachine.SVM_JUMP;
import bec.virtualMachine.SVM_JUMPIF;
import bec.virtualMachine.SVM_NOOP;

public abstract class SkipifConstruction {
	
	/**
	 * skip_statement ::= skipif relation <program_element>* endskip
	 * 
	 * skip_instruction ::= skipif relation <instruction>* endskip
	 * 
	 *		relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
	 * 
	 * The skip_statement is intended to be used where a transfer of control is to be generated without altering
	 * the state of the stack, commonly to report error conditions during expression evaluation. The skip
	 * instruction is the form the statement takes inside routine bodies.
	 * 
	 * skipif relation
	 * force TOS value; force SOS value;
	 * check relation;
	 * pop; pop;
	 * save skip-stack;
	 * 
	 * The generated code will compute the relation, and control is transferred to an "end-label" (to be defined
	 * later), if the relation is true. A copy of the complete state of the S- compiler`s stack is saved as the
	 * "skip-stack".
	 * 
	 * endskip
	 * check stack empty; restore skip-stack;
	 * 
	 * If it is possible for control to reach the current program point, a call on a suitable run time error routine
	 * must be inserted at the end of the generated skip-branch. This will be the interrupt handler described in
	 * (4).
	 * The "end-label" is located at the current program point, and the "skip-stack" is restored as the current
	 * stack.
	 */
	public static void ofScode() {
		CTStack.dumpStack("SkipifConstruction.ofScode: ");
		
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

		CTStack.dumpStack("SkipifConstruction.ofScode: ");
//		Util.IERR("");
		
		ProgramAddress IF_LABEL = Global.PSEG.nextAddress();
		ProgramAddress END_LABEL = null;
		Global.PSEG.emit(new SVM_JUMPIF(relation, null), "JUMPIF["+Global.ifDepth+"] " + relation + ':');
//		Global.PSEG.dump();

//		Relation relation = Relation.ofScode();
		System.out.println("SkipifConstruction.ofScode: CurInstr="+Scode.edInstr(Scode.curinstr));
		
		Scode.inputInstr();
		S_Module.programElements();

		if(Scode.curinstr != Scode.S_ENDSKIP) Util.IERR("Missing ENDSKIP: " + Scode.edInstr(Scode.curinstr));
		// FIXUP:
		SVM_JUMP instr = (SVM_JUMP) Global.PSEG.instructions.get(IF_LABEL.ofst);
		instr.destination = Global.PSEG.nextAddress();
      	Global.PSEG.emit(new SVM_NOOP(), "ENDSKIP["+Global.ifDepth+"]:");			

      	Global.PSEG.dump("SkipifConstruction.ofScode: ELSE: ");
		CTStack.dumpStack("SkipifConstruction.ofScode: ");
//		Util.IERR("");
		
//		Scode.inputInstr();  // ????
		Global.ifDepth--;

//		if(Scode.inputTrace > 3) print();
//		Util.IERR("NOT IMPLEMENTED");
	}
}
