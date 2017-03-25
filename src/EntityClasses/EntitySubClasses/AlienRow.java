package EntityClasses.EntitySubClasses;

import java.awt.Graphics;
import java.util.HashMap;

import EntityClasses.Entity;
import GameClasses.Constants;

public final class AlienRow extends Entity{
	/*|-----------------------------------------------------------------------------------
	 |  AUTHOR: Emma
	 |  DATE: 25/03/2017
	 |
	 |-----------------------------------------------------------------------------------
	 |
	 | DESCRIPTION: AlienRow is a middleman between the parent AlienBlock and child AlienEntity class
	 |			A block has many rows and a row has many aliens.
	 |
	 |				|-------------------------------
	 |				|PUBLIC METHODS
	 |				|
	 |				|draw(Graphics g)
	 |				|The draw method works by cycling through each of the child AlienEntities and 
	 |				|calling their draw method
	 |				|
	 |				|updatePos()
	 |				|Gets updated values from the parentBlock and sets x and y accordingly. Then calls
	 |				|updatePos() on each of the child AlienEntities
	 |				|
	 |				|checkForCollision(ShotEntity shot)
	 |				|This passes the provided shot to each AlienEntity to see if they have collided.
	 |				|If it has, the AlienEntity and the ShotEntity are removed.
	 |				|
	 |				|removeAlien(String alienID)
	 |				|Removes the alien specified by alienID from the aliens HashMap
	 |				|
	 |				|getMinX()/getMaxX()
	 |				|These will get the x position of the alien furthest left/ furthest right respectively
	 |				|
	 |				|getParentBlock()
	 |				|Returns the parent AlienBlock
	 |				-------------------------------
	 ------------------------------------------------------------------------------------*/
	
	public AlienRow(int aliensPerRow, int yOffSet, AlienBlock parentBlock){
		super(	(int)parentBlock.getX(),				//x Value
				(int)(parentBlock.getY() + yOffSet),	//y Value
				parentBlock.getParent());				//Game parent
		
		this.yOffSet = yOffSet;
		this.parentBlock = parentBlock;
		createAliens(aliensPerRow);
	}
	
	private void createAliens(int aliensPerRow){
		
		/*-------------------------------------------------------
		|	METHOD: createAliens(int aliensPerRow)
		|
		|	PURPOSE: Creates a set of new aliens and adds them to the aliens HashMap
		|			Each alien shares a Y value with the parentRow but is Offset from the row X
		|--------------------------------------------------------*/
		//
		
		for (int i = 0; i < aliensPerRow; i++){
			AlienEntity newAlien = new AlienEntity(
 				(i * Constants.ALIEN_OFFSET_X),
				this);
			
			aliens.put(newAlien.getId(), newAlien);
		}
	}
	
	public void draw(Graphics g){
		/*-------------------------------------------------------
		|	METHOD: draw(Graphics g)
		|
		|	PURPOSE: The draw method works by cycling through each of the child AlienEntities and 
		|			calling their draw method
		|
		|	PARAMS: Graphics Object, g
		|--------------------------------------------------------*/
		
		for(String key : aliens.keySet()){
			aliens.get(key).draw(g);
		}
	}
	
	public void updatePos(){
		/*-------------------------------------------------------
		|	METHOD: updatePos()
		|
		|	PURPOSE: The xLocation of the AlienRow is always set to:
	 	|				parentBlock.getX();
	 	|
	 	|			The yLocation of AlienEntity is always set to:
	 	|				parentBlock.getY() + yOffSet;
	 	|				
	 	|			updatePos() just gets updated values from the parentBlock and sets x and y
	 	|			accordingly. Then calls the updatePos on each of the child AlienEntities
		|--------------------------------------------------------*/
		
		setX(parentBlock.getX());
		setY(parentBlock.getY() + yOffSet);
		for(String key : aliens.keySet()){
			aliens.get(key).updatePos();
		}
	}
	
	public boolean checkForCollision(ShotEntity shot){
		/*-------------------------------------------------------
		|	METHOD: checkForCollision(ShotEntity shot)
		|
		|	PURPOSE: This passes the provided shot to each AlienEntity to see if they have collided.
		|			If any collisions are detected this returns true, otherwise false.
		|
		|			This method will be called by the parent AlienBlock
		|
		|	PARAMS: The shot which we will be checking for collisions with
		|
		|	OUTPUT: true if collision is detected otherwise false
		|--------------------------------------------------------*/

		
		for(String key : aliens.keySet()){
			if(aliens.get(key).checkForCollision(shot)){
				return true;
			}
		}
		return false;
	}
	
	public void removeAlien(String alienID){
		/*-------------------------------------------------------
		|	METHOD: removeAlien(String alienID)
		|
		|	PURPOSE: Removes the specified alien from the aliens HashMap. This method will
		|			be called by a child AlienEntity
		|--------------------------------------------------------*/
		
		aliens.remove(alienID);
	}
	
	//Getters
	public double getMinX(){
		/*-------------------------------------------------------
		|	METHOD: getMinX()
		|
		|	PURPOSE: This cycles through each AlienEntity to find the alien which is furthest left.
		|			The x position of this alien is returned to the client.
		|
		|			Note that the return value will not always be equal to the row's X if the entire
		|			leftmost column of aliens is destroyed
		|
		|	RETURNS: The X location of the alien furthest left in the row
		|--------------------------------------------------------*/
		
		double min = Constants.PLAY_AREA_WIDTH;
		for(String key : aliens.keySet()){
			double thisAlienX = aliens.get(key).getX();
			if (thisAlienX < min) min = thisAlienX;
		}
		
		return min;
	}
	
	public double getMaxX(){
		/*-------------------------------------------------------
		|	METHOD: getMaxX()
		|
		|	PURPOSE: This cycles through each child AlienEntity to find the alien which is furthest right.
		|			The x position of this alien's right side is returned to the client.
		|
		|	RETURNS: The X location of the right side of the alien which is furthest right in the row
		|--------------------------------------------------------*/
		
		double max = 0;
		for(String key : aliens.keySet()){
			double thisAlienX = (aliens.get(key).getX()) + (aliens.get(key).getWidth());
			if (thisAlienX > max) max = thisAlienX;
		}
		
		return max;
	}
	
	public AlienBlock getParentBlock(){
		return parentBlock;
	}
	
	
	private int yOffSet;	//Number of pixels AlienRow y should be offset from parent AlienBlock y
	private AlienBlock parentBlock;	//AlienBlock which is the parent of this row
	private HashMap<String, AlienEntity> aliens = new HashMap<>();	//HashMap containing all child AlienEntities
}
