package projectiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import entities.Entity;
import game.Camera;
import id.ID;

public class CanonBall extends Projectile{
	private boolean inAir;
	private float gravity;
	private Color col;
	public CanonBall(float x,float y,float velX,float velY,float width,float height,int facing,Camera cam,Color col,ID id) {
		super(x,y,facing,cam,id);
		this.velX = velX;
		this.velY = velY;
		this.width = width;
		this.height = height;
		this.col = col;
		inAir = true;
		gravity = 0.5f;
		setDamage(15f);
	}
	
	public void update(LinkedList<Entity>entity) {
		changeXDirection();
		y+=velY;
		/*the gravity*/
		
		for(int i =0; i <entity.size();i++) {
			Entity ent = entity.get(i);
			if(ent.getId() == ID.Block) {
				if(!getBottomBounds().intersects(ent.getBounds())) {
					inAir = true;
				}
				else {
					inAir = false;
					entity.remove(this);
				}
				if(getRightBounds().intersects(ent.getBounds()) || getLeftBounds().intersects(ent.getBounds()) || getTopBounds().intersects(ent.getBounds())) {
					entity.remove(this);
				}
				
			}
		}
		
		if(inAir) {
			velY += gravity;
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(col);
		g.fillOval((int)x,(int)y,(int)width,(int)height);
	}
	public Rectangle getBounds() {return new Rectangle((int)x,(int)y,(int)width,(int)height);}
	
	public Rectangle getTopBounds(){
		return new Rectangle((int)((int)x+ (width/2)-((width/2)/2)),(int)y,(int)width/2,(int)height/2);
	}
	public Rectangle getLeftBounds(){
		return new Rectangle((int)x,(int)(y+height/10),(int)width/5,(int)(height-height/5));
	}
	public Rectangle getRightBounds(){
		return new Rectangle((int)((int)x + width - width/6),(int)(y+height/10),(int)width/5,(int)(height-height/5));
	}
	public Rectangle getBottomBounds(){
		return new Rectangle((int)((int)x+ (width/2)-((width/2)/2)),(int)((int)y+(height/2)),(int)width/2,(int)height/2);
	}
}
