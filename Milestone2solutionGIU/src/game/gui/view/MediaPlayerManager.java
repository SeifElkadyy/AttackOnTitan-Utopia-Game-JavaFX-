package game.gui.view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MediaPlayerManager {
    private static MediaPlayerManager instance;
    private MediaPlayer mediaPlayer;
    private static final Logger LOGGER = Logger.getLogger(MediaPlayerManager.class.getName());

    private MediaPlayerManager() {
        File mediaFile = new File("C:\\Users\\User\\Downloads\\Game Song.mp3");
        if (!mediaFile.exists()) {
            LOGGER.log(Level.SEVERE, "Media file not found: " + mediaFile.getAbsolutePath());
            throw new IllegalStateException("Media file not found");
        }

        String mediaFilePath = mediaFile.toURI().toString();
        Media media = new Media(mediaFilePath);
        mediaPlayer = new MediaPlayer(media);
    }

    public static MediaPlayerManager getInstance() {
        if (instance == null) {
            instance = new MediaPlayerManager();
        }
        return instance;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
