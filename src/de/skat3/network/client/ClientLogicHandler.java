package de.skat3.network.client;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.main.SkatMain;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageChat;
import de.skat3.network.datatypes.MessageCommand;
import de.skat3.network.datatypes.SubType;

/**
 * ClientNetwork > this Class > MainController > GUI
 * 
 * @author Jonas
 *
 *         I IMPLEMENT [UNDERSTAND] COMMANDTYPEs
 */
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


  // tell GUI
  public void bidInfoHandler(Message m) {
    // TODO Auto-generated method stub

  }

  // tell GUI
  public void bidRedoHandler(Message m) {
    // TODO Auto-generated method stub
    Player p = (Player) m.payload;
    int b = (int) ((MessageCommand) m).gameState;
    SkatMain.mainController.bidRequest(b);

  }

  // tell GUI
  public void bidRequestHandler(Message m) {
    // TODO Auto-generated method stub
    Player p = (Player) m.payload;
    int b = (int) ((MessageCommand) m).gameState;
    SkatMain.mainController.bidRequest(b);



  }

  // tell GUI
  public void playInfoHandler(Message m) {
    // TODO Auto-generated method stub
    MessageCommand mc = (MessageCommand) m;
    Card c = (Card) mc.gameState;
    SkatMain.mainController.playCard(null, c); //FIXME

  }

  // tell GUI
  public void playRedoHandler(Message m) {
    // TODO Auto-generated method stub

  }

  // tell GUI
  public void playRequestHandler(Message m) {
    SkatMain.mainController.playCardRequest();
  }

  // tell GUI
  public void trickInfoHandler(Message m) {
    // TODO Auto-generated method stub

  }

  public void roundInfoHandler(Message m) {
    // TODO Auto-generated method stub


    System.out.println("AUFGERUFEN");
    MessageCommand mc = (MessageCommand) m;

    if (mc.getSubType() == CommandType.ROUND_START_INFO) {
      SkatMain.mainController.setHand((Player) mc.gameState);
    }
    if (mc.getSubType() == CommandType.ROUND_END_INFO) {
      
      Result result = (Result) mc.payload;
      SkatMain.mainController.showResults(result);
      // FIXME
    }

  }

  public void matchInfoHandler(Message m) {
    // TODO Auto-generated method stub

  }

  public void gameInfoHandler(Message m) {
    // TODO Auto-generated method stub
    System.out.println("AUFGERUFEN");
    MessageCommand mc = (MessageCommand) m;
    SkatMain.lgs.setPlayer((Player) mc.gameState); // FIXME ?

  }

  public void sendChatMessage(String chatString) {
    MessageChat mc = new MessageChat(chatString, "NICK NAME PLACEHOLDER"); // FIXME
    gc.sendToServer(mc);

  }


  public void contractRequestHandler(Message m) {


    SkatMain.mainController.contractRequest();

  }


  public void declarerInfoHander(Message m) {

    MessageCommand mc = (MessageCommand) m;
    Player p = (Player) mc.gameState;
    SkatMain.mainController.showAuctionWinner(p);
  }


  public void handRequestHandler(Message m) {

    SkatMain.mainController.handGameRequest();

  }


  public void skatRequestHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    Card[] skat = (Card[]) mc.gameState;
    SkatMain.mainController.setSkat(skat);

  }
}
