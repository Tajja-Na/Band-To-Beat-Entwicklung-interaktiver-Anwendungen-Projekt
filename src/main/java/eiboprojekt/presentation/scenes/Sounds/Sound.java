package eiboprojekt.presentation.scenes.Sounds;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import eiboprojekt.presentation.scenes.Navigation;
import javafx.beans.property.SimpleIntegerProperty;

public class Sound {

    private SimpleIntegerProperty currentPosition;
    private SimpleIntegerProperty property;

    Track tracks[] = new Track[15];
    public SimpleAudioPlayer audioPlayer;
    public PlayThread playThread;
    public SimpleMinim minim;
    public boolean isPlaying = false;

    public class Track {
        String filepath;
        int length = 30; // 103 sekunden jeder song

        public Track(String filepath) {
            this.filepath = filepath;
        }
    }

    public class PlayThread extends Thread {
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    property = new SimpleIntegerProperty(audioPlayer.position());
                    System.out.println("Property: " + property);
                    setcurrentPosition(property);

                    if (!getAudioPlayer().isPlaying()) {
                        isPlaying = true;
                        audioPlayer.play(0);
                    }

                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread wurde unterbrochen.");
            }
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
        // Falls ein alter Thread läuft, unterbreche ihn
        if (playThread != null && playThread.isAlive()) {
            playThread.interrupt(); // Setzt das Interrupt-Flag
            System.out.println("Alter Thread wurde unterbrochen.");
        }
    
        // Starte einen neuen Thread
        playThread = new PlayThread();
        playThread.setDaemon(true); // Hintergrund-Thread
        playThread.start();
        System.out.println("Neuer Thread wurde gestartet.");
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