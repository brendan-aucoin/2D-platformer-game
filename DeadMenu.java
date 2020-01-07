package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import game.Game;
import game.State;
import images.BufferedImageLoader;
import running.Main;

public class DeadMenu extends Menu{
	
	private Font pyriteFont,judasPriestFont;
	private BufferedImage backgroundImage;
	private Game game;
	private Rectangle replayBounds,characterSelectBounds,quitBounds;
	public DeadMenu(State specificState,Game game,BufferedImageLoader loader) {
		super(specificState);
		this.game = game;
		try {
			pyriteFont = registerCustomFont("pyrite",200);
			judasPriestFont = registerCustomFont("JudasPriest",45);
		} catch (IOException | FontFormatException e) {System.out.println("the desired font wasnt available");}
		backgroundImage = loader.loadImage("backgrounds","deathBackground.png");
	}
	public void render(Graphics2D g) {
		g.drawImage(backgroundImage,0,0,Main.WIDTH,Main.HEIGHT,null);
		g.setFont(pyriteFont);
		g.setColor(Color.RED);
		g.drawString("YOUR DED", (int)(Main.WIDTH/2 - g.getFont().getSize()*1.3), Main.HEIGHT/2);
		g.setColor(new Color(255,255,224));
		if(replayBounds == null) {replayBounds = new Rectangle((int)Main.WIDTH/18,(int)(Main.HEIGHT/2 + Main.HEIGHT/8),Main.WIDTH/4,Main.HEIGHT/8);}
		if(characterSelectBounds == null) {characterSelectBounds = new Rectangle((Main.WIDTH/18) + (Main.WIDTH/4) + Main.WIDTH/20,(int)(Main.HEIGHT/2 + Main.HEIGHT/8),Main.WIDTH/4,Main.HEIGHT/8);}
		if(quitBounds == null) {quitBounds = new Rectangle((Main.WIDTH/18 + (Main.WIDTH/4)*2 + Main.WIDTH/10),(int)(Main.HEIGHT/2 + Main.HEIGHT/8),Main.WIDTH/4,Main.HEIGHT/8);}
		
		/*drawing the rectangles*/
		changeColour(g,replayBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(replayBounds.x,replayBounds.y,replayBounds.width,replayBounds.height,25,25);
		changeColour(g,characterSelectBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(characterSelectBounds.x,characterSelectBounds.y,characterSelectBounds.width,characterSelectBounds.height,25,25);
		changeColour(g,quitBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(quitBounds.x,quitBounds.y,quitBounds.width,quitBounds.height,25,25);
		
		/*drawing the words*/
		g.setColor(Color.BLACK);
		g.setFont(judasPriestFont);
		g.drawString("Replay",(int)(replayBounds.x  + replayBounds.width/2 - g.getFont().getSize()*1.2), replayBounds.y + replayBounds.height/2 + g.getFont().getSize()/4);
		g.drawString("Character Select",(int)(characterSelectBounds.x  + characterSelectBounds.width/2 - g.getFont().getSize()*2.7), characterSelectBounds.y + characterSelectBounds.height/2 + g.getFont().getSize()/4);
		g.drawString("Quit",(int)(quitBounds.x  + quitBounds.width/2 - g.getFont().getSize()/1.4), quitBounds.y + quitBounds.height/2 + g.getFont().getSize()/4);
	}
	
	public void mousePressed(MouseEvent e) {
		if(mouseOver(replayBounds)) {
			try {Thread.sleep(100);}catch(InterruptedException e2) {e2.printStackTrace();}
			Game.gameState = State.Playing;
			game.clearLevel();
			game.loadLevel();
			game.stopMusicPlayer();
		}
		else if(mouseOver(characterSelectBounds)) {
			Game.gameState = State.LevelStart;
			game.clearLevel();
			
			if(game.isMusicOn()) {game.stopSong();}
			game.removeMouseListener(this);
			game.stopMusicPlayer();
		}
		else if(mouseOver(quitBounds)) {
			System.exit(0);
		}
	}
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
