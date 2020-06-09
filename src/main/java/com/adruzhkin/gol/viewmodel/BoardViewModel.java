package com.adruzhkin.gol.viewmodel;

import com.adruzhkin.gol.model.Board;

import java.util.LinkedList;
import java.util.List;

public class BoardViewModel {

    private Board board;
    private List<SimpleChangeListener<Board>> boardListeners;

    public BoardViewModel() {
        this.boardListeners = new LinkedList<>();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
        this.notifyBoardListeners();
    }

    private void notifyBoardListeners() {
        for (SimpleChangeListener<Board> boardListener : this.boardListeners) {
            boardListener.valueChanged(this.board);
        }
    }

    public void listenToBoard(SimpleChangeListener<Board> listener) {
        this.boardListeners.add(listener);
    }
}
