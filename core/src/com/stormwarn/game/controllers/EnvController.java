package com.stormwarn.game.controllers;

import com.stormwarn.game.enviornment.Environment;
import com.stormwarn.game.storm.partitions.StormCell;

import java.util.ArrayList;

public class EnvController {

    private static EnvController instance;

    private Environment environment;

    private ArrayList<StormCell> cells;

    public EnvController() {

        environment = Environment.getInstance(60, 60, 15);
        cells = new ArrayList<StormCell>();
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

}
