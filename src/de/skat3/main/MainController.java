package de.skat3.main;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
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
import javafx.application.Platform;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.MatchResult;



public class MainController implements MainControllerInterface {


  private ClientLogicController clc;
  private GameServer gameServer;
  private GameClient gameClient;
  private GameController gameController;
  public boolean isHost;
  public Lobby currentLobby;

  @Override
  public ArrayList<Lobby> getLocalHosts() {
    return SkatMain.mainNetworkController.discoverServer();
  }

  @Override
  public void startSingleplayerGame() {
    SkatMain.mainNetworkController.playAndHostSinglePlayer();

  }

  @Override
  public void startTrainingMode() {
    // TODO Auto-generated method stub

  }

  @Override
  public void joinMultiplayerGame(Lobby lobby) {
    this.currentLobby = lobby;
    this.gameClient = SkatMain.mainNetworkController.joinServerAsClient(lobby);
    this.currentLobby = lobby;
    this.clc = gameClient.getClc();
    SkatMain.guiController.goInGame();
    this.isHost = false;

  }

  @Override
  public void joinMultiplayerGame(Lobby lobby, String password) {
    this.currentLobby = lobby;
    this.gameClient = SkatMain.mainNetworkController.joinServerAsClient(lobby);
    this.clc = gameClient.getClc();
    SkatMain.guiController.goInGame();
    this.isHost = false;


  }


  public void showWrongPassword() {
    SkatMain.guiController.showWrongPassword();
  }


  /**
   * Creates a lobby for a multiplayer game.
   * 
   * @param spieler number of players
   * @param timer 0 = no timer, >0 timer in seconds
   * @param kontraRekontraEnabled true if feature is enabled
   * @param mode Mode is either Seeger (positive number divisible by 3) or Bierlachs (negative
   *        number between -500 and -1000)
   * @throws UnknownHostException
   */

  @Override
  public void hostMultiplayerGame(String name, int numberOfPlayers, int timer,
      boolean kontraRekontraEnabled, int scoringMode) throws UnknownHostException {
    this.hostMultiplayerGame(name, "", numberOfPlayers, timer, kontraRekontraEnabled, scoringMode);

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



    this.currentLobby = new Lobby((Inet4Address) Inet4Address.getLocalHost(), 0, name, password,
        numberOfPlayers, timer, scoringMode, kontraRekontraEnabled);
    this.gameController =
        new GameController(this.currentLobby.kontraRekontraEnabled, this.currentLobby.scoringMode);
    this.gameServer =
        SkatMain.mainNetworkController.startLocalServer(this.currentLobby, this.gameController);
    this.gameClient = SkatMain.mainNetworkController.joinLocalServerAsClient();
    this.isHost = true;
    this.clc = gameClient.getClc();
    SkatMain.guiController.goInGame();


  }

  @Override
  public void showCardPlayed(Player player, Card card) {
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.getInGameController().playCard(player, card);


      }
    });
    SkatMain.lgs.addToTrick(card);
  }

  @Override
  public void sendMessage(String message) {
    clc.sendChatMessage(message);
  }

  /**
   * Leave current game/lobby
   */
  @Override
  public void receiveMessage(String message) {

    SkatMain.lgs.chatMessages.add(message);
  }



  @Override
  public void exitGame() {

    clc.leaveGame();
    // TODO go back to the menu

  }


  @Override
  public void setHand(Player player) {
    SkatMain.lgs.localClient.setHand(player.getHand());
    Platform.runLater(new Runnable() {


      @Override
      public void run() {
        SkatMain.guiController.getInGameController().startRound();


      }
    });

  }

  @Override
  public void bidRequest(int bid) {
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        // TODO Auto-generated method stub
        SkatMain.guiController.bidRequest(bid);


      }
    });


  }


  @Override
  public void showAuctionWinner(Player player) {
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        // TODO Auto-generated method stub
        SkatMain.guiController.getInGameController().showAuctionWinner();


      }
    });
  }



  /**
   * 
   */
  @Override
  public void handGameRequest() {
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.handGameRequest();


      }
    });



  }


  @Override
  public void contractRequest() {
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.contractRequest();


      }
    });


  }

  @Override
  public void showContract(Contract contract, AdditionalMultipliers additionalMultipliers) {
    SkatMain.lgs.contract = contract;
    SkatMain.lgs.additionalMultipliers = additionalMultipliers;
    SkatMain.guiController.getInGameController().showContract(contract, additionalMultipliers);

  }

  @Override
  public void playCardRequest() {

    Card currentCard = SkatMain.lgs.getCurrentCardInTrick();
    if (currentCard != null) {
      SkatMain.lgs.localClient.getHand().setPlayableCards(currentCard, SkatMain.lgs.contract);
    }
    if (SkatMain.lgs.timerInSeconds > 0) {
      new Timer(SkatMain.lgs.timerInSeconds);
    }
    Platform.runLater(new Runnable() {


      @Override
      public void run() {
        SkatMain.guiController.getInGameController().makeAMove(true);


      }
    });


  }

  @Override
  public void showResults(Result result) {
    SkatMain.guiController.getInGameController().showResults(result);

  }

  @Override
  public void showEndScreen(MatchResult matchResult) {
    // SkatMain.guiController.getInGameController().showEndScreen(matchResult); TODO
  }

  @Override
  public void setSkat(Card[] skat) {
    SkatMain.lgs.skat = skat;
    SkatMain.guiController.getInGameController().showSkatSelection();

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
    System.out.println(card);
    // network
  }

  // public Profile readProfile(String id) {
  // return SkatMain.ioController.readProfile(id);
  // }

  public ArrayList<Profile> getProfileList() {
    return SkatMain.ioController.getProfileList();
  }

  public void addProfile(Profile profile) {
    SkatMain.ioController.addProfile(profile);
  }


  // public void editProfile(Profile profile, String name, Image image) {
  // SkatMain.ioController.editProfile(profile, name, image);
  // }

  public boolean deleteProfile(Profile profile) {
    return SkatMain.ioController.deleteProfile(profile);
  }

  @Override
  public void startGame() {
    this.gameServer.setGameMode(1);
    this.gameController.startGame(this.currentLobby.players,
        this.gameServer.getSeverLogicController());
    clc.announceGameStarted();


  }

  /**
   * 
   */
  public void initializeLocalGameState() {
    SkatMain.lgs = new LocalGameState(this.currentLobby.numberOfPlayers,
        this.currentLobby.getPlayers(), this.currentLobby.timer);
    System.out.println(Arrays.toString(this.currentLobby.getPlayers()));
  }

  @Override
  public void skatSelected(Hand hand, Card[] skat) {
    clc.throwAnswer(hand, skat);
    // FIXME
  }

  @Override
  public void kontraAnnounced() {
    // GUI TODO

  }

  @Override
  public void rekontraAnnounced() {
    // GUI TODO

  }

  @Override
  public void kontraRequest() {
    // GUI TODO

  }

  @Override
  public void rekontraRequest() {
    // GUI TODO

  }

  @Override
  public void roundRestarted() {
    // GUI TODO

  }


}
