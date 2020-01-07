package projectiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import entities.Entity;
import entity_effects.Particle;
import game.Camera;
import id.ID;
import images.Animation;
import running.Main;

public class FireBall extends Projectile
{
	private Animation rightAnimation,leftAnimation;
	public FireBall(float x,float y,int facing,Camera cam,ID id)
	{
		super(x,y,facing,cam,id);
		rightAnimation = new Animation(3,texture.fireBall[1],texture.fireBall[2],texture.fireBall[3],texture.fireBall[4],texture.fireBall[6]);
		leftAnimation = new Animation(3,texture.fireBall[7],texture.fireBall[8],texture.fireBall[9],texture.fireBall[10],texture.fireBall[12]);
		velX = 9;
		setDamage(3f);
	}
	public void update(LinkedList<Entity>entity)
	{
		changeXDirection();
		for(int i =0; i < entity.size();i++)
		{
			Entity ent = entity.get(i);
			if(ent.getId() == ID.Block)
			{
				checkBlockIntersection(entity, ent);
				if(getBounds().intersects(ent.getBounds())) {
				addParticles(entity);}
			}
		}
		if(getFacing() == Main.RIGHT)
		{
			if(rightAnimation.isOver() == false)
			{
				rightAnimation.runAnimation();
				if(rightAnimation.getCurrentImage()!=null) {
				width = rightAnimation.getCurrentImage().getWidth();
				height = rightAnimation.getCurrentImage().getHeight();
				}
			}
		}
		else if(getFacing() == Main.LEFT)
		{
			if(leftAnimation.isOver() == false)
			{
				leftAnimation.runAnimation();
				if(leftAnimation.getCurrentImage()!=null) {width = leftAnimation.getCurrentImage().getWidth();
				height = leftAnimation.getCurrentImage().getHeight();
				}
			}
		}
		else {
			width = texture.fireBall[6].getWidth();
			height = texture.fireBall[6].getHeight();
		}
		outOfBounds(entity);
	}
	
	public void render(Graphics g)
	{
		if(!rightAnimation.isOver() && getFacing() == Main.RIGHT)
		{
			rightAnimation.drawAnimation(g,(int) x, (int)y);
		}
		else if(!leftAnimation.isOver() && getFacing() == Main.LEFT)
		{
			leftAnimation.drawAnimation(g,(int) x, (int)y);
		}
		else
		{
			if(getFacing() == Main.RIGHT) {g.drawImage(texture.fireBall[6],(int)x,(int)y,null);}
			else if(getFacing() == Main.LEFT) {g.drawImage(texture.fireBall[12],(int)x,(int)y,null);}
		}
	}
	/*finds out how many fireball objects are currently on screen*/
	public static int fireBallCount(LinkedList<Entity>entity)
	{
		int count = 0;
		for(int i = 0; i< entity.size();i++)
		{
			Entity ent = entity.get(i);
			if(ent instanceof FireBall)
			{
				count++;
			}
		}
		return count;
	}
	private void addParticles(LinkedList<Entity>entity)
	{
		for(int i=0; i< 25; i++)
		{
			entity.add(new Particle(x + width/2,y + height/2,new Random().nextInt(5 - -5) + -5,new Random().nextInt(5 - -5) + -5,5,5,35,Color.RED,ID.Particle));
		}
	}
	public Rectangle getBounds()
	{
		return (new Rectangle((int)x,(int)y,(int)width,(int)height));
	}
}
