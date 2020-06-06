package com.adruzhkin;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class Infobar extends HBox {

    private Label cursorPosition;
    private Label editingTool;

    public Infobar() {
        this.cursorPosition = new Label("Cursor: (0, 0)");
        this.editingTool = new Label("Draw mode: Drawing");

        //Set spacer to fill the space between editingTool and cursorPosition
        Pane spacer = new Pane();
        spacer.setMinSize(0, 0); //shrink to nothing
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); //shrink to all available space
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(this.editingTool, spacer, this.cursorPosition);
    }
}
