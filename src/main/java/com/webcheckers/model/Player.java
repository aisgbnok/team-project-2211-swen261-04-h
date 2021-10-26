package com.webcheckers.model;

import java.util.Objects;

/**
 * The Player model used for instantiating players and holding their data.
 *
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Player {

  private final String name;
  private boolean inGame;

  /**
   * Player constructor sets the player name and sets inGame to false.
   *
   * @param name Player username.
   */
  public Player(String name) {
    this.name = name;
    this.inGame = false;
  }

  /**
   * Gets the player's name.
   *
   * @return Player username.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets whether the player is in a game or not.
   *
   * @param inGame Boolean used to set whether player is in a game or not.
   */
  public void setGame(boolean inGame) {
    this.inGame = inGame;
  }

  /**
   * Returns value of inGame;
   *
   * @return True if the player is in a game, or false if they aren't.
   */
  public boolean inGame() {
    return this.inGame;
  }

  /**
   * Determines if the provided Player instance is equal to this Player instance.
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
   * Generates Player hashcode.
   *
   * @return Player hashcode.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }
}
