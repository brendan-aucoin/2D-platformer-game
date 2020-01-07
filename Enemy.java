package enemy;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

import entities.Entity;
import entities.Mob;
import entity_effects.Particle;
import game.Camera;
import id.EnemyID;
import id.ID;
import player.Player;
import running.Main;

public abstract class Enemy extends Mob{
	private float distanceX;
	private float distanceY;
	private float range;
	private EnemyID enemyId;
	private Color bloodColour;
	private int bloodDistance;
	public Enemy(float x,float y,Camera cam,ID id) {
		super(x,y,cam,id);
	}
	
	protected void calculateDistance(Player player) {
		distanceX = Math.abs(player.getX() - getX());
		distanceY = Math.abs(player.getY() - getY());
	}
	
	protected void getHit(LinkedList<Entity>entity,Player player) {
		bleed(entity);
		if(player.isAttacking1() || player.isAttacking2()) {
			if(getBounds().intersects(player.getAttackingBoundsLeft()) || getBounds().intersects(player.getAttackingBoundsRight())){
				/*do damage calculations*/
				health -= (player.getDamage() - defense);
			}
		}
	}
	protected void calculateFacing(Player player) {
		if(x < player.getX()) {
			setFacing(Main.RIGHT);
		}
		else if(x > player.getX()) {
			setFacing(Main.LEFT);
		}
		else {
			setFacing(Main.RIGHT);
		}
	}
	protected boolean inRangeOfPlayer() {
		if(distanceX <= range) {
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void bleed(LinkedList<Entity>entity) {
		for(int i=0; i< 25; i++)
		{
			entity.add(new Particle(x + width/2,y + height/2,new Random().nextInt(bloodDistance - -bloodDistance) + -bloodDistance,new Random().nextInt(bloodDistance - -bloodDistance) + -bloodDistance,5,5,35,bloodColour,ID.Particle));
		}
	}
	
	public float getDistanceX() {return distanceX;}
	public void setDistanceX(float distanceX) {this.distanceX = distanceX;}
	public float getRange() {return range;}
	public void setRange(float range) {this.range = range;}
	public EnemyID getEnemyId() {return enemyId;}
	public void setEnemyId(EnemyID enemyId) {this.enemyId = enemyId;}
	public float getDistanceY() {return distanceY;}
	public void setDistanceY(float distanceY) {this.distanceY = distanceY;}
	public Color getBloodColour() {return bloodColour;}
	public void setBloodColour(Color bloodColour) {this.bloodColour = bloodColour;}
	public int getBloodDistance() {return bloodDistance;}
	public void setBloodDistance(int bloodDistance) {this.bloodDistance = bloodDistance;}
	
	
	
}
