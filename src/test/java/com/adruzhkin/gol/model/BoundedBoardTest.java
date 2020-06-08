package com.adruzhkin.gol.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundedBoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        this.board = new BoundedBoard(5, 3);
    }

    @Test
    void copy_sameSizeAsOriginal() {
        Board copy = this.board.copy();

        assertEquals(5, copy.getWidth());
        assertEquals(3, copy.getHeight());
    }

    @Test
    void copy_deepCopy() {
        Board copy = this.board.copy();
        copy.setState(3, 2, CellState.ALIVE);

        assertEquals(CellState.ALIVE, copy.getState(3, 2));
        assertEquals(CellState.DEAD, this.board.getState(3, 2));
    }

    @Test
    void copy_contentsAreTheSame() {
        this.board.setState(0, 0, CellState.ALIVE);
        this.board.setState(0, 1, CellState.ALIVE);
        this.board.setState(0, 2, CellState.ALIVE);

        Board copy = this.board.copy();

        for (int x = 0; x < copy.getWidth(); x++) {
            for (int y = 0; y < copy.getHeight(); y++) {
                assertEquals(this.board.getState(x, y), copy.getState(x, y));
            }
        }
    }

    @Test
    void setState_doNotFailOutOfBounds() {
        this.board.setState(-1, 0, CellState.ALIVE);
        this.board.setState(5, 0, CellState.ALIVE);
        this.board.setState(0, -1, CellState.ALIVE);
        this.board.setState(0, 3, CellState.ALIVE);
    }

    @Test
    void getState_doNotFailOutOfBounds() {
        this.board.getState(-1, 0);
        this.board.getState(5, 0);
        this.board.getState(0, -1);
        this.board.getState(0, 3);
    }

    @Test
    void getState_returnsUpdatedResult() {
        this.board.setState(4, 1, CellState.ALIVE);

        assertEquals(CellState.ALIVE, this.board.getState(4, 1));
    }
}