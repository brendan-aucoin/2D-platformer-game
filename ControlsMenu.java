package menus;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import game.Game;
import game.State;
import running.Main;

public class ControlsMenu extends Menu
{
	private String[] keyNames = {"A","D","Space","K","L","Shift","Esc"};
	private String [] actionNames = {"Left","Right","Jump","Attack 1","Attack 2","Run","Pause/Play"};
	private Rectangle returnBounds;
	public ControlsMenu(State specificState)
	{
		super(specificState);
	}
	public void render(Graphics2D g)
	{
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,65));
		g.setColor(new Color(255,255,224));
		/*the title*/
		g.drawString("Controls", (int) (Main.WIDTH/2 - g.getFont().getSize()*2.5),(int) (g.getFont().getSize()));
		/*the keys and actions*/
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,35));
		g.drawString("Key",  (int) (Main.WIDTH/2 - g.getFont().getSize()*12), Main.HEIGHT/4 - g.getFont().getSize()*2);
		g.drawString("Action",  Main.WIDTH/2 + g.getFont().getSize()*10, Main.HEIGHT/4 - g.getFont().getSize()*2);
		g.setFont(new Font("Lucida Handwriting",Font.PLAIN,30));
		
		//g.setColor(new Color(65,105,225));//blue option
		//g.setColor(new Color(128,0,0));//dark red option
		g.setColor(new Color(220,20,60));//bright red option
		/*the keys*/
		for(int i = 0; i < keyNames.length; i++)
		{
			g.drawString(keyNames[i], Main.WIDTH/2 - g.getFont().getSize()*14, Main.HEIGHT/4 + (i*Main.HEIGHT/13));
		}
		/*the actions*/
		for(int i = 0; i < actionNames.length; i++)
		{
			g.drawString(actionNames[i], Main.WIDTH/2 + g.getFont().getSize()*12, Main.HEIGHT/4 + (i*Main.HEIGHT/13));
		}
		/*the return button*/
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,40));
		g.setColor(new Color(255,255,224));
		if(returnBounds == null) {returnBounds = new Rectangle(Main.WIDTH/2 - g.getFont().getSize()*2,(int)(Main.HEIGHT - g.getFont().getSize()*1.8),((int)(g.getFont().getStringBounds("Main Menu", frc).getWidth())+g.getFont().getSize()/3),(int)(g.getFont().getStringBounds("Main Menu", frc).getHeight()) -g.getFont().getSize()/2);}
		g.setColor(Color.LIGHT_GRAY.brighter().brighter());
		g.setStroke(new BasicStroke(3));
		g.drawRect(returnBounds.x, returnBounds.y, returnBounds.width, returnBounds.height);
		changeColour(g,returnBounds,Color.RED,new Color(255,255,224));
		g.drawString("Main Menu", Main.WIDTH/2 - g.getFont().getSize()*2,Main.HEIGHT - g.getFont().getSize());
	}
	public void mousePressed(MouseEvent e)
	{
		if(Game.gameState == specificState)
		{
		if(mouseOver(e.getX(),e.getY(),returnBounds.x,returnBounds.y,returnBounds.width,returnBounds.height))
		{
			Game.gameState = State.MainMenu;
		}
		}
	}//end of mouse pressed
	
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
}
