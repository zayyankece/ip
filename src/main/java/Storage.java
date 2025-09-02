import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filename;

    public Storage(String filename) {
        this.filename = filename;
    }

    public void saveTasks(ArrayList<Joko.Task> tasks) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            for (Joko.Task t : tasks) {
                if (t instanceof Joko.ToDo) {
                    writer.println("T | " + (t.isDone ? "1" : "0") + " | " + t.desc);
                } else if (t instanceof Joko.Deadline) {
                    Joko.Deadline d = (Joko.Deadline) t;
                    DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                    writer.println("D | " + (d.isDone ? "1" : "0") + " | " + d.desc + " | " + d.by.format(fileFormat));
                } else if (t instanceof Joko.Event) {
                    Joko.Event e = (Joko.Event) t;
                    writer.println("E | " + (e.isDone ? "1" : "0") + " | " + e.desc + " | " + e.from + " | " + e.to);
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public ArrayList<Joko.Task> loadTasks() {
        ArrayList<Joko.Task> tasks = new ArrayList<>();
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

                Joko.Task t = null;
                if (type.equals("T")) {
                    t = new Joko.ToDo(desc);
                } else if (type.equals("D")) {
                    DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                    LocalDateTime by = LocalDateTime.parse(parts[3], fileFormat);
                    t = new Joko.Deadline(desc, by);
                } else if (type.equals("E")) {
                    t = new Joko.Event(desc, parts[3], parts[4]);
                }
                if (t != null) {
                    t.isDone = isDone;
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }
}
