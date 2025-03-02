package bec;

import bec.descriptor.Kind;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.virtualMachine.SVM_Instruction;

public class MainProgram extends S_Module {
	
	/**
	 * 	MainPprogram ::= main <local_quantity>* <program_element>*
	 * 
	 * 		local_quantity ::= local var:newtag quantity_descriptor
	 * 
	 *			quantity_descriptor ::= resolved_type < Rep count:number >?
	 *
	 *		program_element
	 *			::= instruction
	 *			::= label_declaration
	 *			::= routine_profile
	 *			::= routine_definition
	 *			::= if_statement
	 *			::= skip_statement
	 *			::= insert_statement
	 *			::= protect_statement
	 *			::= delete_statement
	 */
	public MainProgram() {
		Global.currentModule = this;
		//  M a i n   P r o g r a m  ---
//        if PROGID.val=0 then PROGID:=DefSymb("MAIN") endif;
//        BEGASM(CSEGNAM,DSEGNAM); ed(sysedit,"SIM_");
//        EdSymb(sysedit,PROGID); entx:=DefPubl(pickup(sysedit));
//        MainEntry:=NewFixAdr(CSEGID,entx);
//        DefLABEL(qBPROC,MainEntry.fix.val,entx.val);

		String sourceID = Global.getSourceID();
		Global.CSEG = new DataSegment("CSEG_" + sourceID, Kind.K_SEG_CONST);
		Global.DSEG = new DataSegment("DSEG_" + sourceID, Kind.K_SEG_DATA);
		Global.PSEG = new ProgramSegment("PSEG_" + sourceID, Kind.K_SEG_CODE);
		ProgramAddress mainEntry = Global.PSEG.nextAddress();
		if(Global.PROGID == null) Global.PROGID = Global.modident;

		while(Scode.nextByte() == Scode.S_LOCAL) {
			Scode.inputInstr(); 
			Util.IERR("NOT IMPL");
//			Minut.inGlobal();
		}
		Scode.inputInstr(); 
		programElements();

//        Qf2(qRET,0,0,0,0);
//        DefLABEL(qEPROC,MainEntry.fix.val,entx.val);
//        peepExhaust(true); ENDASM;
		
		if(Global.PRINT_SVM_CODE) {
			Global.CSEG.dump("END MainProgram: ");
			Global.DSEG.dump("END MainProgram: ");
			Global.PSEG.dump("END MainProgram: ");
		}
		
//		Segment.dumpAll("MainProgram: ");
	
		if(Scode.curinstr != Scode.S_ENDPROGRAM)
			Util.IERR("Illegal termination of program");
		
		System.out.println("\n\nNEW MainProgram: BEGIN EXECUTE: " + mainEntry);
//		MemAddr PSC = mainEntry;
		Global.PSC = mainEntry;
		while(true) {
			Global.PSC.execute();
		}
		
	}
	

}
