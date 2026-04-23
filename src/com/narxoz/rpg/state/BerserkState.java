package com.narxoz.rpg.state;

<<<<<<< HEAD
    }
=======
import com.narxoz.rpg.combatant.Hero;

public class BerserkState implements HeroState {
    @Override
    public String getName() {
        return "Berserk";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int) Math.round(basePower * 1.5);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) Math.round(rawDamage * 1.25);
    }

    @Override
    public void onTurnStart(Hero hero) {
        if (hero.getHp() > hero.getMaxHp() / 2) {
            hero.setState(new NormalState());
            System.out.println(hero.getName() + " calms down and returns to Normal.");
        }
    }

    @Override
    public void onTurnEnd(Hero hero) {
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
>>>>>>> 934378c (3)
