package de.skat3.gamelogic;

public interface GameLogicInterface {

  public void notifyLogicofPlayedCard(Card card);

  public void notifyLogicofBid(boolean accepted);

  public void notifyLogicofContract(Contract contract,AdditionalMulipliers additionMultipliers);

  public void notifyLogicofKontra();

  public void notifyLogicofRekontra();
}
