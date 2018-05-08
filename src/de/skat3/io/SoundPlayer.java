package de.skat3.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;


/**
 * Utility class that can play sounds an background music.
 * 
 * @author Artem Zamarajev, Jonas Bauer
 *
 */

public class SoundPlayer {

  private AdvancedPlayer backgroundMusicPlayer;
  private AdvancedPlayer victorySoundPlayer;
  private AdvancedPlayer cardDealtSoundPlayer;
  private boolean backgroundMusic = false;
  private InputStream backgroundMusicStream;


  public void playBackgroundMusic() {
    backgroundMusic = true;

    new Thread() {
      public void run() {
        try {
          while (backgroundMusic) {
            backgroundMusicStream =
                SoundPlayer.class.getResourceAsStream("/music" + "/Peaceful_-_Countryside.mp3");
            System.out.println(backgroundMusicStream);
            backgroundMusicPlayer = new AdvancedPlayer(backgroundMusicStream);

            backgroundMusicPlayer.play();

            // In case the player was stopped meanwhile
            synchronized (this) {
              if (backgroundMusicPlayer != null) {
                backgroundMusicPlayer.close();
                backgroundMusicPlayer = null;
              }
            }
          }
        } catch (JavaLayerException jle) {
          jle.printStackTrace();
        }
      }
    }.start();
  }

  public void playVictorySound() {
    try {
      InputStream victorySoundStream = SoundPlayer.class.getClassLoader()
          .getResourceAsStream(File.separator + "music" + File.separator + "winSound.mp3");
      victorySoundPlayer = new AdvancedPlayer(victorySoundStream);
      victorySoundPlayer.play();
      victorySoundPlayer.stop();
      victorySoundStream.close();
    } catch (JavaLayerException | IOException e) {
      e.printStackTrace();
    }
  }

  public void playCardDealtSound() {
    try {
      InputStream dealtCardStream = SoundPlayer.class.getClassLoader()
          .getResourceAsStream(File.separator + "music" + File.separator + "dealtCard.mp3");
      cardDealtSoundPlayer = new AdvancedPlayer(dealtCardStream);
      cardDealtSoundPlayer.play();
      cardDealtSoundPlayer.stop();
      dealtCardStream.close();
    } catch (JavaLayerException | IOException e) {
      e.printStackTrace();
    }
  }

  public synchronized void stopBackgroundMusic() {
    if (backgroundMusic) {
      if (backgroundMusicPlayer != null) {
        backgroundMusicPlayer.close();
        backgroundMusicPlayer = null;
      }
      backgroundMusic = false;
    }
  }


}
