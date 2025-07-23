package bec;

import java.util.Vector;

import bec.descriptor.Kind;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.util.EndProgram;
import bec.util.BecGlobal;
import bec.util.Scode;
import bec.util.Util;
import bec.value.ProgramAddress;
import bec.virtualMachine.RTUtil;

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
		BecGlobal.currentModule = this;
		//  M a i n   P r o g r a m  ---
//        if PROGID.val=0 then PROGID:=DefSymb("MAIN") endif;
//        BEGASM(CSEGNAM,DSEGNAM); ed(sysedit,"SIM_");
//        EdSymb(sysedit,PROGID); entx:=DefPubl(pickup(sysedit));
//        MainEntry:=NewFixAdr(CSEGID,entx);
//        DefLABEL(qBPROC,MainEntry.fix.val,entx.val);

		String sourceID = BecGlobal.getSourceID();
		BecGlobal.CSEG = new DataSegment("CSEG_" + sourceID, Kind.K_SEG_CONST);
		BecGlobal.TSEG = new DataSegment("TSEG_" + sourceID, Kind.K_SEG_CONST);
		BecGlobal.DSEG = new DataSegment("DSEG_" + sourceID, Kind.K_SEG_DATA);
		BecGlobal.PSEG = new ProgramSegment("PSEG_" + sourceID, Kind.K_SEG_CODE);
		ProgramAddress mainEntry = BecGlobal.PSEG.nextAddress();
		if(BecGlobal.PROGID == null) BecGlobal.PROGID = BecGlobal.modident;
		BecGlobal.routineSegments = new Vector<Segment>();

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
		
		if(BecGlobal.PRINT_GENERATED_SVM_CODE) {
			BecGlobal.CSEG.dump("END MainProgram: ");
			BecGlobal.TSEG.dump("END MainProgram: ");
			BecGlobal.DSEG.dump("END MainProgram: ");
			BecGlobal.PSEG.dump("END MainProgram: ");
		}
		
//		Segment.dumpAll("MainProgram: ");
//		Segment.lookup("PSEG_STRG_PASS1:BODY").dump("PSEG_STRG_PASS1:BODY",1100,1200);
	
		if(Scode.curinstr != Scode.S_ENDPROGRAM)
			Util.IERR("Illegal termination of program");
		
		try {
			if(BecGlobal.verbose) Util.println("\n\nBEC: NEW MainProgram: BEGIN EXECUTE: " + mainEntry);
			RTUtil.INIT();
			BecGlobal.PSC = mainEntry;
			while(true) {
				BecGlobal.PSC.execute();
			}
		} catch(EndProgram eprog) {

			if(BecGlobal.verbose) {
				Util.println("BEC: MainProgram - Exit: " + eprog.exitCode);
//				Thread.dumpStack();
			}
//			if(Global.INLINE_TESTING) {
//				System.out.println("BecCompiler.UncaughtExceptionHandler: INLINE_TESTING: return");
//				Thread.dumpStack();
//				return;
//			}
			Util.println("\nProgram - Exit: " + eprog.exitCode);
			return;
		}
		
	}
	

}
