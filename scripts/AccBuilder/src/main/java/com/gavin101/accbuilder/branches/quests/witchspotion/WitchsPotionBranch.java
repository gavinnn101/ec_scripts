package com.gavin101.accbuilder.branches.quests.witchspotion;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;

public class WitchsPotionBranch extends Branch {
    @Override
    public boolean isValid() {
        if (!Quest.WITCHS_POTION.isFinished()) {
            Main.setActivity("Doing quest: Witch's Potion");
            return true;
        }
        return false;
    }
}
