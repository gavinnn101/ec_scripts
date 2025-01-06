package com.gavin101.test.accbuilder.quests;

import com.gavin101.test.accbuilder.goal.QuestGoal;
import net.eternalclient.api.frameworks.tree.Branch;

public interface QuestBranches {
    Branch getBranch(QuestGoal goal);
}
