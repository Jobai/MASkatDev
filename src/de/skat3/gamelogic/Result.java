package de.skat3.gamelogic;

public class Result {

  private int points;
  private boolean soloWon;
  private String displayCalculation;

  public Result(RoundInstance roundInstance) {
    this.calcResult(roundInstance);


  }

  int getPoints() {
    return this.points;
  }

  boolean getSoloWon() {
    return soloWon;
  }

  String getCalculationString() {
    return this.displayCalculation;
  }

  void calcResult(RoundInstance roundInstance) {
    int contractValue = 0;
    boolean isBierlachs = (roundInstance.mode > 0) ? false : true;
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
        if (roundInstance.addtionalMultipliers.isHandGame()) {
          if (roundInstance.addtionalMultipliers.isOpenHand()) {
            if (roundInstance.solo.wonTricks.size() == 30) {
              if (isBierlachs) {
                this.points = -59;
                this.displayCalculation = "Null Ouvert Hand: -59 for Opponents";
                this.soloWon = true;
              } else {
                this.points = 59;
                this.displayCalculation = "Null Ouvert Hand: +59";
              }
            } else {
              if (isBierlachs) {
                this.points = -59;
                this.displayCalculation = "Null Ouvert Hand: -59 for Declarer";
                this.soloWon = false;

              } else {
                this.points = -118;
                this.displayCalculation = "Null Ouvert Hand lost: -118";
              }

            }
          } else {
            if (roundInstance.solo.wonTricks.size() == 30) {
              if (isBierlachs) {
                this.points = -35;
                this.displayCalculation = "Null Hand: -35 for Opponents";
                this.soloWon = true;
              } else {
                this.points = 35;
                this.displayCalculation = "Null Hand: +35";
              }

            } else {
              if (isBierlachs) {
                this.points = -35;
                this.displayCalculation = "Null Hand lost: -35 for Declarer";
                this.soloWon = false;

              } else {
                this.points = -70;
                this.displayCalculation = "Null Hand lost: -70";
              }

            }
          }
        } else {
          if (roundInstance.addtionalMultipliers.isOpenHand()) {
            if (roundInstance.solo.wonTricks.size() == 30) {
              if (isBierlachs) {
                this.points = -46;
                this.displayCalculation = "Null Ouvert: -46 for Opponents";
                this.soloWon = true;

              } else {
                this.points = 46;
                this.displayCalculation = "Null Ouvert: +46";
              }

            } else {
              if (isBierlachs) {
                this.points = -46;
                this.displayCalculation = "Null Ouvert lost: -46 for Declarer";
                this.soloWon = false;

              } else {
                this.points = -92;
                this.displayCalculation = "Null Ouvert lost: -92";
              }

            }
          } else {
            if (roundInstance.solo.wonTricks.size() == 30) {
              if (isBierlachs) {
                this.points = -23;
                this.displayCalculation = "Null: -23 for Opponents";
                this.soloWon = true;

              } else {
                this.points = 23;
                this.displayCalculation = "Null: +23";
              }

            } else {
              if (isBierlachs) {
                this.points = -23;
                this.displayCalculation = "Null lost: -23 for Declarer";
                this.soloWon = false;

              } else {
                this.points = -46;
                this.displayCalculation = "Null lost: -46";
              }

            }
          }
        }
        return;
      default:
        System.out.println("Error in result calculation");
        break;

    }
    if (roundInstance.soloPlayerStartHand.contains(GameController.deck.getCard("JACK OF CLUBS"))) {
      this.displayCalculation += "With ";
    } else {
      if (!roundInstance.addtionalMultipliers.isHandGame()
          && (roundInstance.originalSkat[0].toString() == "JACK OF CLUBS"
              || roundInstance.originalSkat[1].toString() == "JACK OF CLUBS")) {
        this.displayCalculation += "With ";
      } else {
        this.displayCalculation += "Against ";
      }
    }
    int baseValue = roundInstance.soloPlayerStartHand
        .calcConsecutiveMatadors(roundInstance.contract, roundInstance.originalSkat);
    this.displayCalculation += baseValue;
    baseValue++;
    this.displayCalculation += " Game " + baseValue;
    if (roundInstance.addtionalMultipliers.isHandGame()) {
      baseValue++;
      this.displayCalculation += " Hand " + baseValue;
    }


    boolean won;
    boolean schneider = false;
    boolean schwarz = false;
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
        schneider = true;
      }
      if (pointsSoloPlayer == 120) {
        schwarz = true;
      }
    } else {
      won = false;
      if (pointsSoloPlayer <= 30) {
        schneider = true;
      }
      if (pointsSoloPlayer == 0) {
        schwarz = true;
      }
    }
    if (schneider) {
      baseValue++;
      this.displayCalculation += " Schneider " + baseValue;
    }
    if (roundInstance.addtionalMultipliers.isSchneiderAnnounced()) {
      baseValue++;
      this.displayCalculation = " Schneider announced " + baseValue;
    }
    if (schwarz) {
      baseValue++;
      this.displayCalculation += " Schwarz " + baseValue;
    }
    if (roundInstance.addtionalMultipliers.isSchwarzAnnounced()) {
      baseValue++;
      this.displayCalculation = " Schwarz announced " + baseValue;
    }
    if (roundInstance.addtionalMultipliers.isOpenHand()) {
      baseValue++;
      this.displayCalculation += " Open " + baseValue;
    }
    if (won) {
      if (roundInstance.addtionalMultipliers.isSchneiderAnnounced() && !schneider
          || roundInstance.addtionalMultipliers.isSchneiderAnnounced() && !schwarz) {
        if (isBierlachs) {
          this.displayCalculation +=
              " x " + contractValue + " = " + (-baseValue * contractValue) + " for Declarer.";
          this.points = -baseValue * contractValue;
          this.soloWon = false;

        } else {
          this.displayCalculation +=
              " x " + contractValue + " x 2 lost = " + (-2 * baseValue * contractValue);
          this.points = -2 * baseValue * contractValue;
        }


      } else {
        if (isBierlachs) {
          this.displayCalculation +=
              " x " + contractValue + " = " + (-baseValue * contractValue) + " for Opponents.";
          this.points = -baseValue * contractValue;
          this.soloWon = true;



        } else {
          this.displayCalculation += " x " + contractValue + " = +" + (baseValue * contractValue);
          this.points = baseValue * contractValue;
        }

      }

    } else {
      if (isBierlachs) {
        this.displayCalculation +=
            " x " + contractValue + " = " + (-baseValue * contractValue) + " for Declarer.";
        this.points = -baseValue * contractValue;
        this.soloWon = false;

      } else {
        this.displayCalculation +=
            " x " + contractValue + " x 2 = " + (-2 * baseValue * contractValue);
        this.points = -2 * baseValue * contractValue;
      }

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
