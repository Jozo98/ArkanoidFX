package main;

import entity.Ball;
import entity.Projectile;
import entity.Stein;

public class SteinManager {

    public static void aktiviereStein(Stein stein, Ball ball, Game game) {
        switch (stein.getType()) {
            case 1 -> game.addBall(ball.getPositionX(), ball.getPositionY());
            case 2, 4 -> game.addPowerUp(stein);
            case 3 -> game.increasePlayer();
        }
    }

    public static void aktiviereStein(Stein stein, Projectile projectile, Game game) {
        switch (stein.getType()) {
            case 1 -> game.addBall(projectile.getPositionX(), projectile.getPositionY());
            case 2 -> game.addPowerUp(stein);
            case 3 -> game.increasePlayer();
            case 4 -> game.activateProjectiles();
        }
    }
}
