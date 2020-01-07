package projectiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import Explosion.BasicExplosion;
import entities.Entity;
import game.Camera;
import id.ID;
import images.BufferedImageLoader;
import running.Main;

public class PlasmaBlast extends Projectile{
	private BufferedImage image;
	private BufferedImageLoader loader;
	public PlasmaBlast(float x,float y,int facing,Camera cam,ID id) {
		super(x,y,facing,cam,id);
		loader = new BufferedImageLoader();
		try {
		if(getFacing() == Main.RIGHT) {image = loader.loadImage("single_images","plasmaBlastRight.png");}
		else if(getFacing() == Main.LEFT) {image = loader.loadImage("single_images","plasmaBlastLeft.png");}
		}catch(Exception e) {image = loader.loadImage("single_images","PlasmaBlastRight.png");}
		width = image.getWidth();
		height = image.getHeight();
		velX = 25;
		setDamage(23f);
	}
	
	public void update(LinkedList<Entity>entity) {
		changeXDirection();
		for(int i=0; i<entity.size();i++) {
			Entity ent = entity.get(i);
			if(ent.getId() == ID.Block) {
				if(getBounds().intersects(ent.getBounds())) {
					entity.remove(this);
					entity.add(new BasicExplosion(x,y,150,20,Color.WHITE,ID.BasicExplosion));
				}
			}
		}
		outOfBounds(entity);
	}
	
	public static int plasmaBlastCount(LinkedList<Entity>entity)
	{
		int count = 0;
		for(int i = 0; i< entity.size();i++)
		{
			Entity ent = entity.get(i);
			if(ent instanceof PlasmaBlast)
			{
				count++;
			}
		}
		return count;
	}
	public void render(Graphics g) {
		g.drawImage(image,(int)x,(int)y,(int)width,(int)height,null);
	}
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,(int)width,(int)height);
	}
}
