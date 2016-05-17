package eu.parcifal.print;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Handles {@link eu.parcifal.print.Printable}s by wrapping them into
 * {@link eu.parcifal.print.Print} objects together with specific
 * {@link eu.parcifal.print.Channel}s and executes the
 * {@link eu.parcifal.print.Print}, causing the
 * {@link eu.parcifal.print.Printable}s to be printed to each
 * {@link eu.parcifal.print.Channel}.
 * 
 * @see eu.parcifal.print.Channel
 * @see eu.parcifal.print.Print
 * @see eu.parcifal.print.Printable
 * 
 * @author Michaël van de Weerd
 * @version 10.05.2016
 * @since 28.04.2016
 */
public final class Printer {
    /**
     * The {@link eu.parcifal.print.Channel}s that are available to the current
     * {@link eu.parcifal.print.Printer}.
     */
    private final static Collection<Channel> CHANNELS = new ArrayList<Channel>();

    /**
     * Executes a {@link eu.parcifal.print.Print} containing the specified
     * {@link eu.parcifal.print.Printable} and {@eu.parcifal.print.Channel}s,
     * causing the Printable to be printed to each
     * {@link eu.parcifal.print.Channel}. Throws an
     * {@link eu.parcifal.print.ChannelNotAvailableException} if one or more of
     * the specified {@link eu.parcifal.print.Channel}s is not available to the
     * current {@link eu.parcifal.print.Printer}.
     * 
     * @param printable
     *            The {@link eu.parcifal.print.Printable} to be printed to the
     *            specified {@link eu.parcifal.print.Channel}s.
     * @param channels
     *            The {@link eu.parcifal.print.Channel}s to which the specified
     *            {@link eu.parcifal.print.Printable} must be printed.
     * @return The {@link eu.parcifal.print.Print} containing the specified
     *         {@link eu.parcifal.print.Printable} and the specified
     *         {@link eu.parcifal.print.Channel}s.
     */
    public final static Print executePrint(Printable printable, Collection<Channel> channels) {
        return compilePrint(printable, channels).execute();
    }

    /**
     * Compiles a {@link eu.parcifal.print.Print} containing the specified
     * {@link eu.parcifal.print.Printable} and
     * {@eu.parcifal.print.Channels. Throws an
     * {@link eu.parcifal.print.ChannelNotAvailableException} if one or more of
     * the specified {@link eu.parcifal.print.Channel}s is not available to the
     * current {@link eu.parcifal.print.Printer}.
     * 
     * @param printable
     *            The {@link eu.parcifal.print.Printable} to be printed to the
     *            specified {@link eu.parcifal.print.Channel}s.
     * @param channels
     *            The {@link eu.parcifal.print.Channel}s to which the specified
     *            {@link eu.parcifal.print.Printable} must be printed.
     * @return The {@link eu.parcifal.print.Print containing the specified
     *         {@link eu.parcifal.print.Printable} and the specified
     *         {@link eu.parcifal.print.Channel}s.
     */
    public final static Print compilePrint(Printable printable, Collection<Channel> channels) {
        Collection<Channel> acceptingChannels = new ArrayList<Channel>();

        for (Channel channel : channels) {
            if (!CHANNELS.contains(channel)) {
                throw new ChannelNotAvailableException(channel);
            } else if (channel.acceptsPrintable(printable)) {
                acceptingChannels.add(channel);
            }
        }

        return new Print(printable, acceptingChannels);
    }

    /**
     * Executes a {@link eu.parcifal.print.Print} containing the specified
     * {@link eu.parcifal.print.Printable} and all
     * {@link eu.parcifal.print.Channel}s available to the current
     * {@link eu.parcifal.print.Printer}, causing the
     * {@link eu.parcifal.print.Printable} to be printed to each
     * {@link eu.parcifal.print.Channel}.
     * 
     * @param printable
     *            The {@link eu.parcifal.print.Printable} to be printed to all
     *            {@link eu.parcifal.print.Channel}s available to the current
     *            {@link eu.parcifal.print.Printer}.
     * @return The {@link eu.parcifal.print.Print} containing the specified
     *         {@link eu.parcifal.print.Printable} and all
     *         {@link eu.parcifal.print.Channel}s available to the current
     *         {@link eu.parcifal.print.Printer}.
     */
    public final static Print executePrint(Printable printable) {
        return compilePrint(printable).execute();
    }

    /**
     * Compiles a {@link eu.parcifal.print.Print} containing the specified
     * {@link eu.parcifal.print.Printable} and all
     * {@link eu.parcifal.print.Channel}s available to the current
     * {@link eu.parcifal.print.Printer}.
     * 
     * @param printable
     *            The {@link eu.parcifal.print.Printable} to be printed to all
     *            {@link eu.parcifal.print.Channel}s available to the current
     *            {@link eu.parcifal.print.Printer}.
     * @return The {@link eu.parcifal.print.Print} containing the specified
     *         {@link eu.parcifal.print.Printable} and all
     *         {@link eu.parcifal.print.Channel}s available to the current
     *         {@link eu.parcifal.print.Printer}.
     */
    public final static Print compilePrint(Printable printable) {
        return compilePrint(printable, CHANNELS);
    }

    /**
     * Add the specified {@link eu.parcifal.print.Channel} to the current
     * {@link eu.parcifal.print.Printer}, making it available for printing.
     * 
     * @param channel
     *            The {@link eu.parcifal.print.Channel} to be added to the
     *            current {@link eu.parcifal.print.Printer}.
     */
    public final static void addChannel(Channel channel) {
        CHANNELS.add(channel);
    }

    /**
     * Remove the specified {@link eu.parcifal.print.Channel} from the current
     * {@link eu.parcifal.print.Printer}, making it unavailable for printing.
     * 
     * @param channel
     *            The {@link eu.parcifal.print.Channel} to be remove from the
     *            current {@link eu.parcifal.print.Printer}.
     */
    public final static void removeChannel(Channel channel) {
        CHANNELS.remove(channel);
    }

}
