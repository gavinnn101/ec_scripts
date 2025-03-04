package com.gavin101.gbuilder.activities.quests.restlessghost.branches;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;

public class TalkToGhostBranch extends Branch {
    @Override
    public boolean isValid() {
        int questState = Quest.THE_RESTLESS_GHOST.getState();
        return questState == 2 || (questState == 4 && Inventory.contains(ItemID.GHOSTS_SKULL));
    }
}
