package bec.inlineTest;

import bec.util.Global;

import java.util.Vector;

import bec.BecCompiler;

public class MiniInlineTest {
	
	public static void main(String[] argv) {
		Vector<String> names=new Vector<String>();
		
//		names.add("ENVIR0xx");
//		names.add("MODL01xx");
//		names.add("MODL02xx");
//		names.add("TEST6xx");
		
//		names.add("ENVIR_RT");
//		names.add("MODL001");
		
//		names.add("ENVIR0");
//		names.add("MODL01");
////		names.add("MODL02");
//		names.add("TEST6");

//		names.add("TEST6xx");

		names.add("TEST00");

		Global.verbose = true;
//		Global.traceMode = 4;
//		Global.SCODE_INPUT_TRACE = true;
//		Global.PRINT_SVM_CODE = true;
//		Global.ATTR_INPUT_TRACE = true;
//		Global.ATTR_OUTPUT_TRACE = true;
//		Global.ATTR_INPUT_DUMP = true;
//		Global.ATTR_OUTPUT_DUMP = true;
//		Global.SEGMENT_INPUT_DUMP = true;
//		Global.SEGMENT_OUTPUT_DUMP = true;
		Global.EXEC_TRACE = 4;

		for(String name:names) {
//			String fileName = "C:/Simuletta/SCode/simulettaTestPrograms/"+name+".scd";
			String fileName = "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulettaTestPrograms/SCode/"+name+".scd";
//			Global.simulettaTESTLib = "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulettaFEC/src/simulettaTestPrograms";
			new BecCompiler(fileName);
		}
	}

}
