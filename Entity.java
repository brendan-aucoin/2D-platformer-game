package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import game.Camera;
import id.ID;
import running.Main;

public abstract class Entity 
{
	protected float x,y;
	protected float velX,velY;
	protected float width,height;
	protected ID id;
	protected Entity(float x,float y,ID id)
	{
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public boolean onScreen(Camera cam)
	{
		if(x + width/2 >=-cam.getX() && x + width/2 <= -cam.getX() + Main.WIDTH)
		{
			if(y + height/2 >= -cam.getY() && y + height/2 <= -cam.getY() + Main.HEIGHT)
			{
				return true;
			}
		}
		return false;
	}
	
	public abstract void update(LinkedList<Entity>entity);
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public String toString(){return id.toString();}
	/*getters and setters*/
	public float getX(){return x;}
	public void setX(float x){this.x = x;}
	public float getY(){return y;}
	public void setY(float y){this.y = y;}
	public float getVelX(){return velX;}
	public void setVelX(float velX){this.velX = velX;}
	public float getVelY(){return velY;}
	public void setVelY(float velY){this.velY = velY;}
	public float getWidth(){return width;}
	public void setWidth(float width){this.width = width;}
	public float getHeight(){return height;}
	public void setHeight(float height){this.height = height;}
	public ID getId(){return id;}
	public void setId(ID id){this.id = id;}
	
	
}
