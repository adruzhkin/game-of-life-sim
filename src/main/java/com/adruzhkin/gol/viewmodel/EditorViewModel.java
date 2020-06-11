package com.adruzhkin.gol.viewmodel;

import com.adruzhkin.gol.model.Board;
import com.adruzhkin.gol.model.CellState;
import com.adruzhkin.gol.util.Property;

public class EditorViewModel {

    Property<CellState> drawMode;

    private BoardViewModel boardViewModel;
    private Board editorBoard;
    private boolean drawingEnabled = true;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialBoard) {
        this.drawMode = new Property<>(CellState.ALIVE);
        this.boardViewModel = boardViewModel;
        this.editorBoard = initialBoard;
    }

    public Property<CellState> getDrawMode() {
        return drawMode;
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
            this.editorBoard.setState(simX, simY, this.drawMode.get());
            this.boardViewModel.getBoard().set(this.editorBoard);
        }
    }
}
