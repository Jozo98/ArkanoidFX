package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.GamePanel;
import main.KeyHandler;
import utility.KollisionsChecker;

public class Projectile extends Entitaet {

    private Spieler spieler;
    private KollisionsChecker kollisionsChecker;
    int projectileWidth = gp.tileSize / 8;
    int projectileHeight = gp.tileSize /4;

    public Projectile (GamePanel gp, KeyHandler keyH, Spieler spieler , KollisionsChecker kollisionsChecker) {
        this.gp = gp;
        this.keyH = keyH;
        this.spieler = spieler;
        this.kollisionsChecker = kollisionsChecker;

        setDefaultValues();
    }

    @Override
    public void setDefaultValues() {
        x = spieler.getPositionX();
        y= spieler.getPositionY();
        geschwindigkeit = 20;
    }

    public int update() {
        y -= geschwindigkeit;
        return kollisionsChecker.pruefeKollisionProjectileSteine(this);
}

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GOLD);
        gc.fillRect(x, y, projectileWidth, projectileHeight);
    }

    public int getPositionX() {
        return (int) this.x;
    }

    public int getPositionY() {
        return (int) this.y;
    }

    public void setPositionX(int x) {
        this.x = x;
    }

    public void setPositionY(int y) {
        this.y = y;
    }
}
