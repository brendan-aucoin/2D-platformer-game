package running;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import game.Game;

public class Display extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public Display(String title,final int WIDTH,final int HEIGHT,String imagePath)
	{
		super();
		Game game = new Game();
		setTitle(title);
		setSize(WIDTH,HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		Image img = new ImageIcon(this.getClass().getResource("/single_images/"+imagePath)).getImage();
		setIconImage(img);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(game);
		setVisible(true);
		game.start();
	}
	
}
