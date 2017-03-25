package EntityClasses.EntitySubClasses;

import EntityClasses.MovableGraphicEntity;
import GameClasses.Constants;
import GameClasses.Game;

public final class ShotEntity extends MovableGraphicEntity{
	
	/*|-----------------------------------------------------------------------------------
	 |  AUTHOR: Emma
	 |  DATE: 25/03/2017
	 |
	 |-----------------------------------------------------------------------------------
	 |
	 | DESCRIPTION: ShotEntity is the object type for bullets fired by the player ship.
	 |			Shots are fired from the centre of the playerShip and travel towards the top of the screen.
	 |			
	 |			If a shot hits the top of the screen it is removed from the game.
	 |
	 |			If a shot hits an AlienEntity, the shot is removed from the game and the alien is destroyed
	 |
	 |			Most useful methods inherited from MovableGraphicEntity
	 ------------------------------------------------------------------------------------*/
	
	//Constructor
	public ShotEntity(int x, int y, Game parent){
		super(x, y, parent,"/shot.png");
		setYSpeed(Constants.SHOT_SPEED);
	}
	
	public void move(long delta){
		//If top of the screen is reached, queue the shot for removal
		if(getY() <= 0){
			getParent().queueShotForRemoval(getId());
		}
		super.move(delta); //Then move the shot
	}
}
