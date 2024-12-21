package game.gui.view;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class WeaponShopView extends VBox {
    private static final String BUTTON_STYLE_NORMAL =
            "-fx-font-family: 'impact'; " +
                    "-fx-font-size: 30px; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-color: transparent; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-border-color: transparent; " +
                    "-fx-padding: 10px 20px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);" +
                    "-fx-text-transform: lowercase;";
    private static final String BUTTON_STYLE_NORMAL2 =
            "-fx-font-family: 'impact'; " +
                    "-fx-font-size: 30px; " +
                    "-fx-text-fill: Black; " +
                    "-fx-background-color: transparent; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-border-color: transparent; " +
                    "-fx-padding: 10px 20px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);" +
                    "-fx-text-transform: lowercase;";
    private static final String BUTTON_STYLE_HOVER =
            "-fx-background-color: #666666; " +
                    "-fx-font-family: 'impact'; " +
                    "-fx-font-size: 30px; " +
                    "-fx-text-fill: black; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-padding: 10px 20px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);";
 
    private Stage primaryStage;
    private Battle battle;
    private Label resourcesLabel;

    public WeaponShopView(Stage primaryStage, Battle battle) {
        this.primaryStage = primaryStage;
        this.battle = battle;

        // Set up the layout of the weapon shop view
        setSpacing(20);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);

        // Initialize the resources label
        resourcesLabel = new Label("Resources: " + battle.getResourcesGathered());
        resourcesLabel.setStyle(BUTTON_STYLE_NORMAL);

        // Set background image
        Image backgroundImage = new Image("WeaponShop.jpg");
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );

        setBackground(new Background(background));

        // Create a GridPane to display the weapons
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setStyle("-fx-background-color: Transparent; -fx-border-color: White; -fx-border-width: 2px;");

        Label nameLabel = createStyledLabel("Name", BUTTON_STYLE_NORMAL2);
        Label typeLabel = createStyledLabel("Type", BUTTON_STYLE_NORMAL2);
        Label priceLabel = createStyledLabel("Price", BUTTON_STYLE_NORMAL2);
        Label damageLabel = createStyledLabel("Damage", BUTTON_STYLE_NORMAL2);


        gridPane.addRow(0, nameLabel, typeLabel, priceLabel, damageLabel);

        // Add weapons to the grid pane
        addWeaponToGrid(gridPane, 1, "Anti-Titan Shell", "Piercing Cannon", 25, 10, "Tank.png");
        addWeaponToGrid(gridPane, 2, "Long Range Spear", "Sniper Cannon", 35, 25, "Spear.png");
        addWeaponToGrid(gridPane, 3, "Wall Spread Cannon", "Volley Spread Cannon", 100, 5, "Cannon.png");
        addWeaponToGrid(gridPane, 4, "Proximity Trap", "Wall Trap", 75, 100, "Trap2.png");

        // Add the resources label and grid pane to the view
        getChildren().addAll(resourcesLabel, gridPane);

        // Add a Back button to close the WeaponShopView
        Button backButton = new Button("Back");
        backButton.setStyle(BUTTON_STYLE_NORMAL);
        backButton.setOnMouseEntered(event -> backButton.setStyle(BUTTON_STYLE_HOVER));
        backButton.setOnMouseExited(event -> backButton.setStyle(BUTTON_STYLE_NORMAL));
        backButton.setOnAction(event -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        });
        getChildren().add(backButton);
    }

    private void addWeaponToGrid(GridPane gridPane, int row, String name, String type, int price, int damage, String imageName) {
        // Load image for the weapon
        Image weaponImage = new Image(imageName);
        ImageView imageView = new ImageView(weaponImage);
        imageView.setFitWidth(50); // Set the width of the image
        imageView.setPreserveRatio(true); // Preserve aspect ratio

        // Create a HBox for the name and image
        HBox nameImageBox = new HBox(5);
        nameImageBox.setAlignment(Pos.CENTER_LEFT);
        Label nameLabel = createStyledLabel(name);
        nameImageBox.getChildren().addAll(imageView, nameLabel);

        gridPane.addRow(row, nameImageBox, createStyledLabel(type), createStyledLabel(String.valueOf(price)), createStyledLabel(String.valueOf(damage)));

        Button purchaseButton = new Button("Purchase");
        purchaseButton.setStyle(BUTTON_STYLE_NORMAL);
        purchaseButton.setOnMouseEntered(event -> purchaseButton.setStyle(BUTTON_STYLE_HOVER));
        purchaseButton.setOnMouseExited(event -> purchaseButton.setStyle(BUTTON_STYLE_NORMAL));
        purchaseButton.setOnAction(event -> {
            // Implement purchase logic here
            if (battle.getResourcesGathered() >= price) {
                promptForLaneSelection(row);
            } else {
                showAlert("Insufficient Resources", "You do not have enough resources to purchase this weapon.");
            }
        });

        gridPane.add(purchaseButton, 4, row);
    }

    private void promptForLaneSelection(int weaponCode) {
        // Create a VBox for lane selection
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        // Set background image for lane selection
        Image backgroundImage2 = new Image("WeaponShop2.jpg");
        BackgroundImage background2 = new BackgroundImage(
                backgroundImage2,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        vbox.setBackground(new Background(background2));

        Label instructionLabel = createStyledLabel("Select a lane to place the weapon:");
        vbox.getChildren().add(instructionLabel);

        List<Lane> lanes = battle.getOriginalLanes();
        for (int i = 0; i < lanes.size(); i++) {
            int laneIndex = i;
            Button laneButton = new Button("Lane " + (i + 1));
            laneButton.setStyle(BUTTON_STYLE_NORMAL);
            laneButton.setOnMouseEntered(event -> laneButton.setStyle(BUTTON_STYLE_HOVER));
            laneButton.setOnMouseExited(event -> laneButton.setStyle(BUTTON_STYLE_NORMAL));
            laneButton.setOnAction(event -> {
                Lane selectedLane = lanes.get(laneIndex);
                try {
                    battle.purchaseWeapon(weaponCode, selectedLane);
                    resourcesLabel.setText("Resources: " + battle.getResourcesGathered());
                    updateGameView();
                    Stage stage = (Stage) laneButton.getScene().getWindow();
                    stage.close();
                } catch (InsufficientResourcesException e) {
                    showAlert("Insufficient Resources", "You do not have enough resources to purchase this weapon.");
                } catch (InvalidLaneException e) {
                    showAlert("Invalid Lane", "The selected lane is invalid.");
                }
            });
            vbox.getChildren().add(laneButton);
        }


        Stage laneSelectionStage = new Stage();
        Scene scene = new Scene(vbox);
        laneSelectionStage.setScene(scene);
        laneSelectionStage.show();
    }

    private void updateGameView() {
        Scene scene = primaryStage.getScene();
        if (scene.getRoot() instanceof EasyGameView) {
            EasyGameView easyGameView = new EasyGameView(primaryStage, battle);
            scene.setRoot(easyGameView);
        } else if (scene.getRoot() instanceof HardGameView) {
            HardGameView hardGameView = new HardGameView(primaryStage, battle);
            scene.setRoot(hardGameView);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle(BUTTON_STYLE_NORMAL);
        return label;
    }
 // Inside your class...

    private Label createStyledLabel(String text, String style) {
        Label label = new Label(text);
        label.setStyle(style + "-fx-text-fill: black;");
        return label;
    }


}