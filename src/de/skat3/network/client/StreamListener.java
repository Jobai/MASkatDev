package de.skat3.network.client;

import java.io.EOFException;
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
  boolean stopped;


  StreamListener(GameClient gc) {
    this.gc = gc;
  }


  public void run() {
    Thread.currentThread().setName("GameClientStreamListenerThreat_" + gc.player.getName());
    while (!this.isInterrupted()) {
      try {
        Object o = gc.fromServer.readObject();
        gc.clientProtocolHandler(o);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (ClassCastException e) {
        e.printStackTrace();
      } catch (SocketException e) {
        if (!stopped) {
          gc.logger.log(Level.SEVERE, "Connection to server failed", e);
          gc.handleLostConnection();
          this.interrupt();
        } else {
          gc.logger.info("Exception after closing in Stream Listener: SocketException ");
        }
      } catch (EOFException e) {

        if (!stopped) {
          gc.logger.log(Level.SEVERE, "Connection to server failed", e);
          gc.handleLostConnection();
          this.interrupt();
        } else {
          gc.logger.info("Exception after closing in Stream Listener: EOFException ");
        }

      } catch (IOException e) {

        if (!stopped) {
          gc.logger.log(Level.SEVERE, "Connection to server failed", e);
          gc.handleLostConnection();
          this.interrupt();
        } else {
          gc.logger.info("Exception after closing in Stream Listener: IOException ");
        }


      }
    }
  }

  void closeStreamListener() {
    this.interrupt();
    stopped = true;


  }
}
