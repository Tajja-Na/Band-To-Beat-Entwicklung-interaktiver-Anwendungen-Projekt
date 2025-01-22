package eiboprojekt.presentation.scenes.Sounds;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Navigation;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SoundController {
    Sound sound;
    PlayThread playThread;

    public class PlayThread extends Thread{
        public void run(){
            try {
                while (!Thread.currentThread().isInterrupted()) { 
                    if (!sound.getAudioPlayer().isPlaying()) {
                        sound.play();
                    }
                    Thread.sleep(100); 
                }
            } catch (InterruptedException e) {
                sound.stop();
                Thread.currentThread().interrupt(); 
                System.out.println("Thread wurde unterbrochen.");
            }
        }
    }

    public SoundController(Sound sound){
        this.sound = sound;

        initialize();
    }
        
    private void initialize() {
        Navigation.getCurrentView().addListener( 
           (observable,  oldValue, newValue) -> {

            switch(newValue){
                case "GAMEPANEL":
                    sound.loadTrack(0);
                    sound.loop();
                    System.out.println("ich hab den titel song geladen und spiel es von hier ab");
                    //startSEThread();
                    break;

                case "GAMELevel1":
                    sound.loadTrack(7);
                    break;

                case "GAMELevel2":
                    sound.loadTrack(8);
                    break;

                case "GAMELevel3":
                    sound.loadTrack(9);
                    break;
            }
            sound.setVolume(0.1);
            play();
        });
    }

    public void play(){
        if(playThread != null && playThread.isAlive()){
            playThread.interrupt();
            System.out.println("hi bin in interrupted");
        }
        playThread = new PlayThread();
        playThread.setDaemon(true);
        playThread.start();
    }

    /*public void startSEThread(){
        playThread = new PlayThread();
        playThread.setDaemon(true);
        sound.loadTrack(1);
        playThread.start();
    }*/
}