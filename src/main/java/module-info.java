module eiboprojekt {
    requires javafx.controls;
    requires javafx.fxml;

    opens eiboprojekt to javafx.fxml;
    exports eiboprojekt;
}
