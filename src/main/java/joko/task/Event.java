package joko.task;

/**
 * Represents an event task that occurs within a specific time period.
 * <p>
 * Extends the {@link Task} class and adds {@code from} and {@code to} fields
 * to store the start and end times of the event.
 * </p>
 */
public class Event extends Task {
    /** The start time of the event. */
    protected String from;

    /** The end time of the event. */
    protected String to;

    /**
     * Constructs a new {@code Event} task with the given description, start time, and end time.
     *
     * @param desc the description of the event
     * @param from the start time of the event
     * @param to   the end time of the event
     */
    public Event(String desc, String from, String to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start time of the event.
     *
     * @return the start time as a {@link String}
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the end time of the event.
     *
     * @return the end time as a {@link String}
     */
    public String getTo() {
        return to;
    }

    /**
     * Checks if this Event is equal to another object.
     * Two Events are equal if they have the same description, start time, and end time.
     *
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Event)) {
            return false;
        }
        Event other = (Event) obj;
        return desc.equals(other.desc) && from.equals(other.from) && to.equals(other.to);
    }
    /**
     * Returns a string representation of the event task, including its type,
     * completion status, description, and start/end times.
     *
     * @return a formatted string representing the event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + "(from: " + from
                + " to: " + to + ")";
    }
}
