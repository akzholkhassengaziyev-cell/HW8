package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.PoisonedState;

import java.util.List;

public class CombatFloor extends TowerFloor {
    private Monster monster;

    @Override
    public String getFloorName() {
        return "Combat Floor";
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[SETUP] A tower beast appears.");
        monster = new Monster("Tower Beast", 40, 10);
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[CHALLENGE] Combat begins.");
        int totalDamageTaken = 0;

        while (monster.isAlive() && hasLivingHeroes(party)) {
            for (Hero hero : party) {
                if (!hero.isAlive() || !monster.isAlive()) {
                    continue;
                }

                hero.onTurnStart();

                if (!hero.canAct()) {
                    hero.onTurnEnd();
                    continue;
                }

                int damage = hero.attack();
                monster.takeDamage(damage);
                System.out.println(hero.getName() + " hits " + monster.getName() + " for " + damage + ".");

                if (!monster.isAlive()) {
                    break;
                }

                int taken = monster.attack(hero);
                totalDamageTaken += taken;
                System.out.println(monster.getName() + " hits " + hero.getName() + " for " + taken + ".");

                if (hero.getHp() <= hero.getMaxHp() / 3 && !"Berserk".equals(hero.getStateName())) {
                    hero.setState(new BerserkState());
                    System.out.println(hero.getName() + " enters Berserk state!");
                }

                hero.onTurnEnd();
            }
        }

        boolean cleared = !monster.isAlive();
        return new FloorResult(cleared, totalDamageTaken,
                cleared ? "The party defeated the tower beast." : "The party was stopped in combat.");
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[LOOT] The party finds a cleansing herb.");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.setState(new PoisonedState(1));
                System.out.println(hero.getName() + " was exposed to toxic residue and becomes Poisoned.");
                break;
            }
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("[CLEANUP] The battlefield grows silent.");
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