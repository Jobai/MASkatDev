package de.skat3.main;

import de.skat3.ai.Ai;
import de.skat3.ai.ReturnSkat;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Player;

public class AiController {

  int botDelay = 1200; // 1500
  Player firstBot;
  Player secondBot;
  Player thirdBot;


  /**
   * Adds a bot to this object if a spot is empty.
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

  /**
   * Removes the bot from this class if he exists.
   */
  public void removeBot(Player bot) {
    if (this.firstBot != null) {
      if (this.firstBot.equals(bot)) {
        this.firstBot = null;
      }
    }
    if (this.secondBot != null) {
      if (this.secondBot.equals(bot)) {
        this.secondBot = null;
      }
    }
    if (this.thirdBot != null) {
      if (this.thirdBot.equals(bot)) {
        this.thirdBot = null;
      }
    }
  }

  /**
   * Gets a bot from the aicontroller if he exists.
   */

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


  /**
   * Updates a bot in the aicontroller.
   */
  public void updatePlayer(Player bot, Player un) {


    this.getBot(bot).updatePlayer(bot);
    this.getBot(bot).ai.setHand(bot.getHand());
    this.getBot(bot).ai.setPosition(bot.getPosition());
    this.getBot(bot).ai.setIsSolo(bot.isSolo());
  }

  /**
   * Requests a skat selection from a bot.
   * 
   */

  public void selectSkatRequest(Card[] skat, Player bot) {
    ReturnSkat rs = this.getBot(bot).ai.selectSkat(skat);
    SkatMain.clc.throwAnswer(rs.getHand(), rs.getSkat());


  }

  /**
   * Asks a bot to play a card.
   */
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
   * Asks a bot to select a contract.
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

  /**
   * Asks a bot to accept or decline a bid.
   */
  public void bidRequest(int bid, Player bot) {
    Player p = this.getBot(bot);
    Ai ai = p.ai;
    boolean b = ai.acceptBid(bid);
    this.delay();
    SkatMain.clc.bidAnswer(b);

  }

  /**
   * Delays a bot decision to make him more human.
   */

  public void delay() {
    try {
      Thread.sleep(this.botDelay);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


}
