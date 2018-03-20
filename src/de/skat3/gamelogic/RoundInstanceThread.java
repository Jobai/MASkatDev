package de.skat3.gamelogic;

public class RoundInstanceThread extends Thread {

  RoundInstance roundInstance;

  public RoundInstanceThread(RoundInstance roundInstance) {
    this.roundInstance = roundInstance;
  }

  @Override
  public void run() {
    // Bidding

    roundInstance.initializeAuction();
    try {
      Player winner = roundInstance.startBidding();
      roundInstance.setDeclarer(winner);
//      // TODO KONTRA AND REKONTRA
//      roundInstance.lock.wait(); // notified by notifiyLogicofKontra


      // Game
      roundInstance.startGame();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }



  }
}
