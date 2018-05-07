package gamelogic;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.CardDeck;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.RoundInstance;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;

public class TestTrickWinner {

  RoundInstance roundInstance;

  @Before
  public void setUp() throws Exception {
    CardDeck cardDeck = new CardDeck();
    roundInstance = new RoundInstance();
    Player p1 = new Player();
    Player p2 = new Player();
    Player p3 = new Player();
    p1.setHand(new Hand(cardDeck.getCards()));
    p2.setHand(new Hand(cardDeck.getCards()));
    p3.setHand(new Hand(cardDeck.getCards()));
    roundInstance.setForehand(p1);
    roundInstance.setMiddlehand(p2);
    roundInstance.setRearhand(p3);

  }

  @Test
  public void testTrickWinner1() {
    this.roundInstance.setContract(Contract.HEARTS);
    this.roundInstance.addCardtoTrick(new Card(Suit.CLUBS, Value.SEVEN));
    this.roundInstance.addCardtoTrick(new Card(Suit.SPADES, Value.EIGHT));
    this.roundInstance.addCardtoTrick(new Card(Suit.SPADES, Value.SEVEN));
    assertEquals(roundInstance.getForehand(), roundInstance.determineTrickWinner());
  }


  @Test
  public void testTrickWinner3() {

    this.roundInstance.setContract(Contract.NULL);
    this.roundInstance.addCardtoTrick(new Card(Suit.CLUBS, Value.SEVEN));
    this.roundInstance.addCardtoTrick(new Card(Suit.SPADES, Value.EIGHT));
    this.roundInstance.addCardtoTrick(new Card(Suit.SPADES, Value.JACK));
    assertEquals(roundInstance.getForehand(), roundInstance.determineTrickWinner());
  }


  @Test
  public void testTrickWinner5() {

    this.roundInstance.setContract(Contract.SPADES);
    this.roundInstance.addCardtoTrick(new Card(Suit.HEARTS, Value.QUEEN));
    this.roundInstance.addCardtoTrick(new Card(Suit.SPADES, Value.TEN));
    this.roundInstance.addCardtoTrick(new Card(Suit.HEARTS, Value.JACK));
    assertEquals(roundInstance.getRearhand(), roundInstance.determineTrickWinner());
  }


  @Test
  public void testTrickWinner7() {

    this.roundInstance.setContract(Contract.CLUBS);
    this.roundInstance.addCardtoTrick(new Card(Suit.DIAMONDS, Value.QUEEN));
    this.roundInstance.addCardtoTrick(new Card(Suit.HEARTS, Value.SEVEN));
    this.roundInstance.addCardtoTrick(new Card(Suit.HEARTS, Value.ACE));
    assertEquals(roundInstance.getForehand(), roundInstance.determineTrickWinner());
  }


}
