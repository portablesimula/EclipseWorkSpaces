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
		if(Global.SCODE_INPUT_TRACE) {
			Scode.initTraceBuff("Line " + Scode.curline + "  " + id + ": " + msg);
		}
	}
	
	public static void println(String s) {
		if(Global.console != null)
			Global.console.write(s + '\n');
		System.out.println(s);
	}

	public static void WARNING(String msg) {
		if(Global.SCODE_INPUT_TRACE) {
			Scode.flushTraceBuff();
		}
		System.out.println("ERROR: " + msg);
	}

	public static void ERROR(String msg) {
		if(Global.SCODE_INPUT_TRACE) {
			Scode.flushTraceBuff();
		}
		System.out.println("ERROR: " + msg);
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
		if (Global.ATTR_OUTPUT_TRACE)
			System.out.println("ATTR OUTPUT: " + msg);
	}

	/**
	 * Utility method: TRACE_INPUT
	 * @param msg the message to print
	 */
	public static void TRACE_INPUT(final String msg) {
		if (Global.ATTR_INPUT_TRACE)
			System.out.println("ATTR INPUT: " + msg);
	}


	
	// ***************************************************************
	// *** EXTERNAL CONSOLE BINDING
	// ***************************************************************

	public static InputStream getConsoleInputStream() {
		try {
			Class<?> console = getClass("simula.compiler.utilities.ConsolePanel", "sim.Console");
			System.out.println("Util.getConsoleReader: console="+console);
			Field field_current = console.getDeclaredField("current");
			System.out.println("Util.getConsoleReader: field_current="+field_current);
			Method getInputStream = console.getDeclaredMethod("getInputStream");
			System.out.println("Util.getConsoleReader: getInputStream="+getInputStream);
			Object object = field_current.get(console);
			return (InputStream) getInputStream.invoke(object);
		} catch(Exception e) { e.printStackTrace(); return null; }
	}

	public static OutputStream getConsoleOutputStream() {
		try {
			Class<?> console = getClass("simula.compiler.utilities.ConsolePanel", "sim.Console");
			System.out.println("Util.getConsoleWriter: console="+console);
			Field field_current = console.getDeclaredField("current");
			System.out.println("Util.getConsoleWriter: field_current="+field_current);
			Method getOutputStream = console.getDeclaredMethod("getOutputStream");
			System.out.println("Util.getConsoleWriter: getOutputStream="+getOutputStream);
			Object object = field_current.get(console);
			return (OutputStream) getOutputStream.invoke(object);
		} catch(Exception e) { e.printStackTrace(); return null; }
	}

	public static Reader getConsoleReader() {
		try {
			Class<?> console = getClass("simula.compiler.utilities.ConsolePanel", "sim.Console");
			System.out.println("Util.getConsoleReader: console="+console);
			Field field_current = console.getDeclaredField("current");
			System.out.println("Util.getConsoleReader: field_current="+field_current);
			Method getReader = console.getDeclaredMethod("getReader");
			System.out.println("Util.getConsoleReader: getReader="+getReader);
			Object object = field_current.get(console);
			return (Reader) getReader.invoke(object);
		} catch(Exception e) { e.printStackTrace(); return null; }
	}

	public static Writer getConsoleWriter() {
		try {
			Class<?> console = getClass("simula.compiler.utilities.ConsolePanel", "sim.Console");
			System.out.println("Util.getConsoleWriter: console="+console);
			Field field_current = console.getDeclaredField("current");
			System.out.println("Util.getConsoleWriter: field_current="+field_current);
			Method getWriter = console.getDeclaredMethod("getWriter");
			System.out.println("Util.getConsoleWriter: getWriter="+getWriter);
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
        System.out.println("MakeSML.execute: command="+line);
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
//			System.out.println("RETURN: "+process.exitValue());
//			Thread.dumpStack();
			return (process.exitValue());

		} catch(Exception e) {
			System.out.println("ERROR: "+e);
			throw new RuntimeException("Process Execution failed: " + line, e);
		}
	}
}
