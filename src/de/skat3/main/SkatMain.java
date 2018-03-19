package de.skat3.main;

import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Player;
import de.skat3.network.client.GameClient;
import de.skat3.network.server.GameServer;

public class SkatMain {


  public static void main(String[] args) {

    GameServer gm = new GameServer();
    GameClient gc = new GameClient("localhost", 2018);
    
    
    Player kai = new Player();
    Player bot1 = new Player();
    Player bot2 = new Player();
    Player[] players = {kai,bot1,bot2};
    
    GameController gameController =
        new GameController(gm.getSeverLogicController(),players, 0, false);
    
  }
}
