package de.skat3.gui;

import de.skat3.gui.matchfield.InGameController;


/**
 * Interface to interact with the gui.
 * 
 * @author Aljoscha Domonell
 *
 */
public interface GuiControllerInterface {

  /**
   * Opens the in game view.
   */
  public void goInGame();

  /**
   * Opens the menu view.
   */
  public void goToMenu();

  /**
   * Get in game view controller.
   * 
   * @return Controller to interact with the in game view.
   */
  public InGameController getInGameController();

  /**
   * Block all inputs to the gui.
   * 
   * @param value True / False.
   */
  public void blockInput(boolean value);

  /**
   * Show a wrong password popup.
   */
  public void showWrongPassword();

  /**
   * Causes the taskbar icon to blink until the windows is back in focus. Used to inform the user
   * about a needed action or completed task.
   * 
   * @author Jonas Bauer
   */
  public void blinkAlert();

  /**
   * Creates and shows a custom alert prompt. Used for informing the user of a failed action.
   * 
   * @author Jonas Bauer
   * @param title of the alarm prompt.
   * @param prompt text of the alarm prompt.
   */
  public void showCustomAlarmPromt(String title, String prompt);

  /**
   * Refresh all statistics.
   */
  public void refreshStatistik();

}
