package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import entities.Entity;
import game.Camera;
import game.Game;
import game.Handler;
import id.ID;
import images.Animation;
import running.Main;
@SuppressWarnings("unused")
public class Knight extends Player
{
	private int coolDown;
	public Knight(float x,float y,Camera cam,Game game,Handler handler,ID id)
	{
		super(x,y,cam,game,handler,id);
		image = texture.knight[1];
		width = image.getWidth();
		height = image.getHeight();
		velX = 0;
		velY = 0;
		jumpHeight = -6.7f;
		v1 = 4.2f;
		setInitialFriction(0.7f);
	    setFriction(getInitialFriction());
		runSpeed =0;
		defense = 30;
		setDamage1(13);
		setDamage2(10);
		coolDown = 0;
		/*the animation*/
		rightWalk = new Animation(4,texture.knight[1],texture.knight[2],texture.knight[3],texture.knight[4],texture.knight[5],texture.knight[6],texture.knight[7],texture.knight[8],texture.knight[9]);
		leftWalk = new Animation(4,texture.knight[10],texture.knight[11],texture.knight[12],texture.knight[13],texture.knight[14],texture.knight[15],texture.knight[16],texture.knight[17],texture.knight[18]);
		
		attack1AnimationRight = new Animation(3,texture.knight[21],texture.knight[22],texture.knight[23],texture.knight[24],texture.knight[25],texture.knight[21]);
		attack1AnimationLeft = new Animation(3,texture.knight[26],texture.knight[27],texture.knight[28],texture.knight[29],texture.knight[30],texture.knight[26]);
		
		attack2AnimationRight = new Animation(3,texture.knight[31],texture.knight[32],texture.knight[33],texture.knight[34],texture.knight[35],texture.knight[36],texture.knight[31]);
		attack2AnimationLeft = new Animation(3,texture.knight[37],texture.knight[38],texture.knight[39],texture.knight[40],texture.knight[41],texture.knight[42],texture.knight[37]);
		
	}
	
	public void update(LinkedList<Entity>entity)
	{
		checkIfDead();
		y += velY;
		x += velX;
		/*y direction*/
		addGravity();
		if(isInAir()) {
			setFriction(0);
		}
		else {
			setFriction(getInitialFriction());
		}
		checkOutOfMapBounds();
		updateAttackingBounds();
		if(!isInAir()) {onGround = false;}
		/*the x direction*/
		if(isMovingX())
		{
			if(getFacing() == Main.RIGHT) {setVelX(v1 + runSpeed);}
			else if(getFacing() == Main.LEFT) {setVelX(-v1 + (runSpeed*-1));}
		}
		
		if(isSliding()){
			addFriction();
		}
		
		health = Game.clamp(health,100,0);
		updateDamage();
		updateGettingHit(entity);
		if(isInvincible()) {coolDown++;}
		else {coolDown = 0;}
		/*the animation*/
		updateMovementBounds(texture.knight);
		if(isMovingX()) {
		rightWalk.runAnimation();
		leftWalk.runAnimation();}
		if(getFacing() == Main.RIGHT)
		{
			attack(isAttacking1(),attack1AnimationRight);
			attack(isAttacking2(),attack2AnimationRight);
		}
		if(getFacing() == Main.LEFT) 
		{
			attack(isAttacking2(),attack2AnimationLeft);
			attack(isAttacking1(),attack1AnimationLeft);
		}
		/*the collision*/
		for(int i =0; i < entity.size(); i++)
		{
			Entity ent = entity.get(i);
			blockCollision(ent);
			checkOnGround(ent);
		}
		
	}
	 
	public void render(Graphics g)
	{
		if(coolDown %2 ==0) {
			//g.setColor(Color.RED);
			if(!isAttacking1() && !isAttacking2()) {drawMovement(g,texture.knight);}
			/*drawing the attacking animation*/
			else if(isAttacking1())
			{
				if(!attack1AnimationRight.isOver() && getFacing() == Main.RIGHT)
				{
					updateDimensions(attack1AnimationRight);
					attack1AnimationRight.drawAnimation(g, (int)x, (int)y);
			
				}
				if(!attack1AnimationLeft.isOver()&& getFacing() == Main.LEFT)
				{
					updateDimensions(attack1AnimationLeft);
					attack1AnimationLeft.drawAnimation(g, (int)(x - width/2), (int)y);
					
				}
			}
			else if(isAttacking2())
			{
				if(!attack2AnimationRight.isOver() && getFacing() == Main.RIGHT)
				{
					updateDimensions(attack2AnimationRight);
					attack2AnimationRight.drawAnimation(g, (int)x, (int)y);
			
				}
				if(!attack2AnimationLeft.isOver()&& getFacing() == Main.LEFT)
				{
					updateDimensions(attack2AnimationLeft);
					attack2AnimationLeft.drawAnimation(g, (int)(x - width/2), (int)y);
				
				}
			}
		}
		//g.setColor(Color.BLUE);
		//Graphics2D g2d = (Graphics2D)g;
		//g2d.draw(getBounds());
		//g2d.draw(getTopBounds());
		//g2d.draw(getLeftBounds());
		//g2d.draw(getRightBounds());
		/*drawing the  hitbox*/
		//g.drawRect(attackingBoundsRight.x,attackingBoundsRight.y,attackingBoundsRight.width,attackingBoundsRight.height);
		//g.drawRect(attackingBoundsLeft.x,attackingBoundsLeft.y,attackingBoundsLeft.width,attackingBoundsLeft.height);
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
		if(getFacing() == Main.RIGHT) {image = rightWalk.getCurrentImage();}
		else if(getFacing() == Main.LEFT) {image = leftWalk.getCurrentImage();}
		}
		else if(!isOnGround() && isMovingX())
		{
			if(getFacing()== Main.RIGHT) {image = imageArr[19];}
			else if(getFacing() == Main.LEFT) {image = imageArr[20];}	
		}
		width = 30;
		height = image.getHeight();
	 }
	
	protected void updateAttackingBounds()
	 {
		attackingBoundsRight.setBounds((int)(x + width/1.5), (int)(y  +height/3.5), (int)(width - (width/1.5)), (int) (height/2));
		attackingBoundsLeft.setBounds((int)(x - width/2.5), (int)(y + height/3.5), (int)(width - width/1.5), (int) (height/2));
	 }
	public Rectangle getBounds()
	  {
	    return new Rectangle((int)((int)x+ (width/2)-((width/2)/2)),(int)((int)y+(height/2)),(int)width/2,(int)height/2);
	  }
	  
	  public Rectangle getTopBounds()
	  {
	    return new Rectangle((int)((int)x+ (width/2)-((width/2)/2)),(int)y,(int)width/2,(int)height/2);
	  }
	  
	  public Rectangle getRightBounds()
	 	{
		  return new Rectangle((int)((int)x + width - width/10),(int)(y+height/10),(int)width/5,(int)(height-height/5));
	 	}
	  
	  public Rectangle getLeftBounds()
	  {
		  return new Rectangle((int)(x-width/8),(int)(y+height/10),(int)width/5,(int)(height-height/5));
	  }
	  private boolean isInAir() {return false;}
}
