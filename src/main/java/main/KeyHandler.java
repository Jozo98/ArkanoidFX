package main;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, escapePressed, enterPressed, spacePressed, qPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyPressed(KeyEvent e) {
        KeyCode code = e.getCode();

        if (code == KeyCode.W) {
            upPressed = true;
        }
        if (code == KeyCode.S) {
            downPressed = true;
        }
        if (code == KeyCode.A) {
            leftPressed = true;
        }
        if (code == KeyCode.D) {
            rightPressed = true;
        }
        if (code == KeyCode.ESCAPE) {
            escapePressed = true;
        }
        if (code == KeyCode.ENTER) {
            enterPressed = true;
        }
        if (code == KeyCode.SPACE) {
            spacePressed = true;
        }
        if (code == KeyCode.Q) {
            qPressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        KeyCode code = e.getCode();

        if (code == KeyCode.W) {
            upPressed = false;
        }
        if (code == KeyCode.S) {
            downPressed = false;
        }
        if (code == KeyCode.A) {
            leftPressed = false;
        }
        if (code == KeyCode.D) {
            rightPressed = false;
        }
        if (code == KeyCode.ESCAPE) {
            escapePressed = false;
        }
        if (code == KeyCode.ENTER) {
            enterPressed = false;
        }
        if (code == KeyCode.SPACE) {
            spacePressed = false;
        }
        if (code == KeyCode.Q) {
            qPressed = false;
        }
    }
}