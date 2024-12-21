package game.gui.view;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainView extends StackPane {
    public MainView(Stage primaryStage) {
        // Set background image
        Image backgroundImage = new Image("attack-on-titan-final-season-uhdpaper.com-4K-8.2101.jpg");
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        setBackground(new Background(background));

        // Create the VBox for the buttons
        VBox buttonContainer = new VBox();
        buttonContainer.setSpacing(20);
        buttonContainer.setPadding(new Insets(20));
        buttonContainer.setAlignment(Pos.CENTER_LEFT);
        buttonContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Button for starting the game
        Button startGameButton = createStyledButton("start game");
        startGameButton.setOnAction(e -> switchToGameDifficultyView(primaryStage));

        // Button for accessing game rules
        Button gameRulesButton = createStyledButton("game rules");
        gameRulesButton.setOnAction(event -> switchToGameRulesView(primaryStage));

        // Button for viewing credits
        Button creditsButton = createStyledButton("credits");
        creditsButton.setOnAction(event -> switchToCreditsView(primaryStage));

        Button SettingButton = createStyledButton("settings");
        SettingButton.setOnAction(event -> switchToSettingsView(primaryStage));

        Button exitButton = createStyledButton("exit");
        exitButton.setOnAction(event -> System.exit(0));

        // Add buttons to the VBox with left padding
        VBox.setMargin(startGameButton, new Insets(0, 0, 0, 20));
        VBox.setMargin(gameRulesButton, new Insets(0, 0, 0, 20));
        VBox.setMargin(creditsButton, new Insets(0, 0, 0, 20));
        VBox.setMargin(SettingButton, new Insets(0,0,0,20));
        VBox.setMargin(exitButton, new Insets(0, 0, 0, 20));

        buttonContainer.getChildren().addAll(startGameButton, gameRulesButton, creditsButton, SettingButton,exitButton);

        // Add the button container to the StackPane
        getChildren().add(buttonContainer);

        // Add fade-in animation for buttons
        buttonContainer.getChildren().forEach(node -> {
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), node);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        // Add slide-in animation for the VBox
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(1500), buttonContainer);
        slideIn.setFromX(-800);
        slideIn.setToX(0);
        slideIn.play();
    }

    private void switchToSettingsView(Stage primaryStage) {
        SettingsView settingsView = new SettingsView(primaryStage);
        performSceneTransition(primaryStage,settingsView);
    }

    private void switchToGameDifficultyView(Stage primaryStage) {
        GameDifficultyView gameDifficultyView = new GameDifficultyView(primaryStage);
        performSceneTransition(primaryStage, gameDifficultyView);
    }

    private void switchToGameRulesView(Stage primaryStage) {
        GameRulesView gameRulesView = new GameRulesView(primaryStage);
        performSceneTransition(primaryStage, gameRulesView);
    }

    private void switchToCreditsView(Stage primaryStage) {
        CreditsView creditsView = new CreditsView(primaryStage);
        performSceneTransition(primaryStage, creditsView);
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
                "-fx-border-color: transparent; " +
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        // Hover effect
        button.setOnMouseEntered(event -> button.setStyle(button.getStyle() + "-fx-background-color: #666666;"));
        button.setOnMouseExited(event -> button.setStyle(button.getStyle().replace("-fx-background-color: #666666;", "")));

        return button;
    }
}
