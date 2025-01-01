package com.gavin101.amethystaio;

import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.wrappers.map.RectArea;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.quest.Quest;

import java.util.List;
import java.util.Map;

public class Constants {
    public static final RectArea GRAND_EXCHANGE_AREA = new RectArea(3146, 3507, 3182, 3470);

    // F2P vars
    public static final List<Quest> F2P_QUESTS = List.of(
            Quest.DORICS_QUEST,
            Quest.COOKS_ASSISTANT,
            Quest.GOBLIN_DIPLOMACY,
            Quest.IMP_CATCHER,
            Quest.SHEEP_SHEARER,
            Quest.WITCHS_POTION
    );

    public static final RectArea F2P_MINING_AREA = new RectArea(2972, 3242, 2975, 3237);

    public static final Map<Integer, Integer> MINING_BRANCH_ITEMS = Map.of(
            ItemID.RUNE_PICKAXE, 1,
            ItemID.ADAMANT_PICKAXE, 1,
            ItemID.MITHRIL_PICKAXE, 1,
            ItemID.STEEL_PICKAXE, 1,
            ItemID.IRON_PICKAXE, 1
    );

    public static final List<WorldTile> F2P_COPPER_SPOTS = List.of(
            new WorldTile(2977, 3246, 0),
            new WorldTile(2978, 3247, 0),
            new WorldTile(2977, 3248, 0)
    );

    public static final List<WorldTile> F2P_IRON_SPOTS = List.of(
            new WorldTile(2969, 3241, 0),
            new WorldTile(2968, 3240, 0),
            new WorldTile(2969, 3239, 0),
            new WorldTile(2981, 3234, 0),
            new WorldTile(2982, 3233, 0)
    );

    public static final Map<Integer, Integer> DORICS_QUEST_ITEMS = Map.of(
            ItemID.CLAY, 6,
            ItemID.COPPER_ORE, 4,
            ItemID.IRON_ORE, 2
    );

    public static final RectArea DORICS_HOUSE_AREA = new RectArea(2950, 3451, 2952, 3449);

    public static final RectArea COOKS_ASSISTANT_AREA = new RectArea(3206, 3216, 3210, 3213);

    public static final Map<Integer, Integer> COOKS_ASSISTANT_QUEST_ITEMS = Map.of(
            ItemID.EGG, 1,
            ItemID.BUCKET_OF_MILK, 1,
            ItemID.POT_OF_FLOUR, 1
    );

    public static final RectArea GOBLIN_DIPLOMACY_AREA = new RectArea(2956, 3512, 2958, 3510);

    public static final Map<Integer, Integer> GOBLIN_DIPLOMACY_QUEST_ITEMS = Map.of(
            ItemID.GOBLIN_MAIL, 3,
            ItemID.BLUE_DYE, 1,
            ItemID.ORANGE_DYE, 1
    );

    public static final WorldTile IMP_CATCHER_TILE = new WorldTile(3104, 3161, 2);

    public static final Map<Integer, Integer> IMP_CATCHER_QUEST_ITEMS = Map.of(
            ItemID.BLACK_BEAD, 1,
            ItemID.YELLOW_BEAD, 1,
            ItemID.RED_BEAD, 1,
            ItemID.WHITE_BEAD, 1
    );

    public static final RectArea SHEEP_SHEARER_AREA = new RectArea(3189, 3274, 3190, 3272);

    public static final Map<Integer, Integer> SHEEP_SHEARER_QUEST_ITEMS = Map.of(
            ItemID.BALL_OF_WOOL, 20
    );

    public static final RectArea WITCHS_POTION_AREA = new RectArea(2965, 3206, 2968, 3205);

    public static final Map<Integer, Integer> WITCHS_POTION_QUEST_ITEMS = Map.of(
            ItemID.COOKED_MEAT, 1,
            ItemID.EYE_OF_NEWT, 1,
            ItemID.ONION, 1
    );

    public static final WorldTile RANGE_TILE = new WorldTile(2969, 3210, 0);

    public static final RectArea RAT_AREA = new RectArea(2955, 3204, 2958, 3203);

    // P2P VARS
    public static final RectArea MINING_GUILD_IRON_AREA = new RectArea(3015, 9720, 3017, 9718);

    public static final List<WorldTile> MINING_GUILD_IRON_SPOTS = List.of(
            new WorldTile(3021, 9721, 0),
            new WorldTile(3024, 9725, 0),
            new WorldTile(3029, 9720, 0)
    );
}
