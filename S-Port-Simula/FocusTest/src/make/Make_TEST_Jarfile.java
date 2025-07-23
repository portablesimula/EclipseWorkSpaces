package make;

import java.io.File;
import java.util.Vector;

import test.Util;


public class Make_TEST_Jarfile {
	private final static String RELEASE_HOME  = "C:/SPORT";
	private final static String FocusTest_ROOT = "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FocusTest";
	private final static String COMPILER_BIN  = FocusTest_ROOT+"/bin";
	
	// Output   CommonBEC.jar   ===>   RELEASE_HOME

	public static void main(String[] args) {
		try {
			System.out.println("Make SPORT BEC Compiler.jar in "+RELEASE_HOME);
			File releaseHome=new File(RELEASE_HOME);
			releaseHome.mkdirs();
			String compilerManifest=FocusTest_ROOT+"/src/make/CompilerManifest.MF";
			
			Util.exec("jar", "cmf", compilerManifest, RELEASE_HOME+"/FocusTest.jar", "-C", COMPILER_BIN, "./test");
			Util.exec("jar", "-tvf", RELEASE_HOME+"/FocusTest.jar");
			
			System.out.println("Make_TEST_Jarfile - DONE: " + RELEASE_HOME + "/FocusTest.jar");
		} catch(Exception e) { e.printStackTrace(); }
	}
}
