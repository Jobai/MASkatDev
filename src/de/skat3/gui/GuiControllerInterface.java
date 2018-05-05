package de.skat3.gui;

import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Result;

public interface GuiControllerInterface {

  public void showWrongPassword();

  public void showRoundResult(Result result);

  public void showGameResult(MatchResult matchResult);
  
  public void refreshStatistik();

}
