package entity;

import main.GamePanel;
import utility.KollisionsChecker;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PowerUp extends Entitaet {

    private KollisionsChecker kollisionsChecker;
    private Stein stein;
    private int type;

    public PowerUp(GamePanel gp, Spieler spieler, Stein stein ,KollisionsChecker kollisionsChecker, int type) {

        this.gp = gp;
        this.kollisionsChecker = kollisionsChecker;
        this.stein = stein;
        this.type = type;

        setDefaultValues();

    }
    @Override
    public void setDefaultValues() {
        x = stein.getPositionX() + (double) gp.tileSize / 4;
        y= stein.getPositionY();
        geschwindigkeit = 6;
    }


    public boolean update() {
        y += geschwindigkeit;
        boolean getroffen = kollisionsChecker.powerUpKollidiertMitSpieler(this);
        if(getroffen) {
            gp.playSE(1);
            return true;
        }
        return false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (this.type == 4) {
            gc.setFill(Color.GREY);
        }else {
            gc.setFill(Color.WHITE);
        }
        gc.fillRect(x, y, gp.tileSize / 2.0, gp.tileSize / 2.0);
    }

    public int getPositionX() {
        return  (int) x;
    }

    public int getPositionY() {
        return  (int) y;
    }

    public int getType() {
        return type;
    }
}