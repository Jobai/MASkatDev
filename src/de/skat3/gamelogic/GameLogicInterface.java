package de.skat3.gamelogic;

public interface GameLogicInterface {

  public void notifyLogicofPlayedCard(Card card);

  public void notifyLogicofBid(boolean accepted);

  public void notifyLogicOfHandGame(boolean accepted);

  public void notifyLogicOfNewSkat(Hand hand, Card[] skat);

  public void notifyLogicofContract(Contract contract, AdditionalMultipliers additionMultipliers);

  public void notifyLogicofKontra();

  public void notifyLogicofRekontra();



}
