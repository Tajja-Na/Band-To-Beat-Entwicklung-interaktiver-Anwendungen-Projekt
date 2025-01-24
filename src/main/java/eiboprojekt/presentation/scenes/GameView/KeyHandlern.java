package eiboprojekt.presentation.scenes.GameView;

import javafx.scene.input.KeyEvent;

public class KeyHandlern {
    private boolean up, down, left, right, jump;

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case W:
                up = true;
                break;
            case S:
                down = true;
                break;
            case A:
                left = true;
                break;
            case D:
                right = true;
                break;
            case SPACE:
                jump = true;
                break;
            default:
                break;
        }
    }

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
            case SPACE:
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
