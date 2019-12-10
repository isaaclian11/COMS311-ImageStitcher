package pa2;

/**
 * @author Isaac Sanga and Justin Worley
 */

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ImageProcessor {

    public static Picture reduceWidth(int x, String inputImage){
        Picture picture = new Picture(inputImage);

        try {
            if (x >= picture.width()) {
               throw new ArrayIndexOutOfBoundsException();
            }

            for (int c = 0; c < x; c++) {
                int m = picture.height();
                int n = picture.width();
                int[][] matrix = new int[m][n];
                for (int j = 0; j < m; j++) {
                    for (int i = 0; i < n; i++) {
                        Color a = picture.get(i, j);
                        Color b;
                        if (i == 0) {
                            b = picture.get(i + 1, j);
                        } else if (i == n - 1) {
                            b = picture.get(i - 1, j);
                        } else {
                            a = picture.get(i - 1, j);
                            b = picture.get(i + 1, j);
                        }
                        int dist = pixelDistance(a, b);
                        matrix[j][i] = dist;
                    }
                }
                ArrayList<Tuple> tuples = MatrixCuts.widthCut(matrix);
                Picture newPicture = new Picture(picture.width() - 1, picture.height());

                for (int r = 0; r < picture.height(); r++) {
                    for (int o = 0; o < picture.width() - 1; o++) {
                        if (0 == tuples.get(r + 1).getY()) {
                            newPicture.setRGB(o, r, picture.getRGB(o + 1, r));
                        } else {
                            newPicture.setRGB(o, r, picture.getRGB(o, r));
                        }

                    }
                }
                picture = newPicture;
            }
        }catch (Exception e){

            System.out.print("\nError - Trying to reduce width that is equal or greater than the Picture's width\n");
        }
        return picture;
    }

    public static void main(String[] args){
        Picture p = reduceWidth(1338, "na1.jpg");

        p.save(new File("n1-result.jpg"));
    }

    public static int pixelDistance(Color c1, Color c2) {

        int r1 = c1.getRed();
        int g1 = c1.getGreen();
        int b1 = c1.getBlue();

        int r2 = c2.getRed();
        int g2 = c2.getGreen();
        int b2 = c2.getBlue();

        int r = (r1 - r2) * (r1 - r2);
        int g = (g1 - g2) * (g1 - g2);
        int b = (b1 - b2) * (b1 - b2);

        return (r + g + b);
    }


}
