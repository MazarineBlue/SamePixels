package org.mazarineblue.samepixels;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import static java.util.Arrays.*;

public class ImageUtil {
    public static BufferedImage compare(boolean activateOnIdenticalPixels, BufferedImage... images) {
        return compare(activateOnIdenticalPixels, 0x00FFFFFF, images);
    }

    private static BufferedImage compare(boolean activateOnIdenticalPixels, int mask, BufferedImage... images) {
        return new PixelComparator(activateOnIdenticalPixels, mask, images).compare();
    }

    private record PixelComparator(boolean activateOnIdenticalPixels, int mask, BufferedImage... images) {
        private BufferedImage compare() {
            int[][] rgb = getRgbArray();
            int[] scanSize = getScanSizes();
            int[] result = createResultArray();

            int w = stream(images).mapToInt(BufferedImage::getWidth).min().orElseThrow();
            int h = stream(images).mapToInt(BufferedImage::getHeight).min().orElseThrow();
            int n = images.length;
            for (int i = 0; i < w; ++i)
                for (int j = 0; j < h; ++j) {
                    boolean flag = true;
                    for (int k = 0; k < n && flag; ++k)
                        for (int l = 0; l < n; ++l)
                            if (k != l)
                                if ((rgb[k][i + j * scanSize[k]] & mask) == (rgb[l][i + j * scanSize[l]] & mask) != activateOnIdenticalPixels) {
                                    flag = false;
                                    break;
                                }
                    if (flag)
                        result[i + j * w] = rgb[0][i + j * scanSize[0]];
                }
            return toImage(result, w, h);
        }

        private int[][] getRgbArray() {
            int[][] rgb = new int[images.length][];
            setAll(rgb, i -> getRgb(images[i]));
            return rgb;
        }

        private static int[] getRgb(BufferedImage image) {
            int w = image.getWidth();
            int[] arr = new int[w * image.getHeight()];
            image.getRGB(0, 0, w, image.getHeight(), arr, 0, w);
            return arr;
        }

        private int[] getScanSizes() {
            int[] arr = new int[images.length];
            setAll(arr, i -> images[i].getWidth());
            return arr;
        }

        private int[] createResultArray() {
            return createArray(
                    stream(images).mapToInt(BufferedImage::getWidth).max().orElseThrow(),
                    stream(images).mapToInt(BufferedImage::getHeight).max().orElseThrow(),
                    0);
        }

        private static int[] createArray(int w, int h, int background) {
            int[] arr = new int[w * h];
            Arrays.fill(arr, background);
            return arr;
        }

        private BufferedImage toImage(int[] arr, int w, int h) {
            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            image.setRGB(0, 0, w, h, arr, 0, w);
            return image;
        }
    }

    static void write(BufferedImage image, String format, OutputStream out) throws IOException {
        for (int i : toArray(image, format))
            out.write(i);
        out.close();
    }

    private static byte[] toArray(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(image.getWidth() * image.getHeight());
        ImageIO.write(image, format, out);
        return out.toByteArray();
    }
}
