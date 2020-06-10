package com.adruzhkin.gol;

import com.adruzhkin.gol.model.CellState;
import com.adruzhkin.gol.viewmodel.EditorViewModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {

    private EditorViewModel editorViewModel;

    public MainView(EditorViewModel editorViewModel) {

        this.editorViewModel = editorViewModel;

        //Set a key listener on the entire MainView, not just the canvas itself
        this.setOnKeyPressed(this::onKeyPressed);
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
