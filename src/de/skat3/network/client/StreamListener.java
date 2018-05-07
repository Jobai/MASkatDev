package de.skat3.network.client;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;

/**
 * Helperclass for the GameClient to listen for incoming messages from the server.
 * 
 * @author Jonas Bauer
 *
 */
class StreamListener extends Thread {

  GameClient gc;


  StreamListener(GameClient gc) {
    this.gc = gc;
  }


  public void run() {
    while (!this.isInterrupted()) {
      try {
        Object o = gc.fromServer.readObject();
        gc.clientProtocolHandler(o);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (ClassCastException e) {
        e.printStackTrace();
      } catch (SocketException e) {
        gc.logger.log(Level.SEVERE, "Connection to server failed", e);
        gc.handleLostConnection();
        this.interrupt();

      } catch (IOException e) {
        gc.logger.log(Level.SEVERE, "Connection to server failed", e);
        gc.handleLostConnection();
        this.interrupt();

      }
    }
  }
}
