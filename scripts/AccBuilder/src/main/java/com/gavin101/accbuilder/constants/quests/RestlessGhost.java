package com.gavin101.accbuilder.constants.quests;

import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.map.RectArea;

public class RestlessGhost {
    public static final EquipmentLoadout RESTLESS_GHOST_EQUIPMENT = new EquipmentLoadout()
            .addAmulet(ItemID.GHOSTSPEAK_AMULET)
            .setEnabled(() -> OwnedItems.contains(ItemID.GHOSTSPEAK_AMULET)
    );

    public static final InventoryLoadout RESTLESS_GHOST_INVENTORY = new InventoryLoadout()
            .addReq(ItemID.GHOSTSPEAK_AMULET)
            .setEnabled(() -> OwnedItems.contains(ItemID.GHOSTSPEAK_AMULET) && !RESTLESS_GHOST_EQUIPMENT.isFulfilled())
            .addReq(ItemID.GHOSTS_SKULL)
            .setEnabled(() -> OwnedItems.contains(ItemID.GHOSTS_SKULL))
            .setLoadoutStrict();

    public static final RectArea LUMBRIDGE_CHURCH_AREA = new RectArea(3239, 3215, 3247, 3204);
    public static final String[] FATHER_AERECK_CHAT_OPTIONS = {
            "I'm looking for a quest!",
            "Ok, let me help then.",
            "Yes."
    };

    public static final RectArea FATHER_URHNEY_AREA = new RectArea(3151, 3173, 3144, 3177);
    public static final String[] FATHER_URHNEY_CHAT_OPTIONS = {
            "Father Aereck sent me to talk to you.",
            "He's got a ghost haunting his graveyard."
    };

    public static final RectArea GRAVEYARD_COFFIN_AREA = new RectArea(3247, 3195, 3252, 3190);
    public static final String[] GHOST_CHAT_OPTIONS = {
            "Yep, now tell me what the problem is.",
            "Yep, clever aren't I?.",
            "Yes, ok. Do you know WHY you're a ghost?",
            "Yes, ok. Do you know why you're a ghost?"
    };

    public static final RectArea ALTAR_AREA = new RectArea(3112, 9569, 3121, 9564);

    public static NPC getRestlessGhost() {
        return NPCs.closest("Restless ghost");
    }
}
