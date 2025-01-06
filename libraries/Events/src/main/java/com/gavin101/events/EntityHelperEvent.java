package com.gavin101.events;

import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.events.AbstractEvent;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.Entity;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.walking.Walking;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class EntityHelperEvent extends AbstractEvent {
    // required args
    private final Supplier<Entity> entitySupplier;
    private final String interaction;
    private final Supplier<Boolean> interactionCompleteCondition;
    private final Area entityArea;

    @Override
    public int onLoop() {
        Entity entity = entitySupplier.get();
        if (entity != null && entity.canReach()) {
            Log.info("Doing interaction: " +interaction +" on entity: " +entity.getName());
            new EntityInteractEvent(entity, interaction).setEventCompleteCondition(
                    interactionCompleteCondition, Calculations.random(750, 1500)
            ).execute();
        } else {
            Log.info("Can't reach entity, walking to its area.");
            Walking.walk(entityArea.getRandomTile(), () -> entityArea.contains(Players.localPlayer()));
        }
        Log.debug("entity helper event finished, returning.");
        setEventCompleted(true);
        return 0;
    }
}
