package com.gavin101.amethystaio;

import com.gavin101.amethystaio.branches.CommonBranches.BuyItemsBranch;
import com.gavin101.amethystaio.branches.CommonBranches.CacheItemsBranch;
import com.gavin101.amethystaio.branches.CommonBranches.NeedToHopWorldsBranch;
import com.gavin101.amethystaio.branches.FreeToPlayBranches.FreeToPlayBranch;
import com.gavin101.amethystaio.branches.FreeToPlayBranches.MiningBranches.MineCopperBranch;
import com.gavin101.amethystaio.branches.FreeToPlayBranches.MiningBranches.MineIronBranch;
import com.gavin101.amethystaio.branches.FreeToPlayBranches.MiningBranches.MiningBranch;
import com.gavin101.amethystaio.branches.FreeToPlayBranches.MiningBranches.UpgradePickaxeBranch;
import com.gavin101.amethystaio.branches.FreeToPlayBranches.QuestBranches.*;
import com.gavin101.amethystaio.branches.PayToPlayBranches.PayToPlayBranch;
import com.gavin101.amethystaio.leafs.CommonLeafs.BuyItemsLeafs.BuyItemsLeaf;
import com.gavin101.amethystaio.leafs.CommonLeafs.BuyItemsLeafs.RequestMuleLeaf;
import com.gavin101.amethystaio.leafs.CommonLeafs.CacheItemsLeafs.CacheItemsLeaf;
import com.gavin101.amethystaio.leafs.CommonLeafs.HopWorldsLeaf;
import com.gavin101.amethystaio.leafs.CommonLeafs.MiningLeafs.*;
import com.gavin101.amethystaio.leafs.CommonLeafs.TakeBreakLeaf;
import com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.CommonQuestLeafs.WithdrawItemsLeaf;
import com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.CooksAssistantLeafs.TalkToCookLeaf;
import com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.DoricsQuestLeafs.TalkToDoricLeaf;
import com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.GoblinDiplomacyLeafs.MakeArmorLeaf;
import com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.GoblinDiplomacyLeafs.TalkToGeneralLeaf;
import com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.ImpCatcherLeafs.TalkToWizardLeaf;
import com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.SheepShearerLeafs.TalkToFarmerLeaf;
import com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.WitchsPotionLeafs.BurnMeatLeaf;
import com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.WitchsPotionLeafs.DrinkFromCauldronLeaf;
import com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.WitchsPotionLeafs.GetRatTailLeaf;
import com.gavin101.amethystaio.leafs.FreeToPlayLeafs.QuestLeafs.WitchsPotionLeafs.TalkToHettyLeaf;
import com.gavin101.amethystaio.leafs.PayToPlayLeafs.MiningLeafs.IronMiningLeafs.BankIronOreLeaf;
import com.gavin101.breakhelper.branches.TakeBreakBranch;
import net.eternalclient.api.Client;
import net.eternalclient.api.accessors.Skills;
import net.eternalclient.api.frameworks.tree.Tree;
import net.eternalclient.api.internal.InteractionMode;
import net.eternalclient.api.listeners.Painter;
import net.eternalclient.api.listeners.skill.SkillTracker;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.script.ScriptCategory;
import net.eternalclient.api.script.ScriptManifest;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.paint.CustomPaint;
import net.eternalclient.api.utilities.paint.PaintLocations;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.skill.Skill;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;

@ScriptManifest(
        name = "AmethystAIO",
        description = "AIO Amethyst builder.",
        author = "Gavin101",
        category = ScriptCategory.MONEYMAKING,
        version = 1.0
)
public class Main extends AbstractScript implements Painter {

    private final Tree tree = new Tree();
    public static Timer startTimer;
    private final NumberFormat nf = NumberFormat.getInstance();

    public static final Area muleArea = Constants.GRAND_EXCHANGE_AREA;

    public static boolean itemsCached = false;
    public static boolean needToHopWorlds = false;

    public static boolean freeToPlayFinished = false;

    public static WorldTile miningSpot = null;

    public static int efAccountId;
    public static String efApiKey;

    @Override
    public void onStart(String[] args) {
        parseArgs(args);
        Log.info("Setting interaction mode to INSTANT_REPLAYED");
        Client.getSettings().setInteractionMode(InteractionMode.INSTANT_REPLAYED);
        startTimer = new Timer();
        SkillTracker.start(Skill.MINING);
        QuestRandomizer.setRandomQuestOrder();
        tree.addBranches(
                new CacheItemsBranch().addLeafs(
                        new CacheItemsLeaf()
                ),
                new TakeBreakBranch().addLeafs(
                        new TakeBreakLeaf()
                ),
                new FreeToPlayBranch().addLeafs(
                        new NeedToHopWorldsBranch().addLeafs(
                                new HopWorldsLeaf(false)
                        ),
                        new DoricsQuestBranch().addLeafs(
                                new BuyItemsBranch(Constants.DORICS_QUEST_ITEMS).addLeafs(
                                        new BuyItemsLeaf(Constants.DORICS_QUEST_ITEMS),
                                        new RequestMuleLeaf()
                                ),
                                new WithdrawItemsLeaf(Constants.DORICS_QUEST_ITEMS),
                                new TalkToDoricLeaf()
                        ),
                        new CooksAssistantBranch().addLeafs(
                                new BuyItemsBranch(Constants.COOKS_ASSISTANT_QUEST_ITEMS).addLeafs(
                                        new BuyItemsLeaf(Constants.COOKS_ASSISTANT_QUEST_ITEMS),
                                        new RequestMuleLeaf()
                                ),
                                new WithdrawItemsLeaf(Constants.COOKS_ASSISTANT_QUEST_ITEMS),
                                new TalkToCookLeaf()
                        ),
                        new GoblinDiplomacyBranch().addLeafs(
                                new BuyItemsBranch(Constants.GOBLIN_DIPLOMACY_QUEST_ITEMS).addLeafs(
                                        new BuyItemsLeaf(Constants.GOBLIN_DIPLOMACY_QUEST_ITEMS),
                                        new RequestMuleLeaf()
                                ),
                                new WithdrawItemsLeaf(Constants.GOBLIN_DIPLOMACY_QUEST_ITEMS),
                                new MakeArmorLeaf(),
                                new TalkToGeneralLeaf()
                        ),
                        new ImpCatcherBranch().addLeafs(
                                new BuyItemsBranch(Constants.IMP_CATCHER_QUEST_ITEMS).addLeafs(
                                        new BuyItemsLeaf(Constants.IMP_CATCHER_QUEST_ITEMS),
                                        new RequestMuleLeaf()
                                ),
                                new WithdrawItemsLeaf(Constants.IMP_CATCHER_QUEST_ITEMS),
                                new TalkToWizardLeaf()
                        ),
                        new SheepShearerBranch().addLeafs(
                                new BuyItemsBranch(Constants.SHEEP_SHEARER_QUEST_ITEMS).addLeafs(
                                        new BuyItemsLeaf(Constants.SHEEP_SHEARER_QUEST_ITEMS),
                                        new RequestMuleLeaf()
                                ),
                                new WithdrawItemsLeaf(Constants.SHEEP_SHEARER_QUEST_ITEMS),
                                new TalkToFarmerLeaf()
                        ),
                        new WitchsPotionBranch().addLeafs(
                                new BuyItemsBranch(Constants.WITCHS_POTION_QUEST_ITEMS).addLeafs(
                                        new BuyItemsLeaf(Constants.WITCHS_POTION_QUEST_ITEMS),
                                        new RequestMuleLeaf()
                                ),
                                new WithdrawItemsLeaf(Constants.WITCHS_POTION_QUEST_ITEMS),
                                new TalkToHettyLeaf(),
                                new BurnMeatLeaf(),
                                new GetRatTailLeaf(),
                                new DrinkFromCauldronLeaf()
                        ),
                        new MiningBranch().addLeafs(
                                new BuyItemsBranch(Constants.MINING_BRANCH_ITEMS).addLeafs(
                                        new BuyItemsLeaf(Constants.MINING_BRANCH_ITEMS),
                                        new RequestMuleLeaf()
                                ),
                                new UpgradePickaxeBranch().addLeafs(
                                        new UpgradePickaxeLeaf()
                                ),
                                new MineCopperBranch().addLeafs(
                                        new GoToMiningAreaLeaf(Constants.F2P_COPPER_SPOTS, Constants.F2P_MINING_AREA),
                                        new ChooseMiningSpotLeaf(Constants.F2P_COPPER_SPOTS, Constants.F2P_MINING_AREA),
                                        new GoToMiningSpotLeaf(),
                                        new MineRockLeaf("Copper rocks"),
                                        new DropOresLeaf()
                                ),
                                new MineIronBranch().addLeafs(
                                        new GoToMiningAreaLeaf(Constants.F2P_IRON_SPOTS, Constants.F2P_MINING_AREA),
                                        new ChooseMiningSpotLeaf(Constants.F2P_IRON_SPOTS, Constants.F2P_MINING_AREA),
                                        new GoToMiningSpotLeaf(),
                                        new MineRockLeaf("Iron rocks"),
                                        new DropOresLeaf()
                                )
                        )
                ),
                new PayToPlayBranch().addLeafs(
                        new NeedToHopWorldsBranch().addLeafs(
                                new HopWorldsLeaf(true)
                        ),
                        new com.gavin101.amethystaio.branches.PayToPlayBranches.MiningBranches.MineIronBranch().addLeafs(
                                new GoToMiningAreaLeaf(Constants.MINING_GUILD_IRON_SPOTS, Constants.MINING_GUILD_IRON_AREA),
                                new ChooseMiningSpotLeaf(Constants.MINING_GUILD_IRON_SPOTS, Constants.MINING_GUILD_IRON_AREA),
                                new GoToMiningSpotLeaf(),
                                new MineRockLeaf("Iron rocks"),
                                new BankIronOreLeaf()
                        )
                )
        );
    }

    @Override
    public void onExit() {
        tree.clear();
        SkillTracker.deregister();
    }

    @Override
    public int onLoop() {
        return tree.onLoop();
    }

    private final CustomPaint paint = new CustomPaint(PaintLocations.TOP_LEFT_PLAY_SCREEN)
            .setInfoSupplier(() -> new ArrayList<String>()
            {{
                add(getScriptName() + " v" + getScriptVersion());
                add("Runtime: " + startTimer);
                add("Current Branch: " + Tree.currentBranch);
                add("Current Leaf: " + Tree.currentLeaf);
                add("Mining level: " + Skills.getRealLevel(Skill.MINING));
                add("Mining exp/hr: " +nf.format(calculateExpPerHour()));
                add("Time until next break: " +BreakHelper.getTimeUntilBreak());
                add("Break time left: " +BreakHelper.getBreakTimeLeft());
            }}.toArray(new String[0]));

    @Override
    public void onPaint(Graphics2D g) {
        paint.paint(g);
    }

    private long calculateExpPerHour() {
        long timeRan = startTimer.elapsed();
        long expGained = SkillTracker.getGainedExperience(Skill.MINING);

        if (timeRan == 0 || expGained == 0) return 0;

        double hoursElapsed = timeRan / 3600000.0;
        double expPerHour = expGained / hoursElapsed;

        return Math.round(expPerHour);
    }

    private static void parseArgs(String[] args) {
        for (String arg : args) {
            String[] parts = arg.split(":");
            if (parts.length == 2) {
                String key = parts[0];
                String value = parts[1];
                switch (key) {
                    case "ef_account_id":
                        try {
                            efAccountId = Integer.parseInt(value);
                            Log.info("Parsed ef_account_id: " + efAccountId);
                        } catch (NumberFormatException e) {
                            Log.error("Invalid ef_account_id. Expected an integer, got: " + value);
                        }
                        break;
                    case "ef_api_key":
                        efApiKey = value;
                        Log.info("Parsed ef_api_key: " + efApiKey);
                        break;
                }
            }
        }
    }
}