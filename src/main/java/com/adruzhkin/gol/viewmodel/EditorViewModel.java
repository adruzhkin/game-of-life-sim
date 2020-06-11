package com.adruzhkin.gol.viewmodel;

import com.adruzhkin.gol.model.Board;
import com.adruzhkin.gol.model.CellState;

import java.util.LinkedList;
import java.util.List;

public class EditorViewModel {

    private CellState drawMode = CellState.ALIVE; //Default mode
    private List<SimpleChangeListener<CellState>> drawModeListeners;

    private BoardViewModel boardViewModel;
    private Board editorBoard;
    private boolean drawingEnabled = true;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialBoard) {
        this.boardViewModel = boardViewModel;
        this.editorBoard = initialBoard;
        this.drawModeListeners = new LinkedList<>();
    }

    public CellState getDrawMode() {
        return this.drawMode;
    }

    public void setDrawMode(CellState drawMode) {
        this.drawMode = drawMode;
        this.notifyDrawModeListeners();
    }

    private void notifyDrawModeListeners() {
        for (SimpleChangeListener<CellState> drawModeListener : drawModeListeners) {
            drawModeListener.valueChanged(this.drawMode);
        }
    }

    public void listenToDrawMode(SimpleChangeListener<CellState> listener) {
        this.drawModeListeners.add(listener);
    }

    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            this.drawingEnabled = true;
            this.boardViewModel.getBoard().set(editorBoard);
        } else {
            this.drawingEnabled = false;
        }
    }

    public void boardPressed(int simX, int simY) {
        if (this.drawingEnabled) {
            this.editorBoard.setState(simX, simY, this.drawMode);
            this.boardViewModel.getBoard().set(this.editorBoard);
        }
    }
}
