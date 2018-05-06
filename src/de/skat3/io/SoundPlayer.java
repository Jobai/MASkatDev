package de.skat3.io;

import jaco.mp3.player.MP3Player;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Utility class that can play sounds an background music.
 * 
 * @author Jonas Bauer
 *
 */
public class SoundPlayer {

  private MP3Player backgroundMusicPlayer;
  private MP3Player victorySoundPlayer;
  private MP3Player cardDealtSoundPlayer;

  /**
   * Initializes the mp3 players.
   * 
   * @author Jonas Bauer
   */
  public SoundPlayer() {
    File backgroundMusicFile = new File("resources/music/Peaceful_-_Countryside.mp3");
    File victorySoundFile = null;
    File cardDealtSoundFile = null;
    try {
      backgroundMusicPlayer = new MP3Player(backgroundMusicFile);
      victorySoundPlayer = new MP3Player(victorySoundFile);
      cardDealtSoundPlayer = new MP3Player(cardDealtSoundFile);
    } catch (java.lang.RuntimeException e) {
      System.err.println("SOUND FILES NOT FOUND!");
    }


    backgroundMusicPlayer.setRepeat(true);


  }

  public void playBackgroundMusic() {
    backgroundMusicPlayer.play();
  }

  public void playVictorySound() {
    victorySoundPlayer.play();
  }

  public void playCardDealtSound() {
    cardDealtSoundPlayer.play();
  }

  public void stopBackgroundMusic() {
    backgroundMusicPlayer.stop();
  }

  public static void main(String[] args) {
    SoundPlayer sp = new SoundPlayer();
    sp.playBackgroundMusic();
    while (true);
  }

}
