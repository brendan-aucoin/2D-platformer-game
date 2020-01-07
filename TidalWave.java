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

public class TidalWave extends Projectile
{
	private Animation rightAnimation,leftAnimation;
	
	public TidalWave(float x,float y,int facing,Camera cam,ID id)
	{
		super(x,y,facing,cam,id);
		rightAnimation = new Animation(4,texture.tidalWave[1],texture.tidalWave[2],texture.tidalWave[3],texture.tidalWave[4],texture.tidalWave[4],texture.tidalWave[4]);
		leftAnimation = new Animation(4,texture.tidalWave[5],texture.tidalWave[6],texture.tidalWave[7],texture.tidalWave[8],texture.tidalWave[8],texture.tidalWave[8]);
		setDamage(6f);
	}
	
	public void update(LinkedList<Entity>entity)
	{
		for(int i =0; i < entity.size();i++)
		{
			Entity ent = entity.get(i);
			if(ent.getId() == ID.Block)
			{
				if((rightAnimation.isOver() || leftAnimation.isOver())){checkBlockIntersection(entity, ent);}
				if(getBounds().intersects(ent.getBounds()) && (rightAnimation.isOver() || leftAnimation.isOver())) {
				addParticles(entity);}
				
			}
		}
		
		if(getFacing() == Main.RIGHT) {
			if(!rightAnimation.isOver())
			{
				rightAnimation.runAnimation();
				if(rightAnimation.getCurrentImage() != null) {
					width = rightAnimation.getCurrentImage().getWidth();
					height = rightAnimation.getCurrentImage().getHeight();
				}
			}
			else
			{
				width = texture.tidalWave[4].getWidth();
				height = texture.tidalWave[4].getHeight();
				entity.remove(this);
			}
		}
		else if(getFacing() == Main.LEFT) {
			if(!leftAnimation.isOver())
			{
				leftAnimation.runAnimation();
				if(leftAnimation.getCurrentImage() != null)
				{
					width = leftAnimation.getCurrentImage().getWidth();
					height = leftAnimation.getCurrentImage().getHeight();
				}
				
			}
			else
			{
				width = texture.tidalWave[8].getWidth();
				height = texture.tidalWave[8].getHeight();
				entity.remove(this);
			}
		}
	}
	
	public void render(Graphics g)
	{
		if(getFacing() == Main.RIGHT && !rightAnimation.isOver())
		{
			rightAnimation.drawAnimation(g,(int) ((int)x),(int)y);
		}
		else if(getFacing() == Main.LEFT && !leftAnimation.isOver())
		{
			leftAnimation.drawAnimation(g, (int) ((int)x- width/2),(int) (y));
		}
	}
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,(int)width,(int)height);
	}
	
	private void addParticles(LinkedList<Entity>entity)
	{
		for(int i=0; i< 50; i++)
		{
			entity.add(new Particle(x + width/2,y + height/2,new Random().nextInt(8 - -8) + -8,new Random().nextInt(8 - -8) + -8,2,2,35,new Color(73,160,240),ID.Particle));
		}
	}
	public static int tidalWaveCount(LinkedList<Entity>entity)
	{
		int count = 0;
		for(int i = 0; i< entity.size();i++)
		{
			Entity ent = entity.get(i);
			if(ent instanceof TidalWave)
			{
				count++;
			}
		}
		return count;
	}
}
