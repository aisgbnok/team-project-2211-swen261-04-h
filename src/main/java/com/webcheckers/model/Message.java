package com.webcheckers.model;

import java.util.logging.Logger;

/**
 * A UI-friendly representation of a message to the user.
 *
 * <p>This is a <a href='https://en.wikipedia.org/wiki/Domain-driven_design'>DDD</a> <a
 * href='https://en.wikipedia.org/wiki/Value_object'>Value Object</a>. This implementation is
 * immutable and also supports a JSON representation.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public final class Message {
  private static final Logger LOG = Logger.getLogger(Message.class.getName());

  //
  // Static Factory methods
  //
  private final String text;
  private final Type type;

  //
  // Inner Types
  //

  /**
   * Create a new message.
   *
   * @param message the text of the message
   * @param type the type of message
   */
  private Message(final String message, final Type type) {
    this.text = message;
    this.type = type;
    LOG.finer(this + " created.");
  }

  //
  // Attributes
  //

  /**
   * A static helper method to create new error messages.
   *
   * @param message the text of the message
   * @return a new {@link Message}
   */
  public static Message error(final String message) {
    return new Message(message, Type.ERROR);
  }

  /**
   * A static helper method to create new informational messages.
   *
   * @param message the text of the message
   * @return a new {@link Message}
   */
  public static Message info(final String message) {
    return new Message(message, Type.INFO);
  }

  //
  // Constructor
  //

  /** Get the text of the message. */
  public String getText() {
    return text;
  }

  //
  // Public methods
  //

  /** Get the type of the message. */
  public Type getType() {
    return type;
  }

  /**
   * Query whether this message was generated from a successful action; ie, not an {@link
   * Type#ERROR}.
   *
   * @return true if not an error
   */
  public boolean isSuccessful() {
    return !type.equals(Type.ERROR);
  }

  @Override
  public String toString() {
    return "{Msg " + type + " '" + text + "'}";
  }

  //
  // Object methods
  //

  /** The type of {@link Message}; either information or an error. */
  public enum Type {
    INFO,
    ERROR
  }
}
