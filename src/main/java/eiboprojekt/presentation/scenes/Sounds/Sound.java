package eiboprojekt.presentation.scenes.Sounds;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

public class Sound {

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

        this.minim = new SimpleMinim(true);

        if (minim != null) {
            System.out.println("Minim ist nicht null");
        }

        try {
            tracks[0] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/undertale_music2.mp3"); //Titelsong
            tracks[1] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/blocked.mp3"); 
            tracks[2] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/coin.mp3"); 
            tracks[3] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/gameover.mp3"); 
            tracks[4] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/levelup.mp3"); 
            tracks[5] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/powerup.mp3"); 
            tracks[6] = new Track("src/main/java/eiboprojekt/presentation/scenes/Sounds/Media/speak.mp3"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }

    public void loadTrack(int i) {

            this.audioPlayer = minim.loadMP3File(tracks[i].filepath);
    }

    public void play() {
        audioPlayer.play();
        isPlaying = true;
    }

    public void loop() {
        audioPlayer.loop();
        System.out.println("Bin in loop");
    }

    public void stop() {
        if (audioPlayer != null) {
            audioPlayer.pause();
            isPlaying = false;
        }
        
    }

    public void setVolume(double value) {
        float volume = 20 * (float) Math.log10(value); // Umrechnung in Dezibel
        audioPlayer.setGain(volume);
    }
    
    
}
