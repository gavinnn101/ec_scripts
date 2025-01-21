package com.gavin101.accbuilder.constants.quests;

import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;

public class DoricsQuest {
    public static final InventoryLoadout DORICS_QUEST_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.CLAY, 6)
            .addReq(ItemID.COPPER_ORE, 4)
            .addReq(ItemID.IRON_ORE, 2)
            .setLoadoutStrict();

    public static final RectArea DORICS_QUEST_AREA = new RectArea(2949, 3452, 2953, 3449);
}
