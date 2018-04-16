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
import java.net.Inet4Address;
import java.net.UnknownHostException;



public class MainController implements MainControllerInterface {


  private ClientLogicController clc;
  private GameServer gameServer;
  private GameClient gameClient;

  @Override
  public ArrayList<Lobby> getLocalHosts() {    
    return SkatMain.mainNetworkController.discoverServer();
  }

  @Override
  public void startSingleplayerGame() {
    //SkatMain.mainNetworkController.blabla TODO pull

  }

  @Override
  public void startTrainingMode() {
    // TODO Auto-generated method stub

  }

  @Override
  public void joinMultiplayerGame(Lobby lobby) {
    this.gameClient = SkatMain.mainNetworkController.joinServerAsClient(lobby);

  }

  @Override
  public void joinMultiplayerGame(Lobby lobby, String password) {
      this.gameClient = SkatMain.mainNetworkController.joinServerAsClient(lobby);

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
   * @throws UnknownHostException
   */

  public void hostMultiplayerGame(String name, String password, int numberOfPlayers, int timer,
      boolean kontraRekontraEnabled, int scoringMode) throws UnknownHostException {



    Lobby lobby = new Lobby((Inet4Address) Inet4Address.getLocalHost(), 0, name, password,
        numberOfPlayers, timer, scoringMode);
    this.gameServer = SkatMain.mainNetworkController.startLocalServer(lobby);
    this.gameClient = SkatMain.mainNetworkController.joinLocalServerAsClient();


  }

  @Override
  public void playCard(Card card) {
    Player player = new Player();
    SkatMain.guiController.getInGameController().playCard(player.getUuid(), card);
    SkatMain.lgs.addToTrick(card);

  }

  @Override
  public void sendMessage(String message) {}

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
    // SkatMain.guiController.getInGameController().startSkatMethod

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

  @Override
  public void startGame() {
    
    
  }

}
