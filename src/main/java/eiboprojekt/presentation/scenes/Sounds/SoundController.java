package eiboprojekt.presentation.scenes.Sounds;

import eiboprojekt.presentation.scenes.Navigation;

public class SoundController {
    Sound sound;

    public SoundController(Sound sound) {
        this.sound = sound;

        initialize();
    }

    private void initialize() {
        Navigation.getCurrentView().addListener(
                (observable, oldValue, newValue) -> {

                    switch (newValue) {
                        case "GAMEPANEL":
                            sound.loadTrack(0);
                            sound.setVolume(0.1);
                            sound.play();
                            sound.loop();
                            break;

                        case "GAMELevel1":
                            sound.loadTrack(1);
                            sound.setVolume(0.1);
                            sound.play();
                            break;

                        case "GAMELevel2":
                            sound.loadTrack(2);
                            sound.setVolume(0.1);
                            sound.play();
                            break;

                        case "GAMELevel3":
                            sound.loadTrack(3);
                            sound.setVolume(0.1);
                            sound.play();
                            break;

                        case "ENDEVIEW":
                            sound.loadTrack(4);
                            sound.setVolume(0.1);
                            sound.play();
                            break;
                    }
                });
    }
}