package eiboprojekt.presentation.scenes.Sounds;

import eiboprojekt.App;
import eiboprojekt.presentation.scenes.Navigation;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SoundController {
    Sound sound;

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
            sound.play();
        });
    }
}