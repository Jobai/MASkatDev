package de.skat3.network.client;

import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageChat;
import de.skat3.network.datatypes.MessageCommand;

public class ClientLogicHandler {
  
  
  GameClient gc;

  /**
   * @author Jonas Bauer
   */
  public ClientLogicHandler(GameClient gc) {
    super();
    this.gc = gc;
    
    
    // TODO Auto-generated constructor stub
  }

  
  //tell GUI
  public  void bidInfoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  //tell GUI
  public void bidRedoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  //tell GUI
  public  void bidRequestHandler(Message m) {
    // TODO Auto-generated method stub
    Player p = (Player) m.payload; 
    int b = (int) ((MessageCommand) m).gameState;
    SkatMain.mainController.bidRequest(b);
    
    
    
  }

  //tell GUI
  public  void playInfoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  //tell GUI
  public  void playRedoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  //tell GUI
  public  void playRequestHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  //tell GUI
  public  void trickInfoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  public  void roundInfoHandler(Message m) {
    // TODO Auto-generated method stub
    System.out.println("AUFGERUFEN");
    MessageCommand mc = (MessageCommand) m;
    SkatMain.mainController.setHand((Player) mc.gameState);
    
  }

  public  void matchInfoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  public  void gameInfoHandler(Message m) {
    // TODO Auto-generated method stub
    System.out.println("AUFGERUFEN");
    MessageCommand mc = (MessageCommand) m;
    SkatMain.lgs.setPlayer((Player) mc.gameState);
    
  }
  
  public  void sendChatMessage(String chatString){
    MessageChat mc = new MessageChat(chatString, "NICK NAME PLACEHOLDER"); //FIXME
    gc.sendToServer(mc);
    
  }
}
