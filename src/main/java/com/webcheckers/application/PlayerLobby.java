package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.ArrayList;

/**
 * The application PlayerLobby. PlayerLobby keeps track of the server's players.
 *
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class PlayerLobby {
  // ArrayList used for storing players
  private static ArrayList<Player> players;

  /** Initialize the players ArrayList. */
  public static void initPlayers() {
    players = new ArrayList<>();
  }

  /**
   * Sets the players ArrayList to a given ArrayList of Players.
   *
   * @param players ArrayList of Players.
   */
  public static void setPlayers(ArrayList<Player> players) {
    PlayerLobby.players = players;
  }

  /**
   * Returns all Players.
   *
   * @return ArrayList containing all Players in PlayerLobby.
   */
  public static ArrayList<Player> getPlayers() {
    return players;
  }

  // TODO: Should username validation checking be handled here or in PostSignInRoute?

  /**
   * Adds a Player to the PlayerLobby.
   *
   * @param player Player to be added to PlayerLobby.
   */
  public static void addPlayer(Player player) {
    players.add(player);
  }

  /**
   * Removes a Player from the PlayerLobby.
   *
   * @param player Player to be removed from PlayerLobby.
   */
  public static void removePlayer(Player player) {
    players.remove(player);
  }

  /**
   * Gets a specific Player based on the given Player name from PlayerLobby and returns it.
   *
   * @param name Player name used to find Player.
   * @return Player with matching name or null if Player not found.
   */
  public static Player getPlayer(String name) {
    for (Player player : players) {
      if (player.getName().equals(name)) {
        return player;
      }
    }
    return null;
  }

  /**
   * Determines if a Player is in the PlayerLobby or not.
   *
   * @param name Player name used to find Player.
   * @return Whether Player with matching name exists or not. True or False respectively.
   */
  public static boolean contains(String name) {
    for (Player player : players) {
      if (player.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * The number of Players in the PlayerLobby.
   *
   * @return Number of Players in the PlayerLobby.
   */
  public static int size() {
    return players.size();
  }
}
