package com.gavin101.gbuilder.activities.quests.restlessghost;

import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.map.RectArea;

public class Constants {
    public static final RectArea GRAVEYARD_COFFIN_AREA = new RectArea(3247, 3195, 3252, 3190);

    public static NPC getRestlessGhost() {
        return NPCs.closest("Restless ghost");
    }
}
