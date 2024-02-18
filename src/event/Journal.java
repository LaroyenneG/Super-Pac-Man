package event;

import java.util.ArrayDeque;
import java.util.Queue;

public class Journal {
    private static final Journal INSTANCE = new Journal();

    private final Queue<Event> eventQueue;

    private Journal() {
        eventQueue = new ArrayDeque<>();
    }

    public Journal getInstance() {
        return INSTANCE;
    }
}
