package gamelogic;

import static org.junit.Assert.*;
import java.util.ArrayList;
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
import de.skat3.io.profile.IoController;
import de.skat3.io.profile.Profile;
import de.skat3.main.SkatMain;

public class TestTrickWinner {

  RoundInstance roundInstance;

  @Before
  public void setUp() throws Exception {
    SkatMain.ioController = new IoController();
    CardDeck cardDeck = new CardDeck();
    roundInstance = new RoundInstance();
    ArrayList<Profile> list = SkatMain.ioController.getProfileList();
    Player p1 = new Player(list.get(0));
    Player p2 = new Player(list.get(1));
    Player p3 = new Player(list.get(2));
    p1.setHand(new Hand(cardDeck.getCards()));
    p2.setHand(new Hand(cardDeck.getCards()));
    p3.setHand(new Hand(cardDeck.getCards()));
    roundInstance.setForehand(p1);
    roundInstance.setMiddlehand(p2);
    roundInstance.setRearhand(p3);

  }

  @Test
  public void testTrickWinner1() {
    System.out.println("F: " + roundInstance.getForehand().getName());
    System.out.println("M: " + roundInstance.getMiddlehand().getName());
    System.out.println("R:" + roundInstance.getRearhand().getName());
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

  @Test
  public void testTrickWinner8() {

    this.roundInstance.setContract(Contract.SPADES);
    this.roundInstance.addCardtoTrick(new Card(Suit.SPADES, Value.JACK));
    this.roundInstance.addCardtoTrick(new Card(Suit.HEARTS, Value.SEVEN));
    this.roundInstance.addCardtoTrick(new Card(Suit.SPADES, Value.EIGHT));
    assertEquals(roundInstance.getForehand(), roundInstance.determineTrickWinner());
  }

  @Test
  public void testTrickWinner9() {
    this.roundInstance.setContract(Contract.SPADES);
    this.roundInstance.addCardtoTrick(new Card(Suit.DIAMONDS, Value.JACK));
    this.roundInstance.addCardtoTrick(new Card(Suit.HEARTS, Value.SEVEN));
    this.roundInstance.addCardtoTrick(new Card(Suit.SPADES, Value.EIGHT));
    assertEquals(roundInstance.getForehand(), roundInstance.determineTrickWinner());
  }


}
