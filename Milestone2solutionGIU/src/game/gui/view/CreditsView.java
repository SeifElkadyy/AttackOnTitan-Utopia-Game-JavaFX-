package game.gui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class CreditsView extends StackPane {

    public CreditsView(Stage primaryStage) {
        // Load background image
        Image backgroundImage = new Image("credits.jpg"); // Adjust the path to your image
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(this.widthProperty());
        backgroundImageView.fitHeightProperty().bind(this.heightProperty());
        getChildren().add(backgroundImageView);

        // Create and position each text node
        Text text1 = createStyledText("seif eldein waleed");
        Text text2 = createStyledText("seif ashraf");
        Text text3 = createStyledText("hassan roshdy");

        text1.setTranslateY(-100); // Move text1 up
        text2.setTranslateY(0);   // Keep text2 at its position
        text3.setTranslateY(100);  // Move text3 down

        // Add text nodes to stack pane
        getChildren().addAll(text1, text2, text3);

        // Create back button
        Button backButton = createStyledButton("back");
        backButton.setOnAction(event -> {
            MainView mainView = new MainView(primaryStage);
            performSceneTransition(primaryStage, mainView);
        });

        // Position the back button
        StackPane.setAlignment(backButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(backButton, new Insets(0, 20, 20, 0)); // Adjust padding

        getChildren().add(backButton);
    }

    private Text createStyledText(String text) {
        Text styledText = new Text(text);
        styledText.setFont(Font.loadFont(getClass().getResourceAsStream("GothTitan-0W5md.ttf"), 120)); // Use custom font
        styledText.setFill(Color.WHITE);
        styledText.setStroke(Color.BLACK); // Add stroke for better visibility
        styledText.setStrokeWidth(2);      // Set stroke width
        return styledText;
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
