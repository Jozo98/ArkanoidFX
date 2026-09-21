package utility;

import entity.*;
import main.GamePanel;
import main.KeyHandler;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class KollisionsChecker {

    GamePanel gp;
    KeyHandler keyH;
    Spieler spieler;
    ArrayList<Stein> steine;

    public KollisionsChecker(GamePanel gp, KeyHandler keyH,  Spieler spieler, ArrayList<Stein> steine) {
        this.gp = gp;
        this.keyH = keyH;
        this.spieler = spieler;
        this.steine = steine;
    }

    public boolean trefferBildschirmLinksRechts(Ball ball) {
        return ball.getPositionX() < gp.tileSize || ball.getPositionX() + ball.getSize() > gp.screenWidth - gp.tileSize;
    }

    public boolean trefferBildschirmOben(Ball ball) {
        return ball.getPositionY() < 0;
    }

    public boolean ballKollidiertMitSpieler(Ball ball) {

        Rectangle2D ballRect = new Rectangle2D(ball.getPositionX(), ball.getPositionY(), ball.getSize(), ball.getSize());
        Rectangle2D playerRect = new Rectangle2D(
                spieler.getPositionX(),
                spieler.getPositionY(),
                spieler.getWidth(),
                gp.tileSize / 2
        );

        return ballRect.intersects(playerRect);
    }

    public boolean trefferSeiteSpieler(Ball ball) {
        int overlapLeft = (ball.getPositionX() + ball.getSize() / 2)  - spieler.getPositionX() + ball.getSize();
        int overlapRight = (spieler.getPositionX() + spieler.getWidth()) - ball.getPositionX();
        int overlapTop = (ball.getPositionY() + ball.getSize() / 2) - spieler.getPositionY() + ball.getSize();
        int overlapBottom = (spieler.getPositionY() + gp.tileSize / 2) - ball.getPositionY();

        int minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                Math.min(overlapTop, overlapBottom));

        if (minOverlap == overlapLeft) {
            ball.setPositionX(spieler.getPositionX() - ball.getSize() - 1);
            if (keyH.leftPressed) {ball.setPositionX(ball.getPositionX() - 7);}
            return true;
        } else if (minOverlap == overlapRight) {
            ball.setPositionX(spieler.getPositionX() + spieler.getWidth() + 1);
            if (keyH.rightPressed) {ball.setPositionX(ball.getPositionX() + 7);}
            return true;
        } else if (minOverlap == overlapTop) {
            ball.setPositionY(spieler.getPositionY() - ball.getSize() - 1);
            return false;
        } else {
            return false;
        }
    }

    public int ballKollidiertMitStein(Ball ball) {
        int i = 0;
        for (Stein stein : steine) {
            Rectangle2D steinRect = new Rectangle2D(stein.getPositionX(), stein.getPositionY(), gp.tileSize, gp.tileSize / 2);
            Rectangle2D ballRect = new Rectangle2D(ball.getPositionX(), ball.getPositionY(), ball.getSize(), ball.getSize());
            if (ballRect.intersects(steinRect)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public boolean ballKollidiertMitSteinVonSeite(Ball ball, int steinIndex) {
        Stein stein = steine.get(steinIndex);

        int overlapLeft = (ball.getPositionX() + ball.getSize() / 2)  - stein.getPositionX();
        int overlapRight = (stein.getPositionX() + gp.tileSize) - ball.getPositionX() - ball.getSize() / 2;
        int overlapTop = (ball.getPositionY() + ball.getSize() / 2) - stein.getPositionY();
        int overlapBottom = (stein.getPositionY() + gp.tileSize / 2) - ball.getPositionY() - ball.getSize() / 2;

        int minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                Math.min(overlapTop, overlapBottom));

        if (minOverlap == overlapLeft) {

            return true;
        } else if (minOverlap == overlapRight) {

            return true;
        } else if (minOverlap == overlapTop) {

            return false;
        } else {

            return false;
        }
    }

    public boolean powerUpKollidiertMitSpieler(PowerUp powerUp) {
        Rectangle2D powerUpRect = new Rectangle2D(powerUp.getPositionX(), powerUp.getPositionY(), gp.tileSize / 2, gp.tileSize / 2);
        Rectangle2D spielerRect = new Rectangle2D(spieler.getPositionX(), spieler.getPositionY(), spieler.getWidth(), gp.tileSize / 2);
        return spielerRect.intersects(powerUpRect);

    }

    public int powerUpKollidiertMitStein(Projectile projectile) {
        int i = 0;
        for (Stein stein : steine) {
            Rectangle2D steinRect = new Rectangle2D(stein.getPositionX(), stein.getPositionY(), gp.tileSize, gp.tileSize / 2);
            Rectangle2D projectileRect = new Rectangle2D(projectile.getPositionX(), projectile.getPositionY(), gp.tileSize / 2, gp.tileSize / 2);
            if (projectileRect.intersects(steinRect)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int pruefeKollisionProjectileSteine(Projectile projectile) {
        int i = 0;
        for (Stein stein : steine) {
            Rectangle2D steinRect = new Rectangle2D(stein.getPositionX(), stein.getPositionY(), gp.tileSize, gp.tileSize / 2);
            Rectangle2D projectileRect = new Rectangle2D(projectile.getPositionX(), projectile.getPositionY(), gp.tileSize / 4, gp.tileSize);
            if (projectileRect.intersects(steinRect)) {
                return i;
            }
            i++;
        }
        return -1;
    }
}