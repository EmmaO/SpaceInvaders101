package EntityClasses;

import GameClasses.Game;

public class MovableGraphicEntity extends GraphicEntity{
	
	/*|-----------------------------------------------------------------------------------
	 |  AUTHOR: Emma
	 |  DATE: 25/03/2017
	 |
	 |-----------------------------------------------------------------------------------
	 |
	 | DESCRIPTION: Adds basic movement capability to graphic entities. Non-ideal way of
	 |				doing things because we are repeating code from MovableEntity here.
	 |			
	 |				This class tracks xSpeed and ySpeed associated with the instance
	 |				and includes methods for getting and setting these values
	 |
	 |				The MovableGraphicEntity class also includes a move() method for updating the X
	 |				and Y values of the object in accordance with the object speed and the
	 |				time since the move method was last invoked
	 |
	 |				ShotEntity and ShipEntity are direct extensions of this class
	 |
	 | KNOWN ISSUES: It should not be
	 ------------------------------------------------------------------------------------*/
	
	public MovableGraphicEntity(int x, int y, Game parent, String imageURL){
		super(x, y, parent, imageURL);
	}
	
public void move(long delta){
		
		//delta is the time since the last loop was run
		
		//Update X
		double newX = getX();
		newX += ((xSpeed * delta) / 1000);
		setX(newX);
		
		//Update Y
		double newY = getY();
		newY += ((ySpeed * delta) / 1000);
		setY(newY);
		
	}
	
	
	//Getters
	public double getXSpeed(){
		return xSpeed;
	}
	
	public double getYSpeed(){
		return ySpeed;
	}
	
	
	//Setters
	public void setXSpeed(double xSpeed){
		this.xSpeed = xSpeed;
	}
	
	public void setYSpeed(double ySpeed){
		this.ySpeed = ySpeed;
	}
	
	
	//iVars
	private double xSpeed, ySpeed; //In pixels per second
	
}
