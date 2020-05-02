// File is part of TouchSC (c) 2020 msg-programs, see LICENSE
package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shortcut {

	public ArrayList<String> keys;
	public int mode;

	public boolean isHeld = false;

	public Shortcut(String cmd) {

		if (cmd == null || cmd.length() == 0) {
			return;
		}

		// [cmd] = mode keys
		// ________|[0|_|[1|
		String[] parts = cmd.split(" ");
		
		if (parts.length<2) {
			return;
		}

		this.keys = new ArrayList<>(Arrays.asList(parts[1].split("\\+")));
		keys.removeIf(s->s==null || s.length() == 0 || s.equals(" "));
		
		if (keys.size()==0) {
			keys = null;
		}

		switch (parts[0]) {
		case "press":
			mode = Shortcuts.PRESS;
			break;
		case "toggle":
			mode = Shortcuts.TOGGLE;
			break;
		}
	}

	public boolean isDef() {
		return keys != null;
	}

}
