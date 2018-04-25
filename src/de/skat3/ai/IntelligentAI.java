package de.skat3.ai;

import java.util.ArrayList;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.CardDeck;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;

public class IntelligentAI implements AiControllerInterface {
  String name;
  CardDeck decks;
  Player ai;

  // Dummy Werte

  // wenn Ki gewonnen hat shwarz schneider oder offen
  ArrayList<AdditionalMultipliers> additionalMultiplayerList;
  //
  ArrayList<Contract> contractList;
  //
  ArrayList<Card> cardList;


  @Override
  public boolean acceptBid(int bid) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean acceptHandGame() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public AdditionalMultipliers chooseAdditionalMultipliers() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Contract chooseContract() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Card chooseCard() {
    // TODO Auto-generated method stub
    return null;
  }

}
