package running;

public class Main 
{
	public static final int WIDTH = 1250;
	public static final int HEIGHT = 700;
//	public static final int WIDTH = 800;
//	public static final int HEIGHT = 600;
	public static final String TEXTSPATH = System.getProperty("user.dir")+ System.getProperty("file.separator")+"texts"+System.getProperty("file.separator");
	public static final String MIDIPATH = System.getProperty("user.dir")+ System.getProperty("file.separator")+"midi"+System.getProperty("file.separator");
	public static final String FONTSPATH = System.getProperty("user.dir")+ System.getProperty("file.separator")+"fonts"+System.getProperty("file.separator");
	public static final int LEFT = 1;
	public static final int RIGHT = 0;
	public static void main(String[] args) 
	{
		new Display("Eritque Arcus Ovium",WIDTH,HEIGHT,"swordIcon.png");
	}
}
