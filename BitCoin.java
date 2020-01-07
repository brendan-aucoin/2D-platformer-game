package blocks;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

import entities.Entity;
import game.Camera;
import game.FileLoader;
import game.Game;
import id.ID;
import images.BufferedImageLoader;
import player.Player;
import running.Main;

public class BitCoin extends Entity
{
	private BufferedImage image;
	private int rotate=45;
	private Game game;
	private Camera cam;
	private int value;
	private FileLoader coinLoader;
	public BitCoin(float x,float y,BufferedImageLoader loader,Game game,Camera cam,ID id)
	{
		super(x,y,id);
		this.game = game;
		this.cam = cam;
		image = loader.loadImage("single_images","bitcoin.png");
		width = image.getWidth();
		height = image.getHeight();
		value = new Random().nextInt(2 - -2) + -1;
		coinLoader = new FileLoader();
		coinLoader.setFileName(Main.TEXTSPATH + "coins.txt");
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
							entity.remove(this);
							game.setCoins(game.getCoins()+value);
							if(game.getCoins() <0) {game.setCoins(0);}
							coinLoader.createFile();
							coinLoader.write(String.valueOf(game.getCoins()));
							coinLoader.closeOutput();
						}
					}
				}
			}
	}
	public void render(Graphics g)
	{
		if(onScreen(cam))
		{
				AffineTransform at = AffineTransform.getTranslateInstance(x,y);
				at.rotate(Math.toRadians(rotate++),image.getWidth()/2,image.getHeight()/2);
				Graphics2D g2d = (Graphics2D)g;
				g2d.drawImage(image,at,null);
				//g.drawImage(image,(int)x,(int)y,null);
		}
	}
	
	public Rectangle getBounds()
	{
		return(new Rectangle((int)x,(int)y,(int)width,(int)height));
	}
}
