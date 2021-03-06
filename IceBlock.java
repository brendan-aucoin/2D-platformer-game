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
import player.Player;

public class IceBlock extends Entity{
	private BufferedImage image;
	private Texture texture;
	private Camera cam;
	private BlockID blockId;
	public IceBlock(float x,float y,float width,float height,Camera cam,ID id) {
		super(x,y,id);
		texture = Game.getTexture();
		this.cam = cam;
		this.width = width;
		this.height = height;
		blockId = BlockID.Ice;
		image = texture.blocks[blockId.ordinal()];
	}
	
	public void update(LinkedList<Entity>entity) {
		if(onScreen(cam)) {
			for(int i=0; i< entity.size();i++)
			{
				if(entity.get(i).getId() == ID.Player)
				{
					Player player = (Player)entity.get(i);
					if(player.intersects(getBounds()))
					{
						/*change the friction*/
					}
				}
			}
		}
	}
	public void render(Graphics g) {
		if(onScreen(cam)) {
			g.drawImage(image,(int)x,(int)y,(int)width,(int)height,null);
		}
	}
	public Rectangle getBounds() {return new Rectangle((int)x,(int)y,(int)width,(int)height);}
}
