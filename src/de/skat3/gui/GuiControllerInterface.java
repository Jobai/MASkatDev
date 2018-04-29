package de.skat3.gui;

import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Result;

public interface GuiControllerInterface {

  // Main Controller contractSelected
  public abstract void contractRequest();

  // Handgame / Yes / No
  // public void handGameSelected(boolean accepted) { }
  public boolean handGameRequest();

  public void showWrongPassword();

  public void showRoundResult(Result result);

  public void showGameResult(MatchResult matchResult);

}
