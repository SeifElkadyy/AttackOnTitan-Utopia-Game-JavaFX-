package game.gui.view;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TransitionUtil {

    public static void applySlideTransition(Node node, double fromX, double toX, double fromY, double toY, int durationMillis) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(durationMillis), node);
        transition.setFromX(fromX);
        transition.setToX(toX);
        transition.setFromY(fromY);
        transition.setToY(toY);
        transition.play();
    }
}
