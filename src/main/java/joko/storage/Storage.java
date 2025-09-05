package joko.storage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import joko.task.Deadline;
import joko.task.Event;
import joko.task.Task;
import joko.task.ToDo;

/**
 * Handles saving and loading tasks to and from a file.
 * <p>
 * This class provides methods to persist the {@link Task} list to a file
 * and to load tasks back into memory. It supports {@link ToDo}, {@link Deadline},
 * and {@link Event} task types.
 * </p>
 */
public class Storage {
    /** The filename where tasks are saved. */
    private final String filename;

    /**
     * Constructs a new {@code Storage} instance with the given filename.
     *
     * @param filename the file path to save and load tasks
     */
    public Storage(String filename) {
        this.filename = filename;
    }

    /**
     * Saves the given list of tasks to the file.
     * <p>
     * Each task is written in a specific format depending on its type:
     * <ul>
     *     <li>ToDo: T | doneFlag | description</li>
     *     <li>Deadline: D | doneFlag | description | dd/MM/yyyy HHmm</li>
     *     <li>Event: E | doneFlag | description | from | to</li>
     * </ul>
     * </p>
     *
     * @param tasks the list of tasks to save
     */
    public void saveTasks(ArrayList<Task> tasks) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            for (Task t : tasks) {
                if (t instanceof ToDo) {
                    writer.println("T | " + (t.isDone() ? "1" : "0") + " | " + t.getDesc());
                } else if (t instanceof Deadline) {
                    Deadline d = (Deadline) t;
                    DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                    writer.println("D | " + (d.isDone() ? "1" : "0") + " | " + d.getDesc() + " | "
                            + d.getBy().format(fileFormat));
                } else if (t instanceof Event) {
                    Event e = (Event) t;
                    writer.println("E | " + (e.isDone() ? "1" : "0") + " | "
                            + e.getDesc() + " | " + e.getFrom() + " | " + e.getTo());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the file.
     * <p>
     * The file is expected to contain tasks in the format written by {@link #saveTasks(ArrayList)}.
     * If the file does not exist, an empty list is returned.
     * </p>
     *
     * @return an {@link ArrayList} of tasks loaded from the file
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            return tasks; // return empty if no file yet
        }

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String desc = parts[2];

                Task task = null;
                if (type.equals("T")) {
                    task = new ToDo(desc);
                } else if (type.equals("D")) {
                    DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                    LocalDateTime by = LocalDateTime.parse(parts[3], fileFormat);
                    task = new Deadline(desc, by);
                } else if (type.equals("E")) {
                    task = new Event(desc, parts[3], parts[4]);
                }

                if (task != null) {
                    task.setDone(isDone);
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }
}
