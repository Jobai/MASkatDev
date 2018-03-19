package de.skat3.gamelogic;

public interface GameLogicInterface {

  public void notifyLogicofPlayedCard(Card card);

  public void notifyLogicofBid(boolean b);

  public void notifyLogicofContract(Contract contract);

  public void notifyLogicofKontra();

  public void notifyLogicofRecontra();
}
