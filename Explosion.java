package Explosion;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import entities.Entity;
import entity_effects.Particle;
import id.ID;

public abstract class Explosion extends Entity{
	private float maxRadius;
	private float radius;
	private float damage;
	public Explosion(float x,float y,float maxRadius,ID id) {
		super(x,y,id);
		this.maxRadius = maxRadius;
		radius = 0;
	}
	
	public boolean intersects(Rectangle bounds) {
		double dx = Math.abs(bounds.x - x);
		double dy = Math.abs(bounds.y -y);
		double dRadius = Math.abs((bounds.width/2) + radius);
		if(((dx*dx) + (dy*dy)) < (dRadius * dRadius)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void removeBlocks(LinkedList<Entity>entity) {
		for(int i=0; i <entity.size(); i++) {
			Entity ent = entity.get(i);
			if(ent.getId() == ID.Block) {
				if(intersects(ent.getBounds())) {
					entity.remove(ent);
					addParticles(entity,ent);
				}
			}
		}
	}
	protected void addParticles(LinkedList<Entity>entity,Entity ent) {
		for(int i =0; i<radius/20;i++) {
			entity.add(new Particle(ent.getX(),ent.getY(),new Random().nextInt(9 - -9) + -9,new Random().nextInt(9 - -9) + -9,3,3,5,new Color(212,212,212),ID.Particle));
		}
	}
	
	
	public void setRadius(float radius) {this.radius = radius;}
	public float getRadius() {return radius;}
	public float getMaxRadius() {return maxRadius;}
	public void setMaxRadius(float maxRadius) {this.maxRadius = maxRadius;}
	public void setDamage(float damage) {this.damage = damage;}
	public float getDamage() {return damage;}
}
