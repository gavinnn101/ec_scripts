package com.gavin101.amethystaio;

import net.eternalclient.api.Client;
import net.eternalclient.api.accessors.Dialogues;
import net.eternalclient.api.accessors.NPCs;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.data.ItemID;
import net.eternalclient.api.events.DialogueEvent;
import net.eternalclient.api.events.EntityInteractEvent;
import net.eternalclient.api.events.ge.GrandExchangeEvent;
import net.eternalclient.api.events.ge.items.BuyItem;
import net.eternalclient.api.events.loadout.InventoryLoadout;
import net.eternalclient.api.events.muling.MuleRequestEvent;
import net.eternalclient.api.events.muling.RequiredItem;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.utilities.LivePrices;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.container.OwnedItems;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.NPC;
import net.eternalclient.api.wrappers.item.Item;
import net.eternalclient.api.wrappers.tabs.Tab;
import net.eternalclient.api.wrappers.tabs.Tabs;
import net.eternalfarm.client.EternalFarmClient;
import net.eternalfarm.client.entities.EFAccount;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GLib {

    public static boolean ownsRequiredItems(Map<Integer, Integer> itemMap) {
        Log.info("Checking if we have required items");
        for (Map.Entry<Integer, Integer> entry : itemMap.entrySet()) {
            int itemId = entry.getKey();
            int requiredAmount = entry.getValue();
            Log.debug("Checking if we have item: " +itemId + " of amount: " +requiredAmount);
            if (!OwnedItems.contains(itemId, requiredAmount)) {
                return false;
            }
        }
        return true;
    }

    public static Map<Integer, Integer> getMissingItems(Map<Integer, Integer> itemMap) {
        Log.info("Checking what items we need from the required item map.");
        Map<Integer, Integer> neededItems = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : itemMap.entrySet()) {
            int itemId = entry.getKey();
            int requiredAmount = entry.getValue();
            int ownedAmount = OwnedItems.count(itemId);

            int missingAmount = requiredAmount - ownedAmount;
            if (missingAmount > 0) {
                neededItems.put(itemId, missingAmount);
                Log.debug("Need " + missingAmount + " of item ID: " + itemId);
            }
        }

        if (neededItems.isEmpty()) {
            Log.info("All required items are available.");
        } else {
            Log.info("Found " + neededItems.size() + " items that need to be acquired.");
        }

        return neededItems;
    }

    public static InventoryLoadout createInventoryLoadout(Map<Integer, Integer> itemMap) {
        InventoryLoadout loadout = new InventoryLoadout();

        for (Map.Entry<Integer, Integer> entry : itemMap.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            loadout.addReq(itemId, quantity);
        }
        loadout.setStrict(true);
        return loadout;
    }

    public static GrandExchangeEvent createGrandExchangeBuyEvent(Map<Integer, Integer> itemMap) {
        GrandExchangeEvent geEvent = new GrandExchangeEvent();

        for (Map.Entry<Integer, Integer> entry : itemMap.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            geEvent.addBuyItems(new BuyItem(itemId, quantity));
        }
        return geEvent;
    }

    public static boolean hasGoldToBuyItem(int itemID, int amount) {
        int itemPrice = LivePrices.getItemPrice(itemID);
        int totalCost = itemPrice * amount;
        int myGoldAmount = OwnedItems.count(ItemID.COINS_995);
        Log.debug(
                "Checking if our gold: " + myGoldAmount + " is enough to buy " + amount + " of item: " + itemID + " for total price: " + totalCost
        );
        return myGoldAmount >= totalCost;
    }

    public static boolean hasGoldToBuyItem(int itemID) {
        return hasGoldToBuyItem(itemID, 1);
    }

    public static boolean hasGoldToBuyAllItems(Map<Integer, Integer> itemMap) {
        int totalCost = 0;
        int myGoldAmount = OwnedItems.count(ItemID.COINS_995);

        for (Map.Entry<Integer, Integer> entry : itemMap.entrySet()) {
            int itemID = entry.getKey();
            int quantity = entry.getValue();
            int ownedQuantity = OwnedItems.count(itemID);
            int neededQuantity = Math.max(0, quantity - ownedQuantity);

            if (neededQuantity > 0) {
                int itemPrice = LivePrices.getItemPrice(itemID);
                totalCost += itemPrice * neededQuantity;
            }
        }

        Log.debug("Total cost to buy all needed items: " + totalCost + ". Available gold: " + myGoldAmount);
        return myGoldAmount >= totalCost;
    }

    public static boolean inventoryContainsAll(Map<Integer, Integer> itemMap) {
        Log.info("Checking if inventory has all required items in itemMap.");
        for (Map.Entry<Integer, Integer> entry : itemMap.entrySet()) {
            int itemID = entry.getKey();
            int quantity = entry.getValue();
            Log.debug("Checking if inventory has item: " +itemID + " of quantity: " +quantity);
            Item invyItem = Inventory.get(itemID);
            int invyItemCount = Inventory.count(invyItem);
            if (invyItem == null || invyItemCount < quantity) {
                Log.debug("Inventory doesn't contain item: " +itemID + " of quantity: " +quantity);
                Log.debug("Inventory has item: " +itemID + " of quantity: " +invyItemCount);
                return false;
            }
        }
        Log.debug("Inventory contains all required items in itemMap.");
        return true;
    }

    public static void muleRequest(int coinAmount) {
        Log.info("Requesting coin amount: " +coinAmount +" from mule.");
        new MuleRequestEvent()
                .addRequiredItem(new RequiredItem(ItemID.COINS_995, coinAmount))
                .setEventCompleteCondition(() -> Inventory.get(ItemID.COINS_995).getAmount() >= coinAmount)
                .execute();
    }

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

    public static void talkWithNpc(String npcName, String... chatOptions) {
        Log.info("Talking to: " +npcName);
        if (Dialogues.inDialogue()) {
            Log.debug("Finishing dialogue with: " +npcName + "with chat options: " + Arrays.toString(chatOptions));
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
            }
        }
    }

    public static void openTab(Tab tab) {
        Log.info("Opening tab: " +tab.name());
        if (!Tabs.isOpen(tab)) {
            Log.debug("Tab isn't open. Opening tab: " +tab.name());
            if (Tabs.open(tab)) {
                MethodProvider.sleepUntil(
                        () -> Tabs.isOpen(tab), Calculations.random(1500, 3000)
                );
                // Above check seems correct but still spam opens tab.
                // Will add an extra sleep as a potential band-aid.
                MethodProvider.sleep(750, 1500);
            }
        }
    }

    public static void endScript(boolean shouldLogout, String endMsg) {
        Log.info(endMsg);
        if (shouldLogout && Client.isLoggedIn()) {
            Log.debug("Logging out before ending script.");
            Client.logout();
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
        } catch (IOException e) {
            throw new RuntimeException("Failed to set EF Account note", e);
        }
    }
}
