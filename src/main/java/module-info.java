module eiboprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires eia.simpleminim.v2;

    opens eiboprojekt to javafx.fxml;
    exports eiboprojekt;
}
