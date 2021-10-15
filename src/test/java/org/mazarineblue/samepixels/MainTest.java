package org.mazarineblue.samepixels;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.nio.charset.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    private PrintStream bak;
    private ByteArrayOutputStream out;

    @BeforeEach
    public void setup() {
        bak = System.out;
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    public void teardown() {
        System.setOut(bak);
    }

    @Test
    @DisplayName ("The help message is printed to stdout when the --argument is provided")
    public void help() {
        Main.main("--help");

        String s = out.toString(StandardCharsets.UTF_8);
        assertTrue(s.contains("--help"));
        assertTrue(s.contains("samepixels [options] file1 file2 [file3...] > file"));
        assertTrue(s.contains("print this message"));
        assertTrue(s.contains("--diff"));
        assertTrue(s.contains("compare both images and write different pixels to stdout"));
        assertTrue(s.contains("--same"));
        assertTrue(s.contains("compare both images and write identical pixels to stdout"));
    }

    @Test
    @DisplayName ("The help information is printed when no arguments are provided")
    public void noArguments() {
        Main.main();

        String s = out.toString(StandardCharsets.UTF_8);
        assertTrue(s.contains("--help"));
        assertTrue(s.contains("samepixels [options] file1 file2 [file3...] > file"));
        assertTrue(s.contains("print this message"));
        assertTrue(s.contains("--diff"));
        assertTrue(s.contains("compare both images and write different pixels to stdout"));
        assertTrue(s.contains("--same"));
        assertTrue(s.contains("compare both images and write identical pixels to stdout"));
    }

    @Test
    @DisplayName ("The help information is printed when one argument is provided")
    public void oneArgument() {
        Main.main("./build/resources/test/red.png");

        String s = out.toString(StandardCharsets.UTF_8);
        assertTrue(s.contains("--help"));
        assertTrue(s.contains("samepixels [options] file1 file2 [file3...] > file"));
        assertTrue(s.contains("print this message"));
        assertTrue(s.contains("--diff"));
        assertTrue(s.contains("compare both images and write different pixels to stdout"));
        assertTrue(s.contains("--same"));
        assertTrue(s.contains("compare both images and write identical pixels to stdout"));
    }

    @Test
    @DisplayName ("The error information is printed when one argument is provided that doesn't exists")
    public void foo() {
        Main.main("--foo");

        String s = out.toString(StandardCharsets.UTF_8);
        assertTrue(s.contains("Malformed command. Reason:"));
        assertTrue(s.contains("--foo"));
        assertTrue(s.contains("--help"));
        assertTrue(s.contains("samepixels [options] file1 file2 [file3...] > file"));
        assertTrue(s.contains("print this message"));
        assertTrue(s.contains("--diff"));
        assertTrue(s.contains("compare both images and write different pixels to stdout"));
        assertTrue(s.contains("--same"));
        assertTrue(s.contains("compare both images and write identical pixels to stdout"));
    }

    @Test
    @DisplayName ("The error information is printed when one argument is provided that doesn't exists")
    public void notExisting() {
        Main.main("./build/resources/test/non-existing.png", "./build/resources/test/non-existing.png");

        String s = out.toString(StandardCharsets.UTF_8);
        assertTrue(s.contains("--help"));
        assertTrue(s.contains("samepixels [options] file1 file2 [file3...] > file"));
        assertTrue(s.contains("print this message"));
        assertTrue(s.contains("--diff"));
        assertTrue(s.contains("compare both images and write different pixels to stdout"));
        assertTrue(s.contains("--same"));
        assertTrue(s.contains("compare both images and write identical pixels to stdout"));
    }

    @ParameterizedTest
    @MethodSource ("validData")
    @DisplayName ("Given these images, when compared, then the output image must be...")
    public void comparingImages(String[] input, int[] expected) throws IOException {
        Main.main(input);
        BufferedImage result = ImageIO.read(new ByteArrayInputStream(out.toByteArray()));
        int[] actual = getRGB(result, 2, 1);
        assertArrayEquals(expected, actual);
    }

    private int[] getRGB(BufferedImage result, int w, int h) {
        int[] actual = new int[2];
        result.getRGB(0, 0, w, h, actual, 0, w);
        return actual;
    }

    static Stream<Arguments> validData() {
        return Stream.of(
                Arguments.of(
                        new String[]{"--diff", "./build/resources/test/red.png", "./build/resources/test/red.png"},
                        new int[]{0x00000000, 0x00000000}),
                Arguments.of(
                        new String[]{"--diff", "./build/resources/test/red.png", "./build/resources/test/red_and_blue.png"},
                        new int[]{0x00000000, 0xFFFF0000}),
                Arguments.of(
                        new String[]{"--diff", "./build/resources/test/red.png", "./build/resources/test/blue.png"},
                        new int[]{0xFFFF0000, 0xFFFF0000}),
                Arguments.of(
                        new String[]{"--diff", "./build/resources/test/red.png", "./build/resources/test/red.png", "./build/resources/test/red_and_blue.png"},
                        new int[]{0x00000000, 0x00000000}),
                Arguments.of(
                        new String[]{"--diff", "./build/resources/test/red.png", "./build/resources/test/red.png", "./build/resources/test/blue.png"},
                        new int[]{0x00000000, 0x00000000}),
                Arguments.of(
                        new String[]{"--diff", "./build/resources/test/red.png", "./build/resources/test/red_and_blue.png", "./build/resources/test/red_and_blue.png"},
                        new int[]{0x00000000, 0x00000000}),
                Arguments.of(
                        new String[]{"--diff", "./build/resources/test/red.png", "./build/resources/test/red_and_blue.png", "./build/resources/test/blue.png"},
                        new int[]{0x00000000, 0x00000000}),
                Arguments.of(
                        new String[]{"--diff", "./build/resources/test/red.png", "./build/resources/test/blue.png", "./build/resources/test/red_and_blue.png"},
                        new int[]{0x00000000, 0x00000000}),
                Arguments.of(
                        new String[]{"--diff", "./build/resources/test/red.png", "./build/resources/test/blue.png", "./build/resources/test/blue.png"},
                        new int[]{0x00000000, 0x00000000}),
                Arguments.of(
                        new String[]{"--same", "./build/resources/test/red.png", "./build/resources/test/red.png"},
                        new int[]{0xFFFF0000, 0xFFFF0000}),
                Arguments.of(
                        new String[]{"--same", "./build/resources/test/red.png", "./build/resources/test/red_and_blue.png"},
                        new int[]{0xFFFF0000, 0x00000000}),
                Arguments.of(
                        new String[]{"--same", "./build/resources/test/red.png", "./build/resources/test/blue.png"},
                        new int[]{0x00000000, 0x00000000}),
                Arguments.of(
                        new String[]{"--same", "./build/resources/test/red.png", "./build/resources/test/red.png", "./build/resources/test/red_and_blue.png"},
                        new int[]{0xFFFF0000, 0x00000000}),
                Arguments.of(
                        new String[]{"--same", "./build/resources/test/red.png", "./build/resources/test/red.png", "./build/resources/test/blue.png"},
                        new int[]{0x00000000, 0x00000000}),
                Arguments.of(
                        new String[]{"--same", "./build/resources/test/red.png", "./build/resources/test/red_and_blue.png", "./build/resources/test/red_and_blue.png"},
                        new int[]{0xFFFF0000, 0x00000000}),
                Arguments.of(
                        new String[]{"--same", "./build/resources/test/red.png", "./build/resources/test/red_and_blue.png", "./build/resources/test/blue.png"},
                        new int[]{0x00000000, 0x00000000}),
                Arguments.of(
                        new String[]{"--same", "./build/resources/test/red.png", "./build/resources/test/blue.png", "./build/resources/test/red_and_blue.png"},
                        new int[]{0x00000000, 0x00000000}),
                Arguments.of(
                        new String[]{"--same", "./build/resources/test/red.png", "./build/resources/test/blue.png", "./build/resources/test/blue.png"},
                        new int[]{0x00000000, 0x00000000}));
    }
}
