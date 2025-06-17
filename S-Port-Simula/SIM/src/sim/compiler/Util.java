package sim.compiler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class Util {
	
	// ***************************************************************
	// *** EXECUTE OS COMMAND
	// ***************************************************************
	public static int exec(final Vector<String> cmd) throws IOException {
		String[] cmds = new String[cmd.size()];
		cmd.copyInto(cmds);
		return (exec(cmds));
	}

	public static int exec(String... cmd) throws IOException {
		String line="";
		for(int i=0;i<cmd.length;i++) line=line+" "+cmd[i];
        if(Global.verbose) System.out.println("MakeSIM.execute: command="+line);
		ProcessBuilder processBuilder = new ProcessBuilder(cmd);
		processBuilder.redirectErrorStream(true);
		try {
			Process process = processBuilder.start();		
			InputStream output = process.getInputStream();  // Process' output
			while (process.isAlive()) {
				while (output.available() > 0)
					System.out.append((char) output.read());
//				System.out.println("ALIVE: "+process.isAlive());
			}
			if(Global.verbose) System.out.println("RETURN: "+process.exitValue());
//			Thread.dumpStack();
			return (process.exitValue());

		} catch(Exception e) {
			System.out.println("ERROR: "+e);
			throw new RuntimeException("Process Execution failed: " + line, e);
		}
	}

	
	// ***************************************************************
	// *** LIST .jar file
	// ***************************************************************
	/**
	 * List .jar file
	 * @param file the .jar file
	 */
	public static void listJarFile(final File file) {
		System.out.println("---------  LIST .jar File: " + file + "  ---------");
		if (!(file.exists() && file.canRead())) {
			System.out.println("ERROR: Can't read .jar file: " + file);
			return;
		}
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(file);
			Manifest manifest = jarFile.getManifest();
			Attributes mainAttributes = manifest.getMainAttributes();
			Set<Object> keys = mainAttributes.keySet();
			for (Object key : keys) {
				String val = mainAttributes.getValue(key.toString());
				System.out.println(key.toString() + "=\"" + val + "\"");
			}

			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String size = "" + entry.getSize();
				while (size.length() < 6)
					size = " " + size;
				FileTime fileTime = entry.getLastModifiedTime();
				String date = DateTimeFormatter.ofPattern("uuuu-MMM-dd HH:mm:ss", Locale.getDefault())
						.withZone(ZoneId.systemDefault()).format(fileTime.toInstant());
				System.out.println("Jar-Entry: " + size + "  " + date + "  \"" + entry + "\"");
			}
		} catch (IOException e) {
			System.out.println("IERR: Caused by: " + e);
		} finally {
			if (jarFile != null)
				try {
					jarFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}


}
