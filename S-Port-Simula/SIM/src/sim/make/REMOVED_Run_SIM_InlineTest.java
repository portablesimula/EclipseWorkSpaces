package sim.make;

import static sim.compiler.Global.*;

import sim.compiler.SimulaBEC;
import sim.compiler.SimulaFEC;

public class REMOVED_Run_SIM_InlineTest {
	
	public static void main(String[] argv) {
//		String name ="adHoc00";
//		String name ="adHoc01";
			String name ="adHoc02";
//		String name ="adHoc03";
//		String name ="simtst00";
//		String name ="simtst01";
//		String name ="simtst02";
//		String name ="simtst03";
//		String name ="simtst04";
//		String name ="simtst05";
		
		sourceFileName = "C:\\GitHub\\EclipseWorkSpaces/S-Port-Simula\\SIM\\src\\sim\\testPrograms\\"+name+".sim";
		sCodeFileName  = "C:\\GitHub\\EclipseWorkSpaces/S-Port-Simula\\SIM\\src\\sim\\testPrograms\\scode\\"+name+".scd";
		
//		verbose = true;
		fecListing = true;
		fecSCodeTrace = true;
//		fecTraceLevel = 4;

//		becListing = true;
//		becSCodeTrace = true;
//		execTrace = 1;
//		callTrace = 1;//2;
//		dumpsAtExit = true;
		
		int execCode = SimulaFEC.callSimulaFEC();
		verbose = true;
		if(verbose) System.out.println("RETURN FROM FEC: ExitCode = "+execCode+"\n\n");
		
		if(execCode == 0) SimulaBEC.callBEC();
	}

}
