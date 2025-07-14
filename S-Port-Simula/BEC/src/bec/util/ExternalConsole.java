package bec.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

//@SuppressWarnings("serial")
//public class Console extends JFrame {
//	ConsolePanel consolePanel;
//	
//	public Console(String title) {
//		this.setSize(800, 1000);
//		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		this.setLocationRelativeTo(null); // center the frame on screen
//
//		ConsolePanel consolePanel = new ConsolePanel();
//		this.add(consolePanel);
//		consolePanel.write("BEGIN TESTING ConsolePanel\n");
//		this.setVisible(true);
//	}
//	
//	public void write(String s) {
//		consolePanel.write(s);
//	}
//	
//	public char read() {
//		return consolePanel.read();
//	}

public class ExternalConsole {
	private InputStream inpt;
	private OutputStream oupt;
	private Reader reader;
	private Writer writer;
	
	public ExternalConsole() {
		inpt = Util.getConsoleInputStream();
		oupt = Util.getConsoleOutputStream();
		reader = Util.getConsoleReader();
		writer = Util.getConsoleWriter();
		this.write("BEGIN TESTING External Console\n");
		this.write("Input: "); 
		char c = read();
		this.write("GOT: " + c);
	}

	public char read() {
		try {
			System.out.println("RETT FØR BEC:read");
//			return (char) reader.read();
			return (char) inpt.read();
		} catch (IOException e) {
			e.printStackTrace();
			return ' ';
		}
	}

	public void write(String s) {
		try {
//			writer.write(s); writer.flush();
			oupt.write(s.getBytes()); oupt.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
