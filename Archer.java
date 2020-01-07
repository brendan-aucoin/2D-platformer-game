package player;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import entities.Entity;
import game.Camera;
import game.Game;
import game.Handler;
import id.ID;
import images.Animation;
import projectiles.Arrow;
import running.Main;

public class Archer extends Player
{
	private int currJump;
	private int numJumps;
	private int coolDown = 0;
	public Archer(float x,float y,Camera cam,Game game,Handler handler,ID id)
	{
		super(x,y,cam,game,handler,id);
		image = texture.archer[1];
		setWidth(image.getWidth());
		setHeight(image.getHeight());
		width = image.getWidth();
		height = image.getHeight();
		setVelY(0);
		setVelX(0);
		velY = 0;
		velX = 0;
		numJumps = 2;
		currJump =0;
		jumpHeight = -7.4f;
	    v1 = 4.85f;
	    runSpeed = 0;
	    defense = 6;
	    setInitialFriction(0.4f);
	    setFriction(getInitialFriction());
	    setDamage1(0);
	    setDamage2(5);
	    coolDown = 0;
	    /*animation*/
		setRightWalk(new Animation(4,texture.archer[1],texture.archer[2],texture.archer[3],texture.archer[4],texture.archer[5],texture.archer[6],texture.archer[7],texture.archer[8],texture.archer[9]));
		setLeftWalk(new Animation(4,texture.archer[10],texture.archer[11],texture.archer[12],texture.archer[13],texture.archer[14],texture.archer[15],texture.archer[16],texture.archer[17],texture.archer[18]));
		rightWalk = new Animation(4,texture.archer[1],texture.archer[2],texture.archer[3],texture.archer[4],texture.archer[5],texture.archer[6],texture.archer[7],texture.archer[8],texture.archer[9]);
	    leftWalk = new Animation(4,texture.archer[10],texture.archer[11],texture.archer[12],texture.archer[13],texture.archer[14],texture.archer[15],texture.archer[16],texture.archer[17],texture.archer[18]);
	    attack2AnimationRight = new Animation(1,texture.archer[43],texture.archer[44],texture.archer[45],texture.archer[46],texture.archer[47],texture.archer[43]);
	    attack2AnimationLeft = new Animation(1,texture.archer[48],texture.archer[49],texture.archer[50],texture.archer[51],texture.archer[52],texture.archer[48]);
	    attack1AnimationRight = new Animation(1,texture.archer[21],texture.archer[22],texture.archer[23],texture.archer[24],texture.archer[25],texture.archer[26],texture.archer[27],texture.archer[28],texture.archer[29],texture.archer[30],texture.archer[31],texture.archer[21]);
	    attack1AnimationLeft = new Animation(1,texture.archer[32],texture.archer[32],texture.archer[33],texture.archer[33],texture.archer[35],texture.archer[36],texture.archer[37],texture.archer[38],texture.archer[39],texture.archer[40],texture.archer[41],texture.archer[42],texture.archer[31]);
	}
	
	/*the implemented methods*/
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
		/*checks the boundaries on the map*/
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
		
		/*the damage updating*/
		updateDamage();
		updateGettingHit(entity);
		if(isInvincible()) {coolDown++;}
		else {coolDown = 0;}
		/*the animation*/
		updateMovementBounds(texture.archer);
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
		
		/*updating what the damage should be depending on what attack your using*/
		
		
		jumpingCollision(entity);
		
		
		/*the projectiles*/
		if(Arrow.arrowCount(entity) <= 6)
		{
			if((attack1AnimationRight.isOver()) &&isAttacking1())
			{
				if(getFacing() == Main.RIGHT) {entity.add(new Arrow(x + width,y + height/2,getFacing(),ID.Arrow,cam));}
				
			}
			else if(attack1AnimationLeft.isOver() && isAttacking1())
			{
				if(getFacing() == Main.LEFT) {entity.add(new Arrow(x-width,y + height/2,getFacing(),ID.Arrow,cam));}
			}
		}
		
		/*if((isAttacking2() || isAttacking1()) && getFacing() == Main.RIGHT)
		{
			if(getAttackingBoundsRight().intersects(enemy))
			{
			System.exit(0);
			}
		}*/
		
	}
	public void render(Graphics g)
	{
		if(coolDown %2 == 0){
			if(!isAttacking1() && !isAttacking2()) {drawMovement(g,texture.archer);}
			/*drawing the attacking animation*/
			else if(isAttacking1())
			{
				if(!attack1AnimationRight.isOver() && getFacing() == Main.RIGHT)
				{
					attack1AnimationRight.drawAnimation(g, (int)x, (int)y);
					updateDimensions(attack1AnimationRight);
				}
				if(!attack1AnimationLeft.isOver()&& getFacing() == Main.LEFT)
				{
					attack1AnimationLeft.drawAnimation(g, (int)x, (int)y);
					updateDimensions(attack1AnimationLeft);
				}
			}
			else if(isAttacking2())
			{
				if(!attack2AnimationRight.isOver() && getFacing() == Main.RIGHT)
				{
					attack2AnimationRight.drawAnimation(g, (int)x, (int)y);
					updateDimensions(attack2AnimationRight);
				}
				if(!attack2AnimationLeft.isOver()&& getFacing() == Main.LEFT)
				{
					attack2AnimationLeft.drawAnimation(g,(int)(x - width/2), (int)y);
					updateDimensions(attack2AnimationLeft);
				}	
			}
		
	}
		/*g.setColor(Color.RED);
		Graphics2D g2d = (Graphics2D)g;
		g2d.draw(getBounds());
		g2d.draw(getTopBounds());
		g2d.draw(getLeftBounds());
		g2d.draw(getRightBounds());*/
		/*drawing the  hitbox*/
		//g.drawRect(attackingBoundsRight.x,attackingBoundsRight.y,attackingBoundsRight.width,attackingBoundsRight.height);
		//g.drawRect(attackingBoundsLeft.x,attackingBoundsLeft.y,attackingBoundsLeft.width,attackingBoundsLeft.height);
	}
	public void jump()
	{
		if(currJump == 0 && isOnGround())
		{
		setJumping(true);
		setVelY(jumpHeight);
		currJump++;
		}
		else if(currJump < numJumps && isJumping())
		{
			setJumping(true);
			setVelY(jumpHeight+0.3f);
			currJump++;
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
	
	private void jumpingCollision(LinkedList<Entity>entity)
	{
		/*the collision*/
		for(int i =0; i < entity.size();i++)
		{
			Entity ent = entity.get(i);
			blockCollision(ent);
			checkOnGround(ent);
			if(ent.getId() == ID.Block)
			{
				if(getBounds().intersects(ent.getBounds()))
				{
					currJump = 0;
				}
			}
		}
	}
	/*the get bounds*/
	public Rectangle getBounds()
	{
		return new Rectangle((int)((int)x+ (width/2)-((width/2)/2)),(int)((int)y+(height/2)),(int)width/2,(int)height/2);
	}
	public Rectangle getTopBounds()
	{
		return new Rectangle((int)((int)x+ (width/2)-((width/2)/2)),(int)y,(int)width/2,(int)height/2);
	}
	public Rectangle getLeftBounds()
	{
		return new Rectangle((int)x,(int)(y+height/10),(int)width/5,(int)(height-height/5));
	}
	public Rectangle getRightBounds()
	{
		return new Rectangle((int)((int)x + width - width/6),(int)(y+height/10),(int)width/5,(int)(height-height/5));
	}
	private boolean isInAir() {return false;}

	public int getCurrJump() {return currJump;}
	public void setCurrJump(int currJump) {this.currJump = currJump;}
	public int getNumJumps() {return numJumps;}
	public void setNumJumps(int numJumps) {this.numJumps = numJumps;}
	
}
