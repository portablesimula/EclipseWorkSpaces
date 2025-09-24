package bec.inlineTest;

import bec.util.Global;
import bec.BecCompiler;

public class InlineTest {
	
	public static void main(String[] argv) {
		String scodeSource = Pick_RTS_Source();		
//		String scodeSource = Pick_FEC_Source();
		
//		Global.verbose = true;
		Global.traceMode = 4;
		Global.SCODE_INPUT_TRACE = true;
		Global.PRINT_GENERATED_SVM_CODE = true;
//		Global.ATTR_INPUT_TRACE = true;
//		Global.ATTR_OUTPUT_TRACE = true;
//		Global.ATTR_INPUT_DUMP = true;
//		Global.ATTR_OUTPUT_DUMP = true;
//		Global.SEGMENT_INPUT_DUMP = true;
//		Global.SEGMENT_OUTPUT_DUMP = true;

		new BecCompiler(scodeSource);
	}

	private static String Pick_RTS_Source() {
		Global.outputDIR = "C:/SPORT/RTS/";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/RT.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/SYSR.scd";
		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/KNWN.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/UTIL.scd";		
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/STRG.scd";	
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/CENT.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/CINT.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/ARR.scd";	
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/FORM.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/LIBR.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/FIL.scd";
		
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/SMST.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/SML.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/EDIT.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/simulaRTS/SCode/MNTR.scd";
	}


	@SuppressWarnings("unused")
	private static String Pick_FEC_Source() {
		Global.outputDIR = "C:/SPORT/TEST_BATCH/";
		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst01.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst02.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst03.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst04.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst05.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst06.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst07.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst08.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst09.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst10.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst11.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst12.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst13.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst14.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst15.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst16.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst17.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst18.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst19.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst20.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst21.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst22.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst23.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst24.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst25.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst26.scd";  // ERROR: SORRY, switch element requiring thunk IS NOT IMPLEMENTED
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst27.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst28.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst29.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst30a.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst30.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst31.scd";  // ERROR: SORRY, complex switch element IS NOT IMPLEMENTED
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst32.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst33.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst34.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst35.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst36.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst37.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst38.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst39.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/p40b.scd";     // Precompile this for Simtst 40.
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/p40a.scd";     // Precompile this for Simtst 40.
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/p40c.scd";     // Precompile this for Simtst 40.
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst40.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/p41.scd";        // Precompile this for Simtst 41.
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst41.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/SimulaTest.scd"; // Precompile this for Simtst 42 ...
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst42.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst43.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst44.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst45.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst46.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst47.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst48.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst49.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst50.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst51.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst52.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst53.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst54.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst55.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst56.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst57.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst58.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst59.scd";  // ERROR: lowerbound, upperbound not implemented
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst60.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst61.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst62.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst63.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst64.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst65.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst66.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst67.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst68.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst69.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst70.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst71.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst72.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst73.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst74.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst75.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst76.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst77.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst78.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst79.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst80.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst81.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst82.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst83.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst84.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst85.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/separat.scd"; // Precompile this for Simtst 86.
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst86.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst87.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst88.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst89.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst90.scd";
//		
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst91.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst92.scd";  // ERROR: lowerbound, upperbound not implemented
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst93.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst94.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst95.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst96.scd";  // ERROR: Wrong WARNING: HIDDEN x ignored, not PROTECTED
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst97.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst98.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst99.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst100.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst101.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst102.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst103.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst104.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst105.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst106.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst107.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst108.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst109.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst110.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst111.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst112.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst113.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst114.scd";  //ERROR: SWITCH STATEMENT NOT IMPLEMENTED
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst115.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst116.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst117.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst118.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/ExternalClass1.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/ExternalClass2.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst119.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst120.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst121.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst122.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst123.scd";  //SORRY, switch element requiring thunk IS NOT IMPLEMENTED
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst124.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst125.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst126.scd";  //SORRY, switch element requiring thunk IS NOT IMPLEMENTED
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst127.scd";  //ERROR: SWITCH STATEMENT NOT IMPLEMENTED
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst128.scd";  //SORRY: The new standard procedures  edit and edfix IS NOT IMPLEMENTD
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/Precompiled129.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst129.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst130.scd";  //SORRY: Class DEC_Lib  IS NOT IMPLEMENTED
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst131.scd";  //SORRY: Class CatchingErrors  IS NOT IMPLEMENTED
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst132.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst133.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst134.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst135.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst136.scd";  //SORRY: Class CatchingErrors  IS NOT IMPLEMENTED
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst137.scd";  //SORRY: Class CatchingErrors  IS NOT IMPLEMENTED
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst138.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst139.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst140.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst141.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst142.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst143.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/Precompiled144.scd"; //ERROR: (l. 77) 151: THIS used in prefix of pref.block
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst144.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst145.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst146.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst147.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst148.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst149.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst150.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst151.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst152.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst153.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst154.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/Pre155.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst155.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst156.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst157.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst158.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst159.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst160.scd";
//
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst161.scd";  //ERROR: Virtual match has wrong type or qualification
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst162.scd";
//		
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst163.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst164.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst165.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst166.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst167.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst168.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst169.scd";
//		return "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/simtst170.scd";

	}


}
