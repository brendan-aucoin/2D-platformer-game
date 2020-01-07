package blocks;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import entities.Entity;
import game.Camera;
import game.Game;
import game.State;
import id.ID;
import images.Texture;
import player.Player;

public class Goal extends Entity
{
	private Texture texture = Game.getTexture();
	private BufferedImage image;
	private Game game;
	private Camera cam;
	public Goal(float x,float y,Game game,Camera cam,ID id)
	{
		super(x,y,id);
		this.game = game;
		this.cam =cam;
		image = texture.doorway[game.getWorldNum()-1];
		width = image.getWidth();
		height = image.getHeight();
	}
	public void update(LinkedList<Entity>entity)
	{
		if(onScreen(cam))
		{
				for(int i=0; i< entity.size(); i++)
				{
					Entity ent = entity.get(i);
					if(ent.getId() == ID.Player)
					{
						Player player = (Player)ent;
						if(getBounds().intersects(player.getBounds()) || getBounds().intersects(player.getTopBounds()) ||getBounds().intersects(player.getRightBounds())||getBounds().intersects(player.getLeftBounds()))
						{
							Game.gameState = State.LevelEnd;
							entity.clear();
							game.pauseSong();
						}
					}
				}
			}
	}
	public void render(Graphics g)
	{
		if(onScreen(cam))
		{
				g.drawImage(image,(int)x,(int)y,null);
		}
	}
	public Rectangle getBounds() {return new Rectangle((int)x,(int)y,(int)width,(int)height);}
}
