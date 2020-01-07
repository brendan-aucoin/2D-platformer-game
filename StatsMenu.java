package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import entities.Entity;
import game.Game;
import game.Handler;
import game.State;
import images.BufferedImageLoader;
import player.Archer;
import player.Knight;
import player.Mage;
import player.Player;
import running.Main;

public class StatsMenu extends Menu
{
	private Handler handler;
	private BufferedImageLoader loader;
	private Game game;
	private String statsNames[] = {"Attack 1: ","Attack 2: ","Defense: ","Speed: ","Running Speed: "};
	private String stats[];
	private Rectangle returnBounds;
	private BufferedImage image;
	private BufferedImage archerImage,knightImage,mageImage;
	public StatsMenu(State specificState,Handler handler,Game game)
	{
		super(specificState);
		this.handler = handler;
		loader = new BufferedImageLoader();
		this.game = game;
		stats = new String[5];
		image = loader.loadImage("backgrounds","rainbow space.png");
		archerImage = loader.loadImage("single_images","archerImage.png");
		knightImage = loader.loadImage("single_images","knightImage.png");
		mageImage = loader.loadImage("single_images","mageImage.png");
	}
	public void render(Graphics2D g)
	{
		g.drawImage(image,0,0,Main.WIDTH,Main.HEIGHT,null);
		g.setColor(new Color(255,255,224));
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,85));
		/*drawing the words*/
		g.drawString("Stats",(int)(Main.WIDTH/2 - g.getFont().getSize()*1.2),(int)(Main.HEIGHT/2- g.getFont().getSize()*3.2));
		
		/*drawing the specialized text.*/
		/*the left hand column*/
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,30));
		for(int i = 0; i< statsNames.length; i++)
		{
			g.drawString(statsNames[i], Main.WIDTH/2 - g.getFont().getSize()*10, (int) (Main.HEIGHT/3 + (i*Main.HEIGHT/9)));
		}
		
		for(int i = 0; i < handler.entity.size(); i++)
		{
			Entity ent = handler.entity.get(i);
			if(ent instanceof Archer){setStats(ent,"Arrow","Dagger");}
			else if(ent instanceof Knight) {setStats(ent,"Sword Strike","Trident");}
			else if(ent instanceof Mage) {setStats(ent,"FireBall","Tome");stats[4] = "Can't Run";}
		}
		/*the right hand column*/
		for(int i = 0; i<stats.length; i++)
		{
			g.drawString(stats[i], Main.WIDTH/2 + g.getFont().getSize()*8, (int) (Main.HEIGHT/3 + (i*Main.HEIGHT/9)));
		}
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,85));
		/*the picture of your character*/
		for(int i =0; i < handler.entity.size();i++)
		{
			Entity ent = handler.entity.get(i);
			if(ent instanceof Archer){g.drawImage(archerImage,(int)(Main.WIDTH/2),(int)(Main.HEIGHT/8),null);}
			else if(ent instanceof Knight){g.drawImage(knightImage,(int)(Main.WIDTH/2),(int)(Main.HEIGHT/8),null);}
			else if(ent instanceof Mage){g.drawImage(mageImage,(int)(Main.WIDTH/2),(int)(Main.HEIGHT/8),null);}
		}
		/*the return button*/
		if(returnBounds == null) {returnBounds = new Rectangle((int)(( Main.WIDTH/2 - Main.WIDTH/4.3)),(int)( Main.HEIGHT/2+ g.getFont().getSize()*2.8),(int)(Main.WIDTH/2),Main.HEIGHT/10);}
		changeColour(g,returnBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(returnBounds.x, returnBounds.y, returnBounds.width, returnBounds.height,g.getFont().getSize()*2,g.getFont().getSize()*2);
		/*the return button text*/
		g.setColor(Color.BLACK);
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,45));
		g.drawString("Return",(int)( returnBounds.x + returnBounds.width/2 - g.getFont().getSize()*2), returnBounds.y + returnBounds.height/2 + g.getFont().getSize()/3);
	}
	
	public void mousePressed(MouseEvent e)
	{
		if(mouseOver(returnBounds))
		{
			Game.gameState = State.Paused;
			game.removeMouseListener(this);
		}
	}
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}
	private void setStats(Entity ent,String attack1,String attack2)
	{
		Player player = (Player)ent;
		stats[0] = attack1;
		stats[1] = attack2;
		stats[2] = String.valueOf(player.getDefense());
		stats[3] = String.valueOf(player.getV1());
		stats[4] = String.valueOf(Math.round(player.getV1() + (player.getV1()/2.3)));
	}
}
