package de.skat3.gui;

public class GuiController implements GuiInterface {
  private Gui gui;

  protected void setGui(Gui gui) {
    this.gui = gui;
  }
  
  public void blockInput(boolean value) {
    this.gui.getMainStage().getScene().getRoot().setDisable(value);
  }
  
}
