package de.skat3.gamelogic;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card implements Serializable {



  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Suit suit;
  private Value value;
  private String view;
  private boolean playable;
  private int trickValue;



  public Card() {
    this.view = "cardImage/green_back.png";
  }

  /**
   * Represents a single card.
   */
  public Card(Suit suit, Value value) {
    this.suit = suit;
    this.value = value;
    this.addTrickValue();
    this.view = "cardImages/" + this.getUrl() + ".png";



  }

  public ImageView getImage() {
    return new ImageView(new Image(this.view));
  }


  void addTrickValue() {
    switch (this.value) {
      case SEVEN:
      case EIGHT:
      case NINE:
        this.trickValue = 0;
        break;
      case QUEEN:
        this.trickValue = 3;
        break;
      case KING:
        this.trickValue = 4;
        break;
      case TEN:
        this.trickValue = 10;
        break;
      case ACE:
        this.trickValue = 11;
        break;
      case JACK:
        this.trickValue = 2;
        break;
      default:
        System.out.println("Error in addTrickValue");
        break;
    }
  }

  int getTrickValue() {
    return this.trickValue;
  }

  public Suit getSuit() {
    return this.suit;
  }

  public Value getValue() {
    return this.value;
  }

  void setPlayable(boolean b) {
    this.playable = b;
  }


  public boolean isPlayable() {
    return this.playable;
  }

  /**
   * Checks if the card is a trump in the given contract.
   * 
   * @param c current contract
   * @return true if the card is a trump card.
   */
  boolean isTrump(Contract contract) {
    switch (contract) {
      case DIAMONDS:
        return (this.suit.equals(Suit.DIAMONDS) || this.isJack()) ? true : false;
      case HEARTS:
        return (this.suit.equals(Suit.HEARTS) || this.isJack()) ? true : false;
      case SPADES:
        return (this.suit.equals(Suit.SPADES) || this.isJack()) ? true : false;
      case CLUBS:
        return (this.suit.equals(Suit.CLUBS) || this.isJack()) ? true : false;
      case GRAND:
        return this.isJack();
      default:
        return false;
    }
  }

  /**
   * Checks if card is a jack from any suit.
   * 
   * @return true if card is a jack.
   */
  boolean isJack() {
    return (this.value.equals(Value.JACK)) ? true : false;
  }

  /**
   * Checks which card would win a trick.
   * 
   * @param contract current contract
   * @param card the other card.
   * @return only true if the card is better than the @param card.
   */
  boolean beats(Contract contract, Card card) {

    switch (contract) {

      case DIAMONDS:
      case HEARTS:
      case SPADES:
      case CLUBS:
      case GRAND:
        if (this.suit == card.suit) {
          return (this.value.ordinal() > card.value.ordinal()) ? true : false;
        } else {
          if (this.isJack() && card.isJack()) {
            return (this.suit.ordinal() > card.suit.ordinal()) ? true : false;
          }
          if (this.isJack()) {
            return true;
          }
          return !card.isTrump(contract);
        }
      case NULL:
        if (this.suit == card.suit) {
          return (this.calcNullValue() > card.calcNullValue()) ? true : false;
        } else {
          return true;
        }
      default:
        System.err.println("Wrong Contract");
        break;

    }
    return false;

  }

  boolean equals(Card card) {

    return (this.value == card.value && this.suit == card.suit) ? true : false;

  }


  private int calcNullValue() {

    switch (this.value) {
      case SEVEN:
        return 0;
      case EIGHT:
        return 1;
      case NINE:
        return 2;
      case TEN:
        return 3;
      case JACK:
        return 4;
      case QUEEN:
        return 5;
      case KING:
        return 6;
      case ACE:
        return 7;
      default:
        return -1;

    }
  }

  private String getUrl() {
    return this.value.name().toLowerCase() + "_of_" + this.suit.name().toLowerCase();
  }


  @Override
  public String toString() {
    return this.value + " OF " + this.suit;
  }
}
