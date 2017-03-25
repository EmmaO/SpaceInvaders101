package EntityClasses;

import GameClasses.Game;

public class Entity {
	
	/*|-----------------------------------------------------------------------------------
	 |  AUTHOR: Emma
	 |  DATE: 25/03/2017
	 |
	 |-----------------------------------------------------------------------------------
	 |
	 | DESCRIPTION: Entity = Base Class for any game element, which for Space Invaders includes
	 |			the following:
	 |			
	 |			- AlienBlock
	 |			- AlienRow
	 |			- AlienEntity
	 |			- ShipEntity
	 |			- ShotEntity
	 |
	 |			It offers simple getters and setters for X/Y location for any class which extends it.
	 |			
	 |			In addition, it generates an EntityID for the instance of the class and also tracks the main Game
	 |			object so that entities can call game methods
	 |
	 |			Extends to GraphicEntity, MovableEntity and MovableGraphicEntity
	 ------------------------------------------------------------------------------------*/
	
	public Entity(int x, int y, Game parent){
		
		/*--------------------------------------------------
		//Basic constructor, sets initial x, y values as well as parent value
		//Also creates a new entity ID using an ID generator
		---------------------------------------------------*/
		setX(x);
		setY(y);
		this.parent = parent;
		this.entityId = "ent" + IDGenerator.get().getNextId();
	}
	
	
	//Getters
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public Game getParent(){
		return parent;
	}

	public String getId(){
		return entityId;
	}

	
	//Setters
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	
	//iVars
	private double x, y;
	private Game parent;
	private String entityId;
}
