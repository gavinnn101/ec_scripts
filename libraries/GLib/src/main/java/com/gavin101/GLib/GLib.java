package com.gavin101.GLib;

import net.eternalclient.api.Client;
import net.eternalclient.api.accessors.AttackStyle;
import net.eternalclient.api.accessors.Dialogues;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.accessors.Players;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.DialogueEvent;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.events.LogoutEvent;
import net.eternalclient.api.events.loadout.EquipmentLoadout;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.events.muling.MuleRequestEvent;
import net.eternalclient.api.events.muling.RequiredItem;
import net.eternalclient.api.events.random.RandomManager;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;
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

    public static void endScript(boolean shouldLogout, String endMsg) {
        Log.info(endMsg);
        if (shouldLogout && Client.isLoggedIn()) {
            Log.debug("Logging out before ending script.");
            RandomManager.setAutoLoginEnabled(false);
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
                MethodProvider.sleepUntil(
                        tab::isOpen, Calculations.random(1500, 3000)
                );
                // Above check seems correct but still spam opens tab.
                // Will add an extra sleep as a potential band-aid.
                MethodProvider.sleep(750, 1500);
            }
        }
    }
}