package de.skat3.gui;

import javafx.scene.layout.AnchorPane;

/**
 * Abstract class to declare a menu.
 * 
 * @author Aljoscha Domonell
 */
public abstract class Menu {
  private int rank;
  private AnchorPane pane;

  /**
   * Constructor.
   * 
   * @param rank Postion in the menu frame starting from the left. Has to be unique.
   */
  public Menu(int rank) {
    this.rank = rank;
  }

  protected void setPane(AnchorPane pane) {
    this.pane = pane;
  }

  /**
   * Get pane.
   * 
   * @return the Pane which represents the Menu.
   */
  public AnchorPane getPane() {
    return this.pane;
  }

  /**
   * Checks if the current menu is placed left or right relative to the compared menu.
   * 
   * @param menu Menu to compare your current Menu to.
   * @return -1 if the current menu is placed right to the compared menu. 1 if the current menu is
   *         placed left to the compared menu. 0 if it is the same.
   */
  public int compareTo(Menu menu) {
    if (this.rank == menu.rank) {
      return 0;
    } else {
      if (this.rank > menu.rank) {
        return -1;
      } else {
        return 1;
      }
    }
  }
}
