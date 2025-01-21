package com.gavin101.accbuilder.branches.quests.runemysteries;

import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;

public class RuneMysteriesBranch extends Branch {
    @Override
    public boolean isValid() {
        return !Quest.RUNE_MYSTERIES.isFinished();
    }
}
