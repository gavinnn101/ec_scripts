package com.gavin101.accbuilder.branches.cooking;

import com.gavin101.accbuilder.Main;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.quest.Quest;
import net.eternalclient.api.wrappers.skill.Skill;

@RequiredArgsConstructor
public class CookFoodBranch extends Branch {
    private final int rawFoodID;

    @Override
    public boolean isValid() {
        if (Quest.COOKS_ASSISTANT.isFinished()
                && OwnedItems.contains(rawFoodID)
                && !Players.localPlayer().isAnimating()
                && !Players.localPlayer().isMoving()) {
            Main.setActivity("Cooking food", Skill.COOKING, 0);
            return true;
        }
        return false;
    }
}
