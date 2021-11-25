package com.webcheckers.application;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link GameCenter} component.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
@Tag("Application-tier")
public class GameCenterTest {

  @BeforeAll
  static void Initialization() {
    // Initialize GameCenter
    GameCenter.initGames();

    // Get games list
    final ArrayList games = GameCenter.getGames();

    // Get starting size
    final int initGameAmount = GameCenter.size();

    // Games list should not be null
    assertNotNull(games, "GameCenter didn't initialize game list!");

    // Games list size should be 0
    assertEquals(initGameAmount, 0);
  }

  @Test
  void AddGame() {
    // Get starting size
    final int initGameAmount = GameCenter.size();

    // Create players for Game
    final Player red = new Player("Red");
    final Player white = new Player("White");

    // Create new game
    Game game = new Game(red, white);

    // Game should not be null
    assertNotNull(game);

    // Add game to GameCenter
    GameCenter.addGame(game);

    // Get new size
    final int addGameAmount = GameCenter.size();

    // New size should be larger than starting size
    assertTrue(addGameAmount > initGameAmount);
  }

  @Test
  void RemoveGame() {
    // Get starting size
    final int initGameAmount = GameCenter.size();

    // Create new games
    final Game game1 = new Game(new Player("Red"), new Player("White"));
    final Game game2 = new Game(new Player("Linda"), new Player("Zack"));
    final Game game3 = new Game(new Player("Mark"), new Player("Carol"));
    final Game game4 = new Game(new Player("Bill"), new Player("Bob"));

    // Games should not be null
    assertNotNull(game1, "Game1 is null");
    assertNotNull(game2, "Game2 is null");
    assertNotNull(game3, "Game3 is null");
    assertNotNull(game4, "Game4 is null");

    // Add games to GameCenter
    GameCenter.addGame(game1);
    GameCenter.addGame(game2);
    GameCenter.addGame(game3);
    GameCenter.addGame(game4);

    // Get new size
    final int addGameAmount = GameCenter.size();

    // We can get game3ID and game3
    final UUID game3ID = game3.getGameID();
    final Game game3GameCenter = GameCenter.getGame(game3ID);
    assertNotNull(game3ID);
    assertNotNull(game3GameCenter);

    // Remove Game
    GameCenter.removeGame(game3GameCenter);

    // Game 3 should now be removed from GameCenter
    assertNull(GameCenter.getGame(game3.getGameID()));

    // Get new size
    final int removeGameAmount = GameCenter.size();

    // New size should be larger than starting size
    assertEquals(removeGameAmount, (addGameAmount - 1));
  }
}
