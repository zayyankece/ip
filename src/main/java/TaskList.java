import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Joko.Task> tasks;
    private final Storage storage;

    public TaskList(ArrayList<Joko.Task> tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
    }

    public Joko.Task addTask(Joko.Task task) {
        tasks.add(task);
        storage.saveTasks(tasks);
        return task;
    }

    public Joko.Task deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index.");
        }
        Joko.Task removed = tasks.remove(index);
        storage.saveTasks(tasks);
        return removed;
    }

    public Joko.Task markTask(int index, boolean isDone) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index.");
        }
        Joko.Task task = tasks.get(index);
        task.isDone = isDone;
        storage.saveTasks(tasks);
        return task;
    }

    public ArrayList<Joko.Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }
}
