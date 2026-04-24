package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState {
    private int turnsRemaining;

    public PoisonedState(int turnsRemaining) {
        this.turnsRemaining = turnsRemaining;
    }

    @Override
    public String getName() {
        return "Poisoned";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return Math.max(1, (int) Math.round(basePower * 0.8));
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) Math.round(rawDamage * 1.15);
    }

    @Override
    public void onTurnStart(Hero hero) {
        hero.takeRawDamage(4);
        System.out.println(hero.getName() + " suffers 4 poison damage.");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            hero.setState(new NormalState());
            System.out.println(hero.getName() + " is no longer poisoned.");
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}