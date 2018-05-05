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
  /**
   * Handels bidInfoMessages and tells the GUI to display the current bid of another player.
   * @author Jonas Bauer
   * @param m network message
   */
  void bidInfoHandler(Message m) {

    System.out.println("BID INFO HANDELD");

    MessageCommand mc = (MessageCommand) m;
    String message = (String) mc.gameState;
    Player p = mc.originSender;
    SkatMain.mainController.showBid(message, p);

  }

  // tell GUI
  @Deprecated
  void bidRedoHandler(Message m) {
    // Player p = (Player) m.payload;
    int b = (int) ((MessageCommand) m).gameState;
    SkatMain.mainController.bidRequest(b);

  }

  // tell GUI
  /**
   * Handels bidRequest message and tells the gui to ask the player for a bid.
   * @author Jonas Bauer
   * @param m network message
   */
  void bidRequestHandler(Message m) {

    System.out.println("BID REQUEST HANDELD");
    int b = (int) ((MessageCommand) m).gameState;
    SkatMain.mainController.bidRequest(b);



  }

  // tell GUI
  /**
   * 
   * @author Jonas Bauer
   * @param m network message
   */
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
  /**
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void playRequestHandler(Message m) {
    SkatMain.mainController.playCardRequest();
  }

  // tell GUI
  /**
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void trickInfoHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    Player trickWinner = (Player) mc.gameState;
    // SkatMain.mainController.
    // TODO

  }

  // Round = All tricks are won, all cards played, the contract is finished.
  /**
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void roundInfoHandler(Message m) {
    System.out.println("AUFGERUFEN - round info handler");
    MessageCommand mc = (MessageCommand) m;

    // Round stated - start hand is set
    if (mc.getSubType() == CommandType.ROUND_START_INFO) {
      System.out.println("roundStarted");

      SkatMain.mainController.roundStarted();
    }

    // Round ended - round results are shown.
    if (mc.getSubType() == CommandType.ROUND_END_INFO) {
      System.out.println("set round result");

      Result result = (Result) mc.payload;
      SkatMain.mainController.showResults(result);
      // FIXME
    }

    if (mc.getSubType() == CommandType.ROUND_GENERAL_INFO) {
      Player player = (Player) mc.gameState;
      // System.out.println("update player");
      // System.out.println("CLIENT RECEIVED" + player.getName() + " CARDS: "
      // + player.getHand());

      SkatMain.mainController.updatePlayer(player);
    }

  }

  /**
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void matchInfoHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    MatchResult mr = (MatchResult) mc.gameState;
    SkatMain.mainController.showEndScreen(mr);

  }

  
  /**
   * 
   * @author Jonas Bauer
   * @param m network message
   */
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

 


  /**
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void contractRequestHandler(Message m) {


    SkatMain.mainController.contractRequest();

  }


  /**
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void declarerInfoHander(Message m) {

    MessageCommand mc = (MessageCommand) m;
    Player p = (Player) mc.gameState;
    SkatMain.mainController.showAuctionWinner(p);
  }


  /**
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void handRequestHandler(Message m) {

    SkatMain.mainController.handGameRequest();

  }


  /**
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void skatRequestHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    Card[] skat = (Card[]) mc.gameState;
    SkatMain.mainController.selectSkatRequest(skat);

  }


  /**
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void kontraAnnouncedInfoHandler(Message m) {
    SkatMain.mainController.kontraAnnounced();

  }


  /**
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void reKontraAnnouncedInfoHandler(Message m) {
    SkatMain.mainController.rekontraAnnounced();

  }



  /**
   * Show a Kontra popup in the GUI.
   * @author Jonas Bauer
   * @param m network message
   */
  void kontraShowHandler(Message m) {
    SkatMain.mainController.kontraRequest();
  }


  /**
   * Show a reKontra popup in the GUI.
   * @author Jonas Bauer
   * @param m network message
   */
  void reKontraShowHandler(Message m) {
    SkatMain.mainController.rekontraRequest();

  }


  void roundRestartHandler(Message m) {
    // TODO Auto-generated method stub


  }


  /**
   * Understands the network message and sets the chosen contract in the GUI.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void contractInfoHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    Contract c = (Contract) mc.payload;
    AdditionalMultipliers am = (AdditionalMultipliers) mc.secondPayload;
    SkatMain.mainController.showContract(c, am);

  }


  /**
   * Understands the network message and Sets the info in the GUI which players are in a team.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void updateEnemyInfoHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    Player transmitedPlayer = (Player) mc.gameState;
    if (!(transmitedPlayer.equals(SkatMain.lgs.getLocalClient()))) {
      SkatMain.mainController.updateEnemy(transmitedPlayer);
    }
  }

  /**
   * Understands the network message and Calls the GUI and orders to user to play a specified card.
   * Used in tutorials an scenarios.
   * 
   * @author Jonas Bauer
   * @param m network message with the card.
   */
  void specificPlayHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    Card c = (Card) mc.gameState;
    SkatMain.mainController.playSpecificCardRequest(c);
  }


  void setDealerHandler(Message m) {
    // TODO Auto-generated method stub
    MessageCommand mc = (MessageCommand) m;
    Player dealer = (Player) mc.gameState;
    SkatMain.mainController.setDealer(dealer);
  }



}
