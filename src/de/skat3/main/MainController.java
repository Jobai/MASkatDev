package de.skat3.main;

import java.util.ArrayList;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.CurrentGameState;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;

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

  @Override
  public void playCard(Card card) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void sendMessage(String message) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void exitGame() {
    // TODO Auto-generated method stub
    
  }

  
  public void setHand(Player player) {
    
  } 
  
  public void bidRequest(int bid) {
    
  }
  
  public void showAuctionWinner(Player player) {
    
  }
  
  public void updateGameState(CurrentGameState currentGameState) {
    
  }
  //called for every change in the actual play of the game.
  
  
  public void handGameRequest() {
    
  }
  
  public void contractRequest() {
    
  }
  
  public void showContract(Contract contract) {
    //TODO add additionmultipliers
  }
  
  public void broadcastResults(Result result) {
    
  }
  
  
  
  
  
}
