package game.model;

import java.util.ArrayList;
import java.util.List;

public class CreditsModel {
    private List<String> creditsList;

    public CreditsModel() {
        creditsList = new ArrayList<>();
        initializeCredits();
    }

    private void initializeCredits() {
        // Add credits data
        creditsList.add("Seif Edlein Waleed");
        creditsList.add("Seif Ashraf");
        creditsList.add("Hassan Roshdy");
    }

    public List<String> getCreditsList() {
        return creditsList;
    }
}
