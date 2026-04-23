package com.narxoz.rpg.tower;

public class TowerRunResult {
    private final int floorsCleared;
    private final int heroesSurviving;
    private final boolean towerCleared;

    public TowerRunResult(int floorsCleared, int heroesSurviving, boolean towerCleared) {
        this.floorsCleared = floorsCleared;
        this.heroesSurviving = heroesSurviving;
        this.towerCleared = towerCleared;
    }

    public int getFloorsCleared() {
        return floorsCleared;
    }

    public int getHeroesSurviving() {
        return heroesSurviving;
    }

    public boolean isTowerCleared() {
        return towerCleared;
    }
}