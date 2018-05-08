package de.skat3.gamelogic;

import java.io.Serializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a single Card from the skat game.
 * 
 * @author kai29
 *
 */
@SuppressWarnings("serial")
public class Card implements Serializable {


  private Suit suit;
  private Value value;
  private String view;
  public transient ImageView imageView;
  private boolean playable;
  private int trickValue;


  /**
   * Creates an empty, facedown card.
   */
  public Card() {
    this.view = "cardImages/back_blue.png";
    this.suit = null;
    this.value = null;
  }

  /**
   * Returns an exact copy of the card.
   * 
   * @return copied Card
   */
  public Card copy() {
    Card copy = new Card();
    copy.value = this.value;
    copy.suit = this.suit;
    copy.playable = this.playable;
    copy.trickValue = this.trickValue;
    copy.view = this.view;
    copy.imageView = this.imageView;
    return copy;

  }

  /**
   * Represents a single card.
   */
  public Card(Suit suit, Value value) {
    this.playable = true;
    this.suit = suit;
    this.value = value;
    this.addTrickValue();
    this.view = "cardImages/" + this.getUrl() + ".png";

  }

  /**
   * Returns the Image of the card.
   * 
   */
  public ImageView getImage() {
    if (this.imageView == null) {
      this.imageView = new ImageView(new Image(this.view));
    }
    return this.imageView;
  }

  /**
   * Adds the point value to the card for suit and grand games.
   */
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

  public int getTrickValue() {
    return this.trickValue;
  }

  public Suit getSuit() {
    return this.suit;
  }

  public Value getValue() {
    return this.value;
  }

  public void setPlayable(boolean b) {
    this.playable = b;
  }


  public boolean isPlayable() {
    return this.playable;
  }

  /**
   * Checks if the card is a trump in the given contract.
   * 
   * @param contract current contract
   * @return true if the card is a trump card.
   */
  public boolean isTrump(Contract contract) {
    switch (contract) {
      case NULL:
        return false;
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
  public boolean beats(Contract contract, Card card) {

    switch (contract) {

      case DIAMONDS:
      case HEARTS:
      case SPADES:
      case CLUBS:
      case GRAND:



        if (this.isTrump(contract)) {
          if (card.isTrump(contract)) {
            if (this.isJack()) {
              if (card.isJack()) {
                return (this.suit.ordinal() > card.suit.ordinal()) ? true : false;
              } else {
                return true;
              }
            } else {
              if (card.isJack()) {
                return false;
              } else {
                return (this.value.ordinal() > card.value.ordinal()) ? true : false;
              }
            }
          } else {
            return true;
          }
        } else {
          if (card.isTrump(contract)) {
            return false;
          } else {
            if (this.suit == card.suit) {
              return (this.value.ordinal() > card.value.ordinal()) ? true : false;
            } else {
              return false;
            }
          }
        }
      case NULL:
        if (this.suit == card.suit) {
          return (this.calcNullValue() > card.calcNullValue()) ? true : false;
        } else {
          return false;
        }
      default:
        System.err.println("Wrong Contract");
        break;

    }
    return false;

  }

  /**
   * Compares two cards.
   * 
   * @return true if both cards are identical.
   */
  public boolean equals(Card card) throws Exception {

    if (this.value == null && this.suit == null && card.value == null && card.suit == null) {
      return true;
    } else {
      if (this.value == null ^ this.suit == null || card.value == null ^ card.suit == null) {
        throw new Exception("Invalid cards compared");
      }
    }

    return (this.value == card.value && this.suit == card.suit) ? true : false;

  }

  /**
   * Used for the null contract to change the order of the card rankings.
   * 
   * @return
   */
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

  /**
   * Returns a string to get an Image from the resources folder.
   */
  private String getUrl() {
    return this.value.name().toLowerCase() + "_of_" + this.suit.name().toLowerCase();
  }


  @Override
  public String toString() {
    return this.value + " OF " + this.suit;
  }
}

