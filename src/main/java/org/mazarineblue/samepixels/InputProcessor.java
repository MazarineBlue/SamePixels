package org.mazarineblue.samepixels;

import org.apache.commons.cli.*;

import java.io.*;
import java.util.*;

class InputProcessor {
    public enum Operation {
        SAME, DIFF, HELP, ERROR
    }

    final Operation operation;
    final String[] arguments;

    public InputProcessor(String... args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        CommandLine line = parser.parse(options(), args);
        arguments = line.getArgs();
        operation = line.hasOption("help") || arguments.length < 2 ? Operation.HELP
                : line.hasOption("diff") ? Operation.DIFF
                : Operation.SAME;
    }

    private InputProcessor(Operation operation) {
        this.operation = operation;
        this.arguments = new String[0];
    }

    public InputProcessor set(IOException e) {
        return new InputProcessor(Operation.ERROR);
    }

    public static void printHelp() {
        new HelpFormatter()
                .printHelp("samepixels [options] file1 file2 [file3...] > file", options());
    }

    private static Options options() {
        Options options = new Options();
        options.addOption(null, "help", false, "print this message");
        options.addOption(null, "diff", false, "compare both images and write different pixels to stdout");
        options.addOption(null, "same", false, "compare both images and write identical pixels to stdout");
        return options;
    }
}
