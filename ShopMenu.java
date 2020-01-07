package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import entities.Entity;
import game.FileLoader;
import game.Game;
import game.Handler;
import game.State;
import id.ID;
import images.BufferedImageLoader;
import player.Archer;
import player.Knight;
import player.Mage;
import running.Main;

public class ShopMenu extends Menu{
	
	private Handler handler;
	private String character;
	private Rectangle lessFrictionBounds,moreFrictionKnightBounds,moreFrictionArcherBounds,rocketBoostBounds,moreJumpsBounds,morePowerBounds,moreJumpBounds;
	private Rectangle returnBounds;
	/*the tome buttons*/
	private Rectangle lightningBounds,tidalWaveBounds,waterDragonBounds,plasmaBlastBounds,darkMagicBounds,meteorBounds;
	private BufferedImage knightBackgroundImage,archerBackgroundImage,mageBackgroundImage;
	private Game game;
	private BufferedImage lightningTomeImage,tidalWaveTomeImage,waterDragonTomeImage,plasmaBlastTomeImage,darkMagicTomeImage,meteorTomeImage;
	private FileLoader tomeLoader,coinLoader;
	private BufferedImage equippedIconImage;
	private String currentTome;
	
	public ShopMenu(State specificState,Game game,Handler handler,BufferedImageLoader loader) {
		super(specificState);
		this.handler = handler;
		this.game = game;
		character = "";
		knightBackgroundImage = loader.loadImage("backgrounds","knight shop background.png");
		archerBackgroundImage = loader.loadImage("backgrounds","archer shop background.png");
		mageBackgroundImage = loader.loadImage("backgrounds","mage shop background.png");
		
		lightningTomeImage = loader.loadImage("single_images","lightningTomeImage.png");
		tidalWaveTomeImage = loader.loadImage("single_images","tidalWaveTomeImage.png");
		waterDragonTomeImage = loader.loadImage("single_images","waterDragonTomeImage.png");
		plasmaBlastTomeImage = loader.loadImage("single_images","plasmaBlastTomeImage.png");
		darkMagicTomeImage = loader.loadImage("single_images","darkMagicTomeImage.png");
		meteorTomeImage = loader.loadImage("single_images","meteorTomeImage.png");
		
		equippedIconImage = loader.loadImage("single_images","equippedIcon.png");
		tomeLoader = new FileLoader();
		tomeLoader.setFileName(Main.TEXTSPATH + "currentTome.txt");
		coinLoader = new FileLoader();
		coinLoader.setFileName(Main.TEXTSPATH + "coins.txt");
		currentTome = "";
	}
	public void render(Graphics2D g) {
		
		g.setColor(new Color(255,255,224));
		/*the bounds for the archer and knight buttons.*/
		/*top left*/
		if(moreJumpsBounds == null) {moreJumpsBounds = new Rectangle(Main.WIDTH/12,Main.HEIGHT/2 - Main.HEIGHT/4,Main.WIDTH/4,Main.HEIGHT/5);}
		if(morePowerBounds == null) {morePowerBounds = new Rectangle(Main.WIDTH/12,Main.HEIGHT/2 - Main.HEIGHT/4,Main.WIDTH/4,Main.HEIGHT/5);}
		
		/*bottom left*/
		if(lessFrictionBounds == null) {lessFrictionBounds = new Rectangle(Main.WIDTH/12,Main.HEIGHT/2 + Main.HEIGHT/6,Main.WIDTH/4,Main.HEIGHT/5);}
		if(moreFrictionKnightBounds == null) {moreFrictionKnightBounds = new Rectangle(Main.WIDTH/12,Main.HEIGHT/2 + Main.HEIGHT/6,Main.WIDTH/4,Main.HEIGHT/5);}
		
		/*top right*/
		if(moreFrictionArcherBounds == null) {moreFrictionArcherBounds = new Rectangle(Main.WIDTH - Main.WIDTH/3,Main.HEIGHT/2- Main.HEIGHT/4,Main.WIDTH/4,Main.HEIGHT/5);}
		if(moreJumpBounds == null) {moreJumpBounds = new Rectangle(Main.WIDTH - Main.WIDTH/3,Main.HEIGHT/2- Main.HEIGHT/4,Main.WIDTH/4,Main.HEIGHT/5);}
		
		/*bottom right*/
		if(rocketBoostBounds == null) {rocketBoostBounds = new Rectangle(Main.WIDTH - Main.WIDTH/3,Main.HEIGHT/2 + Main.HEIGHT/6,Main.WIDTH/4,Main.HEIGHT/5);}
		
		/*the bounds for the mage buttons*/
		if(lightningBounds == null) {lightningBounds = new Rectangle(Main.WIDTH/12,Main.HEIGHT/2 - Main.HEIGHT/3,Main.WIDTH/4,Main.HEIGHT/6);}
		if(tidalWaveBounds == null) {tidalWaveBounds = new Rectangle(Main.WIDTH/12,Main.HEIGHT/2 -Main.HEIGHT/12,Main.WIDTH/4,Main.HEIGHT/6);}
		if(waterDragonBounds == null) {waterDragonBounds = new Rectangle(Main.WIDTH/12,Main.HEIGHT/2 + Main.HEIGHT/6,Main.WIDTH/4,Main.HEIGHT/6);}
		if(plasmaBlastBounds == null) {plasmaBlastBounds = new Rectangle(Main.WIDTH - Main.WIDTH/3,Main.HEIGHT/2 - Main.HEIGHT/3,Main.WIDTH/4,Main.HEIGHT/6);}
		if(darkMagicBounds == null) {darkMagicBounds = new Rectangle(Main.WIDTH - Main.WIDTH/3,Main.HEIGHT/2 - Main.HEIGHT/12,Main.WIDTH/4,Main.HEIGHT/6);}
		if(meteorBounds == null) {meteorBounds = new Rectangle(Main.WIDTH - Main.WIDTH/3,Main.HEIGHT/2 + Main.HEIGHT/6,Main.WIDTH/4,Main.HEIGHT/6);}
		if(returnBounds == null) {returnBounds = new Rectangle(Main.WIDTH/20,g.getFont().getSize()*2,Main.WIDTH/6,Main.HEIGHT/12);}
		
		for(int i =0; i<handler.size(); i++) {
			Entity ent = handler.entity.get(i);
			if(ent instanceof Archer) {
				character = "Archer";
				drawArcherShop(g);
			}
			else if(ent instanceof Knight) {
				character = "Knight";
				drawKnightShop(g);
			}
			else if(ent instanceof Mage) {
				character = "Mage";
				drawMageShop(g);
			
			}
		}
		g.setColor(new Color(251,246,242));
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,80));
		g.drawString("Shop", (int)(Main.WIDTH/2 - g.getFont().getSize()*1.5),g.getFont().getSize());
		/*drawing the return button*/
		changeColour(g,returnBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(returnBounds.x, returnBounds.y, returnBounds.width, returnBounds.height,1000,50);
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,30));
		g.setColor(Color.BLACK);
		g.drawString("Return", (int)(returnBounds.x + returnBounds.width/2 - g.getFont().getSize()*2.2), returnBounds.y + returnBounds.height/2 + g.getFont().getSize()/3);
	}
	private void drawArcherShop(Graphics2D g){
		g.drawImage(archerBackgroundImage,0,0,Main.WIDTH,Main.HEIGHT,null);
		/*drawing the buttons*/
		changeColour(g,moreJumpsBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(moreJumpsBounds.x, moreJumpsBounds.y, moreJumpsBounds.width, moreJumpsBounds.height,30,30);
		changeColour(g,lessFrictionBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(lessFrictionBounds.x, lessFrictionBounds.y, lessFrictionBounds.width, lessFrictionBounds.height,30,30);
		changeColour(g,moreFrictionArcherBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(moreFrictionArcherBounds.x, moreFrictionArcherBounds.y, moreFrictionArcherBounds.width, moreFrictionArcherBounds.height,30,30);
		changeColour(g,rocketBoostBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(rocketBoostBounds.x, rocketBoostBounds.y, rocketBoostBounds.width, rocketBoostBounds.height,30,30);
		
		/*drawing the words*/
		g.setColor(Color.BLACK);
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,36));
		g.drawString("More Jumps", (int) (moreJumpsBounds.x + moreJumpsBounds.width/2 - g.getFont().getSize()*3.7), moreJumpsBounds.y + moreJumpsBounds.height/2 + g.getFont().getSize()/3);
		g.drawString("Less Friction", (int) (lessFrictionBounds.x + lessFrictionBounds.width/2 - g.getFont().getSize()*3.9), lessFrictionBounds.y + lessFrictionBounds.height/2 + g.getFont().getSize()/3);
		g.drawString("More Friction", (int) (moreFrictionArcherBounds.x + moreFrictionArcherBounds.width/2 - g.getFont().getSize()*4.3), moreFrictionArcherBounds.y + moreFrictionArcherBounds.height/2 + g.getFont().getSize()/3);
		g.drawString("Rocket Boost", (int) (rocketBoostBounds.x + rocketBoostBounds.width/2 - g.getFont().getSize()*3.9), rocketBoostBounds.y + rocketBoostBounds.height/2 + g.getFont().getSize()/3);
	
		/*drawing the prices*/
		g.drawString("10",moreJumpsBounds.x + moreJumpsBounds.width - g.getFont().getSize()*2,moreJumpsBounds.y + moreJumpsBounds.height - g.getFont().getSize()/2);
		g.drawString("4",lessFrictionBounds.x + lessFrictionBounds.width - g.getFont().getSize(),lessFrictionBounds.y + lessFrictionBounds.height - g.getFont().getSize()/2);
		g.drawString("5",moreFrictionArcherBounds.x + moreFrictionArcherBounds.width - g.getFont().getSize(),moreFrictionArcherBounds.y + moreFrictionArcherBounds.height - g.getFont().getSize()/2);
		g.drawString("7",rocketBoostBounds.x + rocketBoostBounds.width - g.getFont().getSize(),rocketBoostBounds.y + rocketBoostBounds.height - g.getFont().getSize()/2);
	}
	
	private void drawKnightShop(Graphics2D g) {
		g.drawImage(knightBackgroundImage,0,0,Main.WIDTH,Main.HEIGHT,null);
		/*drawing the buttons*/
		changeColour(g,morePowerBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(morePowerBounds.x, morePowerBounds.y, morePowerBounds.width, morePowerBounds.height,30,30);
		changeColour(g,moreFrictionKnightBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(moreFrictionKnightBounds.x, moreFrictionKnightBounds.y, moreFrictionKnightBounds.width, moreFrictionKnightBounds.height,30,30);
		changeColour(g,moreJumpBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(moreJumpBounds.x, moreJumpBounds.y, moreJumpBounds.width, moreJumpBounds.height,30,30);
		changeColour(g,rocketBoostBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(rocketBoostBounds.x, rocketBoostBounds.y, rocketBoostBounds.width, rocketBoostBounds.height,30,30);
		
		/*drawing the words*/
		g.setColor(Color.BLACK);
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,36));
		g.drawString("More Power", (int) (morePowerBounds.x + morePowerBounds.width/2 - g.getFont().getSize()*3.5), morePowerBounds.y + morePowerBounds.height/2 + g.getFont().getSize()/3);
		g.drawString("More Friction", (int) (moreFrictionKnightBounds.x + moreFrictionKnightBounds.width/2 - g.getFont().getSize()*4.3), moreFrictionKnightBounds.y + moreFrictionKnightBounds.height/2 + g.getFont().getSize()/3);
		g.drawString("More Jump", (int) (moreJumpBounds.x + moreJumpBounds.width/2 - g.getFont().getSize()*3.45), moreJumpBounds.y + moreJumpBounds.height/2 + g.getFont().getSize()/3);
		g.drawString("Rocket Boost", (int) (rocketBoostBounds.x + rocketBoostBounds.width/2 - g.getFont().getSize()*3.9), rocketBoostBounds.y + rocketBoostBounds.height/2 + g.getFont().getSize()/3);
	
		/*drawing the prices*/
		g.drawString("10",morePowerBounds.x + morePowerBounds.width - g.getFont().getSize()*2,morePowerBounds.y + morePowerBounds.height - g.getFont().getSize()/2);
		g.drawString("4",moreFrictionKnightBounds.x + moreFrictionKnightBounds.width - g.getFont().getSize(),moreFrictionKnightBounds.y + moreFrictionKnightBounds.height - g.getFont().getSize()/2);
		g.drawString("5",moreJumpBounds.x + moreJumpBounds.width - g.getFont().getSize(),moreJumpBounds.y + moreJumpBounds.height - g.getFont().getSize()/2);
		g.drawString("7",rocketBoostBounds.x + rocketBoostBounds.width - g.getFont().getSize(),rocketBoostBounds.y + rocketBoostBounds.height - g.getFont().getSize()/2);
		
	}
	
	private void drawMageShop(Graphics2D g) {
		g.drawImage(mageBackgroundImage,0,0,Main.WIDTH,Main.HEIGHT,null);
		/*drawing the buttons*/
		changeColour(g,lightningBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(lightningBounds.x, lightningBounds.y, lightningBounds.width, lightningBounds.height,30,30);
		changeColour(g,tidalWaveBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(tidalWaveBounds.x, tidalWaveBounds.y, tidalWaveBounds.width, tidalWaveBounds.height,30,30);
		changeColour(g,waterDragonBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(waterDragonBounds.x, waterDragonBounds.y, waterDragonBounds.width, waterDragonBounds.height,30,30);
		changeColour(g,plasmaBlastBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(plasmaBlastBounds.x, plasmaBlastBounds.y, plasmaBlastBounds.width, plasmaBlastBounds.height,30,30);
		changeColour(g,darkMagicBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(darkMagicBounds.x, darkMagicBounds.y, darkMagicBounds.width, darkMagicBounds.height,30,30);
		changeColour(g,meteorBounds,new Color(192,192,192),new Color(255,255,224));
		g.fillRoundRect(meteorBounds.x, meteorBounds.y, meteorBounds.width, meteorBounds.height,30,30);
		
		/*drawing the words*/
		g.setFont(new Font("Lucida Handwriting",Font.BOLD,36));
		g.setColor(Color.BLACK);
		g.drawString("Lightning", (int) (lightningBounds.x + lightningBounds.width/2 - g.getFont().getSize()*3.4), lightningBounds.y + lightningBounds.height/2 + g.getFont().getSize()/3);
		g.drawString("Tidal Wave", (int) (tidalWaveBounds.x + tidalWaveBounds.width/2 - g.getFont().getSize()*3.6), tidalWaveBounds.y + tidalWaveBounds.height/2 + g.getFont().getSize()/3);
		g.drawString("Water Dragon", (int) (waterDragonBounds.x + waterDragonBounds.width/2 - g.getFont().getSize()*4.5), waterDragonBounds.y + waterDragonBounds.height/2 + g.getFont().getSize()/3);
		g.drawString("Plasma Blast", (int) (plasmaBlastBounds.x + plasmaBlastBounds.width/2 - g.getFont().getSize()*4.1), plasmaBlastBounds.y + plasmaBlastBounds.height/2 + g.getFont().getSize()/3);
		g.drawString("Dark Magic", (int) (darkMagicBounds.x + darkMagicBounds.width/2 - g.getFont().getSize()*3.8), darkMagicBounds.y + darkMagicBounds.height/2 + g.getFont().getSize()/3);
		g.drawString("Meteor", (int) (meteorBounds.x + meteorBounds.width/2 - g.getFont().getSize()*2.2), meteorBounds.y + meteorBounds.height/2 + g.getFont().getSize()/3);
	
		/*drawing the images*/
		g.drawImage(lightningTomeImage, lightningBounds.x - lightningTomeImage.getWidth()*2, lightningBounds.y + lightningBounds.height/2, null);
		g.drawImage(tidalWaveTomeImage, tidalWaveBounds.x - tidalWaveTomeImage.getWidth()*2, tidalWaveBounds.y + tidalWaveBounds.height/2, null);
		g.drawImage(waterDragonTomeImage, waterDragonBounds.x - waterDragonTomeImage.getWidth()*2, waterDragonBounds.y + waterDragonBounds.height/2, null);
		g.drawImage(plasmaBlastTomeImage, plasmaBlastBounds.x - plasmaBlastTomeImage.getWidth()*2, plasmaBlastBounds.y + plasmaBlastBounds.height/2, null);
		g.drawImage(darkMagicTomeImage, darkMagicBounds.x - darkMagicTomeImage.getWidth()*2, darkMagicBounds.y + darkMagicBounds.height/2, null);
		g.drawImage(meteorTomeImage, meteorBounds.x - meteorTomeImage.getWidth()*2, meteorBounds.y + meteorBounds.height/2, null);
		
		/*drawing the prices*/
		//lightning
		g.drawString("2",lightningBounds.x + lightningBounds.width -g.getFont().getSize(),lightningBounds.y + lightningBounds.height -g.getFont().getSize()/2);
		//tidal wave
		g.drawString("3",tidalWaveBounds.x + tidalWaveBounds.width -g.getFont().getSize(),tidalWaveBounds.y + tidalWaveBounds.height -g.getFont().getSize()/2);
	
		//water dragon
		g.drawString("6",waterDragonBounds.x + waterDragonBounds.width -g.getFont().getSize(),waterDragonBounds.y + waterDragonBounds.height -g.getFont().getSize()/2);
	
		//plasma blast
		g.drawString("15",plasmaBlastBounds.x + plasmaBlastBounds.width -g.getFont().getSize()*2,plasmaBlastBounds.y + plasmaBlastBounds.height -g.getFont().getSize()/2);
	
		//dark magic
		g.drawString("30",darkMagicBounds.x + darkMagicBounds.width -g.getFont().getSize()*2,darkMagicBounds.y + darkMagicBounds.height -g.getFont().getSize()/2);
	
		//meteor
		g.drawString("50",meteorBounds.x + meteorBounds.width -g.getFont().getSize()*2,meteorBounds.y + meteorBounds.height -g.getFont().getSize()/2);
	
		/*drawing the equipped icon*/
		for(int i=0;i < handler.entity.size();i++) {
			Entity ent = handler.entity.get(i);
			if(ent.getId() == ID.Player && ent instanceof Mage) {
				Mage mage = (Mage)ent;
				currentTome = mage.getCurrentTome();
			}
		}
		
		if(currentTome.equals("lightning")) {
			g.drawImage(equippedIconImage, lightningBounds.x + lightningBounds.width + equippedIconImage.getWidth(), lightningBounds.y + lightningBounds.height/2 - equippedIconImage.getHeight()/2, null);
		}
		else if(currentTome.equalsIgnoreCase("tidal wave") || currentTome.equalsIgnoreCase("tidal_wave") || currentTome.equalsIgnoreCase("tidalWave")) {
			g.drawImage(equippedIconImage, tidalWaveBounds.x + tidalWaveBounds.width + equippedIconImage.getWidth(), tidalWaveBounds.y + tidalWaveBounds.height/2 - equippedIconImage.getHeight()/2, null);
		}
		else if(currentTome.equalsIgnoreCase("water dragon") || currentTome.equalsIgnoreCase("water_dragon")||currentTome.equalsIgnoreCase("waterDragon")) {
			g.drawImage(equippedIconImage, waterDragonBounds.x + waterDragonBounds.width + equippedIconImage.getWidth(), waterDragonBounds.y + waterDragonBounds.height/2 - equippedIconImage.getHeight()/2, null);
		}
		else if(currentTome.equalsIgnoreCase("plasma blast") || currentTome.equalsIgnoreCase("plasmaBlast") || currentTome.equalsIgnoreCase("plasma_blast")) {
			g.drawImage(equippedIconImage, plasmaBlastBounds.x + plasmaBlastBounds.width + equippedIconImage.getWidth()/2, plasmaBlastBounds.y + plasmaBlastBounds.height/2 - equippedIconImage.getHeight()/2, null);
		}
		else if(currentTome.equalsIgnoreCase("darkMagic")||currentTome.equalsIgnoreCase("dark magic")||currentTome.equalsIgnoreCase("dark_magic")) {
			g.drawImage(equippedIconImage, darkMagicBounds.x + darkMagicBounds.width + equippedIconImage.getWidth()/2, darkMagicBounds.y + darkMagicBounds.height/2 - equippedIconImage.getHeight()/2, null);
		}
		else if(currentTome.equalsIgnoreCase("meteor")) {
			g.drawImage(equippedIconImage, meteorBounds.x + meteorBounds.width + equippedIconImage.getWidth()/2, meteorBounds.y + meteorBounds.height/2 - equippedIconImage.getHeight()/2, null);
		}
		else {
			g.drawImage(equippedIconImage, lightningBounds.x + lightningBounds.width + equippedIconImage.getWidth(), lightningBounds.y + lightningBounds.height/2 - equippedIconImage.getHeight()/2, null);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		
		if(mouseOver(returnBounds)) {
			Game.gameState = State.Paused;
			game.removeMouseListener(this);
		}
		
		if(character.equalsIgnoreCase("Archer")) {
			handleArcherInput(e);
		}
		else if(character.equalsIgnoreCase("Knight")) {
			handleKnightInput(e);
		}
		else if(character.equalsIgnoreCase("Mage")) {
			handleMageInput(e);
		}
	}
	private void handleArcherInput(MouseEvent e) {
		for(int i= 0; i <handler.entity.size();i++) {
			if(handler.entity.get(i).getId() == ID.Player && handler.entity.get(i) instanceof Archer) {
			Archer archer = (Archer)handler.entity.get(i);
			if(mouseOver(moreJumpsBounds)) {
				if(game.getCoins() >= 10) {
					archer.setNumJumps(archer.getNumJumps() *2);
					game.setCoins(game.getCoins() -10);
					decreaseCoins();
				}
			}
			
			else if(mouseOver(lessFrictionBounds)) {
				if(game.getCoins() >= 4) {
					archer.setInitialFriction(archer.getInitialFriction() *2);
					game.setCoins(game.getCoins() -4);
					decreaseCoins();
				}
			}
		
			else if(mouseOver(moreFrictionArcherBounds)) {
				if(game.getCoins() >= 5) {
					archer.setInitialFriction(archer.getInitialFriction()/3);
					game.setCoins(game.getCoins() -5);
					decreaseCoins();
				}
			}
			else if(mouseOver(rocketBoostBounds)) {
				if(game.getCoins() >= 7) {
					
					game.setCoins(game.getCoins() -7);
					decreaseCoins();
				}
			}
		}
		}
	}
	
	private void handleKnightInput(MouseEvent e) {
		for(int i=0; i < handler.entity.size(); i++) {
			if(handler.entity.get(i).getId() == ID.Player && handler.entity.get(i) instanceof Knight) {
				Knight knight = (Knight)handler.entity.get(i);
				if(mouseOver(morePowerBounds)) {
					if(game.getCoins() >= 10) {
						knight.setDamage((float) (knight.getDamage() *2.5));
						game.setCoins(game.getCoins() -10);
						decreaseCoins();
					}
				}
				else if(mouseOver(moreFrictionKnightBounds)) {
					if(game.getCoins() >= 4) {
						knight.setInitialFriction(knight.getInitialFriction()/3);
						game.setCoins(game.getCoins() -4);
						decreaseCoins();
					}
				}
				else if(mouseOver(moreJumpBounds)) {
					if(game.getCoins() >= 5) {
						knight.setJumpHeight(knight.getJumpHeight() *2);
						game.setCoins(game.getCoins() -5);
						decreaseCoins();
					}
				}
				else if(mouseOver(rocketBoostBounds)) {
					if(game.getCoins() >= 7) {
						
						game.setCoins(game.getCoins() -7);
						decreaseCoins();
					}
				}
			}
		}
	}
	
	private void handleMageInput(MouseEvent e) {
		if(mouseOver(lightningBounds)) {
			if(!currentTome.equalsIgnoreCase("lightning")) {
			setMageTome("lightning",2);}
		}
		else if(mouseOver(tidalWaveBounds)) {
			if(!currentTome.equalsIgnoreCase("tidal wave")) {
			setMageTome("tidal wave",3);}
		}
		else if(mouseOver(waterDragonBounds)) {
			if(!currentTome.equalsIgnoreCase("water dragon")) {
			setMageTome("water dragon",6);}
		}
		else if(mouseOver(plasmaBlastBounds)) {
			if(!currentTome.equalsIgnoreCase("plasma blast")) {
			setMageTome("plasma blast",15);}
		}
		else if(mouseOver(darkMagicBounds)) {
			if(!currentTome.equalsIgnoreCase("dark magic")) {
			setMageTome("dark magic",30);}
		}
		else if(mouseOver(meteorBounds)) {
			if(!currentTome.equalsIgnoreCase("meteor")) {
			setMageTome("meteor",50);}
		}
	}
	private void setMageTome(String currentTome,int coinValue) {
		for(int i =0; i< handler.entity.size();i++) {
			Entity ent = handler.entity.get(i);
			if(ent instanceof Mage && ent.getId() == ID.Player) {
				Mage mage = (Mage)ent;
				if(game.getCoins() >= coinValue) {
					mage.setCurrentTome(currentTome);
					tomeLoader.createFile();
					tomeLoader.write(currentTome);
					tomeLoader.closeOutput();
					game.setCoins(game.getCoins() -coinValue);
					decreaseCoins();
				}
			}
		}
	}
	
	private void decreaseCoins() {
		coinLoader.createFile();
		coinLoader.write(String.valueOf(game.getCoins()));
		coinLoader.closeOutput();
	}
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
