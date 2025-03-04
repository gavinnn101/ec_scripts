package com.gavin101.gbuilder.activities.quests.restlessghost.leafs;

import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.walking.Walking;

public class GetSkullLeaf extends Leaf {
    private static final int altarID = 2146;
    private static final RectArea ALTAR_AREA = new RectArea(3112, 9569, 3121, 9564);

    @Override
    public boolean isValid() {
        return Quest.THE_RESTLESS_GHOST.getState() == 3;
    }

    @Override
    public int onLoop() {
        Log.info("Getting skull from the altar in the wizard's tower basement.");
        GameObject altar = GameObjects.closest(i -> i.hasID(altarID)
                && i.hasAction("Search")
                && i.canReach()
        );
        if (altar != null) {
            Log.debug("Searching the altar for the skull.");
            new EntityInteractEvent(altar, "Search").setEventCompleteCondition(
                    () -> Inventory.contains(ItemID.GHOSTS_SKULL), Calculations.random(1000, 5000)
            ).execute();
        } else {
            Log.debug("Couldn't find the altar to search, going to the altar area.");
            Walking.walk(ALTAR_AREA.getRandomTile());
        }
        return ReactionGenerator.getNormal();
    }
}
