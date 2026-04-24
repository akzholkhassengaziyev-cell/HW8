package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.BossFloor;
import com.narxoz.rpg.floor.CombatFloor;
import com.narxoz.rpg.floor.RestFloor;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.floor.TrapFloor;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.state.PoisonedState;
import com.narxoz.rpg.tower.TowerRunResult;
import com.narxoz.rpg.tower.TowerRunner;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Hero hero1 = new Hero("Aruzhan", 65, 14, new NormalState());
        Hero hero2 = new Hero("Bekzat", 75, 11, new PoisonedState(2));

        List<Hero> party = new ArrayList<>();
        party.add(hero1);
        party.add(hero2);

        List<TowerFloor> floors = new ArrayList<>();
        floors.add(new CombatFloor());
        floors.add(new TrapFloor());
        floors.add(new RestFloor());
        floors.add(new BossFloor());

        TowerRunner runner = new TowerRunner();
        TowerRunResult result = runner.run(party, floors);

        System.out.println();
        System.out.println("===== FINAL TOWER RESULT =====");
        System.out.println("Floors cleared: " + result.getFloorsCleared());
        System.out.println("Heroes surviving: " + result.getHeroesSurviving());
        System.out.println("Tower cleared: " + result.isTowerCleared());
    }
}