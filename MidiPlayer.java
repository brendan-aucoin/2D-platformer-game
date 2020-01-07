package music;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

import running.Main;

public class MidiPlayer 
{
	private static Sequencer	sequencer = null;
	private static Synthesizer	synthesizer = null;
	public  boolean canPlay(String path)
	{
		File midiFile = new File(Main.MIDIPATH + path);
		Sequence sequence = null;
		try
		{
			sequence = MidiSystem.getSequence(midiFile);
		}
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
			return false;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}

		try
		{
			sequencer = MidiSystem.getSequencer();
		}
		catch (MidiUnavailableException e)
		{
			e.printStackTrace();
			return false;
		}

		sequencer.addMetaEventListener(new MetaEventListener()
			{
				public void meta(MetaMessage event)
				{
					if (event.getType() == 47)
					{
						sequencer.close();
						if (synthesizer != null)
						{
							synthesizer.close();
						}
						//System.exit(0);
					}
				}
			});
		try
		{
			sequencer.open();
		}
		catch (MidiUnavailableException e)
		{
			e.printStackTrace();
			return false;
		}
		try
		{
			sequencer.setSequence(sequence);
		}
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
			return false;
		}

		if (! (sequencer instanceof Synthesizer))
		{
			try
			{
				synthesizer = MidiSystem.getSynthesizer();
				synthesizer.open();
				Receiver	synthReceiver = synthesizer.getReceiver();
				Transmitter	seqTransmitter = sequencer.getTransmitter();
				seqTransmitter.setReceiver(synthReceiver);
			}
			catch (MidiUnavailableException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	public void play()
	{
		sequencer.start();
	}
	public void pause()
	{
		sequencer.stop();
	}
	public void stop()
	{
		sequencer.close();
	}

}
