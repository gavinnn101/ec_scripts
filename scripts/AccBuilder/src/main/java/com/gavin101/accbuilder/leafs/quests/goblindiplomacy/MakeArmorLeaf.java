package com.gavin101.accbuilder.leafs.quests.goblindiplomacy;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;

public class MakeArmorLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Inventory.containsAll("Blue dye", "Goblin mail")
                || Inventory.containsAll("Orange dye", "Goblin mail");
    }

    @Override
    public int onLoop() {
        if (Inventory.containsAll("Blue dye", "Goblin mail")) {
            new InventoryEvent(Inventory.get("Blue dye")).on(Inventory.get("Goblin mail")).setEventCompleteCondition(
                    () -> Inventory.contains("blue goblin mail"), Calculations.random(750, 1500)
            ).execute();
        }
        if (Inventory.containsAll("Orange dye", "Goblin mail")) {
            new InventoryEvent(Inventory.get("Orange dye")).on(Inventory.get("Goblin mail")).setEventCompleteCondition(
                    () -> Inventory.contains("orange goblin mail"), Calculations.random(750, 1500)
            ).execute();
        }
        return ReactionGenerator.getPredictable();
    }
}
