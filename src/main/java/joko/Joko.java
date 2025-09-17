package joko;

import joko.parser.Parser;
import joko.storage.Storage;
import joko.task.Deadline;
import joko.task.Event;
import joko.task.Task;
import joko.task.TaskList;
import joko.task.ToDo;
import joko.ui.Ui;


/**
 * The main class of the Joko task manager application.
 *
 * <p>This class handles the user interface, task storage, and command parsing.
 * It runs the main program loop where users can add, list, mark/unmark, find
 * and delete tasks.</p>
 */
public class Joko {


    /**
     * The entry point of the Joko application.
     *
     * <p>It initializes the UI and storage, loads tasks, runs the main program loop,
     * and closes the UI when the user exits.</p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("task.txt");
        TaskList taskList = new TaskList(storage.loadTasks(), storage);

        ui.showWelcome(taskList.getTasks());
        runMainLoop(ui, taskList);
        ui.close();
    }

    private static void runMainLoop(Ui ui, TaskList taskList) {
        while (true) {
            String input = ui.readCommand();
            String commandType = Parser.getCommandType(input);

            if (commandType.equals("bye")) {
                ui.showMessage("Bye. Hope to see you again soon!");
                break;
            }

            if (commandType.equals("list")) {
                handleList(taskList, ui);
            } else if (commandType.equals("mark") || commandType.equals("unmark")) {
                handleMarkUnmark(input, commandType, taskList, ui);
            } else if (commandType.equals("todo") || commandType.equals("deadline") || commandType.equals("event")) {
                handleAddTask(input, commandType, taskList, ui);
            } else if (commandType.equals("delete")) {
                handleDelete(input, taskList, ui);
            } else if (commandType.equals("find")) {
                handleFind(input, taskList, ui);
            } else {
                ui.showMessage("Sorry, I could not understand your command :(");
            }
        }
    }

    private static void handleList(TaskList taskList, Ui ui) {
        ui.showTaskList(taskList.getTasks());
    }

    private static void handleMarkUnmark(String input, String commandType, TaskList taskList, Ui ui) {
        try {
            Parser.Command cmd = Parser.parseIndexCommand(input, commandType);
            Task task = taskList.markTask(cmd.index, commandType.equals("mark"));
            ui.showTaskMarked(task, commandType.equals("mark"));
        } catch (Exception e) {
            ui.showMessage("Please type a valid input: <mark/unmark> <task number>");
        }
    }

    private static void handleAddTask(String input, String commandType, TaskList taskList, Ui ui) {
        try {
            Parser.Command cmd = switch (commandType) {
            case "todo" -> Parser.parseTodo(input);
            case "deadline" -> Parser.parseDeadline(input);
            case "event" -> Parser.parseEvent(input);
            default -> throw new IllegalArgumentException("Unknown command type");
            };

            Task newTask = switch (cmd.type) {
            case "todo" -> new ToDo(cmd.desc);
            case "deadline" -> new Deadline(cmd.desc, cmd.by);
            default -> new Event(cmd.desc, cmd.from, cmd.to);
            };

            taskList.addTask(newTask);
            ui.showTaskAdded(newTask, taskList.size());
        } catch (Exception e) {
            ui.showMessage("Error adding task: " + e.getMessage());
        }
    }

    private static void handleDelete(String input, TaskList taskList, Ui ui) {
        try {
            Parser.Command cmd = Parser.parseIndexCommand(input, "delete");
            Task removed = taskList.deleteTask(cmd.index);
            ui.showTaskDeleted(removed, taskList.size());
        } catch (Exception e) {
            ui.showMessage("Please type a valid input: <delete> <task number>");
        }
    }

    private static void handleFind(String input, TaskList taskList, Ui ui) {
        try {
            Parser.Command cmd = Parser.parseFind(input);
            ui.showFoundTasks(taskList.findTasks(cmd.desc));
        } catch (Exception e) {
            ui.showMessage("Please provide a valid keyword to find.");
        }
    }
}
