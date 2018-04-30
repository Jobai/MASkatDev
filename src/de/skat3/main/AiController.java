package de.skat3.main;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Player;

public class AiController {


  Player firstBot;
  Player secondBot;
  Player thirdBot;


  /**
   * 
   * @param bot
   */
  public void addBot(Player bot) {
    if (this.firstBot == null) {
      this.firstBot = bot;
      return;
    }
    if (this.secondBot == null) {
      this.secondBot = bot;
      return;
    }
    if (this.thirdBot == null) {
      this.thirdBot = bot;
      return;
    }
    System.err.println("Already three bots added");
  }

  public Player getBot(Player bot) {

    if (this.firstBot.equals(bot)) {
      return this.firstBot;
    }
    if (this.secondBot.equals(bot)) {
      return this.secondBot;
    }
    if (this.thirdBot.equals(bot)) {
      return this.thirdBot;
    }
    System.err.println("Bot not found");
    return null;
  }

  public void selectSkatRequest(Card[] skat, Player bot) {
    //TODO

  }

  public void playCardRequest(Player bot) {
    SkatMain.clc.playAnswer(this.getBot(bot).ai.chooseCard());

  }

  public void contractRequest(Player bot) {
    SkatMain.clc.contractAnswer(this.getBot(bot).ai.chooseContract(),
        this.getBot(bot).ai.chooseAdditionalMultipliers());

  }

  public void handGameRequest(Player bot) {
    SkatMain.clc.handAnswer(this.getBot(bot).ai.acceptHandGame());
  }

  public void bidRequest(int bid, Player bot) {
    SkatMain.clc.bidAnswer(this.getBot(bot).ai.acceptBid(bid));

  }
  
  public void kontraRequest(Player bot) {
    //TODO

  }

  
  public void rekontraRequest(Player bot) {
    // TODO
  }

}
