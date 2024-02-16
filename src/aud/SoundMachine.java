package aud;

import stdlib.StdAudio;

import java.util.Base64;

public final class SoundMachine {

    private static final SoundMachine INSTANCE = new SoundMachine();

    private final double[] start;
    private final double[] death;
    private final double[] eatGum;
    private final double[] eatGhost;
    private final double[] eatSuperGum;
    private final double[] eatTrident;
    private final double[] eatStar;
    private final double[] eatLightning;
    private final double[] eatFruit;

    private final Object mutex;

    private SoundMachine() {
        mutex = new Object();
        start = loadSound(Sounds.START);
        death = loadSound(Sounds.DEATH);
        eatGum = loadSound(Sounds.EAT_GUM);
        eatGhost = loadSound(Sounds.EAT_GHOST);
        eatSuperGum = loadSound(Sounds.EAT_SUPER_GUM);
        eatTrident = loadSound(Sounds.DEVIL);
        eatStar = loadSound(Sounds.EAT_STAR);
        eatLightning = loadSound(Sounds.EAT_LIGHTNING);
        eatFruit = loadSound(Sounds.EAT_FRUIT);
    }

    private double[] loadSound(String sound) {

        var decoder = Base64.getDecoder();

        var bytes = decoder.decode(sound);

        return StdAudio.read(bytes);
    }

    private void play(double[] samples, boolean async) {
        var thread = new Thread(() -> {
            for (var sample : samples) {
                synchronized (mutex) {
                    StdAudio.play(sample);
                }
            }
        });
        thread.start();

        if (!async) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void playStart() {
        play(start, false);
    }

    public void playDeath() {
        play(death, true);
    }

    public void playEatGum() {
        play(eatGum, true);
    }

    public void playEatGhost() {
        play(eatGhost, true);
    }

    public void playEatSuperGum() {
        play(eatSuperGum, true);
    }

    public void playEatTrident() {
        play(eatTrident, true);
    }

    public void playEatFruit() {
        play(eatFruit, true);
    }

    public void playEatLightning() {
        play(eatLightning, true);
    }

    public void playEatStar() {
        play(eatStar, true);
    }


    public static SoundMachine getInstance() {
        return INSTANCE;
    }
}
