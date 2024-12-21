package game.gui.view;

import game.engine.Battle;
import game.engine.lanes.Lane;
import game.engine.titans.*;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.Weapon;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EasyGameView extends StackPane {

    private static final String BUTTON_STYLE_NORMAL =
            "-fx-font-family: 'impact'; " +
                    "-fx-font-size: 22px; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-color: transparent; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-border-color: transparent; " +
                    "-fx-padding: 10px 20px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);" +
                    "-fx-text-transform: lowercase;";

    private static final String BUTTON_STYLE_HOVER =
            "-fx-background-color: #666666; " +
                    "-fx-font-family: 'impact'; " +
                    "-fx-font-size: 22px; " +
                    "-fx-text-fill: black; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-padding: 10px 20px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);";

    private static final String LABEL_STYLE = "-fx-font-family: 'Arial'; -fx-font-size: 18px; -fx-text-fill: white;";
    private static final String INFO_BOX_STYLE = "-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 10px; -fx-spacing: 20px;";

    private static final Image PURE_TITAN_IMAGE = new Image("eren_titan_mappa__png_by_dariotsc_dfjn72y-375w-2x.png");
    private static final Image ARMORED_TITAN_IMAGE = new Image("Armor_titan-ezgif.com-gif-maker-removebg.png");
    private static final Image COLOSSAL_TITAN_IMAGE = new Image("colosllaltitan-removebg-preview.png");
    private static final Image DEFAULT_TITAN_IMAGE = new Image("abn.png");
    private static final Image PIERCING_CANNON_IMAGE = new Image("Tank.png");
    private static final Image SNIPER_CANNON_IMAGE = new Image("Spear.png");
    private static final Image VOLLEY_SPREAD_CANNON_IMAGE = new Image("Cannon.png");
    private static final Image DEFAULT_WEAPON_IMAGE = new Image("Trap2.png");
    private static final Image WALL_IMAGE = new Image("stone-castle-wall-background-vector-600nw-1986484865-ezgif.com-webp-to-jpg-converter-removebg-preview.png");
    private static final Image BROKEN_WALL_IMAGE = new Image("Broken-ezgif.com-webp-to-jpg-converter-removebg-preview.png");

    private Battle battle;
    private GameAI gameAI; // AI instance
    private boolean aiActive = false; // Flag to check if AI is active

    // UI components that need to be updated
    private Label scoreLabel;
    private Label resourcesLabel;
    private Label turnLabel;
    private Label currentPhaseLabel;
    private VBox laneBox;
    private Map<Titan, ImageView> titanImageMap = new HashMap<>();
    private Map<Titan, Double> titanPositions = new HashMap<>(); // Track Titan positions
    private Map<Weapon, ImageView> weaponImageMap = new HashMap<>(); // Track Weapon images
    private Button weaponShopButton; // Declare the weapon shop button at class level

    public EasyGameView(Stage primaryStage, Battle battle) {
        this.battle = battle;
        this.gameAI = new GameAI(battle); // Initialize AI

        // Create a BorderPane as the root layout
        BorderPane root = new BorderPane();

        // Load the background image
        Image backgroundImage = new Image("bggg-ezgif.com-webp-to-jpg-converter (1).jpg");

        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );

        // Set the background image to the BorderPane
        root.setBackground(new Background(background));

        // Initialize UI components
        initializeUI(primaryStage);

        // Add a listener to call updateUI() once the scene is set
        sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                updateUI();
            }
        });

        // Set top and center components
        root.setTop(createInfoBox(primaryStage));
        root.setCenter(createLaneBox());

        // Add the BorderPane to the StackPane
        getChildren().add(root);
    }

    private void initializeUI(Stage primaryStage) {
        // Create labels for game information
        scoreLabel = new Label();
        resourcesLabel = new Label();
        turnLabel = new Label();
        currentPhaseLabel = new Label();

        // Style labels
        scoreLabel.setStyle(BUTTON_STYLE_NORMAL);
        resourcesLabel.setStyle(BUTTON_STYLE_NORMAL);
        turnLabel.setStyle(BUTTON_STYLE_NORMAL);
        currentPhaseLabel.setStyle(BUTTON_STYLE_NORMAL);

        // Initialize lane box
        laneBox = new VBox(20); // Adjust spacing as needed
        laneBox.setAlignment(Pos.CENTER_LEFT);
        laneBox.setPadding(new Insets(125, 0, 0, 0)); // Adjust top padding to move all rectangles down
    }

    private HBox createInfoBox(Stage primaryStage) {
        // Create a layout to hold the labels and buttons
        HBox infoBox = new HBox(20);
        infoBox.setAlignment(Pos.TOP_LEFT);
        infoBox.setPadding(new Insets(10));
        infoBox.setStyle(INFO_BOX_STYLE); // Apply styling to the HBox

        // Create a Region to push the buttons to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Create Weapon Shop button
        weaponShopButton = new Button("Weapon Shop"); // Initialize here
        weaponShopButton.setStyle(BUTTON_STYLE_NORMAL);
        weaponShopButton.setOnMouseEntered(e -> weaponShopButton.setStyle(BUTTON_STYLE_HOVER));
        weaponShopButton.setOnMouseExited(e -> weaponShopButton.setStyle(BUTTON_STYLE_NORMAL));
        weaponShopButton.setOnAction(e -> showWeaponShopDialog(primaryStage, battle));

        // Create Back button
        Button backButton = new Button("Back");
        backButton.setStyle(BUTTON_STYLE_NORMAL);
        backButton.setOnMouseEntered(event -> backButton.setStyle(BUTTON_STYLE_HOVER));
        backButton.setOnMouseExited(event -> backButton.setStyle(BUTTON_STYLE_NORMAL));
        backButton.setOnAction(event -> {
            GameDifficultyView gameDifficultyView = new GameDifficultyView(primaryStage);
            performSceneTransition(primaryStage, gameDifficultyView);
        });

        // Create Pass Turn button
        Button passTurnButton = new Button("Pass Turn");
        passTurnButton.setStyle(BUTTON_STYLE_NORMAL);
        passTurnButton.setOnMouseEntered(event -> passTurnButton.setStyle(BUTTON_STYLE_HOVER));
        passTurnButton.setOnMouseExited(event -> passTurnButton.setStyle(BUTTON_STYLE_NORMAL));
        passTurnButton.setOnAction(event -> {
            try {
                battle.passTurn();
                updateUI();
//                weaponShopButton.setDisable(true); // Disable the weapon shop button after passing the turn
                if (aiActive) {
                    gameAI.makeBestMove(); // AI makes its move after the turn is passed
                }
            } catch (Exception e) {
                checkGameOver(primaryStage); // Ensure game over check after an error
            }
        });

        // Create "Play using AI" button
        Button playUsingAIButton = new Button("Play using AI");
        playUsingAIButton.setStyle(BUTTON_STYLE_NORMAL);
        playUsingAIButton.setOnMouseEntered(event -> playUsingAIButton.setStyle(BUTTON_STYLE_HOVER));
        playUsingAIButton.setOnMouseExited(event -> playUsingAIButton.setStyle(BUTTON_STYLE_NORMAL));
        playUsingAIButton.setOnAction(event -> {
            aiActive = !aiActive; // Toggle AI active state
            if (aiActive) {
                playUsingAIButton.setText("Stop AI");
//                weaponShopButton.setDisable(true); // Disable the weapon shop button when AI is active
                try {
                    gameAI.makeBestMove(); // AI makes its move immediately
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                playUsingAIButton.setText("Play using AI");
//                weaponShopButton.setDisable(true); // Keep the weapon shop button disabled when AI is stopped
            }
        });

        // Add labels and buttons to the infoBox
        infoBox.getChildren().addAll(scoreLabel, resourcesLabel, turnLabel, currentPhaseLabel, spacer, weaponShopButton, passTurnButton, backButton, playUsingAIButton);

        // Add fade-in animation for labels and buttons
        infoBox.getChildren().forEach(node -> {
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), node);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        // Add slide-in animation for the VBox
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(1500), infoBox);
        slideIn.setFromY(-200);
        slideIn.setToY(0);
        slideIn.play();

        return infoBox;
    }

    private VBox createLaneBox() {
        return laneBox;
    }

    private void updateUI() {
        // Update labels
        scoreLabel.setText("Score: " + battle.getScore());
        resourcesLabel.setText("Resources: " + battle.getResourcesGathered());
        turnLabel.setText("Turn: " + battle.getNumberOfTurns());
        currentPhaseLabel.setText("Current Phase: " + battle.getBattlePhase());

        // Check if game is over
        checkGameOver((Stage) getScene().getWindow());

        // Update lanes
        updateLanes();
    }


    private void updateLanes() {
        laneBox.getChildren().clear();
        List<Lane> lanes = battle.getOriginalLanes();
        titanImageMap.clear(); // Clear the map to update it with current positions
        weaponImageMap.clear(); // Clear the map to update it with current positions

        final int wallPosition = 1500; // Assuming the wall is at the right edge of the lane
        int laneLength = 1200; // Length of the lane Titans need to traverse
        final int titanStep = laneLength / 10; // Number of steps for Titans to reach the wall
        final int initialSpawnPosition = 0; // Fixed spawn position for Titans
        Random random = new Random();

        for (int i = 0; i < lanes.size(); i++) {
            Lane lane = lanes.get(i);
            VBox laneVBox = new VBox();
            laneVBox.setAlignment(Pos.CENTER_LEFT);

            StackPane laneStackPane = new StackPane();
            laneStackPane.setMinSize(wallPosition, 100); // Set the size to match the lane rectangle

            Rectangle laneRectangle = new Rectangle(wallPosition, 100); // Adjust size as needed
            laneRectangle.setFill(Color.TRANSPARENT);
            laneRectangle.setStroke(Color.TRANSPARENT); // Set border color
            laneRectangle.setStrokeWidth(2); // Set border width

            ImageView wallImageView = new ImageView(WALL_IMAGE);
            wallImageView.setFitWidth(100); // Adjust width as needed
            wallImageView.setFitHeight(100); // Adjust height as needed
            StackPane.setAlignment(wallImageView, Pos.CENTER_RIGHT);
            StackPane.setMargin(wallImageView, new Insets(0, 30, 0, 0)); // Adjust margins as needed

            int currentHealth = lane.getLaneWall().getCurrentHealth();
            double progress = (double) currentHealth / lane.getLaneWall().getBaseHealth();
            ProgressBar healthProgressBar = new ProgressBar(progress);
            healthProgressBar.setPrefWidth(100);

            String progressBarStyle;
            if (progress > 0.75) {
                progressBarStyle = "-fx-accent: green;";
            } else if (progress > 0.25) {
                progressBarStyle = "-fx-accent: yellow;";
            } else {
                progressBarStyle = "-fx-accent: red;";
            }
            healthProgressBar.setStyle(progressBarStyle);
            StackPane.setMargin(healthProgressBar, new Insets(150, 0, 0, 1380));

            Label dangerLevelLabel = new Label();
            dangerLevelLabel.setStyle(LABEL_STYLE);
            int dangerLevel = lane.getDangerLevel();
            dangerLevelLabel.setText("Danger Level: " + dangerLevel);
            StackPane.setMargin(dangerLevelLabel, new Insets(50, 0, 0, 1380));

            if (currentHealth <= 0) {
                wallImageView.setImage(BROKEN_WALL_IMAGE);
            }
            laneStackPane.getChildren().addAll(laneRectangle, wallImageView, healthProgressBar, dangerLevelLabel);

            // Display Titans in the lane
            for (Titan titan : lane.getTitans()) {
                HBox titanHBox = new HBox();
                titanHBox.setAlignment(Pos.CENTER_LEFT);

                ImageView titanImageView;
                if (titan instanceof PureTitan) {
                    titanImageView = new ImageView(PURE_TITAN_IMAGE);
                } else if (titan instanceof ArmoredTitan) {
                    titanImageView = new ImageView(ARMORED_TITAN_IMAGE);
                } else if (titan instanceof ColossalTitan || i == 2) {
                    titanImageView = new ImageView(COLOSSAL_TITAN_IMAGE);
                } else {
                    // Default image for other types of titans
                    titanImageView = new ImageView(DEFAULT_TITAN_IMAGE);
                }
                titanImageView.setFitWidth(100);
                titanImageView.setFitHeight(100);

                Label titanHealthLabel = new Label("HP: " + titan.getCurrentHealth());
                titanHealthLabel.setTextFill(Color.WHITE); // Set text color to white for visibility
                titanHealthLabel.setStyle("-fx-font-size: 14px;");

                titanImageMap.put(titan, titanImageView); // Add titan image to map

                titanHBox.getChildren().addAll(titanImageView, titanHealthLabel);

                // Calculate titan position
                double titanPosition = titanPositions.getOrDefault(titan, (double) initialSpawnPosition);
                if (titan instanceof PureTitan || titan instanceof ArmoredTitan || titan instanceof AbnormalTitan) {
                    if (titanPosition < laneLength) {
                        titanPosition += titanStep; // Move the titan by one step
                        titanPositions.put(titan, titanPosition); // Update the titan's position
                    }

                    int randomYOffset = random.nextInt(40) - 10;
                    StackPane.setMargin(titanHBox, new Insets(randomYOffset, 0, 0, titanPosition));
                } else {
                    laneLength = 600;
                    if (titanPosition < laneLength) {
                        titanPosition += titanStep; // Move the titan by one step
                        titanPositions.put(titan, titanPosition); // Update the titan's position
                    }
                    int randomYOffset = random.nextInt(40) - 10;
                    StackPane.setMargin(titanHBox, new Insets(randomYOffset, 0, 0, titanPosition * 2));
                }

                // Check if the titan is in attack range of the wall
                if (titanPosition >= laneLength && titan.hasReachedTarget()) {
                    // Trigger attack logic
                    titan.attack(lane.getLaneWall());
                    // Update wall health bar
                    progress = (double) lane.getLaneWall().getCurrentHealth() / lane.getLaneWall().getBaseHealth();
                    healthProgressBar.setProgress(progress);
                }

                laneStackPane.getChildren().add(titanHBox);
            }

            // Predefined Y-axis positions for each type of weapon in the lane
            int piercingCannonPositionY = 20;
            int sniperCannonPositionY = 60;
            int volleySpreadCannonPositionY = 100;

            // Display Weapons in the lane
            for (Weapon weapon : lane.getWeapons()) {
                HBox weaponHBox = new HBox();
                weaponHBox.setAlignment(Pos.CENTER_RIGHT);

                ImageView weaponImageView;
                int weaponPositionY;

                if (weapon instanceof PiercingCannon) {
                    weaponImageView = new ImageView(PIERCING_CANNON_IMAGE);
                    weaponImageView.setFitWidth(50);
                    weaponImageView.setFitHeight(50);
                    weaponPositionY = piercingCannonPositionY;
                } else if (weapon instanceof SniperCannon) {
                    weaponImageView = new ImageView(SNIPER_CANNON_IMAGE);
                    weaponImageView.setFitWidth(50);
                    weaponImageView.setFitHeight(50);
                    weaponPositionY = sniperCannonPositionY;
                } else if (weapon instanceof VolleySpreadCannon) {
                    weaponImageView = new ImageView(VOLLEY_SPREAD_CANNON_IMAGE);
                    weaponImageView.setFitWidth(50);
                    weaponImageView.setFitHeight(50);
                    weaponPositionY = volleySpreadCannonPositionY;
                } else {
                    weaponImageView = new ImageView(DEFAULT_WEAPON_IMAGE);
                    weaponImageView.setFitWidth(35);
                    weaponImageView.setFitHeight(35);
                    weaponPositionY = volleySpreadCannonPositionY; // Default position for other types of weapons
                }
//
//                weaponImageView.setFitWidth(50);
//                weaponImageView.setFitHeight(50);

                weaponImageMap.put(weapon, weaponImageView); // Add weapon image to map

                weaponHBox.getChildren().addAll(weaponImageView);
                StackPane.setMargin(weaponHBox, new Insets(weaponPositionY, 0, 0, 1100)); // Adjust the X-axis position as needed
                laneStackPane.getChildren().add(weaponHBox);
            }

            laneVBox.getChildren().add(laneStackPane);
            laneBox.getChildren().add(laneVBox);
        }
    }




    private void checkGameOver(Stage primaryStage) {
        if (battle.isGameOver()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("You have lost the game.");
            alert.setContentText("Your score: " + battle.getScore());
            alert.showAndWait();
            returnToStartWindow(primaryStage);
        }
    }

    private void returnToStartWindow(Stage primaryStage) {
        MainView mainView = new MainView(primaryStage);
        performSceneTransition(primaryStage, mainView);
    }

    public void showWeaponShopDialog(Stage primaryStage, Battle battle) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.initOwner(primaryStage);
        dialog.setTitle("Weapon Shop");

        // Create a new WeaponShopView and set it as the content of the dialog
        WeaponShopView weaponShopView = new WeaponShopView(primaryStage, battle);
        dialog.getDialogPane().setContent(weaponShopView);

        // Show the dialog
        dialog.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.initModality(Modality.NONE); // Non-blocking alert
        alert.show();
    }

    private void performSceneTransition(Stage stage, Pane newView) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), this);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            stage.getScene().setRoot(newView);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newView);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }
}
