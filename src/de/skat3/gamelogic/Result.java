package de.skat3.gamelogic;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.Arrays;

public class Result implements Serializable {

  public int points;
  public int highestBid;
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
  public Player firstPlace;
  public Player secondPlace;
  public Player thirdPlace;
  public Player fourthPlace;

  /**
   * 
   * @param roundInstance
   */
  // XXX TESTEN
  public Result(RoundInstance roundInstance) {

    this.highestBid = BiddingValues.values[roundInstance.currentBiddingValue];
    this.handGame = roundInstance.addtionalMultipliers.isHandGame();
    this.openGame = roundInstance.addtionalMultipliers.isOpenHand();
    this.schneiderAnnounced = roundInstance.addtionalMultipliers.isSchneiderAnnounced();
    this.schwarzAnnounced = roundInstance.addtionalMultipliers.isSchwarzAnnounced();
    this.kontra = roundInstance.kontra;
    this.rekontra = roundInstance.rekontra;
    this.contract = roundInstance.contract;
    this.calcResult(roundInstance);


  }

  int getPoints() {
    return this.points;
  }

  boolean getSoloWon() {
    return soloWon;
  }


  void calcResult(RoundInstance roundInstance) {
    int contractValue = 0;
    boolean isBierlachs = (roundInstance.mode < 0) ? true : false;
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
        if (this.handGame) {
          if (this.openGame) {
            if (roundInstance.solo.wonTricks.size() == 30 && this.highestBid <= 59) {
              this.soloWon = true;
              if (isBierlachs) {
                this.points = -59;
              } else {
                this.points = 59;
              }
            } else {
              this.soloWon = false;
              if (this.highestBid > 59) {
                this.bidTooHigh = true;
              }
              if (isBierlachs) {
                this.points = -59;
              } else {
                this.points = -118;
              }
            }
          } else {
            if (roundInstance.solo.wonTricks.size() == 30 && this.highestBid < 35) {
              this.soloWon = true;
              if (isBierlachs) {
                this.points = -35;
              } else {
                this.points = 35;
              }
            } else {
              this.soloWon = false;
              if (this.highestBid > 35) {
                this.bidTooHigh = true;
              }
              if (isBierlachs) {
                this.points = -35;
              } else {
                this.points = -70;
              }

            }
          }
        } else {
          if (this.openGame) {
            if (roundInstance.solo.wonTricks.size() == 30 && this.highestBid <= 46) {
              this.soloWon = true;
              if (isBierlachs) {
                this.points = -46;
              } else {
                this.points = 46;
              }
            } else {
              this.soloWon = false;
              if (this.highestBid > 46) {
                this.bidTooHigh = true;
              }
              if (isBierlachs) {
                this.points = -46;
              } else {
                this.points = -92;
              }
            }
          } else {
            if (roundInstance.solo.wonTricks.size() == 30 && this.highestBid <= 23) {
              this.soloWon = true;
              if (isBierlachs) {
                this.points = -23;
              } else {
                this.points = 23;
              }
            } else {
              this.soloWon = false;
              if (this.highestBid > 23) {
                this.bidTooHigh = true;
              }
              if (isBierlachs) {
                this.points = -23;
              } else {
                this.points = -46;
              }
            }
          }
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
    if (pointsSoloPlayer > 60) {
      won = true;
      if (pointsSoloPlayer >= 90) {
        this.schneider = true;
      }
      if (pointsSoloPlayer == 120) {
        this.schwarz = true;
      }
    } else {
      won = false;
      if (pointsSoloPlayer <= 30) {
        this.schneider = true;
      }
      if (pointsSoloPlayer == 0) {
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
    if (won) {
      if (roundInstance.addtionalMultipliers.isSchneiderAnnounced() && !schneider
          || roundInstance.addtionalMultipliers.isSchneiderAnnounced() && !schwarz) {
        if (isBierlachs) {
          this.points = -baseValue * contractValue;
          this.soloWon = false;

        } else {
          this.points = -2 * baseValue * contractValue;
        }


      } else {
        if (isBierlachs) {
          this.points = -baseValue * contractValue;
          this.soloWon = true;



        } else {
          this.points = baseValue * contractValue;
        }

      }

    } else {
      if (isBierlachs) {
        this.points = -baseValue * contractValue;
        this.soloWon = false;

      } else {
        this.points = -2 * baseValue * contractValue;
      }

    }

    if (this.kontra) {
      this.points *= 2;
    }
    if (this.rekontra) {
      this.points *= 2;
    }
    this.applyChanges(roundInstance);


  }

  private void applyChanges(RoundInstance roundInstance) {
    boolean isBierlachs = (roundInstance.mode < 0) ? true : false;
    if (isBierlachs) {
      if (this.soloWon) {
        Player[] team = roundInstance.getTeamPlayer();
        team[0].changePoints(points);
        roundInstance.gameThread.gc.matchResult.addScoreToHistory(roundInstance.team[0], points);
        team[1].changePoints(points);
        roundInstance.gameThread.gc.matchResult.addScoreToHistory(roundInstance.team[1], points);
      } else {
        roundInstance.solo.changePoints(points);
        roundInstance.gameThread.gc.matchResult.addScoreToHistory(roundInstance.solo, points);
      }

    } else {
      if (this.points > 0) {
        this.soloWon = true;
        roundInstance.solo.changePoints(points);
        roundInstance.gameThread.gc.matchResult.addScoreToHistory(roundInstance.solo, points);
      } else {
        this.soloWon = false;
        roundInstance.gameThread.gc.matchResult.addScoreToHistory(roundInstance.solo, points);
        roundInstance.solo.changePoints(points);
      }
    }
    Player[] players = roundInstance.gameThread.gc.allPlayers;
    int[] points = new int[players.length];
    for (int i = 0; i < players.length; i++) {
      points[i] = players[i].points;
    }
    Arrays.sort(points);
    for (int i = points.length - 1; i >= 0; i--) {
      for (int j = 0; j < players.length; j++) {
        if (points[i] == players[j].points) {
          switch (i) {
            case 0:
              if (players.length == 3) {
                this.thirdPlace = players[j];
              } else {
                this.fourthPlace = players[j];
              }
              break;
            case 1:
              if (players.length == 3) {
                this.secondPlace = players[j];
              } else {
                this.thirdPlace = players[j];
              }
              break;
            case 2:
              if (players.length == 3) {
                this.firstPlace = players[j];
              } else {
                this.secondPlace = players[j];
              }
              break;
            case 3:
              this.firstPlace = players[j];
              break;

            default:
              System.err.println("ERROR IN RESULT");
              break;
          }
        }
      }
    }
  }

}
