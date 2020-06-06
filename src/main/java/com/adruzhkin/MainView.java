package com.adruzhkin;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    private Canvas canvas;
    private Affine affine;

    private Simulation simulation;

    private int drawMode = Simulation.ALIVE; //Default mode

    public MainView() {
        this.simulation = new Simulation(10, 10);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);

        //Set a key listener on the entire MainView, not just the canvas itself
        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(this);

        this.getChildren().addAll(toolbar, this.canvas);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);
    }

    public Simulation getSimulation() {
        return this.simulation;
    }

    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            //Will draw live cells (draw mode)
            this.drawMode = Simulation.ALIVE;
        }

        if (keyEvent.getCode() == KeyCode.E) {
            //Will draw dead cells (erase mode)
            this.drawMode = Simulation.DEAD;
        }
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

            //Update the state of the corresponding cell in Simulation
            this.simulation.setState(simX, simY, this.drawMode);
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
                if (this.simulation.getState(x, y) == Simulation.ALIVE) {
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
