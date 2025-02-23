/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' '){
			Main.D_keyPressed = false;
			Main.A_keyPressed = false;
			Main.S_keyPressed = false;
			Main.W_keyPressed = false;
			Main.currentSpriteIndex = 0;
			return;	
		}
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
			
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
			
		case 'w':
			Main.W_keyPressed = true;
			Main.space_keyPressed =  false;
			break;
			
		case 'a':
			Main.A_keyPressed = true;
			Main.space_keyPressed =  false;
			break;
			
		case 's':
			Main.S_keyPressed = true;
			Main.space_keyPressed =  false;
			break;
			
		case 'd':
			Main.D_keyPressed = true;
			Main.space_keyPressed =  false;
			break;
			
		case '$':
			Main.space_keyPressed =  true;
			if(Main.chatIndex < 3)
				Main.chatIndex++;
			else
				Main.chatIndex = 0;

			break;
		}
	}
}