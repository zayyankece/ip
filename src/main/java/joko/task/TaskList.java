package joko.task;

import java.util.ArrayList;

import joko.storage.Storage;


/**
 * Represents a list of tasks and provides operations to manage them.
 * <p>
 * This class maintains an {@link ArrayList} of {@link Task} objects and
 * ensures that changes to the list are persisted using {@link Storage}.
 * </p>
 */
public class TaskList {
    /** The list of tasks being managed. */
    private final ArrayList<Task> tasks;

    /** The storage used to persist tasks. */
    private final Storage storage;

    /**
     * Constructs a new {@code TaskList} with the given tasks and storage.
     *
     * @param tasks   the initial list of tasks
     * @param storage the storage instance to save tasks to
     */
    public TaskList(ArrayList<Task> tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
    }

    /**
     * Adds a new task to the list and saves the updated list to storage.
     *
     * @param task the task to add
     * @return the task that was added
     */
    public Task addTask(Task task) {
        tasks.add(task);
        storage.saveTasks(tasks);
        return task;
    }

    /**
     * Deletes the task at the specified index and saves the updated list to storage.
     *
     * @param index the index of the task to delete
     * @return the task that was removed
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Task deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index.");
        }
        Task removed = tasks.remove(index);
        storage.saveTasks(tasks);
        return removed;
    }

    /**
     * Marks or unmarks the task at the specified index and saves the updated list to storage.
     *
     * @param index  the index of the task to mark/unmark
     * @param isDone {@code true} to mark as done, {@code false} to mark as not done
     * @return the task that was updated
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Task markTask(int index, boolean isDone) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index.");
        }
        Task task = tasks.get(index);
        task.setDone(isDone);
        storage.saveTasks(tasks);
        return task;
    }

    /**
     * Returns the list of tasks.
     *
     * @return the {@link ArrayList} of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }
}
