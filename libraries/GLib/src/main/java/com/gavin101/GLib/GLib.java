package com.gavin101.GLib;

import net.eternalclient.api.Client;
import net.eternalclient.api.accessors.*;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.data.VarClientInt;
import net.eternalclient.api.data.VarPlayer;
import net.eternalclient.api.events.DialogueEvent;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.events.LogoutEvent;
import net.eternalclient.api.events.WidgetEvent;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.events.muling.MuleRequestEvent;
import net.eternalclient.api.events.muling.RequiredItem;
import net.eternalclient.api.events.random.RandomManager;
import net.eternalclient.api.events.random.events.LoginEvent;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.Timer;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.item.ItemComposite;
import net.eternalclient.api.wrappers.map.Area;
import net.eternalclient.api.wrappers.map.WorldTile;
import net.eternalclient.api.wrappers.skill.Skill;
import net.eternalclient.api.wrappers.tabs.Tab;
import net.eternalclient.api.wrappers.tabs.Tabs;
import net.eternalclient.api.wrappers.walking.Walking;
import net.eternalclient.api.wrappers.widgets.Widget;
import net.eternalclient.api.wrappers.widgets.WidgetChild;
import net.eternalfarm.client.EternalFarmClient;
import net.eternalfarm.client.entities.EFAccount;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class GLib {
    public static void talkWithNpc(String npcName) {
        Log.info("Talking to: " +npcName);
        if (Dialogues.inDialogue()) {
            Log.debug("Finishing dialogue with: " +npcName);
            new DialogueEvent().setEventCompleteCondition(
                    () -> !Dialogues.inDialogue(), Calculations.random(2500, 5000)
            ).execute();
        } else {
            Log.debug("Starting talk with: " +npcName);
            NPC npc = NPCs.closest(npcName);
            if (npc != null && npc.canReach()) {
                Log.debug("Selecting 'Talk-to' on npc: " +npcName);
                new EntityInteractEvent(npc, "Talk-to").setEventCompleteCondition(
                        Dialogues::inDialogue, Calculations.random(1500, 3000)
                ).execute();
            }
        }
    }

    public static void talkWithNpc(String npcName, Area npcArea) {
        Log.info("Talking to: " +npcName);
        if (Dialogues.inDialogue()) {
            Log.debug("Finishing dialogue with: " +npcName);
            new DialogueEvent().setEventCompleteCondition(
                    () -> !Dialogues.inDialogue(), Calculations.random(2500, 5000)
            ).execute();
        } else {
            Log.debug("Starting talk with: " +npcName);
            NPC npc = NPCs.closest(npcName);
            if (npc != null && npc.canReach()) {
                Log.debug("Selecting 'Talk-to' on npc: " +npcName);
                new EntityInteractEvent(npc, "Talk-to").setEventCompleteCondition(
                        Dialogues::inDialogue, Calculations.random(1500, 3000)
                ).execute();
            } else {
                Log.debug("Can't reach NPC, walking to them.");
                Walking.walk(npcArea.getRandomTile(),
                        () -> npcArea.contains(Players.localPlayer())
                );
            }
        }
    }

    public static void talkWithNpc(String npcName, Area npcArea, String... chatOptions) {
        Log.info("Talking to: " +npcName);
        if (Dialogues.inDialogue()) {
            Log.debug("Finishing dialogue with: " +npcName + " with chat options: " + Arrays.toString(chatOptions));
            new DialogueEvent(chatOptions).setEventCompleteCondition(
                    () -> !Dialogues.inDialogue(), Calculations.random(2500, 5000)
            ).execute();
        } else {
            Log.debug("Starting talk with: " +npcName);
            NPC npc = NPCs.closest(npcName);
            if (npc != null && npc.canReach()) {
                Log.debug("Selecting 'Talk-to' on npc: " +npcName);
                new EntityInteractEvent(npc, "Talk-to").setEventCompleteCondition(
                        Dialogues::inDialogue, Calculations.random(1500, 3000)
                ).execute();
            } else {
                Log.debug("Can't reach NPC, walking to them.");
                Walking.walk(npcArea.getRandomTile(),
                        () -> npcArea.contains(Players.localPlayer())
                );
            }
        }
    }

    public static void muleRequest(int coinAmount) {
        Log.info("Requesting coin amount: " +coinAmount +" from mule.");
        new MuleRequestEvent()
                .addRequiredItem(new RequiredItem(ItemID.COINS_995, coinAmount))
                .setEventCompleteCondition(() -> Inventory.get(ItemID.COINS_995).getAmount() >= coinAmount)
                .execute();
    }

    public static void endScript(boolean shouldLogout, String endMsg, Boolean delayLogout) {
        Log.info(endMsg);
        if (shouldLogout && Client.isLoggedIn()) {
            if (delayLogout) {
                Log.debug("Sleeping 15 - 30 seconds before logging out.");
                MethodProvider.sleep(15_000, 30_000);
            }
            Log.debug("Logging out before ending script.");
            disableAutoLogin();
            new LogoutEvent().setEventCompleteCondition(
                    () -> !Client.isLoggedIn(), Calculations.random(500, 2500)
            ).execute();
        }
        Log.debug("Setting script to stopped.");
        AbstractScript.setStopped();
    }

    public static void setEFAccountNote(String apiKey, int accountID, String note) {
        Log.info("Trying to set EF Account note: " + note + " with API key: " + apiKey + " on account ID: " + accountID);
        EternalFarmClient eternalFarmClient = EternalFarmClient.builder()
                .apiKey(apiKey)
                .build();
        try {
            EFAccount efAccount = eternalFarmClient.getAccount(accountID).getData();
            Log.debug("Setting account: " + efAccount.getDisplayName() + " note to: " + note);
            efAccount.setNotes(note);
            eternalFarmClient.updateAccount(efAccount);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set EF Account note", e);
        }
    }

    public static final Map<Skill, AttackStyle> SKILL_TO_ATTACK_STYLE = Map.of(
            Skill.ATTACK, AttackStyle.ACCURATE,
            Skill.STRENGTH, AttackStyle.AGGRESSIVE,
            Skill.DEFENCE, AttackStyle.DEFENSIVE
    );

    public static boolean loadoutsFulfilled(EquipmentLoadout equipmentLoadout, InventoryLoadout inventoryLoadout) {
        return equipmentLoadout.isFulfilled() && inventoryLoadout.isFulfilled();
    }

    public static boolean equipmentLoadoutsAreEqual(EquipmentLoadout a, EquipmentLoadout b) {
        if (a == null || b == null) {
            return false;
        }
        if (a.getItems().size() != b.getItems().size()) {
            return false;
        }
        for (int i = 0; i < a.getItems().size(); i++) {
            if (!Objects.equals(a.getItems().get(i).getName(), b.getItems().get(i).getName())) {
                return false;
            }
        }
        return true;
    }

    public static Area getRandomArea(List<Area> areas) {
        return areas.get(Calculations.random(areas.size() - 1));
    }

    public static WorldTile getRandomTile(List<WorldTile> tiles) {
        return tiles.get(Calculations.random(tiles.size() - 1));
    }

    public static String getRandomString(List<String> strings) {
        return strings.get(Calculations.random(strings.size() - 1));
    }

    public static boolean isWidgetValid(Widget widget) {
        return widget != null && widget.isVisible();
    }

    public static boolean isWidgetValid(WidgetChild widgetChild) {
        return widgetChild != null && widgetChild.isVisible();
    }

    public static void openTab(Tab tab) {
        Log.info("Opening tab: " +tab.name());
        if (!Tabs.isOpen(tab)) {
            Log.debug("Tab isn't open. Opening tab: " +tab.name());
            if (Tabs.open(tab)) {
                MethodProvider.sleepUntil(() -> Tabs.isOpen(tab), Calculations.random(1500, 3000));
            }
        }
    }

    public static boolean isQuestListTabOpen() {
        int questTabWidgetId = 399;
        return isWidgetValid(Widgets.getWidget(questTabWidgetId));
    }

    public static void openQuestListTab() {
        WidgetChild questListWidget = Widgets.getWidgetChild(629, 10);
        Log.info("Opening the quest list tab");
        GLib.openTab(Tab.QUEST);
        new WidgetEvent(questListWidget, "Quest List").setEventCompleteCondition(
                GLib::isQuestListTabOpen, Calculations.random(500, 1500)
        ).execute();
    }

    public static boolean isCharacterSummaryTabOpen() {
        int characterSummaryWidgetId = 712;
        return isWidgetValid(Widgets.getWidget(characterSummaryWidgetId));
    }

    public static void openCharacterSummaryTab() {
        WidgetChild characterSummaryWidget = Widgets.getWidgetChild(629, 2);
        Log.info("Opening the character summary tab");
        GLib.openTab(Tab.QUEST);
        new WidgetEvent(characterSummaryWidget, "Character Summary").setEventCompleteCondition(
                GLib::isCharacterSummaryTabOpen, Calculations.random(500, 1500)
        ).execute();
    }

    public static int getQuestPoints(boolean forceRefresh) {
        if (forceRefresh) {
            openTab(Tab.QUEST);
        }
        int questPoints = PlayerSettings.getConfig(VarPlayer.QUEST_POINTS);
        Log.debug("Returning quest points: " +questPoints);
        return questPoints;
    }

    public static int getMinutesPlayed(boolean forceRefresh) {
        int minutesPlayedVar = 526;
        if (forceRefresh) {
            GLib.openTab(Tab.QUEST);
            if (!isQuestListTabOpen()) {
                openQuestListTab();
            } else {
                openCharacterSummaryTab();
            }
        }
        int minutesPlayed = PlayerSettings.getVarClientInt(minutesPlayedVar);
        Log.debug("Returning minutes played: " +minutesPlayed);
        return minutesPlayed;
    }

    public static boolean isTradeRestricted() {
        int minutesRequired = 1200; // 20 hours
        int questPointsRequired = 10;

        int questPoints = getQuestPoints(false);
        if (questPoints == 0) {
            questPoints = getQuestPoints(true);
        }

        int minutesPlayed = getMinutesPlayed(false);
        if (minutesPlayed == 0) {
            minutesPlayed = GLib.getMinutesPlayed(true);
        }

        long currentEpoch = System.currentTimeMillis();
        long loggedInEpoch = Client.getLoggedInAt();
        long minutesLoggedIn = msToMinutes(currentEpoch) - msToMinutes(loggedInEpoch);
        long totalMinutesPlayed = minutesPlayed + minutesLoggedIn;

        Log.debug("Total minutes played(summary + logged in time): " +totalMinutesPlayed);

        if (totalMinutesPlayed < minutesRequired) {
            Log.debug("We're trade restricted because our minutes played: " +minutesPlayed +" is lower than: " +minutesRequired);
            return true;
        } else if (questPoints < questPointsRequired) {
            Log.debug("We're trade restricted because our quest points: " +questPoints +" is less than: " +questPointsRequired);
            return true;
        }
        Log.debug("We're not trade restricted.");
        return false;
    }

    public static boolean accHasMembership() {
        return Client.hasMembership();
    }

    public static boolean canSellItems() {
        return !isTradeRestricted() || accHasMembership();
    }

    public static long msToMinutes(long milliseconds) {
        return milliseconds / (60 * 1000);
    }

    public static long minutesToMs(long minutes) {
        return minutes * 60 * 1000;
    }

    public static boolean isNpcAggressive(int npcLevel) {
        int combatLevel = Players.localPlayer().getCombatLevel();
        return combatLevel <= (npcLevel * 2);
    }

    public static String getItemName(int itemId) {
        ItemComposite itemComposite = ItemComposite.getItem(itemId);
        if (itemComposite != null) {
            return itemComposite.getName();
        }
        return null;
    }

    public static void disableRendering() {
        if (Client.getSettings().isRenderingEnabled()) {
            Log.info("Disabling rendering");
            Client.getSettings().setRenderingEnabled(false);
        }
    }

    public static void setFpsLimit(int fpsLimit) {
        if (Client.getSettings().getFpsLimit() != fpsLimit) {
            Log.info("Setting fps to: " +fpsLimit);
            Client.getSettings().setFpsLimit(fpsLimit);
        }
    }

    public static void hideRoofs() {
        if (!RandomManager.isToggleRoofEnabled()) {
            Log.info("Hiding roofs.");
            RandomManager.setToggleRoofEnabled(true);
        }
    }

    public static void waitUntilLoggedIn() {
        if (!Client.isLoggedIn()) {
            Log.info("Waiting until we're logged in.");
            new LoginEvent().setEventCompleteCondition(Client::isLoggedIn).execute();
        }
    }

    public static void disableAutoLogin() {
        if (RandomManager.isAutoLoginEnabled()) {
            Log.info("Setting autoLoginEnabled to false");
            RandomManager.setAutoLoginEnabled(false);
        }
    }

    public static void enableAutoLogin() {
        if (!RandomManager.isAutoLoginEnabled()) {
            Log.info("Setting autoLoginEnabled to true");
            RandomManager.setAutoLoginEnabled(true);
        }
    }
}