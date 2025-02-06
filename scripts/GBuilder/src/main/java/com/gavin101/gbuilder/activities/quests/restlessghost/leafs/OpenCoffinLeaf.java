package com.gavin101.gbuilder.activities.quests.restlessghost.leafs;

import com.gavin101.gbuilder.activities.quests.restlessghost.Constants;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.walking.Walking;

public class OpenCoffinLeaf extends Leaf {
    private final String[] coffinOptions = {"Open", "Search"};
    @Override
    public boolean isValid() {
        return Constants.getRestlessGhost() == null;
    }

    @Override
    public int onLoop() {
        GameObject coffin = GameObjects.closest(i -> i.hasName("Coffin")
                && (i.hasAction("Open") || i.hasAction("Search"))
                && i.canReach()
                && Constants.GRAVEYARD_COFFIN_AREA.contains(i)
        );
        if (coffin != null) {
            Log.info("Searching coffin");
            new EntityInteractEvent(coffin, coffinOptions).setEventCompleteCondition(
                    () -> Constants.getRestlessGhost() != null, Calculations.random(1500, 3500)
            ).execute();
        } else {
            Log.info("Couldn't find coffin, walking to its area.");
            Walking.walk(Constants.GRAVEYARD_COFFIN_AREA.getRandomTile());
        }
        return ReactionGenerator.getPredictable();
    }
}
