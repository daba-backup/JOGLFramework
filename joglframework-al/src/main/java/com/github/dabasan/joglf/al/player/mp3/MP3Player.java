package com.github.dabasan.joglf.al.player.mp3;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * MP3 player
 * 
 * @author Daba
 *
 */
public class MP3Player {
	private static Logger logger = LoggerFactory.getLogger(MP3Player.class);

	private static int count = 0;
	private static Map<Integer, AdvancedPlayer> players_map = new HashMap<>();

	/**
	 * Loads a MP3 file.
	 * 
	 * @param sound_filename
	 *            Filename
	 * @return Sound handle
	 */
	public static int LoadSound(String sound_filename) {
		logger.info("Start loading a sound. sound_filename={}", sound_filename);

		BufferedInputStream stream = null;
		AdvancedPlayer player = null;

		try {
			stream = new BufferedInputStream(new FileInputStream(sound_filename));
			player = new AdvancedPlayer(stream);
		} catch (final IOException e) {
			logger.error("Error while loading a sound. sound_filename={}", sound_filename);
			return -1;
		} catch (final JavaLayerException e) {
			logger.error("Error while loading a sound. sound_filename={}", sound_filename);
			logger.error("", e);
			return -1;
		}

		final int sound_handle = count;
		count++;

		players_map.put(sound_handle, player);

		return sound_handle;
	}
	/**
	 * Deletes a sound.<br>
	 * Use this method to stop a sound.
	 * 
	 * @param sound_handle
	 *            Sound handle
	 * @return -1 on error and 0 on success
	 */
	public static int DeleteSound(int sound_handle) {
		if (players_map.containsKey(sound_handle) == false) {
			logger.warn("No such sound. sound_handle={}", sound_handle);
			return -1;
		}

		final AdvancedPlayer player = players_map.get(sound_handle);
		player.close();

		players_map.remove(sound_handle);

		return 0;
	}

	public static int PlaySound(int sound_handle) {
		if (players_map.containsKey(sound_handle) == false) {
			logger.warn("No such sound. sound_handle={}", sound_handle);
			return -1;
		}

		final AdvancedPlayer player = players_map.get(sound_handle);

		final PlayerThread player_thread = new PlayerThread(player);
		player_thread.start();

		return 0;
	}

	/*
	 * public static int StopSound(int sound_handle) {
	 * if(players_map.containsKey(sound_handle)==false) {
	 * logger.warn("No such sound. sound_handle={}",sound_handle); return -1; }
	 * 
	 * AdvancedPlayer player=players_map.get(sound_handle); player.stop();
	 * 
	 * return 0; }
	 */

	static class PlayerThread extends Thread {
		private final AdvancedPlayer player;

		public PlayerThread(AdvancedPlayer player) {
			this.player = player;
		}
		@Override
		public void run() {
			try {
				player.play();
			} catch (final JavaLayerException e) {
				logger.error("Error", e);
			}
		}
	}
}