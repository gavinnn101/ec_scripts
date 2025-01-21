package com.gavin101.accbuilder.branches.quests.restlessghost;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;

public class RestlessGhostBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.THE_RESTLESS_GHOST.isFinished()) {
            Main.setActivity("Doing quest: Restless ghost");
            return true;
        }
        return false;
    }
}
