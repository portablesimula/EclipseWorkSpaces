package PREV;

import java.io.IOException;
import java.util.Vector;

import PREV.syntaxClass.instruction.LINE;
import PREV.syntaxClass.instruction.RECORD;
import PREV.syntaxClass.programElement.BODY;
import PREV.syntaxClass.programElement.INFO;
import PREV.syntaxClass.programElement.INSERT;
import PREV.syntaxClass.programElement.LABELSPEC;
import PREV.syntaxClass.programElement.PREV_Variable;
import PREV.syntaxClass.programElement.ProgramElement;
import PREV.syntaxClass.programElement.SETSWITCH;
import PREV.syntaxClass.programElement.TAGLIST;
import PREV.syntaxClass.programElement.routine.PREV_PROFILE;
import PREV.syntaxClass.programElement.routine.PREV_ROUTINE;
import PREV.syntaxClass.value.PREV_CONST;
import bec.descriptor.Kind;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class PREV_ModuleDefinition extends PREV_S_Module {
	Vector<ProgramElement> localQuantities;
	Vector<ProgramElement> programElements;
	boolean withEndProgram;

	public PREV_ModuleDefinition() {
		localQuantities = new Vector<ProgramElement>();
		programElements = new Vector<ProgramElement>();
		parse();
	}
	
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
	/**
	 * 	module_definition ::= module module_id:string check_code:string
	 * 							visible_existing
	 * 							body <local_quantity>* <program_element>* endmodule
	 * 
	 * 		visible_existing ::= <visible>* tag_list | existing
	 * 
	 * 			visible
	 * 				::= record_descriptor | routine_profile
	 * 				::= routine_specification | label_specification
	 * 				::= constant_specification | insert_statement
	 * 				::= info_setting
	 * 
	 * 			tag_list ::= < tag internal:tag external:number >+
	 * 
	 * 			local_quantity
	 * 				::= local var:newtag quantity_descriptor
	 * 				::= constant_definition                                             // DETTE ER NYTT
	 * 
	 *				constant_definition                                                 // DETTE ER NYTT
	 *					::= const const:spectag quantity_descriptor repetition_value    // DETTE ER NYTT
	 */
	private void parse() {
		Scode.expect(Scode.S_MODULE);
		modident = Scode.inString();
		modcheck = Scode.inString();
		Global.moduleID = modident;
		String sourceID = Global.getSourceID();
		Global.CSEG = new DataSegment("CSEG_" + sourceID, Kind.K_SEG_CONST);
		Global.DSEG = new DataSegment("DSEG_" + sourceID, Kind.K_SEG_DATA);
		Global.PSEG = new ProgramSegment("PSEG_" + sourceID, Kind.K_SEG_CODE);

		Scode.inputInstr();
		while(visible()) Scode.inputInstr();
		
		programElements.add(new TAGLIST());

		if(Scode.curinstr != Scode.S_BODY)
			Util.IERR("Illegal termination of module head");
		programElements.add(new BODY());

		LOOP:while(true) {
			switch(Scode.nextByte()) {
				case Scode.S_LOCAL: Scode.inputInstr(); localQuantities.add(new PREV_Variable(Scode.S_LOCAL)); break;
				case Scode.S_CONST: Scode.inputInstr(); localQuantities.add(PREV_CONST.of(true)); break;
				default: break LOOP;
			}
		}

		LOOP:while(true) {
			ProgramElement elt = ProgramElement.inProgramElement();
			if(elt == null) break LOOP;
			programElements.add(elt);
		}

  		if(Global.PRINT_SVM_CODE) printTree(0);

		if(Scode.curinstr != Scode.S_ENDMODULE)
			Util.IERR("Improper termination of module");
		if(Scode.accept(Scode.S_ENDPROGRAM))
			withEndProgram = true;
		
		for(ProgramElement elt:programElements) {
			boolean ok = false;
			if(elt instanceof PREV_CONST) ok = true;
			else if(elt instanceof PREV_PROFILE) ok = true;
			else if(elt instanceof PREV_ROUTINE) ok = true;
			
			else if(elt instanceof TAGLIST) ; // Nothing
			else if(elt instanceof BODY) ; // Nothing
			else Util.IERR("MISSING: "+elt.getClass().getSimpleName());
			if(ok) elt.doCode();
		}
		
		
		try {
			this.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean visible() {
//		System.out.println("ModuleDefinition.visible: curinstr = " + Scode.edInstr(Scode.curinstr));
		boolean result = true;
		switch(Scode.curinstr) {
			case Scode.S_CONST->   		PREV_CONST.of(true);
			case Scode.S_CONSTSPEC->    PREV_CONST.of(false);
			case Scode.S_LABELSPEC->    new LABELSPEC();
			case Scode.S_RECORD->       new RECORD();
			case Scode.S_PROFILE->      new PREV_PROFILE(); // InProfile(P_VISIBLE);
			case Scode.S_ROUTINESPEC->  PREV_ROUTINE.ofSpec(true); // SpecRut(true);
			case Scode.S_INSERT->       new INSERT(false);
			case Scode.S_SYSINSERT->    new INSERT(true);
			case Scode.S_DECL->			new LINE(1);
			case Scode.S_STMT->			new LINE(2);
			case Scode.S_LINE->			new LINE(0);
			case Scode.S_SETSWITCH->    new SETSWITCH(); // SetSwitch();
			case Scode.S_INFO->         new INFO(); // Ed(errmsg,InString); WARNING("Unknown info: ");
		default-> result = false;
		}
		return(result);
	}


	@Override
	public void printTree(final int indent) {
//		System.out.println("\nModuleDefinition.printTree: nLocalQuantities="+localQuantities.size());
//		System.out.println("ModuleDefinition.printTree: nProgramElements="+programElements.size());
		sLIST(0, 0, "PROGRAM " + modident + " " + modcheck);
		sLIST(0, 0, "MODULE");
		for(ProgramElement quant:localQuantities) quant.printTree(indent + 1);
		for(ProgramElement elt:programElements) elt.printTree(indent + 1);
		sLIST(0, Scode.curline, "ENDMODULE");
		if(withEndProgram) sLIST(0, Scode.curline, "ENDPROGRAM");
	}
	
}
