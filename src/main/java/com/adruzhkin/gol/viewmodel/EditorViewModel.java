package com.adruzhkin.gol.viewmodel;

import com.adruzhkin.gol.model.Board;
import com.adruzhkin.gol.model.CellPosition;
import com.adruzhkin.gol.model.CellState;
import com.adruzhkin.gol.util.Property;

public class EditorViewModel {

    Property<CellState> drawMode;
    Property<CellPosition> cursorPosition;

    private BoardViewModel boardViewModel;
    private Board editorBoard;
    private boolean drawingEnabled = true;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialBoard) {
        this.boardViewModel = boardViewModel;
        this.editorBoard = initialBoard;

        this.drawMode = new Property<>(CellState.ALIVE);
        this.cursorPosition = new Property<>();
    }

    public Property<CellState> getDrawMode() {
        return drawMode;
    }

    public Property<CellPosition> getCursorPosition() {
        return cursorPosition;
    }

    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            this.drawingEnabled = true;
            this.boardViewModel.getBoard().set(editorBoard);
        } else {
            this.drawingEnabled = false;
        }
    }

    public void boardPressed(CellPosition cursorPosition) {
        if (this.drawingEnabled) {
            this.editorBoard.setState(cursorPosition.getX(), cursorPosition.getY(), this.drawMode.get());
            this.boardViewModel.getBoard().set(this.editorBoard);
        }
    }

    public Board getBoard() {
        return editorBoard;
    }
}
