package EntityClasses.EntitySubClasses;

import EntityClasses.GraphicEntity;

public final class AlienEntity extends GraphicEntity{

	/*|-----------------------------------------------------------------------------------
	 |  AUTHOR: Emma
	 |  DATE: 25/03/2017
	 |
	 |-----------------------------------------------------------------------------------
	 |
	 | DESCRIPTION: AlienEntity is the graphic representation of aliens in the SpaceInvaders game.
	 |				Movement of the class is handled primarily by the parent AlienBlock, but this
	 |				class is capable of detecting whether or not it has collided with a ShotEntity
	 |				provided by the client using the checkForCollision method.
	 |				
	 |				---------------------------
	 |				|PUBLIC METHODS
	 |				|updatePos()
	 |				|The xLocation of the AlienEntity is always set to:
	 |				|	xOffsetFromRow + parentRow.getX();
	 |				|The yLocation of AlienEntity is always set to:
	 |				|	parentRow.getY();
	 |				|
	 |				|updatePos() just gets updated values from the two parents and sets x and y
	 |				|accordingly.
	 |				|
	 |				|---
	 |				|checkForCollision(ShotEntity shot)
	 |				|This just checks to see whether the AlienEntity is currently intersecting the supplied
	 |				|ShotEntity. If it is, the shot is queued for removal and the alien is destroyed
	 |				----------------------------
	 |
	 ------------------------------------------------------------------------------------*/
	
	public AlienEntity(int xOffset, AlienRow parentRow){
		super(	(int) parentRow.getX() + xOffset, //set X
				(int) parentRow.getY(), 		  //set Y
				parentRow.getParent(),            //set parent (Game)
				"/alien.png");					  //URL for sprite image
		
		this.parentRow = parentRow;
		this.xOffsetFromRow = xOffset;
	}
	
	//Updates the location of the instance based on location of parent AlienBlock and AlienRow
	public void updatePos(){
		
		/*-------------------------------------------------------
		|	METHOD: updatePos()
		|
		|	PURPOSE: The xLocation of the AlienEntity is always set to:
	 	|				xOffsetFromRow + parentRow.getX();
	 	|
	 	|			The yLocation of AlienEntity is always set to:
	 	|				parentRow.getY();
	 	|				
	 	|			updatePos() just gets updated values from the two parents and sets x and y
	 	|			accordingly.
		|--------------------------------------------------------*/
		
		setX(parentRow.getX() + xOffsetFromRow); //UpdateX 
		setY(parentRow.getY());					 //UpdateY
	}
	
	//Checks to see whether the alien has collided with the supplied ShotEntity
	public boolean checkForCollision(ShotEntity shot){
		
		/*-------------------------------------------------------
		|	METHOD: checkForCollision(ShotEntity shot)
		|
		|	PURPOSE: This just checks to see whether the AlienEntity is currently intersecting the supplied
	 	|			ShotEntity. If it is, the shot is queued for removal and the alien is destroyed
		|
		|	ALGORITHM: ShotEntity and AlienEntity have a getBounds() method by virtue of being
		|		extensions of the GraphicEntity class. This returns a Rectangle object to show the
		|		bounds of the Sprite. If the Rectangle supplied by the shot intersects the rectangle
		|		supplied by the alien, a collision is detected.	
		|
		|	PARAMS: ShotEntity shot - we check to see whether the supplied shot is intersecting the alien
		|
		|	RETURNS: true if collision is detected, false if not
		|--------------------------------------------------------*/
		
		if(shot.getBounds().intersects(this.getBounds())){
			getParent().queueShotForRemoval(shot.getId()); //Remove the shot
			parentRow.removeAlien(this.getId());		   //Remove the alien
			return true;	//Return true to confirm collision detected
		}
		
		return false;		//Return false if no collision detected
	}
	
	
	//iVars
	private AlienRow parentRow;
	private int xOffsetFromRow; //Number of pixels AlienEntity x should be offset from x of parentRow
}
