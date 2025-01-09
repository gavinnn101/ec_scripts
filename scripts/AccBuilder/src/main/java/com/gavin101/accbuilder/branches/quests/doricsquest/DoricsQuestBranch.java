package com.gavin101.accbuilder.branches.quests.doricsquest;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;

public class DoricsQuestBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.DORICS_QUEST.isFinished()) {
            Main.setActivity("Doing quest: Doric's Quest");
            return true;
        }
        return false;
    }
}
