package test;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FocusTest extends JFrame {
	public static FocusTest test;

	public static void main(String[] args) throws IOException {
//		test=new FocusTest();
//		test.setVisible(true);
		
		System.out.print("Input: ");
		char c = (char) System.in.read();
		System.out.println("GOT: " + c);
		
//		Console console = System.console();
//		PrintWriter prt = console.writer();
//		prt.append("Input: "); prt.flush();
//		String line = console.readLine();
//		System.out.println("GOT: " + line);

	}

	public FocusTest() {

		addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent event) {
				System.out.println("FocusTest'keyTyped: " + event.getKeyChar());
				if(event.getKeyChar() == 'q') new AnotherFrame();
				if(event.getKeyChar() == 't') {
					Thread.startVirtualThread(new Runnable() {
						public void run() {
							Terminal terminal = new Terminal("Terminal");
							terminal.println("Input: ");
							String c = terminal.readln();
						}});
				}
				if(event.getKeyChar() == 'x') {
					new Thread(new Runnable() {
						public void run() {
							Terminal terminal = new Terminal("Terminal");
							terminal.println("Input: ");
							String c = terminal.readln();
						}
					}).start();
				}
			}});

		int frameHeight=800;//500;
		int frameWidth=1000;
		setSize(frameWidth, frameHeight);
		setTitle("FocusTest");
		setVisible(true);
//		toFront();
//		requestFocus();

	}
}
