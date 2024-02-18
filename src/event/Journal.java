package event;

import java.util.ArrayList;
import java.util.List;

public class Journal {
    private static final Journal INSTANCE = new Journal();

    private final List<Event> events;

    private Journal() {
        events = new ArrayList<>();
    }

    public Journal getInstance() {
        return INSTANCE;
    }

    public synchronized void addEvent(Event event) {
        events.add(event);
        event.notifyAll();
    }

    public synchronized Event readEvent(int index) {

        if (events.size() <= index) {
            try {
                events.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return (events.size() > index) ? events.get(index) : null;
    }
}
