/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.util;

import java.awt.AWTEvent;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public final class TextPad extends TextArea {
//public final class TextPad extends JTextArea {

	/// Used by KeyListener and read()
	private boolean reading;

	/// Used by KeyListener and read()
	private char keyin;
	
	public TextPad() {
		this.setFont(new Font("Monospaced", Font.PLAIN, 14));
        this.setFocusable(true);
		this.setEditable(true);
		this.setEnabled(true);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	    toolkit.addAWTEventListener(new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent event) {
				System.out.println("TextPad'eventDispatched: " + event);
		}}, AWTEvent.KEY_EVENT_MASK);

	}

	/// Reads a single character.
	/// @return The character read
	public char read() {
///		this.requestFocus();
		this.requestFocusInWindow();
		System.out.println("TextPad.read: isFocusable=" + this.isFocusable());
		System.out.println("TextPad.read: isEnabled=" + this.isEnabled());
		System.out.println("TextPad.read: isEditable=" + this.isEditable());
		System.out.println("TextPad.read: isFocusOwner=" + this.isFocusOwner());
		System.out.println("TextPad.read: isVisible=" + this.isVisible());
		reading = true; // Enables KeyListener
		while (reading) Thread.yield();
		return (keyin);
	}

	public String readln() {
		StringBuilder sb = new StringBuilder();
		char c = read();
		while(c != '\n') {
			sb.append(c);
			c = read();
		}
		return sb.toString();
	}

	public void print(final String s) {
		append(s);
	}
	
	public void println(final String s) {
		append(s + '\n');
	}

	/// Get a reader suitable for reading from this terminal
	/// @return a reader
	public Reader getReader() {
		Reader reader = new Reader() {
			@Override
			public int read(final char[] cbuf, final int off, final int len) throws IOException {
				cbuf[off] = TextPad.this.read(); return (1);
			}

			@Override
			public void close() throws IOException {}
		};
		return (reader);
	}

	/// Get a InputStream suitable for reading from this terminal
	/// @return a OutputStream
	public InputStream getInputStream() {
		InputStream inpt = new InputStream() {
			@Override
			public int read() throws IOException {
				return TextPad.this.read();
			}
		};
		return inpt;
	}

	/// Get a PrintStream suitable for writing on this terminal
	/// @return a OutputStream
	public PrintStream getOutputStream() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				String s = "" + (char) b;
				TextPad.this.print(s);
			}
		};
		return new PrintStream(out);
	}

	/// Get a writer suitable for writing on this terminal
	/// @return a writer
	public Writer getWriter() {
		return (new Writer() {
			@Override
			public void write(String s) {
				TextPad.this.print(s);
			}

			public void write(char[] cbuf, int off, int len) throws IOException {
				TextPad.this.print(new String(cbuf, off, len));
			}

			@Override
			public void flush() throws IOException {
			}

			@Override
			public void close() throws IOException {
			}
		});
	}
	

	
}