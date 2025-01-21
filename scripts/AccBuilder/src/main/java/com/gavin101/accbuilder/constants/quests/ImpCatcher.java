package com.gavin101.accbuilder.constants.quests;

import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;

public class ImpCatcher {
    public static final InventoryLoadout IMP_CATCHER_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.BLACK_BEAD, 1)
            .addReq(ItemID.WHITE_BEAD, 1)
            .addReq(ItemID.YELLOW_BEAD, 1)
            .addReq(ItemID.RED_BEAD, 1)
            .setStrict(true);

    public static final RectArea IMP_CATCHER_AREA = new RectArea(3103, 3163, 3105, 3161, 2);
}
