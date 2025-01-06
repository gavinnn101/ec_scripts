package com.gavin101.events;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.eternalclient.api.accessors.Dialogues;
import net.eternalclient.api.events.AbstractEvent;
import net.eternalclient.api.events.DialogueEvent;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.Entity;
import net.eternalclient.api.wrappers.map.Area;

import java.util.Arrays;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Accessors(chain = true)
public class TalkToNpcEvent extends AbstractEvent {
    // required args
    private final Supplier<Entity> entitySupplier;
    private final String interaction;
    private final Supplier<Boolean> interactionCompleteCondition;
    private final Area entityArea;

    // optional args
    @Setter
    private String[] dialogueOptions = new String[0];

    @Override
    public int onLoop() {
        if (Dialogues.inDialogue()) {
            Log.debug("Finishing dialogue with chat options: " + Arrays.toString(dialogueOptions));
            new DialogueEvent(dialogueOptions).setEventCompleteCondition(
                    () -> !Dialogues.inDialogue(), Calculations.random(2500, 5000)
            ).execute();
            Log.debug("Finished dialogue with npc, marking talk to npc event as finished.");
            setEventCompleted(true);
        } else {
            new EntityHelperEvent(entitySupplier, interaction, interactionCompleteCondition, entityArea).execute();
        }
        return 0;
    }
}
