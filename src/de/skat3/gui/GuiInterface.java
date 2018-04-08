package de.skat3.gui;


public interface GuiInterface {

  // Auftruf local bid im MainController
  public void bidRequest(int bid);

  // Main Controller contractSelected
  public abstract void contractRequest();

  // Handgame / Yes / No
  // public void handGameSelected(boolean accepted) { }
  public boolean handGameRequest();

}
