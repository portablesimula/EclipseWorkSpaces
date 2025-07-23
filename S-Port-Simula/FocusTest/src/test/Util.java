package test;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JFrame;

public class Util {

	public static Window getFocusedWindow() {
		KeyboardFocusManager kbfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		return kbfm.getFocusedWindow();
	}

//	public static void printJFrameStatus(JFrame frm, String title) {
	public static void printStatus(Component cmp, String title) {
		System.out.println(title + ": FocusedWindow=" + getFocusedWindow());
		if(cmp instanceof JFrame frm) {
			System.out.println(title + ": isFocusableWindow ="  + frm.isFocusableWindow());
			System.out.println(title + ": isAutoRequestFocus =" + frm.isAutoRequestFocus());
		}
		System.out.println(title + ": isFocusable="  + cmp.isFocusable());
		System.out.println(title + ": isEnabled="    + cmp.isEnabled());
		System.out.println(title + ": isFocusOwner=" + cmp.isFocusOwner());
		System.out.println(title + ": isVisible="    + cmp.isVisible());
	}
	
	// ***************************************************************
	// *** EXECUTE OS COMMAND
	// ***************************************************************
	public static int exec(final Vector<String> cmd) throws IOException {
		String[] cmds = new String[cmd.size()];
		cmd.copyInto(cmds);
		return (exec(cmds));
	}

	public static int exec(String... cmd) throws IOException {
		String cmdLine="";
		for(int i=0;i<cmd.length;i++) cmdLine=cmdLine+" "+cmd[i];
//        System.out.println("Util.exec: command="+cmdLine);
		ProcessBuilder processBuilder = new ProcessBuilder(cmd);
//		processBuilder.inheritIO();
//		processBuilder.redirectErrorStream();
		try {
			Process process = processBuilder.start();
			
			BufferedReader reader = process.inputReader(); // Process' output
			String line = null;
			while((line = reader.readLine()) != null) {
			    System.out.println(line);
			}
			
			int exitCode = process.waitFor();
//			if(Option.verbose) System.out.println("Util.exec: exitCode = "+exitCode);
			return exitCode;
		} catch(Exception e) {
			e.printStackTrace();
//			Util.IERR("Util.exec: Process Execution failed: " + cmdLine, e);
			return -1;
		}
	}

	
}
