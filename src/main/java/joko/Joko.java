package joko;

import joko.parser.Parser;
import joko.storage.Storage;
import joko.task.*;
import joko.ui.Ui;

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
                    // Use joko.parser.Parser to get the index from input
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

}