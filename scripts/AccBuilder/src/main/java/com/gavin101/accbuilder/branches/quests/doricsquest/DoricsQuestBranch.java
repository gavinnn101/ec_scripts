package com.gavin101.accbuilder.branches.quests.doricsquest;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.quest.Quest;

public class DoricsQuestBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.DORICS_QUEST.isFinished()
                && Quest.COOKS_ASSISTANT.isFinished()
                && !OwnedItems.contains(i -> i.containsName("Raw"))
                && !Players.localPlayer().isMoving()
                && !Players.localPlayer().isAnimating()) {
            Main.setActivity("Doing quest: Doric's Quest");
            return true;
        }
        return false;
    }
}
