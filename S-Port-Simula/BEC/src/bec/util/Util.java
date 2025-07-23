package bec.util;

import java.io.IOException;
import java.io.InputStream;

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
				while (output.available() > 0) {
					System.out.append((char) output.read());
				}
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
