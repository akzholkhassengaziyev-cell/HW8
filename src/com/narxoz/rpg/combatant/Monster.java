package com.narxoz.rpg.combatant;

public class Monster {
    private final String name;
    private int hp;
    private final int attackPower;

    public Monster(String name, int hp, int attackPower) {
        this.name = name;
        this.hp = hp;
        this.attackPower = attackPower;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int amount) {
        hp -= Math.max(0, amount);
        if (hp < 0) {
            hp = 0;
        }
    }

    public int attack(Hero hero) {
        hero.takeDamage(attackPower);
        return Math.max(1, attackPower);
    }
}