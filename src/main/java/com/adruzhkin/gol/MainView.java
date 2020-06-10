package com.adruzhkin.gol;

import com.adruzhkin.gol.model.CellState;
import com.adruzhkin.gol.view.SimulationCanvas;
import com.adruzhkin.gol.viewmodel.ApplicationViewModel;
import com.adruzhkin.gol.viewmodel.BoardViewModel;
import com.adruzhkin.gol.viewmodel.EditorViewModel;
import com.adruzhkin.gol.viewmodel.SimulationViewModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainView extends VBox {

    private Infobar infobar;

    private EditorViewModel editorViewModel;

    public MainView(ApplicationViewModel appViewModel, BoardViewModel boardViewModel, EditorViewModel editorViewModel,
                    SimulationViewModel simulationViewModel) {

        this.editorViewModel = editorViewModel;

        //Set a key listener on the entire MainView, not just the canvas itself
        this.setOnKeyPressed(this::onKeyPressed);

        SimulationCanvas simulationCanvas = new SimulationCanvas(editorViewModel, boardViewModel);
        VBox.setVgrow(simulationCanvas, Priority.ALWAYS);

        Toolbar toolbar = new Toolbar(editorViewModel, appViewModel, simulationViewModel);

        this.infobar = new Infobar(editorViewModel);
        this.infobar.setCursorPosition(0, 0);

        //Set spacer to fill the space between canvas and infobar
        Pane spacer = new Pane();
        spacer.setMinSize(0, 0); //shrink to nothing
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); //shrink to all available space
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(toolbar, simulationCanvas, spacer, this.infobar);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            //Will draw live cells (draw mode)
            this.editorViewModel.setDrawMode(CellState.ALIVE);
        }

        if (keyEvent.getCode() == KeyCode.E) {
            //Will draw dead cells (erase mode)
            this.editorViewModel.setDrawMode(CellState.DEAD);
        }
    }

}
