package de.skat3.main;

public class PlayTimeTimer extends Thread {

  long playTime;
  boolean isInterrupted;

  public PlayTimeTimer(long playTime) {
    this.playTime = playTime; // FIXME ??
    this.isInterrupted = false;
    this.start();
  }

  /**
   * 
   */
  public void run() {
    while (!this.isInterrupted) {
      try {
        this.playTime++;
        Thread.sleep(1000);
        if (this.playTime % 60 == 0) {
          SkatMain.ioController.getLastUsedProfile().setPlayerGameTime(this.playTime);
          SkatMain.ioController.updateLastUsed(SkatMain.ioController.getLastUsedProfile());
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
