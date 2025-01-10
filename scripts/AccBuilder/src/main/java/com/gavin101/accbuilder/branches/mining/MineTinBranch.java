package com.gavin101.accbuilder.branches.mining;

import com.gavin101.accbuilder.Main;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class MineTinBranch extends Branch {
    @Override
    public boolean isValid() {
        int miningLevel = Skills.getRealLevel(Skill.MINING);
        if (miningLevel >= 1 && miningLevel < 15 && Quest.DORICS_QUEST.isFinished()) {
            Main.setActivity("Mining tin until lv15 mining.", Skill.MINING, 15);
            return true;
        }
        return false;
    }
}
