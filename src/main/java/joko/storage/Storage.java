package joko.storage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import joko.task.Task;
import joko.task.Deadline;
import joko.task.ToDo;
import joko.task.Event;

public class Storage {
    private final String filename;

    public Storage(String filename) {
        this.filename = filename;
    }

    public void saveTasks(ArrayList<Task> tasks) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            for (Task t : tasks) {
                if (t instanceof ToDo) {
                    writer.println("T | " + (t.isDone() ? "1" : "0") + " | " + t.getDesc());
                } else if (t instanceof Deadline) {
                    Deadline d = (Deadline) t;
                    DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                    writer.println("D | " + (d.isDone() ? "1" : "0") + " | " + d.getDesc() + " | " + d.getBy().format(fileFormat));
                } else if (t instanceof Event) {
                    Event e = (Event) t;
                    writer.println("E | " + (e.isDone() ? "1" : "0") + " | " +
                            e.getDesc() + " | " + e.getFrom() + " | " + e.getTo());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

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

                Task t = null;
                if (type.equals("T")) {
                    t = new ToDo(desc);
                } else if (type.equals("D")) {
                    DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                    LocalDateTime by = LocalDateTime.parse(parts[3], fileFormat);
                    t = new Deadline(desc, by);
                } else if (type.equals("E")) {
                    t = new Event(desc, parts[3], parts[4]);
                }
                if (t != null) {
                    t.setDone(isDone);
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }
}
