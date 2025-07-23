package test;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.TextArea;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class Terminal extends Frame {
//public class Terminal extends JFrame {

	/// The text area.
	private static TextArea textArea;
	
	/// The input queue.
	private ArrayDeque<KeyEvent> inputQueue;
	
	public boolean exitOnClose;


	// ****************************************************************
	// *** Main Entry for TESTING ONLY
	// ****************************************************************
    /// SimulaEditor: Main Entry for TESTING ONLY.
    /// @param args the arguments
    /// @throws IOException 
	public static void main(String[] args) throws IOException {
		Terminal terminal = new Terminal("TESTING");
//		terminal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		terminal.exitOnClose = true;
		terminal.println("BEGIN TESTING Terminal");
		terminal.print("Input Single Character: ");
		char c = terminal.read();  
		System.out.println("Single Character read was: " + c);

		terminal.print("\nInput Text Line terminated by NL: ");
		String s = terminal.readln();

//		terminal.println("Single Character read was: " + c);
		terminal.println("Inputed Text Line was: " + s);

//		terminal.print("\nTesting Reader -> Writer:\nInput Text Line terminated by NL: ");
////		Reader reader = terminal.getReader();
//		Writer writer = terminal.getWriter();
//		StringBuilder sb = new StringBuilder();
//		char c = (char) reader.read();
//		while(c != '\n') { sb.append(c); c = (char) reader.read(); }
//		writer.write("Reader GOT: " + sb.toString()+'\n'); writer.flush();
//
//		terminal.print("\nTesting InputStream -> OutputStream:\nInput Text Line terminated by NL: ");
////		InputStream inpt = terminal.getInputStream();
////		OutputStream oupt = terminal.getOutputStream();
////		sb = new StringBuilder();
////		c = (char) inpt.read();
////		while(c != '\n') { sb.append(c); c = (char) inpt.read(); }
////		terminal.println("InputStream GOT: "+sb);
////		oupt.write(sb.toString().getBytes(), 0, 0); oupt.flush();
//		terminal.println("ENDOF TESTING Terminal");
	}
	
	public Terminal(String title) {
        inputQueue = new ArrayDeque<KeyEvent>();
		setTitle(title);
		setSize(1000, 700);
		setBackground(Color.WHITE);
		setLocationRelativeTo(null);
		setFocusable(true);
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		textArea = new TextArea("", 50, 138, TextArea.SCROLLBARS_BOTH);
		
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textArea.setEnabled(true);
		textArea.setEditable(false);
		textArea.setFocusable(true);
		
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.add(textArea);
//		add(scrollPane);
//		
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.add(textArea, BorderLayout.CENTER);
//        add(panel);

        add(textArea);

        addWindowListener(new WindowAdapter () {
			public void windowClosing(WindowEvent e) {
				System.out.println("windowClosing: "+e);
				if(Terminal.this.exitOnClose) System.exit(0);
				Terminal.this.dispose();
		}});
        
//        addKeyListener(new KeyAdapter() {
//			public void keyTyped(KeyEvent event) {
//				System.out.println("Terminal'keyTyped on Frame: " + event.getKeyChar());
//				inputQueue.offerLast(event);
//			}});

        textArea.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent event) {
				System.out.println("Terminal'keyTyped on textArea: " + event.getKeyChar());
				inputQueue.offerLast(event);
			}});

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
	private void write(final String s) {
		Terminal.textArea.append(s);
	}

	/// Reads a single character.
	/// @return The character read
	public char read() {
//		this.toFront();
//		this.requestFocus();
//		this.requestFocusInWindow();
		
		printStatus(this, "X-Terminal.read:");
		printThreadList();
		
//		while(inputQueue.isEmpty()) Thread.yield();
		while(inputQueue.isEmpty())	try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
		char c =  inputQueue.pollFirst().getKeyChar();
		System.out.println("Terminal.read: returns " + c);
		Terminal.this.write(""+c); // ECHO
		return c;
		
//		done = false;
//		Thread.startVirtualThread(new Runnable() {
//			public void run() {
////				Terminal.this.toFront();
//				Terminal.this.requestFocus();
////				Terminal.this.requestFocusInWindow();
//				printStatus(Terminal.this, "Terminal.read:");
//				readDone = true; // Enables KeyListener (see below)
//				while (readDone) Thread.yield();
//				done = true;
//			}});
//		while (done) Thread.yield();
//		return (lastKeyin);
		
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


	public static Window getFocusedWindow() {
		KeyboardFocusManager kbfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		return kbfm.getFocusedWindow();
	}

	public static void printStatus(Component cmp, String title) {
		System.out.println(title + ": FocusedWindow=" + getFocusedWindow());
		if(cmp instanceof Frame frm) {
			System.out.println(title + ": isFocusableWindow ="  + frm.isFocusableWindow());
			System.out.println(title + ": isAutoRequestFocus =" + frm.isAutoRequestFocus());
		}
		System.out.println(title + ": isFocusable="  + cmp.isFocusable());
		System.out.println(title + ": isEnabled="    + cmp.isEnabled());
		System.out.println(title + ": isFocusOwner=" + cmp.isFocusOwner());
		System.out.println(title + ": isVisible="    + cmp.isVisible());
	}
	
	public static void printThreadList(){
        // Get a map of all threads and their stack traces
        Map<Thread, StackTraceElement[]> allThreads = Thread.getAllStackTraces();
        Set<Thread> threadSet = allThreads.keySet();

        System.out.println("Active Threads in JVM:");
        for (Thread t : threadSet) {
            System.out.printf("Name: %-20s | State: %-10s | Priority: %d | Daemon: %b%n",
                              t.getName(), t.getState(), t.getPriority(), t.isDaemon());
        }

	}
}
