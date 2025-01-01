package com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.WitchsPotionLeafs;

import com.gavin101.amethystaio.Constants;
import net.eternalclient.api.accessors.GroundItems;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GroundItem;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.walking.Walking;

public class GetRatTailLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Quest.WITCHS_POTION.getState() == 1
                && !Inventory.contains("Rat's tail")
                && !Players.localPlayer().isInCombat();
    }

    @Override
    public int onLoop() {
        Log.info("Getting rat's tail");
        GroundItem ratTail = GroundItems.closest("Rat's tail");
        if (ratTail != null) {
            Log.debug("Looting rat's tail.");
            new EntityInteractEvent(ratTail, "Take").setEventCompleteCondition(
                    () -> Inventory.contains("Rat's tail"), Calculations.random(1500, 3000)
            ).execute();
        } else {
            NPC rat = NPCs.closest(i -> i.hasName("Rat") && !i.isInCombat());
            if (rat != null && rat.canReach()) {
                Log.debug("Attacking rat.");
                new EntityInteractEvent(rat, "Attack").setEventCompleteCondition(
                        () -> Players.localPlayer().isInCombat(), Calculations.random(1500, 3000)
                ).execute();
            } else {
                Log.debug("Walking to rat area to get tail.");
                Walking.walk(Constants.RAT_AREA.getRandomTile(),
                        () -> Constants.RAT_AREA.contains(Players.localPlayer())
                );
            }
        }
        return ReactionGenerator.getNormal();
    }
}
