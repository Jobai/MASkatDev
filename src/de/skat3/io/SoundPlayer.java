package de.skat3.io;

import jaco.mp3.player.MP3Player;
import java.net.URL;

/**
 * Utility class that can play sounds an background music.
 * @author Jonas Bauer
 *
 */
public class SoundPlayer {

  private MP3Player backgroundMusicPlayer;
  private MP3Player victorySoundPlayer;
  private MP3Player cardDealtSoundPlayer;

  /**
   * Initializes the mp3 players.
   * @author Jonas Bauer
   */
  public SoundPlayer() {
    URL backgroundMusicUrl = null;
    URL victorySoundUrl = null;
    URL cardDealtSoundUrl = null;

    backgroundMusicPlayer = new MP3Player(backgroundMusicUrl);
    victorySoundPlayer = new MP3Player(victorySoundUrl);
    cardDealtSoundPlayer = new MP3Player(cardDealtSoundUrl);

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

}
