package com.gavin101.accbuilder;


import com.gavin101.accbuilder.branches.combat.chickens.FightChickensBranch;
import com.gavin101.accbuilder.branches.combat.common.FightMonsterBranch;
import com.gavin101.accbuilder.branches.combat.cows.FightCowsBranch;
import com.gavin101.accbuilder.branches.fishing.shrimp.FishShrimpBranch;
import com.gavin101.accbuilder.branches.fishing.common.StartFishingBranch;
import com.gavin101.accbuilder.constants.ChickenCombat;
import com.gavin101.accbuilder.constants.CowCombat;
import com.gavin101.accbuilder.constants.ShrimpFishing;
import com.gavin101.accbuilder.leafs.combat.EatFoodLeaf;
import com.gavin101.accbuilder.leafs.combat.FightMonsterLeaf;
import com.gavin101.accbuilder.leafs.combat.LootItemsLeaf;
import com.gavin101.accbuilder.leafs.common.GoToAreaLeaf;
import com.gavin101.accbuilder.leafs.combat.SetAttackStyleLeaf;
import com.gavin101.accbuilder.leafs.common.GetLoadoutLeaf;
import com.gavin101.accbuilder.leafs.fishing.StartFishingLeaf;
import net.eternalclient.api.Client;
import net.eternalclient.api.accessors.AttackStyle;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
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
import net.eternalclient.api.wrappers.skill.Skill;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;

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

    public static Skill currentSkillToTrain;
    public static int currentLevelGoal;
    public static AttackStyle attackStyle = ACCURATE;

    public void onStart(String[] args) {
        Log.info("Setting interaction mode to INSTANT_REPLAYED");
        Client.getSettings().setInteractionMode(InteractionMode.INSTANT_REPLAYED);

        startTimer = new Timer();

        tree.addBranches(
                new FishShrimpBranch().addLeafs(
                        new GetLoadoutLeaf(new EquipmentLoadout(), ShrimpFishing.SHRIMP_INVENTORY),
                        new GoToAreaLeaf(ShrimpFishing.LUMBRIDGE_FISHING_AREA, ShrimpFishing.SHRIMP_EQUIPMENT, ShrimpFishing.SHRIMP_INVENTORY),
                        new StartFishingBranch(ShrimpFishing.LUMBRIDGE_FISHING_AREA).addLeafs(
                                new StartFishingLeaf("Net"),
                                new GetLoadoutLeaf(new EquipmentLoadout(), ShrimpFishing.SHRIMP_INVENTORY)
                        )
                ),
                new FightChickensBranch().addLeafs(
                        new GetLoadoutLeaf(ChickenCombat.CHICKEN_EQUIPMENT, ChickenCombat.CHICKEN_INVENTORY),
                        new SetAttackStyleLeaf(),
                        new GoToAreaLeaf(ChickenCombat.CHICKEN_AREA, ChickenCombat.CHICKEN_EQUIPMENT, ChickenCombat.CHICKEN_INVENTORY),
                        new FightMonsterBranch(ChickenCombat.CHICKEN_AREA).addLeafs(
                                new EatFoodLeaf(ItemID.TROUT, 5),
                                new LootItemsLeaf(ChickenCombat.CHICKEN_LOOT, 3),
                                new FightMonsterLeaf("Chicken")
                        )
                ),
                new FightCowsBranch().addLeafs(
                        new GetLoadoutLeaf(CowCombat.COW_EQUIPMENT, CowCombat.COW_INVENTORY),
                        new SetAttackStyleLeaf(),
                        new GoToAreaLeaf(CowCombat.COW_AREA, CowCombat.COW_EQUIPMENT, CowCombat.COW_INVENTORY),
                        new FightMonsterBranch(CowCombat.COW_AREA).addLeafs(
                                new EatFoodLeaf(ItemID.TROUT, 5),
                                new LootItemsLeaf(CowCombat.COW_LOOT, 3),
                                new FightMonsterLeaf("Cow")
                        )
                )
        );
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
                add("Current skill: " +currentSkillToTrain.getName());
                add("Leveling to: " +currentLevelGoal);
            }}.toArray(new String[0]));

    @Override
    public void onPaint(Graphics2D g) {
        paint.paint(g);
    }

    private long calculateExpPerHour(Skill skill) {
        long timeRan = startTimer.elapsed();
        long expGained = SkillTracker.getGainedExperience(skill);

        if (timeRan == 0 || expGained == 0) return 0;

        double hoursElapsed = timeRan / 3600000.0;
        double expPerHour = expGained / hoursElapsed;

        return Math.round(expPerHour);
    }
}