package com.gavin101.amethystaio.branches.FreeToPlayBranches.QuestBranches;

import com.gavin101.amethystaio.Constants;
import com.gavin101.amethystaio.GActivityHelper;
import com.gavin101.amethystaio.Main;
import com.gavin101.amethystaio.QuestRandomizer;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.wrappers.quest.Quest;

public class ImpCatcherBranch extends Branch {
    @Override
    public boolean isValid() {
        if (Quest.IMP_CATCHER.isFinished()) {
            return false;
        }

        if (!QuestRandomizer.isNextQuest(Quest.IMP_CATCHER)) {
            Log.debug("next quest: " +QuestRandomizer.getNextQuest());
            return false;
        }

        if (!GActivityHelper.isCurrentActivity(Quest.IMP_CATCHER)) {
                if (!GActivityHelper.startNewActivity(
                        Quest.IMP_CATCHER,
                        Constants.IMP_CATCHER_QUEST_ITEMS,
                        Quest.IMP_CATCHER::isFinished
                )) {
                    return false;
                }
        }

        if (GActivityHelper.isActivityFinished()) {
            return false;
        }

        return Main.itemsCached
                && !Players.localPlayer().isAnimating()
                && !Players.localPlayer().isMoving();
    }
}
