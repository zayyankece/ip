package joko.gui;

import java.util.ArrayList;

import joko.task.Task;

/**
 * Handles the user interface for the Joko task manager application in a GUI context.
 * <p>
 * Unlike ui, this class does not print to the console.
 * Instead, it builds and returns strings that can be displayed in a GUI.
 * </p>
 */
public class GuiUi {

    /**
     * Returns a welcome message along with the current tasks in the list.
     *
     * @param tasks the list of tasks to display
     * @return the welcome message
     */
    public String showWelcome(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello! I'm Joko\n");
        sb.append("What can I do for you?\n");
        if (tasks.isEmpty()) {
            sb.append("No tasks in your list yet.\n");
        } else {
            sb.append("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
            }
        }
        return sb.toString().trim();
    }

    /**
     * Returns a formatted list of tasks.
     *
     * @param tasks the list of tasks
     * @return the formatted task list
     */
    public String showTaskList(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        if (tasks.isEmpty()) {
            sb.append("No tasks in your list yet.\n");
        } else {
            sb.append("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
            }
        }
        return sb.toString().trim();
    }

    /**
     * Returns a generic message.
     *
     * @param message the message to display
     * @return the formatted message
     */
    public String showMessage(String message) {
        return message;
    }

    /**
     * Returns a message indicating that a task has been added.
     *
     * @param task the task added
     * @param size the new number of tasks
     * @return the formatted message
     */
    public String showTaskAdded(Task task, int size) {
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Returns a message indicating that a task has been deleted.
     *
     * @param task the task removed
     * @param size the new number of tasks
     * @return the formatted message
     */
    public String showTaskDeleted(Task task, int size) {
        return "Noted. I've removed this task:\n  " + task
                + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Returns a message indicating that a task has been marked/unmarked as done.
     *
     * @param task the task updated
     * @param isDone true if marked as done, false if unmarked
     * @return the formatted message
     */
    public String showTaskMarked(Task task, boolean isDone) {
        if (isDone) {
            return "Nice! I've marked this task as done:\n  " + task;
        } else {
            return "OK, I've marked this task as not done yet:\n  " + task;
        }
    }

    /**
     * Returns a list of found tasks matching a search.
     *
     * @param tasks the matching tasks
     * @return the formatted message
     */
    public String showFoundTasks(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        if (tasks.isEmpty()) {
            sb.append("No matching tasks found.");
        } else {
            sb.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
            }
        }
        return sb.toString().trim();
    }
}
