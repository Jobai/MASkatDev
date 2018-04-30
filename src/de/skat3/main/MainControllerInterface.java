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

public interface MainControllerInterface {


  ArrayList<Lobby> getLocalHosts();

  public void startSingleplayerGame(boolean hardBot, boolean hardBot2, int scoringMode,
      boolean kontraRekontraEnabled);

  public void startTrainingMode();

  public void joinMultiplayerGame(Lobby lobby);

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

  public void setHand(Player player);

  public void showContract(Contract contract, AdditionalMultipliers additionalMultipliers);

  public void showResults(Result result);

  public void showEndScreen(MatchResult matchResult);

  public void localCardPlayed(Card card);

  public void localBid(boolean accepted);

  public void contractSelected(Contract contract, AdditionalMultipliers additionalMultipliers);

  public void handGameSelected(boolean accepted);

  public void skatSelected(Hand hand, Card[] skat);

  public void kontraAnnounced();

  public void rekontraAnnounced();

  public void kontraRequest();

  public void rekontraRequest();

  public void localKontraAnnounced();

  public void localRekontraAnnounced();

  public void roundRestarted();

  public void showWrongPassword();

  



}
