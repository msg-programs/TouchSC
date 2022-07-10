// File is part of TouchSC (c) 2020 msg-programs, see LICENSE
package main;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;

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
	public static final int STICKY = 2;

	public static HashMap<String, Integer> keys = new HashMap<>();

	public static void init() {

//		sc[UP][LEFT] = new Shortcut("press", "B"); // brush
//		sc[UP][RIGHT] = new Shortcut("press", "E"); // eraser
//		sc[DOWN][LEFT] = new Shortcut("press", "ALT+TAB"); // alt tab
//		sc[DOWN][RIGHT] = new Shortcut("press", "CTRL+D"); // deselect
//		sc[LEFT][UP] = new Shortcut("press", "V"); // select lasso
//		sc[LEFT][DOWN] = new Shortcut("press", "L"); // move
//		sc[RIGHT][UP] = new Shortcut("press", "G"); // color pick
//		sc[RIGHT][DOWN] = new Shortcut("press", "I"); // bucket
//
//		tsc[BOTLEFT] = new Shortcut("toggle", "CTRL");
//		tsc[BOTRIGHT] = new Shortcut("toggle", "ALT");
//		tsc[TOPLEFT] = new Shortcut("toggle", "SHIFT");
//		tsc[TOPRIGHT] = new Shortcut("press", "F3");

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

		System.out.println("Pressing " + Arrays.toString(cut.keys));

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
				System.out.println("Toggeling off " + Arrays.toString(cut.keys));
				for (String s : cut.keys) {
					cut.isHeld = false;
					r.keyRelease(keys.get(s));
				}
			} else {
				System.out.println("Toggeling on " + Arrays.toString(cut.keys));
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
			System.out.println("Pressing " + Arrays.toString(cut.keys));
			Robot r = new Robot();

			for (String s : cut.keys)
				r.keyPress(keys.get(s));

			Thread.sleep(10);

			for (String s : cut.keys)
				r.keyRelease(keys.get(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setSC(String cmd, String... parts) {
		int first = -1, second = -1;
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
		String[] keys = cmd.trim().split(" ");
		sc[first][second] = new Shortcut(keys[0], keys[1]);
	}

	public static void setTSC(String what, String s) {
		String[] keys = s.split(" ");
		switch (what) {
		case "TOPRIGHT":
			tsc[TOPRIGHT] = new Shortcut(keys[0], keys[1]);
			break;
		case "TOPLEFT":
			tsc[TOPLEFT] = new Shortcut(keys[0], keys[1]);
			break;
		case "BOTRIGHT":
			tsc[BOTRIGHT] = new Shortcut(keys[0], keys[1]);
			break;
		case "BOTLEFT":
			tsc[BOTLEFT] = new Shortcut(keys[0], keys[1]);
			break;
		}

	}

}
