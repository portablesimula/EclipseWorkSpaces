package sim.compiler;

import java.io.IOException;
import java.util.Vector;

import static sim.compiler.Global.*;

public abstract class SimulaFEC {

	// ****************************************************************
	// *** SimulaEditor: Main Entry for TESTING ONLY
	// ****************************************************************
     public static void main(String[] args) {
    	 Option.verbose = true;
    	 Option.fecListing = true;
//    	 String name = "HelloWorld";
    	 String name = "Sudoku";
    	 sCodeFileName = "src/sim/samplePrograms/scode/"+name+".scd";
    	 sourceFileName = "src/sim/samplePrograms/"+name+".sim";
    	 invokeSimulaFEC();
     }

	public static int invokeSimulaFEC() {
		Vector<String> cmds = new Vector<String>();
		cmds.add("-nopopup");
		if(Option.verbose) cmds.add("-verbose");
		if(Option.fecTraceLevel > 0) { cmds.add("-SPORT:trace"); cmds.add(""+Option.fecTraceLevel); }
		if(Option.fecListing) cmds.add("-SPORT:listing");
		if(Option.fecSCodeTrace) cmds.add("-SPORT:traceScode");
		if(selectors != null) {	cmds.add("-SPORT:select"); cmds.add(selectors); }
		cmds.add("-SPORT:SCodeFile"); cmds.add(sCodeFileName);
		cmds.add(sourceFileName);

		if(Option.verbose) System.out.println("BEGIN SIMULA FEC ==> \"" + sCodeFileName + '"');
		JarFileLoader loader = new JarFileLoader("C:/SPORT/SimulaFEC.jar");
		String[] args = cmds.toArray(new String[0]);
		loader.invokeMain("simprog.FEC", args); // Invoke simprog.FEC.main(args)
		return 0;
	}
	
	public static int callSimulaFEC() {
		Vector<String> cmds = new Vector<String>();
		cmds.add("java");
		cmds.add("-jar");
		cmds.add("C:\\SPORT\\SimulaFEC.jar");
//		cmds.add("-SPORT:noConsole");
		cmds.add("-nopopup");
		if(Option.verbose) cmds.add("-verbose");
		if(Option.fecTraceLevel > 0) { cmds.add("-SPORT:trace"); cmds.add(""+Option.fecTraceLevel); }
		if(Option.fecListing) cmds.add("-SPORT:listing");
		if(Option.fecSCodeTrace) cmds.add("-SPORT:traceScode");
		// -SPORT:traceScode
		if(selectors != null) {	cmds.add("-SPORT:select"); cmds.add(selectors); }
		cmds.add("-SPORT:SCodeFile"); cmds.add(sCodeFileName);
		cmds.add(sourceFileName);

		if(Option.verbose) System.out.println("BEGIN SIMULA FEC ==> \"" + sCodeFileName + '"');
		try {
			return Util.exec(cmds);
		} catch (IOException e) {
			System.out.println("SimulaFEC.callSimulaFEC - Exit: ");
			e.printStackTrace();
			System.exit(0);
		}
		return 0;
	}

}
