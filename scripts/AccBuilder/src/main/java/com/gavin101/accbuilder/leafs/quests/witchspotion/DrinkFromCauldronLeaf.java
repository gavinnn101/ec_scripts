package com.gavin101.accbuilder.leafs.quests.witchspotion;

import com.gavin101.accbuilder.constants.quests.WitchsPotion;
import net.eternalclient.api.accessors.Dialogues;
import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.events.DialogueEvent;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.walking.Walking;

public class DrinkFromCauldronLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Quest.WITCHS_POTION.getState() == 2;
    }

    @Override
    public int onLoop() {
        Log.debug("Drink from cauldron leaf");
        if (Dialogues.inDialogue()) {
            Log.debug("Finishing cauldron dialogue.");
            new DialogueEvent().setEventCompleteCondition(
                    () -> !Dialogues.inDialogue(), Calculations.random(2500, 5000)
            ).execute();
        } else {
            Log.info("Drinking from cauldron.");
            GameObject cauldron = GameObjects.closest("Cauldron");
            if (cauldron != null && cauldron.canReach()) {
                Log.debug("Interacting with cauldron: 'Drink-from'.");
                new EntityInteractEvent(cauldron, "Drink-from").setEventCompleteCondition(
                        Quest.WITCHS_POTION::isFinished, Calculations.random(1000, 2500)
                ).execute();
            } else {
                Log.debug("Walking to cauldron area");
                Walking.walk(WitchsPotion.WITCHS_POTION_AREA.getRandomTile(),
                        () -> WitchsPotion.WITCHS_POTION_AREA.contains(Players.localPlayer())
                );
            }
        }
        return ReactionGenerator.getNormal();
    }
}
