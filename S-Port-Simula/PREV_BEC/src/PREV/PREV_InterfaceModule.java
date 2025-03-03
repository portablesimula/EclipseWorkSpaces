package PREV;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import PREV.syntaxClass.SyntaxClass;
import PREV.syntaxClass.instruction.LINE;
import PREV.syntaxClass.instruction.RECORD;
import PREV.syntaxClass.programElement.INFO;
import PREV.syntaxClass.programElement.INSERT;
import PREV.syntaxClass.programElement.PREV_Variable;
import PREV.syntaxClass.programElement.ProgramElement;
import PREV.syntaxClass.programElement.SETSWITCH;
import PREV.syntaxClass.programElement.TAG;
import PREV.syntaxClass.programElement.TAGLIST;
import PREV.syntaxClass.programElement.routine.PREV_PROFILE;
import PREV.syntaxClass.programElement.routine.PREV_ROUTINE;
import PREV.syntaxClass.value.PREV_CONST;
import bec.descriptor.Kind;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class PREV_InterfaceModule extends PREV_S_Module {
	Vector<ProgramElement> programElements;
	Vector<TAG> tagList;

	public PREV_InterfaceModule() {
		programElements = new Vector<ProgramElement>();
		tagList = new Vector<TAG>();

		parse();		
	}
	
	/**
	 * 	interface_module
	 * 		::= global module module_id:string checkcode:string
	 * 					<global interface>* tag_list
	 * 					body < init global:tag type repetition_value >*
	 * 			endmodule
	 * 
	 * 		global_interface
	 * 			::= record_descriptor
	 * 			::= constant_definition < system sid:string >?
	 * 			::= global_definition < system sid:string >?
	 * 			::= routine_profile
	 * 			::= info_setting
	 * 
	 * 				global_definition ::= global internal:newtag quantity_descriptor
	 * 
	 * 		tag_list ::= < tag internal:tag external:number >+
	 * 
	 */
	private void parse() {
		Scode.expect(Scode.S_GLOBAL);
		Scode.expect(Scode.S_MODULE);
		modident = Scode.inString();
		modcheck = Scode.inString();
		Global.moduleID = modident;
		Global.CSEG = new DataSegment("CSEG_" + modident, Kind.K_SEG_CONST);
		Global.DSEG = new DataSegment("DSEG_" + modident, Kind.K_SEG_DATA);
		
		ProgramElement elt = inGlobalElement();
		while(elt != null) {
			programElements.add(elt);
			elt = inGlobalElement();
		}

		programElements.add(new TAGLIST());

		if(Scode.curinstr != Scode.S_BODY) {
			Util.IERR("Illegal termination of module head. Curinstr = " + Scode.edInstr(Scode.curinstr));
		}
//		System.out.println("InterfaceModule.parse: Curinstr = " + Scode.edInstr(Scode.curinstr));
		while(Scode.accept(Scode.S_INIT)) {
			Util.IERR("InterfaceModule: Init values is not supported");
//			int wrd = Scode.inTag();
//			int type = Scode.inType();
//			SkipRepValue;
		}
		Scode.expect(Scode.S_ENDMODULE);
 		
  		if(Global.PRINT_SVM_CODE) printTree(0);
  		
  		try {
			this.write();
			
			if(Global.ATTR_OUTPUT_DUMP) {
				INSERT.insertModule(Global.getAttrFileName(modident, ".AT2"));  // TODO: TESTING
	  			System.out.println("InterfaceModule.parse: END TESTING");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		if(Global.SEGMENT_OUTPUT_DUMP) {
  			Global.DSEG.dump("Final: ");
  			Global.CSEG.dump("Final: ");
  		}
  	}

	private ProgramElement inGlobalElement() {
		Scode.inputInstr();
		switch(Scode.curinstr) {
			case Scode.S_GLOBAL:	    return new PREV_Variable(Scode.S_GLOBAL);
			case Scode.S_CONSTSPEC:		return PREV_CONST.of(false);
			case Scode.S_CONST:			return PREV_CONST.of(true);
			case Scode.S_RECORD:		return new RECORD();
			case Scode.S_PROFILE:		return new PREV_PROFILE(); // InProfile(P_VISIBLE);
//			case Scode.S_ROUTINE:		return new ROUTINE();
			case Scode.S_SETSWITCH:		return new SETSWITCH();
			case Scode.S_INFO:			return new INFO();
//			case Scode.S_DECL:			return new LINE(1);
//			case Scode.S_STMT:			return new LINE(2);
			case Scode.S_LINE:			return new LINE(0);
//			case Scode.S_INSERT:		return new INSERT(false);
//			case Scode.S_SYSINSERT:		return new INSERT(true);
			default: return null;
		}
	}

	@Override
	public void printTree(final int indent) {
		this.sLIST(0, 0, "GLOBAL MODULE");
		System.out.println("ModuleDefinition.printTree: nProgramElements="+programElements.size());
		for(ProgramElement elt:programElements) elt.printTree(indent + 1);
		this.sLIST(1, Scode.curline, "BODY");
		this.sLIST(0, Scode.curline, "ENDMODULE");
		this.sLIST(0, Scode.curline, "ENDPROGRAM");
	}

}
