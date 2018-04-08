package de.skat3.gamelogic;

public class AdditionalMulipliers {

  private boolean schneiderAnnounced;
  private boolean schwarzAnnounced;
  private boolean openHand;
  private boolean handGame;

  /**
   * Saves
   * 
   * @param schneiderAnnounced
   * @param schwarzAnnounced
   * @param openHand
   */

  public AdditionalMulipliers(boolean schneiderAnnounced, boolean schwarzAnnounced,
      boolean openHand) {

    this.schneiderAnnounced = schneiderAnnounced;
    this.schwarzAnnounced = schwarzAnnounced;
    this.openHand = openHand;
  }

  public boolean isHandGame() {
    return handGame;
  }

  public void setHandGame(boolean handGame) {
    this.handGame = handGame;
  }

  public boolean isSchneiderAnnounced() {
    return schneiderAnnounced;
  }

  public boolean isSchwarzAnnounced() {
    return schwarzAnnounced;
  }

  public boolean isOpenHand() {
    return openHand;
  }

}
