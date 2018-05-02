package de.skat3.gamelogic;

import java.util.Random;
import de.skat3.main.SkatMain;

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
        Random rand = new Random();
        int i;
        while (true) {
          i = rand.nextInt(SkatMain.lgs.getLocalClient().hand.cards.length);
          if (SkatMain.lgs.getLocalClient().hand.cards[i].isPlayable()) {
            System.out.println(SkatMain.lgs.getLocalClient().hand.cards[i]);
            break;
          }
        }
        SkatMain.guiController.getInGameController().makeAMove(false);
        SkatMain.mainController.localCardPlayed(SkatMain.lgs.getLocalClient().hand.cards[i]);
        System.out.println("TIMER ACTIVATED");
        this.isInterrupted = true;

      }
    }
  }
}

/**
 * Interrupts the thread.
 */


