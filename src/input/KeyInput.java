package input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import window.Game;

public class KeyInput extends KeyAdapter{
	
	private Game game;
	
	public KeyInput(Game game){
		this.game = game;
	}
	
	
	public void keyPressed(KeyEvent e){
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D && !game.isLeft()){
			game.setRight(true);
			game.setUp(false);
			game.setDown(false);
		}
		if(key == KeyEvent.VK_A && !game.isRight()){
			game.setLeft(true);
			game.setUp(false);
			game.setDown(false);
		}
		if(key == KeyEvent.VK_W && !game.isDown()){
			game.setUp(true);
			game.setLeft(false);
			game.setRight(false);
		}
		if(key == KeyEvent.VK_S && !game.isUp()){
			game.setDown(true);
			game.setRight(false);
			game.setLeft(false);
		}
		
	
		
	}
	public void keyReleased(KeyEvent e){
		

		
	}

}
