package com.gavin101.accbuilder.constants.quests;

import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;

public class SheepShearer {
    public static final InventoryLoadout SHEEP_SHEARER_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.BALL_OF_WOOL, 20)
            .setStrict(true);

    public static final RectArea SHEEP_SHEARER_AREA = new RectArea(3184, 3279, 3191, 3270, 0);

}
