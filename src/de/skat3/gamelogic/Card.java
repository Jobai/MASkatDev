package de.skat3.gamelogic;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {

  private Suit suit;
  private Value value;
  private ImageView view;

  public Card(Suit suit, Value value) {
    this.suit = suit;
    this.value = value;
    String imageUrl =
        "../cardImages/" + this.getUrl() + ".png";
    System.out.println(imageUrl);
    this.view = new ImageView(new Image(imageUrl));
  }

  Suit getSuit() {
    return this.suit;
  }

  Value getValue() {
    return this.value;
  }

  String getUrl() {
    return this.value.name().toLowerCase() + "_of_" + this.suit.name().toLowerCase();
  }

  @Override
  public String toString() {
    return this.value + "OF" + this.suit;
  }
}
