package com.gavin101.amethystaio.branches.FreeToPlayBranches.QuestBranches;

import com.gavin101.amethystaio.Constants;
import com.gavin101.amethystaio.GActivityHelper;
import com.gavin101.amethystaio.Main;
import com.gavin101.amethystaio.QuestRandomizer;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.wrappers.quest.Quest;

public class WitchsPotionBranch extends Branch {
    @Override
    public boolean isValid() {
        if (Quest.WITCHS_POTION.isFinished()) {
            return false;
        }

        if (!QuestRandomizer.isNextQuest(Quest.WITCHS_POTION)) {
            Log.debug("next quest: " +QuestRandomizer.getNextQuest());
            return false;
        }

        if (!GActivityHelper.isCurrentActivity(Quest.WITCHS_POTION)) {
                if (!GActivityHelper.startNewActivity(
                        Quest.WITCHS_POTION,
                        Constants.WITCHS_POTION_QUEST_ITEMS,
                        Quest.WITCHS_POTION::isFinished
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
