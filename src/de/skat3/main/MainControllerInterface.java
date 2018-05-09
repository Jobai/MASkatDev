package de.skat3.main;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Interface for gui-network, local logic-network and io-gui.
 * 
 * @author kai29
 *
 */
public interface MainControllerInterface {


  /**
   * Returns a list of all lobbies in the local network.
   */
  public ArrayList<Lobby> getLocalHosts();


  /**
   * Starts a singleplayer game with two bots.
   * 
   * @param hardBot false if first ai is easy.
   * @param hardBot2 false if second ai is easy.
   * @param kontraRekontraEnabled true if the kontra/rekontra feature is enabled.
   * @param scoringMode Mode is either Seeger (positive number divisible by 3) or Bierlachs
   *        (negative number between -500 and -1000)
   */

  public void startSingleplayerGame(boolean hardBot, boolean hardBot2, int scoringMode,
      boolean kontraRekontraEnabled);


  /**
   * Starts a specific scenario in training mode.
   * 
   * @param scenario 0 = basic guide, 1-5 = scenarios.
   */
  void startTrainingMode(int scenario);

  /**
   * Joins the specified lobby.
   */

  public void joinMultiplayerGame(Lobby lobby);

  /**
   * Joins the specified lobby.
   * 
   */
  public void joinMultiplayerGame(Lobby lobby, String password);

  /**
   * Creates a lobby for a multiplayer game. s
   * 
   * @param timer 0 = no timer, >0 timer in seconds
   * @param kontraRekontraEnabled true if feature is enabled
   * @param scoringMode Mode is either Seeger (positive number divisible by 3) or Bierlachs
   *        (negative number between -500 and -1000)
   */
  public void hostMultiplayerGame(String name, String password, int numberOfPlayers, int timer,
      boolean kontraRekontraEnabled, int scoringMode) throws UnknownHostException;


  /**
   * Creates a lobby for a multiplayer game.
   * 
   * @param timer 0 = no timer, >0 timer in seconds
   * @param kontraRekontraEnabled true if feature is enabled
   * @param scoringMode Mode is either Seeger (positive number divisible by 3) or Bierlachs
   *        (negative number between -500 and -1000)
   */

  public void hostMultiplayerGame(String name, int numberOfPlayers, int timer,
      boolean kontraRekontraEnabled, int scoringMode) throws UnknownHostException;


  /**
   * Called by host client to start the game.
   * 
   */
  public void startGame();


  /**
   * Tells the gui to go into ingame view.
   */
  public void roundStarted();

  /**
   * The specified player plays the specified card in the gui and adds it to the local trick.
   * 
   */
  public void showCardPlayed(Player player, Card card);


  /**
   * Local client sends chat message to the server.
   * 
   */
  public void sendMessage(String message);

  /**
   * Adds a chat message to the chat in the gui.
   * 
   */
  public void receiveMessage(String message);

  /**
   * Local client leaves the match and returns to the Multiplayer menu.
   */
  public void exitGame();


  /**
   * Starts the skat selection in the gui.
   */
  public void selectSkatRequest(Card[] skat);

  /**
   * Asks the localClient to play a card from his hand.
   */
  public void playCardRequest();

  /**
   * Asks the localClient to play a specific card from his hand.
   */
  public void playSpecificCardRequest(Card card);

  /**
   * Asks the localClient to choose a contract.
   */
  public void contractRequest();

  /**
   * Asks the localClient to accept or decline a handgame.
   */
  public void handGameRequest();

  /**
   * Displays the auction winner in the gui.
   */
  public void showAuctionWinner(Player player);

  /**
   * Asks the localClient to accept or decline the specified bid.
   * 
   */
  public void bidRequest(int bid);

  /**
   * Updates the local player in the local gamestate.
   */

  /**
   * Forces a bid in tutorial mode.
   */
  public void tutorialBidRequest(int bid, boolean shouldAccept);

  public void updatePlayer(Player player);

  /**
   * Updates an enemy in the local gamestate.
   */
  public void updateEnemy(Player transmitedPlayer);

  /**
   * Displays the specified contract in the gui.
   */
  public void showContract(Contract contract, AdditionalMultipliers additionalMultipliers);

  /**
   * Shows the result in the gui and adds the result to the local statistics.
   */
  public void showResults(Result result);

  /**
   * Shows the match result in the gui.
   */
  public void showEndScreen(MatchResult matchResult);

  /**
   * Called by gui to send a played card to the server.
   */
  public void localCardPlayed(Card card);

  /**
   * Called by gui to send an accepted or declined bid to the server.
   */
  public void localBid(boolean accepted);


  public void showBid(String bid, Player player);

  /**
   * Called by gui to send the locally selected contract to the server.
   */
  public void contractSelected(Contract contract, AdditionalMultipliers additionalMultipliers);

  /**
   * Called when the local client chose to accept or decline a handgame.
   * 
   * @param accepted true if the local client plays a handgame.
   */
  public void handGameSelected(boolean accepted);



  /**
   * Sends the new hand and skat to the logic.
   */
  public void skatSelected(Hand hand, Card[] skat);


  /**
   * Shows that kontra was announced.
   */
  public void kontraAnnounced();

  /**
   * Shows that rekontra was announced.
   */
  public void rekontraAnnounced();

  /**
   * Opens the kontra request in the gui.
   */
  public void kontraRequest();


  /**
   * Opens the rekontra request in the gui.
   */
  public void rekontraRequest();

  /**
   * Called when a team player announces kontra.
   */
  public void localKontraAnnounced();


  /**
   * Called when the solo player announces rekontra.
   * 
   */
  public void localRekontraAnnounced();

  /**
   * Called when a wrong password is entered.
   * 
   */
  public void showWrongPassword();

  /**
   * Adds a bot to the lobby.
   * 
   * @param hardBot true if ai is hard.
   */
  public void addBot(boolean hardBot);


  /**
   * TODO Jonas
   * 
   * @param ip
   * @param password
   */
  public void directConnectMultiplayerGame(String ip, String password);


  /*
   * TODO Aljo
   */
  public void reinitializePlayers();


  /**
   * TODO.
   */
  public void goToMenu();


  /**
   * 
   * @author Jonas Bauer
   * @param ip
   */
  void directConnectMultiplayerGame(String ip);



}
