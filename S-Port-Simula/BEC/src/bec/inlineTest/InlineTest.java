package bec.inlineTest;

import bec.util.Global;
import bec.BecCompiler;

public class InlineTest {
	
	public static void main(String[] argv) {
//		String scodeSource = Pick_RTS_Source();		
		String scodeSource = Pick_FEC_Source();
		
		Global.verbose = true;
		Global.traceMode = 4;
		Global.SCODE_INPUT_TRACE = true;
		Global.PRINT_SVM_CODE = true;
		Global.ATTR_INPUT_TRACE = true;
		Global.ATTR_OUTPUT_TRACE = true;
//		Global.ATTR_INPUT_DUMP = true;
//		Global.ATTR_OUTPUT_DUMP = true;
//		Global.SEGMENT_INPUT_DUMP = true;
//		Global.SEGMENT_OUTPUT_DUMP = true;

		new BecCompiler(scodeSource);
	}

	private static String Pick_RTS_Source() {
//		return "C:/Simuletta/SCode/simulaRTS/RT.scd";
//		return "C:/Simuletta/SCode/simulaRTS/SYSR.scd";
//		return "C:/Simuletta/SCode/simulaRTS/KNWN.scd";
//		return "C:/Simuletta/SCode/simulaRTS/UTIL.scd";		
//		return "C:/Simuletta/SCode/simulaRTS/STRG.scd";	
//		return "C:/Simuletta/SCode/simulaRTS/CENT.scd";
//		return "C:/Simuletta/SCode/simulaRTS/CINT.scd";
//		return "C:/Simuletta/SCode/simulaRTS/ARR.scd";	
//		return "C:/Simuletta/SCode/simulaRTS/FORM.scd";
//		return "C:/Simuletta/SCode/simulaRTS/LIBR.scd";
		return "C:/Simuletta/SCode/simulaRTS/FIL.scd";
		
//		return "C:/Simuletta/SCode/simulaRTS/SMST.scd";
//		return "C:/Simuletta/SCode/simulaRTS/SML.scd";
//		return "C:/Simuletta/SCode/simulaRTS/EDIT.scd";
//		return "C:/Simuletta/SCode/simulaRTS/MNTR.scd";
	}


	private static String Pick_FEC_Source() {
		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst00.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst01.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst02.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst03.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst04.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst05.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst06.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst07.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst08.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst09.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst10.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst11.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst12.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst13.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst14.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst15.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst16.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst17.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst18.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst19.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst20.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst21.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst22.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst23.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst24.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst25.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst26.sim.scd";  // ERROR: SORRY, switch element requiring thunk IS NOT IMPLEMENTED
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst27.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst28.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst29.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst30a.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst30.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst31.sim.scd";  // ERROR: SORRY, complex switch element IS NOT IMPLEMENTED
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst32.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst33.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst34.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst35.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst36.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst37.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst38.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst39.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/p40b.sim.scd";     // Precompile this for Simtst 40.
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/p40a.sim.scd";     // Precompile this for Simtst 40.
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/p40c.sim.scd";     // Precompile this for Simtst 40.
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst40.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/p41.sim.scd";        // Precompile this for Simtst 41.
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst41.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/SimulaTest.sim.scd"; // Precompile this for Simtst 42 ...
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst42.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst43.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst44.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst45.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst46.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst47.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst48.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst49.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst50.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst51.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst52.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst53.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst54.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst55.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst56.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst57.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst58.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst59.sim.scd";  // ERROR: lowerbound, upperbound not implemented
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst60.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst61.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst62.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst63.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst64.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst65.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst66.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst67.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst68.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst69.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst70.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst71.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst72.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst73.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst74.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst75.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst76.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst77.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst78.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst79.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst80.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst81.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst82.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst83.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst84.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst85.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/separat.sim.scd"; // Precompile this for Simtst 86.
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst86.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst87.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst88.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst89.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst90.sim.scd";
//		
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst91.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst92.sim.scd";  // ERROR: lowerbound, upperbound not implemented
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst93.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst94.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst95.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst96.sim.scd";  // ERROR: Wrong WARNING: HIDDEN x ignored, not PROTECTED
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst97.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst98.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst99.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst100.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst101.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst102.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst103.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst104.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst105.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst106.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst107.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst108.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst109.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst110.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst111.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst112.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst113.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst114.sim.scd";  //ERROR: SWITCH STATEMENT NOT IMPLEMENTED
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst115.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst116.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst117.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst118.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/ExternalClass1.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/ExternalClass2.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst119.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst120.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst121.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst122.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst123.sim.scd";  //SORRY, switch element requiring thunk IS NOT IMPLEMENTED
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst124.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst125.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst126.sim.scd";  //SORRY, switch element requiring thunk IS NOT IMPLEMENTED
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst127.sim.scd";  //ERROR: SWITCH STATEMENT NOT IMPLEMENTED
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst128.sim.scd";  //SORRY: The new standard procedures  edit and edfix IS NOT IMPLEMENTD
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/Precompiled129.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst129.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst130.sim.scd";  //SORRY: Class DEC_Lib  IS NOT IMPLEMENTED
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst131.sim.scd";  //SORRY: Class CatchingErrors  IS NOT IMPLEMENTED
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst132.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst133.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst134.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst135.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst136.sim.scd";  //SORRY: Class CatchingErrors  IS NOT IMPLEMENTED
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst137.sim.scd";  //SORRY: Class CatchingErrors  IS NOT IMPLEMENTED
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst138.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst139.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst140.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst141.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst142.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst143.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/Precompiled144.sim.scd"; //ERROR: (l. 77) 151: THIS used in prefix of pref.block
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst144.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst145.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst146.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst147.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst148.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst149.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst150.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst151.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst152.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst153.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst154.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/Pre155.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst155.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst156.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst157.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst158.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst159.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst160.sim.scd";
//
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst161.sim.scd";  //ERROR: Virtual match has wrong type or qualification
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst162.sim.scd";
//		
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst163.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst164.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst165.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst166.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst167.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst168.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst169.sim.scd";
//		return "C:/GitHub/SimulaCompiler2/simulaTestBatch2/src/simulaTestBatch/scode/simtst170.sim.scd";

	}


}
