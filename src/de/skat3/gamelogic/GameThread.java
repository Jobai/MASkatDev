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
        System.out.println("New Round: " + gc.numberOfRounds);
        gc.startNewRound();
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      if (this.gc.mode > 0) {
        if (this.gc.numberOfRounds == this.gc.mode) {
          //broadcast Final Result TODO
          break;
        }
      } else {
        for (Player player : this.gc.allPlayers) {
          if (player.points <= this.gc.mode) {
            //broadcast Fina Result TODO
            break;
          }
        }
      }
    }
  }

  void notifyGameThread() {
    synchronized (lock) {
      this.lock.notify();
    }
  }
}
