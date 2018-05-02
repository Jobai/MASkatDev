package de.skat3.gamelogic;

class GameThread extends Thread {

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
      }
      if (this.gc.mode > 0) {
        if (this.gc.numberOfRounds == this.gc.mode) {
          this.gc.slc.broadcastMatchResult(gc.matchResult);
          break;
        } else {
          try {
            Thread.sleep(5000); // XXX
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      } else {
        boolean matchOver = false;
        for (Player player : this.gc.allPlayers) {
          if (player.points <= this.gc.mode) {
            matchOver = true;
            break;
          }
        }
        if (matchOver) {
          this.gc.slc.broadcastMatchResult(gc.matchResult);
          break;
        } else {
          try {
            Thread.sleep(5000); //XXX
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}
