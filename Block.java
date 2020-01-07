package blocks;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import entities.Entity;
import game.Camera;
import game.Game;
import id.BlockID;
import id.ID;
import images.Texture;

public class Block extends Entity 
{
	private BlockID blockId;
	private BufferedImage image;
	public Texture texture;
	private Camera cam;
	public Block(float x,float y,Camera cam,ID id,BlockID blockId,float width,float height)
	{
		super(x,y,id);
		texture = Game.getTexture();
		this.blockId = blockId;
		this.width = width;
		this.height = height;
		this.cam = cam;
		image = texture.blocks[blockId.ordinal()];
		
	}
	public void update(LinkedList<Entity>entity)
	{
		
	}
	public void render(Graphics g)
	{
		if(onScreen(cam)) {
		g.drawImage(image,(int)x,(int)y,(int)width,(int)height,null);
		}
	}
	
	
	public Rectangle getBounds()
	{
		return new Rectangle((int)x,(int)y,(int)width,(int)height);
	}
	
	public void setBlockId(BlockID blockId) {this.blockId = blockId;}
	public BlockID getBlockId() {return blockId;}
	public BufferedImage getImage()
	{
		return image;
	}
	public Camera getCam() {return cam;}
	public void setCam(Camera cam) {this.cam = cam;}
	
}
