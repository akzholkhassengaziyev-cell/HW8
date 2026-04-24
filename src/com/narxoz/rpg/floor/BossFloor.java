package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;

import java.util.List;

public class BossFloor extends TowerFloor {

    private Monster boss;

    @Override
    public String getFloorName() {
        return "Boss Floor";
    }

    @Override
    public void announce() {
        System.out.println("[ANNOUNCE] The Haunted King awaits at the top.");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[SETUP] Dark flames surround the throne.");
        boss = new Monster("Haunted King", 70, 14);
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int damageTaken = 0;

        while (boss.isAlive() && hasAliveHeroes(party)) {

            for (Hero hero : party) {
                if (hero.isAlive() && boss.isAlive()) {
                    hero.onTurnStart();

                    if (hero.canAct()) {
                        int dmg = hero.attack();
                        boss.takeDamage(dmg);
                        System.out.println(hero.getName() + " hits boss for " + dmg);
                    }

                    hero.onTurnEnd();
                }
            }

            if (boss.isAlive()) {
                Hero target = firstAliveHero(party);
                if (target != null) {
                    int dmg = boss.attack(target);
                    damageTaken += dmg;
                    System.out.println("Boss attacks " + target.getName() + " for " + dmg);
                }
            }
        }

        boolean cleared = !boss.isAlive();
        return new FloorResult(cleared, damageTaken, cleared ? "Boss defeated!" : "Party lost!");
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[LOOT] Legendary relic obtained.");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(20);
            }
        }
    }

    private boolean hasAliveHeroes(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private Hero firstAliveHero(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) {
                return hero;
            }
        }
        return null;
    }
}