package com.stormwarn.game.enviornment;

public class EnvCell {
    private int layerDepth;

    private EnvLayer[] layers;

    public EnvCell(int layerDepth) {
        this.layerDepth = layerDepth;
        this.layers = new EnvLayer[layerDepth];
    }

    public EnvLayer[] getLayers() {
        return this.getLayers();
    }

    public void addLayer(int i, EnvLayer layer) {
        if (i >= 0 && i < layerDepth) {
            this.layers[i] = layer;
        }
    }
}
