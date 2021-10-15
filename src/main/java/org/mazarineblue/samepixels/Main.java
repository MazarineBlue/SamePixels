package org.mazarineblue.samepixels;

import org.apache.commons.cli.*;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class Main {
    public static void main(String... args) {
        try {
            InputProcessor input = new InputProcessor(args);
            switch (input.operation) {
                case SAME, DIFF -> input = process(input);
            }
            switch (input.operation) {
                case ERROR, HELP -> InputProcessor.printHelp();
            }
        } catch (ParseException e) {
            System.out.println("Malformed command. Reason: " + e.getMessage());
            InputProcessor.printHelp();
        }
    }

    private static InputProcessor process(InputProcessor input) {
        try {
            BufferedImage[] images = new BufferedImage[input.arguments.length];
            for (int i = 0, n = images.length; i < n; ++i)
                images[i] = ImageIO.read(new File(input.arguments[i]));
            boolean activateOnIdenticalPixels = input.operation == InputProcessor.Operation.SAME;
            BufferedImage result = ImageUtil.compare(activateOnIdenticalPixels, images);

//            BufferedImage a = ImageIO.read(new File(input.arguments[0]));
//            BufferedImage b = ImageIO.read(new File(input.arguments[1]));
//            boolean activateOnIdenticalPixels = input.operation == InputProcessor.Operation.SAME;
//            BufferedImage result = ImageUtil.compare(activateOnIdenticalPixels, a, b);

            ImageUtil.write(result, "PNG", System.out);
            return input;
        } catch (IOException e) {
            return input.set(e);
        }
    }
}
