package de.skat3.gamelogic;

import java.util.concurrent.ThreadLocalRandom;

public class TestMain {

  public static void main(String[] args) {
    Player eins = new Player();
    Player zwei = new Player();
    Player drei = new Player();
    Player[] test = {eins, zwei, drei};
    GameController gc = new GameController(test);
    gc.startNewRound(true);

  }
}
