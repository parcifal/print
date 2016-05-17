package eu.parcifal.print;

import java.util.Collection;

/**
 * Associates a variable amount of {@link eu.parcifal.print.Channel}s with a
 * {@link eu.parcifal.print.Printable} and can print the contained
 * {@eu.parcifal.print.Printable} to all {@eu.parcifal.print.Channel}s.
 * 
 * @see eu.parcifal.print.Printable
 * @see eu.parcifal.print.Channel
 * 
 * @author Michaël van de Weerd
 * @version 12.05.2016
 * @since 28.04.2016
 */
public class Print {

    /**
     * The {@link eu.parcifal.print.Printable} of the current
     * {@link eu.parcifal.print.Print}.
     */
    private final Printable printable;

    /**
     * A {@link java.util.Collection} of {@link eu.parcifal.print.Channel}s to
     * which the current {@link eu.parcifal.print.Print} prints.
     */
    private final Collection<Channel> channels;

    /**
     * Construct a new {@link eu.parcifal.print.Print}, containing the specified
     * {@link eu.parcifal.print.Printable} and {@link eu.parcifal.print.Channel}
     * s.
     * 
     * @param printable
     *            The {@link eu.parcifal.print.Printable} to be printed by the
     *            new {@link eu.parcifal.print.Print}.
     * @param channels
     *            The {@link eu.parcifal.print.Channel}s to which the specified
     *            {@link eu.parcifal.print.Printable} must be printed.
     */
    public Print(Printable printable, Collection<Channel> channels) {
        this.printable = printable;
        this.channels = channels;
    }

    /**
     * Return the {@link eu.parcifal.print.Printable} of the current
     * {@link eu.parcifal.print.Print}.
     * 
     * @return The {@link eu.parcifal.print.Printable} of the current
     *         {@link eu.parcifal.print.Print}.
     */
    public final Printable getPrintable() {
        return this.printable;
    }

    /**
     * Return the {@link java.util.Collection} of
     * {@link eu.parcifal.print.Channel}s of the current
     * {@link eu.parcifal.print.Print}.
     * 
     * @return The {@link java.util.Collection} of
     *         {@link eu.parcifal.print.Channel}s of the current
     *         {@link eu.parcifal.print.Print}.
     */
    public final Collection<Channel> getChannels() {
        return this.channels;
    }

    /**
     * Execute the current {@link eu.parcifal.print.Print}.
     * 
     * @return The current {@link eu.parcifal.print.Print}.
     */
    final Print execute() {
        for (Channel channel : this.channels) {
            channel.print(this.printable);
        }

        return this;
    }

}
