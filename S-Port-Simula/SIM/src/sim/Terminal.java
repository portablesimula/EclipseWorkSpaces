package sim;

import java.awt.Color;
import java.awt.TextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Terminal extends JFrame {

	/// The text area.
	private static TextArea textArea;

	/// Used by KeyListener and read()
	private boolean reading;

	/// Used by KeyListener and read()
	private char keyin;


	// ****************************************************************
	// *** Main Entry for TESTING ONLY
	// ****************************************************************
    /// SimulaEditor: Main Entry for TESTING ONLY.
    /// @param args the arguments
    /// @throws IOException 
	public static void main(String[] args) throws IOException {
		Terminal console = new Terminal("TESTING");
		console.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		console.println("BEGIN TESTING Terminal");
		console.print("Input Single Character: ");
		char c = console.read();  

		console.print("\nInput Text Line terminated by NL: ");
		String s = console.readLine();

		console.println("Single Character read was: " + c);
		console.println("Inputed Text Line was: " + s);

		console.print("\nTesting Reader -> Writer:\nInput Text Line terminated by NL: ");
		Reader reader = console.getReader();
		Writer writer = console.getWriter();
		StringBuilder sb = new StringBuilder();
		c = (char) reader.read();
		while(c != '\n') { sb.append(c); c = (char) reader.read(); }
		writer.write("Reader GOT: " + sb.toString()+'\n'); writer.flush();

		console.print("\nTesting InputStream -> OutputStream:\nInput Text Line terminated by NL: ");
		InputStream inpt = console.getInputStream();
		OutputStream oupt = console.getOutputStream();
		sb = new StringBuilder();
		c = (char) inpt.read();
		while(c != '\n') { sb.append(c); c = (char) inpt.read(); }
		console.println("InputStream GOT: "+sb);
		oupt.write(sb.toString().getBytes(), 0, 0); oupt.flush();
		console.println("ENDOF TESTING Terminal");
	}
	
	public Terminal(String title) {
		setTitle(title);
		setSize(1000, 700);
		setBackground(Color.WHITE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		textArea = new TextArea("", 50, 138, TextArea.SCROLLBARS_BOTH);
		textArea.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent event) {
//				System.out.println("ConsolePanel'keyTyped: "+event.getKeyChar());
				if (reading) {
					keyin = event.getKeyChar();
					reading = false;
				}
			}
		});
		textArea.setEnabled(true);
		add(textArea);
//		validate();
	    setVisible(true);
	}

	public void print(final String s) {
		write(s);
	}
	
	public void println(final String s) {
		write(s + '\n');
	}

	/// Write a string
	/// @param s a string to write
	public void write(final String s) {
		Terminal.textArea.append(s);
	}

	/// Reads a single character.
	/// @return The character read
	public char read() {
//		textArea.requestFocusInWindow();
		reading = true; // Enables KeyListener (see below)
		while (reading) { Thread.yield(); }
		return (keyin);
	}

	
	public String readLine() {
		StringBuilder sb = new StringBuilder();
		char c = read();
		while(c != '\n') {
			sb.append(c);
			c = read();
		}
		return sb.toString();
	}

	/// Get a reader suitable for reading from this console
	/// @return a reader
	public Reader getReader() {
		Reader consoleReader = new Reader() {
			@Override
			public int read(final char[] cbuf, final int off, final int len) throws IOException {
				cbuf[off] = Terminal.this.read(); return (1);
			}

			@Override
			public void close() throws IOException {}
		};
		return (consoleReader);
	}

	/// Get a InputStream suitable for reading from this console
	/// @return a OutputStream
	public InputStream getInputStream() {
		InputStream in = new InputStream() {
			@Override
			public int read() throws IOException {
				return Terminal.this.read();
			}
		};
		return in;
	}

	/// Get a PrintStream suitable for writing on this console
	/// @return a OutputStream
	public PrintStream getOutputStream() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				String s = "" + (char) b;
				Terminal.this.write(s);
			}
		};
		return new PrintStream(out);
	}

	/// Get a writer suitable for writing on this console
	/// @return a writer
	public Writer getWriter() {
		return (new Writer() {
			@Override
			public void write(String s) {
				Terminal.this.write(s);
			}

			public void write(char[] cbuf, int off, int len) throws IOException {
				Terminal.this.write(new String(cbuf, off, len));
			}

			@Override
			public void flush() throws IOException {
			}

			@Override
			public void close() throws IOException {
			}
		});
	}


	// ****************************************************************
	// *** KeyListener
	// ****************************************************************
	/// the KeyListener
	private KeyAdapter keyListener = new KeyAdapter() {
		public void keyTyped(KeyEvent event) {
//			System.out.println("ConsolePanel'keyTyped: "+event.getKeyChar());
			if (reading) {
				keyin = event.getKeyChar();
				reading = false;
			}
		}
	};
	
}
