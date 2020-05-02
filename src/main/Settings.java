// File is part of TouchSC (c) 2020 msg-programs, see LICENSE
package main;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Settings {

	public static Color bg = new Color(238,238,238);
	public static Color rim = new Color(0,0,0);
	public static Color detail = new Color(0,0,0);;

	public static Point pos = new Point(100,100);
	public static int size = 200;

	public static void init() {
		ArrayList<String> lines = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("./settings.ini"))) {

			String line;
			while ((line = br.readLine()) != null) {

				lines.add(line);
			}

		} catch (FileNotFoundException fnfe) {
			createNewInitFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		lines.removeIf(s -> s.length() <= 1);
		lines.removeIf(s -> s.startsWith("//"));

		HashMap<String, String> settingMap = new HashMap<>();

		for (String s : lines) {
			String[] pair = s.split(":");

			// s = keystr: value
			// ___ |_[0]|__|[1]|
			settingMap.put(pair[0].trim(), pair[1].trim());
		}

		lines.clear();

		int[] nums = null;

		nums = parseNums(settingMap.get("POS"));
		if (nums != null) {
			pos = new Point(nums[0], nums[1]);
		}
		settingMap.remove("POS");

		nums = parseNums(settingMap.get("SIZE"));
		if (nums != null) {
			size = nums[0];
		}
		settingMap.remove("SIZE");

		nums = parseNums(settingMap.get("BG"));
		if (nums != null) {
			bg = new Color(nums[0], nums[1], nums[2]);
		}
		settingMap.remove("BG");

		nums = parseNums(settingMap.get("RIM"));
		if (nums != null) {
			rim = new Color(nums[0], nums[1], nums[2]);
		}
		settingMap.remove("RIM");

		nums = parseNums(settingMap.get("DETAILS"));
		if (nums != null) {
			detail = new Color(nums[0], nums[1], nums[2]);
		}	
		settingMap.remove("DETAILS");

		String cmd = settingMap.get("BOTLEFT");
		Shortcuts.setTSC(Shortcuts.BOTLEFT, cmd);
		settingMap.remove("BOTLEFT");

		cmd = settingMap.get("BOTRIGHT");
		Shortcuts.setTSC(Shortcuts.BOTRIGHT, cmd);
		settingMap.remove("BOTRIGHT");

		cmd = settingMap.get("TOPLEFT");
		Shortcuts.setTSC(Shortcuts.TOPLEFT, cmd);
		settingMap.remove("TOPLEFT");

		cmd = settingMap.get("TOPRIGHT");
		Shortcuts.setTSC(Shortcuts.TOPRIGHT, cmd);
		settingMap.remove("TOPRIGHT");

		for (String key : settingMap.keySet()) {
			if (key.contains("->")) {

				// [settingMap] = where -> where: mode keys
				// _______________|_____key____|__|__val__|
				Shortcuts.setSC(key, settingMap.get(key));
			}
		}

	}

	private static void createNewInitFile() {
		try (PrintWriter pw = new PrintWriter("./settings.ini")) {
			pw.println("// ==== Settings ====");
			pw.println();
			pw.println("// Possible keys: A-Z, ALT, CTRL or CMD (latter untested), SHIFT, F1-F24, NUM_0-NUM_9");
			pw.println("// press shortly taps the shortcut, while toggle holds it until invoked again.");
			pw.println("// \"None\" indicates an empty shortcut");
			pw.println();
			pw.println("// Gestures: (swipe 1 -> 2)");
			pw.println("UP -> LEFT: press B");
			pw.println("UP -> RIGHT: press E");
			pw.println("DOWN -> LEFT: press ALT+TAB");
			pw.println("DOWN -> RIGHT: press CTRL+D");
			pw.println("LEFT -> UP: press V");
			pw.println("LEFT -> DOWN: press L");
			pw.println("RIGHT -> UP: press G");
			pw.println("RIGHT -> DOWN: press I");
			pw.println();
			pw.println("// Taps: (tap quater)");
			pw.println("BOTLEFT: toggle CTRL");
			pw.println("BOTRIGHT: toggle ALT");
			pw.println("TOPLEFT: toggle SHIFT");
			pw.println("TOPRIGHT: press F3");
			pw.println();
			pw.println("// Colors in R, G, B:");
			pw.println("// The filled circle");
			pw.println("BG: 238, 238, 238");
			pw.println("// The rim that shows when a tap is held");
			pw.println("RIM: 0, 0, 0");
			pw.println("// The markings on the circle");
			pw.println("DETAILS: 0, 0, 0");
			pw.println();
			pw.println("// Window position in x, y:");
			pw.println("POS: 200, 55");
			pw.println();
			pw.println("// Window size:");
			pw.println("SIZE: 200");

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		init();

	}

	private static int[] parseNums(String instr) {
		int[] res;
		try {
			String[] parts = instr.split(",");
			res = new int[parts.length];

			for (int i = 0; i < res.length; i++) {
				res[i] = Integer.parseInt(parts[i].trim());
			}

		} catch (Exception e) {
			return null;
		}

		return res;
	}
}
