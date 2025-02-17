// Sounds have some delay!!!!

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {
    private static AudioManager audioManager;
    private MediaPlayer mediaPlayer;

    private AudioManager(){

    }

    public static AudioManager getInstance(){
        if (audioManager == null) {
            audioManager = new AudioManager();
        }
        return audioManager;
    }

    public MediaPlayer playMusic(String pathFile, boolean loop, boolean cut){
        if (mediaPlayer != null && cut) {mediaPlayer.stop();}
        String musicFile = getClass().getResource(pathFile).toString();
        Media media = new Media(musicFile);
        mediaPlayer = new MediaPlayer(media);
        if (loop) mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.025);
        mediaPlayer.play();
        return mediaPlayer;
    }

    public void stopMusic(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void setVolume(double value){
        mediaPlayer.setVolume(value);
    }
}
