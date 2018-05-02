package de.skat3.gamelogic;

import java.util.ArrayList;
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
      SkatMain.guiController.getInGameController().setRemainingTime(remainingTime);
      Thread.sleep(1000);
      remainingTime--;
      if (remainingTime == 0) {
        Random rand = new Random();
        int i;
        ArrayList<Card> temp = new ArrayList<Card>();
        for (Card c : SkatMain.lgs.getLocalHand().cards) {
          if (c.isPlayable()) {
            temp.add(c.copy());
          }
        }
        i = rand.nextInt(temp.size());

        SkatMain.guiController.getInGameController().makeAMove(false);
        SkatMain.mainController.localCardPlayed(temp.get(i));
        System.out.println("TIMER ACTIVATED");
        this.isInterrupted = true;
      }
    }
  }
}



/**
 * Interrupts the thread.
 */


