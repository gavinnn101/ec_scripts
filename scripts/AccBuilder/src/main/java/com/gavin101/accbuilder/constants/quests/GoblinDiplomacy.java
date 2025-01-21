package com.gavin101.accbuilder.constants.quests;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.quest.Quest;

public class GoblinDiplomacy {
    public static final RectArea GOBLIN_DIPLOMACY_AREA = new RectArea(2961, 3513, 2956, 3510, 0);

    public static final InventoryLoadout GOBLIN_DIPLOMACY_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.GOBLIN_MAIL, 3)
            .setEnabled(() -> (!Inventory.contains(ItemID.GOBLIN_MAIL)) && Quest.GOBLIN_DIPLOMACY.isNotStarted())
            .addReq(ItemID.ORANGE_DYE, 1)
            .setEnabled(() -> !Inventory.contains(ItemID.ORANGE_GOBLIN_MAIL) && Quest.GOBLIN_DIPLOMACY.isNotStarted())
            .addReq(ItemID.BLUE_DYE, 1)
            .setEnabled(() -> !Inventory.contains(ItemID.BLUE_GOBLIN_MAIL) && Quest.GOBLIN_DIPLOMACY.isNotStarted());
}
