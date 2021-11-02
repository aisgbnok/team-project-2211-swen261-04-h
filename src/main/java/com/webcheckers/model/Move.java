package com.webcheckers.model;

/**
 * This is the Move data type.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Move {
  // Fields
  private final Space start;
  private final Space end;

  /**
   * Constructor for Move that takes in a start and an end space.
   * @param start Starting space
   * @param end Ending space
   */
  public Move(Space start, Space end) {
    this.start = start;
    this.end = end;
  }

  /**
   * Getter for starting space
   * @return Starting space
   */
  public Space getStart() {
    return start;
  }

  /**
   * Getter for ending space
   * @return Ending space
   */
  public Space getEnd() {
    return end;
  }
}
