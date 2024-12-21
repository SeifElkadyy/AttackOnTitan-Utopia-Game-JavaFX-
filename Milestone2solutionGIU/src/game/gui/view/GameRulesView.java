package game.gui.view;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameRulesView extends StackPane {

    public GameRulesView(Stage primaryStage) {

        // Set the background image
        Image backgroundImage = new Image("DALLE2024-05-1602.39.14-AminimalistwidewallpaperinspiredbyAttackonTitan.ThewallpaperfeaturesasimplecleandesignwithsilhouettesofthemaincharactersEren-ezgif.com-webp-to-jpg-converter.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
        setBackground(new Background(new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                backgroundSize)));

        // Create a semi-transparent overlay
        VBox overlay = new VBox();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");
        overlay.setPadding(new Insets(20));
        overlay.setAlignment(Pos.CENTER);
        overlay.setSpacing(20);

        // Create a label for the title
        Label titleLabel = new Label("instructions");
        titleLabel.setFont(Font.font("Goth Titan", 50));
        titleLabel.setTextFill(Color.WHITE);

        // Create a label with the game rules description
        Label gameRulesLabel = new Label("attack on titan: utopia is an endless tower defense game inspired by the anime attack on titan.\n" +
                "the story of the anime revolves around how titans, gigantic humanoid creatures, emerged one day and wiped out most of humanity.\n" +
                "the few surviving humans fled and hid behind three great walls that provided safe haven from the titan threats.\n" +
                "wall maria is the outer wall, wall rose is the middle wall, and wall sina is the inside wall.\n\n" +
                "this game takes place in an imaginary scenario where the titans breached their way throughout wall maria and reached the northern border of wall rose at the utopia district.\n" +
                "the human forces stationed in utopia engage the titans in battle for one last hope of preventing the titans from breaching wall rose.\n" +
                "the humans fight by deploying different types of anti-titan weapons in order to stop the titan’s onslaught and keep utopia’s (and wall rose’s) walls safe.");
        gameRulesLabel.setFont(Font.font("Goth Titan", 23));
        gameRulesLabel.setTextFill(Color.WHITE);
        gameRulesLabel.setWrapText(true);

        // Create a button for the back action
        Button backButton = createStyledButton("back");
        backButton.setOnAction(event -> {
            MainView mainView = new MainView(primaryStage);
            performSceneTransition(primaryStage, mainView);
        });

        // Create a VBox for the content
        VBox contentBox = new VBox(20, titleLabel, gameRulesLabel, backButton);
        contentBox.setPadding(new Insets(20));
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(800);

        // Add the overlay and content to the StackPane
        StackPane container = new StackPane(contentBox);
        container.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");
        container.setMaxWidth(800);
        container.setPadding(new Insets(20));
        StackPane.setAlignment(container, Pos.CENTER);

        getChildren().add(container);
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
