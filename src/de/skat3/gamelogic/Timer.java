package de.skat3.gamelogic;

public class Timer extends Thread {


  boolean isInterrupted;

  /**
   * Timer that counts down.
   * 
   * @param seconds the time in seconds
   */
  public Timer(int seconds) {
    isInterrupted = false;
    try {
      this.run(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Starts the timer.
   * 
   * @param seconds the time in seconds
   */
  public void run(int seconds) throws InterruptedException {
    int remainingTime = seconds;
    while (!this.isInterrupted) {
      System.out.println(remainingTime);
      Thread.sleep(1000);
      remainingTime--;
      if (remainingTime == 0) {
        // TODO
        this.isInterrupted = true;

      }
    }
  }

  /**
   * Interrupts the thread.
   */


  public static void main(String[] args) {
    Timer t = new Timer(10);

  }
}
