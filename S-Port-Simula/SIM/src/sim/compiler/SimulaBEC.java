package sim.compiler;

import static sim.compiler.Global.*;

import java.io.IOException;
import java.util.Vector;

public abstract class SimulaBEC {
	
	public static int callBEC() {
		Vector<String> cmds = new Vector<String>();
		cmds.add("java");
		cmds.add("-jar");
		cmds.add("C:\\SPORT\\CommonBEC.jar");
		if(becVerbose) cmds.add("-verbose");
		if(becSCodeTrace) cmds.add("-inputTrace");
		if(becTraceSVM_CODE) cmds.add("-traceSVM_CODE");
		if(becTraceSVM_DATA) cmds.add("-traceSVM_DATA");
//		if(becListing) cmds.add("-listing");
		if(execTrace > 0) cmds.add("-execTrace");
		if(callTrace > 0) cmds.add("-callTrace");
		if(dumpsAtExit) cmds.add("-dumpsAtExit");

		cmds.add(sCodeFileName);

		if(verbose) System.out.println("BEGIN BEC " + sCodeFileName + " ==> .svm");
		try {
			return Util.exec(cmds);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		return -6;
	}

}
