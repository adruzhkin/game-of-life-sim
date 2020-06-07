package com.adruzhkin.gol;

import com.adruzhkin.gol.model.Board;
import com.adruzhkin.gol.model.CellState;
import com.adruzhkin.gol.model.SimulationRule;

public class Simulation {

    private Board simulationBoard;
    private SimulationRule simulationRule;

    public Simulation(Board simulationBoard, SimulationRule simulationRule) {
        this.simulationBoard = simulationBoard;
        this.simulationRule = simulationRule;
    }

    public Board getBoard() {
        return simulationBoard;
    }

    public void step() {
        Board nextBoard = this.simulationBoard.copy();

        for (int y = 0; y < simulationBoard.getHeight(); y++) {
            for (int x = 0; x < simulationBoard.getWidth(); x++) {
                CellState nextState = simulationRule.getNextState(x, y, simulationBoard);
                nextBoard.setState(x, y, nextState);
            }
        }

        this.simulationBoard = nextBoard;
    }

}
