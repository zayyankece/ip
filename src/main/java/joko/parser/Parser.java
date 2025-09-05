package joko.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    public static class Command {

        public final String type;
        public final String desc;
        public final LocalDateTime by;
        public final String from;
        public final String to;
        public final int index;

        // For simple commands like list, bye
        public Command(String type) {
            this(type, null, null, null, null, -1);
        }

        // For tasks like todo, deadline, event
        public Command(String type, String desc, LocalDateTime by, String from, String to) {
            this(type, desc, by, from, to, -1);
        }

        // For commands with index: mark, unmark, delete
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

    // Detects command type
    public static String getCommandType(String input) {
        return input.split(" ")[0].toLowerCase();
    }

    // Parse index-based commands
    public static Command parseIndexCommand(String input, String type) throws NumberFormatException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            throw new NumberFormatException("Missing task number");
        }
        int index = Integer.parseInt(parts[1].trim()) - 1;
        return new Command(type, index);
    }

    // Parse todo
    public static Command parseTodo(String input) throws IllegalArgumentException {
        String desc = input.substring(5).trim();
        if (desc.isEmpty()) {
            throw new IllegalArgumentException("Todo description cannot be empty");
        }
        return new Command("todo", desc, null, null, null);
    }

    // Parse deadline
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

    // Parse event
    public static Command parseEvent(String input) throws IllegalArgumentException {
        try {
            String[] part1 = input.substring(6).split(" /from ", 2);
            if (part1.length < 2) {
                throw new IllegalArgumentException("Event must have /from time");
            }

            String desc = part1[0].trim();
            String[] part2 = part1[1].split(" /to ", 2);
            if (part2.length < 2) {
                throw new IllegalArgumentException("Event must have /to time");
            }
            return new Command("event", desc, null, part2[0].trim(), part2[1].trim());
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid event format. Use: event <desc> /from <start> /to <end>");
        }
    }

    // Check simple commands
    public static boolean isExit(String input) {
        return input.equalsIgnoreCase("bye");
    }

    public static boolean isList(String input) {
        return input.equalsIgnoreCase("list");
    }
}
