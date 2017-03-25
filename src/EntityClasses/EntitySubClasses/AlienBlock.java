package EntityClasses.EntitySubClasses;

import java.awt.Graphics;
import java.util.ArrayList;

import EntityClasses.MovableEntity;
import GameClasses.Constants;
import GameClasses.Game;

public final class AlienBlock extends MovableEntity{
	
	/*|-----------------------------------------------------------------------------------
	 |  AUTHOR: Emma
	 |  DATE: 25/03/2017
	 |
	 |-----------------------------------------------------------------------------------
	 |
	 | DESCRIPTION: AlienBlock is a container which houses a number of AlienRows (which themselves
	 |				contain a number of AlienEntities). The Game class interacts with the AlienEntities
	 |				by going through the AlienBlock's public methods:
	 |
	 |				- draw()
	 |				- move()
	 |				- checkForCollision()
	 |
	 |				In this implementation, the AlienEntities themselves do not have move methods. They store
	 |				their X position relative to the X position of the AlienBlock. When the AlienBlock moves,
	 |				the position of the AlienEntities update. The AlienEntity's X position then will always be
	 |					parentAlienBlock.getX() + xOffset;
	 |
	 |				A similar thing happens with AlienRows except with the Y position. AlienRow has a Y of:
	 |					parentAlienBlock.getY() + yOffset
	 |
	 |
	 |				-------------------------------
	 |				|PUBLIC METHODS (called by Game)
	 |				|
	 |				|draw(Graphics g)
	 |				|The draw method works by cycling through each of the AlienRows and calling their draw method
	 |				|These will, in turn, call the draw method on all of the child AlienEntities which they contain
	 |				|
	 |				|move(long delta)
	 |				|The move method checks to see if the block has reached either edge of the screen. If it has,
	 |				|the block's movement direction is reversed and it shifts down. Each AlienRow is then called
	 |				|to update its own X and Y coordinates based on the new location of the block. (Each AlienRow
	 |				|will then call on its child ALienEntities to update their own locations.
	 |				|
	 |				|checkForCollision(ShotEntity shot)
	 |				|This passes the provided shot to each row to see if the shot has collided with an AlienEntity.
	 |				|If it has, the AlienEntity and the ShotEntity are removed.
	 |				|	
	 |				-------------------------------
	 |				-------------------------------
	 |				|PRIVATE METHODS (excluding constructor + helpers)
	 |				|
	 |				|getMinX()/getMaxX()
	 |				|These will get the x position of the alien furthest left/ furthest right respectively
	 |				|
	 |				|reverseXSpeed()
	 |				|Flips current xSpeed around by setting it to -xSpeed
	 |				|
	 |				|shiftBlockDown()
	 |				|Moves the block down (distance of one row) when it reaches the edge of the screen
	 |				|
	 |				|increaseSpeed()
	 |				|When an alien is killed, the overall movement speed of the block increases slightly.
	 |				|The amount that the speed will increase is determined by the ALIEN_SPEED_INCREASE variable
	 |				|in Constants.
	 |				--------------------------------
	 |
	 | KNOWN ISSUES: Occasionally, when the block reaches the edge of the screen, it will fall straight down
	 |				very quickly and without reversing direction until all aliens are off the screen. 
	 |
	 |				Has proven difficult to	recreate reliably but it happens every now and then
	 ------------------------------------------------------------------------------------*/
	
	//Constructor
	public AlienBlock(int x, int y, Game parent, int numRows, int aliensPerRow){
		super(x, y, parent);	//Super on MovableEntity
		createRows(numRows, aliensPerRow);	//Create all of the alienRows (which themselves create Aliens)
		setXSpeed(Constants.ALIEN_START_SPEED);	//Set the starting speed
	}
	
	//Create each of the AlienRows and store them in the rows ArrayList
	private void createRows(int numRows, int aliensPerRow){
		for(int i = 0; i < numRows; i++){
			rows.add(new AlienRow(aliensPerRow, (i * Constants.ROW_OFFSET_Y), this));
		}
	}
	
	
	
	//draw method calls draw on each of the block's child AlienRows
	public void draw(Graphics g){
		
		/*-------------------------------------------------------
		|	METHOD: draw(Graphics g)
		|
		|	PURPOSE: The draw method works by cycling through each of the AlienRows and calling their draw method
	 	|			These will, in turn, call the draw method on all of the child AlienEntities which they contain
		|
		|	PARAMS: Graphics Object, g
		|--------------------------------------------------------*/
		
		for(int i = 0; i < rows.size(); i++){
			rows.get(i).draw(g);
		}
	}
	
	//move method calls draw on each of the block's child AlienRows
	public void move(long delta){
		
		/*-------------------------------------------------------
		|	METHOD: move(long delta)
		|
	 	|	PURPOSE: The move method checks to see if the block has reached either edge of the screen. If it has,
	 	|			 the block's movement direction is reversed and it shifts down. 
	 	|
	 	|			The block will then call the move method supplied by MovableEntity to update its xPosition
	 	|			
	 	|			Each AlienRow is then called to update its own X and Y coordinates based on the 
	 	|			new location of the block. (Each AlienRow will then call on its child ALienEntities 
	 	|			to update their own locations)
		|
		|	PARAMS: delta - the time since the method was last run
		|--------------------------------------------------------*/
		
		//delta is time since last execution
		int pixelsToMove = (int)((getXSpeed() * delta) / 1000);
		
		//Reverse X Location and shift down if block has reached the edge of the screen
		if((getMinX() + pixelsToMove) <= 10 ||
			(getMaxX() + pixelsToMove) >= (Constants.PLAY_AREA_WIDTH - 10)){
			
			reverseXSpeed();
			shiftBlockDown();
		}
		
		//Move the block
		super.move(delta);
		
		//Update the position of the AlienRows and AlienEntities based on the block's new location
		for(int i = 0; i < rows.size(); i++){
			rows.get(i).updatePos();
		}
	}
	
	//Check to see if the supplied ShotEntity has collided with any of the aliens
	public void checkForCollision(ShotEntity shot){
		
		/*-------------------------------------------------------
		|	METHOD: checkForCollision(ShotEntity shot)
		|
		|	PURPOSE: This passes the provided shot to each row to see if the shot has collided with an AlienEntity.
		|			If it has, the speed of the block increases and the shot and alien are removed
		|
		|	PARAMS: The shot which we will be checking for collisions with
		|--------------------------------------------------------*/
		
		for(int i = 0; i < rows.size(); i++){
			if(rows.get(i).checkForCollision(shot)){
				increaseSpeed();
				return;
			}
		}
	}

	
	
	//Flips current xSpeed around by setting it to -xSpeed
	private void reverseXSpeed(){
		setXSpeed(getXSpeed() * -1);	//Reverse Speed
	}
	
	//Moves the block down by ROW_OFFSET_Y
	private void shiftBlockDown(){
		double newY = getY();			//Move all rows down one
		newY += Constants.ROW_OFFSET_Y;
		setY(newY);
	}
		
	//Increase the speed of the block slightly when an alien is destroyed
	private void increaseSpeed(){
		setXSpeed(getXSpeed() * Constants.ALIEN_SPEED_INCREASES);
	}
	
	
	
	//Returns the xPosition of the alien which is furthest left in the block
	private double getMinX(){
		
		/*-------------------------------------------------------
		|	METHOD: getMinX()
		|
		|	PURPOSE: This cycles through each AlienRow to find the alien which is furthest left.
		|			The x position of this alien is returned to the client.
		|
		|			Note that the return value will not always be equal to the block's X if the entire
		|			leftmost column of aliens is destroyed
		|
		|	RETURNS: The X location of the alien furthest left in the block
		|--------------------------------------------------------*/
		
		double minX = Constants.PLAY_AREA_WIDTH; //Set minX to max possible value
		
		for(int i = 0; i < rows.size(); i++){
			double rowMinX = rows.get(i).getMinX();	//For each row, get the X of its leftmost Alien
			
														//If that alien is further left than others 
			minX = (rowMinX < minX) ? rowMinX : minX;	//encountered so far, update minX
		}
		
		return minX;
	}
	
	//Returns the xPosition of the alien which is furthest right in the block
	private double getMaxX(){
		/*-------------------------------------------------------
		|	METHOD: getMaxX()
		|
		|	PURPOSE: This cycles through each AlienRow to find the alien which is furthest right.
		|			The x position of this alien's right side is returned to the client.
		|
		|	RETURNS: The X location of the right side of the alien which is furthest right in the block
		|--------------------------------------------------------*/
		
		double maxX = 0; //maxX is set to the minimum value
		
		for(int i = 0; i < rows.size(); i++){
			double rowMaxX = rows.get(i).getMaxX();	//For each row, get the X of its rightmost Alien
														//If that alien is further right than others 
			maxX = (rowMaxX > maxX) ? rowMaxX : maxX;	//encountered so far, update maxX
		}
		
		return maxX;
	}
	
	
	
	//iVars
	private ArrayList<AlienRow> rows = new ArrayList<>(); //ArrayList containing each of the rows
	
}
