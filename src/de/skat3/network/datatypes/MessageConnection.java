package de.skat3.network.datatypes;

import java.io.Serializable;
import de.skat3.gamelogic.Player;


/**
 * Underlying message class for information regarding the connection and disconnection to the
 * server. Informs the server about a new connection and the identity of the user. Informs user
 * about a closing connection and asks them to disconnect them self. Also used for transmitting the
 * lobby password when joining the server.
 * 
 * @author Jonas Bauer
 * 
 *
 */
public class MessageConnection extends Message implements Serializable {


  private static final long serialVersionUID = 5563715379825654565L;
  public String lobbyPassword;
  public String reason;
  public Player disconnectingPlayer;

  public MessageConnection(MessageType mt) {
    super(mt);
  }

  public MessageConnection(MessageType mt, String reason) {
    this(mt);
    this.reason = reason;
  }

  public MessageConnection(MessageType mt, String reason, String lobbyPassword) {
    this(mt, reason);
    this.lobbyPassword = lobbyPassword;
  }


}
