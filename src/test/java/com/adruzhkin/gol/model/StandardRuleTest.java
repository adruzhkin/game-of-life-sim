package com.adruzhkin.gol.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardRuleTest {

    private Board board;
    private SimulationRule simulationRule;

    @BeforeEach
    void setUp() {
        this.board = new BoundedBoard(3, 3);
        this.simulationRule = new StandardRule();
    }

    @Test
    void getNextState_alive_noNeighbours() {
        this.board.setState(1, 1, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, this.board);
        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_alive_oneNeighbour() {
        this.board.setState(1, 1, CellState.ALIVE);
        this.board.setState(0, 0, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, this.board);
        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_alive_twoNeighbours() {
        this.board.setState(1, 1, CellState.ALIVE);
        this.board.setState(0, 0, CellState.ALIVE);
        this.board.setState(0, 1, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, this.board);
        assertEquals(CellState.ALIVE, nextState);
    }

    @Test
    void getNextState_alive_threeNeighbours() {
        this.board.setState(1, 1, CellState.ALIVE);
        this.board.setState(0, 0, CellState.ALIVE);
        this.board.setState(0, 1, CellState.ALIVE);
        this.board.setState(0, 2, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, this.board);
        assertEquals(CellState.ALIVE, nextState);
    }

    @Test
    void getNextState_alive_fourNeighbours() {
        this.board.setState(1, 1, CellState.ALIVE);
        this.board.setState(0, 0, CellState.ALIVE);
        this.board.setState(0, 1, CellState.ALIVE);
        this.board.setState(0, 2, CellState.ALIVE);
        this.board.setState(1, 0, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, this.board);
        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_alive_eightNeighbours() {
        this.board.setState(1, 1, CellState.ALIVE);
        this.board.setState(0, 0, CellState.ALIVE);
        this.board.setState(0, 1, CellState.ALIVE);
        this.board.setState(0, 2, CellState.ALIVE);
        this.board.setState(1, 0, CellState.ALIVE);
        this.board.setState(1, 2, CellState.ALIVE);
        this.board.setState(2, 0, CellState.ALIVE);
        this.board.setState(2, 1, CellState.ALIVE);
        this.board.setState(2, 2, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, this.board);
        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_dead_noNeighbours() {
        this.board.setState(1, 1, CellState.DEAD);

        CellState nextState = simulationRule.getNextState(1, 1, this.board);
        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_dead_twoNeighbours() {
        this.board.setState(1, 1, CellState.DEAD);
        this.board.setState(0, 0, CellState.ALIVE);
        this.board.setState(0, 1, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, this.board);
        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_dead_threeNeighbours() {
        this.board.setState(1, 1, CellState.DEAD);
        this.board.setState(0, 0, CellState.ALIVE);
        this.board.setState(0, 1, CellState.ALIVE);
        this.board.setState(0, 2, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, this.board);
        assertEquals(CellState.ALIVE, nextState);
    }

    @Test
    void getNextState_dead_fourNeighbours() {
        this.board.setState(1, 1, CellState.DEAD);
        this.board.setState(0, 0, CellState.ALIVE);
        this.board.setState(0, 1, CellState.ALIVE);
        this.board.setState(0, 2, CellState.ALIVE);
        this.board.setState(1, 0, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, this.board);
        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_dead_eightNeighbours() {
        this.board.setState(1, 1, CellState.DEAD);
        this.board.setState(0, 0, CellState.ALIVE);
        this.board.setState(0, 1, CellState.ALIVE);
        this.board.setState(0, 2, CellState.ALIVE);
        this.board.setState(1, 0, CellState.ALIVE);
        this.board.setState(1, 2, CellState.ALIVE);
        this.board.setState(2, 0, CellState.ALIVE);
        this.board.setState(2, 1, CellState.ALIVE);
        this.board.setState(2, 2, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, this.board);
        assertEquals(CellState.DEAD, nextState);
    }
}