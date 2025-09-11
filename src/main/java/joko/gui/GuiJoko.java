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
 * A wrapper for the Joko logic to be used in GUI.
 * Returns string responses instead of printing.
 */
public class GuiJoko {

    private final GuiUi guiUi;
    private final TaskList taskList;

    public GuiJoko() {
        this.guiUi = new GuiUi();
        Storage storage = new Storage("task.txt");
        this.taskList = new TaskList(storage.loadTasks(), storage);
    }

    /**
     * Processes a command and returns Joko's response as a string.
     *
     * @param input the user command
     * @return the response string
     */
    public String getResponse(String input) {
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
                    newTask = new ToDo(cmd.desc);
                    break;
                case "deadline":
                    newTask = new Deadline(cmd.desc, cmd.by);
                    break;
                default:
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
     * Returns a welcome message for GUI.
     */
    public String getWelcomeMessage() {
        return guiUi.showWelcome(taskList.getTasks());
    }
}
