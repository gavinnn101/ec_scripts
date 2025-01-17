package com.gavin101.amethystaio;

import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.walking.Walking;

public class CombatHelper {
    private final String monsterName;         // Name of the monster to fight.
    private final RectArea monsterArea;       // Area that the monsters are in.
    private final boolean buryBones;          // Whether to bury bones or not.
    private final boolean shouldEatFood;      // Whether to eat food or not.
    private final String foodToEat;           // The name of food to eat.
    private final int eatThresholdPercent;    // % HP threshold to eat at.
    private final boolean lootItems;          // Whether to loot items.
    private final String[] itemsToLoot;       // List of items to loot.

    public CombatHelper(
            String monsterName,
            RectArea monsterArea,
            boolean buryBones,
            boolean shouldEatFood,
            String foodToEat,
            int eatThresholdPercent,
            boolean lootItems,
            String[] itemsToLoot
    ) {
        this.monsterName = monsterName;
        this.monsterArea = monsterArea;
        this.buryBones = buryBones;
        this.shouldEatFood = shouldEatFood;
        this.foodToEat = foodToEat;
        this.eatThresholdPercent = eatThresholdPercent;
        this.lootItems = lootItems;
        this.itemsToLoot = itemsToLoot;
    }

    public void fightMonster() {
        if (shouldEatFood) {
            if (needToEat()) {
                if (Inventory.contains(foodToEat)) {
                    eatFood();
                } else {
                    Log.debug("We're out of food, need to withdraw more.");
//                    int foodID = ItemComposite.getItem(foodToEat);
//                    new InventoryLoadout().addReq(ItemID.fo)
                }
            }
        }
        if (!isInMonsterArea()) {
            goToMonsterArea();
        }
        NPC monsterTarget = findMonsterTarget();
        if (monsterTarget != null) {
            attackMonster(monsterTarget);
        } else {
            Log.warn("No valid monster found!");
        }
    }

    public NPC findMonsterTarget() {
        return NPCs.closest(g -> g.hasName(monsterName) && !g.isInCombat() && g.canReach());
    }

    public void attackMonster(NPC monsterTarget) {
        Log.debug("Found a valid monsterTarget, attacking.");
        new EntityInteractEvent(monsterTarget, "Attack").setEventCompleteCondition(
                () -> Players.localPlayer().isInCombat(), Calculations.random(1500, 3500)
        ).execute();
    }

    public boolean isInMonsterArea() {
        Log.debug("Checking if we're in the monster area.");
        return monsterArea.contains(Players.localPlayer());
    }

    public void goToMonsterArea() {
        Log.debug("Going to monster area.");
        Walking.walk(monsterArea.getRandomTile(), this::isInMonsterArea);
    }

    public boolean needToEat() {
        Log.debug("Checking if we need to eat food.");
        return Players.localPlayer().getHealthPercent() <= eatThresholdPercent;
    }

    public void eatFood() {
        Log.debug("Eating food.");
        int currentHp = Players.localPlayer().getHealthPercent();
        new InventoryEvent(foodToEat, "Eat").setEventCompleteCondition(
                () -> Players.localPlayer().getHealthPercent() > currentHp, Calculations.random(250, 500)
        ).execute();
    }
}
