package io;

import static org.junit.Assert.assertEquals;
import java.util.Random;
import org.junit.Test;
import de.skat3.io.profile.Profile;

public class TestProfileStatistics {

  @Test
  public void testSinglePlayerStatistics() {
    Profile profile = new Profile("testUser");

    for (int i = 0; i < 10; i++) {
      profile.initializeAllGameStatisticsVariablesWithZero();

      int roundsWonGrand = new Random().nextInt(5);
      int roundsWonNull = new Random().nextInt(5);
      int roundsWonSuit = new Random().nextInt(5);

      int roundsLostGrand = new Random().nextInt(5);
      int roundsLostNull = new Random().nextInt(5);
      int roundsLostSuit = new Random().nextInt(5);

      int roundsGrand = roundsWonGrand + roundsLostGrand;
      int roundsNull = roundsWonNull + roundsLostNull;
      int roundsSuit = roundsWonSuit + roundsLostSuit;

      int roundsWon = roundsWonGrand + roundsWonNull + roundsWonSuit;
      int roundsLost = roundsLostGrand + roundsLostNull + roundsLostSuit;

      int roundsTotal_1 = roundsGrand + roundsNull + roundsSuit;
      int roundsTotal_2 = roundsWon + roundsLost;

      for (int j = 0; j < roundsWonGrand; j++) {
        profile.incrementSinglePlayerRoundsWonGrand();
      }
      for (int j = 0; j < roundsWonNull; j++) {
        profile.incrementSinglePlayerRoundsWonNull();
      }
      for (int j = 0; j < roundsWonSuit; j++) {
        profile.incrementSinglePlayerRoundsWonSuit();
      }
      for (int j = 0; j < roundsLostGrand; j++) {
        profile.incrementSinglePlayerRoundsLostGrand();
      }
      for (int j = 0; j < roundsLostNull; j++) {
        profile.incrementSinglePlayerRoundsLostNull();
      }
      for (int j = 0; j < roundsLostSuit; j++) {
        profile.incrementSinglePlayerRoundsLostSuit();
      }

      assertEquals(profile.getSinglePlayerRoundsWon(), roundsWon);
      assertEquals(profile.getSinglePlayerRoundsLost(), roundsLost);

      assertEquals(profile.getSinglePlayerRoundsWonGrand(), roundsWonGrand);
      assertEquals(profile.getSinglePlayerRoundsWonNull(), roundsWonNull);
      assertEquals(profile.getSinglePlayerRoundsWonSuit(), roundsWonSuit);

      assertEquals(profile.getSinglePlayerRoundsLostGrand(), roundsLostGrand);
      assertEquals(profile.getSinglePlayerRoundsLostNull(), roundsLostNull);
      assertEquals(profile.getSinglePlayerRoundsLostSuit(), roundsLostSuit);

      assertEquals(profile.getSinglePlayerTotalRoundsGrand(), roundsGrand);
      assertEquals(profile.getSinglePlayerTotalRoundsNull(), roundsNull);
      assertEquals(profile.getSinglePlayerTotalRoundsSuit(), roundsSuit);

      assertEquals(profile.getSinglePlayerRoundsWon(), roundsWon);
      assertEquals(profile.getSinglePlayerRoundsLost(), roundsLost);

      assertEquals(profile.getSinglePlayerTotalRounds(), roundsTotal_1);
      assertEquals(roundsTotal_2, roundsTotal_1);
    }
  }

  @Test
  public void testMultiPlayerStatistics() {
    Profile profile = new Profile("testUser");

    for (int i = 0; i < 10; i++) {
      profile.initializeAllGameStatisticsVariablesWithZero();

      int roundsWonGrand = new Random().nextInt(5);
      int roundsWonNull = new Random().nextInt(5);
      int roundsWonSuit = new Random().nextInt(5);

      int roundsLostGrand = new Random().nextInt(5);
      int roundsLostNull = new Random().nextInt(5);
      int roundsLostSuit = new Random().nextInt(5);

      int roundsGrand = roundsWonGrand + roundsLostGrand;
      int roundsNull = roundsWonNull + roundsLostNull;
      int roundsSuit = roundsWonSuit + roundsLostSuit;

      int roundsWon = roundsWonGrand + roundsWonNull + roundsWonSuit;
      int roundsLost = roundsLostGrand + roundsLostNull + roundsLostSuit;

      int roundsTotal_1 = roundsGrand + roundsNull + roundsSuit;
      int roundsTotal_2 = roundsWon + roundsLost;

      for (int j = 0; j < roundsWonGrand; j++) {
        profile.incrementMultiPlayerRoundsWonGrand();
      }
      for (int j = 0; j < roundsWonNull; j++) {
        profile.incrementMultiPlayerRoundsWonNull();
      }
      for (int j = 0; j < roundsWonSuit; j++) {
        profile.incrementMultiPlayerRoundsWonSuit();
      }
      for (int j = 0; j < roundsLostGrand; j++) {
        profile.incrementMultiPlayerRoundsLostGrand();
      }
      for (int j = 0; j < roundsLostNull; j++) {
        profile.incrementMultiPlayerRoundsLostNull();
      }
      for (int j = 0; j < roundsLostSuit; j++) {
        profile.incrementMultiPlayerRoundsLostSuit();
      }

      assertEquals(profile.getMultiPlayerRoundsWon(), roundsWon);
      assertEquals(profile.getMultiPlayerRoundsLost(), roundsLost);

      assertEquals(profile.getMultiPlayerRoundsWonGrand(), roundsWonGrand);
      assertEquals(profile.getMultiPlayerRoundsWonNull(), roundsWonNull);
      assertEquals(profile.getMultiPlayerRoundsWonSuit(), roundsWonSuit);

      assertEquals(profile.getMultiPlayerRoundsLostGrand(), roundsLostGrand);
      assertEquals(profile.getMultiPlayerRoundsLostNull(), roundsLostNull);
      assertEquals(profile.getMultiPlayerRoundsLostSuit(), roundsLostSuit);

      assertEquals(profile.getMultiPlayerTotalRoundsGrand(), roundsGrand);
      assertEquals(profile.getMultiPlayerTotalRoundsNull(), roundsNull);
      assertEquals(profile.getMultiPlayerTotalRoundsSuit(), roundsSuit);

      assertEquals(profile.getMultiPlayerRoundsWon(), roundsWon);
      assertEquals(profile.getMultiPlayerRoundsLost(), roundsLost);

      assertEquals(profile.getMultiPlayerTotalRounds(), roundsTotal_1);
      assertEquals(roundsTotal_2, roundsTotal_1);
    }
  }
}
