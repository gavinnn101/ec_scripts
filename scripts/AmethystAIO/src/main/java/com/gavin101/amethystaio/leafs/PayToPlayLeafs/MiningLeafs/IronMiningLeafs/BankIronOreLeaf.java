package com.gavin101.amethystaio.leafs.PayToPlayLeafs.MiningLeafs.IronMiningLeafs;

import net.eternalclient.api.accessors.GameObjects;
import net.eternalclient.api.containers.Inventory;
import net.eternalclient.api.containers.bank.Bank;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.interactives.GameObject;

public class BankIronOreLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Inventory.isFull();
    }

    @Override
    public int onLoop() {
        Log.info("Banking iron ore.");
        if (Bank.isOpen()) {
            Log.debug("Depositing all items besides pickaxe");
            if (Bank.depositAllExcept(i -> i.containsName("pickaxe"))) {
                MethodProvider.sleepUntil(() -> !Inventory.contains("Iron ore"), Calculations.random(750, 1500));
            }
        } else {
            GameObject bankChest = GameObjects.closest("Bank chest");
            if (bankChest != null && bankChest.canReach()) {
                Log.debug("Opening bank");
                if (Bank.open()) {
                    Log.debug("Waiting for bank to be open.");
                    MethodProvider.sleepUntil(Bank::isOpen, Calculations.random(1500, 3500));
                }
            }
        }
        return ReactionGenerator.getPredictable();
    }
}
