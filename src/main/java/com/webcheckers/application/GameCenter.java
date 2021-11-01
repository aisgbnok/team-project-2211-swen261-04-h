package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.ArrayList;

/**
 * The application GameCenter. GameCenter keeps track of the server's games.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class GameCenter {

  // ArrayList used for storing games
  private static ArrayList<Game> games;

  /** Initialize the games ArrayList. */
  public static void initGames() {
    games = new ArrayList<>();
  }

  /**
   * Returns all Games.
   *
   * @return ArrayList containing all Games in GameCenter.
   */
  public static ArrayList<Game> getGames() {
    return games;
  }

  /**
   * Adds a Game to the GameCenter.
   *
   * @param game Game to be added to GameCenter.
   */
  public static void addGame(Game game) {
    games.add(game);
  }

  /**
   * Removes a Game from the GameCenter.
   *
   * @param game Game to be removed from GameCenter.
   */
  public static void removeGame(Game game) {
    games.remove(game);
  }

  /**
   * Gets a specific Game from GameCenter using the given gameID.
   *
   * @param gameID Game ID used to find Game.
   * @return Game with matching gameID or null if gameID not found.
   */
  public static Game getGame(int gameID) {
    for (Game game : games) {
      if (game.getGameID() == gameID) {
        return game;
      }
    }
    return null;
  }

  /**
   * Finds a game in GameCenter using gameID lookup
   *
   * @param gameID Game ID used to find Game.
   * @return Game with the matching gameID, or null if it doesn't exist.
   */
  public static Game findGame(String gameID) {
    for (Game game : games) {
      if (game.getGameID().equals(gameID)) {
        return game;
      }
    }
    return null;
  }

  /**
   * Finds a game in GameCenter using player lookup.
   *
   * @param player Player used to find Game.
   * @return Game with the matching player, or null if it doesn't exist.
   */
  public static Game findGame(Player player) {
    for (Game game : games) {
      if (game.hasPlayer(player)) {
        return game;
      }
    }
    return null;
  }

  /**
   * The number of Players in the GameCenter.
   *
   * @return Number of Players in the GameCenter.
   */
  public static int size() {
    return games.size();
  }
}
