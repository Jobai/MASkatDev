package de.skat3.gui;

public class GuiController implements GuiInterface {
  private Gui gui;

  protected void setGui(Gui gui) {
    this.gui = gui;
  }
  
  public void blockInput(boolean value) {
    this.gui.getMainStage().getScene().getRoot().setDisable(value);
  }

  @Override
  public void bidRequest(int bid) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void contractRequest() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean handGameRequest() {
    // TODO Auto-generated method stub
    return false;
  }
  
}
