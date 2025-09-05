package joko.task;

/**
 * Represents a basic task in the Joko task manager application.
 * <p>
 * A task has a description and a completion status. This class serves
 * as the superclass for specific types of tasks such as {@link Deadline} and {@link Event}.
 * </p>
 */
public class Task {
    /** The description of the task. */
    protected final String desc;

    /** Indicates whether the task has been completed. */
    protected boolean isDone;

    /**
     * Constructs a new {@code Task} with the given description.
     * The task is initially marked as not done.
     *
     * @param desc the description of the task
     */
    public Task(String desc) {
        this.desc = desc;
        this.isDone = false;
    }

    /**
     * Returns whether the task is completed.
     *
     * @return {@code true} if the task is done, {@code false} otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Returns a string representing the completion status of the task.
     * <p>
     * "[X] " indicates done, "[ ] " indicates not done.
     * </p>
     *
     * @return the status string of the task
     */
    public String getStatus() {
        return (isDone ? "[X] " : "[ ] ");
    }

    /**
     * Sets the completion status of the task.
     *
     * @param done {@code true} to mark the task as done, {@code false} to mark it as not done
     */
    public void setDone(boolean done) {
        this.isDone = done;
    }

    /**
     * Returns a string representation of the task, combining its status and description.
     *
     * @return a formatted string representing the task
     */
    @Override
    public String toString() {
        return getStatus() + desc;
    }
}

