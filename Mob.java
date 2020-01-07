package entities;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import Explosion.Explosion;
import enemy.Enemy;
import game.Camera;
import game.Game;
import game.Handler;
import game.State;
import id.ID;
import images.Animation;
import images.Texture;
import player.Player;
import projectiles.Projectile;

public abstract class Mob extends Entity implements ActionListener
{
	public Texture texture;
	protected float gravity;
	protected float maxGravity;
	protected boolean falling,jumping;
	protected int health;//maybe float
	protected float damage;
	protected float defense;
	protected Animation leftWalk,rightWalk;
	protected int facing;
	protected Camera cam;
	protected Handler handler;
	protected boolean onGround;
	protected Point firstBlock,lastBlock;
	private boolean invincible;
	protected Timer invincibilityTimer;
	private int invincibilityTime;
	public Mob(float x,float y,Camera cam,ID id)
	{
		super(x,y,id);
		this.cam = cam;
		invincibilityTime = 0;
		invincibilityTimer = new Timer(invincibilityTime,this);
	}
	
	
	protected void checkIfDead()
	{
		/*if you have fallen off the map*/
		if(y > lastBlock.y * 2.5) {
			Game.gameState = State.Dead;
		}
		/*if your health is done*/
		if(health <= 0)
		{
			Game.gameState = State.Dead;
		}
	}
	
	protected void checkOnGround(Entity ent)
	{
		if(ent.getId() == ID.Block)
		{
			if(getBounds().intersects(ent.getBounds()))
			{
				onGround = true;
			}
		}
	}
	
	protected final void addGravity()
	{
		if(falling || jumping)
		{
			velY += gravity;
			if(velY > maxGravity){velY = maxGravity;}
		}
	}
	
	protected void blockCollision(Entity ent)
	  {
	    if(ent.getId() == ID.Block)
	      {   
	        if(getTopBounds().intersects(ent.getBounds()))
	        {
	          y = ent.getY() + ent.getHeight();
	          velY = 0;
	        }
	        if(getBounds().intersects(ent.getBounds()))
	        {
	          y = ent.getY() - height + (height/10);
	          velY = 0;
	          this.falling = false;
	          this.jumping = false;
	        }
	        else if(!getBounds().intersects(ent.getBounds()))
	        {
	          falling = true;
	        }
	        
	        if(getRightBounds().intersects(ent.getBounds()))
	        {
	            x = ent.getX() - width;
	        }
	        if(getLeftBounds().intersects(ent.getBounds()))
	        {
	        	setX(ent.getX() + getWidth());
	            x = ent.getX() + width;
	        }
	        
	      }
	  }
	
	protected void updateDimensions(Animation animation)
	 {
		 if(animation.getCurrentImage() != null) 
		 {
			width = animation.getCurrentImage().getWidth();
			height = animation.getCurrentImage().getHeight();
		}
	 }
	
	public void getHit(Enemy enemy) {
		if(!isInvincible()) {
			if(enemy.intersects(getBounds()) || intersects(getRightBounds()) || intersects(getTopBounds()) || intersects(getLeftBounds())) {
				float damageTaken = enemy.getDamage() - getDefense();
				if(damageTaken >0) {
				 health -= damageTaken;
				 setInvincible(true);
				 invincibilityTimer.start();}
			}
		}
	}
	public void getHit(Player player) {
		if(!isInvincible()) {
			if(intersects(player.getBounds()) || intersects(player.getRightBounds()) || intersects(player.getTopBounds()) || intersects(player.getLeftBounds())) {
				float damageTaken = player.getDamage() - getDefense();
				if(damageTaken >0) {				
				health -= damageTaken;
				 setInvincible(true);
				 invincibilityTimer.start();}
			}
		}
	}
	public void getHit(Explosion explosion) {
		if(!isInvincible()) {
			if(explosion.intersects(getBounds()) || explosion.intersects(getRightBounds()) || explosion.intersects(getTopBounds()) || explosion.intersects(getLeftBounds())) {
				float damageTaken = explosion.getDamage() - getDefense();
				if(damageTaken >0) {
				health -= damageTaken;
				 setInvincible(true);
				 invincibilityTimer.start();}
			}
		}
	}
	public void getHit(Projectile projectile) {
		if(!isInvincible()) {
			if(intersects(projectile.getBounds())){
				float damageTaken = projectile.getDamage() - getDefense();
				if(damageTaken >0) {
				 health -= damageTaken;
				 setInvincible(true);
				 invincibilityTimer.start();}
			}
		}
	}
	public boolean intersects(Rectangle bounds) {
		if(getTopBounds().intersects(bounds) || getRightBounds().intersects(bounds) || getLeftBounds().intersects(bounds) || getBounds().intersects(bounds)) {
			return true;
		}
		else {return false;}
	}
	
	/*the invincibity time method*/
	public void actionPerformed(ActionEvent e) {
		setInvincible(false);
		invincibilityTimer.stop();
	}
	public abstract Rectangle getTopBounds();
	public abstract Rectangle getLeftBounds();
	public abstract Rectangle getRightBounds();
	
	public float getGravity() {return gravity;}
	public void setGravity(float gravity) {this.gravity = gravity;}
	public float getMaxGravity() {return maxGravity;}
	public void setMaxGravity(float maxGravity) {this.maxGravity = maxGravity;}
	public boolean isFalling() {return falling;}
	public void setFalling(boolean falling) {this.falling = falling;}
	public boolean isJumping() {return jumping;}
	public void setJumping(boolean jumping) {this.jumping = jumping;}
	public int getHealth() {return health;}
	public void setHealth(int health) {this.health = health;}
	public float getDamage() {return damage;}
	public void setDamage(float damage) {this.damage = damage;}
	public float getDefense() {return defense;}
	public void setDefense(float defense) {this.defense = defense;}
	public Animation getLeftWalk() {return leftWalk;}
	public void setLeftWalk(Animation leftWalk) {this.leftWalk = leftWalk;}
	public Animation getRightWalk() {return rightWalk;}
	public void setRightWalk(Animation rightWalk) {this.rightWalk = rightWalk;}
	public int getFacing() {return facing;}
	public void setFacing(int facing) {this.facing = facing;}
	public boolean isOnGround() {return onGround;}
	public void setOnGround(boolean onGround) {this.onGround = onGround;}
	public Camera getCam() {return new Camera(cam);}
	public void setCam(Camera cam) {this.cam = cam;}
	public Point getFirstBlock() {return firstBlock;}
	public void setFirstBlock(Point firstBlock) {this.firstBlock = firstBlock;}
	public Point getLastBlock() {return lastBlock;}
	public void setLastBlock(Point lastBlock) {this.lastBlock = lastBlock;}
	public boolean isInvincible() {return invincible;}
	public void setInvincible(boolean invincible) {this.invincible = invincible;}
	public int getInvincibilityTime() {return invincibilityTime;}
	public void setInvincibilityTime(int invincibilityTime) {this.invincibilityTime = invincibilityTime;}
	
}
