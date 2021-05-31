package com.stormwarn.game.controllers;

import com.stormwarn.game.enviornment.Environment;
import com.stormwarn.game.storm.partitions.Downdraft;
import com.stormwarn.game.storm.partitions.StormCell;
import com.stormwarn.game.storm.partitions.StormLayer;
import com.stormwarn.game.storm.partitions.StormPartition;
import com.stormwarn.game.storm.partitions.particles.Particle;
import com.stormwarn.game.storm.partitions.particles.Precipitation;

import java.util.ArrayList;
import java.util.Random;

public class EnvController {

    private static EnvController instance;

    private Environment environment;

    private ArrayList<StormCell> cells;

    private ArrayList<Particle> particles;

    public EnvController() {

        environment = Environment.getInstance(60, 60, 15);
        cells = new ArrayList<StormCell>();
        particles = new ArrayList<Particle>();
    }

    public static EnvController getInstance() {
        if (instance == null) {
            instance = new EnvController();
        }
        return instance;
    }

    public void genCell(float x, float y, float vx, float vy) {
        StormCell s = new StormCell(cells.size());
        s.setStrength(1);
        s.setXpos(x);
        s.setYpos(y);
        s.setVx(vx);
        s.setVy(vy);
        cells.add(s);
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void setParticles(ArrayList<Particle> particles) {
        this.particles = particles;
    }

    public void addParticle(Particle p) {
        this.particles.add(p);
    }

    public void spawnParticles(int viewLayer) {
        for(StormCell sc : cells) {
            ArrayList<StormPartition> parts = sc.getParts();
            for (StormPartition sp : parts) {
                if (sp instanceof Downdraft) {
                    StormLayer sl = sp.getLayer(viewLayer);

                }
            }
        }
    }

    public void clearParticles() {
        this.particles.clear();
    }

    public void genParticlesInCircle(float x, float y, float radius, int strength) {
        float area = (float) (Math.PI * radius * radius);
        float mul = (float) strength / 5.0f;
        for (int i = 0; i < area * mul; i++) {
            Random rand = new Random();
            float d = rand.nextFloat() * radius;
            float r = rand.nextInt(360);
            float cx = getXPos(r, d);
            float cy = getYPos(r, d);
            float rx = rand.nextInt(7) - 3;
            float ry = rand.nextInt(7) - 3;
            int ref = rand.nextInt(4) + 1;
            Precipitation p = new Precipitation(cx + x + rx, cy + y + ry, 0 ,0);
            p.setReflectivity(ref);
            addParticle(p);
        }
    }

    private float getRPos(float x, float y) {
        return (float)(Math.atan2(y, x) * (180 / Math.PI) + 180);
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

}
