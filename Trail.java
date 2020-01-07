package entity_effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import entities.Entity;
import id.ID;

public class Trail extends Entity
{
	private float alpha;//usually 1.
	private float life;
	private Color col;
	public Trail(float x,float y,float width,float height,Color col,float life,float alpha,ID id)
	{
		super(x,y,id);
		this.life = life;
		this.col = col;
		this.alpha = alpha;
		this.width  = width;
		this.height = height;
	}
	
	public void update(LinkedList<Entity>entity)
	{
		if(alpha > life)
		{
			alpha -= life - 0.001f;
		}
		else {
			entity.remove(this);
		}
	}
	public void render(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
	    g2d.setComposite(makeTransparent(alpha));
	    g2d.setColor(col);
	    g.fillRect((int)x,(int)y,(int)width,(int)height);
	    g2d.setComposite(makeTransparent(1));//make it fade out
	}
	private AlphaComposite makeTransparent(float alpha)
	  {
	    int type = AlphaComposite.SRC_OVER;
	    return (AlphaComposite.getInstance(type,alpha));
	  }
	public Rectangle getBounds()
	{
		return new Rectangle((int)x,(int)y,(int)width,(int)height);
	}
}
