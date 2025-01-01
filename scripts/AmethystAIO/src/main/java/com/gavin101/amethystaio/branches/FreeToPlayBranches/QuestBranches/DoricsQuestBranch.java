package com.gavin101.amethystaio.branches.FreeToPlayBranches.QuestBranches;

import com.gavin101.amethystaio.Constants;
import com.gavin101.amethystaio.GActivityHelper;
import com.gavin101.amethystaio.Main;
import com.gavin101.amethystaio.QuestRandomizer;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.wrappers.quest.Quest;

public class DoricsQuestBranch extends Branch {
    @Override
    public boolean isValid() {
        if (Quest.DORICS_QUEST.isFinished()) {
            return false;
        }

        if (!QuestRandomizer.isNextQuest(Quest.DORICS_QUEST)) {
            Log.debug("next quest: " +QuestRandomizer.getNextQuest());
            return false;
        }

        if (!GActivityHelper.isCurrentActivity(Quest.DORICS_QUEST)) {
            if (!GActivityHelper.startNewActivity(
                    Quest.DORICS_QUEST,
                    Constants.DORICS_QUEST_ITEMS,
                    Quest.DORICS_QUEST::isFinished)) {
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