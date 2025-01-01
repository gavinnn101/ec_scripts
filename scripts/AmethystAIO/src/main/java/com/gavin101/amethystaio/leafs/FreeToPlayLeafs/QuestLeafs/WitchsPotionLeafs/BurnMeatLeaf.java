package com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.WitchsPotionLeafs;

import com.gavin101.amethystaio.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.InventoryEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.walking.Walking;

public class BurnMeatLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Quest.WITCHS_POTION.isInProgress() && Inventory.contains("Cooked meat");
    }

    @Override
    public int onLoop() {
        GameObject range = GameObjects.closest("Range");
        if (range != null && range.canReach()) {
            Log.info("Burning meat on range.");
            new InventoryEvent(Inventory.get("Cooked meat")).on(range).setEventCompleteCondition(
                    () -> Inventory.contains("Burnt meat")
            ).execute();
        } else {
            Log.debug("Walking to range to burn meat.");
            Walking.walk(Constants.RANGE_TILE, () -> GameObjects.closest("Range") != null);
        }
        return ReactionGenerator.getNormal();
    }
}
