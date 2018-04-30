package de.skat3.network.client;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.main.SkatMain;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageChat;
import de.skat3.network.datatypes.MessageCommand;

/**
 * Handles messages (Information or Commands) from the network and acts on their content. Calls
 * manly methods from the MainController to inform the GUI of changes in the GameState or a Request
 * to do something. <br>
 * ClientNetwork > this Class > MainController > GUI. IMPLEMENTs [UNDERSTAND] COMMANDTYPEs.
 * 
 * @author Jonas
 *
 * 
 */
public class ClientLogicHandler {


  GameClient gc;

  /**
   * Default constructor. Sets the needed GameClient.
   * 
   * @author Jonas Bauer
   */
  public ClientLogicHandler(GameClient gc) {
    super();
    this.gc = gc;
  }


  // tell GUI
  void bidInfoHandler(Message m) {

    System.out.println("BID INFO HANDELD");

    MessageCommand mc = (MessageCommand) m;
    boolean accept = (boolean) mc.gameState;
    //TODO 
    // SkatMain.mainController. FIXME
  }

  // tell GUI
  @Deprecated
  void bidRedoHandler(Message m) {
    //Player p = (Player) m.payload;
    int b = (int) ((MessageCommand) m).gameState;
    SkatMain.mainController.bidRequest(b);

  }

  // tell GUI
  void bidRequestHandler(Message m) {

    System.out.println("BID REQUEST HANDELD");
    int b = (int) ((MessageCommand) m).gameState;
    SkatMain.mainController.bidRequest(b);



  }

  // tell GUI
  void playInfoHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    Card c = (Card) mc.gameState;
    Player op = (Player) mc.originSender;
    SkatMain.mainController.showCardPlayed(op, c);

  }

  // tell GUI
  @Deprecated
  void playRedoHandler(Message m) {
    // TODO Auto-generated method stub

  }

  // tell GUI
  void playRequestHandler(Message m) {
    SkatMain.mainController.playCardRequest();
  }

  // tell GUI
  void trickInfoHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    Player trickWinner = (Player) mc.gameState;
    // SkatMain.mainController.
    //TODO

  }

  //Round = All tricks are won, all cards played, the contract is finished.
  void roundInfoHandler(Message m) {
    System.out.println("AUFGERUFEN - round info handler");
    MessageCommand mc = (MessageCommand) m;

    //Round stated - start hand is set
    if (mc.getSubType() == CommandType.ROUND_START_INFO) {
      System.out.println("set hand");
      SkatMain.mainController.updatePlayer((Player) mc.gameState);
    }
    
    //Round ended - round results are shown. 
    if (mc.getSubType() == CommandType.ROUND_END_INFO) {
      System.out.println("set round result");

      Result result = (Result) mc.payload;
      SkatMain.mainController.showResults(result);
      // FIXME
    }

  }

  void matchInfoHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    MatchResult mr = (MatchResult) mc.gameState;
    SkatMain.mainController.showEndScreen(mr);

  }

  void gameInfoHandler(Message m) {
    // TODO Auto-generated method stub
    System.out.println("AUFGERUFEN + Set Starthand");
    MessageCommand mc = (MessageCommand) m;
    Player pl = (Player) mc.gameState;
    // SkatMain.lgs.setPlayer((Player) mc.gameState); // FIXME ?
    // System.out.println(pl);


    System.out.println(pl.getHand());
    SkatMain.mainController.updatePlayer(pl);

  }

  void sendChatMessage(String chatString) {
    MessageChat mc = new MessageChat(chatString, "NICK NAME PLACEHOLDER"); // FIXME
    gc.sendToServer(mc);

  }


  void contractRequestHandler(Message m) {


    SkatMain.mainController.contractRequest();

  }


  void declarerInfoHander(Message m) {

    MessageCommand mc = (MessageCommand) m;
    Player p = (Player) mc.gameState;
    SkatMain.mainController.showAuctionWinner(p);
  }


  void handRequestHandler(Message m) {

    SkatMain.mainController.handGameRequest();

  }


  void skatRequestHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    Card[] skat = (Card[]) mc.gameState;
    SkatMain.mainController.selectSkatRequest(skat);

  }


  void kontraAnnouncedInfoHandler(Message m) {
    SkatMain.mainController.kontraAnnounced();

  }


  void reKontraAnnouncedInfoHandler(Message m) {
    SkatMain.mainController.rekontraAnnounced();

  }



  void KontraShowHandler(Message m) {
    SkatMain.mainController.kontraRequest();
  }


  void reKontraShowHandler(Message m) {
    SkatMain.mainController.rekontraRequest();

  }


  void KontraHideHandler(Message m) {
    // TODO Auto-generated method stub

  }


  void reKontraHideHandler(Message m) {
    // TODO Auto-generated method stub

  }


  void roundRestartHandler(Message m) {
    // TODO Auto-generated method stub


  }


  void contractInfoHandler(Message m) {
    // TODO Auto-generated method stub

    MessageCommand mc = (MessageCommand) m;
    Contract c = (Contract) mc.payload;
    AdditionalMultipliers am = (AdditionalMultipliers) mc.secondPayload;
    SkatMain.mainController.showContract(c, am);

  }
}
