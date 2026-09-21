package entity;

import main.GamePanel;
import main.KeyHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Spieler extends Entitaet {

    int startPosX = 288;
    int startPosY = 720;
    int playerWidth = gp.tileSize * 2;
    int playerHeight = gp.tileSize / 2;
    int startGeschwindigkeit = 10;
    private long increaseEndTime = 0;

    public Spieler(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
    }
    @Override
    public void setDefaultValues() {
        x = startPosX;
        y = startPosY;
        geschwindigkeit = startGeschwindigkeit;
    }
    public void update() {
        if (keyH.leftPressed && getPositionX() > gp.tileSize) {
            x -= geschwindigkeit;
        } else if (keyH.rightPressed && getPositionX() + playerWidth < gp.screenWidth - 1 * gp.tileSize) {
            x += geschwindigkeit;
        }
        if (increaseEndTime > 0 && System.currentTimeMillis() > increaseEndTime) {
            playerWidth = gp.tileSize * 2;
            x += gp.tileSize / 2;
            increaseEndTime = 0;
        }
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(x, y, playerWidth, playerHeight);
    }

    public int getPositionX() {return (int) x;}

    public int getPositionY() {return (int) y;}

    public void resetPosition() {
        x = startPosX;
        y = startPosY;
    }

    public void setSize(int playerWidth) {
        this.playerWidth = playerWidth;

    }

    public void increasePlayer() {
        setSize(gp.tileSize * 3);
        x -= gp.tileSize / 2;
        increaseEndTime = System.currentTimeMillis() + 8000;
    }

    public int getWidth() {
            return this.playerWidth;
}
    }