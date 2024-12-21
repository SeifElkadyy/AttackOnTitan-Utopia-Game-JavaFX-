package game.gui.view;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeView extends StackPane {

    private MediaPlayer mediaPlayer;

    public WelcomeView(Stage primaryStage) {
        // Load background image
        Image backgroundImage = new Image("463603.jpg");

        // Create background image view
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );

        // Set background to the root pane
        setBackground(new Background(background));
        primaryStage.setMaximized(true); // Ensure the stage is maximized

        // Title text
        Text titleText = new Text("attack on titan - tower defense game");
        titleText.setFont(Font.loadFont(getClass().getResourceAsStream("GothTitan-0W5md.ttf"), 120)); // Load and set custom font
        titleText.setFill(Color.WHITE); // Set text color

        // Add shadow effect to the title text
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(3.0);
        dropShadow.setOffsetX(2.0);
        dropShadow.setOffsetY(2.0);
        dropShadow.setColor(Color.BLACK);
        titleText.setEffect(dropShadow);

        StackPane.setAlignment(titleText, Pos.TOP_CENTER); // Align text to the top center
        setMargin(titleText, new Insets(90, 0, 0, 0)); // Add top margin of 90px

        // Create VBox to hold progress bar and button
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50));

        // Progress bar
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setMaxWidth(200); // Set maximum width of progress bar
        progressBar.setVisible(false); // Initially hide the progress bar

        // Enter button
        Button enterButton = createStyledButton("play game");
        enterButton.setOnAction(e -> switchToMainView(primaryStage)); // Set action for Enter button click
        enterButton.setVisible(false); // Initially hide the button

        content.getChildren().addAll(progressBar, enterButton);

        setAlignment(Pos.CENTER);
        getChildren().addAll(titleText, content);

        // Create and play timeline to show progress bar and button after a delay
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> progressBar.setVisible(true)), // Show progress bar
                new KeyFrame(Duration.seconds(1)), // Wait for 1 second
                new KeyFrame(Duration.seconds(2), e -> animateProgressBar(progressBar)), // Animate progress bar
                new KeyFrame(Duration.seconds(4), e -> { // Wait for 2 seconds
                    progressBar.setVisible(false); // Hide progress bar
                    enterButton.setVisible(true); // Show button
                })
        );
        timeline.setCycleCount(1); // Play timeline once
        timeline.play(); // Start the loading animation
    }

    private void switchToMainView(Stage primaryStage) {
        MainView mainView = new MainView(primaryStage);
        animateSceneSwitch(primaryStage, mainView);
    }

    private void animateSceneSwitch(Stage primaryStage, StackPane newView) {
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

    private void animateProgressBar(ProgressBar progressBar) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(progressBar.progressProperty(), 1))
        );
        timeline.play();
    }
}
