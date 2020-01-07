package images;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
public class BufferedImageLoader
{
  private BufferedImage image = null;
  public BufferedImage loadImage(String dirName,String path)
  {
	try{image = ImageIO.read(this.getClass().getResource("/" + "" + dirName + "/" + "" +  path));}catch(IOException e) {System.err.println("there was a problem loading the level");}
    return image;
  }
  
  public BufferedImage loadImage(String path)
  {
	  try {image = ImageIO.read(this.getClass().getResource(path));}catch(IOException e) {System.err.println("there was a problem loading the level");}
	  return image;
  }
}
