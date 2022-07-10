package main;



import java.awt.Color;

import javax.swing.JFrame;

public class ShortcutMain {

	public static void main(String[] args) {
		try {
			Settings.init();
		} catch (Exception e) {
			// TODO handle
		}
		Shortcuts.init();

		JFrame frame = new JFrame();
		frame.setSize(Settings.size, Settings.size);
		frame.setLocation(Settings.pos);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setFocusableWindowState(false);
		frame.setFocusable(false);
		frame.setAlwaysOnTop(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Canvas c = new Canvas();
		frame.add(c);

		frame.setVisible(true);

	}
	
}
