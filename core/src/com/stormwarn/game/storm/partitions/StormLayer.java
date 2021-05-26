package com.stormwarn.game.storm.partitions;

import com.stormwarn.game.enviornment.EnvLayer;

public class StormLayer extends EnvLayer {

    private int rainAmount;

    public int getRainAmount() {
        return rainAmount;
    }

    public void setRainAmount(int rainAmount) {
        this.rainAmount = rainAmount;
    }
}
