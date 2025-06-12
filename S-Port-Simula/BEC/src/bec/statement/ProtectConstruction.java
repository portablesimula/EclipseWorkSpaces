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
//		System.out.println("ProtectConstruction.ofStatement: " + Scode.edInstr(Scode.curinstr));
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
		FETCH.doFetch(null);
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
		FETCH.doFetch(null);
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
		// ProtectConstruction(false)
		doSAVE();
		
//		Scode.inputInstr();
//		S_Module.programElements();
//		repeat inputInstr while Instruction do endrepeat;
		do Scode.inputInstr(); while(Instruction.inInstruction());
		System.out.println("ProtectConstruction.ofInstruction: " + Scode.edInstr(Scode.curinstr));
		if(Scode.curinstr != Scode.S_RESTORE)
			Util.IERR("Improper termination of protect-construction");
		doRESTORE();
//		Util.IERR("NOT IMPLEMENTED");
	}
	
	public String toString() {
		return "SAVE "; // + lab;
	}
	
//	Routine ProtectConstruction; import Boolean stm;
//	begin Boolean skipped; range(0:255) lng,i; infix(MemAddr) opr;
//	      ref(StackItem) old_SAV; infix(MemAddr) SDpnt;
//	      infix(WORD) dum;
//	      skipped:=SkipProtect; lng:=SavLng;
//	      if skipped then Save86(0,noadr)
//	      else -- lng > 0 --
//	           SDpnt:=EmitLiteral(@sMap.n,(sMap.n+1)*2); sMap.n:=0;
//	%+C        CheckTosType(T_OADDR);
//	%+E        GetTosValueIn86(qEBX); Pop;
//	           opr.kind:=reladr; opr.rela.val:=4; opr.segmid.val:=0;
//	%+E        opr.sibreg:=bEBX+NoIREG;
//	           Save86(lng,opr); opr.rela.val:=0;
//	%+E        Qf4b(qMOVMC,0,qw_D,cOBJ,0,opr,SDpnt);
//	      endif;
//	      old_SAV:=SAV; SAV:=TOS;
//	      if stm then programElements
//	             else repeat inputInstr while Instruction do endrepeat;
//	      endif
//	%+C   if CurInstr <> S_RESTORE
//	%+C   then IERR("Improper termination of protect-construction") endif;
//	%+C   CheckTosRef; CheckTosType(T_OADDR);
//	      SAV:=old_SAV;
//	      if skipped
//	      then -- Remove Effect of: PRECALL restore / CALL restore / PUSH rstr
//	           GQpop;
//	           if qlast.fnc <> qCALL then IERR("PARSE.SAVE-1")
//	           else DeleteLastQ endif;
//	%-E %+C    if CHKSTK then Qf5(qCALL,1,0,0,X_CHKSTK) endif;
//	           InputInstr;
//	%+D        if CurInstr<>S_PRECALL then IERR("PARSE.SAVE-2") endif;
//	           InTag(%dum%);  -- Skip: PRECALL PRESTO
//	           InputInstr;
//	%+D        if CurInstr<>S_CALL   then IERR("PARSE.SAVE-3") endif;
//	           InTag(%dum%);  -- Skip: CALL    PRESTO
//	           Rstr86(0,noadr);
//	      else -- lng>0 --
//	%-E        GQfetch; GetTosValueIn86R3(qBX,qES,0); Pop;
//	%+E        GQfetch; GetTosValueIn86(qEBX); Pop;
//	%-E %+C    if CHKSTK then Qf5(qCALL,1,0,0,X_CHKSTK) endif;
//	           opr.rela.val:=4; Rstr86(lng,opr);
//	%-E        MindMask:=wAND(wNOT(wOR(uES,uBX)),MindMask);
//	%+E        MindMask:=wAND(wNOT(uEBX),MindMask);
//	      endif;
//	end;

}
