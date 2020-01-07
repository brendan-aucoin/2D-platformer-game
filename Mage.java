package player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import entities.Entity;
import game.Camera;
import game.FileLoader;
import game.Game;
import game.Handler;
import id.ID;
import images.Animation;
import projectiles.DarkMagic;
import projectiles.FireBall;
import projectiles.LightningBolt;
import projectiles.Meteor;
import projectiles.PlasmaBlast;
import projectiles.TidalWave;
import projectiles.WaterDragon;
import running.Main;

public class Mage extends Player
{
	private boolean floating;
	private float initialGravity;
	private FileLoader tomeLoader;
	private String currentTome;
	private int coolDown;
	public Mage(float x,float y,Camera cam,Game game,Handler handler,ID id)
	{
		super(x,y,cam,game,handler,id);
		image = texture.mage[1];
		width = image.getWidth();
		height = image.getHeight();
		velY = 0;
		velX = 0;
		jumpHeight = -7f;
		setInitialFriction(0.55f);
	    setFriction(getInitialFriction());
	    defense = 5;
	    v1 = 5.2f;
	    runSpeed = 0;
	    floating = false;
	    this.initialGravity = gravity;
	    setDamage1(0);
	    setDamage2(0);
	    coolDown = 0;
	    /*all the file stuff with tomes*/
	    tomeLoader = new FileLoader(Main.TEXTSPATH + "currentTome.txt");
	    currentTome = tomeLoader.read().get(0);
		/*animation*/
	    rightWalk = new Animation(4,texture.mage[1],texture.mage[2],texture.mage[3],texture.mage[4],texture.mage[5],texture.mage[6],texture.mage[7],texture.mage[8],texture.mage[9]);
	    leftWalk = new Animation(4,texture.mage[10],texture.mage[11],texture.mage[12],texture.mage[13],texture.mage[14],texture.mage[15],texture.mage[16],texture.mage[17],texture.mage[18]);
	  
	    attack1AnimationRight = new Animation(2,texture.mage[21],texture.mage[22],texture.mage[23],texture.mage[24],texture.mage[25],texture.mage[26],texture.mage[27],texture.mage[21]);
	    attack1AnimationLeft = new Animation(2,texture.mage[28],texture.mage[29],texture.mage[30],texture.mage[31],texture.mage[32],texture.mage[33],texture.mage[34],texture.mage[28]);
	    attack2AnimationRight = new Animation(2,texture.mage[21],texture.mage[22],texture.mage[23],texture.mage[24],texture.mage[25],texture.mage[26],texture.mage[27],texture.mage[21]);
	    attack2AnimationLeft = new Animation(2,texture.mage[28],texture.mage[29],texture.mage[30],texture.mage[31],texture.mage[32],texture.mage[33],texture.mage[34],texture.mage[28]);
	   
	}
	
	
	public void update(LinkedList<Entity>entity)
	{
		checkIfDead();
		y += velY;
		x += velX;
		/*y direction*/
		if(floating) {v1 = 4.2f;gravity = initialGravity/2;}
		else {gravity = initialGravity;v1 = 5.2f;}
		if(isInAir()) {
			setFriction(0);
		}
		else {
			setFriction(getInitialFriction());
		}
		addGravity();
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
		updateMovementBounds(texture.mage);
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
		/*the projectiles*/
		if(FireBall.fireBallCount(entity) <= 2)
		{
			if((attack1AnimationLeft.isOver() || attack1AnimationRight.isOver()) && isAttacking1())
			{
				if(getFacing() == Main.RIGHT) {entity.add(new FireBall((float) (x +width*1.5),y ,getFacing(),cam,ID.FireBall));}
				else if(getFacing() == Main.LEFT) {entity.add(new FireBall((float) (x-width*1.5),y,getFacing(),cam,ID.FireBall));}
			}
		}
		shootSecondaryProjectiles(entity);
	}
	
	
	private void shootSecondaryProjectiles(LinkedList<Entity>entity) {
		
		/*the secondary tome depends on the text file*/
		/*lightning*/
		if(currentTome.equalsIgnoreCase("lightning")) {
			if(LightningBolt.lightningBoltCount(entity) <1) 
			{
				if((attack2AnimationLeft.isOver() || attack2AnimationRight.isOver()) && isAttacking2())
				{
					if(getFacing() == Main.RIGHT) {entity.add(new LightningBolt(x + width*3,-cam.getY(),getFacing(),cam,ID.Lightning));}
					else if(getFacing() == Main.LEFT) {entity.add(new LightningBolt(x - width*3,-cam.getY(),getFacing(),cam,ID.Lightning));}
				}
			}
		}
	
		
		/*tidal wave*/
		else if(currentTome.equalsIgnoreCase("tidalWave") || currentTome.equalsIgnoreCase("tidal wave")||currentTome.equalsIgnoreCase("tidal_wave")) {
			if(TidalWave.tidalWaveCount(entity) <5) 
			{
				if((attack2AnimationLeft.isOver() || attack2AnimationRight.isOver()) && isAttacking2())
				{
					if(getFacing()== Main.RIGHT) {entity.add(new TidalWave((float) (x + width*3.5),y + height/4,getFacing(),cam,ID.TidalWave));}
					else if(getFacing() == Main.LEFT) {entity.add(new TidalWave((float) (x - width*3.5),y + height/4,getFacing(),cam,ID.TidalWave));}
				}
			}
		}
		
		/*water dragon*/
		else if(currentTome.equalsIgnoreCase("water dragon") || currentTome.equalsIgnoreCase("waterDragon")||currentTome.equalsIgnoreCase("water_dragon")) {
			if((attack2AnimationLeft.isOver() || attack2AnimationRight.isOver()) && isAttacking2()) {
				if(getFacing() == Main.RIGHT) {entity.add(new WaterDragon((float) (x + width*2),y - getHeight(),getFacing(),cam,ID.WaterDragon));}
				else if(getFacing() == Main.LEFT) {entity.add(new WaterDragon((float) (x-width*2),y - getHeight(),getFacing(),cam,ID.WaterDragon));}
			}
		}
		
		/*plasma blast*/
		else if(currentTome.equalsIgnoreCase("plasma blast") || currentTome.equalsIgnoreCase("plasmaBlast")||currentTome.equalsIgnoreCase("plasma_blast")) {
			if(PlasmaBlast.plasmaBlastCount(entity) <4) {
				if((attack2AnimationLeft.isOver() || attack2AnimationRight.isOver()) && isAttacking2()) {
					if(getFacing() == Main.RIGHT) {entity.add(new PlasmaBlast((float) (x + width*2),y - getHeight()/2,getFacing(),cam,ID.PlasmaBlast));}
					else if(getFacing() == Main.LEFT) {entity.add(new PlasmaBlast((float) (x-width*2),y - getHeight()/2,getFacing(),cam,ID.PlasmaBlast));}
				}
			}
		}
		
		/*dark magic*/
		else if(currentTome.equalsIgnoreCase("dark magic") || currentTome.equalsIgnoreCase("darkMagic")||currentTome.equalsIgnoreCase("dark_magic")) {
			if(DarkMagic.darkMagicCount(entity) <9) {
				if((attack2AnimationLeft.isOver() || attack2AnimationRight.isOver()) && isAttacking2()) {
					if(getFacing() == Main.RIGHT) {
						//straight
						entity.add(new DarkMagic((float) (x + width*2),y - height,12,0,getFacing(),0,cam,ID.DarkMagic));
						//up and right
						entity.add(new DarkMagic((float) (x+ width*2),y - height,12,-4,getFacing(),-45,cam,ID.DarkMagic));
						//down and right
						entity.add(new DarkMagic((float) (x+ width*2),y - height,12,4,getFacing(),45,cam,ID.DarkMagic));
					}
					else if(getFacing() == Main.LEFT) {
						//straight
						entity.add(new DarkMagic((float) (x-width*2),y - height,12,0 ,getFacing(),0,cam,ID.DarkMagic));
						//up and to the left
						entity.add(new DarkMagic((float) (x-width*2),y - height,12,-4,getFacing(),45,cam,ID.DarkMagic));
						//down and left
						entity.add(new DarkMagic((float) (x-width*2),y - height,12,4,getFacing(),-45,cam,ID.DarkMagic));
					}
				}
			}
		}
		
		/*meteor*/
		else if(currentTome.equalsIgnoreCase("meteor")) {
			if(Meteor.meteorCount(entity) <3) {
				if((attack2AnimationLeft.isOver() || attack2AnimationRight.isOver()) && isAttacking2()) {
					if(getFacing() == Main.RIGHT) {entity.add(new Meteor(x - width*2,y-height*7,getFacing(),cam,ID.Meteor));}
					else if(getFacing() == Main.LEFT) {entity.add(new Meteor(x + width*2,y-height*7,getFacing(),cam,ID.Meteor));}
				}
			}
		}
		else {
			if(LightningBolt.lightningBoltCount(entity) <1) 
			{
				if((attack2AnimationLeft.isOver() || attack2AnimationRight.isOver()) && isAttacking2())
				{
					if(getFacing() == Main.RIGHT) {entity.add(new LightningBolt(x + width*3,-cam.getY(),getFacing(),cam,ID.Lightning));}
					else if(getFacing() == Main.LEFT) {entity.add(new LightningBolt(x - width*3,-cam.getY(),getFacing(),cam,ID.Lightning));}
				}
			}
		}
	}
	public void render(Graphics g)
	{
		if(coolDown %2 == 0) {
			if(!isAttacking1() && !isAttacking2()) {drawMovement(g,texture.mage);}
			/*drawing the attacking animation*/
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
					attack2AnimationLeft.drawAnimation(g, (int)x, (int)y);
					updateDimensions(attack2AnimationLeft);
				}
			}
		}
		//g.setColor(Color.BLUE);
		//Graphics2D g2d = (Graphics2D)g;
		/*g2d.draw(getBounds());
		g2d.draw(getTopBounds());
		g2d.draw(getLeftBounds());
		g2d.draw(getRightBounds());*/
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
	
	
	public void setFloating(boolean floating) {this.floating = floating;}
	public boolean isFloating() {return floating;}
	public String getCurrentTome() {return currentTome;}
	public void setCurrentTome(String currentTome) {this.currentTome = currentTome;}
	public FileLoader getTomeLoader() {return tomeLoader;}
	public void setTomeLoader(FileLoader tomeLoader) {this.tomeLoader = tomeLoader;}
	
}

