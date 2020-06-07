package com.adruzhkin.gol.model;

public class StandardRule implements SimulationRule {
    @Override
    public CellState getNextState(int x, int y, Board board) {
        int aliveNeighbours = countAliveNeighbours(x, y, board);

        //Check if CellState needs to be updated
        if (board.getState(x, y) == CellState.ALIVE) {
            if (aliveNeighbours < 2 || aliveNeighbours > 3) return CellState.DEAD;
        } else {
            if (aliveNeighbours == 3) return CellState.ALIVE;
        }

        //If not, then return current state unchanged
        return board.getState(x, y);
    }

    public int countAliveNeighbours(int x, int y, Board board) {
        int count = 0;

        count += board.getState(x - 1, y - 1).ordinal();
        count += board.getState(x, y - 1).ordinal();
        count += board.getState(x + 1, y - 1).ordinal();

        count += board.getState(x - 1, y).ordinal();
        count += board.getState(x + 1, y).ordinal();

        count += board.getState(x - 1, y + 1).ordinal();
        count += board.getState(x, y + 1).ordinal();
        count += board.getState(x + 1, y + 1).ordinal();

        return count;
    }

}
