package game.gui;

import game.engine.Battle;
import game.gui.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Show the IntroView first
        IntroVideoView introView = new IntroVideoView(primaryStage);
        EasyGameView easyGameView = new EasyGameView(primaryStage,new Battle(1,0,0,3,250));
        Scene introScene = new Scene(introView, 800, 600);
        primaryStage.setScene(introScene);
        primaryStage.setResizable(true);
        primaryStage.setMaximized(true);
//        primaryStage.setFullScreen(true);
//        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("Attack On Titan - Tower Defense Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
