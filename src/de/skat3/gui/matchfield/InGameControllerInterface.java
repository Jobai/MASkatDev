package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;


/**
 * Interface to interact with the in game gui.
 * 
 * @author Aljoscha Domonell
 *
 */
public interface InGameControllerInterface {

  /**
   * Is refreshing the Label which is showing the player how many players are joined and ready. Is
   * reinitializing all overlay informations about the players (Name, Picture, Points, Hard/Easy
   * Bot)
   */
  public void initializePlayers();

  /**
   * Is showing a text on the screen.
   * 
   * @param text Text to be shown.
   */
  public void showText(String text);

  /**
   * The GUI will initialize the player hands based on the players in SkatMain.lgs. Changes made to
   * the hand of any player will not effect the hand in the GUI. To play a card use
   * this.playCard(...).
   */
  public void startRound();

  /**
   * Is playing a card from the hand of the spezified player.
   * 
   * 
   * @param owner Player from which hand the card will be played.
   * @param card Card to play.
   */
  public void playCard(Player owner, Card card);

  /**
   * Player is asked to play a card. The user can now interact with his hand. When chosen the card
   * will be send to the server and makeAMoveRequest will be set to false.
   * 
   * @param value True if the player is allowed to do a move. False if not.
   */
  public void showMakeAMoveRequest(boolean value);

  /**
   * Is setting only the card which is passed with this method to be playable in the gui. So only
   * this card can be played via click on it. This spezial card in flashes. All other normally
   * playable cards are not greyed out but can not be played.
   * 
   * @param card Card which can be played and is flashes.
   * @param value True if the player is allowed to do a move. False if not.
   */
  public void showTutorialMakeAMoveRequest(Card card, boolean value);

  /**
   * Is flashing the hand of the player who is making a move right now. Does not effect the local
   * player.
   * 
   * @param player Player who is making a move right now.
   */
  public void showWhoIsPlaying(Player player);

  /**
   * Opens a new bid request popup. The player can now take the bid or pass it.
   * 
   * @param bid Bid value which is shown in the popup.
   */
  public void showBidRequest(int bid);

  /**
   * Opens a new bid request popup. The player can now take the bid or pass it but only one of the
   * options is available.
   * 
   * @param bid Bid value which is shown in the popup.
   * @param yes If true only the yes(bid) button is activated. If false its only the no(pass)
   *        button.
   */
  public void showTutorialBidRequest(int bid, boolean yes);

  /**
   * Opens a handgame popup. The player can choose to take the skat or pass it.
   */
  public void showHandGameRequest();

  /**
   * Opens a new contract request popup. The player can now select his contract.
   */
  public void showContractRequest();

  /**
   * Shows the selected contract and all the additional mulltipliers.
   * 
   * @param show True to show them. False to hide them.
   */
  public void showSelectedGame(boolean show);

  /**
   * Shows a kontra selection button. When clicked kontra will be announced and the button is
   * hidden.
   * 
   * @param show True to show the button. False to hide it.
   */
  public void showKontraButton(boolean show);

  /**
   * Shows a rekontra selection button. When clicked rekontra will be announced and the button is
   * hidden.
   */
  public void showReKontraButton();

  /*
   * Shows the Skat (which has to be stored in SkatMain)!.lgs on the screen. The User can now
   * interact with it. When ready the new cards in the local hand and the new skat are send to the
   * server.
   * 
   */
  public void showSkatSelectionRequest();

  /**
   * @author emre
   * 
   * @param text
   * @param width
   * @param height
   */
  public void showTrainingModeInfoText(String text, int width, int height);

  /**
   * Opens an round result popup. The popup contains many facts about the finished round, like
   * (Winner, points...) No further actions are taken or input is requested.
   * 
   * @author tistraub
   * 
   * @param results Result in which the informations about the game are stored.
   */
  public void showResults(Result results);

  /**
   * Opens an end screen popup. The popup contains many facts about the finished game, like (Winner,
   * points...) No further actions are taken or input is requested.
   * 
   * @param matchResult Result in which the informations about the game are stored.
   */
  public void showEndScreen(MatchResult matchResult);

}
