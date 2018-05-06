package gamelogic;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import de.skat3.gamelogic.CardDeck;
import de.skat3.gamelogic.Contract;

public class TestCards {
  CardDeck cardDeck;
  Contract contract;

  @Before
  public void setUp() throws Exception {
    cardDeck = new CardDeck();

  }

  @Test
  public void beats1() {
    contract = Contract.DIAMONDS;
    assertEquals(false,
        cardDeck.getCard("ACE OF SPADES").beats(this.contract, cardDeck.getCard("JACK OF CLUBS")));
  }

  @Test
  public void beats2() {
    contract = Contract.GRAND;
    assertEquals(true, cardDeck.getCard("JACK OF SPADES").beats(this.contract,
        cardDeck.getCard("JACK OF DIAMONDS")));
  }

  @Test
  public void beats3() {
    contract = Contract.HEARTS;
    assertEquals(true,
        cardDeck.getCard("ACE OF SPADES").beats(this.contract, cardDeck.getCard("ACE OF CLUBS")));
  }

  @Test
  public void beats4() {
    contract = Contract.HEARTS;
    assertEquals(false,
        cardDeck.getCard("ACE OF SPADES").beats(this.contract, cardDeck.getCard("JACK OF CLUBS")));
  }

  @Test
  public void beats5() {
    contract = Contract.NULL;
    assertEquals(true,
        cardDeck.getCard("ACE OF SPADES").beats(this.contract, cardDeck.getCard("JACK OF CLUBS")));
  }

  @Test
  public void beats6() {
    contract = Contract.NULL;
    assertEquals(true,
        cardDeck.getCard("JACK OF CLUBS").beats(this.contract, cardDeck.getCard("ACE OF SPADES")));
  }


}
