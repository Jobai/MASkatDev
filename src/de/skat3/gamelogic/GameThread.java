package de.skat3.gamelogic;

/**
 * Runs while a match is played. Calls gamecontroller and roundinstance methods.
 * 
 * @author Kai Baumann
 *
 */
class GameThread extends Thread {

  GameController gc;
  Object lock = new Object();

  public GameThread(GameController gc) {

    this.gc = gc;
  }

  /**
   * Starts a game. Thread is interrupted when the match is over or when the host leaves the game.
   */
  @Override
  public void run() {
    Thread.currentThread().setName("GameThread");
    while (!Thread.currentThread().isInterrupted()) {
      synchronized (lock) {
        gc.numberOfRounds++;
        System.out.println("New Round: " + gc.numberOfRounds);
        gc.startNewRound();
      }
      boolean matchOver = false;
      if (this.gc.mode > 0) {
        if (this.gc.numberOfRounds == this.gc.mode) {
          matchOver = true;
        }
      } else {
        for (Player player : this.gc.allPlayers) {
          if (player.points <= this.gc.mode) {
            matchOver = true;
            break;
          }
        }
      }
      if (matchOver) {
        gc.matchResult.calcWinner();
        this.gc.slc.broadcastMatchResult(new MatchResult(gc.matchResult));
        break;
      } else {
        try {
          Thread.sleep(5000); // XXX
        } catch (InterruptedException e) {
          System.out.println("Logic Thread Interrupted");
          this.interrupt();
        }
      }
    }
  }
}
