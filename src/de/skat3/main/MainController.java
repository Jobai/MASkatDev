package de.skat3.main;

import java.awt.Image;
import java.util.ArrayList;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.gamelogic.Timer;
import de.skat3.io.profile.Profile;
import de.skat3.network.client.ClientLogicController;
import de.skat3.network.client.GameClient;
import de.skat3.network.server.GameServer;


public class MainController implements MainControllerInterface {


  private ClientLogicController clc;

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
    Player localPlayer = new Player();
    GameClient localClient = new GameClient("localhost", 2018, localPlayer);


  }

  @Override
  public void playCard(Card card) {
    SkatMain.guiController.getInGameController().playCard(player, card);
    SkatMain.lgs.addToTrick(card);

  }

  @Override
  public void sendMessage(String message) {
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
    SkatMain.guiController.getInGameController().showAuctionWinner();
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
    SkatMain.guiController.getInGameController().showContract(contract, additionalMultipliers);

  }

  @Override
  public void playCardRequest() {
    if (SkatMain.lgs.timerInSeconds > 0) {
      new Timer(SkatMain.lgs.timerInSeconds);
    }
    SkatMain.guiController.getInGameController().makeAMove(true);

  }

  @Override
  public void showResults(Result result) {
    SkatMain.guiController.getInGameController().showResults(result);

  }

  @Override
  public void showEndScreen(Object o) {
    SkatMain.guiController.getInGameController().showEndScreen();
  }

  @Override
  public void setSkat(Card[] skat) {
    SkatMain.lgs.skat = skat;
   //SkatMain.guiController.getInGameController().startSkatMethod

  }



  @Override
  public void localBid(boolean accepted) {
    clc.bidAnswer(accepted);
    // network
  }

  public void handGameSelected(boolean accepted) {
    clc.handAnswer(accepted);
    // network
  }

  @Override
  public void contractSelected(Contract contract, AdditionalMultipliers additionalMultipliers) {
    clc.contractAnswer(contract, additionalMultipliers);
    // network
  }

  @Override
  public void localCardPlayed(Card card) {
    clc.playAnswer(card);
    // network
  }

  public Profile readProfile(String id) {
    return SkatMain.ioController.readProfile(id);
  }

  public ArrayList<Profile> getProfileList() {
    return SkatMain.ioController.getProfileList();
  }

  public void addProfile(Profile profile) {
    addProfile(profile);
  }

  public void editProfile(Profile profile, String name, Image image) {
    SkatMain.ioController.editProfile(profile, name, image);
  }

  public boolean deleteProfile(Profile profile) {
    return SkatMain.ioController.deleteProfile(profile);
  }

}
