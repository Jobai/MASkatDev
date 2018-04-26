package de.skat3.gui;


public interface GuiControllerInterface {

  // Main Controller contractSelected
  public abstract void contractRequest();

  // Handgame / Yes / No
  // public void handGameSelected(boolean accepted) { }
  public boolean handGameRequest();

  public void showWrongPassword();

  public void showRoundResult();

  public void showGameResult();

}
