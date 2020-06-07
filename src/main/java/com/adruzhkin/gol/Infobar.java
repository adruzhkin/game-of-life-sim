package com.adruzhkin.gol;

import com.adruzhkin.gol.model.CellState;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class Infobar extends HBox {

    private static String drawModeFormat = "Draw mode: %s";
    private static String cursorPositionFormat = "Cursor: (%d, %d)";

    private Label cursorPosition;
    private Label editingTool;

    public Infobar() {
        this.cursorPosition = new Label();
        this.editingTool = new Label();

        //Set spacer to fill the space between editingTool and cursorPosition
        Pane spacer = new Pane();
        spacer.setMinSize(0, 0); //shrink to nothing
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); //shrink to all available space
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(this.editingTool, spacer, this.cursorPosition);
    }

    public void setDrawMode(CellState drawMode) {
        String drawModeString;
        if (drawMode == CellState.ALIVE) {
            drawModeString = "Drawing";
        } else {
            drawModeString = "Erasing";
        }

        this.editingTool.setText(String.format(drawModeFormat, drawModeString));
    }

    public void setCursorPosition(int x, int y) {
        this.cursorPosition.setText(String.format(cursorPositionFormat, x, y));
    }

}
