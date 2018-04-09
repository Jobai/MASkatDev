package de.skat3.main;

import java.util.ArrayList;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.gamelogic.Timer;
import de.skat3.network.server.GameServer;

public class MainController implements MainControllerInterface {

  @Override
  public ArrayList<Lobby> getLocalHosts() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void startSingleplayerGame() {
    // TODO Auto-generated method stub

  }

  @Override
  public void startTrainingMode() {
    // TODO Auto-generated method stub

  }

  @Override
  public void joinMultiplayerGame(Lobby lobby) {
    // TODO Auto-generated method stub

  }

  @Override
  public void joinMultiplayerGame(Lobby lobby, String password) {
    // TODO Auto-generated method stub

  }

  @Override
  public void hostMultiplayerGame(int spieler) {
    // TODO Auto-generated method stub

  }

  @Override
  public void hostMultiplayerGame(int spieler, String password) {
    // TODO Auto-generated method stub

  }

  @Override
  public void hostMultiplayerGame(int spieler, int timer) {
    // TODO Auto-generated method stub

  }

  @Override
  public void hostMultiplayerGame(int spieler, int timer, String password) {
    // TODO Auto-generated method stub

  }

  /**
   * Creates a lobby for a multiplayer game.
   * 
   * @param spieler number of players
   * @param timer 0 = no timer, >0 timer in seconds
   * @param password "" no password, any other String will be set as the password.
   * @param kontraRekontraEnabled true if feature is enabled
   * @param mode Mode is either Seeger (positive number divisible by 3) or Bierlachs (negative
   *        number between -500 and -1000)
   */

  public void hostMultiplayerGame(int spieler, int timer, String password,
      boolean kontraRekontraEnabled, int mode) {
    // SkatMain.lgs = new LocalGameState();
    GameServer gm = new GameServer();
    System.out.println("ja");
    GameController gameController =
        new GameController(gm.getSeverLogicController(), null, kontraRekontraEnabled, mode);
    gm.setGameController(gameController);

  }

  @Override
  public void playCard(Card card) {
    SkatMain.lgs.addToTrick(card);

  }

  @Override
  public void sendMessage(String message) {
    // TODO Auto-generated method stub

  }

  @Override
  public void exitGame() {
    // TODO Auto-generated method stub

  }


  @Override
  public void setHand(Player player) {
    SkatMain.lgs.localClient.setHand(player.getHand());

  }

  @Override
  public void bidRequest(int bid) {
    SkatMain.guiController.bidRequest(bid);

  }


  @Override
  public void showAuctionWinner(Player player) {
    //SkatMain.guiController.g
  }



  /**
   * 
   */
  @Override
  public void handGameRequest() {
    SkatMain.guiController.handGameRequest();


  }


  @Override
  public void contractRequest() {
    SkatMain.guiController.contractRequest();

  }

  @Override
  public void showContract(Contract contract, AdditionalMultipliers additionalMultipliers) {
    SkatMain.lgs.contract = contract;
    SkatMain.lgs.additionalMultipliers = additionalMultipliers;
    // TODO gui

  }

  @Override
  public void playCardRequest() {
    if (SkatMain.lgs.timerInSeconds > 0) {
      new Timer(SkatMain.lgs.timerInSeconds);
    }
    // TODO GUI

  }

  @Override
  public void showResults(Result result) {
    // TODO methode von gui

  }

  @Override
  public void showEndScreen(Object o) {
    // TODO methode von gui
  }

  @Override
  public void setSkat(Card[] skat) {
    SkatMain.lgs.skat = skat;
    // TODO gui skat selection

  }



  @Override
  public void localBid(boolean accepted) {
    // network

  }

  public void handGameSelected(boolean accepted) {

    // network
  }

  @Override
  public void contractSelected(Contract contract, AdditionalMultipliers additionalMultipliers) {

    // network
  }

  @Override
  public void localCardPlayed(Card card) {
    // network
  }



}
