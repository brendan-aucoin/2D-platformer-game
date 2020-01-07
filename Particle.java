package entity_effects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import entities.Entity;
import id.ID;

public class Particle extends Entity
{
	private float life;
	private Color col;
	public Particle(float x,float y,float velX,float velY,float width,float height,float life,Color col,ID id)
	{
		super(x,y,id);
		this.velX = velX;
		this.velY = velY;
		this.life = life;
		this.width = width;
		this.height = height;
		this.col = col;
	}
	public void update(LinkedList<Entity>entity)
	{
		x+= velX;
		y+=velY;
		life--;
		if(life<=0)
		{
			entity.remove(this);
		}
	}
	public void render(Graphics g)
	{
		g.setColor(col);
		g.fillRect((int)x, (int)y, (int)width, (int)(height));
	}
	
	public Rectangle getBounds()
	{
		return (new Rectangle((int)x,(int)y,(int)width,(int)height));
	}
}
