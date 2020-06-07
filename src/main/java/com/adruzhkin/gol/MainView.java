package com.adruzhkin.gol;

import com.adruzhkin.gol.model.Board;
import com.adruzhkin.gol.model.BoundedBoard;
import com.adruzhkin.gol.model.CellState;
import com.adruzhkin.gol.model.StandardRule;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    public static final int EDITING = 0;
    public static final int SIMULATING = 1;

    private Infobar infobar;
    private Canvas canvas;
    private Affine affine;

    private Simulation simulation;
    private Board initialBoard;

    private CellState drawMode = CellState.ALIVE; //Default mode
    private int applicationState = MainView.EDITING; //Default app state

    public MainView() {
        this.initialBoard = new BoundedBoard(10, 10);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);

        //Set a key listener on the entire MainView, not just the canvas itself
        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(this);
        this.infobar = new Infobar();
        this.infobar.setDrawMode(this.drawMode);
        this.infobar.setCursorPosition(0, 0);

        //Set spacer to fill the space between canvas and infobar
        Pane spacer = new Pane();
        spacer.setMinSize(0, 0); //shrink to nothing
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); //shrink to all available space
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(toolbar, this.canvas, spacer, this.infobar);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);
    }

    public Simulation getSimulation() {
        return this.simulation;
    }

    public void setDrawMode(CellState drawMode) {
        this.drawMode = drawMode;
        this.infobar.setDrawMode(drawMode);
    }

    public int getApplicationState() {
        return this.applicationState;
    }

    public void setApplicationState(int applicationState) {
        //Do nothing if current app state is the same as the new one
        if (this.applicationState == applicationState) return;

        if (applicationState == MainView.SIMULATING) {
            this.simulation = new Simulation(this.initialBoard, new StandardRule());
        }

        this.applicationState = applicationState;
        System.out.println("Application state: " + applicationState);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            //Will draw live cells (draw mode)
            this.drawMode = CellState.ALIVE;
        }

        if (keyEvent.getCode() == KeyCode.E) {
            //Will draw dead cells (erase mode)
            this.drawMode = CellState.DEAD;
        }
    }

    private void handleMoved(MouseEvent mouseEvent) {
        Point2D simulationCoordinates = this.getSimulationCoordinates(mouseEvent);
        this.infobar.setCursorPosition((int) simulationCoordinates.getX(), (int) simulationCoordinates.getY());
    }

    private void handleDraw(MouseEvent mouseEvent) {
        if (this.applicationState == MainView.SIMULATING) return;

        Point2D simulationCoordinates = getSimulationCoordinates(mouseEvent);

        int simX = (int) simulationCoordinates.getX();
        int simY = (int) simulationCoordinates.getY();

        System.out.println(simX + ", " + simY);

        //Update the state of the corresponding cell in Simulation
        this.initialBoard.setState(simX, simY, this.drawMode);
        this.draw();
    }

    private Point2D getSimulationCoordinates(MouseEvent mouseEvent) {
        //Get mouse coordinates
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        //Transform mouse coordinates into simulation coordinates
        try {
            Point2D simulationCoordinates = this.affine.inverseTransform(mouseX, mouseY);
            return simulationCoordinates;
        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException("Non invertible transform");
        }
    }

    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        //Fill the canvas
        g.setFill(Color.LIGHTGREY);
        g.fillRect(0, 0, 400, 400);

        //Draw simulation cells
        if (this.applicationState == MainView.EDITING) {
            drawSimulation(this.initialBoard);
        } else {
            drawSimulation(this.simulation.getBoard());
        }

        //Draw the grid
        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= this.initialBoard.getWidth(); x++) {
            g.strokeLine(x, 0, x, 10);
        }

        for (int y = 0; y <= this.initialBoard.getHeight(); y++) {
            g.strokeLine(0, y, 10, y);
        }

    }

    private void drawSimulation(Board simulationToDraw) {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        for (int x = 0; x < simulationToDraw.getWidth(); x++) {
            for (int y = 0; y < simulationToDraw.getHeight(); y++) {
                if (simulationToDraw.getState(x, y) == CellState.ALIVE) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
    }

}
