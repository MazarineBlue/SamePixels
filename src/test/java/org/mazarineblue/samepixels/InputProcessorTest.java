package org.mazarineblue.samepixels;

import org.apache.commons.cli.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class InputProcessorTest {
    @Test
    @DisplayName ("The operation is set to HELP when no arguments are provided")
    public void noArguments() throws ParseException {
        InputProcessor processor = new InputProcessor();
        assertEquals(InputProcessor.Operation.HELP, processor.operation);
        assertEquals(0, processor.arguments.length);
    }

    @Test
    @DisplayName ("The operation is set to HELP when one argument is provided")
    public void oneArgument() throws ParseException {
        InputProcessor processor = new InputProcessor("foo");
        assertEquals(InputProcessor.Operation.HELP, processor.operation);
        assertEquals(1, processor.arguments.length);
        assertEquals("foo", processor.arguments[0]);
    }

    @Test
    public void error() throws ParseException {
        InputProcessor processor = new InputProcessor("foo", "bar").set(new IOException());
        assertEquals(InputProcessor.Operation.ERROR, processor.operation);
        assertEquals(0, processor.arguments.length);
    }

    @Test
    @DisplayName ("The operation is set to HELP when the help option is used")
    public void help() throws ParseException {
        InputProcessor processor = new InputProcessor("--help", "foo", "bar");
        assertEquals(InputProcessor.Operation.HELP, processor.operation);
        assertEquals(2, processor.arguments.length);
        assertEquals("foo", processor.arguments[0]);
        assertEquals("bar", processor.arguments[1]);
    }

    @Test
    @DisplayName ("The operation is set to DIFF when the diff option is used")
    public void diff() throws ParseException {
        InputProcessor processor = new InputProcessor("--diff", "foo", "bar");
        assertEquals(InputProcessor.Operation.DIFF, processor.operation);
        assertEquals(2, processor.arguments.length);
        assertEquals("foo", processor.arguments[0]);
        assertEquals("bar", processor.arguments[1]);
    }

    @Test
    @DisplayName ("The operation is set to SAME when the same option is used")
    public void same() throws ParseException {
        InputProcessor processor = new InputProcessor("--same", "foo", "bar");
        assertEquals(InputProcessor.Operation.SAME, processor.operation);
        assertEquals(2, processor.arguments.length);
        assertEquals("foo", processor.arguments[0]);
        assertEquals("bar", processor.arguments[1]);
    }

    @Test
    public void twoArguments() throws ParseException {
        InputProcessor processor = new InputProcessor("foo", "bar");
        assertEquals(InputProcessor.Operation.SAME, processor.operation);
        assertEquals(2, processor.arguments.length);
        assertEquals("foo", processor.arguments[0]);
        assertEquals("bar", processor.arguments[1]);
    }
}
