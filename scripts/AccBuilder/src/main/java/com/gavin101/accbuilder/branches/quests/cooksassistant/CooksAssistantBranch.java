package com.gavin101.accbuilder.branches.quests.cooksassistant;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;

public class CooksAssistantBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.COOKS_ASSISTANT.isFinished()) {
            Main.setActivity("Doing quest: Cook's Assistant");
            return true;
        }
        return false;
    }
}
