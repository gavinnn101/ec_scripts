package com.gavin101.tutorialisland.leafs.GuidesRoomLeafs;

import com.gavin101.GLib.GLib;
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
        return PlayerSettings.getConfig(Constants.TUTORIAL_PROGRESS_VAR) == 1 && isSetDisplayNameWindowOpen();
    }

    @Override
    public int onLoop() {
        if (displayName == null) {
            displayName = generateUsername();
        }
        if (isNameEmpty()) {
            selectEnterDisplayNameWidget();
            enterGeneratedName();
        } else if (isNameUnavailable()) {
            selectSuggestedName();
        } else if (GLib.isWidgetValid(getSetDisplayNameChildWidget(Constants.SET_DISPLAY_NAME_SET_NAME_ID))) {
            setName();
        } else {
            selectEnterDisplayNameWidget();
            eraseDisplayName();
        }
        return ReactionGenerator.getNormal();
    }

    private boolean isSetDisplayNameWindowOpen() {
        Widget setDisplayNameParent = Widgets.getWidget(Constants.SET_DISPLAY_NAME_PARENT_ID);
        return GLib.isWidgetValid(setDisplayNameParent);
    }

    private WidgetChild getSetDisplayNameChildWidget(int childId) {
        return Widgets.getWidgetChild(Constants.SET_DISPLAY_NAME_PARENT_ID, childId);
    }

    private boolean isNameEmpty() {
        WidgetChild enterDisplayNameCheckWidget = getSetDisplayNameChildWidget(Constants.SET_DISPLAY_NAME_TEXT_CHECK_ID);
        String widgetText = enterDisplayNameCheckWidget.getText();
        return GLib.isWidgetValid(enterDisplayNameCheckWidget)
                && (widgetText.isEmpty() || widgetText.equals("*"));
    }

    private boolean isNameUnavailable() {
        WidgetChild enterDisplayNameAvailableWidget = getSetDisplayNameChildWidget(Constants.SET_DISPLAY_NAME_AVAILABLE_ID);
        return GLib.isWidgetValid(enterDisplayNameAvailableWidget)
                && enterDisplayNameAvailableWidget.getText().contains("not available");
    }

    private void selectEnterDisplayNameWidget() {
        Log.debug("Selecting enter display name text box widget.");
        WidgetChild enterDisplayNameTextBoxWidget = getSetDisplayNameChildWidget(Constants.SET_DISPLAY_NAME_TEXT_ENTER_ID);
        if (!GLib.isWidgetValid(enterDisplayNameTextBoxWidget)) {
            return;
        }
        new WidgetEvent(enterDisplayNameTextBoxWidget, "Enter name").execute();
    }

    private void eraseDisplayName() {
        Log.debug("Erasing display name from box");
        Keyboard.eraseUntil(this::isNameEmpty, 5000);
    }

    private void enterGeneratedName() {
        Log.info("Entering generated display name: " + displayName);
        Keyboard.type(displayName, true);
//         // Checking for 'available' text, `look up name` and `set name` widgets all seem to return early so we'll use a random sleep for now..
//        Log.debug("Waiting for name result...");
//        MethodProvider.sleepUntil(
//                () -> getSetDisplayNameChildWidget(Constants.SET_DISPLAY_NAME_AVAILABLE_ID).containsText("available")
//                        && (GLib.isWidgetValid(getSetDisplayNameChildWidget(Constants.SET_DISPLAY_NAME_SET_NAME_ID)) || GLib.isWidgetValid(getSetDisplayNameChildWidget(Constants.SET_DISPLAY_NAME_LOOK_UP_NAME_ID))), Calculations.random(5000, 10000)
//        );
//        Log.debug("found 'available' text after entering name.");
        MethodProvider.sleep(3500, 5000);
    }

    private void selectSuggestedName() {
        int suggestedNameNumber = Calculations.random(1, 3);
        WidgetChild suggestedNameWidget;

        switch (suggestedNameNumber) {
            case 2:
                suggestedNameWidget = getSetDisplayNameChildWidget(Constants.SET_DISPLAY_NAME_SUGGESTED_NAME_SECOND_ID);
                Log.debug("Using second suggested name.");
                break;
            case 3:
                suggestedNameWidget = getSetDisplayNameChildWidget(Constants.SET_DISPLAY_NAME_SUGGESTED_NAME_THIRD_ID);
                Log.debug("Using third suggested name.");
                break;
            default:
                suggestedNameWidget = getSetDisplayNameChildWidget(Constants.SET_DISPLAY_NAME_SUGGESTED_NAME_FIRST_ID);
                Log.debug("Using first suggested name.");
                break;
        }

        if (!GLib.isWidgetValid(suggestedNameWidget)) {
            Log.warn("No suggested name found. This is unexpected.");
            return;
        }
        displayName = suggestedNameWidget.getText();
        Log.debug("Display name was unavailable. Selecting suggested display name: " + displayName);
        new WidgetEvent(suggestedNameWidget, "Set name").setEventCompleteCondition(
                () -> GLib.isWidgetValid(getSetDisplayNameChildWidget(Constants.SET_DISPLAY_NAME_SET_NAME_ID)),
                Calculations.random(3000, 5000)
        ).execute();
    }

    private void setName() {
        WidgetChild setNameWidget = getSetDisplayNameChildWidget(Constants.SET_DISPLAY_NAME_SET_NAME_ID);
        Log.debug("Clicking 'Set name' button for name: " + displayName);
        new WidgetEvent(setNameWidget, "Set name").setEventCompleteCondition(
                () -> !GLib.isWidgetValid(setNameWidget), Calculations.random(2000, 3500)
        ).execute();
    }

    // TODO: I think we should generate usernames in a smarter way to make them not look like bots.
    // Make a request to `https://legacy-beta-api.eternalfarm.net/namegenerator/generate?&count=1&length=12`?
    private String generateUsername() {
        String word1 = Constants.DISPLAY_NAME_OPTIONS[Calculations.random(0, Constants.DISPLAY_NAME_OPTIONS.length - 1)];
        String word2 = Constants.DISPLAY_NAME_OPTIONS[Calculations.random(0, Constants.DISPLAY_NAME_OPTIONS.length - 1)];
        int number = Calculations.random(1, 99);
        String displayName = word1 + word2 + number;
        Log.debug("Returning generated display name: " +displayName);
        return displayName;
    }
}