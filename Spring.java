package blocks;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import entities.Entity;
import game.Camera;
import id.ID;
import images.BufferedImageLoader;
import player.Player;

public class Spring extends Entity {
	private BufferedImage image;
	private Camera cam;
	public Spring(float x,float y,Camera cam,BufferedImageLoader loader,ID id) {
		super(x,y,id);
		this.cam = cam;
		image = loader.loadImage("single_images","spring.png");
		width = 30;
		height = 30;
	}
	
	public void update(LinkedList<Entity>entity) {
		if(onScreen(cam)) {
			for(int i =0; i<entity.size(); i++){
				Entity ent = entity.get(i);
				if(ent.getId() == ID.Player) {
					Player player = (Player)ent;
					if(getBounds().intersects(player.getBounds())) {
						player.setVelY((float) (player.getJumpHeight() *2));
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
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,(int)width,(int)height);
	}
}
