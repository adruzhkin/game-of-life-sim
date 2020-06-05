package com.adruzhkin;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    private Button stepButton;
    private Canvas canvas;

    private Affine affine;

    private Simulation simulation;

    public MainView() {
        this.simulation = new Simulation(10, 10);

        this.stepButton = new Button("Step");
        this.stepButton.setOnAction(actionEvent -> {
            this.simulation.step();
            this.draw();
        });

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);

        this.getChildren().addAll(this.stepButton, this.canvas);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);
    }

    private void handleDraw(MouseEvent mouseEvent) {
        //Get mouse coordinates
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        //Transform mouse coordinates into simulation coordinates
        try {
            Point2D simulationCoordinates = this.affine.inverseTransform(mouseX, mouseY);

            int simX = (int) simulationCoordinates.getX();
            int simY = (int) simulationCoordinates.getY();

            System.out.println(simX + ", " + simY);

            this.simulation.setAlive(simX, simY);
            this.draw();

        } catch (NonInvertibleTransformException e) {
            System.out.println("Error: can not invert transform");
        }
    }

    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGREY);
        g.fillRect(0, 0, 400, 400);

        g.setFill(Color.BLACK);
        for (int x = 0; x < this.simulation.getWidth(); x++) {
            for (int y = 0; y < this.simulation.getHeight(); y++) {
                if (this.simulation.getState(x, y) == 1) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }

        //Draw the grid
        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= this.simulation.getWidth(); x++) {
            g.strokeLine(x, 0, x, 10);
        }

        for (int y = 0; y <= this.simulation.getHeight(); y++) {
            g.strokeLine(0, y, 10, y);
        }

    }

}
