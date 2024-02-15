package engine;

public final class GameEngine implements Runnable {

    private final Thread thread;

    public GameEngine() {
        thread = new Thread(this);

    }

    @Override
    public void run() {

    }

    public void start() {
        thread.start();
    }
}
