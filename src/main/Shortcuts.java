// File is part of TouchSC (c) 2020 msg-programs, see LICENSE
package main;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Shortcuts {

	public static Shortcut[][] sc = new Shortcut[4][4];
	public static Shortcut[] tsc = new Shortcut[4];

	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	public static final int TOPLEFT = 0;
	public static final int TOPRIGHT = 1;
	public static final int BOTLEFT = 2;
	public static final int BOTRIGHT = 3;

	public static final int PRESS = 0;
	public static final int TOGGLE = 1;

	public static HashMap<String, Integer> keys = new HashMap<>();

	public static void init() {

		for (int i = 65; i <= 90; i++) {
			keys.put(((char) i) + "", i);
		}
		keys.put("ALT", KeyEvent.VK_ALT);
		keys.put("CTRL", KeyEvent.VK_CONTROL);
		keys.put("TAB", KeyEvent.VK_TAB);
		keys.put("SHIFT", KeyEvent.VK_SHIFT);
		keys.put("CMD", KeyEvent.VK_META);

		for (int i = 0; i <= 9; i++) {
			keys.put("NP_" + i, KeyEvent.VK_NUMPAD0 + i);
		}

		for (int i = 1; i <= 24; i++) {
			keys.put("F" + i, KeyEvent.VK_F1 + i - 1);
		}
	}

	public static void doSC(int first, int second) {
		Shortcut cut = sc[first][second];

		if (!cut.isDef()) {
			return;
		}

		switch (cut.mode) {
		case Shortcuts.PRESS:
			pressSC(cut);
			break;
		case Shortcuts.TOGGLE:
			toggleSC(cut);
			break;
		}

	}

	public static void doTSC(int idx) {
		Shortcut cut = tsc[idx];

		System.out.println("Pressing " + String.join(" + ", cut.keys));

		if (!cut.isDef()) {
			return;
		}

		switch (cut.mode) {
		case Shortcuts.PRESS:
			pressSC(cut);
			break;
		case Shortcuts.TOGGLE:
			toggleSC(cut);
			break;
		default:
			break;
		}

	}

	private static void toggleSC(Shortcut cut) {
		try {

			Robot r = new Robot();

			if (cut.isHeld) {
				System.out.println("Toggeling off " + String.join(" + ", cut.keys));
				for (String s : cut.keys) {
					cut.isHeld = false;
					r.keyRelease(keys.get(s));
				}
			} else {
				System.out.println("Toggeling on " + String.join(" + ", cut.keys));
				for (String s : cut.keys) {
					cut.isHeld = true;
					r.keyPress(keys.get(s));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void pressSC(Shortcut cut) {
		try {
			System.out.println("Pressing " + String.join(" + ", cut.keys));
			Robot r = new Robot();

			for (String s : cut.keys) {
				r.keyPress(keys.get(s));
			}

			Thread.sleep(10);

			for (String s : cut.keys)
				r.keyRelease(keys.get(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setSC(String where, String cmd) {
		int first = -1, second = -1;
		
		// [where] = first -> second
		//  _________|[0]|____|_[1]|
		String[] parts = where.split("->");
		
		switch (parts[0].trim()) {
		case "UP":
			first = Shortcuts.UP;
			break;
		case "DOWN":
			first = Shortcuts.DOWN;
			break;
		case "LEFT":
			first = Shortcuts.LEFT;
			break;
		case "RIGHT":
			first = Shortcuts.RIGHT;
			break;
		}

		switch (parts[1].trim()) {
		case "UP":
			second = Shortcuts.UP;
			break;
		case "DOWN":
			second = Shortcuts.DOWN;
			break;
		case "LEFT":
			second = Shortcuts.LEFT;
			break;
		case "RIGHT":
			second = Shortcuts.RIGHT;
			break;
		}
		
		sc[first][second] = new Shortcut(cmd);
	}

	public static void setTSC(int where, String cmd) {
		tsc[where] = new Shortcut(cmd);
	}

	public static void unpressAll() {
		for (int i = 0; i<4; i++) {
			for (int j = 0; j<4; j++) {
				if (sc[i][j] != null && sc[i][j].isHeld) {
					doSC(i,j);
				}
			}
			if (tsc[i] != null && tsc[i].isHeld) {
				doTSC(i);
			}
		}
		
	}

}
