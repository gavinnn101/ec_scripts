package com.gavin101.accbuilder.leafs.combat;

import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.walking.Walking;

@RequiredArgsConstructor
public class FightMonsterLeaf extends Leaf {
    private final String monsterToFight;
    private final Area monsterArea;

    @Override
    public boolean isValid() {
        return !Players.localPlayer().isInCombat();
    }

    @Override
    public int onLoop() {
        Log.info("Finding monster to fight");
        NPC monster = NPCs.closest(
                i -> i.hasName(monsterToFight)
                        && i.getHealthPercent() > 0
                        && !i.isInCombat()
                        && i.canReach()
        );
        if (monster != null) {
            Log.info("Found valid monster to fight, attacking.");
            new EntityInteractEvent(monster, "Attack").setEventCompleteCondition(
                    () -> Players.localPlayer().isInCombat(), Calculations.random(1000, 2500)
            ).execute();
        } else {
            Log.info("Couldn't find a suitable monster to fight... Walking to monster area.");
            Walking.walk(monsterArea.getRandomTile());
        }
        return ReactionGenerator.getNormal();
    }
}
