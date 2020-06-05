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
        this.board[x][y] = 1;
    }

    public void setDead(int x, int y) {
        this.board[x][y] = 0;
    }

    public int countAliveNeighbours(int x, int y) {
        int count = 0;

        int xLess = Math.max(x - 1, 0);
        int xMore = Math.min(x + 1, width - 1);
        int yLess = Math.max(y - 1, 0);
        int yMore = Math.min(y + 1, height - 1);

        count += this.board[xLess][yLess];
        count += this.board[x][yLess];
        count += this.board[xMore][yLess];

        count += this.board[xLess][y];
        count += this.board[xMore][y];

        count += this.board[xLess][yMore];
        count += this.board[x][yMore];
        count += this.board[xMore][yMore];

        return count;
    }

    public static void main(String[] args) {
        Simulation simulation = new Simulation(8, 5);

        simulation.setAlive(2, 2);
        simulation.setAlive(3, 2);
        simulation.setAlive(4, 2);

        simulation.printBoard();

        System.out.println(simulation.countAliveNeighbours(7, 4));
    }

}
