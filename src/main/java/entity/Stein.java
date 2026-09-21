package entity;

import main.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Stein extends Entitaet {

    int type;


    public Stein(GamePanel gp, int x, int y, int type) {

        this.gp = gp;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void renderSteinImage(GraphicsContext gc) {
        if (type == 0) {gc.setFill(Color.YELLOW);}
        else if (type == 1) {gc.setFill(Color.BLUE);}
        else if (type == 2) {gc.setFill(Color.GREEN);}
        else if (type == 3) {gc.setFill(Color.RED);}
        else if (type == 4) {gc.setFill(Color.GREY);}
        gc.fillRect(x, y, gp.tileSize, gp.tileSize / 2.0);

    }

    public int  getPositionX() {return (int) x;}

    public int getPositionY() {return (int) y;}

    public int getType() {return type;}

}