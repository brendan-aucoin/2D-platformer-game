package projectiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

import entities.Entity;
import entity_effects.Particle;
import game.Camera;
import id.ID;
import running.Main;

public class LightningBolt extends Projectile
{
	private BufferedImage image,cloudImage;
	public LightningBolt(float x,float y,int facing,Camera cam,ID id)
	{
		super(x,y,facing,cam,id);
		velY = 15;
		image = texture.lightning[1];
		cloudImage = texture.lightning[0];
		width = image.getWidth();
		height = image.getHeight();
		setDamage(7f);
	}
	
	public void update(LinkedList<Entity>entity)
	{
		y += velY;
		for(int i =0; i <entity.size(); i++)
		{
			Entity ent = entity.get(i);
			if(ent.getId() == ID.Block)
			{
				checkBlockIntersection(entity,ent);
				if(getBounds().intersects(ent.getBounds())) {
					addParticles(entity);}
			}
		}
		if(y + height/2 > -cam.getY() + Main.HEIGHT){entity.remove(this);}
	}
	
	public void render(Graphics g)
	{
		g.drawImage(cloudImage, (int)x-cloudImage.getWidth()/3, (int)(-cam.getY()), null);
		g.drawImage(image,(int)x,(int)y,(int)width,(int)(height),null);
	}
	private void addParticles(LinkedList<Entity>entity)
	{
		for(int i=0; i< 150; i++)
		{
			entity.add(new Particle(x + width/2,y + height/2,new Random().nextInt(15 - -15) + -15,new Random().nextInt(15 - -15) + -15,2,2,35,new Color(250,250,220),ID.Particle));
		}
	}
	
	/*finds out how many lightning bolt objects are currently on screen*/
	public static int lightningBoltCount(LinkedList<Entity>entity)
	{
		int count = 0;
		for(int i = 0; i< entity.size();i++)
		{
			Entity ent = entity.get(i);
			if(ent instanceof LightningBolt)
			{
				count++;
			}
		}
		return count;
	}
	
	public Rectangle getBounds()
	{
		return (new Rectangle((int)x,(int)y,(int)width,(int)(height)));
	}
}
