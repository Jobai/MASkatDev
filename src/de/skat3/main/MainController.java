package de.skat3.main;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.LogicAnswers;
import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.gamelogic.TrainingController;
import de.skat3.io.profile.Profile;
import de.skat3.network.client.GameClient;
import de.skat3.network.server.GameServer;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class MainController implements MainControllerInterface {


  private GameServer gameServer;
  private GameClient gameClient;
  private GameController gameController;
  public boolean isHost;
  public Lobby currentLobby;
  public transient DoubleProperty maxNumberOfPlayerProperty;
  public transient DoubleProperty numberOfPlayerProperty;
  public ObservableList<String> chatMessages;



  /**
   * Enter any case with a keyWord command which can be entered in the chat field. Dont call this
   * Method only add a case! This method will be called by the gui.
   * 
   * @param command Command word.
   */
  public void execCommand(String command) {
    switch (command) {
      case "$exit": {
        System.exit(0);
        break;
      }
      case "/addBot": {
        System.out.println("addbot");
        this.addBot(false);
        break;
      }

      default: {

      }
    }
  }

  @Override
  public ArrayList<Lobby> getLocalHosts() {
    ArrayList<Lobby> lobbys = SkatMain.mainNetworkController.discoverServer();
    this.blinkAlert();
    return lobbys;
  }

  @Override
  public void startSingleplayerGame(boolean hardBot, boolean hardBot2, int scoringMode,
      boolean kontraRekontraEnabled) {
    SkatMain.lgs = null;
    SkatMain.aiController = new AiController();
    try {
      this.currentLobby = new Lobby((Inet4Address) Inet4Address.getLocalHost(), 0, scoringMode,
          kontraRekontraEnabled);
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    chatMessages = FXCollections.observableArrayList();
    this.maxNumberOfPlayerProperty = new SimpleDoubleProperty(this.currentLobby.numberOfPlayers);
    this.numberOfPlayerProperty = new SimpleDoubleProperty(this.currentLobby.currentPlayers);
    this.gameController =
        new GameController(this.currentLobby.kontraRekontraEnabled, this.currentLobby.scoringMode);
    this.gameServer =
        SkatMain.mainNetworkController.startLocalServer(this.currentLobby, this.gameController);
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    this.gameClient = SkatMain.mainNetworkController.joinLocalServerAsClient();
    this.isHost = true;
    SkatMain.clc = gameClient.getClc();
    SkatMain.mainNetworkController.addAItoLocalServer(hardBot);
    SkatMain.mainNetworkController.addAItoLocalServer(hardBot2);
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    SkatMain.guiController.goInGame();

  }

  @Override
  public void startTrainingMode(int scenario) {
    SkatMain.lgs = null;
    SkatMain.aiController = new AiController();
    try {
      this.currentLobby = new Lobby((Inet4Address) Inet4Address.getLocalHost(), 0);
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    chatMessages = FXCollections.observableArrayList();
    this.maxNumberOfPlayerProperty = new SimpleDoubleProperty(this.currentLobby.numberOfPlayers);
    this.numberOfPlayerProperty = new SimpleDoubleProperty(this.currentLobby.currentPlayers);
    this.gameController = new TrainingController(scenario);
    this.gameServer =
        SkatMain.mainNetworkController.startLocalServer(this.currentLobby, this.gameController);
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    this.gameClient = SkatMain.mainNetworkController.joinLocalServerAsClient();
    this.isHost = true;
    SkatMain.clc = gameClient.getClc();

    SkatMain.mainNetworkController.addAItoLocalServer(false);
    SkatMain.mainNetworkController.addAItoLocalServer(false);
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    SkatMain.guiController.goInGame();


  }

  @Override
  public void joinMultiplayerGame(Lobby lobby) {
    SkatMain.aiController = new AiController();
    SkatMain.lgs = null;
    this.currentLobby = lobby;
    chatMessages = FXCollections.observableArrayList();
    this.gameClient = SkatMain.mainNetworkController.joinServerAsClient(lobby);
    SkatMain.clc = gameClient.getClc();
    SkatMain.guiController.goInGame();
    this.isHost = false;

  }

  /**
   * Jonas TODO.
   */
  public void directConnectMultiplayerGame(String ip) {
    SkatMain.aiController = new AiController();
    SkatMain.lgs = null;
    Lobby lobby = new Lobby();
    Inet4Address i4;
    try {
      i4 = (Inet4Address) Inet4Address.getByName(ip);
      System.out.println(i4);
      lobby.ip = i4;
    } catch (UnknownHostException e) {
      e.printStackTrace();
      SkatMain.mainController.showCustomAlertPormpt("Invalid IP Adress!",
          "The entered IP Adress is invalid! Please check and try again.");; // TODO change to
      // "invalid IP Address"
      return;
    }

    this.gameClient = SkatMain.mainNetworkController.joinServerAsClient(lobby);
    SkatMain.clc = gameClient.getClc();
    SkatMain.guiController.goInGame();
    this.isHost = false;



  }

  @Override
  public void directConnectMultiplayerGame(String ip, String password) {
    SkatMain.aiController = new AiController();
    SkatMain.lgs = null;
    Lobby lobby = new Lobby();
    lobby.password = password;
    Inet4Address i4;
    try {
      i4 = (Inet4Address) Inet4Address.getByName(ip);
      System.out.println(i4);
      lobby.ip = i4;
    } catch (UnknownHostException e) {
      e.printStackTrace();
      SkatMain.mainController.showCustomAlertPormpt("Invalid IP Adress!",
          "The entered IP Adress is invalid! Please check and try again.");; // TODO change to
      // "invalid IP Adress"
      return;
    }

    this.gameClient = SkatMain.mainNetworkController.joinServerAsClient(lobby);
    SkatMain.clc = gameClient.getClc();
    SkatMain.guiController.goInGame();
    this.isHost = false;



  }

  @Override
  public void joinMultiplayerGame(Lobby lobby, String password) {
    SkatMain.aiController = new AiController();
    SkatMain.lgs = null;
    System.out.println("Entered password: '" + password + "'");
    this.currentLobby = lobby;
    lobby.password = password;
    chatMessages = FXCollections.observableArrayList();
    this.gameClient = SkatMain.mainNetworkController.joinServerAsClient(lobby);
    SkatMain.clc = gameClient.getClc();
    SkatMain.guiController.goInGame();
    this.isHost = false;


  }


  @Override
  public void showWrongPassword() {
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.showWrongPassword();
      }
    });
  }

  /**
   * Creates and shows a custom alert prompt. Used for informing the user of a failed action. Simply
   * calls the SkatMain.guiController.showCustomAlarmPromt(title, prompt) method trough a new
   * thread.
   * 
   * @author Jonas Bauer
   * @param title title of the alarm prompt.
   * @param prompt text of the alarm prompt.
   */
  public void showCustomAlertPormpt(String title, String prompt) {
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.showCustomAlarmPromt(title, prompt);
      }
    });
  }

  @Override
  public void goToMenu() {
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.goToMenu();
      }
    });
  }



  @Override
  public void hostMultiplayerGame(String name, int numberOfPlayers, int timer,
      boolean kontraRekontraEnabled, int scoringMode) throws UnknownHostException {
    this.hostMultiplayerGame(name, null, numberOfPlayers, timer, kontraRekontraEnabled,
        scoringMode);

  }

  @Override
  public void hostMultiplayerGame(String name, String password, int numberOfPlayers, int timer,
      boolean kontraRekontraEnabled, int scoringMode) throws UnknownHostException {

    SkatMain.aiController = new AiController();
    SkatMain.lgs = null;
    this.currentLobby = new Lobby((Inet4Address) Inet4Address.getLocalHost(), 0, name, password,
        numberOfPlayers, timer, scoringMode, kontraRekontraEnabled);
    chatMessages = FXCollections.observableArrayList();
    this.maxNumberOfPlayerProperty = new SimpleDoubleProperty(this.currentLobby.numberOfPlayers);
    this.numberOfPlayerProperty = new SimpleDoubleProperty(this.currentLobby.currentPlayers);
    this.gameController =
        new GameController(this.currentLobby.kontraRekontraEnabled, this.currentLobby.scoringMode);
    this.gameServer =
        SkatMain.mainNetworkController.startLocalServer(this.currentLobby, this.gameController);
    this.gameClient = SkatMain.mainNetworkController.joinLocalServerAsClient();
    this.isHost = true;
    SkatMain.clc = gameClient.getClc();
    SkatMain.guiController.goInGame();


  }

  void setLgs() {
    SkatMain.lgs = new LocalGameState(this.currentLobby.numberOfPlayers, this.currentLobby.timer,
        this.currentLobby.singlePlayerGame);
  }

  @Override
  public void showCardPlayed(Player player, Card card) {
    if (!player.equals(SkatMain.lgs.getLocalClient()) || SkatMain.lgs.localPlayerIsDealer()) {
      SkatMain.lgs.getPlayer(player).getHand().remove(new Card());
    }
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.getInGameController().playCard(player, card);
        SkatMain.guiController.getInGameController()
            .showWhoIsPlaying(SkatMain.lgs.getCurrentPlayer());
      }
    });
    SkatMain.lgs.addToTrick(card);
  }

  @Override
  public void sendMessage(String message) {
    SkatMain.clc.sendChatMessage(message);
  }

  @Override
  public void receiveMessage(String message) {
    this.chatMessages.add(message);
  }

  @Override
  public void reinitializePlayers() {
    System.out.println("reinitializePlayers");
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        SkatMain.guiController.getInGameController().initializePlayers();
      }
    });
  }


  @Override
  public void exitGame() {

    SkatMain.clc.leaveGame();
    if (this.gameController != null) {
      this.gameController.closeThread();
    }
    // TODO go back to the menu

  }


  @Override
  public void updatePlayer(Player player) {
    SkatMain.lgs.getLocalClient().updatePlayer(player);
  }

  @Override
  public void roundStarted() {
    SkatMain.lgs.clearAfterRound();
    SkatMain.lgs.setGameActive(true);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        SkatMain.guiController.getInGameController().startRound();
      }
    });
  }


  /**
   * Lets window icon blink in the taskbar. Used for informing the user of a needed action if the
   * game windows is not in focus.
   * 
   * @author jobauer
   */
  @Override
  public void bidRequest(int bid) {
    SkatMain.lgs.currentRequestState = LogicAnswers.BID;
    this.blinkAlert();
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showBidRequest(bid);
      }
    });


  }


  @Override
  public void showAuctionWinner(Player player) {
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showAuctionWinner();
      }
    });
  }

  @Override
  public void handGameRequest() {
    SkatMain.lgs.currentRequestState = LogicAnswers.HANDGAME;
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showHandGameRequest();
      }
    });



  }


  @Override
  public void contractRequest() {
    SkatMain.lgs.currentRequestState = LogicAnswers.CONTRACT;
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showContractRequest();
        SkatMain.guiController.getInGameController()
            .showWhoIsPlaying(SkatMain.lgs.getCurrentPlayer());
      }
    });


  }

  @Override
  public void showContract(Contract contract, AdditionalMultipliers additionalMultipliers) {
    SkatMain.lgs.setContract(contract);
    SkatMain.lgs.setAdditionalMultipliers(additionalMultipliers);
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        System.out.println("wird aufgerufen showContract");
        SkatMain.guiController.getInGameController().showSelectionInfos(true);
      }
    });
  }

  /**
   * Jonas TODO.
   */
  public void blinkAlert() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        SkatMain.guiController.blinkAlert();
      }
    });
  }

  @Override
  public void playCardRequest() {
    SkatMain.lgs.currentRequestState = LogicAnswers.CARD;
    this.blinkAlert();
    Card currentCard = SkatMain.lgs.getFirstCardPlayed();
    if (currentCard != null) {
      SkatMain.lgs.getLocalClient().getHand().setPlayableCards(currentCard,
          SkatMain.lgs.getContract());
    } else {
      SkatMain.lgs.getLocalClient().getHand().setAllCardsPlayable();
    }
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showMakeAMoveRequest(true);

      }

    });
  }

  @Override
  public void playSpecificCardRequest(Card card) {
    this.blinkAlert();
    Card currentCard = SkatMain.lgs.getFirstCardPlayed();
    if (currentCard != null) {
      SkatMain.lgs.getLocalClient().getHand().setPlayableCards(currentCard,
          SkatMain.lgs.getContract());
    } else {
      SkatMain.lgs.getLocalClient().getHand().setAllCardsPlayable();
    }
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showMakeAMoveRequest(true);
        SkatMain.guiController.getInGameController().showTutorialMakeAMoveRequest(card, true);
      }
    });
  }


  @Override
  public void showResults(Result result) {
    if (!result.roundCancelled) {
      this.modifyStatistic(result);
    }
    Platform.runLater(new Runnable() {


      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showResults(result);
        if (SkatMain.mainController.currentLobby.numberOfPlayers == 4) {
          SkatMain.lgs.rotatePlayers();
        }
      }
    });

  }

  private void modifyStatistic(Result result) {

    boolean localWinner;
    if (SkatMain.lgs.getLocalClient().isSolo()) {
      localWinner = result.soloWon;
    } else {
      localWinner = !result.soloWon;
    }
    switch (result.contract) {
      case CLUBS:
      case SPADES:
      case HEARTS:
      case DIAMONDS:
        if (localWinner) {
          if (SkatMain.lgs.isSinglePlayerGame()) {
            SkatMain.ioController.getLastUsedProfile().incrementSinglePlayerRoundsWonSuit();
          } else {
            SkatMain.ioController.getLastUsedProfile().incrementMultiPlayerRoundsWonSuit();
          }
        } else {
          if (SkatMain.lgs.isSinglePlayerGame()) {
            SkatMain.ioController.getLastUsedProfile().incrementSinglePlayerRoundsLostSuit();
          } else {
            SkatMain.ioController.getLastUsedProfile().incrementMultiPlayerRoundsLostSuit();
          }
        }
        break;
      case GRAND:
        if (localWinner) {
          if (SkatMain.lgs.isSinglePlayerGame()) {
            SkatMain.ioController.getLastUsedProfile().incrementSinglePlayerRoundsWonGrand();
          } else {
            SkatMain.ioController.getLastUsedProfile().incrementMultiPlayerRoundsWonGrand();
          }
        } else {
          if (SkatMain.lgs.isSinglePlayerGame()) {
            SkatMain.ioController.getLastUsedProfile().incrementSinglePlayerRoundsLostGrand();
          } else {
            SkatMain.ioController.getLastUsedProfile().incrementMultiPlayerRoundsLostGrand();
          }
        }
        break;
      case NULL:
        if (localWinner) {
          if (SkatMain.lgs.isSinglePlayerGame()) {
            SkatMain.ioController.getLastUsedProfile().incrementSinglePlayerRoundsWonNull();
          } else {
            SkatMain.ioController.getLastUsedProfile().incrementMultiPlayerRoundsWonNull();
          }
        } else {
          if (SkatMain.lgs.isSinglePlayerGame()) {
            SkatMain.ioController.getLastUsedProfile().incrementSinglePlayerRoundsLostNull();
          } else {
            SkatMain.ioController.getLastUsedProfile().incrementMultiPlayerRoundsLostNull();
          }
        }
        break;
      default:
        System.err.println("Wrong contract in Result");
        break;

    }
    SkatMain.ioController.updateLastUsed(SkatMain.ioController.getLastUsedProfile());
  }

  @Override
  public void showEndScreen(MatchResult matchResult) {
    Platform.runLater(new Runnable() {


      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showEndScreen(matchResult);
      }
    });
  }

  @Override
  public void selectSkatRequest(Card[] skat) {
    SkatMain.lgs.currentRequestState = LogicAnswers.SKAT;
    SkatMain.lgs.setSkat(skat);
    Platform.runLater(new Runnable() {


      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showSkatSelectionRequest();


      }
    });

  }



  @Override
  public void localBid(boolean accepted) {
    SkatMain.clc.bidAnswer(accepted);
    // network
  }

  public void handGameSelected(boolean accepted) {
    SkatMain.clc.handAnswer(accepted);
    // network
  }

  @Override
  public void contractSelected(Contract contract, AdditionalMultipliers additionalMultipliers) {
    SkatMain.clc.contractAnswer(contract, additionalMultipliers);
    // network
  }

  @Override
  public void localCardPlayed(Card card) {
    SkatMain.clc.playAnswer(card);
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
    SkatMain.clc.announceGameStarted();
    this.gameController.startGame(this.currentLobby.players,
        this.gameServer.getSeverLogicController());
  }


  @Deprecated
  public void initializeLocalGameState() {
    // this.currentLobby.sortPlayers();
  }

  @Override
  public void skatSelected(Hand hand, Card[] skat) {
    SkatMain.lgs.getSkat()[0] = skat[0];
    SkatMain.lgs.getSkat()[1] = skat[1];
    SkatMain.lgs.getLocalClient().setHand(hand);
    SkatMain.clc.throwAnswer(new Hand(hand.cards), skat);
  }

  @Override
  public void kontraAnnounced() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showText("Kontra announcend");
            SkatMain.guiController.getInGameController().showKontraButton(false);
      }
    });
  }

  @Override
  public void rekontraAnnounced() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showText("Rekontra announced");
      }
    });
  }

  @Override
  public void kontraRequest() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showReKontraButton();
      }
    });
  }

  @Override
  public void rekontraRequest() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showReKontraButton();
      }
    });

  }

  @Override
  public void localKontraAnnounced() {
    SkatMain.clc.kontraAnswer(); // FIXME

  }

  @Override
  public void localRekontraAnnounced() {
    SkatMain.clc.reKontraAnswer(); // FIXME

  }

  @Override
  public void updateEnemy(Player transmittedPlayer) {
    SkatMain.lgs.getPlayer(transmittedPlayer).updatePlayer(transmittedPlayer);
  }

  @Override
  public void addBot(boolean hardBot) {
    SkatMain.mainNetworkController.addAItoLocalServer(hardBot);

  }

  @Override
  public void showBid(String bid, Player player) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        if (currentLobby.numberOfPlayers == 3) {
          if (!player.equals(SkatMain.lgs.getLocalClient())) {
            SkatMain.guiController.getInGameController().showText(player.getName() + ": " + bid);
          }
        } else {
          if (!player.equals(SkatMain.lgs.getLocalClient()) || SkatMain.lgs.localPlayerIsDealer()) {
            SkatMain.guiController.getInGameController().showText(player.getName() + ": " + bid);
          }
        }
      }
    });
  }

  public void setDealer(Player dealer) {
    SkatMain.lgs.setDealerAndRotatePlayers(dealer);
  }
}
