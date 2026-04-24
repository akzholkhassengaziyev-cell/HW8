package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class RegeneratingState implements HeroState {
    private int turnsRemaining;

    public RegeneratingState(int turnsRemaining) {
        this.turnsRemaining = turnsRemaining;
    }

    @Override
    public String getName() {
        return "Regenerating";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return Math.max(1, (int) Math.round(basePower * 0.9));
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return Math.max(0, (int) Math.round(rawDamage * 0.85));
    }

    @Override
    public void onTurnStart(Hero hero) {
        hero.heal(5);
        System.out.println(hero.getName() + " regenerates 5 HP.");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            hero.setState(new NormalState());
            System.out.println(hero.getName() + " is no longer regenerating.");
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}