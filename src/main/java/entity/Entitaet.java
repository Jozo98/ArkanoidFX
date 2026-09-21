package entity;

import javafx.scene.paint.Color;
import main.GamePanel;
import main.KeyHandler;
import javafx.scene.canvas.GraphicsContext;

abstract class Entitaet {

    public double x, y;
    public int geschwindigkeit;
    GamePanel gp;
    KeyHandler keyH;

    public void setDefaultValues() {

    }

    public void draw(GraphicsContext gc) {
    }
}