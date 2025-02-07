package com.gavin101.gbuilder.utility.leafs;

import com.gavin101.gbuilder.Main;
import com.gavin101.gbuilder.fatiguetracker.FatigueTracker;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.events.random.RandomManager;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.map.Map;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.walking.Walking;

public class HandleDeathLeaf extends Leaf {
    private final RectArea LUMBRIDGE_GRAVEYARD_AREA = new RectArea(3238, 3191, 3242, 3196);

    @Override
    public boolean isValid() {
        return Main.died;
    }

    @Override
    public int onLoop() {
        Log.info("We died and have a gravestone, going to death's office to collect our items.");
        if (!RandomManager.isHandleDeathsOffice()) {
            Log.info("Handling Death's office was disabled, enabling it.");
            RandomManager.setHandleDeathsOffice(true);
        }

        GameObject deathsDomain = GameObjects.closest(i -> i.hasName("Death's Domain") && i.canReach());
        if (deathsDomain != null) {
            Log.info("Entering Death's Domain");
            new EntityInteractEvent(deathsDomain, "Enter").setEventCompleteCondition(Map::isInInstance).execute();
        } else {
            Log.info("Walking to the Lumbridge Graveyard to enter Death's Domain.");
            Walking.walk(LUMBRIDGE_GRAVEYARD_AREA.getRandomTile());
        }
        return FatigueTracker.getCurrentReactionTime();
    }
}
