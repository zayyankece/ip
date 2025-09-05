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
        Task removedTask = tasks.remove(index);
        storage.saveTasks(tasks);
        return removedTask;
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }
}
