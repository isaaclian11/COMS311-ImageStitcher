package pa2;

import java.awt.*;

public class ImageProcessor {
    public static Picture reduceWidth(int x, String inputImage){
        Picture picture = new Picture(inputImage);
        int m = picture.height();
        int n = picture.width();
        int[][] matrix = new int[m][n];

        for(int j=0; j<m; j++){
            for(int i=0; i<n; i++){
                Color a = picture.get(i, j);
                Color b;
                if(j==0){
                    b = picture.get(i, j+1);
                }else if(j==n-1){
                    b = picture.get(i, j-1);
                }else{
                    a = picture.get(i, j-1);
                    b = picture.get(i, j+1);
                }
                int dist = dist(a, b);
                matrix[i][j] = dist;
            }
        }



    }

    private static int dist(Color a, Color b){
        return (int) (Math.pow((a.getRed()-b.getRed()), 2) + Math.pow(a.getGreen()-b.getGreen(), 2)+ Math.pow(a.getBlue()-b.getBlue(), 2));
    }

}
