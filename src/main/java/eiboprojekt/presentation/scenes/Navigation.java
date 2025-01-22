package eiboprojekt.presentation.scenes;

import javafx.beans.property.SimpleStringProperty;

public class Navigation {

    private static SimpleStringProperty currentView;

    public static SimpleStringProperty getCurrentView() {
        return currentView;
    }

    public static void setCurrentView(SimpleStringProperty currentView) {
        Navigation.currentView = currentView;
    }
}