package main;

import entity.PowerUp;

public class PowerUpManager {
    public static void aktivierePowerUp(PowerUp powerUp, Game game) {
        switch (powerUp.getType()) {
            case 2 -> game.addLeben();
            case 4 -> game.activateProjectiles();
        }
    }
}