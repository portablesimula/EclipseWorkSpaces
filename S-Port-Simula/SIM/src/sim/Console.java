package sim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import sim.compiler.Util;

@SuppressWarnings("serial")
public class Console extends JFrame {
	
	public static Console current;
	
	/// The TextPanel
	private TextPanel textPanel;

	/// Used by KeyListener and read()
	private boolean reading;

	/// Used by KeyListener and read()
	private char keyin;
	
	private boolean echo = true;
	
	private Reader textReader;

	// ****************************************************************
	// *** ConsolePanel: Main Entry for TESTING ONLY
	// ****************************************************************
    /// SimulaEditor: Main Entry for TESTING ONLY.
    /// @param args the arguments
    /// @throws IOException 
	public static void main(String[] args) throws IOException {
		Console console = new Console();
		console.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		console.println("BEGIN TESTING ConsolePanel");
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
		while(c != '\n') {
			sb.append(c);
			c = (char) reader.read();
		}
		writer.write("Reader GOT: " + sb.toString()+'\n'); writer.flush();

		console.print("\nTesting InputStream -> OutputStream:\nInput Text Line terminated by NL: ");
		InputStream inpt = console.getInputStream();
		OutputStream oupt = console.getOutputStream();
		sb = new StringBuilder();
		c = (char) inpt.read();
		console.print(""+c);
		while(c != '\n') {
			sb.append(c);
			c = (char) inpt.read();
			console.print(""+c);
		}
		console.println("InputStream GOT: "+sb);
		oupt.write(sb.toString().getBytes(), 0, 0); oupt.flush();
	}

     
	public Console() {
		this.setSize(1000, 500); // Initial this size
		this.setTitle("Runtime Console");
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		this.setLocationRelativeTo(null);
		this.textPanel = new TextPanel();
		this.getContentPane().add(textPanel);
		this.setVisible(true);

//		this.addKeyListener(listener);
//		this.setFocusable(true);

		current = this;
	}
	
	public void print(final String s) {
		textPanel.write(s);
	}
	
	public void println(final String s) {
		textPanel.write(s + '\n');
	}

	/// Reads a single character.
	/// @return The character read
	public char read() {
		return textPanel.read();
	}
	
	public String readLine() {
		StringBuilder sb = new StringBuilder();
		char c = textPanel.read();
		while(c != '\n') {
			System.out.println("Console.readLine: c="+c);
			sb.append(c);
			c = textPanel.read();
		}
		return sb.toString();
	}

	/// Get a reader suitable for reading from this console
	/// @return a reader
	public Reader getReader() {
		return textPanel.getReader();
	}

	/// Get a writer suitable for writing on this console
	/// @return a writer
	public Writer getWriter() {
		return textPanel.getWriter();
	}

	/// Get a InputStream suitable for reading from this console
	/// @return a OutputStream
	public InputStream getInputStream() {
		return textPanel.getInputStream();
	}


	/// Get a PrintStream suitable for writing on this console
	/// @return a OutputStream
	public PrintStream getOutputStream() {
		return textPanel.getOutputStream();
	}

	
	// ================================================================
	// The Text Panel
	// ================================================================
	class TextPanel extends JPanel {

		/// The text pane.
		private static JTextPane textPane;

		/// the StyledDocument showed in this panel
		private StyledDocument doc;

		/// Regular style
		private Style styleRegular;

		/// Warning style
		private Style styleWarning;

		/// Error style
		private Style styleError;

		/// the Popup Menu
		private JPopupMenu popupMenu;

		/// Menu item clear
		private JMenuItem clearItem;

		/// Menu item copy
		private JMenuItem copyItem;

		
		/// Create a new TextPanel.
		public TextPanel() {
			super(new BorderLayout());
			JScrollPane scrollPane;
			textPane = new JTextPane();
			textPane.addMouseListener(mouseListener);
			scrollPane = new JScrollPane(textPane);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			doc = new DefaultStyledDocument();
			addStylesToDocument(doc);
			doc.putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");
			textPane.setStyledDocument(doc);

			textPane.addKeyListener(textListener);
			textPane.setFocusable(true);
			
			textPane.setEditable(false);
			popupMenu = new JPopupMenu();
			clearItem = new JMenuItem("Clear Console");
			// clearItem.setAccelerator(KeyStroke.getKeyStroke('X',
			// InputEvent.CTRL_DOWN_MASK));
			popupMenu.add(clearItem);
			clearItem.addActionListener(actionListener);
			copyItem = new JMenuItem("Copy to Clipboard");
			copyItem.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK));
			popupMenu.add(copyItem);
			copyItem.addActionListener(actionListener);
			this.add(scrollPane);
			this.setFocusable(true);
		}


		/// Utility to add styles to the document
		/// @param doc the document
		private void addStylesToDocument(final StyledDocument doc) {
			Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

			Style regular = doc.addStyle("regular", defaultStyle);
			StyleConstants.setFontFamily(defaultStyle, "Courier New");

			Style s = doc.addStyle("warning", regular);
			StyleConstants.setItalic(s, true);
			StyleConstants.setForeground(s, new Color(255, 153, 0));

			s = doc.addStyle("error", regular);
			StyleConstants.setBold(s, true);
			StyleConstants.setForeground(s, Color.RED);

			styleRegular = doc.getStyle("regular");
			styleWarning = doc.getStyle("warning");
			styleError = doc.getStyle("error");
		}

		/// Write a string on this panel using styleRegular.
		/// @param s a string to write
		public void write(final String s) {
			write(s, styleRegular);
		}

		/// Write a string on this panel using styleError.
		/// @param s a string to write
		void writeError(final String s) {
			write(s, styleError);
		}

		/// Write a string on this panel using styleWarning.
		/// @param s a string to write
		void writeWarning(final String s) {
			write(s, styleWarning);
		}

		/// Write a styled string onto this Console.
		/// @param s     the string to write
		/// @param style the style
		private void write(final String s, final Style style) {
			try {
				doc.insertString(doc.getLength(), s, style);
			} catch (BadLocationException e) {
				throw new RuntimeException("Console'TextPanel.write Failed: ", e);
			}
			textPane.setCaretPosition(textPane.getDocument().getLength());
			textPane.repaint();
			textPane.paintImmediately(0, 0, current.getWidth(), current.getHeight());
		}

		/// Reads a single character.
		/// @return The character read
		public char read() {
			reading = true; // Enables KeyListener (see below)
			System.out.println("SIM:Console'textPanel.read: ");
			while (reading)	Thread.yield();
			if(echo) textPanel.write(""+keyin);
			return (keyin);
		}

		/// Clear the Console.
		private void clear() {
			try {
				doc.remove(0, doc.getLength());
			} catch (BadLocationException e) {
				throw new RuntimeException("Console'TextPanel.clear Failed: ", e);
			}
			textPane.setCaretPosition(textPane.getDocument().getLength());
			textPane.update(textPane.getGraphics());
		}

		/// Get a reader suitable for reading from this console
		/// @return a reader
		public Reader getReader() {
			if (textReader == null) {
				textReader = new Reader() {
					@Override
					public int read(final char[] cbuf, final int off, final int len) throws IOException {
//						TextPanel.this.requestFocus();
						System.out.println("SIM:Console.getReader'read: BEFORE READ");
						char c = TextPanel.this.read();
						System.out.println("SIM:Console.getReader'read: AFTER READ c="+c);
						cbuf[off] = c;
						return (1);
					}

					@Override
					public void close() throws IOException {
					}
				};
			}
			return (textReader);
		}

		/// Get a InputStream suitable for reading from this console
		/// @return a OutputStream
		public InputStream getInputStream() {
			InputStream in = new InputStream() {
				@Override
				public int read() throws IOException {
//					Console.this.requestFocus();
					reading = true; // Enables KeyListener (see below)
					while (reading)
						Thread.yield();
					return (keyin);
				}
			    @Override
			    public int read(byte[] b) throws IOException {
			    	Util.IERR("NOT IMPL");
			    	return 0;
//			        return readBytes(b, 0, b.length);
			    }
			    @Override
			    public int read(byte[] b, int off, int len) throws IOException {
			    	Util.IERR("NOT IMPL");
			    	return 0;
//			        return readBytes(b, off, len);
			    }
			    @Override
			    public byte[] readAllBytes() throws IOException {
			    	Util.IERR("NOT IMPL");
					return null;
			    }
			    @Override
			    public byte[] readNBytes(int len) throws IOException {
			    	Util.IERR("NOT IMPL");
					return null;
			    }
			    @Override
			    public long transferTo(OutputStream out) throws IOException {
			    	Util.IERR("NOT IMPL");
					return keyin;
			    }
			    @Override
			    public long skip(long n) throws IOException {
			        return super.skip(n);
			    }
			    @Override
			    public int available() throws IOException {
			    	Util.IERR("NOT IMPL");
			        return 0;
			    }
			    @Override
			    public void close() throws IOException {
			    	Util.IERR("NOT IMPL");
			    }
			};
			return in;
		}

		/// Get a PrintStream suitable for writing on this panel
		/// @return a OutputStream
		public PrintStream getOutputStream() {
			OutputStream out = new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					String s = "" + (char) b;
					TextPanel.this.write(s, styleRegular);
				}
			    @Override
				public void write(byte[] b) throws IOException {
					for(int i = 0; i < b.length; i++) {
						write(b[i]);
					}
			    }
			    @Override
			    public void write(byte[] b, int off, int len) throws IOException {
					for(int i = 0; i < len; i++) {
			    		write(b[off+i]);
			    	}
			    }
			    @Override
			    public void close() throws IOException {
			    	Util.IERR("NOT IMPL");
			    }
			};
			return new PrintStream(out);
		}

		/// Get a writer suitable for writing on this panel
		/// @return a writer
		public Writer getWriter() {
			return (new Writer() {
				@Override
				public void write(String s) {
					TextPanel.this.write(s);
					TextPanel.this.repaint();
				}

				public void write(char[] cbuf, int off, int len) throws IOException {
					TextPanel.this.write(new String(cbuf, off, len));
					TextPanel.this.repaint();
				}

				@Override
				public void flush() throws IOException {
					TextPanel.this.repaint();
				}

				@Override
				public void close() throws IOException {
				}
			});
		}

//		/// Get a OutputStream suitable for writing errors on this panel
//		/// @return a OutputStream
//		public PrintStream getErrorStream() {
//			OutputStream out = new OutputStream() {
//				@Override
//				public void write(int b) throws IOException {
//					String s = "" + (char) b;
//					ConsolePanel.this.write(s, styleError);
//				}
//			};
//			return new PrintStream(out);
//		}


		// ****************************************************************
		// *** MouseListener
		// ****************************************************************
		/// the MouseListener
		MouseListener mouseListener = new MouseListener() {
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3)
					popupMenu.show(textPane, e.getX(), e.getY());
			}
		};

		// ****************************************************************
		// *** ActionListener
		// ****************************************************************
		/// the ActionListener
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object item = e.getSource();
				if (item == clearItem)
					clear();
				else if (item == copyItem) {
					String text = textPane.getSelectedText();
					if (text == null)
						text = textPane.getText();
					StringSelection stringSelection = new StringSelection(text);
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, null);
				}
			}
		};
		
		// ****************************************************************
		// *** KeyListener
		// ****************************************************************
		/// the KeyListener
		private KeyListener textListener = new KeyListener() {
			public void keyPressed(KeyEvent event) {
				System.out.println("Console'keyTyped: "+event);
			}

			public void keyReleased(KeyEvent event) {
				System.out.println("Console'keyTyped: "+event);
			}

			public void keyTyped(KeyEvent event) {
				System.out.println("Console'keyTyped: "+event);
				if (reading) {
					keyin = event.getKeyChar();
					reading = false;
				}
			}
		};


		
	}

}
