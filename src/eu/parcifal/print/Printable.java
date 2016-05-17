package eu.parcifal.print;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * Contains a message and its meta-data to be used by a
 * {@link eu.parcifal.print.Print}.
 * 
 * @see eu.parcifal.print.Print
 * 
 * @author Michaël van de Weerd
 * @version 12.05.2016
 * @since 28.04.2016
 */
public class Printable {

    /**
     * The tag of the current {@link eu.parcifal.print.Printable}.
     */
    private final String tag;

    /**
     * The content of the current {@link eu.parcifal.print.Printable}.
     */
    private final String content;

    /**
     * The {@link java.time.LocalDateTime} at which the current
     * {@link eu.parcifal.print.Printable} was created.
     */
    private final LocalDateTime dateTime = LocalDateTime.now();

    /**
     * The class-name of the source of the current
     * {@link eu.parcifal.print.Printable}.
     */
    private final String sourceClassName;

    /**
     * The method-name of the source of the current
     * {@link eu.parcifal.print.Printable}.
     */
    private final String sourceMethodName;

    /**
     * The file-name of the source of the current
     * {@link eu.parcifal.print.Printable}.
     */
    private final String sourceFileName;

    /**
     * The line-number of the source of the current
     * {@link eu.parcifal.print.Printable}.
     */
    private final int sourceLineNumber;

    /**
     * Construct a new {@link eu.parcifal.print.Printable} containing the
     * specified tag and content.
     * 
     * @param tag
     *            The tag of the current {@link eu.parcifal.print.Printable}.
     * @param content
     *            The content of the current {@link eu.parcifal.print.Printable}
     *            .
     */
    protected Printable(String tag, String content) {
        this.tag = tag;
        this.content = content;

        StackTraceElement source = null;

        for (StackTraceElement element : new Throwable().getStackTrace()) {
            if (!Pattern.matches("^eu\\.parcifal\\.print.*", element.getClassName())) {
                source = element;

                break;
            }
        }

        this.sourceClassName = source.getClassName();
        this.sourceMethodName = source.getMethodName();
        this.sourceFileName = source.getFileName();
        this.sourceLineNumber = source.getLineNumber();
    }

    /**
     * Return the tag of the current {@link eu.parcifal.print.Printable}.
     * 
     * @return The tag of the current {@link eu.parcifal.print.Printable}.
     */
    public final String getTag() {
        return this.tag;
    }

    /**
     * Return the content of the current {@link eu.parcifal.print.Printable}.
     * 
     * @return The content of the current {@link eu.parcifal.print.Printable}.
     */
    public final String getContent() {
        return this.content;
    }

    /**
     * Return the {@link java.time.LocalDateTime} at which the current
     * {@link eu.parcifal.print.Printable} was created.
     * 
     * @return The {@link java.time.LocalDateTime} at which the current
     *         {@link eu.parcifal.print.Printable} was created.
     */
    public final LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * Return the class-name of the source of the current
     * {@link eu.parcifal.print.Printable}.
     * 
     * @return The class-name of the source of the current
     *         {@link eu.parcifal.print.Printable}.
     */
    public final String getSourceClassName() {
        return this.sourceClassName;
    }

    /**
     * Return the method-name of the source of the current
     * {@link eu.parcifal.print.Printable}.
     * 
     * @return The method-name of the source of the current
     *         {@link eu.parcifal.print.Printable}.
     */
    public final String getSourceMethodName() {
        return this.sourceMethodName;
    }

    /**
     * Return the file-name of the source of the current
     * {@link eu.parcifal.print.Printable}.
     * 
     * @return The file-name of the source of the current
     *         {@link eu.parcifal.print.Printable}.
     */
    public final String getSourceFileName() {
        return this.sourceFileName;
    }

    /**
     * Return the line-number of the source of the current
     * {@link eu.parcifal.print.Printable}.
     * 
     * @return The line-number of the source of the current
     *         {@link eu.parcifal.print.Printable}.
     */
    public final int getSourceLineNumber() {
        return this.sourceLineNumber;
    }

    /**
     * Return the amount of lines in the content of the current
     * {@link eu.parcifal.print.Printable}.
     * 
     * @return The amount of lines in the content of the current
     *         {@link eu.parcifal.print.Printable}.
     */
    public int getContentLineCount() {
        return 0;
    }

    /**
     * Return the specified line in the content of the current
     * {@link eu.parcifal.print.Printable}.
     * 
     * @param number
     *            The number of the line in the content of the current
     *            {@link eu.parcifal.print.Printable} to be returned.
     * @return The specified line in the content of the current
     *         {@link eu.parcifal.print.Printable}.
     */
    public String getLine(int number) {
        throw new RuntimeException(this.getClass().getName() + "#getLine has no implementation");
    }

}
