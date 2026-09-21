package entity;

import main.GamePanel;
import main.KeyHandler;
import utility.KollisionsChecker;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends Entitaet {

    private int size;
    private double geschwindigkeitX;
    private double geschwindigkeitY;
    private double richtung;
    private Spieler spieler;
    private KollisionsChecker kollisionsChecker;
    private boolean startBall;

    public Ball(GamePanel gp, KeyHandler keyH, Spieler spieler, KollisionsChecker kollisionsChecker) {

        this.gp = gp;
        this.keyH = keyH;
        this.spieler = spieler;
        this.kollisionsChecker = kollisionsChecker;

        setDefaultValues();
    }

    @Override
    public void setDefaultValues() {
        x = spieler.getPositionX() + spieler.getWidth() / 2 - (double) size / 2;
        y = spieler.getPositionY() - size - 1;
        geschwindigkeitX = 0;
        geschwindigkeitY = 0;
        geschwindigkeit = 10;
        richtung = 1;
        size = 15;
        startBall = false;
    }

    public int update() {
        if (keyH.spacePressed) {
            startBall = true;
        }

        if (startBall) {
            geschwindigkeitX = Math.cos(richtung) * geschwindigkeit;
            geschwindigkeitY = Math.sin(richtung) * geschwindigkeit;
            x += geschwindigkeitX;
            y += geschwindigkeitY;
        } else {
            x = (spieler.getPositionX() + spieler.getWidth() / 2 - (double) size / 2);
        }

        pruefeKollisionBallBildschirm();

        pruefeKollisionBallSpieler();

        return pruefeKollisionBallSteine();
    }

    public void pruefeKollisionBallBildschirm() {
        if (kollisionsChecker.trefferBildschirmLinksRechts(this)) {
            richtung = Math.PI - richtung;
            x = Math.max(gp.tileSize, Math.min(gp.screenWidth - gp.tileSize - size, x));
        }
        if (kollisionsChecker.trefferBildschirmOben(this)) {
            richtung = -richtung;
            y = 0;
        }
    }

    public void pruefeKollisionBallSpieler() {
        if (!(kollisionsChecker.ballKollidiertMitSpieler(this))) {
            return;
        }
        if (kollisionsChecker.trefferSeiteSpieler(this)) {
            richtung -= Math.PI;
        } else {
            richtung = -richtung;

            if (keyH.rightPressed) {
                richtung += 0.4;
            }
            if (keyH.leftPressed) {
                richtung -= 0.4;
            }
            korrigiereBallrichtung();
        }
        gp.playSE(6);
    }

    public int pruefeKollisionBallSteine() {
        int steinIndex = kollisionsChecker.ballKollidiertMitStein(this);
        if (!(steinIndex > -1)) {
            return -1;
        }
        if (kollisionsChecker.ballKollidiertMitSteinVonSeite(this, steinIndex)) {
            richtung = Math.PI - richtung;
        } else {
            richtung = -richtung;
        }
        gp.stopMusic();
        gp.playSE(7);
        return steinIndex;
    }

    public void korrigiereBallrichtung() {
        double minSin = 0.3;
        if (Math.sin(richtung) > 0) {
            richtung = -Math.abs(richtung);
        }
        if (!(Math.abs(Math.sin(richtung)) < minSin)) {
            return;
        }
        if (Math.cos(richtung) > 0) {
            richtung = -Math.asin(minSin);
        } else {
            richtung = Math.PI + Math.asin(minSin);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, size, size);
    }

    public int getPositionX() {
        return (int) x;
    }

    public int getPositionY() {
        return (int) y;
    }

    public void setPositionX(int x) {
        this.x = x;
    }

    public void setPositionY(int y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public void resetRichtung(double x) {richtung = x;}

    public boolean getStartBall() {
        return startBall;
    }

    public void setStartBall(boolean startBall) {
        this.startBall = startBall;
    }

    public void reset() {
        resetRichtung(1);
        x = spieler.getPositionX() + (double) spieler.getWidth() / 2 - (double) size / 2;
        y = spieler.getPositionY() - size - 1;
        startBall = false;
    }
}