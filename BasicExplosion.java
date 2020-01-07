package Explosion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import entities.Entity;
import id.ID;

public class BasicExplosion extends Explosion {
	private float speed;
	private Color col;
	public BasicExplosion(float x,float y,float maxRadius,float speed,Color col,ID id) {
		super(x,y,maxRadius,id);
		this.speed = speed;
		this.col = col;
		setDamage(30f);
	}
	
	public void update(LinkedList<Entity>entity) {
		setRadius(getRadius() + speed);
		if(getRadius() >= getMaxRadius()) {setRadius(getMaxRadius());}
		if(getRadius() == getMaxRadius()) {entity.remove(this);}
		
		removeBlocks(entity);
	}
	
	public void render(Graphics g) {
		g.setColor(getCol());
		g.fillOval((int)(x),(int)y,(int)getRadius(),(int)getRadius());
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,(int)width,(int)height);
	}
	public float getSpeed() {return speed;}
	public void setSpeed(float speed) {this.speed = speed;}
	public Color getCol() {return col;}
	public void setCol(Color col) {this.col = col;}
	
	
}
