package joko.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses user input into commands for the Joko task manager application.
 * <p>
 * Provides methods to detect command types, parse task-related commands,
 * and handle index-based commands such as mark, unmark, and delete.
 * </p>
 */
public class Parser {

    /**
     * Represents a parsed command from user input.
     * <p>
     * Contains information about the command type, task description,
     * deadline, event times, and task index if applicable.
     * </p>
     */
    public static class Command {
        /** The type of the command, e.g., "todo", "deadline", "event", "mark". */
        public final String type;

        /** The task description (for todo, deadline, event). */
        public final String desc;

        /** The deadline for a Deadline task (null for other types). */
        public final LocalDateTime by;

        /** The start time for an Event task (null for other types). */
        public final String from;

        /** The end time for an Event task (null for other types). */
        public final String to;

        /** The index of the task for index-based commands (mark, unmark, delete). */
        public final int index;


        /**
         * Constructs a simple command (like "list" or "bye") with no arguments.
         *
         * @param type the command type
         */
        public Command(String type) {
            this(type, null, null, null, null, -1);
        }

        /**
         * Constructs a task command (todo, deadline, event) with details.
         *
         * @param type the command type
         * @param desc the task description
         * @param by   the deadline (for Deadline tasks)
         * @param from the start time (for Event tasks)
         * @param to   the end time (for Event tasks)
         */
        public Command(String type, String desc, LocalDateTime by, String from, String to) {
            this(type, desc, by, from, to, -1);
        }

        /**
         * Constructs an index-based command (mark, unmark, delete).
         *
         * @param type  the command type
         * @param index the index of the task (0-based)
         */
        public Command(String type, int index) {
            this(type, null, null, null, null, index);
        }

        private Command(String type, String desc, LocalDateTime by, String from, String to, int index) {
            this.type = type;
            this.desc = desc;
            this.by = by;
            this.from = from;
            this.to = to;
            this.index = index;
        }
    }

    /**
     * Returns the command type from the user's input.
     *
     * @param input the raw user input
     * @return the command type in lowercase
     */
    public static String getCommandType(String input) {
        return input.split(" ")[0].toLowerCase();
    }

    /**
     * Parses index-based commands like "mark 2", "unmark 1", "delete 3".
     *
     * @param input the raw user input
     * @param type  the command type
     * @return a {@link Command} with the index parsed
     * @throws NumberFormatException if the index is missing or invalid
     */
    public static Command parseIndexCommand(String input, String type) throws NumberFormatException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) throw new NumberFormatException("Missing task number");
        int index = Integer.parseInt(parts[1].trim()) - 1;
        return new Command(type, index);
    }

    /**
     * Parses a "todo" command.
     *
     * @param input the raw user input starting with "todo "
     * @return a {@link Command} representing the todo task
     * @throws IllegalArgumentException if the description is empty
     */
    public static Command parseTodo(String input) throws IllegalArgumentException {
        String desc = input.substring(5).trim();
        if (desc.isEmpty()) throw new IllegalArgumentException("Todo description cannot be empty");
        return new Command("todo", desc, null, null, null);
    }

    /**
     * Parses a "deadline" command.
     *
     * @param input the raw user input starting with "deadline "
     * @return a {@link Command} representing the deadline task
     * @throws IllegalArgumentException if the format is invalid or date/time is incorrect
     */
    public static Command parseDeadline(String input) throws IllegalArgumentException {
        try {
            String[] parts = input.substring(9).split(" /by", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty())
                throw new IllegalArgumentException("Deadline must have a description and /by time");

            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            LocalDateTime by = LocalDateTime.parse(parts[1].trim(), inputFormat);
            return new Command("deadline", parts[0].trim(), by, null, null);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date/time format. Use dd/MM/yyyy HHmm");
        }
    }

    /**
     * Parses an "event" command.
     *
     * @param input the raw user input starting with "event "
     * @return a {@link Command} representing the event task
     * @throws IllegalArgumentException if the format is invalid
     */
    public static Command parseEvent(String input) throws IllegalArgumentException {
        try {
            String[] part1 = input.substring(6).split(" /from ", 2);
            if (part1.length < 2) throw new IllegalArgumentException("Event must have /from time");

            String desc = part1[0].trim();
            String[] part2 = part1[1].split(" /to ", 2);
            if (part2.length < 2) throw new IllegalArgumentException("Event must have /to time");

            return new Command("event", desc, null, part2[0].trim(), part2[1].trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid event format. Use: event <desc> /from <start> /to <end>");
        }
    }

    /**
     * Checks if the input is the exit command "bye".
     *
     * @param input the raw user input
     * @return {@code true} if the input is "bye" (case-insensitive), {@code false} otherwise
     */
    public static boolean isExit(String input) {
        return input.equalsIgnoreCase("bye");
    }

    /**
     * Checks if the input is the "list" command.
     *
     * @param input the raw user input
     * @return {@code true} if the input is "list" (case-insensitive), {@code false} otherwise
     */
    public static boolean isList(String input) {
        return input.equalsIgnoreCase("list");
    }
}
