package bec.util;

import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Util {

	public static void setLine(Type type) {
		Scode.curline = Scode.inNumber();
	}

	public static void ITRC(String id, String msg) {
		if(BecGlobal.SCODE_INPUT_TRACE) {
			Scode.initTraceBuff("Line " + Scode.curline + "  " + id + ": " + msg);
		}
	}
	
	public static void println(String s) {
		System.out.println(s);
	}

	public static void WARNING(String msg) {
		if(BecGlobal.SCODE_INPUT_TRACE) {
			Scode.flushTraceBuff();
		}
		System.out.println("ERROR: " + msg);
	}

	public static void ERROR(String msg) {
		if(BecGlobal.SCODE_INPUT_TRACE) {
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
		if (BecGlobal.ATTR_OUTPUT_TRACE)
			System.out.println("ATTR OUTPUT: " + msg);
	}

	/**
	 * Utility method: TRACE_INPUT
	 * @param msg the message to print
	 */
	public static void TRACE_INPUT(final String msg) {
		if (BecGlobal.ATTR_INPUT_TRACE)
			System.out.println("ATTR INPUT: " + msg);
	}
	
	public static Window getFocusedWindow() {
		KeyboardFocusManager kbfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		return kbfm.getFocusedWindow();
	}

	
	// ***************************************************************
	// *** EXECUTE OS COMMAND
	// ***************************************************************
	public static int exec(String... cmd) throws IOException {
		String cmdLine="";
		for(int i=0;i<cmd.length;i++) cmdLine=cmdLine+" "+cmd[i];
        System.out.println("Util.exec: command="+cmdLine);
		ProcessBuilder processBuilder = new ProcessBuilder(cmd);
		processBuilder.redirectErrorStream(true);
		try {
			Process process = processBuilder.start();
			
			BufferedReader reader = process.inputReader(); // Process' output
			String line = null;
			while((line = reader.readLine()) != null) {
			    System.out.println(line);
//				if(Global.consolePanel != null) {
//					Global.consolePanel.write(line + '\n');
//				}
			}
			
			boolean terminated = process.waitFor(5, TimeUnit.MINUTES);
			if(! terminated) Util.IERR("Util.exec: Process Execution didn't terminate: " + cmdLine);
			if(BecGlobal.verbose) System.out.println("Util.exec: RETURN: "+process.exitValue());
			
			int exitCode = process.exitValue();
			if(BecGlobal.verbose) System.out.println("Util.exec: exitCode = "+exitCode);
			return exitCode;
		} catch(Exception e) {
			System.out.println("ERROR: "+e);
			throw new RuntimeException("Process Execution failed: " + cmdLine, e);
		}
	}
}
