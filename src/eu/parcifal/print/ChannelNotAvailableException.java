package eu.parcifal.print;

/**
 * Thrown if the current {@link eu.parcifal.print.Printer} is tasked with
 * printing to a {@link eu.parcifal.print.Channel} that is not available to it.
 * 
 * @see eu.parcifal.print.Printer
 * @see eu.parcifal.print.Channel
 * 
 * @author Michaël van de Weerd
 * @version 15.05.2016
 * @since 10.05.2016
 */
public class ChannelNotAvailableException extends IllegalArgumentException {

    /**
     * The serial-version-UID of the current
     * {@link eu.parcifal.print.ChannelNotAvailableException} to be used for
     * serialisation.
     */
    private static final long serialVersionUID = -4907040961384580400L;

    /**
     * The (unformatted) message to be contained in the current
     * {@link eu.parcifal.print.ChannelNotAvailableException}.
     */
    private static final String EXCEPTION_MESSAGE = "Channel with name \"%1$s\" is not available to the current printer.";

    /**
     * The {@link eu.parcifal.print.Channel} that caused the current
     * {@link eu.parcifal.print.ChanelNotAvailableException} to be thrown.
     */
    private Channel channel;

    /**
     * Construct a new {@link eu.parcifal.print.ChannelNotAvailableException}
     * containing the {@link eu.parcifal.print.Channel} that caused it to be
     * thrown.
     * 
     * @param channel
     *            The {@link eu.parcifal.print.Channel} that caused the new
     *            {@link eu.parcifal.print.ChannelNotAvailableException} to be
     *            thrown.
     */
    public ChannelNotAvailableException(Channel channel) {
        super(String.format(EXCEPTION_MESSAGE, channel.getChannelName()));

        this.channel = channel;
    }

    /**
     * Return the {@link eu.parcifal.print.Channel} that caused the current
     * {@link eu.parcifal.print.ChannelNotAvailableException} to be thrown.
     * 
     * @return The {@link eu.parcifal.print.Channel} that caused the current
     *         {@link eu.parcifal.print.ChannelNotAvailableException} to be
     *         thrown.
     */
    public Channel getChannel() {
        return this.channel;
    }

}
