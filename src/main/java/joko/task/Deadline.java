package joko.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that has a specific deadline.
 * <p>
 * Extends the {@link Task} class and adds a {@link LocalDateTime} field
 * to store the date and time by which the task should be completed.
 * </p>
 */
public class Deadline extends Task {
    /** The date and time by which the task must be completed. */
    protected LocalDateTime by;

    /**
     * Constructs a new {@code Deadline} task with the given description and deadline.
     *
     * @param desc the description of the task
     * @param by   the deadline date and time for the task
     */
    public Deadline(String desc, LocalDateTime by) {
        super(desc);
        this.by = by;
    }

    /**
     * Returns the deadline of the task.
     *
     * @return the {@link LocalDateTime} representing the deadline
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of the deadline task, including its type,
     * completion status, description, and formatted deadline.
     *
     * @return a formatted string representing the deadline task
     */
    @Override
    public String toString() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return "[D]" + super.toString()
                + "(by: " + by.format(outputFormat) + ") ";
    }
}
