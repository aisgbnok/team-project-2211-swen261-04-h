package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The GameCenter application for holding and tracking all games on the server over its lifetime.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class GameCenter {

  private final HashMap<UUID, Game> games;

  /** Constructs a new GameCenter used for holding and tracking all games. */
  public GameCenter() {
    this.games = new HashMap<>();
  }

  /**
   * Creates a new game and registers it with the GameCenter.
   *
   * @param redPlayer Starting player with the RED pieces
   * @param whitePlayer Opponent player with the WHITE pieces
   */
  public void newGame(Player redPlayer, Player whitePlayer) {
    Game newGame = new Game(redPlayer, whitePlayer);
    games.put(newGame.getGameID(), newGame);
  }

  /**
   * Finds a game in GameCenter using gameID lookup
   *
   * @param gameID Game ID used to find Game.
   * @return Game with the matching gameID, or null if it doesn't exist.
   */
  public Game getGame(UUID gameID) {
    return games.get(gameID);
  }

  // TODO try not to use this one, should be phased out.
  /**
   * Finds a game in GameCenter using player lookup.
   *
   * @param player Player used to find Game.
   * @return Game with the matching player, or null if it doesn't exist.
   */
  public Game getGame(Player player) {
    for (Map.Entry<UUID, Game> game : games.entrySet()) {
      if (game.getValue().hasPlayer(player)) {
        return game.getValue();
      }
    }
    return null;
  }
}
