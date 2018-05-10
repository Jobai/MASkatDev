package de.skat3.main;

import javafx.application.Platform;

public class PlayTimeTimer extends Thread {

  long playTime;
  boolean isInterrupted;

  /**
   * Increments the play time every minute.
   * 
   * @param playTime the playtime saved in the userprofile.
   */
  public PlayTimeTimer(long playTime) {
    this.playTime = playTime;
    this.isInterrupted = false;
    this.start();
  }

  /**
   * Starts the thread to increment play time.
   */
  public void run() {
    Thread.currentThread().setName("PlayTimeTimerThread");
    while (!this.isInterrupted) {
      try {
        this.playTime++;
        Thread.sleep(1000);
        if (this.playTime % 60 == 0) {
          SkatMain.ioController.getLastUsedProfile().setPlayerGameTime(this.playTime);
          SkatMain.ioController.updateLastUsed(SkatMain.ioController.getLastUsedProfile());

          Platform.runLater(new Runnable() {

            @Override
            public void run() {
              SkatMain.guiController.refreshStatistik();
            }
          });


        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void interrupt() {
    this.isInterrupted = true;
  }
}
