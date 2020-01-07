package userInput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entities.Entity;
import game.Game;
import game.Handler;
import game.State;
import id.ID;
import player.Mage;
import player.Player;
import running.Main;

public class KeyAction implements KeyListener
{
	private Handler handler;
	private boolean[]keyDown;
	private Game game;
	public KeyAction(Handler handler,Game game)
	{
		this.handler = handler;
		this.game = game;
		keyDown = new boolean[8];
		for(int i =0; i< keyDown.length;i++) {keyDown[i]=false;}
	}
	
	/*key methods*/
	public void keyPressed(KeyEvent e)
	{
		/*you can only use the keyboard if you are currently playing*/
		if(Game.gameState == State.Playing)
		{
			for(int i =0; i < handler.entity.size();i++)
			{
				Entity ent = handler.entity.get(i);
				if(ent.getId() == ID.Player)
				{
					Player player = (Player)ent;
				if(e.getKeyCode() == KeyEvent.VK_W)
				{
					keyDown[0] = true;
				}
				else if(e.getKeyCode() == KeyEvent.VK_D)
				{
					keyDown[1] = true;
					keyDown[3] = false;
					player.setMovingX(true);
					player.setFacing(Main.RIGHT);
				}
				else if(e.getKeyCode() == KeyEvent.VK_S)
				{
					keyDown[2] = true;
				}
				else if(e.getKeyCode() == KeyEvent.VK_A)
				{
					keyDown[3] = true;
					keyDown[1] = false;
					player.setMovingX(true);
					player.setFacing(Main.LEFT);
				}
				/*attack 1*/
				else if(e.getKeyCode() == KeyEvent.VK_K)
				{
				if(player.isAttacking1() == false && player.isAttacking2() == false) {player.setAttacking1(true);}
				}
				/*attack 2*/
				else if(e.getKeyCode() == KeyEvent.VK_L)
				{
					if(player.isAttacking1() == false && player.isAttacking2() == false) {player.setAttacking2(true);}
				}
				else if(e.getKeyCode() == KeyEvent.VK_SPACE) 
				{
					player.jump();
				}
				
				else if(e.getKeyCode() == KeyEvent.VK_SHIFT) 
				{
					keyDown[5] = true;
					if(!(player instanceof Mage))
					{
					player.setRunSpeed((float) (player.getV1()/2.3));
					}
					else {
						Mage mage = (Mage)player;
						mage.setFloating(true);
					}
				}
				
				else if(e.getKeyCode() == KeyEvent.VK_ESCAPE ){
					for(int key =0;key< keyDown.length;key++) {keyDown[key] = false;}
					Game.gameState = State.Paused;
					if(game.isMusicOn()) {game.pauseSong();}
					
					}
				}
				
			}//end of entity for loop	
		}//the end of playing state.
		else if(Game.gameState == State.Paused)
		{
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				Game.gameState = State.Playing;
				if(game.isMusicOn()) {game.playSong();}
			}
		}
		e.consume();
	}
	public void keyReleased(KeyEvent e)
	{
		/*you can only use the keyboard if you are currently playing*/
		if(Game.gameState == State.Playing)
		{
			for(int i =0; i < handler.entity.size();i++)
			{
				Entity ent = handler.entity.get(i);
				if(ent.getId() == ID.Player)
				{
					Player player = (Player)ent;
					if(e.getKeyCode() == KeyEvent.VK_W)
					{
						keyDown[0] = false;
					}
					else if(e.getKeyCode() == KeyEvent.VK_D)
					{
						keyDown[1] = false;
					}
					else if(e.getKeyCode() == KeyEvent.VK_S)
					{
						keyDown[2] = false;
					}
					else if(e.getKeyCode() == KeyEvent.VK_A)
					{
						keyDown[3] = false;
					}
					else if(e.getKeyCode() == KeyEvent.VK_SHIFT)
					{
						keyDown[5] = false;
						if(player instanceof Mage)
						{
							Mage mage = (Mage)player;
							mage.setFloating(false);
						}
					}
					/*setting the movement to 0*/
					if(!keyDown[1] && !keyDown[3]) 
					{
						//player.setVelX(0);
						player.setSliding(true);
						player.setMovingX(false);
					}
					if(!keyDown[5]) {player.setRunSpeed(0);}
				}
				
			}
		}
		e.consume();
	}
	
	public void keyTyped(KeyEvent e) {e.consume();}
}
