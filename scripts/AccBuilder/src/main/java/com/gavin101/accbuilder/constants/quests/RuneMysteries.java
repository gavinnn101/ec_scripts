package com.gavin101.accbuilder.constants.quests;

import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.wrappers.map.PolyArea;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.quest.Quest;

public class RuneMysteries {
    public static final EquipmentLoadout RUNE_MYSTERIES_EQUIPMENT_LOADOUT = new EquipmentLoadout();
    public static final InventoryLoadout RUNE_MYSTERIES_INVENTORY_LOADOUT = new InventoryLoadout()
            .addReq(ItemID.AIR_TALISMAN)
            .setEnabled(() -> Quest.RUNE_MYSTERIES.getState() == 1)
            .addReq(ItemID.RESEARCH_PACKAGE)
            .setEnabled(() -> Quest.RUNE_MYSTERIES.getState() == 3)
            .addReq(ItemID.RESEARCH_NOTES)
            .setEnabled(() -> Quest.RUNE_MYSTERIES.getState() == 5);

    public static final RectArea DUKE_AREA = new RectArea(3213, 3218, 3208, 3225, 1);
    public static final String[] DUKE_CHAT_OPTIONS = {
            "Have you any quests for me?",
            "Yes."
    };

    public static final RectArea SEDRIDOR_AREA = new RectArea(3096, 9574, 3107, 9566);
    public static final String[] SEDRIDOR_CHAT_OPTIONS = {
            "I'm looking for the head wizard.",
            "Okay, here you are.",
            "Go ahead.",
            "Yes, certainly."
    };

    public static final PolyArea AUBURY_AREA = new PolyArea(
            new WorldTile(3252, 3403, 0),
            new WorldTile(3249, 3402, 0),
            new WorldTile(3252, 3399, 0),
            new WorldTile(3254, 3399, 0),
            new WorldTile(3256, 3401, 0),
            new WorldTile(3256, 3403, 0),
            new WorldTile(3254, 3404, 0)
    );
    public static final String[] AUBURY_CHAT_OPTIONS = {
            "I've been sent here with a package for you."
    };
}
