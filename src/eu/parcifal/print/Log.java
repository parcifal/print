package eu.parcifal.print;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Prints to a file in a CSV format. Depending on the current configuration
 * message might or might not be written to a log file.
 * 
 * @author Michaël van de Weerd
 * @since 10.05.2016
 * @version 12.05.2016
 */
public class Log {

    /**
     * The format of a line written to the log files.
     */
    private final static String LINE_FORMAT = "%1$s;%2$tY;%2$tm;%2$td;%2$tH;%2$tM;%2$tS;%2$tL;%3$s;%4$s;%5$s;%6$d;\"%7$s\"\r\n";

    /**
     * The {@link eu.parcifal.print.Channel}s to which the current
     * {@link eu.parcifal.print.Log} writes.
     */
    private static Collection<Channel> LOG_CHANNELS = new ArrayList<Channel>();

    static {
        if (Configuration.AVAILABLE) {
            NodeList logNodes;

            try {
                logNodes = (NodeList) Configuration.evaluate("/printer/log", XPathConstants.NODESET);
            } catch (XPathExpressionException exception) {
                throw new Error(exception);
            }

            for (int i = 0; i < logNodes.getLength(); i++) {
                Element log = (Element) logNodes.item(i);

                try {
                    File file = new File(String.format(log.getAttribute("location"), LocalDateTime.now()));

                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }

                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    Channel channel = new Channel("eu.parcifal.print.log+" + file.getPath(),
                            new FileOutputStream(file)) {

                        private String tagPattern = log.getAttribute("tag");

                        private String classNamePattern = log.getAttribute("class-name");

                        private String methodNamePattern = log.getAttribute("method-name");

                        private String fileNamePattern = log.getAttribute("file-name");

                        @Override
                        public boolean acceptsPrintable(Printable printable) {
                            if (this.tagPattern.length() > 0 && !Pattern.matches(this.tagPattern, printable.getTag())) {
                                return false;
                            }

                            if (this.classNamePattern.length() > 0
                                    && !Pattern.matches(this.classNamePattern, printable.getSourceClassName())) {
                                return false;
                            }

                            if (this.methodNamePattern.length() > 0
                                    && !Pattern.matches(this.methodNamePattern, printable.getSourceMethodName())) {
                                return false;
                            }

                            if (this.fileNamePattern.length() > 0
                                    && !Pattern.matches(this.fileNamePattern, printable.getSourceFileName())) {
                                return false;
                            }

                            return true;
                        }

                        @Override
                        protected String format(Printable printable) {
                            return String.format(LINE_FORMAT, printable.getTag(), printable.getDateTime(),
                                    printable.getSourceClassName(), printable.getSourceMethodName(),
                                    printable.getSourceFileName(), printable.getSourceLineNumber(),
                                    printable.getContent().replaceAll("\"", "\\\\\"").replaceAll("\r", "\\\\r")
                                            .replaceAll("\n", "\\\\n"));
                        }

                    };

                    LOG_CHANNELS.add(channel);

                    Printer.addChannel(channel);
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                    throw new Error(exception);
                }
            }
        }
    }

    /**
     * Write the specified message to the {@link eu.parcifal.print.Channel}s of
     * the current {@link eu.parcifal.print.Log}.
     * 
     * @param tag
     *            The tag of the specified message.
     * @param message
     *            The message to be written to the log file.
     * @return The {@link eu.parcifal.print.Print} containing the written
     *         {@link eu.parcifal.print.Printable} and the
     *         {@link eu.parcifal.print.Channel}s to which it is written.
     */
    public final static Print write(String tag, String message) {
        return Printer.executePrint(new Printable(tag, message), LOG_CHANNELS);
    }

    /**
     * Write the specified formatted message to the
     * {@link eu.parcifal.print.Channel}s of the current
     * {@link eu.parcifal.print.Log}.
     * 
     * @param tag
     *            The tag of the specified message.
     * @param message
     *            The message to be written to the log file.
     * @param arguments
     *            The arguments used to format the specified message.
     * @return The {@link eu.parcifal.print.Print} containing the written
     *         {@link eu.parcifal.print.Printable} and the
     *         {@link eu.parcifal.print.Channel}s to which it is written.
     */
    public final static Print write(String tag, String message, Object... attributes) {
        return write(tag, String.format(message, attributes));
    }

}
