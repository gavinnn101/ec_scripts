package com.gavin101.test.accbuilder.quests.doricsquest;

import com.gavin101.test.accbuilder.goal.QuestGoal;
import com.gavin101.test.accbuilder.goal.branches.ValidateGoalBranch;
import com.gavin101.test.accbuilder.goal.leafs.SetCurrentGoalLeaf;
import com.gavin101.test.accbuilder.quests.QuestBranches;
import com.gavin101.test.accbuilder.utility.branches.LoadoutsFulfilledBranch;
import com.gavin101.test.accbuilder.utility.leafs.GetLoadoutLeaf;
import net.eternalclient.api.frameworks.tree.Branch;

public class DoricsQuestBranch implements QuestBranches {
    @Override
    public Branch getBranch(QuestGoal goal) {
        return new ValidateGoalBranch(goal).addLeafs(
                new SetCurrentGoalLeaf(goal),
                GetLoadoutLeaf.builder()
                        .equipmentLoadout(Constants.DORICS_QUEST_EQUIPMENT)
                        .inventoryLoadout(Constants.DORICS_QUEST_INVENTORY)
                        .buyRemainder(true)
                        .build(),
                new LoadoutsFulfilledBranch(
                        Constants.DORICS_QUEST_EQUIPMENT,
                        Constants.DORICS_QUEST_INVENTORY
                ).addLeafs(
                        new TalkToDoricLeaf()
                ));
    }
}
