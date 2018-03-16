package de.skat3.main;


import de.skat3.gamelogic.Card;
import java.util.ArrayList;

public interface MainControllerInterface {


  ArrayList<Lobby> getLocalHosts();

  public void startSingleplayerGame();

  public void startTrainingMode();

  public void joinMultiplayerGame(Lobby lobby);

  public void joinMultiplayerGame(Lobby lobby, String password);

  public void hostMultiplayerGame(int spieler);

  public void hostMultiplayerGame(int spieler, String password);

  public void hostMultiplayerGame(int spieler, int timer);

  public void hostMultiplayerGame(int spieler, int timer, String password);

  public void playCard(Card card);

  public void sendMessage(String message);

  public void exitGame();



}
