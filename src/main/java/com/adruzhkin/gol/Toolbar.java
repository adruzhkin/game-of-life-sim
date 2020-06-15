package com.adruzhkin.gol;

import com.adruzhkin.gol.model.CellState;
import com.adruzhkin.gol.util.event.EventBus;
import com.adruzhkin.gol.viewmodel.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private EditorViewModel editorViewModel;
    private EventBus eventBus;

    public Toolbar(EditorViewModel editorViewModel, EventBus eventBus) {

        this.editorViewModel = editorViewModel;
        this.eventBus = eventBus;

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
        this.editorViewModel.getDrawMode().set(CellState.ALIVE);
    }

    private void handleErase(ActionEvent actionEvent) {
        System.out.println("Erase pressed");
        this.editorViewModel.getDrawMode().set(CellState.DEAD);
    }

    private void handleStep(ActionEvent actionEvent) {
        System.out.println("Step pressed");
        this.eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.STEP));
    }

    private void handleReset(ActionEvent actionEvent) {
        System.out.println("Reset pressed");
        this.eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.RESET));
    }

    private void handleStart(ActionEvent actionEvent) {
        System.out.println("Start pressed");
        this.eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.START));
    }

    private void handleStop(ActionEvent actionEvent) {
        System.out.println("Stop pressed");
        this.eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.STOP));
    }
}
