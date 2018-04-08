package de.skat3.main;

import java.util.ArrayList;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.gamelogic.Timer;

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

  
  
  
  //---------------------------------------------------
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
    // GUI

  }


  @Override
  public void showAuctionWinner(Player player) {
    // GUI
  }



  /**
   * 
   */
  @Override
  public void handGameRequest() {
    // TODO GUI


  }

  @Override
  public void contractRequest() {
    // TODO GUI

  }

  @Override
  public void showContract(Contract contract, AdditionalMultipliers additionalMultipliers) {
    SkatMain.lgs.contract = contract;
    SkatMain.lgs.additionalMultipliers = additionalMultipliers;

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

  }

  @Override
  public void setSkat(Card[] skat) {
    SkatMain.lgs.skat = skat;

  }

  @Override
  public void localCardPlayed(Card card) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void localBid(boolean accepted) {
    
  }

  @Override
  public void contractSelected(Contract contract, AdditionalMultipliers additionalMultipliers) {
    
  }

  @Override
  public void handGameSelected(boolean accepted) {
    // TODO Auto-generated method stub
    
  }



}
