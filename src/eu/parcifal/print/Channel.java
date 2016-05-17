package eu.parcifal.print;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A {@link eu.parcifal.print.Channel} associates a name with an
 * {@link java.io.OutputStream} and is meant to be the only object to write to
 * that specific {@link java.io.OutputStream}. A
 * {@link eu.parcifal.print.Channel}s formats the content of a
 * {@link eu.parcifal.print.Printable} to be written to the
 * {@link java.io.OutputStream}.
 * 
 * @see eu.parcifal.print.Printable
 * 
 * @author Michaël van de Weerd
 * @version 15.05.2016
 * @since 10.05.2016
 */
public abstract class Channel {

    /**
     * The name of the current {@link eu.parcifal.print.Channel}.
     */
    private final String channelName;

    /**
     * The {@link java.io.OutputStream} of the current
     * {@link eu.parcifal.print.Channel}.
     */
    protected final OutputStream outputStream;

    /**
     * Construct a new {@link eu.parcifal.print.Channel} with the specified name
     * and {@link java.io.OutputStream}.
     * 
     * @param channelName
     *            The name of the new {@link eu.parcifal.print.Channel}.
     * @param outputStream
     *            The OutputStream of the new {@link eu.parcifal.print.Channel}.
     */
    protected Channel(String channelName, OutputStream outputStream) {
        this.channelName = channelName;
        this.outputStream = outputStream;
    }

    /**
     * Return the name of the current {@link eu.parcifal.print.Channel}.
     * 
     * @return The name of the current {@link eu.parcifal.print.Channel}.
     */
    public final String getChannelName() {
        return this.channelName;
    }

    /**
     * Return true if the current {@link eu.parcifal.print.Channel} accepts the
     * specified {@link eu.parcifal.print.Printable}, otherwise return false.
     * Returns true by default.
     * 
     * @param printable
     *            The printable that the current
     *            {@link eu.parcifal.print.Channel} does or does not accept.
     * @return True if the current {@link eu.parcifal.print.Channel} accept the
     *         specified {@link eu.parcifal.print.Printable}, otherwise false.
     */
    public boolean acceptsPrintable(Printable printable) {
        return true;
    }

    /**
     * Return a string representing the specified
     * {@link eu.parcifal.print.Printable}.
     * 
     * @param printable
     *            The {@link eu.parcifal.print.Printable} to be formatted.
     * @return A string representing the specified
     *         {@link eu.parcifal.print.Printable}.
     */
    protected abstract String format(Printable printable);

    /**
     * Write the results of the
     * {@link eu.parcifal.print.Channel#format(Printable)} method to the
     * {@link java.io.OutputStream} of the current
     * {@link eu.parcifal.print.Channel}.
     * 
     * @param printable
     *            The {@link eu.parcifal.print.Printable} of which the formatted
     *            string will be written to the OutputStream of the current
     *            {@link eu.parcifal.print.Channel}.
     */
    public void print(Printable printable) {
        String output = this.format(printable);

        try {
            this.outputStream.write(output.getBytes());
            this.outputStream.flush();
        } catch (IOException exception) {
            throw new Error(exception);
        }
    }

}
