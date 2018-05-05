package de.skat3.io.profile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Utils {
  static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  static final String JSON_PATH_PRODUCTION = "data/profiles.json";
  // Production constant names
  static final String JSON_ID_FIELD = "id";
  static final String JSON_NAME_FIELD = "name";
  static final String JSON_PASSWORD_FIELD = "password";
  static final String JSON_IMAGE_FIELD = "image";
  static final String JSON_LAST_USED_FIELD = "lastUsed";
  public static final String JPG = "jpg";
  public static final String PNG = "png";
  // Image constants
  public static final String IMAGE_1_PNG = "resources/profile pictures/IMAGE_1_PNG.png";
  public static final String IMAGE_1_PNG_NEW = "resources/profile pictures/image_png_new.png";
  public static final String IMAGE_1_JPG = "resources/profile pictures/IMAGE_1_JPG.jpg";
  public static final String IMAGE_1_JPG_NEW = "resources/profile pictures/img_landscape_new.jpg";
  public static final String IMAGE_2_PNG = "resources/profile pictures/IMAGE_2_PNG.png";
  public static final String IMAGE_2_JPG = "resources/profile pictures/IMAGE_2_JPG.jpg";
  // Singleplayer constant names
  public static final String JSON_SINGLEPLAYER_TOTAL_GAMES = "singlePlayerTotalGames";
  public static final String JSON_SINGLEPLAYER_TOTAL_GAMES_WON = "singlePlayerTotalGamesWon";
  public static final String JSON_SINGLEPLAYER_TOTAL_GAMES_LOST = "singlePlayerTotalGamesLost";

  public static final String JSON_SINGLEPLAYER_AND_MULTIPLAYER_TOTAL_GAMETIME =
      "singleAndMultiplayerTotalGameTime";
  public static final String JSON_SINGLEPLAYER_TOTAL_ROUNDS = "singlePlayerTotalRounds";
  public static final String JSON_SINGLEPLAYER_TOTAL_ROUNDS_GRAND = "singlePlayerTotalRoundsGrand";
  public static final String JSON_SINGLEPLAYER_TOTAL_ROUNDS_NULL = "singlePlayerTotalRoundsNull";
  public static final String JSON_SINGLEPLAYER_TOTAL_ROUNDS_SUIT = "singlePlayerTotalRoundsSuit";
  public static final String JSON_SINGLEPLAYER_ROUNDS_WON = "singlePlayerRoundsWon";
  public static final String JSON_SINGLEPLAYER_ROUNDS_WON_GRAND = "singlePlayerRoundsWonGrand";
  public static final String JSON_SINGLEPLAYER_ROUNDS_WON_NULL = "singlePlayerRoundsWonNull";
  public static final String JSON_SINGLEPLAYER_ROUNDS_WON_SUIT = "singlePlayerRoundsWonSuit";
  public static final String JSON_SINGLEPLAYER_ROUNDS_LOST = "singlePlayerRoundsLost";
  public static final String JSON_SINGLEPLAYER_ROUNDS_LOST_GRAND = "singlePlayerRoundsLostGrand";
  public static final String JSON_SINGLEPLAYER_ROUNDS_LOST_NULL = "singlePlayerRoundsLostNull";
  public static final String JSON_SINGLEPLAYER_ROUNDS_LOST_SUIT = "singlePlayerRoundsLostSuit";
  public static final String JSON_SINGLEPLAYER_HIGHEST_SCORE = "singlePlayerHighestScore";
  public static final String JSON_SINGLEPLAYER_LOWEST_SCORE = "singlePlayerLowestScore";
  // Multiplayer constant names
  public static final String JSON_MULTIPLAYER_TOTAL_GAMES = "multiPlayerTotalGames";
  public static final String JSON_MULTIPLAYER_TOTAL_GAMES_LOST = "multiPlayerTotalGamesLost";
  public static final String JSON_MULTIPLAYER_TOTAL_GAMES_WON = "multiPlayerTotalGamesWon";

  public static final String JSON_MULTIPLAYER_TOTAL_GAMETIME = "multiPlayerGameTime";
  public static final String JSON_MULTIPLAYER_TOTAL_ROUNDS = "multiPlayerTotalRounds";
  public static final String JSON_MULTIPLAYER_TOTAL_ROUNDS_GRAND = "multiPlayerTotalRoundsGrand";
  public static final String JSON_MULTIPLAYER_TOTAL_ROUNDS_NULL = "multiPlayerTotalRoundsNull";
  public static final String JSON_MULTIPLAYER_TOTAL_ROUNDS_SUIT = "multiPlayerTotalRoundsSuit";
  public static final String JSON_MULTIPLAYER_ROUNDS_WON = "multiPlayerRoundsWon";
  public static final String JSON_MULTIPLAYER_ROUNDS_WON_GRAND = "multiPlayerRoundsWonGrand";
  public static final String JSON_MULTIPLAYER_ROUNDS_WON_NULL = "multiPlayerRoundsWonNull";
  public static final String JSON_MULTIPLAYER_ROUNDS_WON_SUIT = "multiPlayerRoundsWonSuit";
  public static final String JSON_MULTIPLAYER_ROUNDS_LOST = "multiPlayerRoundsLost";
  public static final String JSON_MULTIPLAYER_ROUNDS_LOST_GRAND = "multiPlayerRoundsLostGrand";
  public static final String JSON_MULTIPLAYER_ROUNDS_LOST_NULL = "multiPlayerRoundsLostNull";
  public static final String JSON_MULTIPLAYER_ROUNDS_LOST_SUIT = "multiPlayerRoundsLostSuit";
  public static final String JSON_MULTIPLAYER_HIGHEST_SCORE = "multiPlayerHighestScore";
  public static final String JSON_MULTIPLAYER_LOWEST_SCORE = "multiPlayerLowestScore";
}
