package de.skat3.io.profile;

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
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_TOTAL_ROUNDS;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_TOTAL_ROUNDS_GRAND;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_TOTAL_ROUNDS_NULL;
import static de.skat3.io.profile.Utils.JSON_MULTIPLAYER_TOTAL_ROUNDS_SUIT;
import static de.skat3.io.profile.Utils.JSON_NAME_FIELD;
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
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_TOTAL_GAMETIME;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_TOTAL_ROUNDS;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_TOTAL_ROUNDS_GRAND;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_TOTAL_ROUNDS_NULL;
import static de.skat3.io.profile.Utils.JSON_SINGLEPLAYER_TOTAL_ROUNDS_SUIT;
import java.util.UUID;
import com.google.gson.annotations.SerializedName;
import javafx.scene.image.Image;


public class Profile {
  private transient Image image;

  @SerializedName(JSON_ID_FIELD)
  private UUID uuid = UUID.randomUUID();
  @SerializedName(JSON_NAME_FIELD)
  private String name;
  @SerializedName(JSON_IMAGE_FIELD)
  private String encodedImage;
  @SerializedName(JSON_LAST_USED_FIELD)
  boolean lastUsed;

  @SerializedName(JSON_SINGLEPLAYER_TOTAL_GAMETIME)
  long playerGameTime;

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

  public Profile(String name) {
    this.name = name;
    initializeAllGameStatisticsVariablesWithZero();
  }

  public Profile(String name, Image image, String imageFormat) {
    this.name = name;
    this.setImage(image, imageFormat);
    initializeAllGameStatisticsVariablesWithZero();
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

  public void setImage(Image image, String imageFormat) {
    ImageConverter adapter = new ImageConverter();
    this.image = image;
    this.encodedImage = adapter.imageToEncodedString(image, imageFormat);
  }

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

  // Rounds

  public int getSinglePlayerTotalRounds() {
    return singlePlayerTotalRounds;
  }

  private void incrementSinglePlayerTotalRounds() {
    this.singlePlayerTotalRounds++;
  }

  public int getSinglePlayerTotalRoundsGrand() {
    return singlePlayerTotalRoundsGrand;
  }

  private void incrementSinglePlayerTotalRoundsGrand() {
    this.singlePlayerTotalRoundsGrand++;
  }

  public int getSinglePlayerTotalRoundsNull() {
    return singlePlayerTotalRoundsNull;
  }

  private void incrementSinglePlayerTotalRoundsNull() {
    this.singlePlayerTotalRoundsNull++;
  }

  public int getSinglePlayerTotalRoundsSuit() {
    return singlePlayerTotalRoundsSuit;
  }

  private void incrementSinglePlayerTotalRoundsSuit() {
    this.singlePlayerTotalRoundsSuit++;
  }

  public int getSinglePlayerRoundsWon() {
    return singlePlayerRoundsWon;
  }

  private void incrementSinglePlayerRoundsWon() {
    incrementSinglePlayerTotalRounds();
    this.singlePlayerRoundsWon++;
  }

  public int getSinglePlayerRoundsWonGrand() {
    return singlePlayerRoundsWonGrand;
  }

  public void incrementSinglePlayerRoundsWonGrand() {
    incrementSinglePlayerTotalRoundsGrand();
    incrementSinglePlayerRoundsWon();
    this.singlePlayerRoundsWonGrand++;
  }

  public int getSinglePlayerRoundsWonNull() {
    return singlePlayerRoundsWonNull;
  }

  public void incrementSinglePlayerRoundsWonNull() {
    incrementSinglePlayerTotalRoundsNull();
    incrementSinglePlayerRoundsWon();
    this.singlePlayerRoundsWonNull++;
  }

  public int getSinglePlayerRoundsWonSuit() {
    return singlePlayerRoundsWonSuit;
  }

  public void incrementSinglePlayerRoundsWonSuit() {
    incrementSinglePlayerTotalRoundsSuit();
    incrementSinglePlayerRoundsWon();
    this.singlePlayerRoundsWonSuit++;
  }

  public int getSinglePlayerRoundsLost() {
    return singlePlayerRoundsLost;
  }

  private void incrementSinglePlayerRoundsLost() {
    incrementSinglePlayerTotalRounds();
    this.singlePlayerRoundsLost++;
  }

  public int getSinglePlayerRoundsLostGrand() {
    return singlePlayerRoundsLostGrand;
  }

  public void incrementSinglePlayerRoundsLostGrand() {
    incrementSinglePlayerRoundsLost();
    incrementSinglePlayerTotalRoundsGrand();
    this.singlePlayerRoundsLostGrand++;
  }

  public int getSinglePlayerRoundsLostNull() {
    return singlePlayerRoundsLostNull;
  }

  public void incrementSinglePlayerRoundsLostNull() {
    incrementSinglePlayerRoundsLost();
    incrementSinglePlayerTotalRoundsNull();
    this.singlePlayerRoundsLostNull++;
  }

  public int getSinglePlayerRoundsLostSuit() {
    return singlePlayerRoundsLostSuit;
  }

  public void incrementSinglePlayerRoundsLostSuit() {
    incrementSinglePlayerRoundsLost();
    incrementSinglePlayerTotalRoundsSuit();
    this.singlePlayerRoundsLostSuit++;
  }

  // Score

  public int getSinglePlayerHighestScore() {
    return singlePlayerHighestScore;
  }

  public void setSinglePlayerHighestScore(int potentiallyNewScore) {
    if (potentiallyNewScore > singlePlayerHighestScore) {
      singlePlayerHighestScore = potentiallyNewScore;
    }
  }

  public int getSinglePlayerLowestScore() {
    return singlePlayerLowestScore;
  }

  public void setSinglePlayerLowestScore(int potentiallyNewScore) {
    if (potentiallyNewScore < singlePlayerLowestScore) {
      singlePlayerLowestScore = potentiallyNewScore;
    }
  }


  // Multiplayer
  // Rounds

  public int getMultiPlayerTotalRounds() {
    return multiPlayerTotalRounds;
  }

  private void incrementMultiPlayerTotalRounds() {
    this.multiPlayerTotalRounds++;
  }

  public int getMultiPlayerTotalRoundsGrand() {
    return multiPlayerTotalRoundsGrand;
  }

  private void incrementMultiPlayerTotalRoundsGrand() {
    this.multiPlayerTotalRoundsGrand++;
  }

  public int getMultiPlayerTotalRoundsNull() {
    return multiPlayerTotalRoundsNull;
  }

  private void incrementMultiPlayerTotalRoundsNull() {
    this.multiPlayerTotalRoundsNull++;
  }

  public int getMultiPlayerTotalRoundsSuit() {
    return multiPlayerTotalRoundsSuit;
  }

  private void incrementMultiPlayerTotalRoundsSuit() {
    this.multiPlayerTotalRoundsSuit++;
  }

  public int getMultiPlayerRoundsWon() {
    return multiPlayerRoundsWon;
  }

  private void incrementMultiPlayerRoundsWon() {
    incrementMultiPlayerTotalRounds();
    this.multiPlayerRoundsWon++;
  }

  public int getMultiPlayerRoundsWonGrand() {
    return multiPlayerRoundsWonGrand;
  }

  public void incrementMultiPlayerRoundsWonGrand() {
    incrementMultiPlayerTotalRoundsGrand();
    incrementMultiPlayerRoundsWon();
    this.multiPlayerRoundsWonGrand++;
  }

  public int getMultiPlayerRoundsWonNull() {
    return multiPlayerRoundsWonNull;
  }

  public void incrementMultiPlayerRoundsWonNull() {
    incrementMultiPlayerTotalRoundsNull();
    incrementMultiPlayerRoundsWon();
    this.multiPlayerRoundsWonNull++;
  }

  public int getMultiPlayerRoundsWonSuit() {
    return multiPlayerRoundsWonSuit;
  }

  public void incrementMultiPlayerRoundsWonSuit() {
    incrementMultiPlayerTotalRoundsSuit();
    incrementMultiPlayerRoundsWon();
    this.multiPlayerRoundsWonSuit++;
  }

  public int getMultiPlayerRoundsLost() {
    return multiPlayerRoundsLost;
  }

  private void incrementMultiPlayerRoundsLost() {
    incrementMultiPlayerTotalRounds();
    this.multiPlayerRoundsLost++;
  }

  public int getMultiPlayerRoundsLostGrand() {
    return multiPlayerRoundsLostGrand;
  }

  public void incrementMultiPlayerRoundsLostGrand() {
    incrementMultiPlayerRoundsLost();
    incrementMultiPlayerTotalRoundsGrand();
    this.multiPlayerRoundsLostGrand++;
  }

  public int getMultiPlayerRoundsLostNull() {
    return multiPlayerRoundsLostNull;
  }

  public void incrementMultiPlayerRoundsLostNull() {
    incrementMultiPlayerRoundsLost();
    incrementMultiPlayerTotalRoundsNull();
    this.multiPlayerRoundsLostNull++;
  }

  public int getMultiPlayerRoundsLostSuit() {
    return multiPlayerRoundsLostSuit;
  }

  public void incrementMultiPlayerRoundsLostSuit() {
    incrementMultiPlayerRoundsLost();
    incrementMultiPlayerTotalRoundsSuit();
    this.multiPlayerRoundsLostSuit++;
  }

  // Score

  public int getMultiPlayerHighestScore() {
    return multiPlayerHighestScore;
  }

  public void setMultiPlayerHighestScore(int potentiallyNewScore) {
    if (potentiallyNewScore > multiPlayerHighestScore) {
      multiPlayerHighestScore = potentiallyNewScore;
    }
  }

  public int getMultiPlayerLowestScore() {
    return multiPlayerLowestScore;
  }

  public void setMultiPlayerLowestScore(int potentiallyNewScore) {
    if (potentiallyNewScore < multiPlayerLowestScore) {
      multiPlayerLowestScore = potentiallyNewScore;
    }
  }

  public void initializeAllGameStatisticsVariablesWithZero() {
    playerGameTime = 0;
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
    singlePlayerHighestScore = 0;
    singlePlayerLowestScore = 0;

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
    multiPlayerHighestScore = 0;
    multiPlayerLowestScore = 0;
  }
  // End of Block with Game Statistics Getters and Setters




}
