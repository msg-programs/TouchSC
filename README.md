# === Readme - TouchSC v1.2 ===

## == By:  
* msg (msg-programs)

## == What:  
* A program to execute keyboard shortcuts with a touch screen.
* Useful when you only have a touchscreen available, like when using a convertible laptop to draw.

## == Needed:  
* Java >=8
* Tested with Java 8 as shipped with the current Minecraft Launcher and JDK 13.

## == Usage:  
* Running the program will generate a default settings.ini if it doesn't exist already.
* Program will then prompt you to take a look at the se-ttings and then close itself.
* Adjust the shortcuts in the settings file. Keep to the formatting present, more info in the file.
* To do a shortcut, tap a quatercircle ("tap") or draw a L-shape ("gesture", self explaining when you look into the settings)
* To close the program, draw circles around the middle.

## == Generated files:  
* settings.ini, containing the settings in the same directory as the java runtime.

## == Final notes:
* It doesn't need to be an exact L-shape, and it doesn't have to be in the center. 
	Be warned that a drawing too sloppy may cause the wrong shortcut to be executed.
* You can set a "toggle" command on a gesture, but this won't show an indicator if the key is held or not.
	You can set a "press" command on a tap, but this won't show the rim.
* Close the program using the method mentioned above, else keys toggled on may continue to be pressed for 
	some time without you being able to do anything about it.
* Program may be very small when using Java 8. To fix, right-click the java.exe 
	and set the high-DPI scaling setting under "Compaibility" to "System".