package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        GamePanel gamePanel = new GamePanel();

        Pane root = new Pane(gamePanel);
        Scene scene = new Scene(root, gamePanel.getWidth(), gamePanel.getHeight());

        stage.setTitle("Arkanoid");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.show();

        gamePanel.startGameThread();
        gamePanel.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}