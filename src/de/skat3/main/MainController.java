package de.skat3.main;

import java.util.ArrayList;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
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


  public void setHand(Player player) {
    SkatMain.lgs.localClient.setHand(player.getHand());

  }

  public void bidRequest(int bid) {

  }

  public void showAuctionWinner(Player player) {
    // GUI
  }


  /**
   * 
   */
  public void handGameRequest() {
    // GUI


  }

  public void contractRequest() {
    // GUI

  }

  public void showContract(Contract contract) {
    SkatMain.lgs.contract = contract;
  }


  public void showResults(Result result) {

  }

  @Override
  public void setSkat(Card[] skat) {
    SkatMain.lgs.skat = skat;

  }



}
