package com.stormwarn.game.enviornment;

public class Environment {

    private int xcells;

    private int ycells;

    private EnvCell[][] cells;

    public Environment(int xcells, int ycells) {
        this.xcells = xcells;
        this.ycells = ycells;
        this.cells = new EnvCell[xcells][ycells];
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
}
