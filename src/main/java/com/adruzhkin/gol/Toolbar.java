package com.adruzhkin.gol;

import com.adruzhkin.gol.model.CellState;
import com.adruzhkin.gol.viewmodel.ApplicationState;
import com.adruzhkin.gol.viewmodel.ApplicationViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private MainView mainView;
    private ApplicationViewModel applicationViewModel;
    private Simulator simulator;

    public Toolbar(MainView mainView, ApplicationViewModel applicationViewModel) {
        this.mainView = mainView;
        this.applicationViewModel = applicationViewModel;

        Button draw = new Button("Draw");
        draw.setOnAction(this::handleDraw);

        Button erase = new Button("Erase");
        erase.setOnAction(this::handleErase);

        Button step = new Button("Step");
        step.setOnAction(this::handleStep);

        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);

        Button start = new Button("Start");
        start.setOnAction(this::handleStart);

        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);

        this.getItems().addAll(draw, erase, reset, step, start, stop);
    }

    private void handleDraw(ActionEvent actionEvent) {
        System.out.println("Draw pressed");
        this.mainView.setDrawMode(CellState.ALIVE);
    }

    private void handleErase(ActionEvent actionEvent) {
        System.out.println("Erase pressed");
        this.mainView.setDrawMode(CellState.DEAD);
    }

    private void handleStep(ActionEvent actionEvent) {
        System.out.println("Step pressed");
        this.switchToSimulatingState();
        this.mainView.getSimulation().step();
        this.mainView.draw();
    }

    private void handleReset(ActionEvent actionEvent) {
        System.out.println("Reset pressed");
        this.applicationViewModel.setCurrentState(ApplicationState.EDITING);
        this.simulator = null;
        this.mainView.draw();
    }

    private void handleStart(ActionEvent actionEvent) {
        this.switchToSimulatingState();
        this.simulator.start();
    }

    private void handleStop(ActionEvent actionEvent) {
        if (this.applicationViewModel.getCurrentState() == ApplicationState.SIMULATING) {
            this.simulator.stop();
        }
    }

    private void switchToSimulatingState() {
        this.applicationViewModel.setCurrentState(ApplicationState.SIMULATING);
        this.simulator = new Simulator(this.mainView, this.mainView.getSimulation());
    }

}
