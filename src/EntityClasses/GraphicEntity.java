package EntityClasses;

import java.awt.Graphics;
import java.awt.Rectangle;

import GameClasses.Game;
import SpriteClasses.Sprite;
import SpriteClasses.SpriteLibrary;

public class GraphicEntity extends Entity{
	
	/*|-----------------------------------------------------------------------------------
	 |  AUTHOR: Emma
	 |  DATE: 25/03/2017
	 |
	 |-----------------------------------------------------------------------------------
	 |
	 | DESCRIPTION: Extension of the Entity class offering basic graphics functionality to
	 |			clients. This class tracks a sprite object associated with the instance
	 |			and includes methods for getting the width and height of that sprite.
	 |
	 |			The GraphicEntity class also includes a draw method for adding the sprite to
	 |			the screen. As well as a getBounds() method which returns a Rectangle object
	 |			to assist with collision detection
	 |
	 |			This only final class which currently extends GraphicEntity is AlienEntity (whose
	 |			movement is handled by its parent AlienBlock). This class also extends to
	 |			MovableGraphicEntity
	 |
	 ------------------------------------------------------------------------------------*/
	
	public GraphicEntity(int x, int y, Game parent, String imageURL){
		
		/*--------------------------------------------------
		//Simple constructor, invokes its super (Entity) and sets the sprite
		//by pulling one from the SpriteLibrary
		---------------------------------------------------*/
		
		super(x, y , parent);
		this.sprite = SpriteLibrary.get().getSprite(imageURL);
	}
	
	//Draw method
	public void draw(Graphics g){
		sprite.draw(g, (int) getX(), (int) getY()); //Simply invokes the draw method of the sprite
	}
	
	//Returns a rectangle object which the client can use to check for collisions
	public Rectangle getBounds(){
		return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
	}
	
	
	//Getters
	//Returns the width of the object's sprite
	public int getWidth(){
		return sprite.getWidth();
	}

	//Returns the height of the object's sprite
	public int getHeight(){
		return sprite.getHeight();
	}
	

	//iVars
	private Sprite sprite;
}
