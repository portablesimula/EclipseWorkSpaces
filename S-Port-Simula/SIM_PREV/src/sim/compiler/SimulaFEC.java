package sim.compiler;

import java.io.IOException;
import java.util.Vector;

import static sim.compiler.SimGlobal.*;

public abstract class SimulaFEC {
	private static boolean fecTerminated;
	private static int exitCode;

	// ****************************************************************
	// *** SimulaEditor: Main Entry for TESTING ONLY
	// ****************************************************************
	public static void main(String[] args) {
		Option.verbose = true;
		Option.fecListing = true;
//		String name = "HelloWorld";
		String name = "Sudoku";
		sCodeFileName = "src/sim/samplePrograms/scode/"+name+".scd";
		sourceFileName = "src/sim/samplePrograms/"+name+".sim";
		callSimulaFEC();
	}
	
	/// Called from EditorMenues 'run'
	public static int callSimulaFEC() {
		Vector<String> cmds = new Vector<String>();
		cmds.add("java");
		cmds.add("-jar");
		cmds.add("C:\\SPORT\\SimulaFEC.jar");
		cmds.add("-nopopup");
		if(Option.verbose) cmds.add("-verbose");
		if(Option.fecTraceLevel > 0) { cmds.add("-SPORT:trace"); cmds.add(""+Option.fecTraceLevel); }
		if(Option.fecListing) cmds.add("-SPORT:listing");
		if(Option.fecSCodeTrace) cmds.add("-SPORT:traceScode");
		if(selectors != null) {	cmds.add("-SPORT:select"); cmds.add(selectors); }
		cmds.add("-SPORT:SCodeFile"); cmds.add(sCodeFileName);
		cmds.add(sourceFileName);

		if(Option.verbose) System.out.println("BEGIN SIMULA FEC ==> \"" + sCodeFileName + '"');
		Runnable task = new Runnable() {
			public void run() {
				try {
					exitCode = Util.exec(cmds);
					fecTerminated = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		fecTerminated = false;
		new Thread(task).start();
		while(! fecTerminated) Thread.yield();
		System.out.println("SimulaFEC.callSimulaFEC: exitCode=" + exitCode);
		return exitCode;
	}

}
