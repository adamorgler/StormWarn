package com.stormwarn.game.enviornment;

public class Environment {

    private static Environment instance;

    private final int xcells;

    private final int ycells;

    private final int cellHeight;

    private EnvCell[][] cells;

    public Environment(int xcells, int ycells, int cellHeight) {
        this.xcells = xcells;
        this.ycells = ycells;
        this.cellHeight = cellHeight;
        this.cells = new EnvCell[xcells][ycells];
    }

    public static Environment getInstance(int xcells, int ycells, int cellHeight) {
        if (instance == null) {
            instance = new Environment(xcells, ycells, cellHeight);
        }
        return instance;
    }

    public EnvCell[][] getCells() {
        return this.cells;
    }

    public EnvCell getCell(int xpos, int ypos) {
        if (xpos >= 0 && xpos < xcells && ypos >= 0 && ypos < ycells) {
            return cells[xpos][ypos];
        } else {
            return null;
        }
    }

    public void setCell(int xpos, int ypos, EnvCell cell) {
        if (xpos >= 0 && xpos < xcells && ypos >= 0 && ypos < ycells) {
            this.cells[xpos][ypos] = cell;
        }
    }

    public int getCellHeight() {
        return cellHeight;
    }
}
