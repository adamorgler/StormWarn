package com.stormwarn.game.radar;

public class Radar {

    /**
     * Distance from radar
     */
    private final int d;

    /**
     * Radial distance from north in degrees
     */
    private final int r;

    private RadarCell[][] radarCells;

    public Radar() {
        this.d = 250;
        this.r = 360;
        this.radarCells = new RadarCell[r][d];
    }

    public Radar(int d) {
        this.d = d;
        this.r = 360;
        this.radarCells = new RadarCell[r][d];
    }

    public RadarCell[][] getRadarCells() {
        return radarCells;
    }

    public void setRadarCells(RadarCell[][] radarCells) {
        this.radarCells = radarCells;
    }

    public int getDistance() {
        return d;
    }

    public int getRadialAmount() { return r; }

    public RadarCell getRadarCell(int r, int d) {
        return this.radarCells[r][d];
    }

    public void setRadarCell(int r, int d, RadarCell cell) {
        if (r >= 0 && r < this.r && d >= 0 && d < this.d) {
            this.radarCells[r][d] = cell;
        }
    }

    public int getNumCells() {
        return r * d;
    }
}
