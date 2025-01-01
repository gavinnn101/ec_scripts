package com.gavin101.amethystaio.branches.FreeToPlayBranches.MiningBranches;

import com.gavin101.amethystaio.Constants;
import com.gavin101.amethystaio.GActivityHelper;
import com.gavin101.amethystaio.Main;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

public class MiningBranch extends Branch {

    public static final String activityName = "Mining";
    @Override
    public boolean isValid() {
        if (!Quest.DORICS_QUEST.isFinished()
                || !Quest.COOKS_ASSISTANT.isFinished()
                || !Quest.GOBLIN_DIPLOMACY.isFinished()
                || !Quest.IMP_CATCHER.isFinished()
                || !Quest.SHEEP_SHEARER.isFinished()
                || !Quest.WITCHS_POTION.isFinished()
        ) {
            return false;
        }

        if (!GActivityHelper.isCurrentActivity(activityName)) {
                if (!GActivityHelper.startNewActivity(
                        activityName,
                        Constants.MINING_BRANCH_ITEMS,
                        () -> Skills.getRealLevel(Skill.MINING) >= 60
                )) {
                    return false;
                } else {
                    Log.debug("Starting to track mining exp/hr.");
                    Main.startTimer = new Timer();
                }
        }

        if (GActivityHelper.isActivityFinished()) {
            return false;
        }

        return Main.itemsCached && !Players.localPlayer().isMoving();
    }
}
