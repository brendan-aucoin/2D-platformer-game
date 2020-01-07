package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import game.Camera;
import game.Game;
import game.State;
import images.BufferedImageLoader;
import running.Main;

public class PauseMenu extends Menu
{
	private Camera cam;
	private Rectangle resumeBounds,characterSelectBounds,restartBounds,quitBounds;
	private Game game;
	private BufferedImage houseIcon,statsIcon,shopIcon;
	private Rectangle houseIconBounds,statsIconBounds,shopIconBounds;
	public PauseMenu(State specificState,Camera cam,Game game,BufferedImageLoader loader)
	{
		super(specificState);
		this.cam = cam;
		this.game = game;
		houseIcon = loader.loadImage("single_images","houseIcon.png");
		statsIcon = loader.loadImage("single_images","statsIcon.png");
		shopIcon = loader.loadImage("single_images","shopIcon.png");
	}
	
	public void render(Graphics2D g)
	{
		/*drawing the text*/
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,80));
		
		g.setColor(new Color(251,246,242));//regular colour
		//g.setColor(new Color(208,18,4));//piece of mind colour.
		
		g.drawString("Paused",(int)(-cam.getX() + Main.WIDTH/2 - g.getFont().getSize()*2),(int)(-cam.getY() + Main.HEIGHT/2- g.getFont().getSize()*3));
		g.setColor(new Color(255,255,224));
		//the resume button
		if(resumeBounds == null) {resumeBounds = new Rectangle((int)((-cam.getX() + Main.WIDTH/2 - g.getFont().getSize()*3.5)),(int)(-cam.getY() + Main.HEIGHT/2- g.getFont().getSize()*2.5),(int)(Main.WIDTH/2),Main.HEIGHT/10);}
		else {resumeBounds.setBounds((int)((-cam.getX() + Main.WIDTH/2 - g.getFont().getSize()*3.5)),(int)(-cam.getY() + Main.HEIGHT/2- g.getFont().getSize()*2.5),(int)(Main.WIDTH/2),Main.HEIGHT/10);}
		
		//the character select menu
		if(characterSelectBounds == null) {characterSelectBounds = new Rectangle((int)((-cam.getX() + Main.WIDTH/2 - g.getFont().getSize()*3.5)),(int)(-cam.getY() + Main.HEIGHT/2- g.getFont().getSize()*1),(int)(Main.WIDTH/2),Main.HEIGHT/10);}
		else {characterSelectBounds.setBounds((int)((-cam.getX() + Main.WIDTH/2 - g.getFont().getSize()*3.5)),(int)(-cam.getY() + Main.HEIGHT/2- g.getFont().getSize()*1),(int)(Main.WIDTH/2),Main.HEIGHT/10);}
		
		//the play button
		if(restartBounds == null) {restartBounds = new Rectangle((int)((-cam.getX() + Main.WIDTH/2 - g.getFont().getSize()*3.5)),(int)(-cam.getY() + Main.HEIGHT/2 + g.getFont().getSize()*0.5),(int)(Main.WIDTH/2),Main.HEIGHT/10);}
		else {restartBounds.setBounds((int)((-cam.getX() + Main.WIDTH/2 - g.getFont().getSize()*3.5)),(int)(-cam.getY() + Main.HEIGHT/2+ g.getFont().getSize()*0.5),(int)(Main.WIDTH/2),Main.HEIGHT/10);}
		
		//the pause button
		if(quitBounds == null) {quitBounds = new Rectangle((int)((-cam.getX() + Main.WIDTH/2 + g.getFont().getSize())),(int)(-cam.getY() + Main.HEIGHT/2+ g.getFont().getSize()*2),(int)(Main.WIDTH/2),Main.HEIGHT/10);}
		else {quitBounds.setBounds((int)((-cam.getX() + Main.WIDTH/2 - g.getFont().getSize()*3.5)),(int)(-cam.getY() + Main.HEIGHT/2 + g.getFont().getSize()*2),(int)(Main.WIDTH/2),Main.HEIGHT/10);}
		
		/*drawing the buttons*/
		changeColour(g,resumeBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(resumeBounds.x, resumeBounds.y, resumeBounds.width, resumeBounds.height,g.getFont().getSize()*2,g.getFont().getSize()*2);
		changeColour(g,characterSelectBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(characterSelectBounds.x, characterSelectBounds.y, characterSelectBounds.width, characterSelectBounds.height,g.getFont().getSize()*2,g.getFont().getSize()*2);
		changeColour(g,restartBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(restartBounds.x, restartBounds.y, restartBounds.width, restartBounds.height,g.getFont().getSize()*2,g.getFont().getSize()*2);
		changeColour(g,quitBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(quitBounds.x, quitBounds.y, quitBounds.width, quitBounds.height,g.getFont().getSize()*2,g.getFont().getSize()*2);
		/*drawing the words on the buttons*/
		
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,45));
		
		/*the resume text*/
		g.drawString("Resume",(resumeBounds.x + resumeBounds.width/2) - g.getFont().getSize()*2, (resumeBounds.y + resumeBounds.height/2) + g.getFont().getSize()/3);
		
		/*the character select text*/
		g.drawString("Character Select",(characterSelectBounds.x + characterSelectBounds.width/2) - g.getFont().getSize()*5, (characterSelectBounds.y + characterSelectBounds.height/2) + g.getFont().getSize()/3);
		
		/*the play text*/
		g.drawString("Restart",(int) ((restartBounds.x + restartBounds.width/2) - g.getFont().getSize()*2.3), (restartBounds.y + restartBounds.height/2) + g.getFont().getSize()/3);
		
		/*the pause text*/
		g.drawString("Quit",(int) ((quitBounds.x + quitBounds.width/2)-g.getFont().getSize()*1.5), (quitBounds.y + quitBounds.height/2) + g.getFont().getSize()/3);
	
		/*drawing the 2 icons*/
		
		if(houseIconBounds == null) {houseIconBounds = new Rectangle((int) (-cam.getX() + houseIcon.getWidth()/5),(int) -cam.getY()+ houseIcon.getHeight()/5,(int)(houseIcon.getWidth()*1.5),(int)(houseIcon.getHeight()*1.5));}
		else {houseIconBounds.setBounds((int) (-cam.getX() + houseIcon.getWidth()/5),(int) -cam.getY()+ houseIcon.getHeight()/5,(int)(houseIcon.getWidth()*1.5),(int)(houseIcon.getHeight()*1.5));}
		
		if(statsIconBounds == null) {statsIconBounds = new Rectangle((int) (-cam.getX() + houseIcon.getWidth() *2 + statsIcon.getWidth()/5),(int) (-cam.getY() + statsIcon.getHeight()/5),(int)(statsIcon.getWidth()*1.5),(int)(statsIcon.getHeight()*1.5));}
		else {statsIconBounds.setBounds((int) (-cam.getX() + houseIcon.getWidth() *2 + statsIcon.getWidth()/5),(int) (-cam.getY() + statsIcon.getHeight()/5),(int)(statsIcon.getWidth()*1.5),(int)(statsIcon.getHeight()*1.5));}
		
		if(shopIconBounds == null) {shopIconBounds = new Rectangle(
				(int)(-cam.getX() + houseIcon.getWidth()*2 + statsIcon.getWidth()*2 + shopIcon.getWidth()/5),
				(int) (-cam.getY() + shopIcon.getHeight()/5),(int)(shopIcon.getWidth()*1.5),(int)(shopIcon.getHeight() * 1.5)
				);}
		else {shopIconBounds.setBounds((int)(-cam.getX() + houseIcon.getWidth()*2 + statsIcon.getWidth()*2 + shopIcon.getWidth()/5),
				(int) (-cam.getY() + shopIcon.getHeight()/5),(int)(shopIcon.getWidth()*1.5),(int)(shopIcon.getHeight() * 1.5));}
		/*drawing the icons*/
		g.drawImage(houseIcon,(int)(-cam.getX() + (houseIconBounds.width/2) - (houseIcon.getWidth()/3)),(int)(-cam.getY() + (houseIconBounds.height/2) - (houseIcon.getHeight()/3)),null);
		g.drawImage(statsIcon,(int)((statsIconBounds.x + statsIconBounds.width/2) - statsIcon.getWidth()/2),(int)((statsIconBounds.y + statsIconBounds.height/2) - statsIcon.getHeight()/2),null);
		g.drawImage(shopIcon, (shopIconBounds.x + shopIconBounds.width/2) - shopIcon.getWidth()/2, (shopIconBounds.y + shopIconBounds.height/2) - shopIcon.getHeight()/2, null);
		/*drawing the icon rectangles*/
		g.setColor(new Color(224,255,255));
		g.draw3DRect(houseIconBounds.x,houseIconBounds.y,houseIconBounds.width,houseIconBounds.height,mouseOver(mouseX,mouseY,houseIconBounds));
		g.draw3DRect(statsIconBounds.x,statsIconBounds.y,statsIconBounds.width,statsIconBounds.height,mouseOver(mouseX,mouseY,statsIconBounds));
		g.draw3DRect(shopIconBounds.x,shopIconBounds.y,shopIconBounds.width,shopIconBounds.height,mouseOver(mouseX,mouseY,shopIconBounds));
		
		
	}
	public void mousePressed(MouseEvent e)
	{
		if(Game.gameState == specificState)
		{
			if(mouseOver(resumeBounds))
			{
				if(game.isMusicOn()) {game.playSong();}
				Game.gameState = State.Playing;
			}
		
			else if(mouseOver(characterSelectBounds))
			{
				Game.gameState = State.LevelStart;
				game.clearLevel();
				
				if(game.isMusicOn()) {game.stopSong();}
				game.removeMouseListener(this);
			}
			
			else if(mouseOver(restartBounds))
			{
				Game.gameState = State.Playing;
				game.clearLevel();
				game.loadLevel();
				
			}
			
			else if(mouseOver(quitBounds))
			{
				System.exit(0);
			}
			
			else if(mouseOver(houseIconBounds)) {
				Game.gameState = State.MainMenu;
				game.clearLevel();
				game.removeMouseListener(this);
				game.playTheme(game.magicaPath);
			}
			
			else if(mouseOver(statsIconBounds)) {
				Game.gameState = State.StatsMenu;
				game.removeMouseListener(this);
			}
			else if(mouseOver(shopIconBounds)) {
				Game.gameState = State.Shop;
				game.removeMouseListener(this);
			}
		}
		e.consume();
	}
	public void mouseMoved(MouseEvent e)
	{
		mouseX = (int) (e.getX() - cam.getX());
		mouseY = (int) (e.getY() - cam.getY());
	}
	
	
}
