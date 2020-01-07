package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import id.ID;
import player.Player;
import running.Main;

public class HUD 
{
	private Game game;
	private int greenValue = 255;
	private Camera cam;
	private int health;
	private Handler handler;
	public HUD(Game game,Camera cam,Handler handler)
	{
		this. game = game;
		this.cam = cam;
		this.handler = handler;
	}
	public void update()
	{
		greenValue = Game.clamp(greenValue, 255, 0);

		for(int i =0;i<handler.entity.size();i++)
		{
			if(handler.entity.get(i).getId() == ID.Player)
			{
				Player player = (Player)handler.entity.get(i);
				health = player.getHealth();
			}
		}
		greenValue = health*2;
		if(greenValue <= 10) {
			greenValue = 0;
		}
	}
	
	public void render(Graphics g)
	{
		/*the health bar*/
		g.setColor(Color.GRAY);
		g.fillRect((int)(-cam.getX() + Main.WIDTH/25),(int)(-cam.getY() + Main.HEIGHT/25),200,Main.HEIGHT/20);
		g.setColor(new Color(75,greenValue,0));
		g.fillRect((int)(-cam.getX() + Main.WIDTH/25),(int)(-cam.getY() + Main.HEIGHT/25),health*2,Main.HEIGHT/20);
		g.setColor(Color.WHITE);
		g.drawRect((int)(-cam.getX() + Main.WIDTH/25),(int)(-cam.getY() + Main.HEIGHT/25),health*2,Main.HEIGHT/20);
		
		/*the words*/
		g.setColor(Color.WHITE);
		g.setFont(new Font("Ubuntu",Font.BOLD + Font.ITALIC,30));
		g.drawString("Coins: " + game.getCoins(), (int)(-cam.getX() + Main.WIDTH/20),(int) (-cam.getY() + Main.HEIGHT/8));
		
	}
}
