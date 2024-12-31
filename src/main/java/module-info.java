module eiboprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens eiboprojekt to javafx.fxml;
    exports eiboprojekt;
}
