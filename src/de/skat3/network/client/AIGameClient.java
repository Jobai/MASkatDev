/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0
 * 30.04.2018
 * 
 * (c) 2018 All Rights Reserved. 
 * -------------------------
 */
package de.skat3.network.client;

import de.skat3.gamelogic.Player;

/**
 * @author Jonas Bauer
 *
 */
public class AIGameClient extends GameClient {
  
  AIClientLogicHandler aiCLH;

  /**
   * @author Jonas Bauer
   * @param hostAdress
   * @param port
   * @param player
   */
  public AIGameClient(String hostAdress, int port, Player player) {
    super(hostAdress, port, player);
    aiCLH = new AIClientLogicHandler(this, this.player);
    // TODO Auto-generated constructor stub
  }

  /**
   * @author Jonas Bauer
   * @param hostAdress
   * @param port
   * @param player
   * @param lobbyPassword
   */
  public AIGameClient(String hostAdress, int port, Player player, String lobbyPassword) {
    super(hostAdress, port, player, lobbyPassword);
    // TODO Auto-generated constructor stub
  }

}
