package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.PoisonedState;

import java.util.List;

public class BossFloor extends TowerFloor {
    private Monster boss;

    @Override
    public String getFloorName() {
        return "Boss Floor";
    }

    @Override
    protected void announce() {
        System.out.println("[ANNOUNCE] The Haunted King awaits at the top of the tower.");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[SETUP] Dark flames gather around the throne.");
        boss = new Monster("Haunted King", 70, 14);
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[CHALLENGE] The final battle begins.");
        int totalDamageTaken = 0;
        int round = 0;

        while (boss.isAlive() && hasLivingHeroes(party)) {
            round++;
            System.out.println("  Round " + round);

            for (Hero hero : party) {
                if (!hero.isAlive() || !boss.isAlive()) {
                    continue;
                }

                hero.onTurnStart();

                if (!hero.canAct()) {
                    hero.onTurnEnd();
                    continue;
                }

                int dealt = hero.attack();
                boss.takeDamage(dealt);
                System.out.println(hero.getName() + " strikes the boss for " + dealt + ".");

                if (!boss.isAlive()) {
                    hero.onTurnEnd();
                    break;
                }

                int taken = boss.attack(hero);
                totalDamageTaken += taken;
                System.out.println(boss.getName() + " hits " + hero.getName() + " for " + taken + ".");

                if (hero.isAlive() && round == 1) {
                    hero.setState(new PoisonedState(2));
                    System.out.println(hero.getName() + " is cursed and becomes Poisoned.");
                }

                hero.onTurnEnd();
            }
        }

        boolean cleared = !boss.isAlive();
        return new FloorResult(cleared, totalDamageTaken,
                cleared ? "The Haunted King is defeated." : "The tower claims the party.");
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[LOOT] A royal relic restores the survivors.");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(12);
                System.out.println(hero.getName() + " heals 12 HP.");
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