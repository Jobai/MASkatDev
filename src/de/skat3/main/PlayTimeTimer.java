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
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void interrupt() {
    this.isInterrupted = true;
  }
}
