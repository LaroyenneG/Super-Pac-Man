package aud;

import stdlib.StdAudio;

import java.util.concurrent.atomic.AtomicBoolean;

public class SoundThread implements Runnable {
    private final int priority;
    private final double[] samples;
    private final AtomicBoolean running;

    private final Thread thread;

    public SoundThread(int priority, double[] samples) {
        this.priority = priority;
        this.samples = samples;
        running = new AtomicBoolean(true);
        thread = new Thread(this);
    }

    @Override
    public void run() {
        for (var sample : samples) {
            if (isFinish()) {
                break;
            }
            StdAudio.play(sample);
        }

        running.set(false);
    }

    public void play() {
        thread.start();
    }

    public void cancel() {
        running.set(false);
        try {
            thread.join();
        } catch (InterruptedException ignored) {
        }
    }

    public int getPriority() {
        return priority;
    }

    public boolean isFinish() {
        return !running.get();
    }
}