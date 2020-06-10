package com.adruzhkin.gol.view;

import com.adruzhkin.gol.model.Board;
import com.adruzhkin.gol.model.CellState;
import com.adruzhkin.gol.viewmodel.BoardViewModel;
import com.adruzhkin.gol.viewmodel.EditorViewModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class SimulationCanvas extends Pane {

    private Canvas canvas;
    private Affine affine;

    private EditorViewModel editorViewModel;
    private BoardViewModel boardViewModel;

    public SimulationCanvas(EditorViewModel editorViewModel, BoardViewModel boardViewModel) {
        this.editorViewModel = editorViewModel;
        this.boardViewModel = boardViewModel;
        boardViewModel.listenToBoard(this::draw);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        //this.canvas.setOnMouseMoved(this::handleMoved);

        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());

        this.getChildren().addAll(this.canvas);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        this.draw(boardViewModel.getBoard());
    }

    //    private void onBoardChanged(Board board) {
//        this.draw(board);
//    }

    private void handleDraw(MouseEvent mouseEvent) {
        Point2D simulationCoordinates = getSimulationCoordinates(mouseEvent);

        int simX = (int) simulationCoordinates.getX();
        int simY = (int) simulationCoordinates.getY();

        System.out.println(simX + ", " + simY);

        //Update the state of the corresponding cell in Simulation
        this.editorViewModel.boardPressed(simX, simY);
    }

//    private void handleMoved(MouseEvent mouseEvent) {
//        Point2D simulationCoordinates = this.getSimulationCoordinates(mouseEvent);
//        this.infobar.setCursorPosition((int) simulationCoordinates.getX(), (int) simulationCoordinates.getY());
//    }

    private Point2D getSimulationCoordinates(MouseEvent mouseEvent) {
        //Get mouse coordinates
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        //Transform mouse coordinates into simulation coordinates
        try {
            Point2D point2D = this.affine.inverseTransform(mouseX, mouseY);
            return point2D;
        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException("Non invertible transform");
        }
    }

    private void draw(Board board) {
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
            g.strokeLine(x, 0, x, board.getHeight());
        }

        for (int y = 0; y <= board.getHeight(); y++) {
            g.strokeLine(0, y, board.getWidth(), y);
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
