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

  /**
   * Player constructor sets the player name.
   *
   * @param name Player username.
   */
  public Player(String name) {
    this.name = name;
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
   * Determiens if the provided Player instance is equal to this Player instance.
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
