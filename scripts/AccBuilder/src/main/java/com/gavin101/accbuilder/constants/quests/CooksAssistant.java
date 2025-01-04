package com.gavin101.accbuilder.constants.quests;

import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;

public class CooksAssistant {
    public static EquipmentLoadout COOKS_ASSISTANT_EQUIPMENT = new EquipmentLoadout();

    public static InventoryLoadout COOKS_ASSISTANT_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.EGG)
            .addReq(ItemID.POT_OF_FLOUR)
            .addReq(ItemID.BUCKET_OF_MILK)
            .setStrict(true);

    public static final RectArea COOKS_ASSISTANT_AREA = new RectArea(3209, 3216, 3211, 3213);
}
