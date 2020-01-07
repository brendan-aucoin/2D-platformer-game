package projectiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

import entities.Entity;
import entity_effects.Particle;
import game.Camera;
import id.ID;
import images.BufferedImageLoader;
import running.Main;

public class DarkMagic extends Projectile{
	
	/* example to the right
	 				//straight
					entity.add(new DarkMagic(x,y - height,12,0,getFacing(),0,cam,ID.PlasmaBlast));
					//up and right
					entity.add(new DarkMagic(x,y - height,12,-4,getFacing(),-45,cam,ID.PlasmaBlast));
					//down and right
					entity.add(new DarkMagic(x,y - height,12,4,getFacing(),45,cam,ID.PlasmaBlast));
					
		example to the left
					//straight
					entity.add(new DarkMagic(x,y - height,12,0 ,getFacing(),0,cam,ID.PlasmaBlast));
					//up and to the left
					entity.add(new DarkMagic(x,y - height,12,-4,getFacing(),45,cam,ID.PlasmaBlast));
					//down and left
					entity.add(new DarkMagic(x,y - height,12,4,getFacing(),-45,cam,ID.PlasmaBlast));
	 */
	
	private BufferedImage image;
	private BufferedImageLoader loader;
	private int angle;
	public DarkMagic(float x,float y,float velX,float velY,int facing,int angle,Camera cam,ID id) {
		super(x,y,facing,cam,id);
		loader = new BufferedImageLoader();
		if(getFacing() == Main.RIGHT) {image = loader.loadImage("single_images","darkMagicWaveRight.png");}
		else if(getFacing() == Main.LEFT) {image = loader.loadImage("single_images","darkMagicWaveLeft.png");}
		this.angle = angle;
		this.velX = velX;
		this.velY = velY;
		width = image.getWidth();
		height= image.getHeight();
		setDamage(29f);
	}
	
	public void update(LinkedList<Entity>entity) {
		changeXDirection();
		y += velY;
		for(int i =0; i <entity.size(); i++) {
			Entity ent = entity.get(i);
			if(ent.getId() == ID.Block) {
				if(getBounds().intersects(ent.getBounds())) {
					entity.remove(this);
					addParticles(entity);
				}
			}
		}
			
		outOfBounds(entity);
	}
	
	public void render(Graphics g) {
		AffineTransform at = AffineTransform.getTranslateInstance(x,y);
		at.rotate(Math.toRadians(angle),image.getWidth()/2,image.getHeight()/2);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(image,at,null);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,(int)width,(int)height);
	}
	
	private void addParticles(LinkedList<Entity>entity)
	{
		for(int i=0; i< 10; i++)
		{
			entity.add(new Particle(x + width/2,y + height/2,new Random().nextInt(15 - -15) + -15,new Random().nextInt(15 - -15) + -15,2,2,35,Color.BLACK,ID.Particle));
		}
	}
	
	public static int darkMagicCount(LinkedList<Entity>entity)
	{
		int count = 0;
		for(int i = 0; i< entity.size();i++)
		{
			Entity ent = entity.get(i);
			if(ent instanceof DarkMagic)
			{
				count++;
			}
		}
		return count;
	}
}
