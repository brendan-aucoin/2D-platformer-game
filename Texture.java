package images;

import java.awt.image.BufferedImage;

public class Texture 
{
	/*the blocks*/
	private SpriteSheet blockSheet;
	private BufferedImage blockSheetImage = null;
	public BufferedImage blocks[] = new BufferedImage[64];
	/*the goal*/
	private SpriteSheet doorwaySheet1,doorwaySheet2;
	private BufferedImage doorwaySheetImage1,doorwaySheetImage2;
	public BufferedImage doorway[] = new BufferedImage[12];
	/*the players*/
	private SpriteSheet archerBowSheet,archerSaberSheet,knightSwordSheet,knightTridentSheet,mageSheet;
	private BufferedImage archerBowSheetImage,archerSaberSheetImage,knightSwordSheetImage,knightTridentSheetImage,mageSheetImage;
	public BufferedImage archer[] = new BufferedImage[64];
	public BufferedImage knight[] = new BufferedImage[64];
	public BufferedImage[] mage = new BufferedImage[64];
	
	/*the projectiles*/
	/*the arrow*/
	public BufferedImage[] arrow;
	/*the fire ball*/
	public BufferedImage[] fireBall = new BufferedImage[25];
	private SpriteSheet fireBallSheetRight,fireBallSheetLeft;
	private BufferedImage fireBallSheetImageRight,fireBallSheetImageLeft;
	/*the ligthing bolt*/
	public BufferedImage[]lightning;
	private SpriteSheet lightningSheet;
	private BufferedImage lightningSheetImage;
	/*the tidal wave*/
	public BufferedImage[] tidalWave;
	private SpriteSheet tidalWaveSheetRight,tidalWaveSheetLeft;
	private BufferedImage tidalWaveSheetImageRight,tidalWaveSheetImageLeft;
	/*the water dragon*/
	public BufferedImage[] waterDragon;
	private SpriteSheet waterDragonSheetRight,waterDragonSheetLeft;
	private BufferedImage waterDragonSheetImageRight,waterDragonSheetImageLeft;
	
	
	/*the explosion*/
	public BufferedImage[] explosion;
	private SpriteSheet explosionSheet;
	private BufferedImage explosionSheetImage;
	public Texture()
	{
		BufferedImageLoader loader = new BufferedImageLoader();
		/*the blocks*/
		blockSheetImage = loader.loadImage("sprite_sheets","block sprite sheetTest.png");
		blockSheet = new SpriteSheet(blockSheetImage);
		blocks = readFromSheet(blocks,blockSheet,8,8,32,32);
		/*the player*/
		loadPlayerSprites(loader);
		loadProjectileSprites(loader);
		loadGoalSprites(loader);
		loadExplosionSprites(loader);
	}
	private void loadPlayerSprites(BufferedImageLoader loader)
	{
		/*the archer bow*/
		archerBowSheetImage = loader.loadImage("sprite_sheets","elf bow spritesheet.png");
		archerBowSheet = new SpriteSheet(archerBowSheetImage);
		//the walking right
		archer[0] = archerBowSheet.crop(17,140,34,50);//the base image
		archer[1] = archerBowSheet.crop(20,717,29,49);
		archer[2] = archerBowSheet.crop(85,718,28,48);
		archer[3] = archerBowSheet.crop(149,718,28,47);
		archer[4] = archerBowSheet.crop(211,717,30,48);
		archer[5] = archerBowSheet.crop(273,717,32,49);
		archer[6] = archerBowSheet.crop(336,718,33,48);
		archer[7] = archerBowSheet.crop(401,718,32,48);
		archer[8] = archerBowSheet.crop(467,717,30,49);
		archer[9] = archerBowSheet.crop(533,717,28,49);
		//the walking left
		archer[10] = archerBowSheet.crop(14,589,29,49);
		archer[11] = archerBowSheet.crop(78,590,28,48);
		archer[12] = archerBowSheet.crop(142,590,28,47);
		archer[13] = archerBowSheet.crop(206,589,30,48);
		archer[14] = archerBowSheet.crop(270,589,32,49);
		archer[15] = archerBowSheet.crop(334,590,33,48);
		archer[16] = archerBowSheet.crop(398,590,32,48);
		archer[17] = archerBowSheet.crop(462,589,30,49);
		archer[18] = archerBowSheet.crop(526,589,28,49);
		//the jumping images
		archer[19] = archerBowSheet.crop(339,973,34,49);
		archer[20] = archerBowSheet.crop(330,845,34,49);
		//the bow right
		archer[21] = archerBowSheet.crop(11,1229,53,49);
		archer[22] = archerBowSheet.crop(75,1229,42,49);
		archer[23] = archerBowSheet.crop(144,1229,42,49);
		archer[24] = archerBowSheet.crop(207,1229,45,49);
		archer[25] = archerBowSheet.crop(271,1224,44,54);
		archer[26] = archerBowSheet.crop(339,1222,45,56);
		archer[27] = archerBowSheet.crop(401,1220,46,58);
		archer[28] = archerBowSheet.crop(462,1223,46,55);
		archer[29] = archerBowSheet.crop(524,1225,45,53);
		archer[30] = archerBowSheet.crop(591,1224,36,54);
		archer[31] = archerBowSheet.crop(656,1224,35,54);
		//the bow left
		archer[32] = archerBowSheet.crop(0,1101,52,49);
		archer[33] = archerBowSheet.crop(74,1101,42,49);
		archer[34] = archerBowSheet.crop(113,1101,42,49);
		archer[35] = archerBowSheet.crop(195,1101,45,49);
		archer[36] = archerBowSheet.crop(260,1096,44,54);
		archer[37] = archerBowSheet.crop(319,1094,45,56);
		archer[38] = archerBowSheet.crop(384,1092,46,58);
		archer[39] = archerBowSheet.crop(451,1095,46,55);
		archer[40] = archerBowSheet.crop(518,1097,45,53);
		archer[41] = archerBowSheet.crop(587,1096,36,54);
		archer[42] = archerBowSheet.crop(651,1096,35,54);
		/*the archer saber*/
		archerSaberSheetImage = loader.loadImage("sprite_sheets","elf saber spritesheet.png");
		archerSaberSheet = new SpriteSheet(archerSaberSheetImage);
		//the saber right
		archer[43] = archerSaberSheet.crop(276,1999,31,47);
		archer[44] = archerSaberSheet.crop(468,1998,31,48);
		archer[45] = archerSaberSheet.crop(659,1997,33,49);
		archer[46] = archerSaberSheet.crop(851,1997,50,49);
		archer[47] = archerSaberSheet.crop(1043,1997,55,49);
		//the saber left
		archer[48] = archerSaberSheet.crop(268,1615,31,47);
		archer[49] = archerSaberSheet.crop(460,1614,31,48);
		archer[50] = archerSaberSheet.crop(651,1613,33,49);
		archer[51] = archerSaberSheet.crop(825,1613,50,49);
		archer[52] = archerSaberSheet.crop(1013,1613,55,49);
		
		
		/*the knight sword*/
		knightSwordSheetImage = loader.loadImage("sprite_sheets","knight sword spritesheet.png");
		knightSwordSheet = new SpriteSheet(knightSwordSheetImage);
		/*the knight trident*/
		knightTridentSheetImage = loader.loadImage("sprite_sheets","knight trident spritesheet.png");
		knightTridentSheet = new SpriteSheet(knightTridentSheetImage);
		
		/*the knight walking right*/
		knight[1] = knightSwordSheet.crop(17,712,26,54);
		knight[2] = knightSwordSheet.crop(81,713,28,53);
		knight[3] = knightSwordSheet.crop(145,712,26,53);
		knight[4] = knightSwordSheet.crop(209,712,26,53);
		knight[5] = knightSwordSheet.crop(273,712,26,54);
		knight[6] = knightSwordSheet.crop(335,713,29,53);
		knight[7] = knightSwordSheet.crop(401,712,26,54);
		knight[8] = knightSwordSheet.crop(464,712,27,54);
		knight[9] = knightSwordSheet.crop(529,712,26,54);
		/*the knight walking left*/
		knight[10] = knightSwordSheet.crop(20,584,26,54);
		knight[11] = knightSwordSheet.crop(82,585,28,53);
		knight[12] = knightSwordSheet.crop(148,584,26,53);
		knight[13] = knightSwordSheet.crop(212,584,26,53);
		knight[14] = knightSwordSheet.crop(276,584,26,54);
		knight[15] = knightSwordSheet.crop(339,585,29,53);
		knight[16] = knightSwordSheet.crop(404,584,26,54);
		knight[17] = knightSwordSheet.crop(468,584,27,54);
		knight[18] = knightSwordSheet.crop(532,584,26,54);
		/*the jumping images*/
		knight[19] = knightSwordSheet.crop(275,968,37,54);
		knight[20] = knightSwordSheet.crop(263,840,37,54);
		/*the sword right*/
		knight[21] = knightSwordSheet.crop(275,1994,34,52);
		knight[22] = knightSwordSheet.crop(457,1992,37,54);
		knight[23] = knightSwordSheet.crop(640,1992,58,54);
		knight[24] = knightSwordSheet.crop(851,1992,94,54);
		knight[25] = knightSwordSheet.crop(1043,1992,92,54);
		/*the sword left*/
		knight[26] = knightSwordSheet.crop(266,1610,34,52);
		knight[27] = knightSwordSheet.crop(465,1608,37,54);
		knight[28] = knightSwordSheet.crop(645,1608,58,54);
		knight[29] = knightSwordSheet.crop(782,1608,94,54);
		knight[30] = knightSwordSheet.crop(976,1608,92,54);
		
		/*the trident right*/
		knight[31] = knightTridentSheet.crop(455,1992,96,54);
		knight[32] = knightTridentSheet.crop(644,1992,96,54);
		knight[33] = knightTridentSheet.crop(844,1992,96,54);
		knight[34] = knightTridentSheet.crop(1038,1992,96,54);
		knight[35] = knightTridentSheet.crop(1226,1992,96,54);
		knight[36] = knightTridentSheet.crop(1412,1992,96,54);
		/*the trident left*/
		knight[37] = knightTridentSheet.crop(408,1608,96,54);
		knight[38] = knightTridentSheet.crop(603,1608,96,54);
		knight[39] = knightTridentSheet.crop(787,1608,96,54);
		knight[40] = knightTridentSheet.crop(977,1608,96,54);
		knight[41] = knightTridentSheet.crop(1173,1608,96,54);
		knight[42] = knightTridentSheet.crop(1372,1608,96,54);
		
		/*the mage*/
		mageSheetImage = loader.loadImage("sprite_sheets","mage spritesheet (2).png");
		mageSheet = new SpriteSheet(mageSheetImage);
		/*the walking right*/
		mage[1] = mageSheet.crop(20,717,22,49);
		mage[2] = mageSheet.crop(83,718,26,48);
		mage[3] = mageSheet.crop(148,717,23,48);
		mage[4] = mageSheet.crop(210,717,24,48);
		mage[5] = mageSheet.crop(273,717,25,49);
		mage[6] = mageSheet.crop(335,718,29,48);
		mage[7] = mageSheet.crop(401,717,25,49);
		mage[8] = mageSheet.crop(466,717,24,49);
		mage[9] = mageSheet.crop(531,717,23,49);
		/*the walking left*/
		mage[10] = mageSheet.crop(21,589,22,49);
		mage[11] = mageSheet.crop(82,590,26,48);
		mage[12] = mageSheet.crop(148,589,23,48);
		mage[13] = mageSheet.crop(213,589,24,48);
		mage[14] = mageSheet.crop(277,589,25,49);
		mage[15] = mageSheet.crop(339,590,29,48);
		mage[16] = mageSheet.crop(405,589,25,49);
		mage[17] = mageSheet.crop(469,589,24,49);
		mage[18] = mageSheet.crop(533,589,23,49);
		
		/*the jumping images*/
		mage[19] = mageSheet.crop(275,973,37,49);
		mage[20] = mageSheet.crop(263,845,37,49);
		
		/*the attacking animation right*/
		mage[21] = mageSheet.crop(20,205,22,49);
		mage[22] = mageSheet.crop(83,205,24,49);
		mage[23] = mageSheet.crop(148,205,22,49);
		mage[24] = mageSheet.crop(209,205,27,49);
		mage[25] = mageSheet.crop(268,205,34,49);
		mage[26] = mageSheet.crop(332,205,34,49);
		mage[27] = mageSheet.crop(399,205,29,49);
		/*the attacking animation left*/
		mage[28] = mageSheet.crop(21,77,22,49);
		mage[29] = mageSheet.crop(84,77,24,49);
		mage[30] = mageSheet.crop(149,77,22,49);
		mage[31] = mageSheet.crop(211,77,27,49);
		mage[32] = mageSheet.crop(273,77,34,49);
		mage[33] = mageSheet.crop(337,77,34,49);
		mage[34] = mageSheet.crop(403,77,29,49);
	}
	
	private void loadProjectileSprites(BufferedImageLoader loader)
	{
		/*the arrow*/
		arrow = new BufferedImage[2];
		arrow[0]= loader.loadImage("single_images","arrowRight.png");
		arrow[1] = loader.loadImage("single_images","arrowLeft.png");
		/*the fireball*/
		fireBallSheetImageRight = loader.loadImage("sprite_sheets","magic sprite sheet1.png");
		fireBallSheetImageLeft = loader.loadImage("sprite_sheets","magic sprite sheet1 left.png");
		fireBallSheetRight = new SpriteSheet(fireBallSheetImageRight);
		fireBallSheetLeft = new SpriteSheet(fireBallSheetImageLeft);
		/*to the right*/
		fireBall[1] = fireBallSheetRight.crop(1137,460,46,28);
		fireBall[2] = fireBallSheetRight.crop(1245,460,47,28);
		fireBall[3] = fireBallSheetRight.crop(1367,460,43,28);
		fireBall[4] = fireBallSheetRight.crop(1425,452,69,44);
		fireBall[5] = fireBallSheetRight.crop(1511,453,65,41);
		fireBall[6] = fireBallSheetRight.crop(1591,455,67,38);
		
		/*to the left*/
		fireBall[7] = fireBallSheetLeft.crop(520,1591,46,28);
		fireBall[8] = fireBallSheetLeft.crop(411,1591,47,28);
		fireBall[9] = fireBallSheetLeft.crop(293,1591,43,28);
		fireBall[10] = fireBallSheetLeft.crop(209,1583,69,44);
		fireBall[11] = fireBallSheetLeft.crop(127,1585,65,41);
		fireBall[12] = fireBallSheetLeft.crop(45,1586,67,38);
		
		/*the lightning*/
		lightningSheetImage = loader.loadImage("sprite_sheets","lightning bolt spritesheet.png");
		lightningSheet = new SpriteSheet(lightningSheetImage);
		lightning = new BufferedImage[9];
		lightning[0] = loader.loadImage("single_images","lightningCloud.png");
		lightning[1] = lightningSheet.crop(718,1311,29,181);
		
		/*the tidal wave*/
		tidalWave = new BufferedImage[12];
		tidalWaveSheetImageRight = loader.loadImage("sprite_sheets","magic sprite sheet2.png");
		tidalWaveSheetRight = new SpriteSheet(tidalWaveSheetImageRight);
		tidalWaveSheetImageLeft = loader.loadImage("sprite_sheets","magic sprite sheet2 left.png");
		tidalWaveSheetLeft = new SpriteSheet(tidalWaveSheetImageLeft);
		/*to the right*/
		tidalWave[1] = tidalWaveSheetRight.crop(570,736,20,14);
		tidalWave[2] = tidalWaveSheetRight.crop(559,760,28,33);
		tidalWave[3] = tidalWaveSheetRight.crop(537,806,53,40);
		tidalWave[4] = tidalWaveSheetRight.crop(531,856,71,33);
		/*to the left*/
		tidalWave[5] = tidalWaveSheetLeft.crop(309,736,20,14);
		tidalWave[6] = tidalWaveSheetLeft.crop(312,760,28,33);
		tidalWave[7] = tidalWaveSheetLeft.crop(309,806,53,40);
		tidalWave[8] = tidalWaveSheetLeft.crop(297,856,71,33);
		
		/*the water dragon*/
		waterDragon = new BufferedImage[20];
		waterDragonSheetImageRight = loader.loadImage("sprite_sheets","water dragon spritesheetRight.png");
		waterDragonSheetRight = new SpriteSheet(waterDragonSheetImageRight);
		waterDragonSheetImageLeft = loader.loadImage("sprite_sheets","water dragon spritesheetLeft.png");
		waterDragonSheetLeft = new SpriteSheet(waterDragonSheetImageLeft);
		/*to the right*/
		waterDragon[1] = waterDragonSheetRight.crop(657,174,79,20);
		waterDragon[2] = waterDragonSheetRight.crop(523,117,113,75);
		waterDragon[3] = waterDragonSheetRight.crop(373,94,128,89);
		waterDragon[4] = waterDragonSheetRight.crop(236,85,127,91);
		waterDragon[5] = waterDragonSheetRight.crop(96,61,125,102);
		waterDragon[6] = waterDragonSheetRight.crop(492,238,127,89);
		waterDragon[7] = waterDragonSheetRight.crop(347,226,124,79);
		waterDragon[8] = waterDragonSheetRight.crop(195,224,128,70);
		/*to the left*/
		waterDragon[9] = waterDragonSheetLeft.crop(47,174,79,20);
		waterDragon[10] = waterDragonSheetLeft.crop(147,117,113,75);
		waterDragon[11] = waterDragonSheetLeft.crop(282,94,128,89);
		waterDragon[12] = waterDragonSheetLeft.crop(420,85,127,91);
		waterDragon[13] = waterDragonSheetLeft.crop(562,61,125,102);
		waterDragon[14] = waterDragonSheetLeft.crop(164,238,127,89);
		waterDragon[15] = waterDragonSheetLeft.crop(312,226,124,79);
		waterDragon[16] = waterDragonSheetLeft.crop(460,224,128,70);
		
	}
	
	private void loadExplosionSprites(BufferedImageLoader loader) {
		explosionSheetImage = loader.loadImage("sprite_sheets","explosion spritesheet.png");
		explosionSheet = new SpriteSheet(explosionSheetImage);
		explosion = new BufferedImage[30];
		explosion[1] = explosionSheet.crop(771,192,31,25);
		explosion[2] = explosionSheet.crop(1080,179,55,38);
		explosion[3] = explosionSheet.crop(1395,167,75,50);
		explosion[4] = explosionSheet.crop(102,380,96,69);
		explosion[5] = explosionSheet.crop(414,369,117,81);
		explosion[6] = explosionSheet.crop(721,367,132,81);
		explosion[7] = explosionSheet.crop(1040,352,141,99);
		explosion[8] = explosionSheet.crop(1351,340,152,108);
		explosion[9] = explosionSheet.crop(61,565,176,115);
		explosion[10] = explosionSheet.crop(375,557,188,124);
		explosion[11] = explosionSheet.crop(693,546,190,135);
		explosion[12] = explosionSheet.crop(1006,536,207,142);
		explosion[13] = explosionSheet.crop(1322,515,224,165);
		explosion[14] = explosionSheet.crop(40,738,227,173);
		explosion[15] = explosionSheet.crop(351,734,246,176);
		explosion[16] = explosionSheet.crop(668,725,251,182);
		explosion[17] = explosionSheet.crop(980,724,262,184);
		explosion[18] = explosionSheet.crop(1301,723,280,181);
	}
	private void loadGoalSprites(BufferedImageLoader loader)
	{
		doorwaySheetImage1 = loader.loadImage("sprite_sheets","doorwaySpritesheet1.png");
		doorwaySheetImage2 = loader.loadImage("sprite_sheets","doorwaySpritesheet2.png");
		doorwaySheet1 = new SpriteSheet(doorwaySheetImage1);
		doorwaySheet2 = new SpriteSheet(doorwaySheetImage2);
		
		doorway[0] = doorwaySheet2.crop(16,128,62,62);
		doorway[1] = doorwaySheet1.crop(18,64,59,64);
		doorway[2] = doorwaySheet1.crop(210,192,59,63);
		doorway[3] = doorwaySheet2.crop(112,128,62,62);
		doorway[4] = doorwaySheet2.crop(304,128,62,62);
		doorway[5] = doorwaySheet2.crop(114,128,59,64);
		doorway[6] = doorwaySheet2.crop(304,128,62,62);
		
	}
	private BufferedImage[] readFromSheet(BufferedImage[] sprites, SpriteSheet sheet,int rows,int cols,int width,int height)
	{
		for(int i = 0; i < rows; i++)
		{
			for(int x = 0; x < cols; x++)
			{
				sprites[(i*cols)+x] = sheet.crop(x*width, i*height, width, height);
			}
		}
		return sprites;
	}
	}
