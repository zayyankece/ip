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
 * A wrapper class for the Joko logic in a GUI context.
 * <p>
 * Processes user commands and returns string responses instead of printing to console.
 * Handles task operations: add, delete, mark/unmark, list, and find.
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
     * Initializes the UI handler and loads tasks from storage.
     * </p>
     */
    public GuiJoko() {
        this.guiUi = new GuiUi();
        Storage storage = new Storage("task.txt");
        this.taskList = new TaskList(storage.loadTasks(), storage);
    }

    /**
     * Processes a user command and returns the response string.
     *
     * @param input raw user input
     * @return the response string or error message
     */
    public String getResponse(String input) {
        assert input != null && !input.trim().isEmpty() : "User input must not be null/empty";
        String commandType = Parser.getCommandType(input);

        try {
            return switch (commandType) {
            case "bye" -> "Bye. Hope to see you again soon!";
            case "list" -> guiUi.showTaskList(taskList.getTasks());
            case "mark", "unmark" -> handleMarkUnmark(input, commandType);
            case "todo", "deadline", "event" -> handleAddTask(input, commandType);
            case "delete" -> handleDeleteTask(input);
            case "find" -> handleFindTask(input);
            default -> "Sorry, I could not understand your command :(";
            };
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Handles {@code mark} and {@code unmark} commands.
     *
     * @param input the raw user input
     * @param commandType either "mark" or "unmark"
     * @return the formatted response string
     */
    private String handleMarkUnmark(String input, String commandType) {
        Parser.Command cmd = Parser.parseIndexCommand(input, commandType);
        assert cmd.index >= 0 : "Parsed index must not be negative";
        boolean isMark = commandType.equals("mark");
        Task task = taskList.markTask(cmd.index, isMark);
        return guiUi.showTaskMarked(task, isMark);
    }

    /**
     * Handles {@code todo}, {@code deadline}, and {@code event} commands.
     *
     * @param input the raw user input
     * @param commandType the type of task to add
     * @return the formatted response string
     */
    private String handleAddTask(String input, String commandType) {
        Parser.Command cmd = parseCommandByType(input, commandType);
        Task newTask = createTaskFromCommand(cmd);
        taskList.addTask(newTask);
        return guiUi.showTaskAdded(newTask, taskList.size());
    }

    /**
     * Parses the input into a {@link Parser.Command} according to the task type.
     *
     * @param input the raw user input
     * @param type the command type (todo, deadline, event)
     * @return the parsed command
     * @throws IllegalArgumentException if the command type is invalid
     */
    private Parser.Command parseCommandByType(String input, String type) {
        return switch (type) {
        case "todo" -> Parser.parseTodo(input);
        case "deadline" -> Parser.parseDeadline(input);
        case "event" -> Parser.parseEvent(input);
        default -> throw new IllegalArgumentException("Unknown command type: " + type);
        };
    }

    /**
     * Creates a {@link Task} instance from a parsed {@link Parser.Command}.
     *
     * @param cmd the parsed command containing task details
     * @return the created {@code Task}
     * @throws IllegalArgumentException if the task type is invalid
     */
    private Task createTaskFromCommand(Parser.Command cmd) {
        return switch (cmd.type) {
        case "todo" -> new ToDo(cmd.desc);
        case "deadline" -> new Deadline(cmd.desc, cmd.by);
        case "event" -> new Event(cmd.desc, cmd.from, cmd.to);
        default -> throw new IllegalArgumentException("Unknown task type: " + cmd.type);
        };
    }

    /**
     * Handles the {@code delete} command.
     *
     * @param input the raw user input
     * @return the formatted response string
     */
    private String handleDeleteTask(String input) {
        Parser.Command cmd = Parser.parseIndexCommand(input, "delete");
        Task removed = taskList.deleteTask(cmd.index);
        return guiUi.showTaskDeleted(removed, taskList.size());
    }

    /**
     * Handles the {@code find} command.
     *
     * @param input the raw user input
     * @return the formatted response string
     */
    private String handleFindTask(String input) {
        Parser.Command cmd = Parser.parseFind(input);
        ArrayList<Task> found = taskList.findTasks(cmd.desc);
        return guiUi.showFoundTasks(found);
    }

    /**
     * Returns the welcome message for display in the GUI.
     *
     * @return the welcome message
     */
    public String getWelcomeMessage() {
        return guiUi.showWelcome(taskList.getTasks());
    }
}
