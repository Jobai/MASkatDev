package de.skat3.main;

import de.skat3.ai.Ai;
import de.skat3.ai.ReturnSkat;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Player;

public class AiController {

  int botDelay = 1500;
  Player firstBot;
  Player secondBot;
  Player thirdBot;


  /**
   * 
   * @param bot
   */
  public void addBot(Player bot) {
    if (this.firstBot == null) {
      this.firstBot = bot.copyPlayer();
      System.out.println("first bot added");
      return;
    }
    if (this.secondBot == null) {
      System.out.println("second bot added");
      this.secondBot = bot.copyPlayer();
      return;
    }
    if (this.thirdBot == null) {
      System.out.println("third bot added");
      this.thirdBot = bot.copyPlayer();
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


  public void updatePlayer(Player bot, Player un) {

    this.getBot(bot).updatePlayer(bot);
    this.getBot(bot).ai.setHand(bot.getHand());
    this.getBot(bot).ai.setPosition(bot.getPosition());
    this.getBot(bot).ai.setIsSolo(bot.isSolo());
  }

  public void selectSkatRequest(Card[] skat, Player bot) {
    ReturnSkat rs = this.getBot(bot).ai.selectSkat(skat);
    SkatMain.clc.throwAnswer(rs.getHand(), rs.getSkat());


  }

  public void playCardRequest(Player bot) {
    Card currentCard = SkatMain.lgs.getFirstCardPlayed();
    if (currentCard != null) {
      this.getBot(bot).ai.getHand().setPlayableCards(currentCard, SkatMain.lgs.getContract());
    } else {
      this.getBot(bot).ai.getHand().setAllCardsPlayable();
    }
    this.delay();
    SkatMain.clc.playAnswer(this.getBot(bot).ai.chooseCard());

  }

  /**
   * 
   * @param bot
   */
  public void contractRequest(Player bot) {
    this.delay();
    SkatMain.clc.contractAnswer(this.getBot(bot).ai.chooseContract(),
        this.getBot(bot).ai.chooseAdditionalMultipliers());

  }

  public void handGameRequest(Player bot) {
    this.delay();
    SkatMain.clc.handAnswer(this.getBot(bot).ai.acceptHandGame());
  }

  public void bidRequest(int bid, Player bot) {
    Player p = this.getBot(bot);
    Ai ai = p.ai;
    boolean b = ai.acceptBid(bid);
    this.delay();
    SkatMain.clc.bidAnswer(b);

  }

  public void kontraRequest(Player bot) {
    // TODO

  }


  public void rekontraRequest(Player bot) {
    // TODO

  }

  public void delay() {
    try {
      Thread.sleep(this.botDelay);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }


}
