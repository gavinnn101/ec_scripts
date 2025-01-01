package com.gavin101.amethystaio.branches.FreeToPlayBranches.QuestBranches;

import com.gavin101.amethystaio.Constants;
import com.gavin101.amethystaio.GActivityHelper;
import com.gavin101.amethystaio.Main;
import com.gavin101.amethystaio.QuestRandomizer;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.wrappers.quest.Quest;

public class GoblinDiplomacyBranch extends Branch {
    @Override
    public boolean isValid() {
        if (Quest.GOBLIN_DIPLOMACY.isFinished()) {
            return false;
        }

        if (!QuestRandomizer.isNextQuest(Quest.GOBLIN_DIPLOMACY)) {
            Log.debug("next quest: " +QuestRandomizer.getNextQuest());
            return false;
        }

        if (!GActivityHelper.isCurrentActivity(Quest.GOBLIN_DIPLOMACY)) {
            Log.debug("Starting Goblin Diplomacy activity");
            if (!GActivityHelper.startNewActivity(
                    Quest.GOBLIN_DIPLOMACY,
                    Constants.GOBLIN_DIPLOMACY_QUEST_ITEMS,
                    Quest.GOBLIN_DIPLOMACY::isFinished
            )) {
                Log.debug("Failed to start Goblin Diplomacy activity");
                return false;
            }
        }

        if (GActivityHelper.isActivityFinished()) {
            Log.debug("Goblin Diplomacy activity is finished");
            return false;
        }

        boolean isValid = Main.itemsCached
                && !Players.localPlayer().isAnimating()
                && !Players.localPlayer().isMoving();

        Log.debug("GoblinDiplomacyBranch validity: " + isValid);
        return isValid;
    }
}