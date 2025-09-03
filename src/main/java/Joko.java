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
            String commandType = Parser.getCommandType(input);
            //String[] inputParts = input.split(" ", 2);
            //String command = inputParts[0];

            if (commandType.equals("bye")) {
                ui.showMessage("Bye. Hope to see you again soon!");
                break;
            } else if (commandType.equals("list")) {
                ui.showTaskList(taskList.getTasks());
            } else if (commandType.equals("mark") || commandType.equals("unmark")) {
                try {
                    Parser.Command cmd = Parser.parseIndexCommand(input, commandType);
                    Task t = taskList.markTask(cmd.index, commandType.equals("mark"));
                    ui.showTaskMarked(t, commandType.equals("mark"));
                } catch (Exception e) {
                    ui.showMessage("Please type a valid input: <mark/unmark> <task number>");
                }
            } else if (commandType.equals("todo") || commandType.equals("deadline") || commandType.equals("event")) {
                try {
                    Parser.Command cmd;
                    switch (commandType) {
                        case "todo":
                            cmd = Parser.parseTodo(input);
                            break;
                        case "deadline":
                            cmd = Parser.parseDeadline(input);
                            break;
                        case "event":
                            cmd = Parser.parseEvent(input);
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown command type");
                    }

                    Task newTask = null;
                    switch (cmd.type) {
                        case "todo":
                            newTask = new ToDo(cmd.desc);
                            break;
                        case "deadline":
                            newTask = new Deadline(cmd.desc, cmd.by);
                            break;
                        case "event":
                            newTask = new Event(cmd.desc, cmd.from, cmd.to);
                            break;
                    }

                    taskList.addTask(newTask);
                    ui.showTaskAdded(newTask, taskList.size());
                } catch (Exception e) {
                    ui.showMessage("Error adding task: " + e.getMessage());
                }
            } else if (commandType.equals("delete")) {
                try {
                    // Use Parser to get the index from input
                    Parser.Command cmd = Parser.parseIndexCommand(input, "delete");
                    int index = cmd.index;

                    // Delete the task and show confirmation
                    Task removed = taskList.deleteTask(index);
                    ui.showTaskDeleted(removed, taskList.size());
                } catch (Exception e) {
                    ui.showMessage("Please type a valid input: <delete> <task number>");
                }
            } else {
                ui.showMessage("Sorry, I could not understand your command :(");
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