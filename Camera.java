package game;

import entities.Entity;
import running.Main;

public class Camera 
{
	private float x,y;
	public Camera(float x,float y)
	{
		this.x = x;
		this.y = y;
	}
	public Camera(Camera cam)
	{
		this.x = cam.x;
		this.y = cam.y;
	}
	public void update(Entity player)
	{
		x = -player.getX() + Main.WIDTH /2;
		y = -player.getY() + Main.HEIGHT/2;
	}
	
	public float getX(){return x;}
	public float getY(){return y;}
	public void setX(float x){this.x = x;}
	public void setY(float y){this.y = y;}
}
