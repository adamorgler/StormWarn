package com.stormwarn.game.radar;

import com.badlogic.gdx.utils.Queue;
import com.stormwarn.game.storm.partitions.particles.Precipitation;

import java.util.ArrayList;

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
        initRadar();
    }

    public Radar(int d) {
        this.d = d;
        this.r = 360;
        this.radarCells = new RadarCell[r][d];
        initRadar();
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

    public void setRadarCell(float x, float y, RadarCell cell) {
        int r = (int) getRPos(x, y);
        int d = (int) getDPos(x, y);
        if (checkBounds(r, d)) {
            this.radarCells[r][d] = cell;
        }
    }

    public void setRadarCell(int r, int d, RadarCell cell) {
        if (checkBounds(r, d)) {
            this.radarCells[r][d] = cell;
        }
    }

    public int getNumCells() {
        return r * d;
    }

    public void detectPrecipitation(Precipitation p) {
        float x = p.getxPos();
        float y = p.getyPos();
        float vx = p.getVx();
        float vy = p.getVy();

        int rPos = (int) getRPos(x, y);
        int dPos = (int) getDPos(x, y);

        if (checkBounds(rPos, dPos)) {
            RadarCell rc = radarCells[rPos][dPos];
            rc.setReflectivity(p.getReflectivity());
            rc.setVelocity(observedVelocity(x, y, vx, vy));
        }
    }

    private int observedVelocity(float x, float y, float vx, float vy) {


        return 0;
    }

    private void initRadar() {
        for (int i = 0; i < radarCells.length; i++) {
            for (int j = 0; j < radarCells[i].length; j++) {
                radarCells[i][j] = new RadarCell(i, j);
            }
        }
    }

    private boolean checkCircleBounds(float xc, float yc, float x, float y, float radius) {
        float d = getDPos(x - xc, y - yc);
        if (d <= radius) {
            return true;
        }
        return false;
    }

    private boolean checkRadiusBounds(float x, float y, float radius) {
        float d = getDPos(x, y);
        float t = 1 - (radius / d);
        float xt = t * x;
        float yt = t * y;
        return checkPointBounds(xt, yt);
    }

    private boolean checkPointBounds(float x, float y) {
        int d = (int) getDPos(x, y);
        if (d >= 0 && d < this.d) {
            return true;
        }
        return false;
    }

    private float getRPos(float x, float y) {
        return (float)(Math.atan2(y, x) * (180 / Math.PI));
    }

    private float getDPos(float x, float y) {
        return (float) Math.hypot(x, y);
    }

    private float getXPos(float r, float d) {
        double rad = r * Math.PI / 180;
        double xpos = d * Math.cos(rad);
        return (float) xpos;
    }

    private float getYPos(float r, float d) {
        double rad = r * Math.PI / 180;
        double ypos = d * Math.sin(rad);
        return (float) ypos;
    }

    private boolean checkBounds(int r, int d) {
        if (r >= 0 && r < this.r && d >= 0 && d < this.d) {
            return true;
        }
         return false;
    }

//    public void detectCircle(float xc, float yc, float radius) {
//        if (checkRadiusBounds(xc, yc, radius)) {
//            ArrayList<RadarCell> outBound = new ArrayList<RadarCell>();
//            ArrayList<RadarCell> checked = new ArrayList<RadarCell>();
//            Queue<RadarCell> queue = new Queue<RadarCell>();
//            int rCenter = (int) getRPos(xc, yc);
//            int dCenter = (int) getDPos(xc, yc);
//
//            if (checkBounds(rCenter, dCenter)) {
//                queue.addLast(getRadarCell(rCenter, dCenter));
//            } else {
//                RadarCell n = new RadarCell(rCenter, dCenter);
//                outBound.add(n);
//                queue.addLast(n);
//            }
//
//            while(queue.notEmpty()) {
//                RadarCell next = queue.removeFirst();
//                checked.add(next);
//                int rPos = next.getrPos();
//                int dPos = next.getdPos();
//                float rc = rPos + 0.5f;
//                float dc = dPos + 0.5f;
//
//                //detecting point in circle if within radar screen
//                if (checkBounds(rPos, dPos)) {
//                    next.setReflectivity(1);
//                }
//
//                //checking neighbors are within circle object then adding to queue if not already checked
//                RadarCell r;
//                if (checkCircleBounds(xc, yc, getXPos(rc + 1, dc), getYPos(rc + 1, dc), radius)) {
//                    int i = rPos + 1;
//                    if (checkBounds(i, dPos)) {
//                        r = radarCells[i][dPos];
//                    } else {
//                        r = new RadarCell(i, dPos);
//                    }
//                    if(!checked.contains(r)) {
//                        queue.addLast(r);
//                    }
//                }
//                if (checkCircleBounds(xc, yc, getXPos(rc - 1, dc), getYPos(rc - 1, dc), radius)) {
//                    int i = rPos - 1;
//                    if (checkBounds(i, dPos)) {
//                        r = radarCells[i][dPos];
//                    } else {
//                        r = new RadarCell(i, dPos);
//                    }
//                    if(!checked.contains(r)) {
//                        queue.addLast(r);
//                    }
//                }
//                if (checkCircleBounds(xc, yc, getXPos(rc, dc + 1), getYPos(rc, dc + 1), radius)) {
//                    int i = dPos + 1;
//                    if (checkBounds(rPos, i)) {
//                        r = radarCells[rPos][i];
//                    } else {
//                        r = new RadarCell(rPos, i);
//                    }
//                    if(!checked.contains(r)) {
//                        queue.addLast(r);
//                    }
//                }
//                if (checkCircleBounds(xc, yc, getXPos(rc, dc - 1), getYPos(rc, dc - 1), radius)) {
//                    int i = dPos - 1;
//                    if (checkBounds(rPos, i)) {
//                        r = radarCells[rPos][i];
//                    } else {
//                        r = new RadarCell(rPos, i);
//                    }
//                    if(!checked.contains(r)) {
//                        queue.addLast(r);
//                    }
//                }
//            }
//        }
//    }
}
