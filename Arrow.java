package projectiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.Timer;

import entities.Entity;
import game.Camera;
import id.ID;
import running.Main;

public class Arrow extends Projectile implements ActionListener
{
	private BufferedImage imageRight,imageLeft;
	private int facing;
	private Timer timer;
	private boolean remove;
	public Arrow(float x,float y,int facing,ID id,Camera cam)
	{
		super(x,y,facing,cam,id);
		this.facing = facing;
		velX = 25;
		imageRight = texture.arrow[0];
		imageLeft = texture.arrow[1];
		width = imageRight.getWidth();
		height = imageRight.getHeight();
		timer = new Timer(1000,this);
		remove = false;
		setDamage(0);
	}
	
	public void update(LinkedList<Entity> entity)
	{
		if(remove) {setDamage(0);}
		changeXDirection();
		for(int i =0; i < entity.size();i++)
		{
			Entity ent = entity.get(i);
			if(ent.getId() == ID.Block)
			{
				checkBlockIntersection(entity, ent);
			}
		}
		
		outOfBounds(entity);
	}
	public void render(Graphics g)
	{
		if(facing == Main.RIGHT) {g.drawImage(imageRight,(int)x,(int)y,(int)width,(int)height,null);}
		else if(facing == Main.LEFT) {g.drawImage(imageLeft, (int)x,(int)y,(int)width,(int)height,null);}
	}
	
	public void checkBlockIntersection(LinkedList<Entity>entity,Entity ent)
	{
		if(getBounds().intersects(ent.getBounds()))
		{
			velX = 0;
			if(facing == Main.RIGHT) {
			x = (float) (ent.getX() - width/1.5);}
			else if(facing == Main.LEFT) {x = ent.getX() +width/2;}
			timer.start();
			if(remove) 
			{
				entity.remove(this);
				timer.stop();
				remove = false;
			}
		}
	}
	
	/*private void addParticles(LinkedList<Entity>entity,Entity ent)
	{
			Block block = (Block)ent;
		 	BufferedImage image = block.getImage();

	        int clr=  image.getRGB(image.getWidth()/2,image.getMinY()); 
	        int  red   = (clr & 0x00ff0000) >> 16;
	        int  green = (clr & 0x0000ff00) >> 8;
	        int  blue  =  clr & 0x000000ff;
	        Color col = new Color(red,green,blue);
	        for(int i=0; i< 25; i++)
			{
				entity.add(new Particle(x + width/2,y + height/2,new Random().nextInt(5 - -5) + -5,new Random().nextInt(5 - -5) + -5,5,5,10,col,ID.Particle));
			}
	}*/
	
	/*finds out how many arrows objects are currently on screen*/
	public static int arrowCount(LinkedList<Entity>entity)
	{
		int count = 0;
		for(int i = 0; i< entity.size();i++)
		{
			Entity ent = entity.get(i);
			if(ent instanceof Arrow)
			{
				count++;
			}
		}
		return count;
	}
	public Rectangle getBounds()
	{
		return new Rectangle((int)x,(int)y,(int)width,(int)height);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		remove = true;
	}
}
