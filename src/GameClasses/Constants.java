package GameClasses;

public final class Constants {
	private Constants(){
		
	}
	
	//Game variables
	public static final int PLAY_AREA_WIDTH = 550;
	public static final int PLAY_AREA_HEIGHT = 600;
	public static final int GAME_DELAY = 10; //Number of milliseconds to wait between game loops
	
	//Shot variables
	public static final int SHOT_SPEED = -200;	//Speed shots travel at
	public static final int SHOT_DELAY = 500;	//Time user must wait between shots
	
	//Ship Variables
	public static final int SHIP_Y = 570;
	public static final int SHIP_SPEED = 200;
	
	//Alien Variables
	public static final int ALIEN_START_SPEED = 70;
	public static final double ALIEN_SPEED_INCREASES = 1.05;	//Rate at which alien speed increases when 1 is destroyed
	public static final int ALIEN_OFFSET_X = 30; //Distance between aliens
	public static final int ALIENS_PER_ROW = 10;
	public static final int NUM_ROWS = 4;
	public static final int ROW_OFFSET_Y = 30;	//Distance between rows
}
