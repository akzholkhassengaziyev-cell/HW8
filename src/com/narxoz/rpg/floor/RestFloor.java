package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.state.RegeneratingState;

import java.util.List;

public class RestFloor extends TowerFloor {
    @Override
    public String getFloorName() {
        return "Rest Floor";
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[SETUP] A calm sanctuary glows with soft light.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[CHALLENGE] The party rests and recovers.");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(10);
                hero.setState(new RegeneratingState(2));
                System.out.println(hero.getName() + " rests, heals 10 HP, and becomes Regenerating.");
            }
        }
        return new FloorResult(true, 0, "The party recovers on the rest floor.");
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return false;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
    }

    @Override
    protected void cleanup(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive() && "Poisoned".equals(hero.getStateName())) {
                hero.setState(new NormalState());
                System.out.println(hero.getName() + " is cleansed by the sanctuary.");
            }
        }
        System.out.println("[CLEANUP] The sanctuary doors close behind the party.");
    }
}