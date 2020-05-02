// File is part of TouchSC (c) 2020 msg-programs, see LICENSE
package main;

public class Shortcut {

	public String[] keys;
	public int mode = 0;

	public boolean isHeld = false;

	public Shortcut(String mod, String keyRaw) {
		this.keys = keyRaw.split("\\+");

		switch (mod) {
		case "press":
			mode = Shortcuts.PRESS;
			break;
		case "toggle":
			mode = Shortcuts.TOGGLE;
			break;
		default: 
			keys[0] = "None";
			break;
		}
	}
	
	public boolean isDef() {
		return !(keys[0].equals("None"));
	}

}
