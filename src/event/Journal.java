package event;

public class Journal {
    private static final Journal INSTANCE = new Journal();


    private Journal() {
    }

    public Journal getInstance() {
        return INSTANCE;
    }
}
