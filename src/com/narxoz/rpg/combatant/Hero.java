package com.narxoz.rpg.combatant;

import com.narxoz.rpg.state.HeroState;
import com.narxoz.rpg.state.NormalState;

public class Hero {
    private final String name;
    private final int maxHp;
    private int hp;
    private final int basePower;
    private HeroState state;

    public Hero(String name, int maxHp, int basePower) {
        this(name, maxHp, basePower, new NormalState());
    }

    public Hero(String name, int maxHp, int basePower, HeroState state) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.basePower = basePower;
        this.state = state == null ? new NormalState() : state;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getBasePower() {
        return basePower;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public String getStateName() {
        return state.getName();
    }

    public void setState(HeroState newState) {
        if (newState != null) {
            this.state = newState;
            System.out.println(name + " is now in state: " + state.getName());
        }
    }

    public void onTurnStart() {
        if (isAlive()) {
            state.onTurnStart(this);
        }
    }

    public void onTurnEnd() {
        if (isAlive()) {
            state.onTurnEnd(this);
        }
    }

    public boolean canAct() {
        return isAlive() && state.canAct();
    }

    public int attack() {
        return state.modifyOutgoingDamage(basePower);
    }

    public void takeDamage(int rawDamage) {
        int adjusted = Math.max(0, state.modifyIncomingDamage(rawDamage));
        hp -= adjusted;
        if (hp < 0) {
            hp = 0;
        }
    }

    public void takeRawDamage(int damage) {
        hp -= Math.max(0, damage);
        if (hp < 0) {
            hp = 0;
        }
    }

    public void heal(int amount) {
        hp += Math.max(0, amount);
        if (hp > maxHp) {
            hp = maxHp;
        }
    }
}