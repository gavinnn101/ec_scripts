package com.gavin101.accbuilder.branches.quests.sheepshearer;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;

public class SheepShearerBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.SHEEP_SHEARER.isFinished()) {
            Main.setActivity("Doing quest: Sheep Shearer");
            return true;
        }
        return false;
    }
}
