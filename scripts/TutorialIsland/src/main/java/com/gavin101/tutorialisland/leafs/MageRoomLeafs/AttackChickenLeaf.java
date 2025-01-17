package com.gavin101.tutorialisland.leafs.MageRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.magic.Magic;
import net.eternalclient.api.wrappers.magic.Spell;
import net.eternalclient.api.wrappers.walking.Walking;

public class AttackChickenLeaf extends Leaf {

    private final Spell windStrike = Magic.getSpellFromName("Wind Strike");

    @Override
    public boolean isValid() {
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 650;
    }

    @Override
    public int onLoop() {
        Log.info("Trying to attack a chicken");
        NPC chicken = getChicken();
        if (chicken != null) {
            if (Magic.canCast(windStrike)) {
                Log.debug("Casting wind strike on chicken.");
                if (Magic.castSpellOn(windStrike, chicken)) {
                    MethodProvider.sleepUntil(
                            () -> PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 670, Calculations.random(2500, 5000)
                    );
                }
            }
        } else {
            Log.warn("Couldn't find a chicken to attack!");
            Log.info("Walking to mage area to find a chicken.");
            Walking.localWalk(Constants.MAGE_ROOM_AREA.getRandomTile(),
            () -> getChicken() != null);
        }
        return ReactionGenerator.getNormal();
    }

    public static NPC getChicken() {
        return NPCs.closest(g -> g.hasName("Chicken")
                && Players.localPlayer().hasLineOfSightTo(g)
                && !g.isInCombat()
        );
    }
}
