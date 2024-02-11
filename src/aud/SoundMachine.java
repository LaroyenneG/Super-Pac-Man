package aud;

public final class SoundMachine {

    private static final SoundMachine INSTANCE = new SoundMachine();

    private SoundMachine() {

    }

    public static SoundMachine getInstance() {
        return INSTANCE;
    }
}
