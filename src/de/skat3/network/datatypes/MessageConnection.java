package de.skat3.network.datatypes;

/**
 * @author Jonas Bauer Message Klasse die Benutzt wird um dem Sever mitzuteilen das der Client
 *         beendet wurde und die Verbindung geschlossen werden kann
 * 
 *
 */
public class MessageConnection extends Message {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public MessageConnection(MessageType mt) {
    super(mt);
  }

}
