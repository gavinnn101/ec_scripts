package com.gavin101.mule;

import com.gavin101.mule.branches.MuleBranch;
import com.gavin101.mule.leafs.GoToMuleAreaLeaf;
import com.gavin101.mule.leafs.MuleLeaf;
import net.eternalclient.api.frameworks.tree.Tree;
import net.eternalclient.api.script.AbstractScript;
import net.eternalclient.api.script.ScriptCategory;
import net.eternalclient.api.script.ScriptManifest;
import net.eternalclient.api.wrappers.map.Area;

@ScriptManifest(
        name = "MuleHandler",
        description = "Handles mule requests.",
        author = "Gavin101",
        category = ScriptCategory.OTHER,
        version = 1.0
)

public class Main extends AbstractScript {

    private final Tree tree = new Tree();

    private static final Area muleArea = Constants.GRAND_EXCHANGE_AREA;

    @Override
    public void onStart(String[] strings) {
        tree.addBranches(
                new MuleBranch().addLeafs(
                        new GoToMuleAreaLeaf(muleArea),
                        new MuleLeaf(muleArea)
                )
        );
    }

    @Override
    public int onLoop() {
        return tree.onLoop();
    }

    @Override
    public void onExit() {
        tree.clear();
    }
}
