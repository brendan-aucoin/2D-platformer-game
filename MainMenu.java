package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import game.FileLoader;
import game.Game;
import game.State;
import running.Main;
public class MainMenu extends Menu 
{
	private Rectangle continueBounds,newGameBounds,multiPlayerBounds,controlsBounds,quitBounds;
	private FileLoader fileLoader;
	private String world;
	private int level,coins;
	private FileLoader levelLoader,coinsLoader,worldLoader;
	private boolean canContinue;
	private Game game;
	public MainMenu(State specificState,Game game)
	{
		super(specificState);
		this.game = game;
		fileLoader = new FileLoader();
		level = 0;
		coins = 0;
		world = "World 1";
		levelLoader = new FileLoader();
		levelLoader.setFileName(Main.TEXTSPATH + "level.txt");
		coinsLoader = new FileLoader();
		coinsLoader.setFileName(Main.TEXTSPATH + "coins.txt");
		worldLoader = new FileLoader();
		worldLoader.setFileName(Main.TEXTSPATH + "world.txt");
		//tomeLoader.setFileName(Main.TEXTSPATH + "currentTome.txt");
		canContinue =false;
		
	}
	public void render(Graphics2D g)
	{
		/*the title*/
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,65));
		g.setColor(new Color(255,255,224));
		g.drawString("Eritque Arcus Ovium", (int) (Main.WIDTH/2 - (g.getFont().getSize()*5.7)),(int) (g.getFont().getSize()*1.5));
		
		/*the rectangles*/
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		if(continueBounds == null) {continueBounds = new Rectangle(Main.WIDTH/40,Main.HEIGHT/4,(int)(((int)(g.getFont().getStringBounds("Continue", frc).getWidth()))-g.getFont().getSize()*2.7),((int)(g.getFont().getStringBounds("Continue", frc).getHeight()))-g.getFont().getSize());}
		if(newGameBounds == null) {newGameBounds = new Rectangle(Main.WIDTH/40,(int)(Main.HEIGHT/2.7),(int)(((int)(g.getFont().getStringBounds("New Game", frc).getWidth()))-g.getFont().getSize()*2.8),(int)(((int)(g.getFont().getStringBounds("New Game", frc).getHeight()))-g.getFont().getSize()/1.2));}
		if(multiPlayerBounds == null) {multiPlayerBounds = new Rectangle(Main.WIDTH/40,(int)(Main.HEIGHT/1.9)-g.getFont().getSize()/5,(int)(((int)(g.getFont().getStringBounds("Multi Player", frc).getWidth())-g.getFont().getSize()*3.75)),(int)(((int)(g.getFont().getStringBounds("Multi Player", frc).getHeight()))-g.getFont().getSize()/1.3));}
		if(controlsBounds == null) {controlsBounds = new Rectangle(Main.WIDTH/40,(int)(Main.HEIGHT/1.5 - g.getFont().getSize()/3),(int)(((int)(g.getFont().getStringBounds("Controls", frc).getWidth())-g.getFont().getSize()*2.5)),(int)((int)(g.getFont().getStringBounds("Controls", frc).getHeight()) -g.getFont().getSize()/1.5));}
		if(quitBounds == null) {quitBounds = new Rectangle(Main.WIDTH/40,(int)(Main.HEIGHT/1.25 - g.getFont().getSize()/3),(int)(((int)(g.getFont().getStringBounds("Quit", frc).getWidth())-g.getFont().getSize()*1.2)),(int)((int)(g.getFont().getStringBounds("Quit", frc).getHeight()) -g.getFont().getSize()/1.4));}
		/*the buttons*/
		g.setFont(new Font("Lucida Handwriting",Font.PLAIN,35));
		/*the continue button*/
		if(levelLoader.exists()&&coinsLoader.exists() && worldLoader.exists()) {canContinue = true;changeColour(g,continueBounds,Color.RED,new Color(255,255,224));}
		else {changeColour(g,continueBounds,Color.GRAY,new Color(255,255,244));canContinue = false;}
		g.drawString("Continue",Main.WIDTH/40,(int) (Main.HEIGHT/3.5));
		/*the new game button*/
		changeColour(g,newGameBounds,Color.RED,new Color(255,255,224));
		g.drawString("New Game", Main.WIDTH/40,(int) (Main.HEIGHT/2.4));
		/*the multiplayer button*/
		changeColour(g,multiPlayerBounds,Color.GRAY,new Color(255,255,224));
		g.drawString("Multi Player",Main.WIDTH/40, (int) (Main.HEIGHT/1.8));
		/*the controls button*/
		changeColour(g,controlsBounds,Color.RED,new Color(255,255,224));
		g.drawString("Controls",Main.WIDTH/40,(int) (Main.HEIGHT/1.45));
		/*the quit button*/
		changeColour(g,quitBounds,Color.RED,new Color(255,255,224));
		g.drawString("Quit", Main.WIDTH/40, (int)(Main.HEIGHT/1.23));
		/*drawing the bounds*/
		//g.setColor(Color.BLUE);
		//g.drawRect(controlsBounds.x, controlsBounds.y, controlsBounds.width, controlsBounds.height);
		//g.drawRect(multiPlayerBounds.x, multiPlayerBounds.y, multiPlayerBounds.width, multiPlayerBounds.height);
		//g.drawRect(newGameBounds.x, newGameBounds.y, newGameBounds.width, newGameBounds.height);
		//g.drawRect(continueBounds.x,continueBounds.y,continueBounds.width,continueBounds.height);
		//g.draw(quitBounds);
	}
	public void mousePressed(MouseEvent e)
	{
		if(Game.gameState == specificState)
		{
		/*if you press the continue button*/
		if(mouseOver(e.getX(),e.getY(),continueBounds.x,continueBounds.y,continueBounds.width,continueBounds.height))
		{
			if(canContinue) {
			continueGameFiles();
			game.setLevel(level);
			game.setCoins(coins);
			game.setWorld(world);
			try {Thread.sleep(120);}catch(InterruptedException ex) {ex.printStackTrace();}
			Game.gameState = State.LevelStart;
			game.stopMusicPlayer();
			game.removeMouseListener(this);
			}
			
		}
		/*if you press the new game button*/
		else if(mouseOver(e.getX(),e.getY(),newGameBounds.x,newGameBounds.y,newGameBounds.width,newGameBounds.height)) 
		{
			newGameFiles();
			game.setLevel(level);
			game.setCoins(coins);
			game.setWorld(world);
			try {Thread.sleep(120);}catch(InterruptedException ex) {ex.printStackTrace();}
			Game.gameState = State.LevelStart;
			game.stopMusicPlayer();
			game.removeMouseListener(this);
			
		}
		/*if you press the multi player button*/
		//else if(mouseOver(e.getX(),e.getY(),multiPlayerBounds.x,multiPlayerBounds.y,multiPlayerBounds.width,multiPlayerBounds.height))
		//{
		//	Game.gameState = State.MultiPlayer;
		//}
		/*if you press the controls button*/
		else if(mouseOver(e.getX(),e.getY(),controlsBounds.x,controlsBounds.y,controlsBounds.width,controlsBounds.height)) 
		{
			Game.gameState = State.ControlsMenu;
		}
		else if(mouseOver(e.getX(),e.getY(),quitBounds)) {System.exit(0);}
		else {return;}
		}
	}//end of mouse pressed
	
	
	private void continueGameFiles()
	{
		level = Integer.parseInt(levelLoader.read().get(0));
		coins = Integer.parseInt(coinsLoader.read().get(0));
		world = worldLoader.read().get(0);
	}
	
	private void newGameFiles()
	{
		resetFile(Main.TEXTSPATH + "level.txt","0");
		
		resetFile(Main.TEXTSPATH + "coins.txt","0");
		
		resetFile(Main.TEXTSPATH + "world.txt","World 1");
		
		resetFile(Main.TEXTSPATH + "currentTome.txt","lightning");
		level =0;
		coins = 0;
		world = "World 1";
	}
	
	
	public void resetFile(String path,String output)
	 {
	    fileLoader.setFileName(path);
	    fileLoader.createFile();
	    fileLoader.write(output);
	    fileLoader.closeOutput();
	 }
	
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	public String getWorld() {return world;}
	public void setWorld(String world) {this.world = world;}
	public int getLevel() {return level;}
	public void setLevel(int level) {this.level = level;}
	public int getCoins() {return coins;}
	public void setCoins(int coins) {this.coins = coins;}
	
	
}
