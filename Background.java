package background;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import images.BufferedImageLoader;

public class Background 
{
	private BufferedImageLoader loader;
	private BufferedImage image;
	private String path;
	public Background(BufferedImageLoader loader)
	{
		this.loader = loader;
	}
	public void render(Graphics g,float x,float y,int width,int height)
	{
		g.drawImage(image,(int)x,(int)y,width,height,null);
	}
	
	public void setPath(String path)
	{
		this.path = path;
		image = loader.loadImage("backgrounds",path);
	}
	public String getPath() {return path;}
}
