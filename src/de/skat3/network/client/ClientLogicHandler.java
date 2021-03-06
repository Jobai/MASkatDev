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
import java.util.logging.Logger;


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

  Logger logger = Logger.getLogger("de.skat3.network.client");

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
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void bidInfoHandler(Message m) {
    logger.entering(this.getClass().getName(), "bidInfoHandler(Message m)");
    MessageCommand mc = (MessageCommand) m;
    String message = (String) mc.gameState;
    Player p = mc.originSender;
    SkatMain.mainController.showBid(message, p);

  }


  // tell GUI
  /**
   * Handels bidRequest message and tells the gui to ask the player for a bid.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void bidRequestHandler(Message m) {
    logger.entering(this.getClass().getName(), "bidRequestHandler(Message m)");
    logger.fine("BID REQUEST HANDLED!");
    int b = (int) ((MessageCommand) m).gameState;
    SkatMain.mainController.bidRequest(b);
  }

  // tell GUI
  /**
   * Understands the network message and calls the main controller method to show a card played by
   * another player.
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
  /**
   * Understands the network message and calls the main controller method to let the user play a
   * card.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void playRequestHandler(Message m) {
    SkatMain.mainController.playCardRequest();
  }


  /**
   * Understands the network message and calls the main controller method to update round
   * information. Depending on CommandType, starts the round, shows the round results or updates the
   * player.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void roundInfoHandler(Message m) {
    // Round = All tricks are won, all cards played, the contract is finished.
    logger.finer("AUFGERUFEN - round info handler");
    MessageCommand mc = (MessageCommand) m;

    // Round stated - start hand is set
    if (mc.getSubType() == CommandType.ROUND_START_INFO) {
      logger.fine("roundStarted");
      SkatMain.mainController.roundStarted();
    }

    // Round ended - round results are shown.
    if (mc.getSubType() == CommandType.ROUND_END_INFO) {
      logger.fine("set round result");
      Result result = (Result) mc.payload;
      SkatMain.mainController.showResults(result);
    }

    if (mc.getSubType() == CommandType.ROUND_GENERAL_INFO) {
      logger.finer("update player");
      Player player = (Player) mc.gameState;
      SkatMain.mainController.updatePlayer(player);
    }

  }

  /**
   * Understands the network message and calls the main controller method to show the end game
   * screen with the match results.
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
   * Understands the network message and calls the main controller method to update the player. Is
   * called by the logic on the server side numerous times if changes occur.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void gameInfoHandler(Message m) {
    System.out.println("AUFGERUFEN + Set Starthand");
    MessageCommand mc = (MessageCommand) m;
    Player pl = (Player) mc.gameState;
    System.out.println(pl.getHand());
    SkatMain.mainController.updatePlayer(pl);

  }



  /**
   * Understands the network message and calls the main controller method to show the contract
   * popup.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void contractRequestHandler(Message m) {
    SkatMain.mainController.contractRequest();
  }


  /**
   * Understands the network message and calls the main controller method to show the declarer.
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
   * Understands the network message and calls the main controller method for the handgame question
   * popup.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void handRequestHandler(Message m) {
    SkatMain.mainController.handGameRequest();

  }


  /**
   * Understands the network message and shows the skat selection in the GUI via the main
   * controller.
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
   * Understands the network message and shows a KontraAnnounced message in the gui.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void kontraAnnouncedInfoHandler(Message m) {
    SkatMain.mainController.kontraAnnounced();

  }


  /**
   * Understands the network message and shows a reKontraAnnounced message in the gui.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void reKontraAnnouncedInfoHandler(Message m) {
    SkatMain.mainController.rekontraAnnounced();

  }



  /**
   * Understands the network message and shows a Kontra popup in the GUI.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void kontraShowHandler(Message m) {
    SkatMain.mainController.kontraRequest();
  }


  /**
   * Understands the network message and shows a reKontra popup in the GUI.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void reKontraShowHandler(Message m) {
    SkatMain.mainController.rekontraRequest();

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


  /**
   * Understands the network message and sets the current Dealer in the GUI via the main controller.
   * 
   * @author Jonas Bauer
   * @param m network message
   */
  void setDealerHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    Player dealer = (Player) mc.gameState;
    SkatMain.mainController.setDealer(dealer);
  }


  void trickInfoHandler(Message m) {
    // functionality not used

  }



}
