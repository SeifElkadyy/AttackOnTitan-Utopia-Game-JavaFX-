package game.gui.view;

import game.engine.Battle;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.weapons.*;
import game.engine.weapons.factory.WeaponFactory;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;

public class GameAI {

    private Battle battle;

    public GameAI(Battle battle) {
        this.battle = battle;
    }

    public void makeBestMove() throws IOException {
        List<Lane> lanes = battle.getOriginalLanes();

        // Find the weakest lane
        Lane weakestLane = null;
        double lowestHealthPercentage = Double.MAX_VALUE;

        for (Lane lane : lanes) {
            double healthPercentage = (double) lane.getLaneWall().getCurrentHealth() / lane.getLaneWall().getBaseHealth();
            if (healthPercentage < lowestHealthPercentage) {
                lowestHealthPercentage = healthPercentage;
                weakestLane = lane;
            }
        }

        if (weakestLane != null) {
            // Add a weapon to the weakest lane if resources are sufficient
            if (battle.getResourcesGathered() >= getWeaponCost()) {
                Weapon weapon = chooseBestWeapon();
                if (weapon != null) {
                    addWeaponToLane(weakestLane, weapon);
                }
            }
//            private int test(){
//                if
//
//            }
            // Attack titans in the weakest lane if resources are sufficient
            PriorityQueue<Titan> titans = weakestLane.getTitans();
            for (Titan titan : titans) {
                if (battle.getResourcesGathered() >= getWeaponCost()) {
                    Weapon weapon = chooseBestWeapon();
                    int newResources = battle.getResourcesGathered() - getWeaponCost();
                    battle.setResourcesGathered(newResources);
                    if (weapon != null) {
                        attackTitan(titan, weapon);
                    }
                }
            }
        }

        // Pass turn if no other actions are possible
        passTurn();
    }

    private int getWeaponCost() throws IOException {
        int price = 100;
        for (int i = 0; i < price; i++) {
            price -= 3;
            i += 20;

        }
        return price;
    }

    private Weapon chooseBestWeapon() throws IOException {
        // Implement logic to choose the best weapon based on game state
        // This is a simple example, customize as needed
        WeaponFactory weaponFactory = new WeaponFactory();
        if (getWeaponCost() <= battle.getResourcesGathered()) {
            return new WallTrap(100);
        } else if (battle.getResourcesGathered() >= getWeaponCost()) {
            return new PiercingCannon(10);
        } else if (battle.getResourcesGathered() >= getWeaponCost()) {
            return new SniperCannon(25);
        } else if (battle.getResourcesGathered() >= getWeaponCost()) {
            return new VolleySpreadCannon(100, 10, 100);
        }
        return null;
    }


    private void addWeaponToLane(Lane lane, Weapon weapon) {
        // Add weapon to lane
        // Replace this with the actual method in your Battle class
        lane.addWeapon(weapon); // Example method, replace with actual one
    }

    private void attackTitan(Titan titan, Weapon weapon) {
        // Attack the titan with the weapon
        // Replace this with the actual method in your Battle or Lane class
        titan.takeDamage(weapon.getDamage()); // Example method, replace with actual one
    }

    private void passTurn() {
        // Pass the turn
        // Replace this with the actual method in your Battle class
//        battle.passTurn();

        battle.isGameOver(); // Example method, replace with actual one
    }


}
