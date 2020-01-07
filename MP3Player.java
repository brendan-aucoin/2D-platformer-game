package music;

//import javazoom.jl.player.advanced.AdvancedPlayer;
//import javazoom.jl.player.advanced.PlaybackListener;

public class MP3Player //extends PlaybackListener  implements Runnable
{
	private String filePath;
    //private AdvancedPlayer player;
    private Thread playerThread;  
	public MP3Player(String filePath)
	{
		this.filePath = filePath;
	}
	public MP3Player()
	{
		filePath = "";
	}
	 public void run()
	    {
	      /*  try
	        {
	            this.player.play();
	        }
	        catch (javazoom.jl.decoder.JavaLayerException ex)
	        {
	            ex.printStackTrace();
	        }*/

	    }
	public void play()
    {
      /*  try
        {
            String urlAsString =  this.getClass().getResource("/music/") + filePath;
            this.player = new AdvancedPlayer
            (
                new java.net.URL(urlAsString).openStream(),
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
            );

            this.player.setPlayBackListener(this);

            this.playerThread = new Thread(this, "AudioPlayerThread");

            this.playerThread.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }*/
    }
	public void play(String filePath)
    {
        try
        {
           /* String urlAsString =  this.getClass().getResource("/music/") + filePath;
            this.player = new AdvancedPlayer
            (
                new java.net.URL(urlAsString).openStream(),
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
            );

            this.player.setPlayBackListener(this);

            this.playerThread = new Thread(this, "AudioPlayerThread");

            this.playerThread.start();*/
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
	public void stop()
	{
		//player.close();
	}
	
	  public String getFilePath() {return filePath;}
	  public void setFilePath(String filePath) {this.filePath = filePath;}
	

}
