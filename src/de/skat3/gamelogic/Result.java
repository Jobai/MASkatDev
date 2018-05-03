package de.skat3.gamelogic;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.Arrays;

public class Result implements Serializable {

  public int gameValue;
  public int currentRound;
  public int maxRounds;
  public boolean isBierlachs;
  public int highestBid;
  public int scoringPoints;
  public boolean soloWon;
  public boolean handGame;
  public boolean openGame;
  public boolean schneider;
  public boolean schneiderAnnounced;
  public boolean schwarz;
  public boolean schwarzAnnounced;
  public boolean kontra;
  public boolean rekontra;
  public boolean bidTooHigh;
  public Contract contract;
  public Player[] ranks;
  private int contractValue;
  public boolean roundCancelled;
  public String declarerName;

  /**
   * 
   * @param roundInstance
   */
  // XXX TESTEN
  public Result(RoundInstance roundInstance) {

    this.isBierlachs = (roundInstance.mode < 0) ? true : false;
    this.highestBid = BiddingValues.values[roundInstance.currentBiddingValue];
    this.handGame = roundInstance.addtionalMultipliers.isHandGame();
    this.openGame = roundInstance.addtionalMultipliers.isOpenHand();
    this.schneiderAnnounced = roundInstance.addtionalMultipliers.isSchneiderAnnounced();
    this.schwarzAnnounced = roundInstance.addtionalMultipliers.isSchwarzAnnounced();
    this.kontra = roundInstance.kontra;
    this.rekontra = roundInstance.rekontra;
    this.contract = roundInstance.contract;
    this.currentRound = roundInstance.gameThread.gc.numberOfRounds;
    if (!this.isBierlachs) {
      this.maxRounds = roundInstance.gameThread.gc.mode;
    }
    if (roundInstance.roundCancelled) {
      this.roundCancelled = true;
    } else {
      System.out.println(roundInstance.contract);
      System.out.println(roundInstance.solo.getWonTricks());
      this.calcResult(roundInstance);
    }
    this.declarerName = roundInstance.solo.getName();


  }

  int getPoints() {
    return this.gameValue;
  }

  boolean getSoloWon() {
    return soloWon;
  }


  void calcResult(RoundInstance roundInstance) {
    switch (roundInstance.contract) {
      case DIAMONDS:
        contractValue = 9;
        break;
      case HEARTS:
        contractValue = 10;
        break;
      case SPADES:
        contractValue = 11;
        break;
      case CLUBS:
        contractValue = 12;
        break;
      case GRAND:
        contractValue = 24;
        break;
      case NULL:
        contractValue = 23;
        if (this.openGame) {
          if (this.handGame) {
            this.gameValue = 59;
          } else {
            this.gameValue = 46;
          }
        } else {
          if (this.handGame) {
            this.gameValue = 35;
          } else {
            this.gameValue = 23;
          }
        }
        System.out.println(roundInstance.solo.wonTricks.size());
        if (roundInstance.solo.wonTricks.size() == 0) {
          this.soloWon = true;
        } else {
          this.soloWon = false;
        }
        this.applyChanges(roundInstance);
        return;
      default:
        System.out.println("Error in result calculation");
        break;

    }

    int baseValue = roundInstance.soloPlayerStartHand
        .calcConsecutiveMatadors(roundInstance.contract, roundInstance.originalSkat);
    baseValue++;
    System.out.println("baseValue: "+baseValue);
    if (roundInstance.addtionalMultipliers.isHandGame()) {
      baseValue++;
    }


    boolean won;
    int pointsSoloPlayer = 0;
    for (Card c : roundInstance.solo.wonTricks) {
      pointsSoloPlayer += c.getTrickValue();
    }
    for (Card c : roundInstance.skat) {
      pointsSoloPlayer += c.getTrickValue();
    }
    System.out.println("soloplayer points:" + pointsSoloPlayer);
    if (pointsSoloPlayer > 60) {
      won = true;
      if (pointsSoloPlayer >= 90) {
        this.schneider = true;
      }
      if (roundInstance.solo.wonTricks.size() == 30) {
        this.schwarz = true;
      }
    } else {
      won = false;
      if (pointsSoloPlayer <= 30) {
        this.schneider = true;
      }
      if (roundInstance.solo.wonTricks.size() == 0) {
        this.schwarz = true;
      }
    }
    if (this.schneider) {
      baseValue++;
    }
    if (this.schneiderAnnounced) {
      baseValue++;
    }
    if (this.schwarz) {
      baseValue++;
    }
    if (this.schwarzAnnounced) {
      baseValue++;
    }
    if (roundInstance.addtionalMultipliers.isOpenHand()) {
      baseValue++;
    }
    System.out.println("baseValue: " + baseValue + "  contractValue: " + contractValue);
    this.gameValue = baseValue * contractValue;
    System.out.println("gameValue " + gameValue);
    if (won) {
      if (roundInstance.addtionalMultipliers.isSchneiderAnnounced() && !schneider
          || roundInstance.addtionalMultipliers.isSchneiderAnnounced() && !schwarz) {
        this.soloWon = false;
      } else {
        this.soloWon = true;
      }
    } else {
      this.soloWon = false;
    }

    if (this.kontra) {
      this.gameValue *= 2;
    }
    if (this.rekontra) {
      this.gameValue *= 2;
    }
    this.applyChanges(roundInstance);


  }

  private void applyChanges(RoundInstance roundInstance) {
    int leastMultiple = 0;
    if (this.gameValue < this.highestBid) {
      this.bidTooHigh = true;
      if (this.highestBid % this.contractValue == 0) {
        leastMultiple = this.highestBid / this.contractValue;
      } else {
        leastMultiple = this.highestBid / this.contractValue;
        leastMultiple++;
      }
    }

    if (this.bidTooHigh) {
      this.scoringPoints = -leastMultiple * this.contractValue;
      roundInstance.solo.changePoints(this.scoringPoints, !isBierlachs);
      this.soloWon = false;
    } else {
      if (this.soloWon) {
        if (isBierlachs) {
          this.scoringPoints = -2 * this.gameValue;
          Player[] team = roundInstance.getTeamPlayer();
          team[0].changePoints(this.scoringPoints, false);
          roundInstance.gameThread.gc.matchResult.addScoreToHistory(team[0], this.scoringPoints);
          team[1].changePoints(this.scoringPoints, false);
          roundInstance.gameThread.gc.matchResult.addScoreToHistory(team[1], this.scoringPoints);
        } else {
          this.scoringPoints = this.gameValue;
          roundInstance.gameThread.gc.matchResult.addScoreToHistory(roundInstance.solo,
              this.scoringPoints);
          roundInstance.solo.changePoints(this.scoringPoints, true);
        }
      } else {
        if (isBierlachs) {
          this.scoringPoints = -2 * this.gameValue;
          roundInstance.solo.changePoints(this.scoringPoints, false);
          roundInstance.gameThread.gc.matchResult.addScoreToHistory(roundInstance.solo,
              this.scoringPoints);
        } else {
          this.scoringPoints = -2 * this.gameValue;
          roundInstance.gameThread.gc.matchResult.addScoreToHistory(roundInstance.solo,
              this.scoringPoints);
          roundInstance.solo.changePoints(this.scoringPoints, true);

        }
      }
    }
    this.ranks = new Player[roundInstance.gameThread.gc.allPlayers.length];
    for (int i = 0; i < ranks.length; i++) {
      this.ranks[i] = roundInstance.gameThread.gc.allPlayers[i].copyPlayer();
    }
    Arrays.sort(this.ranks);
  }


  @Override
  public String toString() {

    String result = "Result: ";
    if (this.isBierlachs) {
      if (this.soloWon) {
        result += "Team lost " + this.scoringPoints + " points.";
      } else {
        result += "Solo lost " + this.scoringPoints + " points.";
      }
    } else {
      if (this.soloWon) {
        result += "Solo won " + this.scoringPoints + " points.";
      } else {
        result += "Solo lost " + this.scoringPoints + " points.";
      }
    }

    return result;

  }

}


