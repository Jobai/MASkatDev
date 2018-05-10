package de.skat3.io.profile;

import static de.skat3.io.profile.Utils.ACHIEVEMENT_UNLOCKED;
import static de.skat3.io.profile.Utils.JSON_ID_FIELD;
import static de.skat3.io.profile.Utils.JSON_IMAGE_FIELD;
import static de.skat3.io.profile.Utils.JSON_LAST_USED_FIELD;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_HIGHEST_SCORE;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_LOWEST_SCORE;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_ROUNDS_LOST;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_ROUNDS_LOST_GRAND;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_ROUNDS_LOST_NULL;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_ROUNDS_LOST_SUIT;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_ROUNDS_WON;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_ROUNDS_WON_GRAND;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_ROUNDS_WON_NULL;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_ROUNDS_WON_SUIT;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_TOTAL_GAMES;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_TOTAL_GAMES_LOST;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_TOTAL_GAMES_WON;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_TOTAL_ROUNDS;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_TOTAL_ROUNDS_GRAND;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_TOTAL_ROUNDS_NULL;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_TOTAL_ROUNDS_SUIT;
import static de.skat3.io.profile.Utils.JSON_NAME_FIELD;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_AND_MULTIPLAYER_TOTAL_GAMETIME;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_HIGHEST_SCORE;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_LOWEST_SCORE;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_ROUNDS_LOST;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_ROUNDS_LOST_GRAND;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_ROUNDS_LOST_NULL;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_ROUNDS_LOST_SUIT;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_ROUNDS_WON;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_ROUNDS_WON_GRAND;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_ROUNDS_WON_NULL;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_ROUNDS_WON_SUIT;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_TOTAL_GAMES;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_TOTAL_GAMES_LOST;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_TOTAL_GAMES_WON;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_TOTAL_ROUNDS;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_TOTAL_ROUNDS_GRAND;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_TOTAL_ROUNDS_NULL;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_TOTAL_ROUNDS_SUIT;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;
import javafx.scene.image.Image;


/**
 * Represents profile of a player.
 * 
 * @author Artem Zamarajev
 */
public class Profile implements ProfileStatisticsInterface {
  private transient Image image;

  @SerializedName(JSON_ID_FIELD)
  private UUID uuid = UUID.randomUUID();
  @SerializedName(JSON_NAME_FIELD)
  private String name;
  @SerializedName(JSON_IMAGE_FIELD)
  private String encodedImage;
  @SerializedName(JSON_LAST_USED_FIELD)
  private boolean lastUsed;

  @SerializedName(ACHIEVEMENT_UNLOCKED)
  private boolean achievementUnlocked;

  @SerializedName(JSON_SINGLEPLAYER_AND_MULTIPLAYER_TOTAL_GAMETIME)
  long playerGameTime;

  @SerializedName(JSON_SINGLEPLAYER_TOTAL_GAMES)
  int singlePlayerTotalGames;
  @SerializedName(JSON_SINGLEPLAYER_TOTAL_GAMES_WON)
  int singlePlayerTotalGamesWon;
  @SerializedName(JSON_SINGLEPLAYER_TOTAL_GAMES_LOST)
  int singlePlayerTotalGamesLost;

  @SerializedName(JSON_SINGLEPLAYER_TOTAL_ROUNDS)
  int singlePlayerTotalRounds;
  @SerializedName(JSON_SINGLEPLAYER_TOTAL_ROUNDS_GRAND)
  int singlePlayerTotalRoundsGrand;
  @SerializedName(JSON_SINGLEPLAYER_TOTAL_ROUNDS_NULL)
  int singlePlayerTotalRoundsNull;
  @SerializedName(JSON_SINGLEPLAYER_TOTAL_ROUNDS_SUIT)
  int singlePlayerTotalRoundsSuit;
  @SerializedName(JSON_SINGLEPLAYER_ROUNDS_WON)
  int singlePlayerRoundsWon;
  @SerializedName(JSON_SINGLEPLAYER_ROUNDS_WON_GRAND)
  int singlePlayerRoundsWonGrand;
  @SerializedName(JSON_SINGLEPLAYER_ROUNDS_WON_NULL)
  int singlePlayerRoundsWonNull;
  @SerializedName(JSON_SINGLEPLAYER_ROUNDS_WON_SUIT)
  int singlePlayerRoundsWonSuit;
  @SerializedName(JSON_SINGLEPLAYER_ROUNDS_LOST)
  int singlePlayerRoundsLost;
  @SerializedName(JSON_SINGLEPLAYER_ROUNDS_LOST_GRAND)
  int singlePlayerRoundsLostGrand;
  @SerializedName(JSON_SINGLEPLAYER_ROUNDS_LOST_NULL)
  int singlePlayerRoundsLostNull;
  @SerializedName(JSON_SINGLEPLAYER_ROUNDS_LOST_SUIT)
  int singlePlayerRoundsLostSuit;
  @SerializedName(JSON_SINGLEPLAYER_HIGHEST_SCORE)
  int singlePlayerHighestScore;
  @SerializedName(JSON_SINGLEPLAYER_LOWEST_SCORE)
  int singlePlayerLowestScore;


  @SerializedName(JSON_MULTIPLAYER_TOTAL_GAMES)
  int multiPlayerTotalGames;
  @SerializedName(JSON_MULTIPLAYER_TOTAL_GAMES_WON)
  int multiPlayerTotalGamesLost;
  @SerializedName(JSON_MULTIPLAYER_TOTAL_GAMES_LOST)
  int multiPlayerTotalGamesWon;

  @SerializedName(JSON_MULTIPLAYER_TOTAL_ROUNDS)
  int multiPlayerTotalRounds;
  @SerializedName(JSON_MULTIPLAYER_TOTAL_ROUNDS_GRAND)
  int multiPlayerTotalRoundsGrand;
  @SerializedName(JSON_MULTIPLAYER_TOTAL_ROUNDS_NULL)
  int multiPlayerTotalRoundsNull;
  @SerializedName(JSON_MULTIPLAYER_TOTAL_ROUNDS_SUIT)
  int multiPlayerTotalRoundsSuit;
  @SerializedName(JSON_MULTIPLAYER_ROUNDS_WON)
  int multiPlayerRoundsWon;
  @SerializedName(JSON_MULTIPLAYER_ROUNDS_WON_GRAND)
  int multiPlayerRoundsWonGrand;
  @SerializedName(JSON_MULTIPLAYER_ROUNDS_WON_NULL)
  int multiPlayerRoundsWonNull;
  @SerializedName(JSON_MULTIPLAYER_ROUNDS_WON_SUIT)
  int multiPlayerRoundsWonSuit;
  @SerializedName(JSON_MULTIPLAYER_ROUNDS_LOST)
  int multiPlayerRoundsLost;
  @SerializedName(JSON_MULTIPLAYER_ROUNDS_LOST_GRAND)
  int multiPlayerRoundsLostGrand;
  @SerializedName(JSON_MULTIPLAYER_ROUNDS_LOST_NULL)
  int multiPlayerRoundsLostNull;
  @SerializedName(JSON_MULTIPLAYER_ROUNDS_LOST_SUIT)
  int multiPlayerRoundsLostSuit;
  @SerializedName(JSON_MULTIPLAYER_HIGHEST_SCORE)
  int multiPlayerHighestScore;
  @SerializedName(JSON_MULTIPLAYER_LOWEST_SCORE)
  int multiPlayerLowestScore;

  /**
   * Creates new profile with name as parameter name and initializes all statistics variables with
   * zero.
   * 
   * @param name name to be set.
   */
  public Profile(String name) {
    this.name = name;
    initializeAllGameStatisticsVariablesWithZero();
  }

  /**
   * Creates new profile with name as parameter name and image as parameter image and initializes
   * all statistics variables with zero.
   * 
   * @param name name to be set.
   * @param image image to be set.
   * @param imageFormat format of the image that is to be set.
   */
  public Profile(String name, Image image, String imageFormat) {
    this.name = name;
    this.setImage(image, imageFormat);
    initializeAllGameStatisticsVariablesWithZero();
  }

  public boolean getAchievementUnlocked() {
    return achievementUnlocked;
  }

  public void setAchievementUnlocked(boolean newAchievementUnlocked) {
    achievementUnlocked = newAchievementUnlocked;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEncodedImage() {
    return encodedImage;
  }

  public Image getImage() {
    return image;
  }

  /**
   * Sets image as JavaFX Image and encodedImage as String.
   * 
   * @param image image to be set.
   * @param imageFormat format of the image.
   */
  public void setImage(Image image, String imageFormat) {
    ImageConverter adapter = new ImageConverter();
    this.image = image;
    this.encodedImage = adapter.imageToEncodedString(image, imageFormat);
  }

  /**
   * Sets image from encoded String.
   *
   */
  public void setImageFromEncodedString() {
    ImageConverter adapter = new ImageConverter();
    this.image = adapter.encodedStringToImage(this.encodedImage);
  }

  public boolean getLastUsed() {
    return lastUsed;
  }

  public void setLastUsed(boolean lastUsed) {
    this.lastUsed = lastUsed;
  }


  // Block of Getters and Setters for Game Statistics


  // Time
  public long getPlayerGameTime() {
    return playerGameTime;
  }

  public void setPlayerGameTime(long playerGameTime) {
    this.playerGameTime = playerGameTime;
  }

  // Singleplayer

  // Games
  @Override
  public int getSinglePlayerTotalGames() {
    return singlePlayerTotalGames;
  }

  @Override
  public int getSinglePlayerTotalGamesWon() {
    return singlePlayerTotalGamesWon;
  }

  @Override
  public int getSinglePlayerTotalGamesLost() {
    return singlePlayerTotalGamesLost;
  }

  @Override
  public void incrementSinglePlayerTotalGamesWon() {
    singlePlayerTotalGamesWon++;
    singlePlayerTotalGames++;
  }

  public void incrementSinglePlayerTotalGamesLost() {
    singlePlayerTotalGamesLost++;
    singlePlayerTotalGames++;
  }

  // Rounds
  @Override
  public int getSinglePlayerTotalRounds() {
    return singlePlayerTotalRounds;
  }

  private void incrementSinglePlayerTotalRounds() {
    this.singlePlayerTotalRounds++;
  }

  @Override
  public int getSinglePlayerTotalRoundsGrand() {
    return singlePlayerTotalRoundsGrand;
  }

  private void incrementSinglePlayerTotalRoundsGrand() {
    this.singlePlayerTotalRoundsGrand++;
  }

  @Override
  public int getSinglePlayerTotalRoundsNull() {
    return singlePlayerTotalRoundsNull;
  }

  private void incrementSinglePlayerTotalRoundsNull() {
    this.singlePlayerTotalRoundsNull++;
  }

  @Override
  public int getSinglePlayerTotalRoundsSuit() {
    return singlePlayerTotalRoundsSuit;
  }

  private void incrementSinglePlayerTotalRoundsSuit() {
    this.singlePlayerTotalRoundsSuit++;
  }

  @Override
  public int getSinglePlayerRoundsWon() {
    return singlePlayerRoundsWon;
  }

  /**
   * Increments singlePlayerTotalRounds and singlePlayerRoundsWon.
   */
  private void incrementSinglePlayerRoundsWon() {
    incrementSinglePlayerTotalRounds();
    this.singlePlayerRoundsWon++;
  }

  @Override
  public int getSinglePlayerRoundsWonGrand() {
    return singlePlayerRoundsWonGrand;
  }

  @Override
  public void incrementSinglePlayerRoundsWonGrand() {
    incrementSinglePlayerTotalRoundsGrand();
    incrementSinglePlayerRoundsWon();
    this.singlePlayerRoundsWonGrand++;
  }

  @Override
  public int getSinglePlayerRoundsWonNull() {
    return singlePlayerRoundsWonNull;
  }

  @Override
  public void incrementSinglePlayerRoundsWonNull() {
    incrementSinglePlayerTotalRoundsNull();
    incrementSinglePlayerRoundsWon();
    this.singlePlayerRoundsWonNull++;
  }

  @Override
  public int getSinglePlayerRoundsWonSuit() {
    return singlePlayerRoundsWonSuit;
  }

  @Override
  public void incrementSinglePlayerRoundsWonSuit() {
    incrementSinglePlayerTotalRoundsSuit();
    incrementSinglePlayerRoundsWon();
    this.singlePlayerRoundsWonSuit++;
  }

  @Override
  public int getSinglePlayerRoundsLost() {
    return singlePlayerRoundsLost;
  }

  private void incrementSinglePlayerRoundsLost() {
    incrementSinglePlayerTotalRounds();
    this.singlePlayerRoundsLost++;
  }

  @Override
  public int getSinglePlayerRoundsLostGrand() {
    return singlePlayerRoundsLostGrand;
  }

  @Override
  public void incrementSinglePlayerRoundsLostGrand() {
    incrementSinglePlayerRoundsLost();
    incrementSinglePlayerTotalRoundsGrand();
    this.singlePlayerRoundsLostGrand++;
  }

  @Override
  public int getSinglePlayerRoundsLostNull() {
    return singlePlayerRoundsLostNull;
  }

  @Override
  public void incrementSinglePlayerRoundsLostNull() {
    incrementSinglePlayerRoundsLost();
    incrementSinglePlayerTotalRoundsNull();
    this.singlePlayerRoundsLostNull++;
  }

  @Override
  public int getSinglePlayerRoundsLostSuit() {
    return singlePlayerRoundsLostSuit;
  }

  @Override
  public void incrementSinglePlayerRoundsLostSuit() {
    incrementSinglePlayerRoundsLost();
    incrementSinglePlayerTotalRoundsSuit();
    this.singlePlayerRoundsLostSuit++;
  }

  // Score

  public int getSinglePlayerHighestScore() {
    return singlePlayerHighestScore;
  }

  @Override
  public void setSinglePlayerHighestScore(int potentiallyNewScore) {
    if (potentiallyNewScore > singlePlayerHighestScore) {
      singlePlayerHighestScore = potentiallyNewScore;
    }
  }

  @Override
  public int getSinglePlayerLowestScore() {
    return singlePlayerLowestScore;
  }

  @Override
  public void setSinglePlayerLowestScore(int potentiallyNewScore) {
    if (potentiallyNewScore < singlePlayerLowestScore) {
      singlePlayerLowestScore = potentiallyNewScore;
    }
  }


  // Multiplayer

  // Games
  @Override
  public int getMultiPlayerTotalGames() {
    return multiPlayerTotalGames;
  }

  @Override
  public int getMultiPlayerTotalGamesWon() {
    return multiPlayerTotalGamesWon;
  }

  @Override
  public int getMultiPlayerTotalGamesLost() {
    return multiPlayerTotalGamesLost;
  }

  @Override
  public void incrementMultiPlayerTotalGamesWon() {
    multiPlayerTotalGamesWon++;
    multiPlayerTotalGames++;
  }

  @Override
  public void incrementMultiPlayerTotalGamesLost() {
    multiPlayerTotalGamesLost++;
    multiPlayerTotalGames++;
  }

  // Rounds
  @Override
  public int getMultiPlayerTotalRounds() {
    return multiPlayerTotalRounds;
  }

  private void incrementMultiPlayerTotalRounds() {
    this.multiPlayerTotalRounds++;
  }

  @Override
  public int getMultiPlayerTotalRoundsGrand() {
    return multiPlayerTotalRoundsGrand;
  }

  private void incrementMultiPlayerTotalRoundsGrand() {
    this.multiPlayerTotalRoundsGrand++;
  }

  @Override
  public int getMultiPlayerTotalRoundsNull() {
    return multiPlayerTotalRoundsNull;
  }

  private void incrementMultiPlayerTotalRoundsNull() {
    this.multiPlayerTotalRoundsNull++;
  }

  @Override
  public int getMultiPlayerTotalRoundsSuit() {
    return multiPlayerTotalRoundsSuit;
  }

  private void incrementMultiPlayerTotalRoundsSuit() {
    this.multiPlayerTotalRoundsSuit++;
  }

  @Override
  public int getMultiPlayerRoundsWon() {
    return multiPlayerRoundsWon;
  }

  /**
   * Increments multiPlayerTotalRounds and multiPlayerRoundsWon.
   */
  private void incrementMultiPlayerRoundsWon() {
    incrementMultiPlayerTotalRounds();
    this.multiPlayerRoundsWon++;
  }

  @Override
  public int getMultiPlayerRoundsWonGrand() {
    return multiPlayerRoundsWonGrand;
  }

  @Override
  public void incrementMultiPlayerRoundsWonGrand() {
    incrementMultiPlayerTotalRoundsGrand();
    incrementMultiPlayerRoundsWon();
    this.multiPlayerRoundsWonGrand++;
  }

  @Override
  public int getMultiPlayerRoundsWonNull() {
    return multiPlayerRoundsWonNull;
  }

  @Override
  public void incrementMultiPlayerRoundsWonNull() {
    incrementMultiPlayerTotalRoundsNull();
    incrementMultiPlayerRoundsWon();
    this.multiPlayerRoundsWonNull++;
  }

  @Override
  public int getMultiPlayerRoundsWonSuit() {
    return multiPlayerRoundsWonSuit;
  }

  @Override
  public void incrementMultiPlayerRoundsWonSuit() {
    incrementMultiPlayerTotalRoundsSuit();
    incrementMultiPlayerRoundsWon();
    this.multiPlayerRoundsWonSuit++;
  }

  @Override
  public int getMultiPlayerRoundsLost() {
    return multiPlayerRoundsLost;
  }

  private void incrementMultiPlayerRoundsLost() {
    incrementMultiPlayerTotalRounds();
    this.multiPlayerRoundsLost++;
  }

  @Override
  public int getMultiPlayerRoundsLostGrand() {
    return multiPlayerRoundsLostGrand;
  }

  @Override
  public void incrementMultiPlayerRoundsLostGrand() {
    incrementMultiPlayerRoundsLost();
    incrementMultiPlayerTotalRoundsGrand();
    this.multiPlayerRoundsLostGrand++;
  }

  @Override
  public int getMultiPlayerRoundsLostNull() {
    return multiPlayerRoundsLostNull;
  }

  @Override
  public void incrementMultiPlayerRoundsLostNull() {
    incrementMultiPlayerRoundsLost();
    incrementMultiPlayerTotalRoundsNull();
    this.multiPlayerRoundsLostNull++;
  }

  @Override
  public int getMultiPlayerRoundsLostSuit() {
    return multiPlayerRoundsLostSuit;
  }

  @Override
  public void incrementMultiPlayerRoundsLostSuit() {
    incrementMultiPlayerRoundsLost();
    incrementMultiPlayerTotalRoundsSuit();
    this.multiPlayerRoundsLostSuit++;
  }

  // Score
  @Override
  public int getMultiPlayerHighestScore() {
    return multiPlayerHighestScore;
  }

  @Override
  public void setMultiPlayerHighestScore(int potentiallyNewScore) {
    if (potentiallyNewScore > multiPlayerHighestScore) {
      multiPlayerHighestScore = potentiallyNewScore;
    }
  }

  @Override
  public int getMultiPlayerLowestScore() {
    return multiPlayerLowestScore;
  }

  @Override
  public void setMultiPlayerLowestScore(int potentiallyNewScore) {
    if (potentiallyNewScore < multiPlayerLowestScore) {
      multiPlayerLowestScore = potentiallyNewScore;
    }
  }

  /**
   * Initializes all statistics related variables with zero.
   */
  public void initializeAllGameStatisticsVariablesWithZero() {

    // Achievement
    achievementUnlocked = false;

    // Gametime
    playerGameTime = 0;

    // Singleplayer

    // Games
    singlePlayerTotalGames = 0;
    singlePlayerTotalGamesWon = 0;
    singlePlayerTotalGamesLost = 0;

    // Rounds
    singlePlayerTotalRounds = 0;
    singlePlayerTotalRoundsGrand = 0;
    singlePlayerTotalRoundsNull = 0;
    singlePlayerTotalRoundsSuit = 0;
    singlePlayerRoundsWon = 0;
    singlePlayerRoundsWonGrand = 0;
    singlePlayerRoundsWonNull = 0;
    singlePlayerRoundsWonSuit = 0;
    singlePlayerRoundsLost = 0;
    singlePlayerRoundsLostGrand = 0;
    singlePlayerRoundsLostNull = 0;
    singlePlayerRoundsLostSuit = 0;

    // Score
    singlePlayerHighestScore = 0;
    singlePlayerLowestScore = 0;

    // Multiplayer

    // Games
    multiPlayerTotalGames = 0;
    multiPlayerTotalGamesWon = 0;
    multiPlayerTotalGamesLost = 0;

    // Rounds
    multiPlayerTotalRounds = 0;
    multiPlayerTotalRoundsGrand = 0;
    multiPlayerTotalRoundsNull = 0;
    multiPlayerTotalRoundsSuit = 0;
    multiPlayerRoundsWon = 0;
    multiPlayerRoundsWonGrand = 0;
    multiPlayerRoundsWonNull = 0;
    multiPlayerRoundsWonSuit = 0;
    multiPlayerRoundsLost = 0;
    multiPlayerRoundsLostGrand = 0;
    multiPlayerRoundsLostNull = 0;
    multiPlayerRoundsLostSuit = 0;

    // Score
    multiPlayerHighestScore = 0;
    multiPlayerLowestScore = 0;
  }

}
