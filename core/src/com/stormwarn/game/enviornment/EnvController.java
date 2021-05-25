package com.stormwarn.game.enviornment;

import java.util.ArrayList;

public class EnvController {

    private static EnvController instance;

    private Environment environment;

    private ArrayList<StormCell> cells;

    public EnvController() {
        environment = Environment.getInstance(60, 60, 15);
    }

    public static EnvController getInstance() {
        if (instance == null) {
            instance = new EnvController();
        }
        return instance;
    }
}
