package com.stormwarn.game.storm.partitions;

public class StormPartition {

    private StormLayer[] layers;

    StormPartition(int layerNum) {
        this.layers = new StormLayer[layerNum];
    }

    public void addLayer(int index, StormLayer stormLayer) {
        layers[index] = stormLayer;
    }

    public StormLayer getLayer(int index) {
        return layers[index];
    }
}
