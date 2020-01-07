package player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.Timer;

import Explosion.Explosion;
import enemy.Enemy;
import entities.Entity;
import entities.Mob;
import game.Camera;
import game.Game;
import game.Handler;
import game.State;
import id.ID;
import images.Animation;
import projectiles.Projectile;
import running.Main;

public abstract class Player extends Mob
{
	protected boolean attacking1;
	protected boolean attacking2;
	protected float runSpeed;
	protected Animation attack1AnimationRight,attack1AnimationLeft,attack2AnimationRight,attack2AnimationLeft;
	protected boolean movingX;
	protected float jumpHeight;
	protected BufferedImage image;
	protected float v1;
	protected Rectangle attackingBoundsRight;
	protected Rectangle attackingBoundsLeft;
	protected int yFinal;
	protected Game game;
	private boolean sliding;
	private float friction;
	private float initialFriction;
	private float damage1,damage2;
	protected Player(float x,float y,Camera cam,Game game,Handler handler,ID id)
	{
		super(x,y,cam,id);
		this.cam = cam;
		this.handler = handler;
		this.game = game;
		texture = Game.getTexture();
		setGravity(0.33f);
		setMaxGravity(9f);
		gravity = 0.33f;
		maxGravity = 9f;
		attacking1 = false;
		attacking2 = false;
		attackingBoundsRight = new Rectangle();
		attackingBoundsLeft = new Rectangle();
		firstBlock = handler.firstBlockPoint();
		lastBlock = handler.lastBlockPoint();
		health = 100;
		sliding = false;
		setInvincibilityTime(800);
	    invincibilityTimer = new Timer(getInvincibilityTime(),this);
	}
	
	protected void checkOutOfMapBounds()
	{
		if(x <= firstBlock.x)
		{
			x=  firstBlock.x;
		}
		//else if(x >= lastBlockX)
		//{
		//	x = lastBlockX;
		//}
	}
	protected void checkIfDead() {
		/*if you have fallen off the map*/
		if(y > lastBlock.y * 2.5) {
			Game.gameState = State.Dead;
			executeMusicWhenDead();
		}
		/*if your health is done*/
		if(health <= 0)
		{
			Game.gameState = State.Dead;
			executeMusicWhenDead();
		}
	}
	private void executeMusicWhenDead() {
		if(game.isMusicOn()) {game.stopSong();}
		try {Thread.sleep(90);}catch(InterruptedException e) {e.printStackTrace();}
		game.playTheme(game.magicaReprisePath);
	}
	 protected void updateAttackingBounds()
	 {
		attackingBoundsRight.setBounds((int)(x + width/1.5), (int)(y  +height/3.5), (int)(width - (width/1.5)), (int) (height/2));
		attackingBoundsLeft.setBounds((int)(x), (int)(y + height/3.5), (int)(width - width/1.5), (int) (height/2));
	 }
	 public void jump()
	 {
		 if(isOnGround())
		 {
			 if(!isJumping())
			 {
				 setJumping(true);
				 setVelY(jumpHeight);
			 }
		 }
	 }
	 protected void addFriction() {
		 if(getFacing() == Main.RIGHT) {
			 velX -= getFriction();
			 if(velX <= 0 ) {
				 sliding = false;
				 velX = 0;
			 }
		 }
		 else if(getFacing() == Main.LEFT) {
			 velX += getFriction();
			 if(velX >= 0 ) {
				 sliding = false;
				 velX = 0;
			 }
		 }
	 }
	 protected void updateDamage(boolean attacking,float damage) {
		 if(attacking) {this.damage = damage;}
	 }
	 protected void updateDamage() {
		 if(isAttacking1()) {this.damage = getDamage1();}
		 else if(isAttacking2()) {this.damage = getDamage2();}
		 else {this.damage = 0;}
	 }
	 public void attack(boolean attacking,Animation animation)
	 {
		 if(attacking)
			{
				if(animation.isOver())
				{
					setAttacking1(false);
					setAttacking2(false);
					animation.setCount(0);
				}
				animation.runAnimation();
			}
	 }
	 protected void drawMovement(Graphics g,BufferedImage [] imageArr)
	 {
		 if(!isMovingX() && isOnGround())
			{
				if(getFacing() == Main.RIGHT) {g.drawImage(imageArr[1],(int)x,(int)y,null);}
				else if(getFacing() == Main.LEFT) {g.drawImage(imageArr[10], (int)x, (int)y, null);}
			}
			else if(!isMovingX() && !isOnGround())
			{
				if(getFacing() == Main.RIGHT) {g.drawImage(imageArr[1],(int)x,(int)y,null);}
				else if(getFacing() == Main.LEFT) {g.drawImage(imageArr[10], (int)x, (int)y, null);}
			}
			/*if your on the ground.*/
			else if(isMovingX() && isOnGround())
			{
			if(getFacing() == Main.RIGHT) {rightWalk.drawAnimation(g, (int)x, (int)y);}
			else if(getFacing() == Main.LEFT) {leftWalk.drawAnimation(g, (int)x, (int)y);}
			}
			else if(!isOnGround() && isMovingX())
			{
				if(getFacing()== Main.RIGHT) {g.drawImage(imageArr[19],(int)getX(),(int)getY(),null);}///
				else if(getFacing() == Main.LEFT) {g.drawImage(imageArr[20],(int)getX(),(int)getY(),null);}///
				
			}
	 }
	 
	 public void updateMovementBounds(BufferedImage []imageArr)
	 {
		if(!isMovingX() && isOnGround())
		{
			if(getFacing() == Main.RIGHT) {image = imageArr[1];}
			else if(getFacing() == Main.LEFT) {image = imageArr[10];}
		}
		else if(!isMovingX() && !isOnGround())
		{
			if(getFacing() == Main.RIGHT) {image = imageArr[1];}
			else if(getFacing() == Main.LEFT) {image = imageArr[10];;}
		}
			/*if your on the ground.*/
		else if(isMovingX() && isOnGround())
		{
		if(getFacing() == Main.RIGHT) {image = getRightWalk().getCurrentImage();}
		else if(getFacing() == Main.LEFT) {image = getLeftWalk().getCurrentImage();}
		}
		else if(!isOnGround() && isMovingX())
		{
			if(getFacing()== Main.RIGHT) {image = imageArr[19];}
			else if(getFacing() == Main.LEFT) {image = imageArr[20];}	
		}
		    width = image.getWidth();
		    height = image.getHeight();
	 }
	 	protected void updateGettingHit(LinkedList<Entity>entity){
		for(int i=0; i< entity.size(); i++) {
			Entity ent = entity.get(i);
			if(ent instanceof Enemy) {
				Enemy enemy = (Enemy)ent;
				getHit(enemy);
			}
			else if(ent instanceof Projectile) {
				Projectile projectile = (Projectile)ent;
				getHit(projectile);
			}
			else if(ent instanceof Explosion) {
				Explosion explosion = (Explosion)ent;
				getHit(explosion);
			}
		}
	}
	public boolean isAttacking1() {return attacking1;}
	public void setAttacking1(boolean attacking1) {this.attacking1 = attacking1;}
	public boolean isAttacking2() {return attacking2;}
	public void setAttacking2(boolean attacking2) {this.attacking2 = attacking2;}
	public float getRunSpeed() {return runSpeed;}
	public void setRunSpeed(float runSpeed) {this.runSpeed = runSpeed;}
	public Animation getAttack1AnimationRight() {return attack1AnimationRight;}
	public void setAttack1AnimationRight(Animation attack1AnimationRight) {this.attack1AnimationRight = attack1AnimationRight;}
	public Animation getAttack2AnimationRight() {return attack2AnimationRight;}
	public void setAttack2AnimationRight(Animation attack2AnimationRight) {this.attack2AnimationRight = attack2AnimationRight;}
	public Animation getAttack1AnimationLeft() {return attack1AnimationLeft;}
	public void setAttack1AnimationLeft(Animation attack1AnimationLeft) {this.attack1AnimationLeft = attack1AnimationLeft;}
	public Animation getAttack2AnimationLeft() {return attack2AnimationLeft;}
	public void setAttack2AnimationLeft(Animation attack2AnimationLeft) {this.attack2AnimationLeft = attack2AnimationLeft;}
	public boolean isMovingX() {return movingX;}
	public void setMovingX(boolean movingX) {this.movingX = movingX;}
	public void setJumpHeight(float jumpHeight) {this.jumpHeight = jumpHeight;}
	public float getJumpHeight() {return jumpHeight;}
	public float getV1() {return v1;}
	public void setV1(float v1) {this.v1 = v1;}
	public Rectangle getAttackingBoundsRight() {return attackingBoundsRight;}
	public void setAttackingBoundsRight(Rectangle attackingBoundsRight) {this.attackingBoundsRight = attackingBoundsRight;}
	public Rectangle getAttackingBoundsLeft() {return attackingBoundsLeft;}
	public void setAttackingBoundsLeft(Rectangle attackingBoundsLeft) {this.attackingBoundsLeft = attackingBoundsLeft;}
	public void setSliding(boolean sliding) {this.sliding = sliding;}
	public boolean isSliding() {return sliding;}
	public void setFriction(float friction) {this.friction = friction;}
	public float getFriction() {return friction;}
	public float getInitialFriction() {return initialFriction;}
	public void setInitialFriction(float initialFriction) {this.initialFriction = initialFriction;}
	public float getDamage1() {return damage1;}
	public void setDamage1(float damage1) {this.damage1 = damage1;}
	public float getDamage2() {return damage2;}
	public void setDamage2(float damage2) {this.damage2 = damage2;}	
}
