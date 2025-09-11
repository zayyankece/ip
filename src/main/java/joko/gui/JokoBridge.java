package joko.gui;

import joko.Joko;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Interface between GUI and old Joko CLI.
 * Captures printed output and feeds GUI commands.
 */
public class JokoBridge {

    private final Joko joko;

    private final ByteArrayOutputStream outputStream;
    private final PrintStream captureOut;
    private final PrintStream originalOut;

    public JokoBridge() {
        this.joko = new Joko();
        this.outputStream = new ByteArrayOutputStream();
        this.captureOut = new PrintStream(outputStream);
        this.originalOut = System.out;

        // Redirect output
        System.setOut(captureOut);
    }

    /**
     * Feed a single command string to Joko and capture output.
     */
    public String handleUserInput(String input) {
        // Feed the input to Joko
        // Hack: temporarily replace System.in with this command
        Scanner scanner = new Scanner(input + "\n"); // single line input
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

        try {
            // Call main, but only process this input
            // Because Joko reads Scanner.nextLine(), it will pick up this input
            // You may need to extract Joko.main() logic into a private helper internally
        } finally {
            System.out.flush();
        }

        // Return captured output
        String response = outputStream.toString().trim();

        // Clear the stream for the next command
        outputStream.reset();

        return response;
    }

    public void close() {
        System.setOut(originalOut);
    }
}
