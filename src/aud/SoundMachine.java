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
    private final double[] bump;

    private final Object mutex;

    private SoundThread soundThread;


    private SoundMachine() {
        mutex = new Object();
        soundThread = null;
        start = loadSound(Sounds.START);
        death = loadSound(Sounds.DEATH);
        eatGum = loadSound(Sounds.EAT_GUM);
        eatGhost = loadSound(Sounds.EAT_GHOST);
        eatSuperGum = loadSound(Sounds.EAT_SUPER_GUM);
        eatTrident = loadSound(Sounds.DEVIL);
        eatStar = loadSound(Sounds.EAT_STAR);
        eatLightning = loadSound(Sounds.EAT_LIGHTNING);
        eatFruit = loadSound(Sounds.EAT_FRUIT);
        bump = loadSound(Sounds.BUMP);
    }

    private double[] loadSound(String sound) {

        var decoder = Base64.getDecoder();

        var bytes = decoder.decode(sound);

        return StdAudio.read(bytes);
    }

    private void play(int priority, double[] samples, boolean async) {

        if (!async) {
            StdAudio.play(samples);
            return;
        }

        var nextSound = new SoundThread(priority, samples);

        synchronized (mutex) {
            if (soundThread == null) {
                soundThread = nextSound;
                soundThread.play();
            } else {
                var currentPriority = soundThread.getPriority();
                var newPriority = nextSound.getPriority();
                if (currentPriority <= newPriority || soundThread.isFinish()) {
                    soundThread.cancel();
                    soundThread = nextSound;
                    soundThread.play();
                }
            }
        }
    }

    public void playStart() {
        play(0, start, false);
    }

    public void playDeath() {
        play(10, death, true);
    }

    public void playEatGum() {
        play(1, eatGum, true);
    }

    public void playEatGhost() {
        play(5, eatGhost, true);
    }

    public void playEatSuperGum() {
        play(2, eatSuperGum, true);
    }

    public void playEatTrident() {
        play(6, eatTrident, true);
    }

    public void playEatFruit() {
        play(3, eatFruit, true);
    }

    public void playEatLightning() {
        play(4, eatLightning, true);
    }

    public void playEatStar() {
        play(5, eatStar, true);
    }

    public void playBump() {
        play(1, bump, true);
    }


    public static SoundMachine getInstance() {
        return INSTANCE;
    }
}
