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

public class WaterDragon extends Projectile{
	private Animation rightAnimation,leftAnimation;
	public WaterDragon(float x,float y,int facing,Camera cam,ID id) {
		super(x,y,facing,cam,id);
		rightAnimation = new Animation(5,texture.waterDragon[1],texture.waterDragon[2],texture.waterDragon[3],texture.waterDragon[4],texture.waterDragon[5],texture.waterDragon[6],texture.waterDragon[7],texture.waterDragon[8]);
		leftAnimation = new Animation(5,texture.waterDragon[9],texture.waterDragon[10],texture.waterDragon[11],texture.waterDragon[12],texture.waterDragon[13],texture.waterDragon[14],texture.waterDragon[15],texture.waterDragon[16]);
		velX = 7.5f;
		setDamage(15f);
	}
	
	public void update(LinkedList<Entity>entity) {
		changeXDirection();
		/*intersecion*/
		for(int i =0; i< entity.size();i++) {
			Entity ent = entity.get(i);
			if(ent.getId() == ID.Block) {
				if(getBounds().intersects(ent.getBounds()) && (rightAnimation.isOver() || leftAnimation.isOver())) {
					addParticles(entity);
				}
			}
		}
		if(getFacing() == Main.RIGHT) {
			if(!rightAnimation.isOver()) {
				rightAnimation.runAnimation();
				if(rightAnimation.getCurrentImage() != null) {
				width = rightAnimation.getCurrentImage().getWidth();
				height = rightAnimation.getCurrentImage().getHeight();}
			}
			
			else {
				width = texture.waterDragon[8].getWidth();
				height = texture.waterDragon[8].getHeight();
				entity.remove(this);
			}
			
		}
		else if(getFacing() == Main.LEFT) {
			if(!leftAnimation.isOver()) {
				leftAnimation.runAnimation();
				if(leftAnimation.getCurrentImage() != null) {
				width = leftAnimation.getCurrentImage().getWidth();
				height = leftAnimation.getCurrentImage().getHeight();}
			}
			
			else {
				width = texture.waterDragon[16].getWidth();
				height = texture.waterDragon[16].getHeight();
				entity.remove(this);
			}
			
		}
	}
	
	private void addParticles(LinkedList<Entity>entity)
	{
		for(int i=0; i< 50; i++)
		{
			entity.add(new Particle(x + width/2,y + height/2,new Random().nextInt(50 - -50) + -50,new Random().nextInt(50 - -50) + -50,6,6,35,new Color(160,192,247),ID.Particle));
		}
	}
	
	public void render(Graphics g) {
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
}
