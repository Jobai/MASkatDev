/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 06.05.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package de.skat3.io;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;

/**
 * Controls the volume of the java application.
 * 
 * @author Jonas Bauer
 *
 */
public class SoundVolumeUtil {



  /**
   * Sets the volume for the complete java application <b> on all possible soundOutputs </b>.
   * 
   * @author Jonas Bauer
   * @param volumeLevel volume level from 0.0F to 1.0F
   */
  public static void setVolume(float volumeLevel) {
    Info soundSystem = Port.Info.SPEAKER;
    setJavaVolume(soundSystem, volumeLevel);

    soundSystem = Port.Info.LINE_OUT;
    setJavaVolume(soundSystem, volumeLevel);

    soundSystem = Port.Info.HEADPHONE;
    setJavaVolume(soundSystem, volumeLevel);

  }

  /**
   * Utility method that uses the system mixer to sets the volume for the java application.
   * 
   * @author Jonas Bauer
   * @param soundSystem soundOutput to set the volume for (Port.Info.SPEAKER, Port.Info.LINE_OUT or
   *        Port.Info.HEADPHONE).
   * @param volume volume level from 0.0F to 1.0F
   */
  private static void setJavaVolume(Info soundSystem, float volume) {

    if (AudioSystem.isLineSupported(soundSystem)) {
      // checks if the line / soundOutput is present on this computer.
      try {
        Port soundPort = (Port) AudioSystem.getLine(soundSystem);
        soundPort.open();
        FloatControl volumeFloatControler =
            (FloatControl) soundPort.getControl(FloatControl.Type.VOLUME);
        volumeFloatControler.setValue(volume);
      } catch (LineUnavailableException e) {
        e.printStackTrace();
      }

    } else {
      // silent drop ok - soundPort just not available on this machine.
    }



  }

}
