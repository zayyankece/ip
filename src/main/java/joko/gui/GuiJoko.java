package joko.gui;

import java.util.ArrayList;

import joko.parser.Parser;
import joko.storage.Storage;
import joko.task.Deadline;
import joko.task.Event;
import joko.task.Task;
import joko.task.TaskList;
import joko.task.ToDo;

/**
 * A wrapper class for the Joko logic to be used in a GUI environment.
 * <p>
 * This class processes user commands and returns string responses
 * instead of printing them directly to the console.
 * It handles task operations such as adding, deleting, marking/unmarking,
 * listing, and finding tasks.
 * </p>
 */
public class GuiJoko {

    /** The GUI interface used to format responses. */
    private final GuiUi guiUi;

    /** The task list storing all tasks and interacting with storage. */
    private final TaskList taskList;

    /**
     * Constructs a new {@code GuiJoko} instance.
     * <p>
     * Initializes the GUI interface and loads tasks from storage.
     * </p>
     */
    public GuiJoko() {
        this.guiUi = new GuiUi();
        Storage storage = new Storage("task.txt");
        this.taskList = new TaskList(storage.loadTasks(), storage);
    }

    /**
     * Processes a user command and returns the response as a string.
     * <p>
     * Supported commands include:
     * <ul>
     *     <li>bye</li>
     *     <li>list</li>
     *     <li>mark/unmark</li>
     *     <li>todo/deadline/event</li>
     *     <li>delete</li>
     *     <li>find</li>
     * </ul>
     * </p>
     *
     * @param input the command input string from the user
     * @return the response string after executing the command, or an error message if failed
     */
    public String getResponse(String input) {
        assert input != null && !input.trim().isEmpty() : "User input must not be null/empty";
        String commandType = Parser.getCommandType(input);
        try {
            switch (commandType) {
            case "bye":
                return "Bye. Hope to see you again soon!";

            case "list":
                return guiUi.showTaskList(taskList.getTasks());

            case "mark":
            case "unmark": {
                Parser.Command cmd = Parser.parseIndexCommand(input, commandType);
                assert cmd.index >= 0 : "Parsed index must not be negative";
                Task t = taskList.markTask(cmd.index, commandType.equals("mark"));
                return guiUi.showTaskMarked(t, commandType.equals("mark"));
            }

            case "todo":
            case "deadline":
            case "event": {
                Parser.Command cmd;
                switch (commandType) {
                case "todo":
                    cmd = Parser.parseTodo(input);
                    break;
                case "deadline":
                    cmd = Parser.parseDeadline(input);
                    break;
                default:
                    cmd = Parser.parseEvent(input);
                    break;
                }

                Task newTask;
                switch (cmd.type) {
                case "todo":
                    assert cmd.desc != null && !cmd.desc.isEmpty() : "Todo must have a description";
                    newTask = new ToDo(cmd.desc);
                    break;
                case "deadline":
                    assert cmd.by != null : "Deadline must have a /by date";
                    newTask = new Deadline(cmd.desc, cmd.by);
                    break;
                default:
                    assert cmd.from != null && cmd.to != null : "Event must have /from and /to dates";
                    newTask = new Event(cmd.desc, cmd.from, cmd.to);
                    break;
                }

                taskList.addTask(newTask);
                return guiUi.showTaskAdded(newTask, taskList.size());
            }

            case "delete": {
                Parser.Command cmd = Parser.parseIndexCommand(input, "delete");
                Task removed = taskList.deleteTask(cmd.index);
                return guiUi.showTaskDeleted(removed, taskList.size());
            }

            case "find": {
                Parser.Command cmd = Parser.parseFind(input);
                ArrayList<Task> found = taskList.findTasks(cmd.desc);
                return guiUi.showFoundTasks(found);
            }

            default:
                return "Sorry, I could not understand your command :(";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Returns the welcome message for the GUI.
     * <p>
     * Typically called when the GUI is initialized to show the user a greeting.
     * </p>
     *
     * @return a string representing the welcome message
     */
    public String getWelcomeMessage() {
        return guiUi.showWelcome(taskList.getTasks());
    }
}
