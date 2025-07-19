package sim;

import java.io.IOException;

public class ConsoleTester {

	public static void main(String[] args) throws IOException {
		Terminal terminal = new Terminal("Testing");
		System.setIn(terminal.getInputStream());
		System.setOut(terminal.getOutputStream());
		
		System.out.println("BEGIN TESTING ConsolePanel");
		System.out.print("\nInput Single Character: ");
		char c = (char) System.in.read();  
		System.out.println("\nSingle Character read was: " + c);
		
		System.out.print("\nInput Text Line terminated by NL: ");
		String s = readLine();
		System.out.println("Inputed Text Line was: " + s);
		
		System.out.println("END - ConsoleTest");
	}
	
	public static String readLine() throws IOException {
		StringBuilder sb = new StringBuilder();
		char c = (char) System.in.read();
		while(c != '\n') {
			sb.append(c);
			c = (char) System.in.read();
		}
		return sb.toString();
	}

}
