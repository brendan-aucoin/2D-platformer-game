package projectiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import Explosion.BigExplosion;
import entities.Entity;
import game.Camera;
import id.ID;
import images.BufferedImageLoader;
import running.Main;

public class Meteor extends Projectile{
	private BufferedImage image;
	private BufferedImageLoader loader;
	public Meteor(float x,float y,int facing,Camera cam,ID id) {
		super(x,y,facing,cam,id);
		loader = new BufferedImageLoader();
		if(getFacing() == Main.RIGHT) {image = loader.loadImage("single_images","meteorRight.png");}
		else if(getFacing() == Main.LEFT) {image = loader.loadImage("single_images","meteorLeft.png");}
		velX = 11;
		velY = 10;
		width = image.getWidth();
		height = image.getHeight();
		setDamage(50f);
	}
	public void update(LinkedList<Entity>entity){
		changeXDirection();
		y+= velY;
		for(int i=0; i < entity.size(); i++) {
			Entity ent = entity.get(i);
			if(ent.getId() == ID.Block) {
				if(getBounds().intersects(ent.getBounds())) {
					entity.remove(this);
					entity.add(new BigExplosion(x,y,300,ID.BigExplosion));
				}
			}
		}
		
		outOfBounds(entity);
	}
	
	public void render(Graphics g) {
		g.drawImage(image,(int)x,(int)y,(int)width,(int)height,null);
	}
	public static int meteorCount(LinkedList<Entity>entity)
	{
		int count = 0;
		for(int i = 0; i< entity.size();i++)
		{
			Entity ent = entity.get(i);
			if(ent instanceof Meteor)
			{
				count++;
			}
		}
		return count;
	}
	public Rectangle getBounds() {return new Rectangle((int)x,(int)y,(int)width,(int)height);
	}
}
