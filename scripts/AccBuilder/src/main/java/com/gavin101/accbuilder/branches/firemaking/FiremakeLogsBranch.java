package com.gavin101.accbuilder.branches.firemaking;

import com.gavin101.accbuilder.Main;
import lombok.RequiredArgsConstructor;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.frameworks.tree.Branch;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.item.ItemComposite;
import net.eternalclient.api.wrappers.skill.Skill;

@RequiredArgsConstructor
public class FiremakeLogsBranch extends Branch {
    private final int logID;
    private final int tierWoodcuttingLevelGoal; // We should only ever cook food after we're finished woodcutting.

    @Override
    public boolean isValid() {
        if (Skills.getRealLevel(Skill.FIREMAKING) >= logToFiremakingLevel(logID)
                && Skills.getRealLevel(Skill.WOODCUTTING) >= tierWoodcuttingLevelGoal
                && OwnedItems.contains(logID)
        ) {
            Main.setActivity("Firemaking logs: " + ItemComposite.getItem(logID).getName(), Skill.FIREMAKING, 0);
            return true;
        }
        return false;
    }

    private int logToFiremakingLevel(int logID) {
        switch (logID) {
            case ItemID.LOGS:
                return 1;
            case ItemID.OAK_LOGS:
                return 15;
            case ItemID.WILLOW_LOGS:
                return 30;
            case ItemID.MAPLE_LOGS:
                return 45;
            case ItemID.YEW_LOGS:
                return 60;
            case ItemID.MAGIC_LOGS:
                return 75;
            case ItemID.REDWOOD_LOGS:
                return 90;
        }
        return 0;
    }
}
