package bec.statement;

import bec.S_Module;
import bec.compileTimeStack.CTStack;
import bec.instruction.FETCH;
import bec.instruction.Instruction;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_PUSHLEN;
import bec.virtualMachine.SVM_RESTORE;
import bec.virtualMachine.SVM_SAVE;

public class ProtectConstruction { // extends ProgramElement {
	
//	public ProtectStatement() {
//		parse();
//	}

	/**
	 * protect_statement ::= save <program_element>* restore
	 * 
	 * End-Condition: Scode'nextByte = First byte after RESTORE  ???
	 */
	public static void ofStatement() {
		// ProtectConstruction(true)
		doSAVE();
		
		Scode.inputInstr();
		S_Module.programElements();
//		IO.println("ProtectConstruction.ofStatement: " + Scode.edInstr(Scode.curinstr));
		if(Scode.curinstr != Scode.S_RESTORE)
			Util.IERR("Improper termination of protect-construction");
		doRESTORE();
	}
	
	/// save
	/// * force TOS value; check TOS type(OADDR);
	/// * pop;
	/// * remember stack;
	/// * purge stack;
	///
	/// TOS describes the address of a save-object. The size of this object is as determined by the
	/// preceding pushlen. The complete state of the stack is remembered (together with the values of
	/// ALLOCATED and MARKS) and the compilation continues with an empty stack.
	///
	/// Code is generated, which - if TOS.VALUE <> onone (see note below) - at run time will save the
	/// used part of the temporary area, and set the SAVE-MARKS attribute.
	///
	/// TOS is popped.
	private static void doSAVE() {
		CTStack.checkTosType(Type.T_OADDR);
		CTStack.forceTosValue();
		CTStack.pop();
		CTStack.SAVE("SAVE");
		
		Global.PSEG.emit(new SVM_SAVE(), "ProtectConstruction.ofStatement: ");
	}
	
	
	/// restore
	/// * check TOS ref; check TOS type(OADDR);
	/// * push(onone); perform assign;
	/// * check stack empty;
	/// * reestablish stack remembered at corresponding save;
	///
	/// The stack remembered by the corresponding save is reestablished (together with the attributes
	/// ALLOCATED and MARKS).
	///
	/// Code is generated, which - if TOS.VALUE <> onone (see note below) - at run time will copy the
	/// content of the specified save-object into the temporary area (the save-object will be the one
	/// generated at the corresponding save). After the copy has been taken, onone is assigned to what is
	/// referred by TOS.
	///
	/// TOS is popped.
	private static void doRESTORE() {
		CTStack.checkTosRef(); CTStack.checkTosType(Type.T_OADDR);
		CTStack.forceTosValue();
		CTStack.pop();
		CTStack.checkStackEmpty();
		CTStack.RESTORE();
		
		Global.PSEG.emit(new SVM_RESTORE(), "ProtectConstruction.ofStatement: ");
	}

	/**
	 * protect_instruction ::= save <instruction>* restore
	 * 
	 * End-Condition: Scode'nextByte = First byte after RESTORE  ???
	 */
	public static void ofInstruction() {
		doSAVE();
		
		do Scode.inputInstr(); while(Instruction.inInstruction());

		if(Scode.curinstr != Scode.S_RESTORE)
			Util.IERR("Improper termination of protect-construction");
		doRESTORE();
	}
	
	public String toString() {
		return "SAVE "; // + lab;
	}

}
