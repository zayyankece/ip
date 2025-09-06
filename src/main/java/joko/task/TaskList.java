package joko.task;

import joko.storage.Storage;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;
    private final Storage storage;

    public TaskList(ArrayList<Task> tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
    }

    public Task addTask(Task task) {
        tasks.add(task);
        storage.saveTasks(tasks);
        return task;
    }

    public Task deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index.");
        }
        Task removed = tasks.remove(index);
        storage.saveTasks(tasks);
        return removed;
    }

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
     * Returns a list of tasks whose descriptions contain the given keyword.
     *
     * <p>The search is case-insensitive.</p>
     *
     * @param keyword the keyword to search for
     * @return an ArrayList of matching tasks
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> results = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDesc().contains(keyword)) {
                results.add(t);
            }
        }
        return results;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }
}
