package eiboprojekt.presentation.scenes.GameView;

//import javax.swing.plaf.synth.SynthScrollBarUI;

import javafx.scene.input.KeyEvent;

public class KeyHandlern {
    private boolean up, down, left, right, jump;

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case W: // W-Taste für hoch
                up = true;
                break;
            case S: // S-Taste für runter
                down = true;
                break;
            case A: // A-Taste für links
                left = true;
                break;
            case D: // D-Taste für rechts
                right = true;
                break;
            case SPACE: // Leertaste für Springen
                jump = true;
                break;
            default:
                break;
        }
    }

    // Auf Tastenfreigabe reagieren (KeyReleased)
    public void keyReleased(KeyEvent e) {
        switch (e.getCode()) {
            case W:
                up = false;
                break;
            case S:
                down = false;
                break;
            case A:
                left = false;
                break;
            case D:
                right = false;
                break;
            case SPACE: // Leertaste für Springen
                jump = false;
                break;
            default:
                break;
        }
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isJump() {
        return jump;
    }
}
