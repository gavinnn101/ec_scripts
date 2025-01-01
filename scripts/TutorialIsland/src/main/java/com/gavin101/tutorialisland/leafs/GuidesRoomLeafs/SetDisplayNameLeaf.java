package com.gavin101.tutorialisland.leafs.GuidesRoomLeafs;

import com.gavin101.tutorialisland.Constants;
import net.eternalclient.api.accessors.PlayerSettings;
import net.eternalclient.api.accessors.Widgets;
import net.eternalclient.api.events.WidgetEvent;
import net.eternalclient.api.frameworks.tree.Leaf;
import net.eternalclient.api.utilities.Log;
import net.eternalclient.api.utilities.MethodProvider;
import net.eternalclient.api.utilities.ReactionGenerator;
import net.eternalclient.api.utilities.math.Calculations;
import net.eternalclient.api.wrappers.input.Keyboard;
import net.eternalclient.api.wrappers.widgets.Widget;
import net.eternalclient.api.wrappers.widgets.WidgetChild;

public class SetDisplayNameLeaf extends Leaf {

    private String displayName = null;

    @Override
    public boolean isValid() {
        Widget setDisplayNameParent = Widgets.getWidget(Constants.SET_DISPLAY_NAME_PARENT_ID);
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 1
                && setDisplayNameParent != null
                && setDisplayNameParent.isVisible();
    }

    @Override
    public int onLoop() {
        Log.info("Setting display name.");
        WidgetChild enterDisplayNameCheckWidget = getWidget(Constants.SET_DISPLAY_NAME_TEXT_CHECK_ID);
        WidgetChild enterDisplayNameAvailableWidget = getWidget(Constants.SET_DISPLAY_NAME_AVAILABLE_ID);
        WidgetChild setNameWidget = getWidget(Constants.SET_DISPLAY_NAME_SET_NAME_ID);

        if (enterDisplayNameCheckWidget != null && enterDisplayNameCheckWidget.isVisible()) {
            if (isNameEmpty(enterDisplayNameCheckWidget)) {
                enterGeneratedName();
            } else if (isNameUnavailable(enterDisplayNameAvailableWidget)) {
                selectSuggestedName();
            } else {
                setName(setNameWidget);
            }
        }

        return ReactionGenerator.getNormal();
    }

    private WidgetChild getWidget(int childId) {
        return Widgets.getWidgetChild(Constants.SET_DISPLAY_NAME_PARENT_ID, childId);
    }

    private boolean isNameEmpty(WidgetChild widget) {
        return widget.getText().isEmpty() || widget.getText().contains("*");
    }

    private boolean isNameUnavailable(WidgetChild widget) {
        return widget != null && widget.isVisible() && widget.getText().contains("not available");
    }

    private void enterGeneratedName() {
        if (displayName == null) {
            displayName = generateUsername();
        }
        Log.debug("Entering generated display name: " + displayName);
        WidgetChild enterDisplayNameEnterWidget = getWidget(Constants.SET_DISPLAY_NAME_TEXT_ENTER_ID);
        new WidgetEvent(enterDisplayNameEnterWidget, "Enter name").setEventCompleteCondition(
                () -> getWidget(Constants.SET_DISPLAY_NAME_TEXT_CHECK_ID).getText().contains("*"),
                Calculations.random(2000, 3500)
        ).execute();
        Keyboard.type(displayName, true);
        MethodProvider.tickSleepUntil(
                () -> getWidget(Constants.SET_DISPLAY_NAME_AVAILABLE_ID).containsText("available"), 10
        );
    }

    private void selectSuggestedName() {
        int suggestedNameNumber = Calculations.random(1, 3);
        WidgetChild suggestedNameWidget;

        switch (suggestedNameNumber) {
            case 2:
                suggestedNameWidget = getWidget(Constants.SET_DISPLAY_NAME_SUGGESTED_NAME_SECOND_ID);
                Log.debug("Using second suggested name.");
                break;
            case 3:
                suggestedNameWidget = getWidget(Constants.SET_DISPLAY_NAME_SUGGESTED_NAME_THIRD_ID);
                Log.debug("Using third suggested name.");
                break;
            default:
                suggestedNameWidget = getWidget(Constants.SET_DISPLAY_NAME_SUGGESTED_NAME_FIRST_ID);
                Log.debug("Using first suggested name.");
                break;
        }

        if (suggestedNameWidget != null && suggestedNameWidget.isVisible()) {
            displayName = suggestedNameWidget.getText();
            Log.debug("Name unavailable. Selecting suggested name: " + displayName);
            new WidgetEvent(suggestedNameWidget, "Set name").setEventCompleteCondition(
                    () -> getWidget(Constants.SET_DISPLAY_NAME_SET_NAME_ID) != null
                            && getWidget(Constants.SET_DISPLAY_NAME_SET_NAME_ID).isVisible(),
                    Calculations.random(3000, 5000)
            ).execute();
        } else {
            Log.warn("No suggested name found. This is unexpected.");
        }
    }

    private void setName(WidgetChild setNameWidget) {
        if (setNameWidget != null && setNameWidget.isVisible()) {
            Log.debug("Clicking 'Set name' button for name: " + displayName);
            new WidgetEvent(setNameWidget, "Set name").setEventCompleteCondition(
                    () -> !setNameWidget.isVisible(), Calculations.random(2000, 3500)
            ).execute();
            // I don't feel like this should be able to execute in a loop with these conditions but it does sometimes.
            // Will add a small sleep after to make sure it doesn't spam the button.
            MethodProvider.sleep(750, 1500);
        }
    }

    private String generateUsername() {
        String word1 = Constants.DISPLAY_NAME_OPTIONS[Calculations.random(0, Constants.DISPLAY_NAME_OPTIONS.length - 1)];
        String word2 = Constants.DISPLAY_NAME_OPTIONS[Calculations.random(0, Constants.DISPLAY_NAME_OPTIONS.length - 1)];
        int number = Calculations.random(1, 99);
        return word1 + word2 + number;
    }
}