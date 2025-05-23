package sim.compiler;

import java.io.IOException;
import java.util.Vector;

import static sim.compiler.Global.*;

public abstract class SimulaFEC {
	
	public static int callSimulaFEC() {
		Vector<String> cmds = new Vector<String>();
		cmds.add("java");
		cmds.add("-jar");
		cmds.add("C:\\SPORT\\SimulaFEC.jar");
		cmds.add("-SPORT:noConsole");
		if(verbose) cmds.add("-verbose");
		if(fecTraceLevel > 0) { cmds.add("-SPORT:trace"); cmds.add(""+fecTraceLevel); }
		if(fecListing) cmds.add("-SPORT:listing");
		if(fecSCodeTrace) cmds.add("-SPORT:traceScode");
		// -SPORT:traceScode
		if(selectors != null) {	cmds.add("-SPORT:select"); cmds.add(selectors); }
		cmds.add("-SPORT:SCodeFile"); cmds.add(sCodeFileName);
		cmds.add(sourceFileName);

		if(verbose) System.out.println("BEGIN SIMULA FEC ==> \"" + sCodeFileName + '"');
		try {
			return Util.exec(cmds);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		return 0;
	}

}
