package bec.inlineTest;

import bec.util.Global;

import java.util.Vector;

import bec.BecCompiler;

public class RunMake_RTS {
	
	public static void main(String[] argv) {
		Vector<String> names=new Vector<String>();
		
		// ============================================
		//  BACKEND COMPILE RTS FROM SCODE TO SVM-CODE
		// ============================================
		
		names.add("RT");	// SCode ==> C:/SPORT/RTS/RT.svm
		names.add("SYSR");	// SCode ==> C:/SPORT/RTS/SYSR.svm
		names.add("KNWN");	// SCode ==> C:/SPORT/RTS/KNWN.svm
		names.add("UTIL");	// SCode ==> C:/SPORT/RTS/UTIL.svm
		names.add("STRG");	// SCode ==> C:/SPORT/RTS/STRG.svm
		names.add("CENT");	// SCode ==> C:/SPORT/RTS/CENT.svm
		names.add("CINT");	// SCode ==> C:/SPORT/RTS/CINT.svm
		names.add("ARR");	// SCode ==> C:/SPORT/RTS/ARR.svm
		names.add("FORM");	// SCode ==> C:/SPORT/RTS/FORM.svm
		names.add("LIBR");	// SCode ==> C:/SPORT/RTS/LIBR.svm
		names.add("FIL");	// SCode ==> C:/SPORT/RTS/FIL.svm
		names.add("SMST");	// SCode ==> C:/SPORT/RTS/SMST.svm
		names.add("SML");	// SCode ==> C:/SPORT/RTS/SML.svm
		names.add("EDIT");	// SCode ==> C:/SPORT/RTS/EDIT.svm
		names.add("MNTR");	// SCode ==> C:/SPORT/RTS/MNTR.svm

		Global.outputDIR = "C:/SPORT/RTS/";
		Global.verbose = true;
//		Global.traceMode = 4;
//		Global.SCODE_INPUT_TRACE = true;
//		Global.PRINT_GENERATED_SVM_CODE = true;
//		Global.PRINT_GENERATED_SVM_DATA = true;
//		Global.ATTR_INPUT_TRACE = true;
//		Global.ATTR_OUTPUT_TRACE = true;
//		Global.ATTR_INPUT_DUMP = true;
//		Global.ATTR_OUTPUT_DUMP = true;
//		Global.SEGMENT_INPUT_DUMP = true;
//		Global.SEGMENT_OUTPUT_DUMP = true;

		for(String name:names) {
			String fileName = "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/"+name+".scd";
			new BecCompiler(fileName);
		}
	}

}
