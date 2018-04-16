package de.skat3.gamelogic;

import java.io.Serializable;

public class AdditionalMultipliers implements Serializable {

  private boolean schneiderAnnounced;
  private boolean schwarzAnnounced;
  private boolean openHand;
  private boolean handGame;

  /**
   * Saves the selected modifiers.
   * 
   */

  public AdditionalMultipliers(boolean schneiderAnnounced, boolean schwarzAnnounced,
      boolean openHand) {

    this.schneiderAnnounced = schneiderAnnounced;
    this.schwarzAnnounced = schwarzAnnounced;
    this.openHand = openHand;
  }

  /**
   * All modifiers will be set false.
   */
  public AdditionalMultipliers() {
    this.schneiderAnnounced = false;
    this.schwarzAnnounced = false;
    this.openHand = false;
    this.handGame = false;

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
