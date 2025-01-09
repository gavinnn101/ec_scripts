package com.gavin101.accbuilder.branches.quests.impcatcher;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;

public class ImpCatcherBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.IMP_CATCHER.isFinished()) {
            Main.setActivity("Doing quest: Imp Catcher");
            return true;
        }
        return false;
    }
}
