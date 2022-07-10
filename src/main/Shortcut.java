package main;

public class Shortcut {

	public String[] keys;
	public int mode = 0;

	public boolean isHeld = false;

	public Shortcut(String t, String k) {
		this.keys = k.split("\\+");

		switch (t) {
		case "press":
			mode = Shortcuts.PRESS;
			break;
		case "toggle":
			mode = Shortcuts.TOGGLE;
			break;
		default: 
			keys[0] = "NULL";
			break;
		}
	}
	
	public boolean isSet() {
		return !(keys[0].equals("None"));
	}

}
