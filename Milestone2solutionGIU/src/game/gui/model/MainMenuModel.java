package game.gui.model;

public class MainMenuModel {
    private String selectedGameMode;

    public MainMenuModel() {
        this.selectedGameMode = "Easy"; // Default game mode
    }

    public String getSelectedGameMode() {
        return selectedGameMode;
    }

    public void setSelectedGameMode(String selectedGameMode) {
        this.selectedGameMode = selectedGameMode;
    }
}
