package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.StunnedState;

import java.util.List;

public class TrapFloor extends TowerFloor {
    @Override
    public String getFloorName() {
        return "Trap Floor";
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[SETUP] Hidden darts line the walls.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[CHALLENGE] The trap is triggered.");
        int totalDamageTaken = 0;

        for (Hero hero : party) {
            if (!hero.isAlive()) {
                continue;
            }
            int damage = 7;
            hero.takeDamage(damage);
            totalDamageTaken += damage;
            System.out.println(hero.getName() + " takes " + damage + " damage from traps.");

            if (hero.isAlive() && hero.getHp() % 2 == 0) {
                hero.setState(new StunnedState(1));
                System.out.println(hero.getName() + " is stunned by the trap.");
            }
        }

        boolean cleared = hasLivingHeroes(party);
        return new FloorResult(cleared, totalDamageTaken,
                "The party survives the trap corridor.");
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[LOOT] The party finds a small healing potion.");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(6);
                System.out.println(hero.getName() + " heals 6 HP.");
                break;
            }
        }
    }

    private boolean hasLivingHeroes(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) {
                return true;
            }
        }
        return false;
    }
}