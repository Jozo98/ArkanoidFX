package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class Sound {

    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/Arkanoid SFX (1).wav");
        soundURL[1] = getClass().getResource("/sound/Arkanoid SFX (2).wav");
        soundURL[2] = getClass().getResource("/sound/arkanoid_-_doh_it_again_-_hope_or_deception.wav");
        soundURL[3] = getClass().getResource("/sound/Arkanoid SFX (3).wav");
        soundURL[4] = getClass().getResource("/sound/Arkanoid SFX (4).wav");
        soundURL[5] = getClass().getResource("/sound/Arkanoid SFX (5).wav");
        soundURL[6] = getClass().getResource("/sound/Arkanoid SFX (6).wav");
        soundURL[7] = getClass().getResource("/sound/Arkanoid SFX (7).wav");
        soundURL[8] = getClass().getResource("/sound/Arkanoid SFX (8).wav");
        soundURL[9] = getClass().getResource("/sound/Arkanoid SFX (9).wav");
        soundURL[10] = getClass().getResource("/sound/Arkanoid SFX (10).wav");
        soundURL[11] = getClass().getResource("/sound/Arkanoid SFX (11).wav");
        soundURL[12] = getClass().getResource("/sound/Arkanoid SFX (12).wav");
    }

    public void setFile(int i) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch(Exception e) {

        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}