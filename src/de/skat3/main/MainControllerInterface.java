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
 * Main interface for gui-network and io-gui.
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
   * Starts a specific scenario in training mode:
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
   * @param password
   */
  public void joinMultiplayerGame(Lobby lobby, String password);

  public void hostMultiplayerGame(String name, String password, int numberOfPlayers, int timer,
      boolean kontraRekontraEnabled, int scoringMode) throws UnknownHostException;

  public void hostMultiplayerGame(String name, int numberOfPlayers, int timer,
      boolean kontraRekontraEnabled, int scoringMode) throws UnknownHostException;

  public void startGame();

  public void showCardPlayed(Player player, Card card);

  public void sendMessage(String message);

  public void receiveMessage(String message);

  public void exitGame();

  public void selectSkatRequest(Card[] skat);

  public void playCardRequest();

  public void contractRequest();

  public void handGameRequest();

  public void showAuctionWinner(Player player);

  public void bidRequest(int bid);

  public void updatePlayer(Player player);

  public void showContract(Contract contract, AdditionalMultipliers additionalMultipliers);

  public void showResults(Result result);

  public void showEndScreen(MatchResult matchResult);

  public void localCardPlayed(Card card);

  public void localBid(boolean accepted);


  /**
   * 
   * @param contract
   * @param additionalMultipliers
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
   * TODO
   * 
   */
  public void roundRestarted();

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



}
