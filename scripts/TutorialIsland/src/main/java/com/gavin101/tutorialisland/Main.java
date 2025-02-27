package com.gavin101.tutorialisland;

import com.gavin101.GLib.GLib;
import com.gavin101.GLib.branches.common.IsAfkBranch;
import com.gavin101.tutorialisland.branches.*;
import com.gavin101.tutorialisland.leafs.BankRoomLeafs.*;
import com.gavin101.tutorialisland.leafs.CombatRoomLeafs.*;
import com.gavin101.tutorialisland.leafs.CooksRoomLeafs.*;
import com.gavin101.tutorialisland.leafs.GuidesRoomLeafs.CustomizeCharacterLeaf;
import com.gavin101.tutorialisland.leafs.GuidesRoomLeafs.SetDisplayNameLeaf;
import com.gavin101.tutorialisland.leafs.GuidesRoomLeafs.TalkToGuideLeaf;
import com.gavin101.tutorialisland.leafs.MageRoomLeafs.AttackChickenLeaf;
import com.gavin101.tutorialisland.leafs.MageRoomLeafs.OpenMageTabLeaf;
import com.gavin101.tutorialisland.leafs.MageRoomLeafs.TalkToMageLeaf;
import com.gavin101.tutorialisland.leafs.MainlandLeafs.EndScriptLeaf;
import com.gavin101.tutorialisland.leafs.MinerRoomLeafs.*;
import com.gavin101.tutorialisland.leafs.PrayerRoomLeafs.OpenFriendsTabLeaf;
import com.gavin101.tutorialisland.leafs.PrayerRoomLeafs.OpenPrayerTabLeaf;
import com.gavin101.tutorialisland.leafs.PrayerRoomLeafs.TalkToBrotherLeaf;
import com.gavin101.tutorialisland.leafs.QuesterRoomLeafs.OpenQuestTabLeaf;
import com.gavin101.tutorialisland.leafs.QuesterRoomLeafs.TalkToQuesterLeaf;
import com.gavin101.tutorialisland.leafs.SurvivalExpertLeafs.*;
import net.eternalclient.api.Client;
import net.eternalclient.api.events.random.RandomManager;
import net.eternalclient.api.frameworks.tree.Tree;
import net.eternalclient.api.internal.InteractionMode;
import net.eternalclient.api.listeners.Painter;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.script.ScriptCategory;
import net.eternalclient.api.script.ScriptManifest;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.paint.CustomPaint;
import net.eternalclient.api.utilities.paint.PaintLocations;

import java.awt.*;
import java.util.ArrayList;

@ScriptManifest(
        name = "TutorialIsland",
        description = "Completes tutorial island.",
        author = "Gavin101",
        category = ScriptCategory.OTHER,
        version = 1.0
)
public class Main extends AbstractScript implements Painter {

    private final Tree tree = new Tree();
    private Timer startTimer;

    @Override
    public void onStart(String[] strings) {
        Log.info("Setting interaction mode to INSTANT_REPLAYED");
        Client.getSettings().setInteractionMode(InteractionMode.INSTANT_REPLAYED);

        GLib.hideRoofs();

        // TODO: Set rendering and fps limit as script args with defaults.
        GLib.disableRendering();
        GLib.setFpsLimit(3);

        startTimer = new Timer();
        boolean walkToGE = false;
        tree.addBranches(
                new IsAfkBranch().addLeafs(
                        // https://oldschool.runescape.wiki/w/RuneScape:Varplayer/281
                        new GuidesRoomBranch().addLeafs(
                                new SetDisplayNameLeaf(),
                                new CustomizeCharacterLeaf(),
                                new TalkToGuideLeaf()
                        ),
                        new SurvivalExpertBranch().addLeafs(
                                new TalkToExpertLeaf(),
                                new OpenInventoryLeaf(),
                                new FishShrimpLeaf(),
                                new OpenSkillsTabLeaf(),
                                new ChopTreeLeaf(),
                                new LightFireLeaf(),
                                new CookShrimpLeaf()
                        ),
                        new CooksRoomBranch().addLeafs(
                                new TalkToCookLeaf(),
                                new MakeDoughLeaf(),
                                new BakeBreadLeaf()
                        ),
                        new QuesterRoomBranch().addLeafs(
                                new TalkToQuesterLeaf(),
                                new OpenQuestTabLeaf()
                        ),
                        new MinerRoomBranch().addLeafs(
                                new TalkToMinerLeaf(),
                                new MineRocksLeaf(),
                                new SmeltBarLeaf(),
                                new UseAnvilLeaf(),
                                new SmithDaggerLeaf()
                        ),
                        new CombatRoomBranch().addLeafs(
                                new TalkToCombatInstructorLeaf(),
                                new OpenEquipmentTabLeaf(),
                                new OpenEquipmentInterfaceLeaf(),
                                new EquipDaggerLeaf(),
                                new CloseEquipmentInterfaceLeaf(),
                                new EquipSwordLeaf(),
                                new OpenCombatTabLeaf(),
                                new GoToRatPitLeaf(),
                                new MeleeRatLeaf(),
                                new EquipBowLeaf(),
                                new RangeRatLeaf()
                        ),
                        new BankRoomBranch().addLeafs(
                                new GoToBankAreaLeaf(),
                                new OpenBankLeaf(),
                                new CloseBankLeaf(),
                                new UsePollBoothLeaf(),
                                new TalkToAccountGuideLeaf(),
                                new OpenManagementTabLeaf()
                        ),
                        new PrayerRoomBranch().addLeafs(
                                new TalkToBrotherLeaf(),
                                new OpenPrayerTabLeaf(),
                                new OpenFriendsTabLeaf()
                        ),
                        new MageRoomBranch().addLeafs(
                                new TalkToMageLeaf(),
                                new OpenMageTabLeaf(),
                                new AttackChickenLeaf()
                        ),
                        new MainlandBranch().addLeafs(
                                new EndScriptLeaf(walkToGE)
                        )
                )
        );
    }

    @Override
    public void onExit() {
        tree.clear();
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
            }}.toArray(new String[0]));

    @Override
    public void onPaint(Graphics2D g) {
        paint.paint(g);
    }
}