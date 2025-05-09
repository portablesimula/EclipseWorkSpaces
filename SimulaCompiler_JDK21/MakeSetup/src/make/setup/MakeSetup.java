/*
 * (CC) This work is licensed under a Creative Commons
 * Attribution 4.0 International License.
 *
 * You find a copy of the License on the following
 * page: https://creativecommons.org/licenses/by/4.0/
 */
package make.setup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static java.nio.file.StandardCopyOption.*;

//import simula.compiler.utilities.Global;
//import simula.compiler.utilities.Util;

/**
 * Updates are written to:  C://GitHub/github.io/setup
 * 
 * NOTE: Remember to Update Global.simulaReleaseID
 * 
 * @author Øystein Myhre Andersen
 *
 */
public final class MakeSetup {
	public static final String RELEASE_ID = "Simula-2.0";
	private final static int REVISION = 21;
	
	private final static boolean EARLY_ACCESS = true;   // Used to produce a Release
//	private final static boolean EARLY_ACCESS = false;  // Used to produce a Early Access
	
	private final static String SETUP_TEMPS="C:\\GitHub\\MakeSetup_Temps";
	private final static String RELEASE_HOME=SETUP_TEMPS+"\\"+RELEASE_ID;
	private final static String RELEASE_SAMPLES=RELEASE_HOME+"\\samples";
	private final static String GITHUB_ROOT="C:\\GitHub";
	private final static String SETUP_ROOT="C:\\GitHub\\EclipseWorkSpaces\\SimulaCompiler_JDK21\\MakeSetup";
	private final static String SIMULA_ROOT="C:\\GitHub\\EclipseWorkSpaces\\SimulaCompiler_JDK21\\Simula";
	private final static String TESTBATCH_ROOT="C:\\GitHub\\EclipseWorkSpaces\\SimulaCompiler_JDK21\\SimulaTestBatch";
//	private final static String COMPILER_BIN=SIMULA_ROOT+"\\bin17";
	private final static String COMPILER_BIN=SIMULA_ROOT+"\\bin";
	private final static String INSTALLER_BIN=SETUP_ROOT+"\\bin";

	private static String SETUP_IDENT;
	private static void setSetupIdent() {
//		if(EARLY_ACCESS)
			 SETUP_IDENT="SimulaSetup-R21";  // Used to produce a TestSetup
//		else SETUP_IDENT="SimulaSetup";    // Used to produce a Release
	}

	public static void main(String[] args) {
        //System.setProperty("file.encoding","UTF-8");
		printHeading("Make Simula Compiler, SETUP_TEMPS="+SETUP_TEMPS);
		try {
			setSetupIdent();
			deleteFiles(SETUP_TEMPS);
//			new File(SETUP_TEMPS).mkdirs();

//			printSystemProperties();
			updateSetupProperties();

			makeSimulaCompiler_JDK21();
			makeRuntimeSystemJar();
			copySimulaRuntimeSystem();
			copySimulaIconFiles();
//			dummyExecuteSimulaCompiler_JDK21();
//			copySimulaReleaseTestBats();
//			compileAndExecuteSimulaPrograms();
			copySimulaReleaseTests();
			makeSimulaInstaller();
//			executeSimulaCompiler_JDK21Editor();
			executeSimulaSetup();
			} catch(Exception e) { e.printStackTrace(); }
	}
	
	// ***************************************************************
	// *** MAKE SIMULA COMPILER JAR
	// ***************************************************************
	private static void makeSimulaCompiler_JDK21() throws IOException	{
		printHeading("Make Simula Compiler.jar in "+RELEASE_HOME);
//		deleteFiles(RELEASE_HOME);
//		list(RELEASE_HOME);
		File releaseHome=new File(RELEASE_HOME);
		releaseHome.mkdirs();
//		String compilerManifest=SIMULA_ROOT+"\\src\\make\\setup\\CompilerManifest.MF";
		String compilerManifest=SETUP_ROOT+"\\src\\make\\setup\\CompilerManifest.MF";
//		execute("jar cmf "+compilerManifest+" "+RELEASE_HOME+"\\simula.jar -C "+COMPILER_BIN+" ./simula/common -C "+COMPILER_BIN+" ./simula/compiler -C "+COMPILER_BIN+" ./org/objectweb/asm");
		execute("jar","cmf",compilerManifest,RELEASE_HOME+"\\simula.jar",
//				"-C",COMPILER_BIN,"./simula/common",
				"-C", COMPILER_BIN, "./simula/editor",
				"-C", COMPILER_BIN, "./simula/compiler",
				"-C", COMPILER_BIN, "./org/objectweb/asm");
//		execute("jar -tvf "+RELEASE_HOME+"\\simula.jar");
		execute("jar", "-tvf", RELEASE_HOME+"\\simula.jar");
	}
	
	// ***************************************************************
	// *** MAKE SIMULA RUNTIME SYSTEM JAR
	// ***************************************************************
	private static void makeRuntimeSystemJar() throws IOException {
		
		// jar --create --file classes.jar Foo.class Bar.class
		execute("jar", "--create", "--file", RELEASE_HOME+"\\RTS.jar", "-C", COMPILER_BIN, "./simula/runtime");
		// jar -tf foo.jar
		execute("jar", "-tf", RELEASE_HOME+"\\RTS.jar");
	}
		
	// ***************************************************************
	// *** COPY SIMULA RUNTIME SYSTEM
	// ***************************************************************
	private static void copySimulaRuntimeSystem() throws IOException {
		File source=new File(COMPILER_BIN+"\\simula\\runtime");
		String target=RELEASE_HOME+"\\rts\\simula\\runtime";
//		String target=RELEASE_HOME+"\\rts";
		printHeading("Copy Simula RuntimeSystem "+source+" ===> "+target);
        System.out.println("MakeCompiler.copySimulaRuntimeSystem: target="+target);
		copyFolder(source,new File(target),true);
		list(source);
	}
	
	// ***************************************************************
	// *** COPY SIMULA ICON FILES
	// ***************************************************************
	private static void copySimulaIconFiles() throws IOException	{
		printHeading("Copy Simula Icons .png's into "+RELEASE_HOME);
		copyImageFile("sim.ico");
		copyImageFile("sim.png");
		copyImageFile("sim2.png");
		copyImageFile("simula.png");
	}
	private static void copyImageFile(String fileName) throws IOException	{
		File source=new File(SIMULA_ROOT+"\\src\\icons\\"+fileName);
		File target=new File(RELEASE_HOME+"\\icons\\"+fileName);
		target.mkdirs();
		System.out.println("source="+source);
		System.out.println("target="+target);
		Files.copy(source.toPath(), target.toPath(), REPLACE_EXISTING);
	}
	
//	// ***************************************************************
//	// *** COPY SIMULA RELEASE TEST BATCH FILES
//	// ***************************************************************
//	private static void copySimulaReleaseTestBats() throws IOException	{
//		printHeading("Copy Simula Release Info into "+RELEASE_HOME);
//		copyTxtFile("ReleaseNotes.txt");
//	}
//	private static void copyTxtFile(String batName) throws IOException	{
//		File source=new File(SIMULA_ROOT+"\\src\\make\\setup\\"+batName);
//		File target=new File(RELEASE_HOME+"\\"+batName);
//		target.mkdirs();
//		System.out.println("source="+source);
//		System.out.println("target="+target);
//		Files.copy(source.toPath(), target.toPath(), REPLACE_EXISTING);
//	}
	
	// ***************************************************************
	// *** COPY SIMULA RELEASE TEST PROGRAMS
	// ***************************************************************
	private static void copySimulaReleaseTests() throws IOException	{
		printHeading("Copy Simula TestPrograms into "+RELEASE_SAMPLES);
		deleteFiles(RELEASE_SAMPLES);
		copyFolder(new File(TESTBATCH_ROOT+"\\src\\simulaSamples"), new File(RELEASE_SAMPLES), false);
		list(new File(TESTBATCH_ROOT+"\\src\\simulaTestPrograms\\samples"));
		list(new File(RELEASE_SAMPLES));
		copyFolder(new File(TESTBATCH_ROOT+"\\src\\simulaSamples\\data"), new File(RELEASE_SAMPLES+"\\data"), false);
	}
	
	// ***************************************************************
	// *** DELETE FILES
	// ***************************************************************
	private static void deleteFiles(final String dirName) {
		try { File tmpClass = new File(dirName);
			  File[] elt = tmpClass.listFiles();
			  if(elt==null) return; 
			  for (File f : elt) {
				  if(f.isDirectory()) deleteFiles(f.getPath());
				  f.delete();
			  }
		} catch (Exception e) { e.printStackTrace(); }
	}

	// ***************************************************************
	// *** COPY FOLDER
	// ***************************************************************
	static private void copyFolder(File source, File target,boolean copySubFolders) throws IOException {
		System.out.println("COPY: "+source+" ==> "+target);
		target.mkdirs();
	    for(File file: source.listFiles()) {
	        File fileDest = new File(target, file.getName());
	        //System.out.println(fileDest.getAbsolutePath());
	        if(file.isDirectory()) {
	            if(copySubFolders) copyFolder(file, fileDest, copySubFolders);
	        } else {
	            //if(fileDest.exists()) continue;
	        	Files.copy(file.toPath(), fileDest.toPath());
	        }
	    }
	}	
	// ***************************************************************
	// *** LIST FILES
	// ***************************************************************
	private static void list(final String dirName) { list(new File(dirName)); }
	private static void list(final File dir) {
		try { Util.println("------------  LIST "+dir+"  ------------");
			  list("",dir);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	private static void list(String indent,final File dir) {
		try {
			//Util.println("tmpClass: "+dir);
			File[] elt = dir.listFiles();
			if(elt==null || elt.length==0) {
				Util.println("Empty Directory: "+dir);
				return; 
			}
			Util.println("Elements: "+elt.length);
			for (File f : elt) {
				Util.println(indent+"- "+getModifiedTime(f)+"  "+f);
				if(f.isDirectory()) list(indent+"   ",f);
			}
		} catch (Exception e) { e.printStackTrace(); }
	}

	private static String getModifiedTime(File file) {
		try { Path path = Paths.get(file.toString());
			  BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
			  return(attr.lastModifiedTime().toString().substring(0,19).replace('T',' '));
		} catch (IOException e) { e.printStackTrace(); }
		return(null);
	}
	
//	// ***************************************************************
//	// *** DUMMY EXECUTE SIMULA COMPILER JAR
//	// ***************************************************************
//	private static void dummyExecuteSimulaCompiler_JDK21() throws IOException	{
//		printHeading("Dummy Execute SimulaCompiler_JDK21: "+RELEASE_HOME+"\\simula.jar");
//		execute("java -jar "+RELEASE_HOME+"\\simula.jar");
//	}
	
//	// ***************************************************************
//	// *** EXECUTE SIMULA COMPILER EDITOR
//	// ***************************************************************
//	private static void executeSimulaCompiler_JDK21Editor() throws IOException	{
//		printHeading("Execute SimulaCompiler_JDK21Editor: "+RELEASE_HOME+"\\simula.jar");
//		execute("java -jar "+RELEASE_HOME+"\\simula.jar");
//	}
	
	// ***************************************************************
	// *** COMPILE AND EXECUTE SIMULA PROGRAMS
	// ***************************************************************
//	private static void compileAndExecuteSimulaPrograms() throws IOException {
//		printHeading("Compile and Execute TestPrograms from "+RELEASE_SAMPLES);
//		compileAndExecuteSimulaProgram(RELEASE_SAMPLES+"\\HelloWord.sim");
//		compileAndExecuteSimulaProgram(RELEASE_SAMPLES+"\\JensensDevice.sim");
//		compileAndExecuteSimulaProgram(RELEASE_SAMPLES+"\\FittingRoom.sim");
//		compileAndExecuteSimulaProgram(RELEASE_SAMPLES+"\\Docking.sim");
//	}
//	private static void compileAndExecuteSimulaProgram(String source) throws IOException {
//		execute("java -jar "+RELEASE_HOME+"\\simula.jar "+source);
//	}
	
	// ***************************************************************
	// *** MAKE SIMULA INSTALLER JAR
	// ***************************************************************
	private static void makeSimulaInstaller() throws IOException	{
		printHeading("Make Simula Setup.jar in "+SETUP_TEMPS);
		File releaseHome=new File(RELEASE_HOME);
		releaseHome.mkdirs();
//		String SETUP_SRC=SIMULA_ROOT+"\\src\\make\\setup";
		String SETUP_SRC=SETUP_ROOT+"\\src\\make\\setup";
		// CopySimulaIcon  --> INSTALLER_BIN;
		File source=new File(SETUP_SRC+"\\sim.png");
		File target=new File(INSTALLER_BIN+"\\make\\setup\\sim.png");
		target.mkdirs();
		System.out.println("source="+source);
		System.out.println("target="+target);
		Files.copy(source.toPath(), target.toPath(), REPLACE_EXISTING);
			
		String installerManifest=SETUP_SRC+"\\InstallerManifest.MF";
		
		String files=" -C "+RELEASE_HOME+"."  // Complete Simula Release
				    +" -C "+INSTALLER_BIN+" ./make/setup";
		System.out.println("jar cmf "+installerManifest+" "+SETUP_TEMPS+"\\"+SETUP_IDENT+files);
		
		execute("jar", "cmf", installerManifest, SETUP_TEMPS+"\\"+SETUP_IDENT,
				"-C",RELEASE_HOME, ".",  // Complete Simula Release
			    "-C",INSTALLER_BIN, "./make/setup");
		printHeading("BEGIN -- List Simula Setup.jar in "+SETUP_TEMPS);
		execute("jar","-tvf",SETUP_TEMPS+"\\"+SETUP_IDENT);
		printHeading("END -- List Simula Setup.jar in "+SETUP_TEMPS);
		copySetupJAR();
	}
	
	// ***************************************************************
	// *** COPY SIMULA INSTALLER JAR
	// ***************************************************************
	private static void copySetupJAR() throws IOException	{
		File source=new File(SETUP_TEMPS+"\\"+SETUP_IDENT);
		File target2=new File(GITHUB_ROOT+"\\github.io\\setup\\"+SETUP_IDENT+".jar");
		System.out.println("source="+source);
		System.out.println("target2="+target2);
		Files.copy(source.toPath(), target2.toPath(), REPLACE_EXISTING);
		if(! EARLY_ACCESS) {
			String SETUP_IDENT_WITH_REVISION=SETUP_IDENT+"-R"+REVISION+".jar"; // E.g: simula-setup-r28.jar
			File target1=new File(GITHUB_ROOT+"\\github.io\\setup\\"+SETUP_IDENT_WITH_REVISION);
			System.out.println("target1="+target1);
			Files.copy(source.toPath(), target1.toPath(), REPLACE_EXISTING);

		}
		//updateSetupProperties();
	}
	
	// ***************************************************************
	// *** EXECUTE SIMULA SETUP
	// ***************************************************************
	private static void executeSimulaSetup() throws IOException	{
		String SETUP_JAR=GITHUB_ROOT+"\\github.io\\setup\\"+SETUP_IDENT+".jar";
		printHeading("Execute SimulaSetup: "+SETUP_JAR);
//		execute("java -jar "+SETUP_JAR);
		execute("java","-jar",SETUP_JAR);
	}
	
	// ***************************************************************
	// *** EXECUTE OS COMMAND
	// ***************************************************************
	private static int execute(String... cmd) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		String line="";
		for(int i=0;i<cmd.length;i++) line=line+" "+cmd[i];
        System.out.println("MakeCompiler.execute: command="+line);
//	    String cmd=command.trim()+'\n';
		Process process = runtime.exec(cmd);
		//try
		{ InputStream err=process.getErrorStream();
		  InputStream inp=process.getInputStream();
		  while(process.isAlive())
		  { while(err.available()>0) System.err.append((char)err.read());
		    while(inp.available()>0) System.out.append((char)inp.read());
			
		  }
		  // process.waitFor();
		} //catch(InterruptedException e) { e.printStackTrace(); }
		return(process.exitValue());
	}

	private static void printHeading(String heading) {
		System.out.println("************************************************************************************************************************************");
		System.out.println("*** "+heading);
		System.out.println("************************************************************************************************************************************");
	}
	
	
	// ***************************************************************
	// *** UPDATE SETUP PROPERTIES
	// ***************************************************************
	@SuppressWarnings("unused")
	private static void updateSetupProperties() {
		String setupDated=""+new Date();
		setProperty("simula.setup.dated",setupDated);
		setProperty("simula.version",""+RELEASE_ID);
		setProperty("simula.revision",""+REVISION);
		try { // also update 'Simula-Revision' and 'Simula-Setup-Dated' in InstallerManifest.MF
//		   String SETUP_SRC=SIMULA_ROOT+"\\src\\make\\setup";
		   String SETUP_SRC=SETUP_ROOT+"\\src\\make\\setup";
		   File installerManifestFile=new File(SETUP_SRC+"\\InstallerManifest.MF");
		   System.out.println("installerManifestFile: "+installerManifestFile);
		   Manifest manifest=new Manifest();
		   InputStream inputStream=new FileInputStream(installerManifestFile);
		   manifest.read(inputStream);
		   Attributes main=manifest.getMainAttributes();
		   System.out.println("Main-Class: "+main.getValue("Main-Class"));
		   System.out.println("Simula-Revision: "+main.getValue("Simula-Revision"));
		   main.putValue("Simula-Revision",""+REVISION);
		   main.putValue("Simula-Setup-Dated",""+setupDated);
		   System.out.println("Simula-Revision: "+main.getValue("Simula-Revision"));
		   System.out.println("Simula-Setup-Dated: "+main.getValue("Simula-Setup-Dated"));
		   OutputStream outputStream=new FileOutputStream(installerManifestFile);
		   manifest.write(outputStream);
		} catch(Exception e) { e.printStackTrace(); }
	}
    private static File setupPropertiesFile;
    private static Properties setupProperties;
	public static String getProperty(String key,String defaultValue) {
		if(setupPropertiesFile==null) loadProperties();
		return(setupProperties.getProperty(key,defaultValue));
	}
	
	public static void setProperty(String key,String value) {
		if(setupPropertiesFile==null) loadProperties();
		setupProperties.setProperty(key,value);
		storeProperties();
	}
	
	private static void loadProperties() {
		String USER_HOME=System.getProperty("user.home");
		System.out.println("USER_HOME="+USER_HOME);
//		File setupPropertiesDir=new File(USER_HOME+File.separatorChar+".simula");
		File setupPropertiesDir=new File(GITHUB_ROOT+"\\github.io\\setup");
		System.out.println("setupPropertiesDir="+setupPropertiesDir);
		setupPropertiesDir.mkdirs();
		setupPropertiesFile=new File(setupPropertiesDir,"setupProperties.xml");
		setupProperties = new Properties();
		try { setupProperties.loadFromXML(new FileInputStream(setupPropertiesFile));
		} catch(Exception e) {} // e.printStackTrace(); }
	}
	
	private static void storeProperties() {
		if(! EARLY_ACCESS) {
			setupProperties.list(System.out);
			try { setupProperties.storeToXML(new FileOutputStream(setupPropertiesFile),"Setup Properties");
			} catch(Exception e) { e.printStackTrace(); }
		}
	}
	
//	private static void printSystemProperties() {
//		printHeading("Jave System Properties");
//		Properties prop = System.getProperties();
//		Set<Object> keySet = prop.keySet();
//		for (Object obj : keySet) {
//			System.out.println("System Property: {" 
//					+ obj.toString() + " = " 
//					+ System.getProperty(obj.toString()) + "}");
//		}
//	}

}
