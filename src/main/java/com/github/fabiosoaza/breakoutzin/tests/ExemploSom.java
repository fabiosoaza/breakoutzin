package com.github.fabiosoaza.breakoutzin.tests;

import com.github.fabiosoaza.breakoutzin.SoundPlayer;

import javax.sound.sampled.*;
import java.io.IOException;

public class ExemploSom {


    private static SoundPlayer jukeBox;


    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        try {
            jukeBox = new SoundPlayer();
            for (SoundPlayer.Sfx sound : SoundPlayer.Sfx.values()) {
                System.out.println(sound.name());
                jukeBox.playSound(sound);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}
