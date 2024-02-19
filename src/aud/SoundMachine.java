package aud;

import stdlib.StdAudio;

import java.util.Base64;

public final class SoundMachine {

    private static class SoundThread extends Thread {

        private final double[] samples;

        public SoundThread(double[] samples) {
            this.samples = samples;
        }

        @Override
        public void run() {
            for (var sample : samples) {
                if(isInterrupted()) {
                    break;
                }
                StdAudio.play(sample);
            }
        }
    }

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

    private Thread thread;
    private int priority;


    private SoundMachine() {
        priority = -1;
        thread = null;
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

        synchronized (mutex) {
            if (this.priority >= priority) {
                return;
            }

            if (thread != null) {
                thread.interrupt();
            }

            this.priority = priority;

            thread = new Thread(() -> {
                for (var sample : samples) {
                    if (thread.isInterrupted()) {
                        System.out.println("interrupted");
                        break;
                    }
                    StdAudio.play(sample);
                }

                synchronized (mutex) {
                    thread = null;
                    this.priority = -1;
                }
            });

            thread.start();
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
        System.out.println("Eat gum");
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
