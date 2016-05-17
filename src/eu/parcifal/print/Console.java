package eu.parcifal.print;

import java.util.Arrays;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

/**
 * Prints to the {@link eu.parcifal.print.Console} for direct communication with
 * the user. Each {@link eu.parcifal.print.Printable} is formatted for user
 * readability and the possibility to backtrack the source of the message.
 * 
 * @see eu.parcifal.print.Printable
 * 
 * @author Michaël van de Weerd
 * @since 10.05.2016
 * @version 12.05.2016
 */
public class Console {

    /**
     * The format of the header of each print that the
     * {@link eu.parcifal.print.Console} makes.
     */
    private final static String HEADER_FORMAT = "%1$-8.8S[ %2$tY.%2$tm.%2$td %2$tH:%2$tM:%2$tS:%2$tL ] %3$s#%4$s (%5$s:%6$d)\r\n";

    /**
     * The instance of the {@link eu.parcifal.print.Channel} used by the
     * {@link eu.parcifal.print.Console} to print. Its
     * {@link eu.parcifal.print.Channel#format(Printable)} method creates a
     * header with meta-data and appends each line of content.
     */
    private final static Channel CHANNEL_CONSOLE = new Channel("eu.parcifal.print.console", System.out) {

        @Override
        protected String format(Printable printable) {
            String output = String.format(HEADER_FORMAT, printable.getTag(), printable.getDateTime(),
                    printable.getSourceClassName(), printable.getSourceMethodName(), printable.getSourceFileName(),
                    printable.getSourceLineNumber());

            for (int i = 0; i < printable.getContentLineCount(); i++) {
                output += printable.getLine(i) + "\r\n";
            }

            return output;
        }

    };

    /**
     * Indicates whether or not the current {@link eu.parcifal.print.Console}
     * prints debug messages or not.
     */
    private final static boolean DEBUG;

    static {
        if (Configuration.AVAILABLE) {
            try {
                DEBUG = (boolean) Configuration.evaluate("/printer/console/@debug", XPathConstants.BOOLEAN);
            } catch (XPathExpressionException exception) {
                throw new Error(exception);
            }
        } else {
            DEBUG = false;
        }

        Printer.addChannel(CHANNEL_CONSOLE);
    }

    /**
     * Print a note containing the specified message to the console
     * {@link eu.parcifal.print.Channel}.
     * 
     * @param message
     *            The message to be printed to the console.
     * @return The {@link eu.parcifal.print.Print} containing the printed
     *         {@link eu.parcifal.print.Printable} and the
     *         {@link eu.parcifal.print.Channel}s to which it is printed.
     */
    public final static Print note(String message) {
        return Printer.executePrint(new Printable("note", message) {

            private String[] lines = this.getContent().split("\r?\n");

            @Override
            public int getContentLineCount() {
                return this.lines.length;
            }

            @Override
            public String getLine(int number) {
                return " : " + this.lines[number];
            }

        }, Arrays.asList(CHANNEL_CONSOLE));
    }

    /**
     * Print a formatted note containing the specified message to the console
     * {@link eu.parcifal.print.Channel}.
     * 
     * @param message
     *            The message to be printed to the console.
     * @param arguments
     *            The arguments used to format the specified message.
     * @return The {@link eu.parcifal.print.Print} containing the printed
     *         {@link eu.parcifal.print.Printable} and the
     *         {@link eu.parcifal.print.Channel}s to which it is printed.
     */
    public final static Print note(String message, Object... arguments) {
        return note(String.format(message, arguments));
    }

    /**
     * Print a warning containing the specified message to the console
     * {@link eu.parcifal.print.Channel}.
     * 
     * @param message
     *            The message to be printed to the console.
     * @return The {@link eu.parcifal.print.Print} containing the printed
     *         {@link eu.parcifal.print.Printable} and the
     *         {@link eu.parcifal.print.Channel}s to which it is printed.
     */
    public final static Print warning(String message) {
        return Printer.executePrint(new Printable("warning", message) {

            private String[] lines = this.getContent().split("\r?\n");

            @Override
            public int getContentLineCount() {
                return this.lines.length;
            }

            @Override
            public String getLine(int number) {
                return " ! " + this.lines[number];
            }

        }, Arrays.asList(CHANNEL_CONSOLE));
    }

    /**
     * Print a warning containing the specified message to the console
     * {@link eu.parcifal.print.Channel}.
     * 
     * @param message
     *            The message to be printed to the console.
     * @param arguments
     *            The arguments used to format the specified message.
     * @return The {@link eu.parcifal.print.Print} containing the printed
     *         {@link eu.parcifal.print.Printable} and the
     *         {@link eu.parcifal.print.Channel}s to which it is printed.
     */
    public final static Print warning(String message, Object... arguments) {
        return warning(String.format(message, arguments));
    }

    /**
     * Print an error containing the message and stack-trace of the specified
     * {@link java.lang.Throwable} to the console
     * {@link eu.parcifal.print.Channel}.
     * 
     * @param throwable
     *            The throwable to be printed to the console.
     * @return The {@link eu.parcifal.print.Print} containing the printed
     *         {@link eu.parcifal.print.Printable} and the
     *         {@link eu.parcifal.print.Channel}s to which it is printed.
     */
    public final static Print error(Throwable throwable) {
        return Printer.executePrint(
                new Printable("error", throwable.getMessage() + " (" + throwable.getClass().getName() + ")") {

                    private StackTraceElement[] stackTrace = throwable.getStackTrace();

                    @Override
                    public int getContentLineCount() {
                        return stackTrace.length + 1;
                    }

                    @Override
                    public String getLine(int number) {
                        if (number == 0) {
                            return " : " + this.getContent();
                        } else {
                            StackTraceElement element = this.stackTrace[number - 1];

                            String line = " @ " + element.getClassName() + "#" + element.getMethodName();

                            if (!(element.getFileName() == null || element.getLineNumber() == -1)) {
                                return line + " (" + element.getFileName() + ":" + element.getLineNumber() + ")";
                            } else {
                                return line;
                            }
                        }
                    }

                }, Arrays.asList(CHANNEL_CONSOLE));
    }

    /**
     * Print a debug message containing the specified message to the console
     * {@link eu.parcifal.print.Channel} if the current
     * {@link eu.parcifal.print.Console} is set to print debug messages.
     * 
     * @param message
     *            The message to be printed to the console.
     * @return The {@link eu.parcifal.print.Print} containing the printed
     *         {@link eu.parcifal.print.Printable} and the
     *         {@link eu.parcifal.print.Channel}s to which it is printed.
     */
    public final static Print debug(String message) {
        Printable debug = new Printable("debug", message) {

            private String[] lines = this.getContent().split("\r?\n");

            @Override
            public int getContentLineCount() {
                return lines.length;
            }

            @Override
            public String getLine(int number) {
                return " > " + this.lines[number];
            }

        };

        if (DEBUG) {
            return Printer.executePrint(debug, Arrays.asList(CHANNEL_CONSOLE));
        } else {
            return Printer.compilePrint(debug, Arrays.asList(CHANNEL_CONSOLE));
        }
    }

    /**
     * Print a formatted debug message containing the specified message to the
     * console {@link eu.parcifal.print.Channel} if the current
     * {@link eu.parcifal.print.Console} is set to print debug messages.
     * 
     * @param message
     *            The message to be printed to the console.
     * @param arguments
     *            The arguments used to format the specified message.
     * @return The {@link eu.parcifal.print.Print} containing the printed
     *         {@link eu.parcifal.print.Printable} and the
     *         {@link eu.parcifal.print.Channel}s to which it is printed.
     */
    public final static Print debug(String message, Object... arguments) {
        return debug(String.format(message, arguments));
    }

}
