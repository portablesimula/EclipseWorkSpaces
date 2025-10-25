/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Util {

	public static void setLine(Type type) {
		Scode.curline = Scode.inNumber();
	}

	public static void ITRC(String id, String msg) {
		if(Option.SCODE_INPUT_TRACE) {
			Scode.initTraceBuff("Line " + Scode.curline + "  " + id + ": " + msg);
		}
	}
	
	public static void println(String s) {
		if(Global.console != null)
			Global.console.write(s + '\n');
		IO.println(s);
	}

	public static void WARNING(String msg) {
		if(Option.SCODE_INPUT_TRACE) {
			Scode.flushTraceBuff();
		}
		IO.println("ERROR: " + msg);
	}

	public static void ERROR(String msg) {
		if(Option.SCODE_INPUT_TRACE) {
			Scode.flushTraceBuff();
		}
		IO.println("ERROR: " + msg);
	}

	public static void IERR(String msg) {
		ERROR("Internal error: " + msg);
		Thread.dumpStack();
		System.exit(0);
	}


	/**
	 * Utility method: TRACE_OUTPUT
	 * @param msg the message to print
	 */
	public static void TRACE_OUTPUT(final String msg) {
		if (Option.ATTR_OUTPUT_TRACE)
			IO.println("ATTR OUTPUT: " + msg);
	}

	/**
	 * Utility method: TRACE_INPUT
	 * @param msg the message to print
	 */
	public static void TRACE_INPUT(final String msg) {
		if (Option.ATTR_INPUT_TRACE)
			IO.println("ATTR INPUT: " + msg);
	}


	
	// ***************************************************************
	// *** EXTERNAL CONSOLE BINDING
	// ***************************************************************

	public static InputStream getConsoleInputStream() {
		try {
			Class<?> console = getClass("simula.compiler.utilities.ConsolePanel", "sim.Console");
			IO.println("Util.getConsoleReader: console="+console);
			Field field_current = console.getDeclaredField("current");
			IO.println("Util.getConsoleReader: field_current="+field_current);
			Method getInputStream = console.getDeclaredMethod("getInputStream");
			IO.println("Util.getConsoleReader: getInputStream="+getInputStream);
			Object object = field_current.get(console);
			return (InputStream) getInputStream.invoke(object);
		} catch(Exception e) { e.printStackTrace(); return null; }
	}

	public static OutputStream getConsoleOutputStream() {
		try {
			Class<?> console = getClass("simula.compiler.utilities.ConsolePanel", "sim.Console");
			IO.println("Util.getConsoleWriter: console="+console);
			Field field_current = console.getDeclaredField("current");
			IO.println("Util.getConsoleWriter: field_current="+field_current);
			Method getOutputStream = console.getDeclaredMethod("getOutputStream");
			IO.println("Util.getConsoleWriter: getOutputStream="+getOutputStream);
			Object object = field_current.get(console);
			return (OutputStream) getOutputStream.invoke(object);
		} catch(Exception e) { e.printStackTrace(); return null; }
	}

	public static Reader getConsoleReader() {
		try {
			Class<?> console = getClass("simula.compiler.utilities.ConsolePanel", "sim.Console");
			IO.println("Util.getConsoleReader: console="+console);
			Field field_current = console.getDeclaredField("current");
			IO.println("Util.getConsoleReader: field_current="+field_current);
			Method getReader = console.getDeclaredMethod("getReader");
			IO.println("Util.getConsoleReader: getReader="+getReader);
			Object object = field_current.get(console);
			return (Reader) getReader.invoke(object);
		} catch(Exception e) { e.printStackTrace(); return null; }
	}

	public static Writer getConsoleWriter() {
		try {
			Class<?> console = getClass("simula.compiler.utilities.ConsolePanel", "sim.Console");
			IO.println("Util.getConsoleWriter: console="+console);
			Field field_current = console.getDeclaredField("current");
			IO.println("Util.getConsoleWriter: field_current="+field_current);
			Method getWriter = console.getDeclaredMethod("getWriter");
			IO.println("Util.getConsoleWriter: getWriter="+getWriter);
			Object object = field_current.get(console);
			return (Writer) getWriter.invoke(object);
		} catch(Exception e) { e.printStackTrace(); return null; }
	}

	private static Class<?> getClass(String... names) {
		for(String name:names)
			try { return Class.forName(name); } catch (ClassNotFoundException e) {}
		return null;
	}



	
	// ***************************************************************
	// *** EXECUTE OS COMMAND
	// ***************************************************************
	public static int exec(String... cmd) throws IOException {
		String line="";
		for(int i=0;i<cmd.length;i++) line=line+" "+cmd[i];
        IO.println("MakeSML.execute: command="+line);
		ProcessBuilder processBuilder = new ProcessBuilder(cmd);
		processBuilder.redirectErrorStream(true);
		try {
			Process process = processBuilder.start();		
			InputStream output = process.getInputStream();  // Process' output
			while (process.isAlive()) {
				while (output.available() > 0) {
					System.out.append((char) output.read());
				}
//				IO.println("ALIVE: "+process.isAlive());
			}
//			IO.println("RETURN: "+process.exitValue());
//			Thread.dumpStack();
			return (process.exitValue());

		} catch(Exception e) {
			IO.println("ERROR: "+e);
			throw new RuntimeException("Process Execution failed: " + line, e);
		}
	}
}
