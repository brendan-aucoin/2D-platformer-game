package menus;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Game;
import game.State;
import running.Main;

public class LevelStartMenu extends Menu
{
	private Rectangle archerBounds,knightBounds,mageBounds;
	private BufferedImage archerImage,knightImage,mageImage;
	private Game game;
	private Rectangle musicBounds;
	private String onOff;
	public LevelStartMenu(State specificState,Game game)
	{
		super(specificState);
		this.game = game;
		try {
			archerImage = ImageIO.read(this.getClass().getResource("/single_images/archerImage.png"));
			knightImage = ImageIO.read(this.getClass().getResource("/single_images/knightImage.png"));
			mageImage = ImageIO.read(this.getClass().getResource("/single_images/mageImage.png"));
		}catch(IOException e) {System.err.println("there was a problem getting the images in level start menu");}
		archerBounds = new Rectangle(Main.WIDTH/20,Main.HEIGHT/6,Main.WIDTH/5,(int)(Main.HEIGHT/1.5));
		knightBounds = new Rectangle((int)(Main.WIDTH/2.5),Main.HEIGHT/6,Main.WIDTH/5,(int)(Main.HEIGHT/1.5));
		mageBounds = new Rectangle((int)(Main.WIDTH/1.35),Main.HEIGHT/6,Main.WIDTH/5,(int)(Main.HEIGHT/1.5));
		
	}
	
	public void render(Graphics2D g)
	{
		/*the character selections*/
		g.setColor(new Color(4,25,51));
		g.setStroke(new BasicStroke(5));
		changeColour(g,archerBounds,new Color(255,0,255),new Color(4,25,51));
		g.draw(archerBounds);
		changeColour(g,knightBounds,new Color(255,0,255),new Color(4,25,51));
		g.draw(knightBounds);
		changeColour(g,mageBounds,new Color(255,0,255),new Color(4,25,51));
		g.draw(mageBounds);
		/*the images*/
		/*the archer*/
		g.drawImage(archerImage,(int) (archerBounds.x + archerBounds.width/2.5),(int) (archerBounds.height/1.5),null);
		g.drawImage(knightImage,(int) (knightBounds.x + knightBounds.width/2.5),(int) (knightBounds.height/1.5),null);
		g.drawImage(mageImage,(int) (mageBounds.x + mageBounds.width/2.5),(int) (mageBounds.height/1.5),null);
		/*the words*/
		g.setFont(new Font("Lucida Handwriting",Font.PLAIN,25));
		g.setColor(new Color(255,255,224));
		g.drawString("Archer: Eriel",(int) (archerBounds.x + archerBounds.width/2.5-g.getFont().getSize()*2.5), (int) (archerBounds.height-g.getFont().getSize()*1.5));
		g.drawString("Knight: Shouvley",(int) (knightBounds.x + knightBounds.width/2.5-g.getFont().getSize()*3.8), (int) (knightBounds.height-g.getFont().getSize()*1.5));
		g.drawString("Mage: Challis",(int) (mageBounds.x + mageBounds.width/2.5-g.getFont().getSize()*2.5), (int) (mageBounds.height-g.getFont().getSize()*1.5));
		/*the world and level text*/
		g.setFont(new Font("Lucida Handwriting",Font.PLAIN,45));
		g.drawString(game.getWorld(), Main.WIDTH/2 - g.getFont().getSize()*2,Main.HEIGHT/15);
	//	g.drawString("Level " + (game.getLevel()+1), Main.WIDTH/2- g.getFont().getSize()*2, Main.HEIGHT/7);
		g.drawString("Level " + (game.getLevel()+1) + ": " +game.getLevelNames()[game.getLevel()], Main.WIDTH/2- g.getFont().getSize()*2 - (game.getLevelNames()[game.getLevel()].length()*12), Main.HEIGHT/7);
		/*the song words and rectangles*/
		g.setFont(new Font("Lucida Handwriting",Font.PLAIN,25));
		g.drawString("Song: " + game.musicNames[game.getLevel()],Main.WIDTH/20-g.getFont().getSize()*2,Main.HEIGHT - g.getFont().getSize()*3);
		/*the on and off rectangles and words*/
		g.setColor(new Color(4,25,51));
		g.setStroke(new BasicStroke(2));
		if(musicBounds == null) {onOff = "On";musicBounds = new Rectangle(Main.WIDTH/10,(int) (Main.HEIGHT - g.getFont().getSize()*2.5),Main.WIDTH/6,Main.HEIGHT/30);}
		changeColour(g,musicBounds,Color.RED.brighter(),new Color(4,25,51));
		g.draw(musicBounds);
		
		g.setFont(new Font("Lucida Handwriting",Font.PLAIN,15));
		g.setColor(new Color(255,255,224));
		g.drawString(onOff, (int) (musicBounds.x + musicBounds.width/2.5),(int) (musicBounds.y + musicBounds.height/1.3));
	}
	
	public void mousePressed(MouseEvent e)
	{
		if(Game.gameState == specificState)
		{
			/*if you click the archer*/
			if(mouseOver(e.getX(),e.getY(),archerBounds))
			{
				startGame("Archer");
			}
			
			/*if you click the knight*/
			else if(mouseOver(e.getX(),e.getY(),knightBounds))
			{
				startGame("Knight");
			}
			
			/*if you click the mage*/
			else if(mouseOver(e.getX(),e.getY(),mageBounds))
			{
				startGame("Mage");
			}
			/*if you click the music button*/
			else if(mouseOver(e.getX(),e.getY(),musicBounds.x,musicBounds.y,musicBounds.width,musicBounds.height))
			{
				if(onOff.equals("On")) {onOff = "Off";}
				else if(onOff.equals("Off")) {onOff = "On";}
				else {onOff = "On";}
			}
		}
	}
	private void startGame(String characterName)
	{
		if(onOff.equals("On")) {game.setMusicOn(true);}
		else if(onOff.equals("Off")){game.setMusicOn(false);}
		game.setCharacter(characterName);
		game.loadLevel();
		Game.gameState = State.Playing;
		game.removeMouseListener(this);
	}
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
