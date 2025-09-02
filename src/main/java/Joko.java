import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;

public class Joko {
    public static void main(String[] args) {
        final String TASK_FILE = "tasks.txt";
        ArrayList<Task> taskList = loadTasks(TASK_FILE);
        Ui ui = new Ui();

        ui.showWelcome(taskList);

        while (true) {
            String input = ui.readCommand();
            String[] inputParts = input.split(" ", 2);
            String command = inputParts[0];

            if (input.equals("bye")) {
                ui.showMessage("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                ui.showTaskList(taskList);
            } else if (command.equals("mark") || command.equals("unmark")) {
                try {
                    int index = Integer.parseInt(inputParts[1]) - 1;
                    if (index < 0 || index >= taskList.size()) {
                        ui.showMessage("Invalid task number.");
                        continue;
                    }
                    if (command.equals("mark")) {
                        taskList.get(index).isDone = true;
                        saveTasks(taskList, TASK_FILE);
                        ui.showTaskMarked(taskList.get(index), true);
                    } else {
                        taskList.get(index).isDone = false;
                        saveTasks(taskList, TASK_FILE);
                        ui.showTaskMarked(taskList.get(index), false);
                    }
                } catch (Exception e) {
                    ui.showMessage("Please type a valid input: <mark/unmark> <task number>");
                }

            } else if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
                Task newTask = null;

                if (command.equals("todo")) {
                    try {
                        String desc = input.substring(5).trim();
                        if (desc.isEmpty()) {
                            throw new IllegalArgumentException("Todo description cannot be empty!");
                        }
                        newTask = new ToDo(desc);
                    } catch (Exception e) {
                        ui.showMessage("Error adding Todo: " + e.getMessage());
                    }
                } else if (command.equals("deadline")) {
                    try {
                        String[] parts = input.substring(9).split(" /by", 2);
                        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                            throw new IllegalArgumentException("Deadline must have a description and " +
                                    "a /by date/time (dd/MM/yyyy HHmm)");
                        }
                        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                        LocalDateTime by = LocalDateTime.parse(parts[1].trim(), inputFormat);
                        newTask = new Deadline(parts[0].trim(), by);
                    } catch (Exception e) {
                        ui.showMessage("Error adding Deadline: " + e.getMessage());
                    }
                } else if (command.equals("event")) {
                    try {
                        String[] part1 = input.substring(6).split(" /from ", 2);
                        if (part1.length < 2) {
                            throw new IllegalArgumentException("Event must have a description and a /from start time.");
                        }
                        String desc = part1[0].trim();
                        String[] part2 = part1[1].split(" /to ", 2);
                        if (part2.length < 2) {
                            throw new IllegalArgumentException("Event must have a /to end time.");
                        }
                        newTask = new Event(desc, part2[0].trim(), part2[1].trim());
                    } catch (Exception e) {
                        ui.showMessage("Error adding Event: " + e.getMessage());
                    }
                }

                if (newTask != null) {
                    taskList.add(newTask);
                    saveTasks(taskList, TASK_FILE);
                    ui.showTaskAdded(newTask, taskList.size());
                }

            } else if (command.equals("delete")) {
                try {
                    int index = Integer.parseInt(inputParts[1]) - 1;
                    if (index < 0 || index >= taskList.size()) {
                        ui.showMessage("Invalid task number.");
                        continue;
                    } else {
                        Task temp = taskList.get(index);
                        taskList.remove(index);
                        saveTasks(taskList, TASK_FILE);
                        ui.showTaskDeleted(temp, taskList.size());
                    }
                } catch (Exception e) {
                    ui.showMessage("Please type a valid input: <delete> <task number>");
                }
            } else {
                ui.showMessage("sorry i could not understand your command :(");
            }
        }

        ui.close();
    }

    static class Task {
        protected final String desc;
        protected boolean isDone;

        public Task(String desc) {
            this.desc = desc;
            this.isDone = false;
        }

        public String getStatus() {
            return (isDone ? "[X] " : "[ ] ");
        }

        @Override
        public String toString() {
            return getStatus() + desc;
        }
    }

    static class Deadline extends Task {
        protected LocalDateTime by;

        public Deadline(String desc, LocalDateTime by) {
            super(desc);
            this.by = by;
        }

        @Override
        public String toString() {
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
            return "[D]" + super.toString() +
                    "(by: " + by.format(outputFormat) + ") ";
        }
    }

    static class ToDo extends Task {
        public ToDo(String desc) {
            super(desc);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    static class Event extends Task {
        protected String from;
        protected String to;

        public Event(String desc, String from, String to) {
            super(desc);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() +
                    "(from: " + from +
                    " to: " + to + ")";
        }
    }

    public static void saveTasks(ArrayList<Task> tasks, String filename) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            for (Task t : tasks) {
                if (t instanceof ToDo) {
                    writer.println("T | " + (t.isDone ? "1" : "0") + " | " + t.desc);
                } else if (t instanceof Deadline) {
                    Deadline d = (Deadline) t;
                    DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                    writer.println("D | " + (d.isDone ? "1" : "0") + " | " + d.desc + " | " + d.by.format(fileFormat));
                } else if (t instanceof Event) {
                    Event e = (Event) t;
                    writer.println("E | " + (e.isDone ? "1" : "0") + " | " + e.desc + " | " + e.from + " | " + e.to);
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadTasks(String filename) {
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