package joko.task;

/**
 * Represents a simple to-do task without a specific date or time.
 * <p>
 * Extends the {@link Task} class. This task type only contains a description
 * and a completion status.
 * </p>
 */
public class ToDo extends Task {

    /**
     * Constructs a new {@code ToDo} task with the given description.
     *
     * @param desc the description of the task
     */
    public ToDo(String desc) {
        super(desc);
    }

    /**
     * Checks if this Todo is equal to another object.
     * Two Todo are equal if they have the same description.
     *
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ToDo)) {
            return false;
        }
        ToDo other = (ToDo) obj;
        return desc.equals(other.desc);
    }

    /**
     * Returns a string representation of the to-do task, including its type
     * and completion status.
     *
     * @return a formatted string representing the to-do task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
