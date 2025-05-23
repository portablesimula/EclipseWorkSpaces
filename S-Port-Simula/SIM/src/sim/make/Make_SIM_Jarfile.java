package sim.make;

import java.io.File;
import java.util.Vector;
import sim.compiler.SimulaBEC;
import sim.compiler.SimulaFEC;
import sim.compiler.Util;

import static sim.compiler.Global.*;

public class Make_SIM_Jarfile {

	private final static String RELEASE_HOME  = "C:/SPORT";
	private final static String SportSIM_ROOT = "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SIM";
	private final static String COMPILER_BIN  = SportSIM_ROOT+"/bin";

	public static void main(String[] argv) {
		try {
			System.out.println("Make SPORT SIM Compiler.jar in "+RELEASE_HOME);
			File releaseHome=new File(RELEASE_HOME);
			releaseHome.mkdirs();
			String compilerManifest=SportSIM_ROOT+"/src/sim/make/CompilerManifest.MF";
			Util.exec("jar", "cmf", compilerManifest, RELEASE_HOME+"/SIM.jar", "-C", COMPILER_BIN, "./sim");
			Util.exec("jar", "-tvf", RELEASE_HOME+"/SIM.jar");
			
			System.out.println("Make_SIM_Jarfile - DONE: " + RELEASE_HOME + "/CommonSIM.jar");
			
//			SINGLE_INLINE_TEST();
			FULL_INLINE_TEST();
			
		} catch(Exception e) { e.printStackTrace(); }
	}

	@SuppressWarnings("unused")
	private static void SINGLE_INLINE_TEST() {
//			String name ="adHoc00";
//		String name ="adHoc01";
//		String name ="adHoc02";
//		String name ="adHoc03";
		String name ="simtst00";
//		String name ="simtst01";
//		String name ="simtst02";
//		String name ="simtst03";
//		String name ="simtst04";
//		String name ="simtst05";
//		String name ="simtst06";
//		String name ="simtst07";
//		String name ="simtst08";
//		String name ="simtst09";
//		String name ="simtst10";
//		String name ="simtst11";
//		String name ="simtst12";
//		String name ="simtst13";
//		String name ="simtst14";
//		String name ="simtst15";
//		String name ="simtst16";
//			String name ="simtst17";
//			String name ="simtst18";
//		String name ="simtst19";
		
		sourceFileName = "C:\\GitHub\\EclipseWorkSpaces/S-Port-Simula\\SIM\\src\\sim\\testPrograms\\"+name+".sim";
		sCodeFileName  = "C:\\GitHub\\EclipseWorkSpaces/S-Port-Simula\\SIM\\src\\sim\\testPrograms\\scode\\"+name+".scd";
		
////		verbose = true;
//		fecListing = true;
//		fecSCodeTrace = true;
////		fecTraceLevel = 4;
//
//		becTraceSVM_CODE = true;
//		becTraceSVM_DATA = true;
////		becListing = true;
//		becSCodeTrace = true;
//		execTrace = 1;
////		callTrace = 1;//2;
////		dumpsAtExit = true;
		
		doCompile();
	}

	@SuppressWarnings("unused")
	private static void FULL_INLINE_TEST() {
		Vector<String> names = new Vector<String>();
//		names.add("adHoc00");
//		names.add("adHoc01");
//		names.add("adHoc02");
//		names.add("adHoc03");
		
//		names.add("simtst00");
//		names.add("simtst01");
//		names.add("simtst02");
//		names.add("simtst03");
//		names.add("simtst04");
//		names.add("simtst05");
//		names.add("simtst06");
//		names.add("simtst07");
//		names.add("simtst08");
//		names.add("simtst09");
//		names.add("simtst10");
//		names.add("simtst11");
//		names.add("simtst12");
//		names.add("simtst13");
//		names.add("simtst14");
//		names.add("simtst15");
//		names.add("simtst16");
//		names.add("simtst17");
		names.add("simtst18");
//		names.add("simtst19");
		
//		verbose = true;
//		fecListing = true;
//		fecSCodeTrace = true;
//		fecTraceLevel = 4;

//		becListing = true;
//		becSCodeTrace = true;
//		execTrace = 1;
//		callTrace = 1;//2;
//		dumpsAtExit = true;
		
		for(String name:names) {
			sourceFileName = "C:\\GitHub\\EclipseWorkSpaces/S-Port-Simula\\SIM\\src\\sim\\testPrograms\\"+name+".sim";
			sCodeFileName  = "C:\\GitHub\\EclipseWorkSpaces/S-Port-Simula\\SIM\\src\\sim\\testPrograms\\scode\\"+name+".scd";
			doCompile();
		}
	}

	private static void doCompile() {
		int execCode = SimulaFEC.callSimulaFEC();
		verbose = true;
		if(verbose) System.out.println("RETURN FROM FEC: ExitCode = "+execCode+"\n\n");
		if(execCode == 0) SimulaBEC.callBEC();
		
	}
}
