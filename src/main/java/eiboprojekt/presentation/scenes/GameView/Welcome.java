package eiboprojekt.presentation.scenes.GameView;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Welcome extends BorderPane {

    private Button switchButton;

    public Welcome(int width, int height) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        initializeUI();
    }

    private void initializeUI() {
        VBox centerBox = new VBox(20); // 20 ist der vertikale Abstand zwischen Elementen
        centerBox.setAlignment(Pos.CENTER);

        Text welcomeLabel = new Text("Willkommen");
        switchButton = new Button("Press Start!");

        centerBox.getChildren().addAll(welcomeLabel, switchButton);

        setCenter(centerBox);
    }

    public Button getSwitchButton() {
        return switchButton;
    }
}
