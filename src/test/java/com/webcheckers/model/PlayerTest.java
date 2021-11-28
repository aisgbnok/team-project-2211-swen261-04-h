package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class PlayerTest {

    //TODO: Test getID, but it's random so I'm confused on what to check for
    @Test
    void testmakePlayer(){
        Player player = new Player("Test");
        assertNotNull(player);
    }

    @Test
    void testgetName(){
        Player player = new Player("Bob");
        assertEquals("Bob", player.getName(), "getName failed to return correct name");
        assertNotEquals("Jimmy", player.getName());
    }

    @Test
    void testInGame(){
        Player player = new Player("Bob");
        assertFalse(player.inGame());
        player.inGame(true);
        assertTrue(player.inGame());
    }

    @Test
    void testequals(){
        Player player1 = new Player("Bob");
        Player player2 = new Player("Tom");
        assertTrue(player1.equals(player1), "Player is not equivalent to itself");
        assertFalse(player1.equals(player2), "Player should not equal a different player");
    }
}
