package model;

public record ScheduledTask(String id, long moment, Runnable task) {

    public static final String SCARE_OFF_GHOSTS_TASK = "scare-off-ghosts";
    public static final String EVOLVE_TASK_PREFIX = "evolve-";
    public static final int TASK_DEFAULT_DURATION = 10000;

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
