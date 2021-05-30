package com.stormwarn.game.controllers;

import com.stormwarn.game.enviornment.Environment;
import com.stormwarn.game.storm.partitions.StormCell;
import com.stormwarn.game.storm.partitions.particles.Particle;

import java.util.ArrayList;

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

    public void genCell() {
        StormCell s = new StormCell(cells.size());
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

    public void clearParticles() {
        this.particles.clear();
    }
}
