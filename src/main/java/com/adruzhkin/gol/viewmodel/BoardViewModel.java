package com.adruzhkin.gol.viewmodel;

import com.adruzhkin.gol.model.Board;
import com.adruzhkin.gol.util.Property;

public class BoardViewModel {

    private Property<Board> board;

    public BoardViewModel() {
        this.board = new Property<>();
    }

    public Property<Board> getBoard() {
        return board;
    }
}
