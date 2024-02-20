package model;

public record ScheduledTask(String id, long moment, Runnable task) {

    @Override
    public boolean equals(Object that) {

        if (this == that) return true;

        if (!(that instanceof ScheduledTask scheduledTask)) return false;

        return id.equals(scheduledTask.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
