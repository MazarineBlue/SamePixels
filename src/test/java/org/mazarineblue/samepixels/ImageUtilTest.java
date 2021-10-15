package org.mazarineblue.samepixels;

import org.junit.jupiter.api.*;

import java.awt.image.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ImageUtilTest {
    @Nested
    @DisplayName ("When comparing two images")
    public class Comparing {
        @Nested
        @DisplayName ("And looking for identical pixels")
        public class LookingForIdenticalPixels {
            @Test
            @DisplayName ("A transparent image is returned when comparing two images with different pixels")
            public void diffImages() {
                BufferedImage a = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage b = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                a.setRGB(0, 0, 0x00FF0000);
                b.setRGB(0, 0, 0x0000FF00);
                BufferedImage result = ImageUtil.compare(true, a, b);
                assertEquals(1, result.getWidth());
                assertEquals(1, result.getHeight());
                assertEquals(0x00000000, result.getRGB(0, 0));
            }

            @Test
            @DisplayName ("A identical image is returned when comparing two identical images")
            public void identicalImages() {
                BufferedImage a = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage b = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                a.setRGB(0, 0, 0x00FF0000);
                b.setRGB(0, 0, 0x00FF0000);
                BufferedImage result = ImageUtil.compare(true, a, b);
                assertEquals(1, result.getWidth());
                assertEquals(1, result.getHeight());
                assertEquals(0x00FF0000, result.getRGB(0, 0));
            }

            @Test
            @DisplayName ("A identical image is returned when comparing two identical images")
            public void diffImages2() {
                BufferedImage a = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage b = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage c = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                a.setRGB(0, 0, 0x00FF0000);
                b.setRGB(0, 0, 0x00FF0000);
                c.setRGB(0, 0, 0x0000FF00);
                BufferedImage result = ImageUtil.compare(true, a, b, c);
                assertEquals(1, result.getWidth());
                assertEquals(1, result.getHeight());
                assertEquals(0x00000000, result.getRGB(0, 0));
            }

            @Test
            @DisplayName ("A identical image is returned when comparing two identical images")
            public void identicalImages2() {
                BufferedImage a = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage b = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage c = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                a.setRGB(0, 0, 0x00FF0000);
                b.setRGB(0, 0, 0x00FF0000);
                c.setRGB(0, 0, 0x00FF0000);
                BufferedImage result = ImageUtil.compare(true, a, b, c);
                assertEquals(1, result.getWidth());
                assertEquals(1, result.getHeight());
                assertEquals(0x00FF0000, result.getRGB(0, 0));
            }
        }

        @Nested
        @DisplayName ("And looking for different pixels")
        public class LookingForDifferentPixels {
            @Test
            @DisplayName ("A transparent image is returned when comparing two identical images")
            public void identicalImages() {
                BufferedImage a = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage b = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                a.setRGB(0, 0, 0x00FF0000);
                b.setRGB(0, 0, 0x00FF0000);
                BufferedImage result = ImageUtil.compare(false, a, b);
                assertEquals(1, result.getWidth());
                assertEquals(1, result.getHeight());
                assertEquals(0x00000000, result.getRGB(0, 0));
            }

            @Test
            @DisplayName ("The first image is returned when comparing two completely different images")
            public void diffImages() {
                BufferedImage a = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage b = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                a.setRGB(0, 0, 0x00FF0000);
                b.setRGB(0, 0, 0x0000FF00);
                BufferedImage result = ImageUtil.compare(false, a, b);
                assertEquals(1, result.getWidth());
                assertEquals(1, result.getHeight());
                assertEquals(0x00FF0000, result.getRGB(0, 0));
            }

            @Test
            @DisplayName ("The first image is returned when comparing two completely different images")
            public void identicalImages2() {
                BufferedImage a = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage b = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage c = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                a.setRGB(0, 0, 0x00FF0000);
                b.setRGB(0, 0, 0x0000FF00);
                c.setRGB(0, 0, 0x0000FF00);
                BufferedImage result = ImageUtil.compare(false, a, b, c);
                assertEquals(1, result.getWidth());
                assertEquals(1, result.getHeight());
                assertEquals(0x00000000, result.getRGB(0, 0));
            }

            @Test
            @DisplayName ("The first image is returned when comparing two completely different images")
            public void diffImages2() {
                BufferedImage a = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage b = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage c = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                a.setRGB(0, 0, 0x00FF0000);
                b.setRGB(0, 0, 0x0000FF00);
                c.setRGB(0, 0, 0x000000FF);
                BufferedImage result = ImageUtil.compare(false, a, b, c);
                assertEquals(1, result.getWidth());
                assertEquals(1, result.getHeight());
                assertEquals(0x00FF0000, result.getRGB(0, 0));
            }
        }

        @Nested
        @DisplayName ("And testing preconditions")
        public class Precondition {
            @Test
            @DisplayName ("A image with the minimum dimensions is returned when comparing to images with different width")
            public void diffWidth() {
                BufferedImage a = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage b = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);
                a.setRGB(0, 0, 0x00FF0000);
                b.setRGB(0, 0, 0x00FF0000);
                b.setRGB(1, 0, 0x00FF0000);
                BufferedImage result = ImageUtil.compare(true, a, b);
                assertEquals(1, result.getWidth());
                assertEquals(1, result.getHeight());
            }

            @Test
            @DisplayName ("A image with the minimum dimensions is returned when comparing to images with different height")
            public void diffHeight() {
                BufferedImage a = new BufferedImage(1, 2, BufferedImage.TYPE_INT_ARGB);
                BufferedImage b = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                a.setRGB(0, 0, 0x00FF0000);
                a.setRGB(0, 1, 0x00FF0000);
                b.setRGB(0, 0, 0x00FF0000);
                BufferedImage result = ImageUtil.compare(true, a, b);
                assertEquals(1, result.getWidth());
                assertEquals(1, result.getHeight());
            }

            @Test
            @DisplayName ("A identical image is returned when comparing two images with different image types")
            public void diffImageType() {
                BufferedImage a = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                BufferedImage b = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
                a.setRGB(0, 0, 0x00FF0000);
                b.setRGB(0, 0, 0x00FF0000);
                BufferedImage result = ImageUtil.compare(true, a, b);
                assertEquals(1, result.getWidth());
                assertEquals(1, result.getHeight());
                assertEquals(0x00FF0000, result.getRGB(0, 0));
            }
        }
    }

    @Nested
    @DisplayName ("When writing an image")
    public class Writing {
        @Test
        @DisplayName("Then something is written to the output stream")
        public void test() throws IOException {
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            image.setRGB(0,0, 0x000000FF);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageUtil.write(image, "PNG", out);
            assertNotNull(out.toByteArray());
        }
    }
}
