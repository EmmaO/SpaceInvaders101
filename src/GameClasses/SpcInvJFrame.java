package GameClasses;

import javax.swing.JFrame;

public class SpcInvJFrame extends JFrame{
	
	/*|-----------------------------------------------------------------------------------
	 |  AUTHOR: Emma
	 |  DATE: 25/03/2017
	 |
	 |-----------------------------------------------------------------------------------
	 |
	 | DESCRIPTION: Simple JFrame with preconfigured properties. Mostly used for keeping the code tidier in the
	 |			Game class.
	 |
	 ------------------------------------------------------------------------------------*/
	
	public SpcInvJFrame(){
		this.setTitle("Space Invaders 101");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
}
