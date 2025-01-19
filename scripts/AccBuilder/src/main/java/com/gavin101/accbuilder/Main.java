package com.gavin101.accbuilder;


import com.gavin101.GLib.GLib;
import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.GLib.leafs.common.*;
import com.gavin101.accbuilder.branches.combat.alkharidguards.FightAlkharidGuardsBranch;
import com.gavin101.accbuilder.branches.combat.barbarians.FightBarbariansBranch;
import com.gavin101.accbuilder.branches.combat.chickens.FightChickensBranch;
import com.gavin101.accbuilder.branches.common.LoadoutsFulfilledBranch;
import com.gavin101.accbuilder.branches.combat.cows.FightCowsBranch;
import com.gavin101.accbuilder.branches.cooking.CookFoodBranch;
import com.gavin101.accbuilder.branches.firemaking.FiremakeLogsBranch;
import com.gavin101.accbuilder.branches.fishing.bait.BaitFishingBranch;
import com.gavin101.accbuilder.branches.fishing.flyfishing.FlyFishingBranch;
import com.gavin101.accbuilder.branches.fishing.shrimp.FishShrimpBranch;
import com.gavin101.accbuilder.branches.mining.MineIronBranch;
import com.gavin101.accbuilder.branches.mining.MineCopperBranch;
import com.gavin101.accbuilder.branches.quests.cooksassistant.CooksAssistantBranch;
import com.gavin101.accbuilder.branches.quests.doricsquest.DoricsQuestBranch;
import com.gavin101.accbuilder.branches.quests.goblindiplomacy.GoblinDiplomacyBranch;
import com.gavin101.accbuilder.branches.quests.impcatcher.ImpCatcherBranch;
import com.gavin101.accbuilder.branches.quests.sheepshearer.SheepShearerBranch;
import com.gavin101.accbuilder.branches.quests.witchspotion.WitchsPotionBranch;
import com.gavin101.accbuilder.branches.tiers.TierOneBranch;
import com.gavin101.accbuilder.branches.tiers.TierThreeBranch;
import com.gavin101.accbuilder.branches.tiers.TierTwoBranch;
import com.gavin101.accbuilder.branches.woodcutting.ChopNormalTreesBranch;
import com.gavin101.accbuilder.branches.woodcutting.ChopOakTreesBranch;
import com.gavin101.accbuilder.branches.woodcutting.ChopWillowTreesBranch;
import com.gavin101.accbuilder.constants.combat.AlkharidGuardsCombat;
import com.gavin101.accbuilder.constants.combat.BarbarianCombat;
import com.gavin101.accbuilder.constants.combat.ChickenCombat;
import com.gavin101.accbuilder.constants.combat.CowCombat;
import com.gavin101.accbuilder.constants.firemaking.Firemaking;
import com.gavin101.accbuilder.constants.fishing.BaitFishing;
import com.gavin101.accbuilder.constants.fishing.Fishing;
import com.gavin101.accbuilder.constants.fishing.FlyFishing;
import com.gavin101.accbuilder.constants.fishing.ShrimpFishing;
import com.gavin101.accbuilder.constants.mining.Mining;
import com.gavin101.accbuilder.constants.quests.*;
import com.gavin101.accbuilder.constants.woodcutting.Woodcutting;
import com.gavin101.accbuilder.leafs.combat.*;
import com.gavin101.accbuilder.leafs.common.*;
import com.gavin101.accbuilder.leafs.cooking.CookFoodLeaf;
import com.gavin101.accbuilder.leafs.firemaking.GoToFiremakingTileLeaf;
import com.gavin101.accbuilder.leafs.firemaking.LightLogsLeaf;
import com.gavin101.accbuilder.leafs.fishing.StartFishingLeaf;
import com.gavin101.accbuilder.leafs.mining.MineRockLeaf;
import com.gavin101.accbuilder.leafs.quests.cooksassistant.TalkToCookLeaf;
import com.gavin101.accbuilder.leafs.quests.doricsquest.TalkToDoricLeaf;
import com.gavin101.accbuilder.leafs.quests.goblindiplomacy.MakeArmorLeaf;
import com.gavin101.accbuilder.leafs.quests.goblindiplomacy.TalkToGeneralLeaf;
import com.gavin101.accbuilder.leafs.quests.impcatcher.TalkToWizardLeaf;
import com.gavin101.accbuilder.leafs.quests.sheepshearer.TalkToFarmerLeaf;
import com.gavin101.accbuilder.leafs.quests.witchspotion.BurnMeatLeaf;
import com.gavin101.accbuilder.leafs.quests.witchspotion.DrinkFromCauldronLeaf;
import com.gavin101.accbuilder.leafs.quests.witchspotion.GetRatTailLeaf;
import com.gavin101.accbuilder.leafs.quests.witchspotion.TalkToWitchLeaf;
import com.gavin101.accbuilder.leafs.woodcutting.ChopTreeLeaf;
import net.eternalclient.api.Client;
import net.eternalclient.api.accessors.AttackStyle;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.frameworks.tree.Tree;
import net.eternalclient.api.internal.InteractionMode;
import net.eternalclient.api.listeners.Notify;
import net.eternalclient.api.listeners.Painter;
import net.eternalclient.api.listeners.message.ChatMessageEvent;
import net.eternalclient.api.listeners.skill.SkillTracker;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.script.ScriptCategory;
import net.eternalclient.api.script.ScriptManifest;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.utilities.paint.CustomPaint;
import net.eternalclient.api.utilities.paint.PaintLocations;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.skill.Skill;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static net.eternalclient.api.accessors.AttackStyle.ACCURATE;

@ScriptManifest(
        name = "Account builder",
        description = "Builds an account",
        author = "Gavin101",
        category = ScriptCategory.AIO,
        version = 1.0
)

public class Main extends AbstractScript implements Painter {
    private final Tree tree = new Tree();
    public static Timer startTimer;
    private final NumberFormat nf = NumberFormat.getInstance();

    public static String currentActivity = "None";
    public static Timer activityTimer;
    public static boolean activityLoadoutsFulfilled = false;
    public static Skill currentSkillToTrain = Skill.RUNECRAFTING;
    public static int currentLevelGoal = 99;
    public static AttackStyle attackStyle = ACCURATE;
    public static final List<WorldTile> firemakingTiles = Firemaking.getRandomFiremakingTileSet();
    public static boolean needToChangeFiremakingTile = false;

    public static EquipmentLoadout currentEquipmentLoadout = new EquipmentLoadout();
    public static InventoryLoadout currentInventoryLoadout = new InventoryLoadout();

    public void onStart(String[] args) {
        Log.info("Setting interaction mode to INSTANT_REPLAYED");
        Client.getSettings().setInteractionMode(InteractionMode.INSTANT_REPLAYED);

        // Save resources
        Client.getSettings().setRenderingEnabled(false);
        Client.getSettings().setFpsLimit(3);

        startTimer = new Timer();
        activityTimer = new Timer();

        tree.addBranches(
                new EnableRunningLeaf(),
                new CacheBankLeaf(),
                new RequestMuleLeaf(() -> OwnedItems.count(ItemID.COINS_995) < 5000),
                new TierOneBranch().addShuffledLeafs(
                        new DoricsQuestBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .equipmentLoadout(DoricsQuest.DORICS_QUEST_EQUIPMENT)
                                        .inventoryLoadout(DoricsQuest.DORICS_QUEST_INVENTORY)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new TalkToDoricLeaf()
                                        )
                                )
                        ),
                        new CooksAssistantBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .inventoryLoadout(CooksAssistant.COOKS_ASSISTANT_INVENTORY)
                                        .equipmentLoadout(CooksAssistant.COOKS_ASSISTANT_EQUIPMENT)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new TalkToCookLeaf()
                                        )
                                )
                        ),
                        new ChopNormalTreesBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .inventoryLoadout(Woodcutting.WOODCUTTING_INVENTORY)
                                        .equipmentLoadout(Woodcutting.WOODCUTTING_EQUIPMENT)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new LootItemsLeaf(Woodcutting.WOODCUTTING_LOOT, 10),
                                                new GoToAreaLeaf(GLib.getRandomArea(Woodcutting.TREE_TO_AREAS_MAP("Tree"))),
                                                new ChopTreeLeaf("Tree")
                                        )
                                )
                        ),
                        new FiremakeLogsBranch(
                                ItemID.LOGS,
                                Woodcutting.NORMAL_TREE_LEVEL_RANGES.get(Skill.WOODCUTTING).getMax()
                        ).addLeafs(
                                new IsAfkBranch().addLeafs(
                                        GetLoadoutLeaf.builder()
                                                .equipmentLoadout(new EquipmentLoadout())
                                                .inventoryLoadout(
                                                        new InventoryLoadout()
                                                                .addReq(ItemID.TINDERBOX)
                                                                .addReq(ItemID.LOGS, () -> Math.min(OwnedItems.count(ItemID.LOGS), 27))
                                                                .setEnabled(() -> !Inventory.contains(ItemID.LOGS))
                                                                .setStrict(true)
                                                )
                                                .buyRemainder(false)
                                                .build(),
                                        new LoadoutsFulfilledBranch().addLeafs(
                                                new GoToFiremakingTileLeaf(firemakingTiles),
                                                new LightLogsLeaf(ItemID.LOGS)
                                        )
                                )
                        ),
                        new MineCopperBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .inventoryLoadout(Mining.MINING_INVENTORY)
                                        .equipmentLoadout(Mining.MINING_EQUIPMENT)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new GoToTileLeaf(GLib.getRandomTile(Mining.ROCK_TO_TILES_MAP("Copper rocks"))),
                                                new MineRockLeaf("Copper rocks")
                                        )
                                )
                        ),
                        new FishShrimpBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .inventoryLoadout(ShrimpFishing.SHRIMP_INVENTORY)
                                        .equipmentLoadout(ShrimpFishing.SHRIMP_EQUIPMENT)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new GoToAreaLeaf(GLib.getRandomArea(Fishing.FISHING_TYPE_TO_AREAS_MAP(Fishing.FishingType.SMALL_FISHING_NET))),
                                                new StartFishingLeaf(Fishing.FishingType.SMALL_FISHING_NET)
                                        )
                                )
                        ),
                        new CookFoodBranch(
                                ItemID.RAW_SHRIMPS,
                                ShrimpFishing.SHRIMP_FISHING_LEVEL_RANGES.get(Skill.FISHING).getMax()
                        ).addLeafs(
                                GetLoadoutLeaf.builder()
                                        .equipmentLoadout(new EquipmentLoadout())
                                        .inventoryLoadout(
                                                new InventoryLoadout()
                                                        .addReq(ItemID.RAW_SHRIMPS, () -> Math.min(OwnedItems.count(ItemID.RAW_SHRIMPS), 28))
                                                        .setEnabled(() -> !Inventory.contains(ItemID.RAW_SHRIMPS))
                                                        .setStrict(true)
                                        )
                                        .buyRemainder(false)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new CookFoodLeaf("Cooking range", ItemID.RAW_SHRIMPS)
                                        )
                                )
                        ),
                        new FightChickensBranch().addLeafs(
                                new SetBestCombatEquipmentLeaf(),
                                GetLoadoutLeaf.builder()
                                        .useBestEquipment(true)
                                        .inventoryLoadout(ChickenCombat.CHICKEN_INVENTORY)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new LootItemsLeaf(ChickenCombat.CHICKEN_LOOT, 3),
                                                new SetAttackStyleLeaf(),
                                                new FightMonsterLeaf("Chicken", GLib.getRandomArea(ChickenCombat.CHICKEN_COMBAT_AREAS))
                                        )
                                )
                        )
                ),
                new TierTwoBranch().addShuffledLeafs(
                        new SheepShearerBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .equipmentLoadout(SheepShearer.SHEEP_SHEARER_EQUIPMENT)
                                        .inventoryLoadout(SheepShearer.SHEEP_SHEARER_INVENTORY)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new TalkToFarmerLeaf()
                                        )
                                )
                        ),
                        new ImpCatcherBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .equipmentLoadout(ImpCatcher.IMP_CATCHER_EQUIPMENT)
                                        .inventoryLoadout(ImpCatcher.IMP_CATCHER_INVENTORY)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new TalkToWizardLeaf()
                                        )
                                )
                        ),
                        new ChopOakTreesBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .inventoryLoadout(Woodcutting.WOODCUTTING_INVENTORY)
                                        .equipmentLoadout(Woodcutting.WOODCUTTING_EQUIPMENT)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new LootItemsLeaf(Woodcutting.WOODCUTTING_LOOT, 10),
                                                new GoToAreaLeaf(GLib.getRandomArea(Woodcutting.TREE_TO_AREAS_MAP("Oak tree"))),
                                                new ChopTreeLeaf("Oak tree")
                                        )
                                )
                        ),
                        new FiremakeLogsBranch(
                                ItemID.OAK_LOGS,
                                Woodcutting.OAK_TREE_LEVEL_RANGES.get(Skill.WOODCUTTING).getMax()
                        ).addLeafs(
                                new IsAfkBranch().addLeafs(
                                        GetLoadoutLeaf.builder()
                                                .equipmentLoadout(new EquipmentLoadout())
                                                .inventoryLoadout(
                                                        new InventoryLoadout()
                                                                .addReq(ItemID.TINDERBOX)
                                                                .addReq(ItemID.OAK_LOGS, () -> Math.min(OwnedItems.count(ItemID.OAK_LOGS), 27))
                                                                .setEnabled(() -> !Inventory.contains(ItemID.OAK_LOGS))
                                                                .setStrict(true)
                                                )
                                                .buyRemainder(false)
                                                .build(),
                                        new LoadoutsFulfilledBranch().addLeafs(
                                                new GoToFiremakingTileLeaf(firemakingTiles),
                                                new LightLogsLeaf(ItemID.OAK_LOGS)
                                        )
                                )
                        ),
                        new MineIronBranch(31).addLeafs(
                                GetLoadoutLeaf.builder()
                                        .inventoryLoadout(Mining.MINING_INVENTORY)
                                        .equipmentLoadout(Mining.MINING_EQUIPMENT)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new GoToTileLeaf(GLib.getRandomTile(Mining.ROCK_TO_TILES_MAP("Iron rocks"))),
                                                new MineRockLeaf("Iron rocks")
                                        )
                                )
                        ),
                        new BaitFishingBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .equipmentLoadout(BaitFishing.BAIT_FISHING_EQUIPMENT)
                                        .inventoryLoadout(BaitFishing.BAIT_FISHING_INVENTORY)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new GoToAreaLeaf(GLib.getRandomArea(Fishing.FISHING_TYPE_TO_AREAS_MAP(Fishing.FishingType.BAIT_FISHING))),
                                                new StartFishingLeaf(Fishing.FishingType.BAIT_FISHING)
                                        )
                                )
                        ),
                        new CookFoodBranch(
                                ItemID.RAW_SARDINE,
                                BaitFishing.BAIT_FISHING_LEVEL_RANGES.get(Skill.FISHING).getMax()
                        ).addLeafs(
                                GetLoadoutLeaf.builder()
                                        .equipmentLoadout(new EquipmentLoadout())
                                        .inventoryLoadout(
                                                new InventoryLoadout()
                                                        .addReq(ItemID.RAW_SARDINE, () -> Math.min(OwnedItems.count(ItemID.RAW_SARDINE), 28))
                                                        .setEnabled(() -> !Inventory.contains(ItemID.RAW_SARDINE))
                                                        .setStrict(true)
                                        )
                                        .buyRemainder(false)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new CookFoodLeaf("Cooking range", ItemID.RAW_SARDINE)
                                        )
                                )
                        ),
                        new CookFoodBranch(
                                ItemID.RAW_HERRING,
                                BaitFishing.BAIT_FISHING_LEVEL_RANGES.get(Skill.FISHING).getMax()
                        ).addLeafs(
                                GetLoadoutLeaf.builder()
                                        .equipmentLoadout(new EquipmentLoadout())
                                        .inventoryLoadout(
                                                new InventoryLoadout()
                                                        .addReq(ItemID.RAW_HERRING, () -> Math.min(OwnedItems.count(ItemID.RAW_HERRING), 28))
                                                        .setEnabled(() -> !Inventory.contains(ItemID.RAW_HERRING))
                                                        .setStrict(true)
                                        )
                                        .buyRemainder(false)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new CookFoodLeaf("Cooking range", ItemID.RAW_HERRING)
                                        )
                                )
                        ),
                        new FightCowsBranch().addLeafs(
                                new SetBestCombatEquipmentLeaf(),
                                GetLoadoutLeaf.builder()
                                        .useBestEquipment(true)
                                        .inventoryLoadout(CowCombat.COW_INVENTORY)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new EatFoodLeaf(ItemID.HERRING),
                                        new IsAfkBranch().addLeafs(
                                                new LootItemsLeaf(CowCombat.COW_LOOT, 3),
                                                new SetAttackStyleLeaf(),
                                                new FightMonsterLeaf("Cow", GLib.getRandomArea(CowCombat.COW_COMBAT_AREAS))
                                        )
                                )
                        )
                ),
                new TierThreeBranch().addShuffledLeafs(
                        new WitchsPotionBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .inventoryLoadout(WitchsPotion.WITCHS_POTION_INVENTORY)
                                        .equipmentLoadout(WitchsPotion.WITCHS_POTION_EQUIPMENT)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new TalkToWitchLeaf(),
                                                new BurnMeatLeaf(),
                                                new GetRatTailLeaf(),
                                                new DrinkFromCauldronLeaf()
                                        )
                                )
                        ),
                        new GoblinDiplomacyBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .equipmentLoadout(GoblinDiplomacy.GOBLIN_DIPLOMACY_EQUIPMENT)
                                        .inventoryLoadout(GoblinDiplomacy.GOBLIN_DIPLOMACY_INVENTORY)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new MakeArmorLeaf(),
                                                new TalkToGeneralLeaf()
                                        )
                                )
                        ),
                        new ChopWillowTreesBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .inventoryLoadout(Woodcutting.WOODCUTTING_INVENTORY)
                                        .equipmentLoadout(Woodcutting.WOODCUTTING_EQUIPMENT)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new LootItemsLeaf(Woodcutting.WOODCUTTING_LOOT, 10),
                                                new GoToAreaLeaf(GLib.getRandomArea(Woodcutting.TREE_TO_AREAS_MAP("Willow tree"))),
                                                new ChopTreeLeaf("Willow tree")
                                        )
                                )
                        ),
                        new FiremakeLogsBranch(
                                ItemID.WILLOW_LOGS,
                                Woodcutting.WILLOW_TREE_LEVEL_RANGES.get(Skill.WOODCUTTING).getMax()
                        ).addLeafs(
                                new IsAfkBranch().addLeafs(
                                        GetLoadoutLeaf.builder()
                                                .equipmentLoadout(new EquipmentLoadout())
                                                .inventoryLoadout(
                                                        new InventoryLoadout()
                                                                .addReq(ItemID.TINDERBOX)
                                                                .addReq(ItemID.WILLOW_LOGS, () -> Math.min(OwnedItems.count(ItemID.WILLOW_LOGS), 27))
                                                                .setEnabled(() -> !Inventory.contains(ItemID.WILLOW_LOGS))
                                                                .setStrict(true)
                                                )
                                                .buyRemainder(false)
                                                .build(),
                                        new LoadoutsFulfilledBranch().addLeafs(
                                                new GoToFiremakingTileLeaf(firemakingTiles),
                                                new LightLogsLeaf(ItemID.WILLOW_LOGS)
                                        )
                                )
                        ),
                        new MineIronBranch(41).addLeafs(
                                GetLoadoutLeaf.builder()
                                        .inventoryLoadout(Mining.MINING_INVENTORY)
                                        .equipmentLoadout(Mining.MINING_EQUIPMENT)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new GoToTileLeaf(GLib.getRandomTile(Mining.ROCK_TO_TILES_MAP("Iron rocks"))),
                                                new MineRockLeaf("Iron rocks")
                                        )
                                )
                        ),
                        new FlyFishingBranch().addLeafs(
                                GetLoadoutLeaf.builder()
                                        .equipmentLoadout(FlyFishing.FLY_FISHING_EQUIPMENT)
                                        .inventoryLoadout(FlyFishing.FLY_FISHING_INVENTORY)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new GoToAreaLeaf(GLib.getRandomArea(Fishing.FISHING_TYPE_TO_AREAS_MAP(Fishing.FishingType.FLY_FISHING))),
                                                new StartFishingLeaf(Fishing.FishingType.FLY_FISHING)
                                        )
                                )
                        ),
                        new CookFoodBranch(
                                ItemID.RAW_TROUT,
                                FlyFishing.FLY_FISHING_LEVEL_RANGES.get(Skill.FISHING).getMax()
                        ).addLeafs(
                                GetLoadoutLeaf.builder()
                                        .equipmentLoadout(new EquipmentLoadout())
                                        .inventoryLoadout(
                                                new InventoryLoadout()
                                                        .addReq(ItemID.RAW_TROUT, () -> Math.min(OwnedItems.count(ItemID.RAW_TROUT), 28))
                                                        .setEnabled(() -> !Inventory.contains(ItemID.RAW_TROUT))
                                                        .setStrict(true)
                                        )
                                        .buyRemainder(false)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new CookFoodLeaf("Cooking range", ItemID.RAW_TROUT)
                                        )
                                )
                        ),
                        new CookFoodBranch(
                                ItemID.RAW_SALMON,
                                FlyFishing.FLY_FISHING_LEVEL_RANGES.get(Skill.FISHING).getMax()
                        ).addLeafs(
                                GetLoadoutLeaf.builder()
                                        .equipmentLoadout(new EquipmentLoadout())
                                        .inventoryLoadout(
                                                new InventoryLoadout()
                                                        .addReq(ItemID.RAW_SALMON, () -> Math.min(OwnedItems.count(ItemID.RAW_SALMON), 28))
                                                        .setEnabled(() -> !Inventory.contains(ItemID.RAW_SALMON))
                                                        .setStrict(true)
                                        )
                                        .buyRemainder(false)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new IsAfkBranch().addLeafs(
                                                new CookFoodLeaf("Cooking range", ItemID.RAW_SALMON)
                                        )
                                )
                        ),
                        new FightBarbariansBranch().addLeafs(
                                new SetBestCombatEquipmentLeaf(),
                                GetLoadoutLeaf.builder()
                                        .useBestEquipment(true)
                                        .inventoryLoadout(BarbarianCombat.BARBARIAN_INVENTORY)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new EatFoodLeaf(ItemID.TROUT),
                                        new IsAfkBranch().addLeafs(
                                                new SetAttackStyleLeaf(),
                                                new FightMonsterLeaf("Barbarian", BarbarianCombat.BARBARIAN_AREA)
                                        )
                                )
                        ),
                        new FightAlkharidGuardsBranch().addLeafs(
                                new SetBestCombatEquipmentLeaf(),
                                GetLoadoutLeaf.builder()
                                        .useBestEquipment(true)
                                        .inventoryLoadout(AlkharidGuardsCombat.ALKHARID_GUARD_INVENTORY)
                                        .buyRemainder(true)
                                        .build(),
                                new LoadoutsFulfilledBranch().addLeafs(
                                        new EatFoodLeaf(ItemID.TROUT),
                                        new IsAfkBranch().addLeafs(
                                                new SetAttackStyleLeaf(),
                                                new FightMonsterLeaf("Al Kharid warrior", AlkharidGuardsCombat.ALKHARID_GUARD_AREA)
                                        )
                                )
                        )
                ),
                new EndScriptLeaf()
        );
    }


    @Override
    public int onLoop() {
        return tree.onLoop();
    }

    private final CustomPaint paint = new CustomPaint(PaintLocations.TOP_LEFT_PLAY_SCREEN)
            .setInfoSupplier(() -> new ArrayList<String>() {{
                add(getScriptName() + " v" + getScriptVersion());
                add("Runtime: " + startTimer);
                add("Current Activity: " + currentActivity);
                add("Current Branch: " + Tree.currentBranch);
                add("Current Leaf: " + Tree.currentLeaf);
                add("Current skill: " + currentSkillToTrain.getName());
                add("Current skill level: " + Skills.getRealLevel(currentSkillToTrain));
                add("Current skill level goal: " + currentLevelGoal);
                add("Current skill xp/hr: " + calculateExpPerHour(currentSkillToTrain));
            }}.toArray(new String[0]));

    @Override
    public void onPaint(Graphics2D g) {
        paint.paint(g);
    }

    private long calculateExpPerHour(Skill skill) {
        long timeRan = activityTimer.elapsed();
        long expGained = SkillTracker.getGainedExperience(skill);

        if (timeRan == 0 || expGained == 0) {
            return 0;
        }

        double hoursElapsed = timeRan / 3600000.0;
        double expPerHour = expGained / hoursElapsed;

        return Math.round(expPerHour);
    }

    public static void setActivity(String activity) {
        setActivity(activity, Skill.RUNECRAFTING, 0);
    }

    public static void setActivity(String activity, Skill skill) {
        setActivity(activity, skill, 0);
    }

    public static void setActivity(String activity, Skill skill, int levelGoal) {
        if (currentActivity.equals(activity)) {
            return;
        }
        SkillTracker.deregister();
        Log.debug("Setting current activity to: " + activity);
        currentActivity = activity;
        activityLoadoutsFulfilled = false;
        currentSkillToTrain = skill;
        currentLevelGoal = levelGoal;
        activityTimer.reset();
        SkillTracker.start(skill);
    }

    @Notify
    public void notifyChatMessageEvent(ChatMessageEvent event) {
        String msg = event.getMessage();
        if (msg.equals("You can't light a fire here.")) {
            Log.debug("Not on a valid firemaking tile, setting boolean to move to a new tile.");
            needToChangeFiremakingTile = true;
        }
    }
}