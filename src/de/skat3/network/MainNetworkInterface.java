package de.skat3.network;

public interface MainNetworkInterface {
  
  public Object getFoundServer();
  public void startLobbyServer();
  public void switchToGameServerMode();
  
  
  public void joinServerAsClient();
  

}
