package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UI {

    private GamePanel gp;
    private GraphicsContext gc;
    private int menuNum = 0;
    private Image ball1;

    public UI(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(GraphicsContext gc) {

        this.gc = gc;
        GameState gameState = gp.gsm.getGameState();

        switch (gameState) {

            case TITLE:
                drawMenu();
                if (gp.keyH.upPressed && menuNum > 0) {
                    menuNum--;
                } else if (gp.keyH.downPressed && menuNum < 2) {
                    menuNum++;
                }
                if (gp.keyH.escapePressed || gp.keyH.enterPressed) {
                    menuNum = 0;
                    gp.keyH.escapePressed = false;
                    gp.keyH.enterPressed = false;
                }
                if (gp.keyH.downPressed || gp.keyH.upPressed) {
                    gp.keyH.downPressed = false;
                    gp.keyH.upPressed = false;
                }
                break;

            case PLAY:
                drawHUD();
                drawScore();
                break;

            case GAME_OVER:
                drawGameOver();
                if (gp.keyH.upPressed && menuNum > 0) {
                    menuNum--;
                } else if (gp.keyH.downPressed && menuNum < 1) {
                    menuNum++;
                }
                break;
        }
    }

    public void drawMenu() {
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        gc.setFont(Font.font("System", FontWeight.BOLD, 96));
        String text = "Arkanoid";

        double x = getCenterX(text);
        double y = gp.tileSize * 3;

        gc.setFill(Color.BLACK);
        gc.fillText(text, x, y);

        gc.setFill(Color.WHITE);
        gc.fillText(text, x + 5, y + 5);

        gc.setFont(Font.font("System", FontWeight.BOLD, 40));
        text = "NEW GAME";
        x = getCenterX(text);
        y += gp.tileSize * 2;
        gc.fillText(text, x, y);
        if (menuNum == 0) {
            gc.fillText(">", x - gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getCenterX(text);
        y += gp.tileSize + gp.tileSize / 2.0;
        gc.fillText(text, x, y);
        if (menuNum == 1) {
            gc.fillText(">", x - gp.tileSize, y);

        }

        text = "QUIT GAME";
        x = getCenterX(text);
        y += gp.tileSize + gp.tileSize / 2.0;
        gc.fillText(text, x, y);
        if (menuNum == 2) {
            gc.fillText(">", x - gp.tileSize, y);
        }
    }

    public double getCenterX(String text) {
        Text helper = new Text(text);
        helper.setFont(gc.getFont());
        double length = helper.getLayoutBounds().getWidth();
        return (gp.screenWidth - length) / 2;
    }

    public void drawHUD() {
        if (ball1 == null) {
            ball1 = new Image(getClass().getResourceAsStream("/Ball.png"));
        }

        int ballSize = gp.tileSize / 2;
        int startX = 0;
        int y = gp.tileSize * 15 + gp.tileSize / 2;

        for (int i = 0; i < gp.game.getLeben(); i++) {
            gc.drawImage(ball1, startX + i * ballSize, y, ballSize, ballSize);
        }
        gc.setFill(Color.SILVER);
        gc.fillRect(gp.screenWidth - gp.tileSize * 4, 0, 5, gp.screenHeight);
    }

    public void drawScore() {
        String text = String.valueOf(gp.game.score);
        int x = gp.screenWidth - gp.tileSize / 2 * 7;
        int y = gp.tileSize * 2;
        gc.setFill(Color.WHITE);

        gc.setFont(Font.font("System", FontWeight.BOLD, 20));
        gc.fillText(text, x, y);
    }

    public void drawGameOver() {
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("System", FontWeight.BOLD, 96));
        String text = "Game Over";
        double x = getCenterX(text);
        double y = gp.tileSize * 5;
        gc.fillText(text, x, y);
        gc.setFill(Color.WHITE);
        gc.fillText(text, x + 5, y + 5);

        gc.setFont(Font.font("System", FontWeight.BOLD, 40));
        text = "TRY AGAIN";
        x = getCenterX(text);
        y += gp.tileSize * 2;
        gc.fillText(text, x, y);
        if (menuNum == 0) {
            gc.fillText(">", x - gp.tileSize, y);
        }

        gc.setFont(Font.font("System", FontWeight.BOLD, 40));
        text = "BACK TO MENU";
        x = getCenterX(text);
        y += gp.tileSize * 2;
        gc.fillText(text, x, y);
        if (menuNum == 1) {
            gc.fillText(">", x - gp.tileSize, y);
        }
    }

    public int getMenuNum() {
        return menuNum;
    }
}