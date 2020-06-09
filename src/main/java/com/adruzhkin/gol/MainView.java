package com.adruzhkin.gol;

import com.adruzhkin.gol.model.Board;
import com.adruzhkin.gol.model.CellState;
import com.adruzhkin.gol.viewmodel.ApplicationViewModel;
import com.adruzhkin.gol.viewmodel.BoardViewModel;
import com.adruzhkin.gol.viewmodel.EditorViewModel;
import com.adruzhkin.gol.viewmodel.SimulationViewModel;
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

    private Infobar infobar;
    private Canvas canvas;
    private Affine affine;

    private BoardViewModel boardViewModel;
    private EditorViewModel editorViewModel;

    public MainView(ApplicationViewModel appViewModel, BoardViewModel boardViewModel, EditorViewModel editorViewModel,
                    SimulationViewModel simulationViewModel) {

        this.boardViewModel = boardViewModel;
        this.boardViewModel.listenToBoard(this::onBoardChanged);

        this.editorViewModel = editorViewModel;

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);

        //Set a key listener on the entire MainView, not just the canvas itself
        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(editorViewModel, appViewModel, simulationViewModel);

        this.infobar = new Infobar(editorViewModel);
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

    private void onBoardChanged(Board board) {
        this.draw(board);
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

    private void handleMoved(MouseEvent mouseEvent) {
        Point2D simulationCoordinates = this.getSimulationCoordinates(mouseEvent);
        this.infobar.setCursorPosition((int) simulationCoordinates.getX(), (int) simulationCoordinates.getY());
    }

    private void handleDraw(MouseEvent mouseEvent) {
        Point2D simulationCoordinates = getSimulationCoordinates(mouseEvent);

        int simX = (int) simulationCoordinates.getX();
        int simY = (int) simulationCoordinates.getY();

        System.out.println(simX + ", " + simY);

        //Update the state of the corresponding cell in Simulation
        this.editorViewModel.boardPressed(simX, simY);
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

    public void draw(Board board) {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        //Fill the canvas
        g.setFill(Color.LIGHTGREY);
        g.fillRect(0, 0, 400, 400);

        //Draw simulation cells
        this.drawSimulation(board);

        //Draw the grid
        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= board.getWidth(); x++) {
            g.strokeLine(x, 0, x, 10);
        }

        for (int y = 0; y <= board.getHeight(); y++) {
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
