package de.skat3.gamelogic;

import java.io.Serializable;

public class Result implements Serializable {

  public int points;
  public boolean soloWon;
  public boolean handGame;
  public boolean openGame;
  public boolean schneider;
  public boolean schneiderAnnounced;
  public boolean schwarz;
  public boolean schwarzAnnounced;
  public boolean kontra;
  public boolean rekontra;

  /**
   * 
   * @param roundInstance
   */
  public Result(RoundInstance roundInstance) {

    this.handGame = roundInstance.addtionalMultipliers.isHandGame();
    this.openGame = roundInstance.addtionalMultipliers.isOpenHand();
    this.schneiderAnnounced = roundInstance.addtionalMultipliers.isSchneiderAnnounced();
    this.schwarzAnnounced = roundInstance.addtionalMultipliers.isSchwarzAnnounced();
    this.kontra = roundInstance.kontra;
    this.rekontra = roundInstance.rekontra;
    this.calcResult(roundInstance);


  }

  int getPoints() {
    return this.points;
  }

  boolean getSoloWon() {
    return soloWon;
  }

  //FIXME ueberreizen funktioniert noch
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
            if (roundInstance.solo.wonTricks.size() == 30) {
              if (isBierlachs) {
                this.points = -59;
                this.soloWon = true;
              } else {
                this.points = 59;
              }
            } else {
              if (isBierlachs) {
                this.points = -59;
                this.soloWon = false;

              } else {
                this.points = -118;
              }

            }
          } else {
            if (roundInstance.solo.wonTricks.size() == 30) {
              if (isBierlachs) {
                this.points = -35;
                this.soloWon = true;
              } else {
                this.points = 35;
              }

            } else {
              if (isBierlachs) {
                this.points = -35;
                this.soloWon = false;

              } else {
                this.points = -70;
              }

            }
          }
        } else {
          if (this.openGame) {
            if (roundInstance.solo.wonTricks.size() == 30) {
              if (isBierlachs) {
                this.points = -46;
                this.soloWon = true;

              } else {
                this.points = 46;
              }

            } else {
              if (isBierlachs) {
                this.points = -46;
                this.soloWon = false;

              } else {
                this.points = -92;
              }

            }
          } else {
            if (roundInstance.solo.wonTricks.size() == 30) {
              if (isBierlachs) {
                this.points = -23;
                this.soloWon = true;

              } else {
                this.points = 23;
              }

            } else {
              if (isBierlachs) {
                this.points = -23;
                this.soloWon = false;

              } else {
                this.points = -46;
              }

            }
          }
        }
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
    if (isBierlachs) {
      if (this.soloWon) {
        Player[] team = roundInstance.getTeamPlayer();
        team[0].changePoints(points);
        team[1].changePoints(points);
      } else {
        roundInstance.solo.changePoints(points);
      }

    } else {
      if (this.points > 0) {
        this.soloWon = true;
        roundInstance.solo.wonAGame();
        roundInstance.solo.changePoints(points);
      } else {
        this.soloWon = false;
        roundInstance.solo.lostAGame();
        roundInstance.solo.changePoints(points);
      }
    }



  }

}
