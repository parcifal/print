package eu.parcifal.print;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Gives access to the configuration file, defining the debug mode of the
 * Console and the Logs that will be used.
 * 
 * @see eu.parcifal.print.Console
 * @see eu.parcifal.print.Log
 * 
 * @author Michaël van de Weerd
 * @version 15.05.2016
 * @since 11.05.2016
 */
class Configuration {

    /**
     * The location of the file containing custom configuration settings.
     */
    private final static String LOCATION = "./cfg/print.xml";

    /**
     * The document representing the configuration file.
     */
    private final static Document DOCUMENT;

    /**
     * The XPath object to be used for navigating the configuration document.
     */
    private final static XPath X_PATH;

    /**
     * Indicates whether or not the configuration file is available.
     */
    final static boolean AVAILABLE;

    static {
        try {
            File file = new File(LOCATION);

            AVAILABLE = file.exists();

            if (AVAILABLE) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

                factory.setIgnoringElementContentWhitespace(true);

                DocumentBuilder builder = factory.newDocumentBuilder();

                builder.setEntityResolver(new Resolver());

                FileInputStream inputStream = new FileInputStream(file);

                DOCUMENT = builder.parse(inputStream);
                DOCUMENT.normalize();

                X_PATH = XPathFactory.newInstance().newXPath();
            } else {
                DOCUMENT = null;
                X_PATH = null;
            }
        } catch (Exception exception) {
            throw new Error(exception);
        }
    }

    /**
     * Evaluate an x-path expression on the current {@link org.w3c.dom.Document}
     * and return the result.
     * 
     * @param expression
     *            The x-path expression to be evaluated.
     * @param type
     *            The type to be returned.
     * @return The result of the evaluation of the specified x-path expression
     *         on the current {@link org.w3c.dom.Document}.
     * @throws XPathExpressionException
     *             Thrown if the specified x-path expression is invalid.
     */
    static Object evaluate(String expression, QName type) throws XPathExpressionException {
        return X_PATH.evaluate(expression, DOCUMENT, type);
    }

    /**
     * Provides the XML parser with the location of the DTD file, used to
     * validate the contents of the configuration file.
     * 
     * @author Michaël van de Weerd
     * @version 15.05.2016
     * @since 15.05.2016
     */
    private static class Resolver implements EntityResolver {

        /**
         * The name of the DTD file.
         */
        private final static String FILE_NAME = "eu.parcifal.print.dtd";

        @Override
        public InputSource resolveEntity(String publicID, String systemID) throws SAXException, IOException {
            if (systemID.contains(FILE_NAME)) {
                return new InputSource(this.getClass().getResourceAsStream("/" + FILE_NAME));
            } else {
                return null;
            }
        }

    }

}
