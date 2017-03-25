package SpriteClasses;

import java.awt.Graphics;
import java.awt.Image;


/*|-----------------------------------------------------------------------------------
|  AUTHOR: Emma
|  DATE: 25/03/2017
|
|-----------------------------------------------------------------------------------
|
| DESCRIPTION: Basically just a wrapper class for an image.
|
|			Contains a constructor to set the image, getters for width and height
|			and a draw method for adding the image onto the specified Graphics object
------------------------------------------------------------------------------------*/



public class Sprite {
	
	//Constructor
	public Sprite(Image image){
		this.image = image;
	}

	//Draws the loaded image onto the specified Graphics object at x, y
	public void draw(Graphics g, int x, int y){
		g.drawImage(image, x, y, null);
	}
	
	
	//Getters
	public int getWidth(){
		return image.getWidth(null);
	}

	public int getHeight(){
		return image.getHeight(null);
	}
	
	private Image image;
}
