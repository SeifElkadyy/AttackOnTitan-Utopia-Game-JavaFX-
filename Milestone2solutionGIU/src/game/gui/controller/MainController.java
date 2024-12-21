package game.gui.controller;

import game.gui.view.MainView;
import javafx.stage.Stage;

public class MainController {
    private MainView view;
    private Stage primaryStage;

//    public MainController(MainView view, Stage primaryStage) {
//        this.view = view;
//        this.primaryStage = primaryStage;
//        view.setController(this);
//    }

    public void handleStartGame() {
//        // Instantiate the view for the game screen
//        // For example, let's assume you have a GameView class
//        GameView gameView = new GameView(primaryStage);
//
//        // Instantiate the controller for the game screen
//        // For example, let's assume you have a GameController class
//        GameController gameController = new GameController(gameView, primaryStage);
//
//        // Set the game view as the root of the scene
//        primaryStage.getScene().setRoot(gameView);
    }

    public void handleGameRules() {
        // Handle game rules action if needed
    }

    public void handleCredits() {
        // Handle credits action if needed
    }
}
//    public void showWeaponShopDialog(Stage primaryStage, Battle battle) {
//        Dialog<Void> dialog = new Dialog<>();
//        dialog.initOwner(primaryStage);
//        dialog.setTitle("Weapon Shop");
//
//        // Create a new WeaponShopView and set it as the content of the dialog
//        WeaponShopView weaponShopView = new WeaponShopView(primaryStage, battle);
//        dialog.getDialogPane().setContent(weaponShopView);
//
//        // Add OK button to close the dialog
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
//
//        // Show the dialog
//        dialog.showAndWait();
//    }