package game.gui.view;

import game.gui.view.WelcomeView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

public class IntroVideoView extends StackPane {

    public IntroVideoView(Stage primaryStage) {
        // Path to your video file
        String videoPath = new File("C:\\Users\\User\\Downloads\\Attack on Titan 2 - Opening Cinematic.mp4").toURI().toString();

        // Create Media and MediaPlayer
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Create MediaView
        MediaView mediaView = new MediaView(mediaPlayer);

//        // Preserve the aspect ratio of the video
        mediaView.setPreserveRatio(true);
//
//        // Set a fixed width for the MediaView
        double desiredWidth = 1540; // Set your desired width here
        mediaView.setFitWidth(desiredWidth);

//        // Center the MediaView in the StackPane
        setAlignment(mediaView, Pos.CENTER);
//
//        // Add MediaView to the layout
        getChildren().add(mediaView);
//
//        // Play the video
        mediaPlayer.setAutoPlay(true);
//
//        // Set an action after the video ends
        mediaPlayer.setOnEndOfMedia(() -> transitionToWelcomeView(primaryStage));
//
//        // Add a hint label to skip the intro
        Label skipHint = new Label("Press any key to skip");
        skipHint.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.5);");
        StackPane.setAlignment(skipHint, Pos.BOTTOM_CENTER);
        StackPane.setMargin(skipHint, new javafx.geometry.Insets(10));
        getChildren().add(skipHint);
//
//        // Add key event handler to skip the intro when any key is pressed
        setOnKeyPressed(event -> {
            mediaPlayer.stop();
            transitionToWelcomeView(primaryStage);
        });
//
//        // Request focus to receive key events
        primaryStage.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != null) {
                mediaPlayer.stop();
                transitionToWelcomeView(primaryStage);
            }
        });

        // Request focus on the StackPane to receive key events
        setFocusTraversable(true);
        requestFocus();

        // Maximize the primary stage and set it to full screen
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(""); // Hide the default exit hint
    }

    private void transitionToWelcomeView(Stage primaryStage) {
        WelcomeView welcomeView = new WelcomeView(primaryStage);
        Scene scene = new Scene(welcomeView, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
//        primaryStage.setFullScreen(true);  /// Ensure the new scene is also in full screen
        primaryStage.setFullScreenExitHint(""); // Hide the default exit hint
    }
}
