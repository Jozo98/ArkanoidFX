package main;

import entity.Ball;
import entity.PowerUp;
import entity.Projectile;
import entity.Stein;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;

public class GamePanel extends Canvas implements Runnable {

    private static final int originalTitleSize = 16;
    private static final int scale = 3;
    private static final int maxScreenCol = 16;
    private static final int maxScreenRow = 16;

    public static final int tileSize = originalTitleSize * scale;
    public static final int screenWidth = tileSize * maxScreenCol;
    public static final int screenHeight = tileSize * maxScreenRow;

    Game game;
    UI ui;
    KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    GameStateManager gsm;
    Thread gameThread;
    GraphicsContext gc;

    public GamePanel() throws IOException {
        super(screenWidth, screenHeight);
        gc = getGraphicsContext2D();
        this.setFocusTraversable(true);
        this.setOnKeyPressed(keyH::keyPressed);
        this.setOnKeyReleased(keyH::keyReleased);
        game = new Game(this, keyH);
        ui = new UI(this);
        gsm = new GameStateManager(this, keyH);
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        gameThread.setDaemon(true);
        gameThread.start();
    }

    @Override
    public void run() {

        double FPS = 60;
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            try {
                gsm.updateGameState();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (gsm.getGameState() == GameState.EXIT) {
                Platform.exit();
                return;
            }
            if (gsm.getGameState() == GameState.PLAY) {
                try {
                    game.update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            Platform.runLater(this::draw);

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void draw() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        if (gsm.getGameState() == GameState.PLAY) {
            for (Stein stein : game.getSteine()) {
                stein.renderSteinImage(gc);
            }
            game.getSpieler().draw(gc);
            for (Ball b : game.getBall()) {
                b.draw(gc);
            }
            for (PowerUp p : game.getPowerUp()) {
                p.draw(gc);
            }
            for (Projectile pj : game.getProjectiles()) {
                pj.draw(gc);
            }
        }
        ui.draw(gc);
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public void removeSteinFromImage(Stein stein) {
    }

    public void resetSteineImage() {
    }
}