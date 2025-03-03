package PREV;

import java.io.FileOutputStream;
import java.io.IOException;

import PREV.syntaxClass.SyntaxClass;
import PREV.syntaxClass.instruction.RECORD;
import PREV.syntaxClass.programElement.PREV_Variable;
import PREV.syntaxClass.programElement.routine.PREV_PROFILE;
import PREV.syntaxClass.programElement.routine.PREV_ROUTINE;
import PREV.syntaxClass.value.PREV_CONST;
import bec.AttributeOutputStream;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;

public class PREV_S_Module extends SyntaxClass {
	public static String modident;     // Module ident String
	public static String modcheck;     // Module check String
	
	public PREV_S_Module() {
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write() throws IOException {
		if(Global.ATTR_OUTPUT_TRACE)
			System.out.println("**************   Begin  -  Output-module  " + modident + "  " + modcheck + "   **************");
		AttributeOutputStream modoupt = new AttributeOutputStream(new FileOutputStream(Global.getAttrFileName(modident, ".AT2")));
		modoupt.writeKind(Scode.S_MODULE);
		modoupt.writeString(modident);
		modoupt.writeString(modcheck);
		
		// Write Segments
		Global.DSEG.write(modoupt);
		Global.CSEG.write(modoupt);

		for(SyntaxClass elt:Global.Display) {
//			if(elt != null) elt.write(modoupt);
			boolean doWrite = false;
//			if(elt instanceof Segment) doWrite = true;
//			else
				if(elt instanceof RECORD) doWrite = true;
			else if(elt instanceof PREV_PROFILE) doWrite = true;
			else if(elt instanceof PREV_ROUTINE)	doWrite = true;
			else if(elt instanceof PREV_CONST) doWrite = true;
			else if(elt instanceof PREV_Variable var) {
				if(var.instr == Scode.S_GLOBAL) doWrite = true;
				if(var.instr == Scode.S_LOCAL) doWrite = true;
			}
			
			if(doWrite) elt.write(modoupt);
		}

		modoupt.writeKind(Scode.S_ENDMODULE);
		modoupt.close();
		
		if(Global.ATTR_OUTPUT_TRACE)
			System.out.println("**************   End of  -  Output-module  " + modident + "  " + modcheck + "   **************");
		
		if(Global.SEGMENT_OUTPUT_DUMP & Global.ATTR_OUTPUT_DUMP) {
			Global.DSEG.dump("S_Module.write: ");
			Global.CSEG.dump("S_Module.write: ");
		}
//		Util.IERR("");
	}

}
