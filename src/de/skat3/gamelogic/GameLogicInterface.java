package de.skat3.gamelogic;

public interface GameLogicInterface {

  public void notifyLogicofPlayedCard(Card card);

  public void notifyLogicofBid(boolean accepted);
  
  public void notifyLogicOfHandGame(boolean accepted);
  
  public void notifyLogicOfNewSkat(Card[] skat);

  public void notifyLogicofContract(Contract contract,AdditionalMulipliers additionMultipliers);

  public void notifyLogicofKontra(boolean accepted);

  public void notifyLogicofRekontra(boolean accepted);
}
