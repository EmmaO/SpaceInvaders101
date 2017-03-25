package GameClasses;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import EntityClasses.EntitySubClasses.AlienBlock;
import EntityClasses.EntitySubClasses.ShipEntity;
import EntityClasses.EntitySubClasses.ShotEntity;

public class Game extends Canvas{
	
	public Game(){
		/*-------------------------------------------------------
		|		METHOD: Game()	
		|
		|		PURPOSE: Kicks off the game. Graphics window is initialised
		|		and the player ship and aliens are created.
		|
		|		A key listener is then added to handle keyboard input from the player
		|
		|		Finally the game loop is started to begin gameplay
		|--------------------------------------------------------*/
		
		initGraphics();	//Initialises the graphics window and background
		initEntities();	//Initialises the player ship and aliens
		addKeyListener(new KeyInputHandler()); //Set up keyboard listened so that the player can move the ship
		
		lastLoopTime = System.currentTimeMillis(); //Set lastLoopTime to current time
										//If this is not done delta will be very high on first iter of gameLoop
		
		gameLoop();	//Main game loop to handle gameplay
	}
	
	public static void main(String[] args){
		Game g = new Game();	//Creates a new Game object to begin the game
	}
	
	private void initGraphics(){
		
		/*-------------------------------------------------------
		|	METHOD: initGraphics()
		|
		|	PURPOSE: Here we initialise the graphics by creating a new JFrame and adding a JPanel to it
		| 
		|		 	Because we'll be using accelerated graphics, we tell AWT not to deal with repaint
		|			requests (so that we can manually delegate tasks to the graphics card instead of to the CPU
		| 
		| 			We'll also be using a buffer strategy for painting, so we set that up here too
		|--------------------------------------------------------*/

		//Create the game window
		container = new SpcInvJFrame();
			
		//Set up the content pane within which we'll later add sprites
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(Constants.PLAY_AREA_WIDTH, Constants.PLAY_AREA_HEIGHT));
		panel.setLayout(null); //Set up absolute positioning - no layout manager
		
		//Game class extends Canvas,
		//here, we set up canvas bounds and add it into the content of the frame
		this.setBounds(0, 0, Constants.PLAY_AREA_WIDTH, Constants.PLAY_AREA_HEIGHT);
		panel.add(this);
		
		//Using accelerated graphics so we'll tell AWT to ignore repainting
		this.setIgnoreRepaint(true);
		
		//Set up the container
		container.pack();
		
		//Create a buffer strategy to handle accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}
	
	private void initEntities(){
		
		/*-------------------------------------------------------
		|	METHOD: initEntities()
		|
		|	PURPOSE: Adds the player ship and aliens to the screen
		|--------------------------------------------------------*/
		
		//Create player ship, roughly centred
		ship = new ShipEntity((Constants.PLAY_AREA_WIDTH / 2), Constants.SHIP_Y, this);
		
		//Create aliens		
		alienBlock = new AlienBlock(30, 40, this, Constants.NUM_ROWS, Constants.ALIENS_PER_ROW);
	}
	
	private void gameLoop(){
		
		/*-------------------------------------------------------
		|	METHOD: gameLoop()
		|
		|	PURPOSE: This loop runs continuously for gameplay to occur.
		|
		|	ALGORITHM: The time since the loop was last run is calculated (delta). This is used
		|			in the actionEntities method for calculating how far entities should move
		|			((movementSpeed * delta) / 1000)
		|
		|			actionEntities handles the movement of all entities as well as shooting actions
		|
		|			After all entities have been actioned, the display is updated and the
		|			loop is slept for a few milliseconds before restarting
		|
		|	KNOWN ISSUES: No game end currently. Loop runs continuously.
		|--------------------------------------------------------*/
		
		while(true){
			long delta = System.currentTimeMillis() - lastLoopTime; //delta is time since last loop was run
			lastLoopTime = System.currentTimeMillis();	//update lastLoopTime
			
			actionEntities(delta);	//perform actions on all entities (movement and shooting actions)
			updateGraphics();	//update display after all entities have been actioned
				
			try {Thread.sleep(Constants.GAME_DELAY);} catch (Exception e){}	//Wait before running loop again
		}
	}
	
	private void actionEntities(long delta){
		
		/*-------------------------------------------------------
		|	METHOD: actionEntities(long delta)
		|
		|	PURPOSE: moves all entities and attempts to shoot.
		---------------------------------------------------*/
		
		moveShip(delta);
		tryToShoot();		
		moveShots(delta);
		moveAliens(delta);
	}
	
	private void moveShip(long delta){
		/*-------------------------------------------------------
		|	METHOD: moveShip(long delta)
		|
		|	PURPOSE: First set the ship's speed.
		|			(SHIP_SPEED * 1 if right key is pressed,
		|			 SHIP_SPEED * -1 if left key is pressed,
		|			 0 if neither are pressed)
		|
		|			Then call the ship's move method
		|--------------------------------------------------------*/
		
		if(leftPressed){
			ship.setXSpeed(-Constants.SHIP_SPEED);
		} else if (rightPressed){
			ship.setXSpeed(Constants.SHIP_SPEED);
		} else {
			ship.setXSpeed(0);
		}
		ship.move(delta);
	}
	
	private void tryToShoot(){
		
		/*-------------------------------------------------------
		|	METHOD: tryToShoot()
		|
		|	PURPOSE: If the player is trying to shoot (ie holding space)
		|		and enough time has passed since the last shot, a new 
		|		ShotEntity will be created and added to the shots HashMap
		|--------------------------------------------------------*/
		
		if(tryingToShoot){	//If player is pressing space
			if(System.currentTimeMillis() - lastShotTime > Constants.SHOT_DELAY){	//If enough time has passed
				lastShotTime = System.currentTimeMillis();	//Update time since last shot
				
				//Create a new shot
				ShotEntity newShot = new ShotEntity((int) (ship.getX() + (0.5 * ship.getWidth())),
													(int) (ship.getY()),
													this);
				
				//Add the new shot to the shots HashMap
				shots.put(newShot.getId(), newShot);
			}
		}
	}
	
	private void moveShots(long delta){
		/*-------------------------------------------------------
		|	METHOD: moveShots(long delta)
		|
		|	PURPOSE: Calls the move method on each ShotEntity within the shots HashMap.
		|		After a shot has been moved, it is passed to the AlienBlock object to see
		|		if it has collided with an AlienEntity.
		|
		|		If the shot has collided with an alien or has reached the top of the screen
		|		it will be queued for removal.
		|
		|		All shots queued for removal will be deleted in the removeShots() method
		|		which is called after all shots have moved.
		|-------------------------------------------------------*/
		
		for (String key: shots.keySet()){					//For each shot
			shots.get(key).move(delta);						//Move it
			alienBlock.checkForCollision(shots.get(key));	//See if it's collided with an alien
		}
		
		removeShots();	//Remove all shots queued for removal
	}
	
	private void moveAliens(long delta){

		/*-------------------------------------------------------
		|	METHOD: moveAliens(long delta)
		|
		|	PURPOSE: In the current implementation, this just calls the alienBlock's move() method
		|--------------------------------------------------------*/
		
		alienBlock.move(delta);
	}
	
	public void queueShotForRemoval(String shotId){
		
		/*-------------------------------------------------------
		|	METHOD: queueShotForRemoval(String shotId)
		|
		|	PURPOSE: This is actually called by child objects (either the shot itself or an
		|			AlienEntity that the shot has collided with). It adds the relevant shot
		|			to a Queue containing the ids of each shot to be removed from the shots
		|			HashMap.
		|
		|			All queued shots are removed when the removeShots() method is called
		|--------------------------------------------------------*/
		
		shotsForRemoval.add(shotId);
	}
	
	private void removeShots(){
		
		/*-------------------------------------------------------
		|	METHOD: removeShots()
		|
		|	PURPOSE: Removes all shots specified by the shotsForRemoval Queue
		|		from the shots HashMap
		|--------------------------------------------------------*/
		
		while (shotsForRemoval.isEmpty() == false){
			shots.remove(shotsForRemoval.remove());
		}

	}
	
	private void updateGraphics (){
		
		/*-------------------------------------------------------
		|	METHOD: updateGraphics()
		|
		|	PURPOSE: Called by gameLoop() to update the graphics of the
		|			game. A black background is added before each of the entities is drawn
		|--------------------------------------------------------*/
		
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		
		//Draw a black background onto the canvas
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Constants.PLAY_AREA_WIDTH, Constants.PLAY_AREA_HEIGHT);
		
		//Draw entities
		ship.draw(g);		//draw the ship
		alienBlock.draw(g);	//draw the aliens
		for (String key: shots.keySet()){
			shots.get(key).draw(g);	//draw all of the shots
		}
		
		//When all drawing is done, clear up graphics
		//and flip the buffer over.
		g.dispose();
		strategy.show();
	}
	
	private class KeyInputHandler extends KeyAdapter{
		
		/*-------------------------------------------------------
		|	CLASS: KeyInputHandler
		|
		|	PURPOSE: KeyInputHandler for listening out for user key presses.
		|
		|		Specifically listens for Left Arrow Key, Right Arrow Key and Space bar
		|--------------------------------------------------------*/
		
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
			if(e.getKeyCode() == KeyEvent.VK_SPACE) tryingToShoot = true;
		}
		
		public void keyReleased(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
			if(e.getKeyCode() == KeyEvent.VK_SPACE) tryingToShoot = false;
		}
		
	}
	
	
	//iVars
	private BufferStrategy strategy; //Buffer strategy for graphics
	private SpcInvJFrame container;	 //Space Invader JFrame (standard JFrame with properties set)
	
	private long lastLoopTime; //Time at which last game loop was run in ms
	private long lastShotTime = 0; //Time at which the player last fired in ms
	
	private AlienBlock alienBlock; //Main block of aliens
	
	private ShipEntity ship; //player ship
	
	private HashMap<String, ShotEntity> shots = new HashMap<>();
	private Queue<String> shotsForRemoval = new LinkedList<>();
	
	private boolean leftPressed = false;  //boolean shows whether left arrow key is pressed
	private boolean rightPressed = false;	//boolean shows whether right arrow key is pressed
	private boolean tryingToShoot = false;	//boolean shows whether space is pressed (player trying to shoot)
}
