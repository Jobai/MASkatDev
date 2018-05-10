package de.skat3.gui;

import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Result;

public interface GuiInterface {

  public void bidRequest(int bid);

  public abstract void contractRequest();

  public boolean handGameRequest();

  public void showWrongPassword();

  public void showRoundResult(Result result);

  public void showGameResult(MatchResult matchResult);

}
