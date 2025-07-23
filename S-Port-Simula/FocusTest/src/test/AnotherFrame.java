package test;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class AnotherFrame extends JFrame {

	public AnotherFrame() {

		addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent event) {
				System.out.println("AnotherFrame'keyTyped: " + event.getKeyChar());
				Util.printStatus(AnotherFrame.this, "AnotherFrame");
				if(event.getKeyChar() == 'Q') {
					new AnotherFrame();
				}
			}});

		int frameHeight=800;//500;
		int frameWidth=1000;
		setSize(frameWidth, frameHeight);
		setTitle("AnotherFrame");
		setVisible(true);
//		toFront();
//		requestFocus();
	}

}
