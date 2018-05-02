package de.skat3.network.client;

import java.io.IOException;
import java.util.logging.Level;
import de.skat3.gamelogic.Player;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageCommand;

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

        Message message = (Message) o;


        gc.clientProtocolHandler(o);
        if (((MessageCommand) message).getSubType() == CommandType.ROUND_GENERAL_INFO) {
//          System.out.println("============= StreamListener [ROUND_GENERAL_INFO] ================");
//
//          System.out.println(((MessageCommand) message).gameState);
//          System.out.println(((Player) ((MessageCommand) message).gameState));
//         
//          System.out.println("=========================================");
        }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (ClassCastException e) {
        //
      } catch (IOException e) {
        gc.logger.log(Level.SEVERE, "Connection to server failed", e);
        gc.handleLostConnection();
        this.interrupt();
      }
    }
  }
}
