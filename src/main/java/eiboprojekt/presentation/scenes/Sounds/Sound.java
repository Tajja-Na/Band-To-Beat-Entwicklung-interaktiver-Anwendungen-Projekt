package eiboprojekt.presentation.scenes.Sounds;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import eiboprojekt.presentation.scenes.Navigation;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;

public class Sound {

    private SimpleIntegerProperty currentPosition = new SimpleIntegerProperty();
    private SimpleIntegerProperty property;

    Track tracks[] = new Track[15];
    public SimpleAudioPlayer audioPlayer;
    public SimpleMinim minim;
    public boolean isPlaying = false;

    public class Track {
        String filepath;

        public Track(String filepath) {
            this.filepath = filepath;
        }
    }

    public Sound() {
        this.minim = new SimpleMinim();

        try {
            tracks[0] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/undertale_music2.mp3"); // Titelsong
            tracks[1] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/blocked.mp3");
            tracks[2] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/coin.mp3");
            tracks[3] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/gameover.mp3");
            tracks[4] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/levelup.mp3");
            tracks[5] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/powerup.mp3");
            tracks[6] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/speak.mp3");
            tracks[7] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/w2e(musik).mp3"); // level1
            tracks[8] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/w2e(musik,bass).mp3"); // level2
            tracks[9] = new Track(
                    "src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/w2e(musik,bass,drums).mp3"); // level3
            tracks[10] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/w2e(normal).mp3"); // ende?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTrack(int i) {
        pause();
        this.audioPlayer = minim.loadMP3File(tracks[i].filepath);
    }

    public void play() {
        if (audioPlayer != null) {

            Thread playThread = new Thread(() -> {
                audioPlayer.play(0);
                while(audioPlayer.isPlaying()){
                    Platform.runLater(() -> getcurrentPosition().set(audioPlayer.position()/1000));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            playThread.start();
        }
    }
    

    public void loop() {
        audioPlayer.loop();
        System.out.println("Bin in loop");
    }

    public void pause() {
        if (audioPlayer != null) {
            isPlaying = false;
            audioPlayer.pause();
        }
    }

    public void setVolume(double value) {
        float volume = 20 * (float) Math.log10(value); // Umrechnung in Dezibel
        audioPlayer.setGain(volume);
    }

    public SimpleAudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public SimpleIntegerProperty getcurrentPosition() {
        return this.currentPosition;
    }

    public void setcurrentPosition(SimpleIntegerProperty currentPosition) {
        this.currentPosition = currentPosition;
    }
}