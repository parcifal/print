# PRINT

The JAVA PRINT API provides easy access to the console and log files, enabling developers to print notes, warnings, errors, or custom messages with minimal effort. Using an XML configuration file, log files can be defined for ordered output filtered by tag, class-name, method-name and file-name.

## Configuring PRINT

The PRINT API can be configured using a file with the path "cfg/print.xml" in the root of your JAVA project. For an example configuration file, check out [/parcifal/print/example/print.xml](https://github.com/parcifal/print/blob/master/example/print.xml). See the following DTD notation (or [/print/src/eu.parcifal.print.dtd](https://github.com/parcifal/print/blob/master/eu.parcifal.print.dtd)) for the definition of print.xml.

    <!--
       - The print.xml file contains a PRINTER root element that has zero or
       - one CONSOLE elements and zero or more LOG elements as children. 
       - PRINTER should define the https://print.parcifal.eu/2016 namespace 
       - using the XMLNS attribute.
       -->
    <!ELEMENT printer (console?, log*) >
    <!ATTLIST printer xmlns CDATA #REQUIRED >
    
    <!--
       - The CONSOLE element does not contain any content and only has a single 
       - attribute, DEBUG. DEBUG can be either "true" or "false", is "false" by 
       - default and controls whether or not the 
       - eu.parcifal.print.Console#debug(String) method will result in a print 
       - or not.
       -->
    <!ELEMENT console EMPTY >
    <!ATTLIST console debug (true|false) "false" >
    
    <!--
       - The LOG element does not contain any content and can have a maximum of 
       - five attributes of which four are optional.
       - 
       - The fifth attribute, LOCATION, defines the location of the log file, 
       - relative of the root of the current JAVA application. The value of 
       - this attribute is formatted using the java.lang.String#format(String) 
       - method, having a LocalDateTime object containing the date-time at 
       - which the log file has been created as the first argument. Check out 
       - https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html for 
       - more information on how to reference the arguments and using its 
       - values to define the log file-name.
       - 
       - The TAG, CLASS-NAME, METHOD-NAME and FILE-NAME attributes are used to 
       - define the filter applied to all log writes called by the current LOG. 
       - Only writes that match the defined regular expression filters are 
       - written to the log file of the current LOG. Check out 
       - https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html 
       - for more information on REGEX in JAVA.
       -->
    <!ELEMENT log EMPTY >
    <!ATTLIST log tag CDATA #IMPLIED
                  class-name CDATA #IMPLIED
                  method-name CDATA #IMPLIED
                  file-name CDATA #IMPLIED 
                  location CDATA #REQUIRED >

## Using PRINT

An effort has been made to reduce the steps needed for using PRINT in JAVA code to the bare minimum.

### The CONSOLE

The CONSOLE class, requiring the import of `eu.parcifal.print.Console`, contains several static method meant for different situations. The output of these method will always contain a header consisting of the TAG, defined by the method used, the date-time at which the method was called and the location at which the method was called, followed by all lines of the actual message provided to the method. The NOTE, WARNING and DEBUG methods all have a second variant, providing the possibility to add one or more arguments used for formatting the actual message. The formatting syntax is identical to that of the `java.lang.String#format(String, Object...)` method. Check out https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html for more information on formatting strings.

The following example code demonstrates some uses of the methods in both the CONSOLE class and the LOG class, followed by the resulting output. Assume that all method calls are made at exactly 12 'o clock on the first of January in the year 2000 and the JAVA application is configured using the example configuration file at [/print/example/print.xml](https://github.com/parcifal/print/blob/master/example/print.xml).

    package eu.parcifal.print.demo;
    
    import eu.parcifal.print.Console;
    import eu.parcifal.print.Log;
    
    public class Demo {
        
        public void main(String... arguments) {
        
            Console.note("Hello PRINT!");
            
            /*
             * NOTE    [ 2000.01.01 12:00:00.000 ] eu.parcifal.print.demo.Demo#main (demo.java:10)
             *  : Hello PRINT!
             */
            
            Console.note("Hello $1%s!", "CONSOLE");
            
            /*
             * NOTE    [ 2000.01.01 12:00:00.000 ] eu.parcifal.print.demo.Demo#main (demo.java:17)
             *  : Hello CONSOLE!
             */
            
            Console.warning("Warn PRINT!");
            
            /*
             * WARNING [ 2000.01.01 12:00:00.000 ] eu.parcifal.print.demo.Demo#main (demo.java:24)
             *  ! Warn PRINT!
             */
            
            Console.warning("Warn %1$s!", "CONSOLE");
            
            /*
             * WARNING [ 2000.01.01 12:00:00.000 ] eu.parcifal.print.demo.Demo#main (demo.java:31)
             *  ! Warn CONSOLE!
             */
            
            try {
              throw new Exception("Help PRINT!");
            } catch(Exception exception) {
              Console.error(exception);
            }
            
            /*
             * ERROR   [ 2000.01.01 12:00:00.000 ] eu.parcifal.print.demo.Demo#main (demo.java:41)
             *  : Help PRINT (Exception)
             *  @ eu.parcifal.print.demo.Demo#main (demo.java:39)
             */
            
            Console.debug("Debug PRINT!");
            
            /*
             * DEBUG   [ 2000.01.01 12:00:00.000 ] eu.parcifal.print.demo.Demo#main (demo.java:50)
             *  > Debug PRINT!
             */
            
            Console.debug("Debug %1$s!", "CONSOLE");
            
            /*
             * DEBUG   [ 2000.01.01 12:00:00.000 ] eu.parcifal.print.demo.Demo#main (demo.java:57)
             *  > Debug CONSOLE!
             */
            
            Log.write("demo", "Hello PRINT!");
            
            /*
             * demo; 2000; 01; 01; 12; 00; 00; 000; eu.parcifal.print.demo.Demo; main; demo.java; 64; "Debug CONSOLE!"
             */
            
            Log.write("demo", "Hello %1$s!", "LOG");
            
            /*
             * demo; 2000; 01; 01; 12; 00; 00; 000; eu.parcifal.print.demo.Demo; main; demo.java;  71; "Debug LOG!"
             */
        
        }
        
    }
