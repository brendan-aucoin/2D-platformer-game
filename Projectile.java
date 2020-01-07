package projectiles;

import java.util.LinkedList;

import entities.Entity;
import game.Camera;
import game.Game;
import id.ID;
import images.Texture;
import running.Main;

public abstract class Projectile extends Entity
{
	protected Texture texture;
	private int facing;
	protected Camera cam;
	private float damage;
	public Projectile(float x,float y,int facing,Camera cam,ID id)
	{
		super(x,y,id);
		this.texture = Game.getTexture();
		this.facing = facing;
		this.cam = cam;
	}
	protected void changeXDirection()
	{
		if(facing == Main.RIGHT) {x += velX;}
		else if(facing == Main.LEFT) {x -=velX;}
	}
	
	protected void checkBlockIntersection(LinkedList<Entity>entity,Entity ent)
	{
		if(getBounds().intersects(ent.getBounds()))
		{
			entity.remove(this);
		}
	}
	protected void outOfBounds(LinkedList<Entity>entity)
	{
		if(getX() - width/2 > -cam.getX() + Main.WIDTH)
		{
			entity.remove(this);
		}
		
		else if((getX() + width) < -cam.getX())
		{
			entity.remove(this);
		}
	}
	
	public void setFacing(int facing) {this.facing = facing;}
	public int getFacing() {return facing;}
	public void setDamage(float damage) {this.damage = damage;}
	public float getDamage() {return damage;}
}
