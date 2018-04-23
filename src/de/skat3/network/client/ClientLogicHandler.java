package de.skat3.network.client;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.MatchResult;
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
  }


  // tell GUI
  public void bidInfoHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    boolean accept = (boolean) mc.gameState;
    SkatMain.mainController.localBid(accept);
  }

  // tell GUI
  public void bidRedoHandler(Message m) {
    Player p = (Player) m.payload;
    int b = (int) ((MessageCommand) m).gameState;
    SkatMain.mainController.bidRequest(b);

  }

  // tell GUI
  public void bidRequestHandler(Message m) {
    Player p = (Player) m.payload;
    int b = (int) ((MessageCommand) m).gameState;
    SkatMain.mainController.bidRequest(b);



  }

  // tell GUI
  public void playInfoHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    Card c = (Card) mc.gameState;
    Player op = (Player) mc.originSender;
    SkatMain.mainController.showCardPlayed(op, c);

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
    MessageCommand mc = (MessageCommand) m;
    Player trickWinner = (Player) mc.gameState;
//    SkatMain.mainController.

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
    MessageCommand mc = (MessageCommand) m;
    MatchResult mr = (MatchResult) mc.gameState;
    SkatMain.mainController.showEndScreen(mr);

  }

  public void gameInfoHandler(Message m) {
    // TODO Auto-generated method stub
    System.out.println("AUFGERUFEN + Set Starthand");
    MessageCommand mc = (MessageCommand) m;
    Player pl = (Player) mc.gameState;
//    SkatMain.lgs.setPlayer((Player) mc.gameState); // FIXME ?
//    System.out.println(pl);
    System.out.println(pl.getHand());
    SkatMain.mainController.setHand(pl);

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


  public void kontraAnnouncedInfoHandler(Message m) {
    SkatMain.mainController.kontraAnnounced();

  }


  public void reKontraAnnouncedInfoHandler(Message m) {
    SkatMain.mainController.rekontraAnnounced();

  }



  public void KontraShowHandler(Message m) {
    SkatMain.mainController.kontraRequest();
  }


  public void reKontraShowHandler(Message m) {
    SkatMain.mainController.rekontraRequest();

  }


  public void KontraHideHandler(Message m) {
    // TODO Auto-generated method stub

  }


  public void reKontraHideHandler(Message m) {
    // TODO Auto-generated method stub

  }


  public void roundRestartHandler(Message m) {
    // TODO Auto-generated method stub


  }
}
