package de.skat3.main;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Hand;
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
import java.util.Arrays;
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



  @Override
  public ArrayList<Lobby> getLocalHosts() {
    ArrayList<Lobby> lobbys = SkatMain.mainNetworkController.discoverServer();
    this.blinkAlert();
    return lobbys;
  }

  @Override
  public void startSingleplayerGame(boolean hardBot, boolean hardBot2, int scoringMode,
      boolean kontraRekontraEnabled) {
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
    try {
      this.currentLobby = new Lobby((Inet4Address) Inet4Address.getLocalHost(), 0);
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
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
    this.currentLobby = lobby;
    chatMessages = FXCollections.observableArrayList();
    this.gameClient = SkatMain.mainNetworkController.joinServerAsClient(lobby);
    SkatMain.clc = gameClient.getClc();
    // while (SkatMain.lgs == null) {
    // try {
    // TimeUnit.MILLISECONDS.sleep(10);
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    SkatMain.guiController.goInGame();
    this.isHost = false;

  }

  /**
   * Jonas TODO.
   */
  public void directConnectMultiplayerGame(String ip) {

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
    System.out.println("Entered password: '" + password + "'");
    this.currentLobby = lobby;
    lobby.password = password;
    chatMessages = FXCollections.observableArrayList();
    this.gameClient = SkatMain.mainNetworkController.joinServerAsClient(lobby);
    SkatMain.clc = gameClient.getClc();
    // while (SkatMain.lgs == null) {
    // try {
    // TimeUnit.MILLISECONDS.sleep(10);
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
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
    // while (SkatMain.lgs == null) {
    // try {
    // TimeUnit.MILLISECONDS.sleep(10);
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    SkatMain.guiController.goInGame();


  }

  void setLgs() {
    SkatMain.lgs = new LocalGameState(this.currentLobby.numberOfPlayers, this.currentLobby.timer,
        this.currentLobby.singlePlayerGame);
  }

  @Override
  public void showCardPlayed(Player player, Card card) {
    if (!player.equals(SkatMain.lgs.getLocalClient())) {
      SkatMain.lgs.getPlayer(player).getHand().remove(new Card());
    }
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        if (currentLobby.numberOfPlayers == 4) {
          if (player.equals(SkatMain.lgs.getEnemyThree())) {

            SkatMain.guiController.getInGameController().playCard(SkatMain.lgs.getLocalClient(),
                card);
          }


        } else {
          SkatMain.guiController.getInGameController().playCard(player, card);
        }
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
    this.gameController.closeThread();
    // TODO go back to the menu

  }


  @Override
  public void updatePlayer(Player player) {
    System.out.println("�bergeben " + player.getHand());
    SkatMain.lgs.getLocalClient().updatePlayer(player);
  }

  @Override
  public void roundStarted() {
    SkatMain.lgs.getEnemyOne().setHand(new Hand());
    SkatMain.lgs.getEnemyTwo().setHand(new Hand());
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
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showHandGameRequest();
      }
    });



  }


  @Override
  public void contractRequest() {
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        SkatMain.guiController.getInGameController().showContractRequest();
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
    this.blinkAlert();
    Card currentCard = SkatMain.lgs.getFirstCardPlayed();
    if (currentCard != null) {
      SkatMain.lgs.getLocalClient().getHand().setPlayableCards(currentCard,
          SkatMain.lgs.getContract());
    } else {
      SkatMain.lgs.getLocalClient().getHand().setAllCardsPlayable();
    }
    if (SkatMain.lgs.getTimerInSeconds() > 0) {
      // new Timer(SkatMain.lgs.timerInSeconds); Replaced by -->
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          SkatMain.guiController.getInGameController().startTimer(SkatMain.lgs.getTimerInSeconds());
        }
      });
    }
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        SkatMain.guiController.getInGameController().makeAMoveRequest(true);
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
        SkatMain.guiController.getInGameController().makeAMoveRequest(true);
        SkatMain.guiController.getInGameController().singleMakeAMoveRequest(card, true);
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
        System.out.println(Arrays.toString(result.ranks));
        SkatMain.guiController.getInGameController().showResults(result);
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
    SkatMain.guiController.getInGameController().showEndScreen(matchResult);
  }

  @Override
  public void selectSkatRequest(Card[] skat) {
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
    System.out.println("neue hand: " + hand);
    SkatMain.lgs.getSkat()[0] = skat[0];
    SkatMain.lgs.getSkat()[1] = skat[1];
    SkatMain.lgs.getLocalClient().setHand(hand);
    SkatMain.clc.throwAnswer(new Hand(hand.cards), skat);
  }

  @Override
  public void kontraAnnounced() {

  }

  @Override
  public void rekontraAnnounced() {


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
  public void localKontraAnnounced() {
    SkatMain.clc.kontraAnswer(); // FIXME

  }

  @Override
  public void localRekontraAnnounced() {
    SkatMain.clc.reKontraAnswer(); // FIXME

  }

  @Override
  public void updateEnemy(Player transmitedPlayer) {
    SkatMain.lgs.getPlayer(transmitedPlayer).updatePlayer(transmitedPlayer);
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
        SkatMain.guiController.getInGameController().showBidActivity(player, bid);
      }
    });
  }
}
