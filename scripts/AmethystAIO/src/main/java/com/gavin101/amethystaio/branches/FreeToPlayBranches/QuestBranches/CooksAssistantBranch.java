package com.gavin101.amethystaio.branches.FreeToPlayBranches.QuestBranches;

import com.gavin101.amethystaio.Constants;
import com.gavin101.amethystaio.GActivityHelper;
import com.gavin101.amethystaio.Main;
import com.gavin101.amethystaio.QuestRandomizer;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.wrappers.quest.Quest;

public class CooksAssistantBranch extends Branch {
    @Override
    public boolean isValid() {
        if (Quest.COOKS_ASSISTANT.isFinished()) {
            return false;
        }

        if (!QuestRandomizer.isNextQuest(Quest.COOKS_ASSISTANT)) {
            return false;
        }

        if (!GActivityHelper.isCurrentActivity(Quest.COOKS_ASSISTANT)) {
            if (!GActivityHelper.startNewActivity(
                    Quest.COOKS_ASSISTANT,
                    Constants.COOKS_ASSISTANT_QUEST_ITEMS,
                    Quest.COOKS_ASSISTANT::isFinished)) {
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