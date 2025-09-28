package make;

import java.io.File;
import bec.util.Util;

public class Make_BEC_Jarfile {
	private final static String RELEASE_HOME  = "C:/SPORT";
	private final static String SportBEC_ROOT = "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/BEC";
	private final static String COMPILER_BIN  = SportBEC_ROOT+"/bin";
	
	// Output   CommonBEC.jar   ===>   RELEASE_HOME

	public static void main(String[] args) {
		try {
			IO.println("Make SPORT BEC Compiler.jar in "+RELEASE_HOME);
			File releaseHome=new File(RELEASE_HOME);
			releaseHome.mkdirs();
			String compilerManifest=SportBEC_ROOT+"/src/make/CompilerManifest.MF";
			
			Util.exec("jar", "cmf", compilerManifest, RELEASE_HOME+"/CommonBEC.jar", "-C", COMPILER_BIN, "./bec");
			Util.exec("jar", "-tvf", RELEASE_HOME+"/CommonBEC.jar");
			
			IO.println("Make_BEC_Jarfile - DONE: " + RELEASE_HOME + "/CommonBEC.jar");
		} catch(Exception e) { e.printStackTrace(); }
	}

}
