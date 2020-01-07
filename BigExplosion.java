package Explosion;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import entities.Entity;
import game.Game;
import id.ID;
import images.Animation;
import images.Texture;

public class BigExplosion extends Explosion{
	
	private Animation animation;
	private Texture texture = Game.getTexture();
	private float rate;
	public BigExplosion(float x,float y,float maxRadius,ID id) {
		super(x,y,maxRadius,id);
		animation = new Animation(1,texture.explosion[1],texture.explosion[2],texture.explosion[3],texture.explosion[4],texture.explosion[5],texture.explosion[6],texture.explosion[7],texture.explosion[8],texture.explosion[9],texture.explosion[10],texture.explosion[11],texture.explosion[12],texture.explosion[13],texture.explosion[14],texture.explosion[15],texture.explosion[16],texture.explosion[17],texture.explosion[18]);
		rate = 10;
		setDamage(55f);
	}
	
	public void update(LinkedList<Entity>entity) {
		animation.runAnimation();
		if(animation.getCurrentImage() != null) {
			setRadius(getRadius() + rate);
			if(getRadius() >= animation.getCurrentImage().getWidth()/2) {
				setRadius(animation.getCurrentImage().getWidth()/2 + animation.getCurrentImage().getHeight()/2);
			}
			width = animation.getCurrentImage().getWidth();
			height = animation.getCurrentImage().getHeight();
		}
		if(animation.isOver()) {
			entity.remove(this);
		}
		removeBlocks(entity);
	}
	
	public void render(Graphics g) {
		animation.drawAnimation(g, (int)(x - width/2), (int)(y));
	}
	public Rectangle getBounds() {
		return new Rectangle((int)x ,(int)y,(int)width,(int)height);
	}
}
