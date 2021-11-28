package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class RowTest {
    @Test
    void testMakeRows(){
        Row row = new Row(0);
        assertNotNull(row);
        Row row2 = new Row(1, Color.WHITE);
        assertNotNull(row2);
    }

    @Test
    void testGetIndex(){
        Row row = new Row(0);
        assertEquals(0, row.getIndex());
    }

    @Test
    void testGetSpace(){
        Row row = new Row(1, Color.WHITE);
        Space space = new Space(0, true, Color.WHITE);
        assertEquals(space.isValid(), row.getSpace(1).isValid());
    }
}
