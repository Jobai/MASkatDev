package de.skat3.network.datatypes;

import de.skat3.main.Lobby;
import java.io.Serializable;


/**
 * @author Jonas Bauer Message Klasse die Benutzt wird um dem Sever mitzuteilen das der Client
 *         beendet wurde und die Verbindung geschlossen werden kann
 * 
 *
 */
public class MessageConnection extends Message implements Serializable {

  public String lobbyPassword;
  public String reason;

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
