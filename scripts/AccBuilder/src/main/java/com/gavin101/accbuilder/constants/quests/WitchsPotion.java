package com.gavin101.accbuilder.constants.quests;

import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.quest.Quest;

public class WitchsPotion {
    public static final InventoryLoadout WITCHS_POTION_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.COOKED_MEAT, 1)
            .setEnabled(() -> !Inventory.contains("Burnt meat") && Quest.WITCHS_POTION.getState() != 2)
            .addReq(ItemID.EYE_OF_NEWT, 1)
            .setEnabled(() -> Quest.WITCHS_POTION.getState() != 2)
            .addReq(ItemID.ONION, 1)
            .setEnabled(() -> Quest.WITCHS_POTION.getState() != 2)
            .setStrict(true);

    public static final RectArea WITCHS_POTION_AREA = new RectArea(2965, 3206, 2968, 3205);
    public static final WorldTile RANGE_TILE = new WorldTile(2969, 3210, 0);

    public static final RectArea RAT_AREA = new RectArea(2955, 3204, 2958, 3203);
}
