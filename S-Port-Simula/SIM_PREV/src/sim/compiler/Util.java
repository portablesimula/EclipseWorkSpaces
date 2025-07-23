package sim.compiler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Util {
	
	public static void warning(final String msg) {
		System.out.println("WARNING: " + msg);
	}
	
	public static void error(final String msg) {
		System.out.println("ERROR: " + msg);
	}

	/// Utility method: ASSERT
	/// @param test this test must be true
	/// @param msg the message when test = false
	public static void ASSERT(final boolean test, final String msg) {
		if (!test) {
			IERR("ASSERT(" + msg + ") -- FAILED");
		}
	}

	/// Print a string.
	/// @param s the string
	public static void println(final String s) {
		System.out.println(s);
	}  

	/// Print the internal error message: IMPOSSIBLE.
	public static void IERR() {
		IERR("IMPOSSIBLE");
	}

	/// Print a internal error message.
	/// @param msg the message
	public static void IERR(final String msg) {
//		String err = edLINE(": Internal error - " + msg);
//		nError++;
//		printError(err);
		System.out.println("Internal error: " + msg);
		Thread.dumpStack();
//		FORCED_EXIT();
		System.exit(-1);
	}

	/// Print a internal error message.
	/// @param msg the message
	/// @param e any Throwable
	public static void IERR(final String msg,final Throwable e) {
		IERR(msg +"  "+ e);
	}


	/// Utility method: TRACE
	/// @param msg the message to print
	public static void TRACE(final String msg) {
//		if (Option.TRACING)
			System.out.println("TRACE " + SimGlobal.sourceLineNumber + ": " + msg);
	}
	
	public static boolean getBoolProperty(Properties properties, String key, boolean defaultValue) {
		String prop = properties.getProperty(key);
		if(prop == null) return defaultValue;
		return (prop.equalsIgnoreCase("true"))? true : false;
	}
	
	public static int getIntProperty(Properties properties, String key, int defaultValue) {
		String prop = properties.getProperty(key);
		if(prop == null) return defaultValue;
		return Integer.valueOf(prop);
	}
	  
    //*******************************************************************************
    //*** 
    //*******************************************************************************
	/// Returns true if the two specified strings are equal to one another.
	/// @param s1 argument string
	/// @param s2 argument string
	/// @return true if the two specified strings are equal to one another
	public static boolean equals(String s1,String s2) {
		if(Option.CaseSensitive)
			 return(s1.equals(s2));			
		else return(s1.equalsIgnoreCase(s2));
	}
	
	/// Pop up an error message box.
	/// @param msg the error message
	public static void popUpError(final String msg) {
		int res=Util.optionDialog(msg+"\nDo you want to continue ?","Error",JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, "Yes", "No");
		if(res!=JOptionPane.YES_OPTION) System.exit(0);
	}

	/// Brings up an option dialog.
	/// @param msg the message to display
	/// @param title the title string for the dialog
	/// @param optionType an integer designating the options available on the dialog
	/// @param messageType an integer designating the kind of message this is
	/// @param option an array of objects indicating the possible choices the user can make
	/// @return an integer indicating the option chosen by the user, or CLOSED_OPTION if the user closed the dialog
	public static int optionDialog(final Object msg, final String title, final int optionType, final int messageType, final String... option) {
		Object OptionPaneBackground = UIManager.get("OptionPane.background");
		Object PanelBackground = UIManager.get("Panel.background");
		UIManager.put("OptionPane.background", Color.WHITE);
		UIManager.put("Panel.background", Color.WHITE);
		int answer = JOptionPane.showOptionDialog(null, msg, title, optionType, messageType, SimGlobal.sIcon, option, option[0]);
		// System.out.println("doClose.saveDialog: answer="+answer);
		UIManager.put("OptionPane.background", OptionPaneBackground);
		UIManager.put("Panel.background", PanelBackground);
		return (answer);
	}

	/// Editor Utility: Create a checkBox with tooltips.
	/// @param id option id
	/// @param tooltip option's tooltip or null
	/// @return the resulting check box
	public static JCheckBox checkBox(String id,String tooltip) {
		return checkBox(id, tooltip,Option.getOption(id));
	}

	/// Editor Utility: Create a checkBox with tooltips.
	/// @param id option id.
	/// @param tooltip option's tooltip or null.
	/// @param selected true: this checkBox is selected.
	/// @return the resulting check box.
	public static JCheckBox checkBox(String id,String tooltip,boolean selected) {
		JCheckBox item = new JCheckBox(id);
		item.setBackground(Color.white);
        item.setSelected(selected);
        item.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Option.setOption(id,item.isSelected());
		}});
        if(tooltip != null) item.setToolTipText(tooltip);
        item.addMouseListener(new MouseAdapter() {
            Color color = item.getBackground();
            @Override
            public void mouseEntered(MouseEvent me) {
               color = item.getBackground();
               item.setBackground(Color.lightGray); // change the color to lightGray when mouse over a button
            }
            @Override
            public void mouseExited(MouseEvent me) {
            	item.setBackground(color);
            }
         });
        return(item);
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
        if(Option.verbose) System.out.println("Util.exec: command="+cmdLine);
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
			
			boolean terminated = process.waitFor(5, TimeUnit.MINUTES);
			if(! terminated) Util.IERR("Util.exec: Process Execution didn't terminate: " + cmdLine);
			if(Option.verbose) System.out.println("Util.exec: RETURN: "+process.exitValue());
			
			int exitCode = process.exitValue();
			if(Option.verbose) System.out.println("Util.exec: exitCode = "+exitCode);
			return exitCode;
		} catch(Exception e) {
			e.printStackTrace();
			Util.IERR("Util.exec: Process Execution failed: " + cmdLine, e);
			return -1;
		}
	}
	
//	// ***************************************************************
//	// *** LIST .jar file
//	// ***************************************************************
//	/**
//	 * List .jar file
//	 * @param file the .jar file
//	 */
//	public static void listJarFile(final File file) {
//		System.out.println("---------  LIST .jar File: " + file + "  ---------");
//		if (!(file.exists() && file.canRead())) {
//			System.out.println("ERROR: Can't read .jar file: " + file);
//			return;
//		}
//		JarFile jarFile = null;
//		try {
//			jarFile = new JarFile(file);
//			Manifest manifest = jarFile.getManifest();
//			Attributes mainAttributes = manifest.getMainAttributes();
//			Set<Object> keys = mainAttributes.keySet();
//			for (Object key : keys) {
//				String val = mainAttributes.getValue(key.toString());
//				System.out.println(key.toString() + "=\"" + val + "\"");
//			}
//
//			Enumeration<JarEntry> entries = jarFile.entries();
//			while (entries.hasMoreElements()) {
//				JarEntry entry = entries.nextElement();
//				String size = "" + entry.getSize();
//				while (size.length() < 6)
//					size = " " + size;
//				FileTime fileTime = entry.getLastModifiedTime();
//				String date = DateTimeFormatter.ofPattern("uuuu-MMM-dd HH:mm:ss", Locale.getDefault())
//						.withZone(ZoneId.systemDefault()).format(fileTime.toInstant());
//				System.out.println("Jar-Entry: " + size + "  " + date + "  \"" + entry + "\"");
//			}
//		} catch (IOException e) {
//			System.out.println("IERR: Caused by: " + e);
//		} finally {
//			if (jarFile != null)
//				try {
//					jarFile.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//		}
//	}


}
