package de.skat3.gamelogic;

public class GameThread extends Thread {

  GameController gc;
  Object lock = new Object();

  public GameThread(GameController gc) {

    this.gc = gc;
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      synchronized (lock) {
        gc.numberOfRounds++;
        System.out.println("New Round: "+gc.numberOfRounds);
        gc.startNewRound();
        try {
          lock.wait();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }


}
