package game.gui.view;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SettingsView extends StackPane {

    public SettingsView(Stage primaryStage) {
        MediaPlayerManager mediaPlayerManager = MediaPlayerManager.getInstance();

        // Ensure music is playing
        if (!mediaPlayerManager.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayerManager.getMediaPlayer().play();
        }

        // Create volume slider
        Slider volumeSlider = new Slider(0, 1, mediaPlayerManager.getMediaPlayer().getVolume());
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(0.1);
        volumeSlider.setBlockIncrement(0.1);

        // Inline CSS for the slider
        volumeSlider.setStyle(
                "-fx-padding: 10px; " +
                        "-fx-background-color: transparent;"
        );

        // Additional CSS for the track and thumb
        String sliderCSS = """
            .slider .track {
                -fx-background-color: linear-gradient(to bottom, #3c3c3c, #383838);
                -fx-background-insets: 0;
                -fx-background-radius: 5px;
            }
            .slider .thumb {
                -fx-background-color: #66b3ff; /* Light blue color */
                -fx-background-insets: 0;
                -fx-background-radius: 5px;
                -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.75), 5, 0.5, 0, 0);
            }
            .slider .tick-mark {
                -fx-stroke: #ffffff;
            }
            .slider .tick-label {
                -fx-font-size: 12px;
                -fx-fill: #ffffff;
            }
        """;

        volumeSlider.getStylesheets().add("data:, " + sliderCSS);

        // Bind mediaPlayer volume to slider and add a listener for debug output
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayerManager.getMediaPlayer().setVolume(newValue.doubleValue());
        });

        // Create mute button
        Button muteButton = createStyledButton("mute");
        muteButton.setOnAction(event -> {
            boolean isMuted = mediaPlayerManager.getMediaPlayer().isMute();
            mediaPlayerManager.getMediaPlayer().setMute(!isMuted);
            muteButton.setText(isMuted ? "mute" : "unmute");
        });

        // Create back button with transition
        Button backButton = createStyledButton("back");
        backButton.setOnAction(event -> {
            MainView mainView = new MainView(primaryStage);
            performSceneTransition(primaryStage, mainView);
        });

        // Layout setup
        VBox controlsBox = new VBox(20, volumeSlider, muteButton, backButton);
        controlsBox.setAlignment(Pos.CENTER);
        controlsBox.setPadding(new Insets(20));
        getChildren().add(controlsBox);

        // Set background image
        Image backgroundImage = new Image("background.jpg"); // Adjust the path to your image
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, false, true) // This line ensures the image covers the entire background
        );
        setBackground(new Background(background));

        // Center the controls box in the settings view
        setAlignment(Pos.CENTER);

        // Add fade-in animation for sliders and buttons
        controlsBox.getChildren().forEach(node -> {
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), node);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        // Add slide-in animation for the VBox
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(1500), controlsBox);
        slideIn.setFromY(800);
        slideIn.setToY(0);
        slideIn.play();
    }

    private void performSceneTransition(Stage primaryStage, StackPane newView) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), primaryStage.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            primaryStage.getScene().setRoot(newView);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newView);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);

        // Apply inline CSS styling for buttons
        button.setStyle("-fx-font-family: 'Goth Titan'; " +
                "-fx-font-size: 40px; " +
                "-fx-text-fill: white; " +
                "-fx-background-color: transparent; " +
                "-fx-background-radius: 10px; " +
                "-fx-border-color: transparent; " + // Remove border
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        // Hover effect
        button.setOnMouseEntered(event -> button.setStyle(button.getStyle() + "-fx-background-color: #666666;"));
        button.setOnMouseExited(event -> button.setStyle(button.getStyle().replace("-fx-background-color: #666666;", "")));

        return button;
    }
}
