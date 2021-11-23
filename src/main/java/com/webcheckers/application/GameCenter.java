package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The application GameCenter. GameCenter keeps track of the server's games.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class GameCenter {

  private ArrayList<Game> games;

  public GameCenter() {
    this.games = new ArrayList<>();
  }

  /**
   * Creates a new game and registers it with the GameCenter
   *
   * @param redPlayer Starting player with the RED pieces
   * @param whitePlayer Opponent player with the WHITE pieces
   */
  public void newGame(Player redPlayer, Player whitePlayer) {
    games.add(new Game(redPlayer, whitePlayer));
  }

  /**
   * Finds a game in GameCenter using gameID lookup
   *
   * @param gameID Game ID used to find Game.
   * @return Game with the matching gameID, or null if it doesn't exist.
   */
  public Game getGame(UUID gameID) {
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
  public Game getGame(Player player) {
    for (Game game : games) {
      if (game.hasPlayer(player)) {
        return game;
      }
    }
    return null;
  }

  /**
   * The number of Games in the GameCenter.
   *
   * @return Number of Games in the GameCenter.
   */
  public int size() {
    return games.size();
  }
}
