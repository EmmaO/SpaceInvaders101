package EntityClasses.EntitySubClasses;

import EntityClasses.MovableGraphicEntity;
import GameClasses.Constants;
import GameClasses.Game;

public final class ShipEntity extends MovableGraphicEntity{
	
	/*|-----------------------------------------------------------------------------------
	 |  AUTHOR: Emma
	 |  DATE: 25/03/2017
	 |
	 |-----------------------------------------------------------------------------------
	 |
	 | DESCRIPTION: Ship controlled by the player. Can be moved from left to right using the arrow keys.
	 |				Can fire bullets using the space key.
	 |		
	 |				Mostly inherits from the GraphicMovableEntity class with checks on the move()
	 |				method to ensure the player cannot fly off screen.
	 |
	 |				User input is handled primarily by the parent Game
	 |
	 | KNOWN ISSUES: Currently the ship is not destroyed when in contact with an alien
	 ------------------------------------------------------------------------------------*/

	public ShipEntity(int x, int y, Game parent){
		super(x, y, parent, "/ship.png");
		setXSpeed(200);
	}
	
	public void move(long delta){
		if((getXSpeed() < 0) && (getX() < 10)){		//If the left edge of the screen has been reached
			return;									//Don't move
		} else if ((getXSpeed() > 1) && 			//If right edge has been reached
				(getX() > (Constants.PLAY_AREA_WIDTH - (10 + getWidth())))){
			return;		//don't move
		} else {
			super.move(delta);	//Otherwise move
		}
		
	}

}