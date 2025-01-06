package com.gavin101.test.accbuilder.tiers.fishing;

import com.gavin101.test.accbuilder.tasks.leveling.fishing.tierone.TierOneFishingConstants;
import com.gavin101.test.accbuilder.tasks.leveling.fishing.tierone.branches.TierOneFishingBranch;
import com.gavin101.test.accbuilder.tasks.leveling.fishing.tierone.leafs.TierOneFishingLeaf;
import com.gavin101.test.accbuilder.tasks.leveling.fishing.tiertwo.TierTwoConstants;
import com.gavin101.test.accbuilder.tasks.leveling.fishing.tiertwo.branches.TierTwoFishingBranch;
import com.gavin101.test.accbuilder.tasks.leveling.fishing.tiertwo.leafs.TierTwoFishingLeaf;
import com.gavin101.test.accbuilder.utility.branches.LoadoutsFulfilledBranch;
import com.gavin101.test.accbuilder.utility.leafs.GetLoadoutLeaf;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FishingTiers {
    public static final FishingTier TIER_ONE = new FishingTier(1, 4, Collections.singletonList(
            new TierOneFishingBranch().addLeafs(
                    GetLoadoutLeaf.builder()
                            .equipmentLoadout(TierOneFishingConstants.FISHING_TIER_ONE_EQUIPMENT)
                            .inventoryLoadout(TierOneFishingConstants.FISHING_TIER_ONE_INVENTORY)
                            .buyRemainder(true)
                            .build(),
                    new LoadoutsFulfilledBranch(
                            TierOneFishingConstants.FISHING_TIER_ONE_EQUIPMENT,
                            TierOneFishingConstants.FISHING_TIER_ONE_INVENTORY
                    ).addLeafs(
                            new TierOneFishingLeaf()
                    )
            )
    ));

    public static final FishingTier TIER_TWO = new FishingTier(5, 19, Collections.singletonList(
            new TierTwoFishingBranch().addLeafs(
                    GetLoadoutLeaf.builder()
                            .equipmentLoadout(TierTwoConstants.FISHING_TIER_TWO_EQUIPMENT)
                            .inventoryLoadout(TierTwoConstants.FISHING_TIER_TWO_INVENTORY)
                            .buyRemainder(true)
                            .build(),
                    new LoadoutsFulfilledBranch(
                            TierTwoConstants.FISHING_TIER_TWO_EQUIPMENT,
                            TierTwoConstants.FISHING_TIER_TWO_INVENTORY
                    ).addLeafs(
                            new TierTwoFishingLeaf()
                    )
            )
    ));

    public static List<FishingTier> getAllTiers() {
        return Arrays.asList(
                TIER_ONE,
                TIER_TWO
        );
    }
}
