/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0
 * 13.03.2018
 * 
 * (c) 2018 All Rights Reserved. 
 * -------------------------
 */
package de.skat3.network;

import de.skat3.gamelogic.Card;

/**
 * @author Jonas Bauer
 * Methodes that are called by the GUI during the Matchphase
 */
public interface ClientNetworkInterface {
  
  public void localCardPlayed(Card c);
  
  public void localBid(boolean acceptBid);
  
  public void sendChatMessage(String chatMessage);
  
  public void leaveMatchByForce();

}
