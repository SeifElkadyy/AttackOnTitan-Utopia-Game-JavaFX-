package game.gui.view;

import game.engine.Battle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class GameDifficultyView extends StackPane {

    public GameDifficultyView(Stage primaryStage) {
        // Set padding for the StackPane
        setPadding(new Insets(20));

        // Set background image
        Image backgroundImage = new Image("img_2.png");
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        setBackground(new Background(background));

        // Create buttons for choosing difficulty
        Button easyButton = createDifficultyButton("easy");
        easyButton.setOnAction(event -> {
            Battle battle = null;
            try {
                battle = new Battle(1, 0, 0, 3, 250);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            EasyGameView easyGameView = new EasyGameView(primaryStage, battle);
            performSceneTransition(primaryStage, easyGameView);
        });

        Button hardButton = createDifficultyButton("hard");
        hardButton.setOnAction(event -> {
            Battle battle = null;
            try {
                battle = new Battle(1, 0, 0, 5, 125);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            HardGameView hardGameView = new HardGameView(primaryStage, battle);
            performSceneTransition(primaryStage, hardGameView);
        });

        // Create a VBox to hold the "Easy" and "Hard" buttons vertically aligned
        VBox difficultyButtonsBox = new VBox(20, easyButton, hardButton);
        difficultyButtonsBox.setAlignment(Pos.CENTER);

        // Add back button
        Button backButton = createBackButton(primaryStage);
        VBox.setMargin(backButton, new Insets(20, 0, 0, 0));
        difficultyButtonsBox.getChildren().add(backButton);

        // Ensure the buttons box is not obscured
        difficultyButtonsBox.setMouseTransparent(false);

        // Add buttons VBox to the StackPane
        getChildren().addAll(difficultyButtonsBox);

        // Add fade-in animation for buttons
        difficultyButtonsBox.getChildren().forEach(node -> {
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), node);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        // Add slide-in animation for the VBox
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(1500), difficultyButtonsBox);
        slideIn.setFromY(800);
        slideIn.setToY(0);
        slideIn.play();
    }

    private Button createDifficultyButton(String difficulty) {
        Button button = new Button(difficulty);

        // Apply inline CSS styling for buttons
        button.setStyle("-fx-font-family: 'Goth Titan'; " +
                "-fx-font-size: 40px; " +
                "-fx-text-fill: black; " +
                "-fx-background-color: transparent; " +
                "-fx-background-radius: 10px; " +
                "-fx-border-color: transparent; " +
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        // Set preferred size for buttons
        button.setPrefSize(200, 50);

        // Hover effect
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #666666; " +
                "-fx-font-family: 'Goth Titan'; " +
                "-fx-font-size: 40px; " +
                "-fx-text-fill: black; " +
                "-fx-background-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);"));
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: transparent; " +
                "-fx-font-family: 'Goth Titan'; " +
                "-fx-font-size: 40px; " +
                "-fx-text-fill: black; " +
                "-fx-background-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);"));

        return button;
    }

    private Button createBackButton(Stage primaryStage) {
        Button backButton = new Button("back");
        backButton.setOnAction(event -> {
            MainView mainView = new MainView(primaryStage);
            performSceneTransition(primaryStage, mainView);
        });
        // Apply inline CSS styling for the back button
        backButton.setStyle("-fx-font-family: 'Goth Titan'; " +
                "-fx-font-size: 24px; " +
                "-fx-text-fill: black; " +
                "-fx-background-color: transparent; " +
                "-fx-background-radius: 10px; " +
                "-fx-border-color: transparent; " +
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        // Hover effect
        backButton.setOnMouseEntered(event -> backButton.setStyle("-fx-background-color: #666666; " +
                "-fx-font-family: 'Goth Titan'; " +
                "-fx-font-size: 24px; " +
                "-fx-text-fill: black; " +
                "-fx-background-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);"));

        // Reset button style on mouse exit
        backButton.setOnMouseExited(event -> backButton.setStyle("-fx-font-family: 'Goth Titan'; " +
                "-fx-font-size: 24px; " +
                "-fx-text-fill: black; " +
                "-fx-background-color: transparent; " +
                "-fx-background-radius: 10px; " +
                "-fx-border-color: transparent; " +
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);"));

        return backButton;
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
}
