package com.gavin101.accbuilder.branches.quests.goblindiplomacy;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;

public class GoblinDiplomacyBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.GOBLIN_DIPLOMACY.isFinished()) {
            Main.setActivity("Doing quest: Goblin Diplomacy");
            return true;
        }
        return false;
    }
}
