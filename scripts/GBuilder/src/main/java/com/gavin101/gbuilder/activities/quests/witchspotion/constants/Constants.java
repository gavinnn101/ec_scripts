package com.gavin101.gbuilder.activities.quests.witchspotion.constants;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.quest.Quest;

public class Constants {
    public static final InventoryLoadout WITCHS_POTION_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.COOKED_MEAT, 1)
            .setEnabled(() -> !Inventory.contains("Burnt meat") && Quest.WITCHS_POTION.getState() != 2)
            .addReq(ItemID.BURNT_MEAT, 1)
            .setEnabled(() -> OwnedItems.contains(ItemID.BURNT_MEAT) && Quest.WITCHS_POTION.getState() == 1)
            .addReq(ItemID.RATS_TAIL, 1)
            .setEnabled(() -> OwnedItems.contains(ItemID.RATS_TAIL) && Quest.WITCHS_POTION.getState() == 1)
            .addReq(ItemID.EYE_OF_NEWT, 1)
            .setEnabled(() -> Quest.WITCHS_POTION.getState() != 2)
            .addReq(ItemID.ONION, 1)
            .setEnabled(() -> Quest.WITCHS_POTION.getState() != 2)
            .setLoadoutStrict();

    public static final RectArea WITCHS_POTION_AREA = new RectArea(2965, 3206, 2968, 3205);
    public static final WorldTile RANGE_TILE = new WorldTile(2969, 3210, 0);

    public static final RectArea RAT_AREA = new RectArea(2955, 3204, 2958, 3203);
}
