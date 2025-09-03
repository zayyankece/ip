import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Joko {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("task.txt");
        TaskList taskList = new TaskList(storage.loadTasks(), storage);

        ui.showWelcome(taskList.getTasks());

        while (true) {
            String input = ui.readCommand();
            String[] inputParts = input.split(" ", 2);
            String command = inputParts[0];

            if (input.equals("bye")) {
                ui.showMessage("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                ui.showTaskList(taskList.getTasks());
            } else if (command.equals("mark") || command.equals("unmark")) {
                try {
                    int index = Integer.parseInt(inputParts[1]) - 1;
                    Joko.Task t = taskList.markTask(index, command.equals("mark"));
                    ui.showTaskMarked(t, command.equals("mark"));
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
                } else  {
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
                    taskList.addTask(newTask);
                    ui.showTaskAdded(newTask, taskList.size());
                }

            } else if (command.equals("delete")) {
                try {
                    int index = Integer.parseInt(inputParts[1]) - 1;
                    if (index < 0 || index >= taskList.getTasks().size()) {
                        ui.showMessage("Invalid task number.");
                        continue;
                    } else {
                        Task removed = taskList.deleteTask(index);
                        ui.showTaskDeleted(removed, taskList.size());
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

}