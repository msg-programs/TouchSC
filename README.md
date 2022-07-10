# === Readme - TouchSC v1.1 ===

## == By:  
* msg (msg-programs)

## == What:  
* A program to execute keyboard shortcuts when you only have a touchscreen available,
	like when using a convertible laptop to draw.

## == Needed:  
* Only tested with JDK 13

## == Usage:  
* Running the program will generate a default settings.ini if it doesn't exist already.
* To close, draw circles around the middle.
* Adjust the shortcuts in the settings file. Keep to the formatting present, more info in the file.
* To do a shortcut, tap a quatercircle ("tap") or draw a L-shape ("gesture", self explaining when you look into the settings)


## == Generated files:  
* settings.ini, containing the settings in the same directory as the java runtime.

## == Final notes:
* It doesn't need to be an exact L-shape, and it doesn't have to be in the center. 
	Be warned that a drawing too sloppy may cause the wrong shortcut to be executed.
* You can set a "toggle" command on a gesture, but this won't show an indicator if the key is held or not.
	You can set a "press" command on a tap, but this won't show the rim.
* Close the program using the method mentioned above, else keys toggled on may continue to be pressed for 
	some time without you being able to do anything about it.