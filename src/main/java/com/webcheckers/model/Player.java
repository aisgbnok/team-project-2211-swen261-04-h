package com.webcheckers.model;

import java.util.UUID;

/**
 * Player representation used for holding their information.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 */
public class Player {

  private final UUID playerID; // Player Identifier
  private final String name; // Player's name
  private boolean inGame; // Player is in a game or not

  /**
   * Constructs a Player given name, generates a player ID, and sets inGame to false.
   *
   * @param name Player username
   */
  public Player(String name) {
    this.playerID = UUID.randomUUID(); // Generate random player ID
    this.name = name;
    this.inGame = false;
  }

  /**
   * Getter for the playerID.
   *
   * @return The player's ID in UUIDv4.
   */
  public UUID getPlayerID() {
    return playerID;
  }

  /**
   * Get the name of the {@link Player}.
   *
   * @return Player username
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the {@link Player} {@code inGame} status.
   *
   * @param inGame Boolean used to set whether player is in a game or not.
   */
  public void inGame(boolean inGame) {
    this.inGame = inGame;
  }

  /**
   * Returns value of {@code inGame}.
   *
   * @return True if the player is in a game, false if they aren't.
   */
  public boolean inGame() {
    return this.inGame;
  }

  /**
   * Determines if the provided {@link Player} instance is equal to this {@link Player} instance.
   *
   * @param o Player to compare equality.
   * @return True if the two Players are equal, or false if they are not equal.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Player)) return false;
    Player player = (Player) o;
    return this.playerID.equals(player.playerID) && this.name.equals(player.name);
  }
}
