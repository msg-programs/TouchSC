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

public class Settings {

	public static Color bg;
	public static Color rim;
	public static Color detail;

	public static Point pos;
	public static int size;

	public static void init() {
		try (BufferedReader br = new BufferedReader(new FileReader("./settings.ini"))) {
			ArrayList<String> lines = new ArrayList<>();

			String line;
			while ((line = br.readLine()) != null) {
				
				lines.add(line);
			}

			lines.removeIf(s -> s.length() <= 1);
			lines.removeIf(s -> s.startsWith("//"));

			for (String s : lines) {
				if (s.contains("POS:")) {
					int[] nums = parseNums(s, 2);
					if (nums == null)
						continue;
					pos = new Point(nums[0], nums[1]);
					continue;
				}
				if (s.contains("SIZE: ")) {
					int[] nums = parseNums(s, 1);
					if (nums == null)
						continue;
					size = nums[0];
					continue;
				}
				if (s.contains("BG:")) {
					int[] nums = parseNums(s, 3);
					if (nums == null)
						continue;
					bg = new Color(nums[0], nums[1], nums[2]);
					continue;
				} 
				if (s.contains("RIM:")) {
					int[] nums = parseNums(s, 3);
					if (nums == null)
						continue;
					rim = new Color(nums[0], nums[1], nums[2]);
					continue;
				}
				if (s.contains("DETAILS:")) {
					int[] nums = parseNums(s, 3);
					if (nums == null)
						continue;
					detail = new Color(nums[0], nums[1], nums[2]);
					continue;
				}

				if (s.contains("->")) {
					String[] parts = s.split(":");
					Shortcuts.setSC(parts[1], parts[0].split("->"));
					continue;
				}
				
				if (s.contains("None")) {
					s+=" a "; // ugly hack
				}
					
				Shortcuts.setTSC(s.split(": ")[0], s.split(": ")[1]);
			}

		} catch (FileNotFoundException fnfe) {
			createNewInitFile();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void createNewInitFile() {
		try (PrintWriter pw = new PrintWriter("./settings.ini")) {
			pw.println("// ==== Settings ====");
			pw.println();
			pw.println("// Keyboard Shortcuts");
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
			pw.println("// Colors in R, G, B");
			pw.println("BG: 238, 238, 238");
			pw.println("RIM: 0, 0, 0");
			pw.println("DETAILS: 0, 0, 0");
			pw.println();
			pw.println("// Window position in x, y");
			pw.println("POS: 200, 55");
			pw.println();
			pw.println("// Window size");
			pw.println("SIZE: 200");

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		init();

	}

	private static int[] parseNums(String s, int i) {
		int[] res = new int[i];
		try {

			String nums = s.split(": ")[1];
			String[] parts = nums.split(",");

			for (int j = 0; j < i; j++) {
				res[j] = Integer.parseInt(parts[j].trim());
			}

		} catch (Exception e) {
			return null;
		}
		return res;
	}
}
