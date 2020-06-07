package com.adruzhkin.gol.model;

public interface Board {

    Board copy();

    CellState getState(int x, int y);

    void setState(int x, int y);

    int getWidth();

    int getHeight();

}
