package de.skat3.main;

import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Player;
import de.skat3.network.client.GameClient;
import de.skat3.network.server.GameServer;

public class SkatMain {

  public static LocalGameState lgs = new LocalGameState();

  public static void main(String[] args) {

    GameServer gm = new GameServer();
    Player kai = new Player();
    Player bot1 = new Player();
    Player bot2 = new Player();
    GameClient gcKai = new GameClient("localhost", 2018,kai);
    GameClient gcBot1 = new GameClient("localhost", 2018,bot1);
    GameClient gcBot2 = new GameClient("localhost", 2018,bot2);
    

    Player[] players = {kai,bot1,bot2};
    
    
    GameController gameController =
        new GameController(gm.getSeverLogicController(),players, 0, false);
    
    
    
  }
}
