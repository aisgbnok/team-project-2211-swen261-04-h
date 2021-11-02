package com.webcheckers.model;

import java.util.Objects;

/**
 * The Player model used for instantiating players and holding their data.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 */
public class Player {

  private final String name;
  private boolean inGame;

  /**
   * Creates a new Player. Sets the player name to the given name, and initializes inGame as false.
   *
   * @param name Player username
   */
  public Player(String name) {
    this.name = name;
    this.inGame = false;
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
    return Objects.equals(getName(), player.getName());
  }

  /**
   * Generates {@link Player} hashcode.
   *
   * @return Player hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }
}
