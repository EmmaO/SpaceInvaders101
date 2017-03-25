package EntityClasses;

import GameClasses.Game;

public class MovableEntity extends Entity{

	/*|-----------------------------------------------------------------------------------
	 |  AUTHOR: Emma
	 |  DATE: 25/03/2017
	 |
	 |-----------------------------------------------------------------------------------
	 |
	 | DESCRIPTION: Extension of the Entity class offering basic movement functionality to
	 |			clients. This class tracks xSpeed and ySpeed associated with the instance
	 |			and includes methods for getting and setting these values
	 |
	 |			The MovableEntity class also includes a move() method for updating the X
	 |			and Y values of the object in accordance with the object speed and the
	 |			time since the move method was last invoked
	 |
	 |			This only class which currently extends GraphicEntity directly is AlienBlock
	 |			which moves but is not graphically displayed directly (instead acting as a container)
	 |
	 ------------------------------------------------------------------------------------*/

	
	public MovableEntity(int x, int y, Game parent){
		super(x, y, parent);
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
