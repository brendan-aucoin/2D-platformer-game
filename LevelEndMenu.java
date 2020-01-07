package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import game.FileLoader;
import game.Game;
import game.Handler;
import game.State;
import running.Main;

public class LevelEndMenu extends Menu
{
	private Font underlinedFont;
	private Rectangle nextLevelBounds,mainMenuBounds,replayBounds,saveBounds;
	private Handler handler;
	private Game game;
	private FileLoader levelLoader,coinsLoader,worldLoader;
	public LevelEndMenu(State specificState,Game game,Handler handler)
	{
		super(specificState);
		this.handler = handler;
		this.game = game;
		Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		underlinedFont = new Font("Lucida Handwriting",Font.BOLD,60).deriveFont(fontAttributes);
		levelLoader = new FileLoader();
		levelLoader.setFileName(Main.TEXTSPATH + "level.txt");
		
		coinsLoader = new FileLoader();
		coinsLoader.setFileName(Main.TEXTSPATH + "coins.txt");
		
		worldLoader = new FileLoader();
		worldLoader.setFileName(Main.TEXTSPATH + "world.txt");
		
	}

	public void render(Graphics2D g) 
	{
		g.setFont(underlinedFont);
		g.setColor(new Color(255,255,224));
		g.drawString("EHY YOU WIN!",(int) (Main.WIDTH/2 - g.getFont().getSize()*4),Main.HEIGHT/10);
		
		if(nextLevelBounds == null) {nextLevelBounds =new Rectangle((int)(Main.WIDTH/2 - g.getFont().getSize()*5),Main.HEIGHT/5,Main.WIDTH/2,Main.HEIGHT/10);}
		if(replayBounds == null) {replayBounds = new Rectangle((int)(Main.WIDTH/2 - g.getFont().getSize()*5),(int)(Main.HEIGHT/2.5),Main.WIDTH/2,Main.HEIGHT/10);}
		if(saveBounds == null) {saveBounds = new Rectangle(
				(int)(Main.WIDTH/2 - g.getFont().getSize()*5),
				(int)(Main.HEIGHT/1.7),Main.WIDTH/2,Main.HEIGHT/10
				);
				}
		if(mainMenuBounds == null) {mainMenuBounds = new Rectangle((int)(Main.WIDTH/2 - g.getFont().getSize()*5),(int)(Main.HEIGHT/1.3),Main.WIDTH/2,Main.HEIGHT/10);}
	
		/*next level*/
		changeColour(g,nextLevelBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(nextLevelBounds.x, nextLevelBounds.y, nextLevelBounds.width, nextLevelBounds.height,g.getFont().getSize()*2,g.getFont().getSize()*2);
		/*replay*/
		changeColour(g,replayBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(replayBounds.x, replayBounds.y, replayBounds.width, replayBounds.height,g.getFont().getSize()*2,g.getFont().getSize()*2);
		/*save button*/
		changeColour(g,saveBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(saveBounds.x, saveBounds.y, saveBounds.width, saveBounds.height, g.getFont().getSize()*2,g.getFont().getSize()*2);
		/*main menu*/
		changeColour(g,mainMenuBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(mainMenuBounds.x, mainMenuBounds.y, mainMenuBounds.width, mainMenuBounds.height,g.getFont().getSize()*2,g.getFont().getSize()*2);
	
	
		/*the words*/
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,60));
		/*next level*/
		g.drawString("Next Level", (int)(nextLevelBounds.x + nextLevelBounds.width/2 - g.getFont().getSize()*3), (int) (nextLevelBounds.y + nextLevelBounds.height/2 + g.getFont().getSize()/3));
		/*replay*/
		g.drawString("Replay", (int)(replayBounds.x + replayBounds.width/2 - g.getFont().getSize()*2), (int) (replayBounds.y + replayBounds.height/2 + g.getFont().getSize()/3));
		/*save*/
		g.drawString("Save", (int)(saveBounds.x + saveBounds.width/2 - g.getFont().getSize()*1.5), (int)(saveBounds.y + saveBounds.height/2 + g.getFont().getSize()/2.7));
		/*mainMenu*/
		g.drawString("Main Menu", (int)(mainMenuBounds.x + mainMenuBounds.width/2 - g.getFont().getSize()*3.5), (int) (mainMenuBounds.y + mainMenuBounds.height/2 + g.getFont().getSize()/3));
	}
	
	public void mousePressed(MouseEvent e)
	{
		if(Game.gameState == specificState)
		{
			if(mouseOver(nextLevelBounds))
			{
				handler.clear();
				save();
				game.setLevel(game.getLevel()+1);
				Game.gameState = State.LevelStart;
				
			}
			else if(mouseOver(replayBounds))
			{
				handler.clear();
				Game.gameState = State.LevelStart;
			}
			else if(mouseOver(saveBounds)) {
				save();
			}
			else if(mouseOver(mainMenuBounds))
			{
				handler.clear();
				Game.gameState = State.MainMenu;
				game.playTheme(game.magicaPath);
			}
		}
	}
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	private void save() {
		levelLoader.createFile();
		coinsLoader.createFile();
		worldLoader.createFile();
		levelLoader.write(String.valueOf(game.getLevel() +1));
		worldLoader.write(game.getWorld());
		coinsLoader.write(String.valueOf(game.getCoins()));
		levelLoader.closeOutput();
		coinsLoader.closeOutput();
		worldLoader.closeOutput();
	}
	
}


/*if(nextLevelBounds == null) {nextLevelBounds =new Rectangle((int)(Main.WIDTH/2 - g.getFont().getSize()*5),Main.HEIGHT/7,Main.WIDTH/2,Main.HEIGHT/5);}
if(replayBounds == null) {replayBounds = new Rectangle((int)(Main.WIDTH/2 - g.getFont().getSize()*5),(int)(Main.HEIGHT/2.5),Main.WIDTH/2,Main.HEIGHT/5);}
if(saveBounds == null) {saveBounds = new Rectangle(
		
		);
		}
if(mainMenuBounds == null) {mainMenuBounds = new Rectangle((int)(Main.WIDTH/2 - g.getFont().getSize()*5),(int)(Main.HEIGHT/1.5),Main.WIDTH/2,Main.HEIGHT/5);}
*/