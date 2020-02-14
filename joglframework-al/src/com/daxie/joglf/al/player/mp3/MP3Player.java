package com.daxie.joglf.al.player.mp3;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 * MP3 player
 * @author Daba
 *
 */
public class MP3Player{
	private static int count=0;
	private static Map<Integer, PlayerThread> player_threads_map=new HashMap<>();
	
	/**
	 * Loads a MP3 file.
	 * @param sound_filename Filename
	 * @return Sound handle
	 */
	public static int LoadSound(String sound_filename) {
		AdvancedPlayer player;
		try(BufferedInputStream bis=new BufferedInputStream(new FileInputStream(sound_filename))){
			player=new AdvancedPlayer(bis);
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[MP3Player-LoadSound] Below is the stack trace.", true);
			LogFile.WriteWarn(str, false);
			
			return -1;
		}
		catch(JavaLayerException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[MP3Player-LoadSound] Below is the stack trace.", true);
			LogFile.WriteWarn(str, false);
			
			return -1;
		}
		
		int sound_handle=count;
		count++;
		
		PlayerThread player_thread=new PlayerThread(player);
		player_threads_map.put(sound_handle, player_thread);
		
		return sound_handle;
	}
	/**
	 * Deletes a sound.<br>
	 * Use this method to stop a sound.
	 * @param sound_handle Sound handle
	 * @return -1 on error and 0 on success
	 */
	public static int DeleteSound(int sound_handle) {
		if(player_threads_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[MP3Player-DeleteSound] No such sound. sound_handle:"+sound_handle, true);
			return -1;
		}
		
		PlayerThread player_thread=player_threads_map.get(sound_handle);
		player_thread.Close();
		
		player_threads_map.remove(sound_handle);
		
		return 0;
	}
	
	public static int PlaySound(int sound_handle) {
		if(player_threads_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[MP3Player-PlaySound] No such sound. sound_handle:"+sound_handle, true);
			return -1;
		}
		
		PlayerThread player_thread=player_threads_map.get(sound_handle);
		player_thread.start();
		
		return 0;
	}
	public static int StopSound(int sound_handle) {
		if(player_threads_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[MP3Player-StopSound] No such sound. sound_handle:"+sound_handle, true);
			return -1;
		}
		
		PlayerThread player_thread=player_threads_map.get(sound_handle);
		player_thread.Stop();
		
		return 0;
	}
	
	static class PlayerThread extends Thread{
		private AdvancedPlayer player;
		private int frame;
		
		public PlayerThread(AdvancedPlayer player) {
			this.player=player;
			frame=0;
			
			player.setPlayBackListener(new PlaybackListener() {
				@Override
				public void playbackFinished(PlaybackEvent event) {
					frame=event.getFrame();
				}
			});
		}
		
		@Override
		public void run() {
			try {
				player.play(frame,Integer.MAX_VALUE);
			}
			catch(JavaLayerException e) {
				String str=ExceptionFunctions.GetPrintStackTraceString(e);
				
				LogFile.WriteWarn("[MP3Player-PlayerThread-run] Below is the stack trace.", true);
				LogFile.WriteWarn(str, false);
			}
		}
		
		public void Stop() {
			player.stop();
		}
		public void Close() {
			player.close();
		}
	}
}
