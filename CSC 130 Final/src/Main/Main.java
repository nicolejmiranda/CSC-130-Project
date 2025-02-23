package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import Data.Box;
import Data.Vector2D;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
		public static stopWatchX timer = new stopWatchX(100);
		
		public static ArrayList<spriteInfo> N_sprites = new ArrayList<>();				// lists to store sprites
		public static ArrayList<spriteInfo> E_sprites = new ArrayList<>();				
		public static ArrayList<spriteInfo> S_sprites = new ArrayList<>();				
		public static ArrayList<spriteInfo> W_sprites = new ArrayList<>();				
		public static int currentSpriteIndex = 0;										// hold the current sprite index in use
		
		public static boolean D_keyPressed = false;										// flags for key pressed
		public static boolean A_keyPressed = false;
		public static boolean S_keyPressed = false;
		public static boolean W_keyPressed = false;
		public static boolean space_keyPressed = false;
		
		public static String lastDir = "E";												// tracks last facing direction to draw idle sprite
		public static Vector2D nicoPos = new Vector2D(120,120);						// initial positions
		public static Vector2D treePos = new Vector2D(400, 160);
		public static Vector2D bearPos = new Vector2D(580, 160);
		public static Vector2D pondPos = new Vector2D(830, 440);
		public static int dxPos = 15;
		
		public static HashMap<String, String> obstacleDescriptions = new HashMap<>(); 	// descriptions for objects		
		public static Box nicoBox; 													// player's collision box
		public static ArrayList<Box> obstaclesBox; 				// list to store all obstacle boxes
		
		public static int chatIndex = 0;
		
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
		
		// Load lines from file into HashMap
		loadDialogue("Descriptions.txt", obstacleDescriptions);			
		
		// intialize player sprites
		for(int i = 0; i < 4; i++){
			N_sprites.add(new spriteInfo(nicoPos, "Nnico" + i));
			E_sprites.add(new spriteInfo(nicoPos, "Enico" + i));
			S_sprites.add(new spriteInfo(nicoPos, "Snico" + i));
			W_sprites.add(new spriteInfo(nicoPos, "Wnico" + i));
		}
		
		// initialize player's box
		nicoBox = new Box(nicoPos, 70, 80);
		
		// initialize obstacle boxes
		obstaclesBox = new ArrayList<>();
	    obstaclesBox.add(new Box(new Vector2D(0, 0), 1280, 20)); 	// border (top)
	    obstaclesBox.add(new Box(new Vector2D(0, 640), 1280, 50)); 	// border (bottom)
	    obstaclesBox.add(new Box(new Vector2D(0, 0), 50, 720)); 	// border (left)
	    obstaclesBox.add(new Box(new Vector2D(1230, 0), 50, 720)); 	// border (right)
	    obstaclesBox.add(new Box(treePos, 98, 85)); 				// honeytree
	    obstaclesBox.add(new Box(pondPos, 275, 110)); 				// pond
	    obstaclesBox.add(new Box(bearPos, 178, 60)); 				// bear
		 

	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)
		
		// draw background and objects
	    ctrl.addSpriteToFrontBuffer(0, 0, "ground");
	    ctrl.addSpriteToFrontBuffer(0, 0, "border");
	    ctrl.addSpriteToFrontBuffer(treePos.getX(), treePos.getY(), "honeytree");	    
	    ctrl.addSpriteToFrontBuffer(bearPos.getX(), bearPos.getY(), "bear");
	    ctrl.addSpriteToFrontBuffer(pondPos.getX(), pondPos.getY(), "pond");

	    // draw the player
	    if(D_keyPressed){	
			ctrl.addSpriteToFrontBuffer(nicoPos.getX(), nicoPos.getY(), E_sprites.get(currentSpriteIndex).getTag());	
		} else if (A_keyPressed){
			ctrl.addSpriteToFrontBuffer(nicoPos.getX(), nicoPos.getY(), W_sprites.get(currentSpriteIndex).getTag());			
		} else if (S_keyPressed){
			ctrl.addSpriteToFrontBuffer(nicoPos.getX(), nicoPos.getY(), S_sprites.get(currentSpriteIndex).getTag());				
		} else if (W_keyPressed){
			ctrl.addSpriteToFrontBuffer(nicoPos.getX(), nicoPos.getY(), N_sprites.get(currentSpriteIndex).getTag());
		} else ctrl.addSpriteToFrontBuffer(nicoPos.getX(), nicoPos.getY(), lastDir + "nico" + 0);
	    
	 // checks interaction with objects
	    Box interactionBox = null;

	    if (lastDir == "E") { // facing east
	        interactionBox = new Box(new Vector2D(nicoPos.getX() + 70, nicoPos.getY()), 50, 80);
	    } else if (lastDir == "W") { // facing west
	        interactionBox = new Box(new Vector2D(nicoPos.getX() - 50, nicoPos.getY()), 50, 80);
	    } else if (lastDir == "S") { // facing south
	        interactionBox = new Box(new Vector2D(nicoPos.getX(), nicoPos.getY() + 80), 70, 50);
	    } else if (lastDir == "N") { // facing north
	        interactionBox = new Box(new Vector2D(nicoPos.getX(), nicoPos.getY() - 50), 70, 50);
	    }
	  
		if (space_keyPressed && interactionBox != null) {	
		    for (int i = 0; i < obstaclesBox.size(); i++) {
		        Box obstacle = obstaclesBox.get(i);
		        
			        if (interactionBox.intersects(obstacle)) {
			            String description = null;
			          
				            // get obstacle description from hashmap
				            if (i == 4) {				           
				          		description = obstacleDescriptions.get("honeytree" + chatIndex);	
				            } else if (i == 5) { 
				                description = obstacleDescriptions.get("pond" + chatIndex);
				            } else if (i == 6) { 
				                description = obstacleDescriptions.get("bear" + chatIndex);
				            }
				
				            // display the description
				            if (description != null) {
				            	ctrl.addSpriteToFrontBuffer(-1, 480, "chatbox");
				                ctrl.drawString(55, 570, description, new Color(255, 255, 255));
				            }
			            
			            break; // stop checking after the first match
			        }
		           
		    }
		}
		
	    // Timer reset
	    if (timer.isTimeUp()) {
	    	// temporary position for collision check
		    Vector2D nextPos = new Vector2D(nicoPos.getX(), nicoPos.getY());
		   
		    // update player's box with the new position
		    Box nextNicoBox = new Box(nextPos, nicoBox.getWidth(), nicoBox.getHeight());
	    	
	    	// Aajust the position when key is pressed
		    if (D_keyPressed) {
		        nextPos.setX(nicoPos.getX() + dxPos);
		        lastDir = "E";
		        currentSpriteIndex = (currentSpriteIndex + 1) % E_sprites.size();
		    } else if (A_keyPressed) {
		        nextPos.setX(nicoPos.getX() - dxPos);
		        lastDir = "W";
		        currentSpriteIndex = (currentSpriteIndex + 1) % W_sprites.size();
		    } else if (S_keyPressed) {
		        nextPos.setY(nicoPos.getY() + dxPos);
		        lastDir = "S";
		        currentSpriteIndex = (currentSpriteIndex + 1) % S_sprites.size();
		    } else if (W_keyPressed) {
		        nextPos.setY(nicoPos.getY() - dxPos);
		        lastDir = "N";
		        currentSpriteIndex = (currentSpriteIndex + 1) % N_sprites.size();
		    }
		    
		    // check for collision
		    boolean collision = false;
		    for (int i = 0; i < obstaclesBox.size(); i++) {
		        Box obstacle = obstaclesBox.get(i); 		// get the current obstacle
		        if (nextNicoBox.intersects(obstacle)) {
		            collision = true; 						// set collision flag to true if objects are touching
		            break; 									// exit loop when a collision is detected
		        }
		    }
		    // apply movement if no collision
		    if (!collision) {
		        nicoPos.setX(nextPos.getX());
		        nicoPos.setY(nextPos.getY());
		    }
	        timer.resetWatch();
	    }
				
	}
	
	// Additional Static methods below...(if needed)
	
	/* Loads lines from text file into a HashMap */
	public static void loadDialogue(String fileName, HashMap<String, String> map){
		EZFileRead ezr = new EZFileRead(fileName);
		for (int i = 0; i < ezr.getNumLines(); i++){
			String raw = ezr.getLine(i);
			StringTokenizer st = new StringTokenizer(raw, "*");
			String Key = st.nextToken(); // Acquires the Key from the raw String
			String Value = st.nextToken(); // Acquires the Value from the raw String
			map.put(Key, Value);
		}
	}
}
