package main;


import entity.*;
import utility.KollisionsChecker;
import utility.SaveManager;

import java.io.IOException;
import java.util.ArrayList;

public class Game {

    private Spieler spieler;
    private KollisionsChecker kollisionsChecker;
    private GamePanel gp;
    private ArrayList<Stein> steine;
    private ArrayList<Ball> balls;
    private ArrayList<Projectile> projectiles;
    private ArrayList<PowerUp> powerUps;
    private int aktuellesLevel;
    private int leben;
    private int projectileActive = 300;
    int projectileCooldown = 0;
    int PROJECTILE_COOLDOWN_MAX = 15;
    boolean projectileLeft = true;
    public int score = 0;

    public Game(GamePanel gp, KeyHandler keyH) throws IOException {
        spieler = new Spieler(gp, keyH);
        steine = new ArrayList<>();
        balls = new ArrayList<>();
        powerUps = new ArrayList<>();
        projectiles = new ArrayList<>();
        aktuellesLevel = SaveManager.loadGame();
        addSteine(gp);
        kollisionsChecker = new KollisionsChecker(gp, keyH, spieler, steine);
        balls.add(new Ball(gp, keyH, spieler, kollisionsChecker));
        leben = 3;
        this.gp = gp;
    }

    public void update() throws IOException {
        spieler.update();
        updateLevels();
        updateBalls();
        updatePowerUps();
        updateProjectiles();
    }

    public void updateBalls() {
        ArrayList<Ball> toRemove = new ArrayList<>();
        int size = balls.size();
        for (int i = 0; i < size; i++) {
            Ball ball = balls.get(i);
            int steinIndex = ball.update();

            if (steinIndex != -1) {
                SteinManager.aktiviereStein(steine.get(steinIndex), ball, this);
                entferneStein(steinIndex);
                increaseScore();
            }

            if (ball.getPositionY() > gp.screenHeight) {
                if (balls.size() == 1) {
                    leben--;
                    if (leben > 0) {
                        ball.reset();
                        gp.playSE(10);
                    } else {
                        gp.playSE(11);
                    }
                } else {
                    toRemove.add(ball);
                }
            }
        }
        balls.removeAll(toRemove);
        gp.keyH.spacePressed = false;
    }

    public void updatePowerUps() {
        ArrayList<PowerUp> toRemovePowerUp = new ArrayList<>();
        int size = powerUps.size();
        for (int i = 0; i < size; i++) {
            PowerUp powerUp = powerUps.get(i);
            boolean getroffen = powerUp.update();
            if (getroffen) {
                PowerUpManager.aktivierePowerUp(powerUp, this);
                toRemovePowerUp.add(powerUp);
            }
        }
        powerUps.removeAll(toRemovePowerUp);
    }

    public void updateProjectiles() {
        ArrayList<Projectile> toRemove = new ArrayList<>();
        if (gp.keyH.qPressed && projectileActive > 0 && projectileCooldown == 0) {
            addProjectile(spieler.getPositionX(), spieler.getPositionY());
            addProjectile(spieler.getPositionX(), spieler.getPositionY());
            gp.playSE(3);
            projectileCooldown = PROJECTILE_COOLDOWN_MAX;
        }
        if (projectileCooldown > 0) {
            projectileCooldown--;
        }
        if (projectileActive > 0) {
            projectileActive--;
        }
        for (Projectile projectile : projectiles) {
            int steinIndex = projectile.update();
            if (steinIndex != -1) {
                SteinManager.aktiviereStein(steine.get(steinIndex), projectile, this);
                entferneStein(steinIndex);
                increaseScore();
                toRemove.add(projectile);
            }
            if (projectile.getPositionY() < 0) {
                toRemove.add(projectile);
            }
        }
        projectiles.removeAll(toRemove);
    }


    public void updateLevels() throws IOException {
        if (steine.isEmpty()) {
            aktuellesLevel++;
            SaveManager.saveGame(aktuellesLevel);
            reset();
            gp.stopMusic();
            gp.playSE(9);
        }
    }

    public void reset() {
        spieler.resetPosition();
        steine.clear();
        leben = 3;
        addSteine(gp);
        gp.resetSteineImage();
        balls.clear();
        balls.add(new Ball(gp, gp.keyH, spieler, kollisionsChecker));
    }

    public void addSteine(GamePanel gp) {
        int y = 10;
        int abstand = 4;
        for (int i = 0; i < 16; i++) {
            int x = 4;
            y += 26;
            for (int j = 0; j < 11; j++) {
                if (Levels.levels[aktuellesLevel][j + i * 11] == 1) {
                    steine.add(new Stein(gp, x, y, 0));
                } else if (Levels.levels[aktuellesLevel][j + i * 11] == 2) {
                    steine.add(new Stein(gp, x, y, 1));
                } else if (Levels.levels[aktuellesLevel][j + i * 11] == 3) {
                    steine.add(new Stein(gp, x, y, 2));
                } else if (Levels.levels[aktuellesLevel][j + i * 11] == 4) {
                    steine.add(new Stein(gp, x, y, 3));
                } else if (Levels.levels[aktuellesLevel][j + i * 11] == 5) {
                    steine.add(new Stein(gp, x, y, 4));
                }
                x += gp.tileSize + abstand;
            }
        }
    }

    public void entferneStein(int steinIndex) {
        Stein stein = steine.get(steinIndex);
        steine.remove(stein);
        gp.removeSteinFromImage(stein);
    }

    public void addBall(int x, int y) {
        for (int i = 0; i < 2; i++) {
            Ball newBall = new Ball(gp, gp.keyH, spieler, kollisionsChecker);
            newBall.setPositionX(x);
            newBall.setPositionY(y);
            newBall.setStartBall(true);
            double richtung = 0.8 + (balls.size() * 0.1 * i);
            newBall.resetRichtung(richtung);
            balls.add(newBall);
        }
    }

    public void addProjectile(int x, int y) {
        Projectile newProjectile = new Projectile(gp, gp.keyH, spieler, kollisionsChecker);
        if (projectileLeft) {
            newProjectile.setPositionX(x);
            newProjectile.setPositionY(y);
            projectileLeft = false;
        } else {
            newProjectile.setPositionX(x + gp.tileSize * 2 - gp.tileSize / 8);
            newProjectile.setPositionY(y);
            projectileLeft = true;
        }

        projectiles.add(newProjectile);
    }

    public void addPowerUp(Stein stein) {
        powerUps.add(new PowerUp(gp, spieler, stein, kollisionsChecker, stein.getType()));
    }

    public void startNewGame() throws IOException {
        SaveManager.resetGame();
        aktuellesLevel = SaveManager.loadGame();
        reset();
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public ArrayList<Ball> getBall() {
        return balls;
    }

    public ArrayList<PowerUp> getPowerUp() {
        return powerUps;
    }

    public ArrayList<Stein> getSteine() {
        return steine;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public boolean isGameOver() {
        return leben == 0;
    }

    public int getLeben() {
        return leben;
    }

    public void addLeben() {
        leben++;
    }

    public void increasePlayer() {
        spieler.increasePlayer();
    }

    public void activateProjectiles() {
        projectileActive = 300;
    }

    public void increaseScore() {
        score += 40;
    }
}

