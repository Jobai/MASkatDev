package de.skat3.main;

import de.skat3.ai.Ai;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
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

  }

  public void selectSkatRequest(Card[] skat, Player bot) {
    // TODO

  }

  public void playCardRequest(Player bot) {
    SkatMain.clc.playAnswer(this.getBot(bot).ai.chooseCard());

  }

  /**
   * 
   * @param bot
   */
  public void contractRequest(Player bot) {
    SkatMain.clc.contractAnswer(this.getBot(bot).ai.chooseContract(),
        this.getBot(bot).ai.chooseAdditionalMultipliers());

  }

  public void handGameRequest(Player bot) {
    SkatMain.clc.handAnswer(this.getBot(bot).ai.acceptHandGame());
  }

  public void bidRequest(int bid, Player bot) {
    System.out.println("BOT WAS ASKED");
    Player p =this.getBot(bot);
    Ai ai = p.ai;
    boolean b = ai.acceptBid(bid);
    SkatMain.clc.bidAnswer(b);

  }

  public void kontraRequest(Player bot) {
    // TODO

  }


  public void rekontraRequest(Player bot) {
    // TODO

  }



}
