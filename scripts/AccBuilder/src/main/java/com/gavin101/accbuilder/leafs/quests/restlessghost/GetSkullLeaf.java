package com.gavin101.accbuilder.leafs.quests.restlessghost;

import com.gavin101.accbuilder.constants.quests.RestlessGhost;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.interactives.Player;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.walking.Walking;

public class GetSkullLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Quest.THE_RESTLESS_GHOST.getState() == 3;
    }

    @Override
    public int onLoop() {
        Log.info("Getting skull from the altar in the wizard's tower basement.");
        GameObject altar = GameObjects.closest(i -> i.hasName("Altar")
                && i.hasAction("Search")
                && i.canReach()
        );
        if (altar != null) {
            Log.debug("Searching the altar for the skull.");
            new EntityInteractEvent(altar, "Search").setEventCompleteCondition(
                    () -> Inventory.contains("Ghost's skull"), Calculations.random(1000, 5000)
            ).execute();
        } else {
            Log.debug("Couldn't find the altar to search, going to the altar area.");
            Walking.walk(RestlessGhost.ALTAR_AREA.getRandomTile());
        }
        return ReactionGenerator.getNormal();
    }
}
