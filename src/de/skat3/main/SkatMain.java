package de.skat3.main;

import de.skat3.network.client.GameClient;
import de.skat3.network.server.GameServer;

public class SkatMain {

  
  public static void main(String[] args) {
    
    GameServer gm = new GameServer();
    GameClient gc = new GameClient("localhost", 2018);
    }
}
