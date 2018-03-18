package de.skat3.network.client;

import java.io.IOException;
import java.util.logging.Level;

public class StreamListener extends Thread {
  
  GameClient gc;

  /**
   * @author Jonas Bauer
   * @param gc
   */
  public StreamListener(GameClient gc) {
    this.gc = gc;
  }

  
  public void run ()
  {
    while (!this.isInterrupted())
    {
      try {
        Object o = gc.fromServer.readObject();
        gc.clientProtocolHandler(o);
        
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        gc.logger.log(Level.SEVERE, "Connection to server failed", e);
        gc.handleLostConnection();
        this.interrupt();
      }
    }
  }
}
