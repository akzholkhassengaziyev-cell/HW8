package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;

import java.util.List;

public class TowerRunner {
    public TowerRunResult run(List<Hero> party, List<TowerFloor> floors) {
        int floorsCleared = 0;

        for (TowerFloor floor : floors) {
            if (!hasLivingHeroes(party)) {
                break;
            }

            System.out.println();
            System.out.println("===== ENTERING " + floor.getFloorName().toUpperCase() + " =====");
            FloorResult result = floor.explore(party);
            System.out.println("[RESULT] " + result.getSummary());
            System.out.println("[RESULT] Damage taken: " + result.getDamageTaken());

            if (result.isCleared()) {
                floorsCleared++;
            } else {
                break;
            }

            printPartyStatus(party);
        }

        boolean towerCleared = floorsCleared == floors.size() && hasLivingHeroes(party);
        int heroesSurviving = countLivingHeroes(party);

        return new TowerRunResult(floorsCleared, heroesSurviving, towerCleared);
    }

    private boolean hasLivingHeroes(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private int countLivingHeroes(List<Hero> party) {
        int count = 0;
        for (Hero hero : party) {
            if (hero.isAlive()) {
                count++;
            }
        }
        return count;
    }

    private void printPartyStatus(List<Hero> party) {
        System.out.println("[PARTY STATUS]");
        for (Hero hero : party) {
            System.out.println(" - " + hero.getName() + " | HP: " + hero.getHp() + "/" + hero.getMaxHp()
                    + " | State: " + hero.getStateName()
                    + " | Alive: " + hero.isAlive());
        }
    }
}