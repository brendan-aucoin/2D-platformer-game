package menus;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import game.State;
import running.Main;

public abstract class Menu implements MouseListener,MouseMotionListener
{
	protected State specificState;
	protected int mouseX,mouseY;
	protected float x,y;
	protected float radius;
	protected float velX;
	protected float velY;
	/*
	 Color[]colours = {new Color(0.0f,0.0f,0.0f,0.0f),Color.BLACK};
	drawLightingOval(g,Main.WIDTH/2 - 150,Main.HEIGHT/2 - 150,300,300,0.79f,colours);
	 */
	public Menu(State specificState)
	{
		this.specificState = specificState;
		mouseX = 0;
		mouseY = 0;
	}
	protected abstract void render(Graphics2D g);
	
	public void update()
	{
		x += velX;
		y += velY;
		if(x <=0 || x >= Main.WIDTH) {velX *=-1;}
		if(y <=0 || y >=Main.HEIGHT) {velY*=-1;}
	}
	
	protected boolean mouseOver(int mx,int my,int x,int y,int width,int height)
	{
	  if(mx > x && mx < x+width)
	  {
	    if(my > y && my <y +height)
	    {
	      return true;
	    }
	  }
	  return false;
	}
	
	protected boolean mouseOver(int mx,int my,Rectangle bounds)
	{
	  if(mx > bounds.x && mx < bounds.x+bounds.width)
	  {
	    if(my > bounds.y && my <bounds.y +bounds.height)
	    {
	      return true;
	    }
	  }
	  return false;
	}
	protected boolean mouseOver(Rectangle bounds)
	{
	  if(mouseX > bounds.x && mouseX < bounds.x+bounds.width)
	  {
	    if(mouseY > bounds.y && mouseY <bounds.y +bounds.height)
	    {
	      return true;
	    }
	  }
	  return false;
	}
	
	protected void changeColour(Graphics2D g,Rectangle bounds,Color newCol,Color oldCol)
	{
		if(mouseOver(mouseX,mouseY,bounds.x,bounds.y,bounds.width,bounds.height)) {g.setColor(newCol);}
		else {g.setColor(oldCol);}
	}
	protected void changeColour(Graphics2D g,int x,int y, int width,int height,Color newCol,Color oldCol)
	{
		if(mouseOver(mouseX,mouseY,x,y,width,height)) {g.setColor(newCol);}
		else {g.setColor(oldCol);}
	}
	
	public void drawLighting(Graphics2D g,int width,int height,float blackness,Color[]colours)
	{
		update();
		Point2D centre = new Point2D.Float(x,y);
		float[] distance = {0.0f,1.0f};
		//Color[]colours = {new Color(0.0f,0.0f,0.0f,0.0f),Color.BLACK};//black is the backdrop
		RadialGradientPaint p = new RadialGradientPaint(centre,radius,distance,colours);
		g.setPaint(p);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,blackness));
		g.fillRect(0, 0,width,height);
		g.dispose();
	}
	public void drawLighting(Graphics2D g,int x1,int y1,int width,int height,float blackness,Color[]colours)
	{
		update();
		Point2D centre = new Point2D.Float(x,y);
		float[] distance = {0.0f,1.0f};
		//Color[]colours = {new Color(0.0f,0.0f,0.0f,0.0f),Color.BLACK};//black is the backdrop
		RadialGradientPaint p = new RadialGradientPaint(centre,radius,distance,colours);
		g.setPaint(p);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,blackness));
		g.fillRect(x1, y1,width,height);
		g.dispose();
	}
	public void drawLightingOval(Graphics2D g,int width,int height,float blackness,Color[]colours)
	{
		update();
		Point2D centre = new Point2D.Float(x,y);
		float[] distance = {0.0f,1.0f};
		//Color[]colours = {new Color(0.0f,0.0f,0.0f,0.0f),Color.BLACK};//black is the backdrop
		RadialGradientPaint p = new RadialGradientPaint(centre,radius,distance,colours);
		g.setPaint(p);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,blackness));
		g.fillOval(0, 0,width,height);
		g.dispose();
	}
	public void drawLightingOval(Graphics2D g,int x1,int y1,int width,int height,float blackness,Color[]colours)
	{
		update();
		Point2D centre = new Point2D.Float(x,y);
		float[] distance = {0.0f,1.0f};
		//Color[]colours = {new Color(0.0f,0.0f,0.0f,0.0f),Color.BLACK};//black is the backdrop
		RadialGradientPaint p = new RadialGradientPaint(centre,radius,distance,colours);
		g.setPaint(p);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,blackness));
		g.fillOval(x1, y1,width,height);
		g.dispose();
	}
	public void setCustomFont(Graphics2D g,String fontName,float fontSize) throws IOException,FontFormatException
	{
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Main.FONTSPATH + fontName+ ".ttf")).deriveFont(fontSize);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File((Main.FONTSPATH + fontName + ".ttf"))));
		     g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		    g.setFont(customFont);
	}
	public Font registerCustomFont(String fontName,float fontSize) throws IOException, FontFormatException {
		Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Main.FONTSPATH + fontName + ".ttf")).deriveFont(fontSize);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File((Main.FONTSPATH + fontName + ".ttf"))));
	    return customFont;
	}
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
