package sim.compiler;

import static sim.compiler.SimGlobal.*;

import java.io.IOException;
import java.util.Vector;

public abstract class SimulaBEC {

	// ****************************************************************
	// *** SimulaEditor: Main Entry for TESTING ONLY
	// ****************************************************************
     public static void main(String[] args) {
    	 Option.verbose = true;
    	 Option.fecListing = true;
//    	 String name = "HelloWorld";
    	 String name = "Sudoku";
    	 sCodeFileName = "src/sim/samplePrograms/scode/"+name+".scd";
    	 callBEC();
     }
		
	/// Called from EditorMenues 'run'
	public static int callBEC() {
		Vector<String> cmds = new Vector<String>();
		cmds.add("java");
		cmds.add("-jar");
		cmds.add("C:\\SPORT\\CommonBEC.jar");
		if(Option.verbose) {
			cmds.add("-verbose");
			cmds.add("-execVerbose");
		}
		if(Option.becSCodeTrace) cmds.add("-inputTrace");
		if(Option.becTraceSVM_CODE) cmds.add("-traceSVM_CODE");
		if(Option.becTraceSVM_DATA) cmds.add("-traceSVM_DATA");
		if(Option.execTrace > 0) cmds.add("-execTrace");
		if(Option.callTrace > 0) cmds.add("-callTrace");
		if(Option.dumpsAtExit) cmds.add("-dumpsAtExit");

		cmds.add(sCodeFileName);

		if(Option.verbose) System.out.println("BEGIN BEC " + sCodeFileName + " ==> .svm");
		try {
			return Util.exec(cmds);
		} catch (IOException e) {
			System.out.println("SimulaBEC.callBEC - Exit: ");
			e.printStackTrace();
			System.exit(0);
		}
		return -6;
	}

}
