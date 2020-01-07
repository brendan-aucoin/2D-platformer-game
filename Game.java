package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import background.Background;
import background.BackgroundNamesList;
import blocks.BitCoin;
import blocks.Block;
import blocks.Goal;
import blocks.IceBlock;
import blocks.Ladder;
import blocks.Spring;
import id.BlockID;
import id.ID;
import images.BufferedImageLoader;
import images.Texture;
import menus.ControlsMenu;
import menus.DeadMenu;
import menus.LevelEndMenu;
import menus.LevelStartMenu;
import menus.MainMenu;
import menus.Menu;
import menus.PauseMenu;
import menus.ShopMenu;
import menus.StatsMenu;
import music.MP3Player;
import music.MidiPlayer;
import music.MusicList;
import player.Archer;
import player.Knight;
import player.Mage;
import player.Player;
import running.Main;
import userInput.KeyAction;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	public final String magicaPath = "Dio-Magica Theme.mp3";
	public final String magicaReprisePath = "Dio-Magica-Reprise.mp3";
	private Thread thread;
	private boolean running;
	private String currMouseListener = "";
	private String[] backgroundNames;
	private BufferedImage backgroundImages[];
	private String[] stageBackgroundNames = {"fieldBackground_stage.png"};
	private String [] levelNames;
	private BufferedImage mainMenuImage;
	public String []musicNames;
	public Color[] backgroundColours = {Color.WHITE,new Color(153,204,255),Color.GREEN};
	private int worldNum;
	private int level,coins;
	private String world;
	private boolean musicOn;
	private String character;
	public BufferedImage levelImage;
	private boolean canPlaySong;
	
	/*the important objects*/
	public static State gameState;
	private Handler handler;
	private Camera cam;
	private static Texture texture;
	public BufferedImageLoader loader;
	private MainMenu mainMenu;
	private ControlsMenu controlsMenu;
	private FileLoader beginingLoader;
	private LevelStartMenu levelStartMenu;
	private LevelEndMenu levelEndMenu;
	private ShopMenu shopMenu;	
	private DeadMenu deadMenu;
	private StatsMenu statsMenu;
	private PauseMenu pauseMenu;
	private Player player;
	private MP3Player mp3Player;
	private MidiPlayer midiPlayer;
	private Background background;
	private HUD hud;
	private Cursor transparentCursor;
	public Game()
	{
		setFocusable(true);
		handler = new Handler();
		cam = new Camera(0,0);
		hud = new HUD(this,cam,handler);
		loader = new BufferedImageLoader();
		texture = new Texture();
		running  = false;
		mainMenu = new MainMenu(State.MainMenu,this);
		controlsMenu = new ControlsMenu(State.ControlsMenu);
		levelStartMenu = new LevelStartMenu(State.LevelStart,this);
		shopMenu = new ShopMenu(State.Shop,this,handler,loader);
		pauseMenu = new PauseMenu(State.Paused,cam,this,loader);
		statsMenu = new StatsMenu(State.StatsMenu,handler,this);
		levelEndMenu = new LevelEndMenu(State.LevelEnd,this,handler);
		deadMenu = new DeadMenu(State.Dead,this,loader);
		background = new Background(loader);
		addKeyListener(new KeyAction(handler,this));
		createBeginingFiles();
		backgroundImages = new BufferedImage[backgroundNames.length];
		for(int i =0; i < backgroundImages.length; i++) {backgroundImages[i] = loader.loadImage(backgroundNames[i]);}
		mainMenuImage = loader.loadImage("backgrounds","sunsetCastleBackground.png");
		worldNum = 0;
		musicOn = true;
		character = "Knight";
		levelImage = null;
		mp3Player = new MP3Player();
		midiPlayer = new MidiPlayer();
		playTheme(magicaPath);
		transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().getImage(""), new Point(), "trans");
	}
	public void update()
	{
		if(gameState == State.MainMenu){
			updateMenu(mainMenu,"Main Menu");
		}
		else if(gameState == State.ControlsMenu){updateMenu(controlsMenu,"Controls Menu");}
		else if(gameState == State.LevelStart) {updateMenu(levelStartMenu,"Level Start Menu");worldNum = Integer.parseInt(world.substring(6));}
		else if(gameState == State.Paused) {updateMenu(pauseMenu,"Pause Menu");}
		else if(gameState == State.StatsMenu) {updateMenu(statsMenu,"Stats Menu");}
		else if(gameState == State.LevelEnd) {updateMenu(levelEndMenu,"Level End Menu");}
		else if(gameState == State.Dead) {updateMenu(deadMenu,"dead menu");}
		else if(gameState == State.Shop) {updateMenu(shopMenu,"Shop Menu");}
		/*where the actual game takes place*/
		else if(gameState == State.Playing)
		{
			this.setCursor(transparentCursor);
			handler.update();
			hud.update();
			clamp(level,musicNames.length,0);
			clamp(coins,500,0);
			clamp(worldNum,9,1);
			for(int i =0; i < handler.entity.size();i++)
			{
				if(handler.entity.get(i).getId() == ID.Player)
				{
					cam.update(handler.entity.get(i));
				}
			}
		}
		if(gameState != State.Playing) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}
	}
	
	public void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
	    if(bs == null){this.createBufferStrategy(3);return;}
	    Graphics g = bs.getDrawGraphics();
	    Graphics2D g2d = (Graphics2D)g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setColor(backgroundColours[worldNum]);
	    
	    if(gameState != State.MainMenu || gameState != State.ControlsMenu) {g.fillRect(0,0,Main.WIDTH,Main.HEIGHT);}
	    /*Beginning of the rendering code*/
	    if(gameState == State.MainMenu || gameState == State.ControlsMenu) {g.drawImage(mainMenuImage,0,0,Main.WIDTH,Main.HEIGHT,null);}
	    /*drawing the main menu background*/
	    /*all the menus*/
	    if(gameState == State.LevelStart) {g.drawImage(backgroundImages[worldNum], 0, 0, Main.WIDTH,Main.HEIGHT,null);}
	    if(gameState == State.MainMenu){mainMenu.render(g2d);}
	    else if(gameState == State.ControlsMenu){controlsMenu.render(g2d);}
	    else if(gameState == State.LevelStart) {levelStartMenu.render(g2d);}
	    else if(gameState == State.LevelEnd) {levelEndMenu.render(g2d);}
	    else if(gameState == State.MultiPlayer) {}
	    else if(gameState == State.StatsMenu) {statsMenu.render(g2d);}
	    else if(gameState == State.Dead) {deadMenu.render(g2d);}
	    else if(gameState == State.Shop) {shopMenu.render(g2d);}
	    if(gameState == State.Playing || gameState == State.Paused||gameState == State.LevelEnd)
	    {
	    	g2d.translate(cam.getX(), cam.getY());
	    	/*the actual states involving the game*/
	    	/*drawing the backgrounds*/
	    	background.render(g, -cam.getX(),-cam.getY(),Main.WIDTH,Main.HEIGHT);
	    	
	    	/*end of drawing backgrounds*/
		    	
	    	handler.render(g);
	    	if(gameState == State.Playing) {
		    	hud.render(g);
		    }
	    	if(gameState == State.Paused)
	    	{
	    		pauseMenu.render(g2d);
	    	}
	    	
	    	g2d.translate(-cam.getX(),-cam.getY());
	    	if(gameState == State.LevelEnd) {
	    		levelEndMenu.render(g2d);
	    	}
	    }
	    /*end of rendering code*/
	    g.dispose();
	    bs.show();
	}
	
	/*the level methods*/
	
	public void loadLevel()
	{
		background.setPath(stageBackgroundNames[worldNum -1]);
		levelImage = loader.loadImage("levels","Level " +getLevel() + ".png");
		loadImageLevel(levelImage);
		handler.addEntity(player);
		openSong();
		if(musicOn) {playSong();}
	}
	public void loadImageLevel(BufferedImage image)
	  {
	    int w = image.getWidth();
	    int h = image.getHeight();
	    for(int xx = 0; xx < h; xx++)
	    {
	      for(int yy =0; yy < w; yy++)
	      {
	        int pixel = image.getRGB(xx,yy);
	        int red = (pixel >> 16) & 0xff;
	        int green = (pixel >>8) & 0xff;
	        int blue = (pixel) & 0xff;
	        loadInTextures(red,green,blue,xx,yy);
	      }
	    }
	  }
	
	private void loadInTextures(int red,int green,int blue,int xx,int yy)
	{
		/*the blocks*/
		loadInBlockTextures(red,green,blue,xx,yy);
		/*the player*/
		if(red == 255 && green == 255 && blue == 255)
		{
			if(character.equalsIgnoreCase("Archer")){player = new Archer(xx*30,yy*30,cam,this,handler,ID.Player);}
			else if(character.equalsIgnoreCase("Knight")) {player = new Knight(xx*30,yy*30,cam,this,handler,ID.Player);}
			else if(character.equalsIgnoreCase("Mage")) {player = new Mage(xx*30,yy*30,cam,this,handler,ID.Player);}
		}
		/*the bitcoin*/
		if(red == 218 && green == 165 && blue == 32)
		{
			handler.addEntity(new BitCoin(xx*30,yy*30,loader,this,cam,ID.Coin));
		}
		if(red == 255 && green == 0 && blue == 255)
		{
			handler.addEntity(new Goal(xx*30,yy*30,this,cam,ID.Goal));
		}
		if(red == 210 && green == 180 && blue ==140)
		{
			handler.addEntity(new Ladder(xx*30,yy*30,30,30,cam,ID.Ladder));
		}
		if(red == 255 && green == 127 && blue == 80) {
			handler.addEntity(new Spring(xx*30,yy*30,cam,loader,ID.Spring));
		}
		if(red == 176 && green == 224 && blue ==230) {
			handler.addEntity(new IceBlock(xx*30,yy*30,30,30,cam,ID.Block));
		}
		
	}
	
	public void clearLevel()
	{
		handler.clear();
	}
	public void openSong()
	{
	   if(midiPlayer.canPlay(musicNames[level] + ".mid"))
	   {
		   canPlaySong = true;
	   }
	   else {canPlaySong = false;}
	}
	public void playSong()
	{
		if(canPlaySong)
		{
			midiPlayer.play();
		}
		else {return;}
	}
	public void pauseSong()
	{
		midiPlayer.pause();
	}
	public void stopSong()
	{
		midiPlayer.stop();
	}
	
	private void loadInBlockTextures(int red,int green,int blue,int xx,int yy)
	{
		//row 1
		colourEqual(xx,yy,red,green,blue,105,105,105,BlockID.SmoothStone);
		colourEqual(xx,yy,red,green,blue,128,128,128,BlockID.CobbleStone);
		colourEqual(xx,yy,red,green,blue,139,69,19,BlockID.Dirt);
		colourEqual(xx,yy,red,green,blue,50,205,50,BlockID.Grass);
		colourEqual(xx,yy,red,green,blue,240,230,140,BlockID.Sand);
		colourEqual(xx,yy,red,green,blue,119,136,153,BlockID.RoughSteel);
		colourEqual(xx,yy,red,green,blue,112,128,144,BlockID.Steel);
		colourEqual(xx,yy,red,green,blue,210,105,30,BlockID.Brick);
		//row 2
		colourEqual(xx,yy,red,green,blue,250,235,215,BlockID.LightStone);
		colourEqual(xx,yy,red,green,blue,245,245,220,BlockID.Gravel);
		colourEqual(xx,yy,red,green,blue,192,192,192,BlockID.Iron);
		colourEqual(xx,yy,red,green,blue,255,215,0,BlockID.GoldBrick);
		colourEqual(xx,yy,red,green,blue,0,255,255,BlockID.BlueBlock);
		colourEqual(xx,yy,red,green,blue,0,255,127,BlockID.CheckeredGreenBlock);
		colourEqual(xx,yy,red,green,blue,220,20,60,BlockID.DiamondRedBlock);
		colourEqual(xx,yy,red,green,blue,245,255,250,BlockID.SpiderWeb);
		//row 3
		colourEqual(xx,yy,red,green,blue,127,255,212,BlockID.MossyStone);
		colourEqual(xx,yy,red,green,blue,255,228,181,BlockID.BookShelf);
		colourEqual(xx,yy,red,green,blue,188,143,143,BlockID.GrayFlower);
		colourEqual(xx,yy,red,green,blue,0,0,205,BlockID.BluePanel);
		colourEqual(xx,yy,red,green,blue,32,32,32,BlockID.BlackPanel);
		colourEqual(xx,yy,red,green,blue,255,250,250,BlockID.Snow);
		//colourEqual(xx,yy,red,green,blue,176,224,230,BlockID.Ice);
		colourEqual(xx,yy,red,green,blue,248,248,255,BlockID.SnowDirt);
		//row 4
		colourEqual(xx,yy,red,green,blue,244,164,96,BlockID.RedLeafDirt);
		colourEqual(xx,yy,red,green,blue,205,133,63,BlockID.RedLeaf);
		//colourEqual(xx,yy,red,green,blue,210,180,140,BlockID.Ladder);
		colourEqual(xx,yy,red,green,blue,255,105,180,BlockID.PinkStone);
		colourEqual(xx,yy,red,green,blue,139,69,25,BlockID.BrownStone);
		colourEqual(xx,yy,red,green,blue,255,69,0,BlockID.Lava);
		colourEqual(xx,yy,red,green,blue,30,144,255,BlockID.Water);
		colourEqual(xx,yy,red,green,blue,255,218,185,BlockID.Mulch);
		//row 5
		colourEqual(xx,yy,red,green,blue,210,105,50,BlockID.Wood);
		colourEqual(xx,yy,red,green,blue,210,105,90,BlockID.DarkLog);
		colourEqual(xx,yy,red,green,blue,124,252,0,BlockID.BrightLeaf);
		colourEqual(xx,yy,red,green,blue,34,139,34,BlockID.DarkLeaf);
		colourEqual(xx,yy,red,green,blue,189,183,107,BlockID.Clock);
		
		
		//row 6
		colourEqual(xx,yy,red,green,blue,200,210,220,BlockID.PatternQuarts);
		colourEqual(xx,yy,red,green,blue,230,220,220,BlockID.StarQuarts);
		colourEqual(xx,yy,red,green,blue,230,220,245,BlockID.SmoothQuarts);
		colourEqual(xx,yy,red,green,blue,230,235,245,BlockID.NoteBlock);
	}
	
	private void colourEqual(int xx,int yy,int red,int green,int blue, int redVal,int greenVal,int blueVal,BlockID blockId)
	{
		if(red == redVal && green == greenVal && blue == blueVal)
		{
			handler.addEntity(new Block(xx*30,yy*30,cam,ID.Block,blockId,30,30));
		}
	}
	private void updateMenu(Menu menu,String menuStr)
	{
		if(currMouseListener != menuStr) {this.addMouseListener(menu);this.addMouseMotionListener(menu);}
		currMouseListener = menuStr;
	}
	
	private void createFile(String path,String text)
	{
		beginingLoader.setFileName(Main.TEXTSPATH + path);
		if(!beginingLoader.exists()) {mainMenu.resetFile(Main.TEXTSPATH + path,text);}
	}
	
	public static int clamp(int val,int max,int min)
	{
	if(val >= max){return val = max;}
    else if(val <=min){return val = min;}
    else {return val;}
	}
	public static Color randomColour()
	{
		int r = (int)(Math.random()*256);
		int g = (int)(Math.random()*256);
		int b= (int)(Math.random()*256);
		return new Color(r,g,b);
	}
	public static Color randomColour(int rBounds,int gBounds,int bBounds)
	{
		if(rBounds > 256 || gBounds > 256 || bBounds > 256) {return Color.WHITE;}
		int r = (int)(Math.random()*rBounds);
		int g = (int)(Math.random()*gBounds);
		int b= (int)(Math.random()*bBounds);
		return new Color(r,g,b);
	}
	public static Texture getTexture() {return texture;}
	/*all the thread code*/
	/*public void run()
	  {
	    long lastTime = System.nanoTime();
	    double amountOfTicks = 60.0;
	    double ns = 1000000000 / amountOfTicks;
	    double delta = 0;
	    long timer = System.currentTimeMillis();
	    int frames = 0;
	    while(running)
	    {
	      long now = System.nanoTime();
	      delta += (now - lastTime) /ns;
	      lastTime = now;
	      while(delta >= 1)
	      {
	        update();
	        delta--;
	      }
	      if(running)
	      {
	        render();
	      }
	      frames++;
	      if(System.currentTimeMillis() - timer > 1000)
	      {
	        timer += 1000;
	        System.out.println("FPS: " + frames);
	        frames = 0;
	      }
	    }//end of running loop
	    stop();
	  }//end of run*/
	
	public void run()
	  {
	    this.requestFocus();
	    int fps = 60;//the frames per second./ ticks per second
	    double timePerTick = 1000000000/fps;//becuase 1 billion nano seconds in 1 second
	    double delta = 0;//how much time you have until you have to call the tick and render methods again.
	    long now;//the time right now.
	    long lastTime = System.nanoTime();//returns how many nano seconds our computer is running at.
	    long timer = 0;
	    int ticks =0;
	    while(running)//game loop
	    {
	      now = System.nanoTime();//now is current time.
	      delta += (now - lastTime)/timePerTick;
	      timer += now -lastTime;//how much time has passed since the last call of tick and render
	      lastTime = now;
	      if(delta >= 1)//this is to achieve 60 fps.
	      {
	          update();
	          render();
	          ticks++;
	          delta--;
	      }
	      if(timer >= 1000000000)
	      {
	        System.out.println("Updates: " + ticks);
	        ticks = 0;
	        timer = 0;
	      }
	      try{Thread.sleep(2);}catch(InterruptedException ie){System.out.println("Game loop sleeping interuppted");}//could be 8
	    }
	    stop();
	  }//end of run
	
	public synchronized void start()
	{
		if(running) {return;}
		running = true;
		gameState = State.MainMenu;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop()
	{
		try{thread.join();}catch(InterruptedException e) {e.printStackTrace();}
		running = false;
	}
	public void playTheme(final String path)
	{
		mp3Player.play(path);
	}
	public void stopMusicPlayer()
	{
		mp3Player.stop();
	}
	/*all the methods just for organization*/
	private void createBeginingFiles()
	{
		beginingLoader = new FileLoader();
		beginingLoader.makeDirectory(Main.TEXTSPATH);
		createFile("game tracklist.txt",MusicList.musicList);
		ArrayList<String> musicNamesList = beginingLoader.read();
		musicNames = new String[musicNamesList.size()];
		for(int i =0; i < musicNames.length; i++) {
			musicNames[i] = musicNamesList.get(i);
		}
		createFile("background names.txt",BackgroundNamesList.backgroundNamesList);
		ArrayList <String> backgroundNamesList =  beginingLoader.read();
		backgroundNames = new String[backgroundNamesList.size()];
		for(int i =0; i < backgroundNames.length; i++)
		{
			backgroundNames[i] = "/backgrounds/" + backgroundNamesList.get(i);
		}
		/*the music*/
		beginingLoader.makeDirectory(Main.MIDIPATH);
		/*the level names*/
		beginingLoader.setFileName(Main.TEXTSPATH + "LevelNames.txt");
		ArrayList<String>levelNamesList = beginingLoader.read();
		levelNames = new String[levelNamesList.size()];
		for(int i =0; i<levelNamesList.size(); i++) {
			levelNames[i] = levelNamesList.get(i);
		}
		
		/*the tome*/
		beginingLoader.setFileName(Main.TEXTSPATH + "currentTome.txt");
		if(!beginingLoader.exists()) {
			beginingLoader.createFile();
			beginingLoader.write("lightning");
			beginingLoader.closeOutput();
		}
		beginingLoader = null;
	}
	/*getters and setters*/
	public int getLevel() {return level;}
	public void setLevel(int level) {this.level = level;}
	public int getCoins() {return coins;}
	public void setCoins(int coins) {this.coins = coins;}
	public String getWorld() {return world;}
	public void setWorld(String world) {this.world = world;}
	public boolean isMusicOn() {return musicOn;}
	public void setMusicOn(boolean musicOn) {this.musicOn = musicOn;}
	public String getCharacter(){return character;}
	public void setCharacter(String character){this.character = character;}
	public void setCanPlaySong(boolean canPlaySong) {this.canPlaySong = canPlaySong;}
	public boolean getCanPlaySong() {return canPlaySong;}
	public int getWorldNum() {return worldNum;}
	public void setWorldNum(int worldNum) {this.worldNum = worldNum;}
	public String[] getLevelNames() {return levelNames;}
	public void setLevelNames(String[] levelNames) {this.levelNames = levelNames;}
	
	}
