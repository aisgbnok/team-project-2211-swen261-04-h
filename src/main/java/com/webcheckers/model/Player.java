package com.webcheckers.model;

import java.util.Objects;

/**
 * The Player model.
 *
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Player {

  private String name;
  // private String type;

  /**
   * Player Constructor
   *
   * @param name Player username
   */
  public Player(String name) {
    this.name = name;
  }

  // TODO: Should the player name be mutable? Any reason to allow it to change?
  /**
   * Sets the player username
   *
   * @param name Player username
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the player username
   *
   * @return Player username
   */
  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Player)) return false;
    Player player = (Player) o;
    return Objects.equals(getName(), player.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }
}
