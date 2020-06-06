package com.adruzhkin;

public class Simulation {

    private int width;
    private int height;
    private int[][] board;

    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void printBoard() {
        System.out.println("---");
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder("|");
            for (int x = 0; x < width; x++) {
                if (this.board[x][y] == 0) {
                    line.append(".");
                } else {
                    line.append("*");
                }
            }
            line.append("|");
            System.out.println(line);
        }
        System.out.println("---\n");
    }

    public void setAlive(int x, int y) {
        this.setState(x, y, 1);
    }

    public void setDead(int x, int y) {
        this.setState(x, y, 0);
    }

    public boolean isAlive(int x, int y) {
        return this.board[x][y] == 1;
    }

    public boolean isDead(int x, int y) {
        return this.board[x][y] == 0;
    }

    public int getState(int x, int y) {
        if (x < 0 || x >= width) return 0;
        if (y < 0 || y >= height) return 0;
        return board[x][y];
    }

    public void setState(int x, int y, int state) {
        if (x < 0 || x >= width) return;
        if (y < 0 || y >= height) return;
        board[x][y] = state;
    }

    public int countAliveNeighbours(int x, int y) {
        int count = 0;

        count += getState(x - 1, y - 1);
        count += getState(x, y - 1);
        count += getState(x + 1, y - 1);

        count += getState(x - 1, y);
        count += getState(x + 1, y);

        count += getState(x - 1, y + 1);
        count += getState(x, y + 1);
        count += getState(x + 1, y + 1);

        return count;
    }

    public void step() {
        int[][] newBoard = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int aliveNeighbours = countAliveNeighbours(x, y);
                newBoard[x][y] = board[x][y];

                if (isAlive(x, y)) {
                    if (aliveNeighbours < 2 || aliveNeighbours > 3) newBoard[x][y] = 0;
                } else {
                    if (aliveNeighbours == 3) newBoard[x][y] = 1;
                }

            }
        }

        board = newBoard;
    }

}
