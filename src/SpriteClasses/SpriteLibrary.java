package SpriteClasses;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SpriteLibrary {
	
	/*|-----------------------------------------------------------------------------------
	 |  AUTHOR: Emma O'Donnell
	 |  DATE: 25/03/2017
	 |
	 |-----------------------------------------------------------------------------------
	 |
	 | DESCRIPTION: Singleton SpriteLibrary used for retrieving and storing Sprite objects
	 |
	 |			When a sprite is requested, the SpriteLibrary checks to see if it has already
	 |			been loaded in the current session. If it has, a stored copy is returned to the
	 |			the requestor.
	 |
	 |			If it hasn't, a new Sprite is created (with the image loaded from a user supplied URL)
	 |			This new Sprite is then stored in the library and returned to the user.
	 |
	 | INPUT: imageURL is required from the user.
	 |
	 | OUTPUT: Sprite object
	 |
	 ------------------------------------------------------------------------------------*/
	
	public static SpriteLibrary get(){
		return single;
	}
	
	public Sprite getSprite(String ref){
		
		if(spriteMap.containsKey(ref)){				//If the sprite has been loaded before...
			return spriteMap.get(ref);				//Just return that sprite to the client
		} else {									//Otherwise..
			Image image = getImageFromRef(ref);		//Load up a new image
			Sprite sprite = new Sprite(image);		//Create a new sprite with that image
			spriteMap.put(ref, sprite);				//Add the new sprite to the library's HashMap
			return sprite;							//And return the sprite to the user
		}
	}
	
	private Image getImageFromRef(String ref){
		BufferedImage srcImage = null;
		
		//Get the URL for the image specified by ref
		URL url = this.getClass().getResource(ref);
		
		//Load the image into srcImage
		try {
			srcImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Allocate some Accelerated Graphics memory to store our image in
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().
				getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(srcImage.getWidth(), 
				srcImage.getHeight(), Transparency.BITMASK);
		
		//Add the source image onto the new image
		image.getGraphics().drawImage(srcImage, 0, 0, null);
		
		return image;
	}
	
	private static SpriteLibrary single = new SpriteLibrary();;
	
	private HashMap<String, Sprite> spriteMap = new HashMap<String, Sprite>();
}
