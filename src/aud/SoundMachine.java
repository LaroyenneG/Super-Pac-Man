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

    private SoundMachine() {
        start = loadSound(Sounds.START);
        death = loadSound(Sounds.DEATH);
        eatGum = loadSound(Sounds.EAT_GUM);
        eatGhost = loadSound(Sounds.EAT_GHOST);
        eatSuperGum = loadSound(Sounds.EAT_SUPER_GUM);
    }

    private double[] loadSound(String sound) {

        var decoder = Base64.getDecoder();

        var bytes = decoder.decode(sound);

        return StdAudio.read(bytes);
    }

    private void play(double[] samples, boolean async) {

        var thread = new Thread(() -> StdAudio.play(samples));
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


    public static SoundMachine getInstance() {
        return INSTANCE;
    }
}
