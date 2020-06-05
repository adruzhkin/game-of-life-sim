package com.adruzhkin;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainView extends VBox {

    private Button stepButton;
    private Canvas canvas;

    public MainView() {
        this.stepButton = new Button("Step");
        this.canvas = new Canvas(400, 400);

        this.getChildren().addAll(this.stepButton, this.canvas);
    }



}
