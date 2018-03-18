package de.skat3.gui;

public class StartTestClass {
  public static GuiController guiController;

  public static void main(String[] args) {
    StartTestClass.guiController = new GuiController();
    Gui.showAndWait();
  }
}
