package de.skat3.main;


import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Player;
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

  public void setSkat(Card[] skat);

  public void contractRequest();

  public void handGameRequest();

  public void showAuctionWinner(Player player);

  public void bidRequest(int bid);
  
  public void setHand(Player player);



}
