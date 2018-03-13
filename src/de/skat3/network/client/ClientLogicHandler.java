package de.skat3.network.client;

import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageChat;

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

  public  void bidInfoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  public void bidRedoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  public  void bidRequestHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  public  void playInfoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  public  void playRedoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  public  void playRequestHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  public  void trickInfoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  public  void roundInfoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  public  void matchInfoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }

  public  void gameInfoHandler(Message m) {
    // TODO Auto-generated method stub
    
  }
  
  public  void sendChatMessage(String chatString){
    MessageChat mc = new MessageChat(chatString, "NICK NAME PLACEHOLDER"); //FIXME
    gc.sendToServer(mc);
    
  }
}
