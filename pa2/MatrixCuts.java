package pa2;

/**
 * @author Isaac Sanga and Justin Worley
 */

import java.util.ArrayList;

public class MatrixCuts {

    public static ArrayList<Tuple> widthCut(int[][] M){
        int i, j;
        int m = M.length;
        int n = M[0].length;

        int costMatrix[][] = new int[m][n];

        for(i=0; i<m; i++){
            for(j=0; j<n; j++) {
                if(i!=m-1)
                    costMatrix[i][j] = Integer.MAX_VALUE;
                else
                    costMatrix[i][j] = M[i][j];
            }
        }

        costMatrix[m-1][n-1] = M[m-1][n-1];

        for(i=m-1; i>0; i--){
            for(j=n-1; j>=0; j--){
                if(i<m && j<n-1 && j>0){
                    if(costMatrix[i-1][j] > costMatrix[i][j] + M[i-1][j])
                        costMatrix[i-1][j] = costMatrix[i][j] + M[i-1][j];
                    if(costMatrix[i-1][j-1] > costMatrix[i][j] + M[i-1][j-1])
                        costMatrix[i-1][j-1] = costMatrix[i][j] + M[i-1][j-1];
                    if(costMatrix[i-1][j+1] > costMatrix[i][j] + M[i-1][j+1])
                        costMatrix[i-1][j+1] = costMatrix[i][j] + M[i-1][j+1];
                }else if(j==0){
                    if(costMatrix[i-1][j] > costMatrix[i][j] + M[i-1][j])
                        costMatrix[i-1][j] = costMatrix[i][j] + M[i-1][j];
                    if(costMatrix[i-1][j+1] > costMatrix[i][j] + M[i-1][j+1])
                        costMatrix[i-1][j+1] = costMatrix[i][j] + M[i-1][j+1];
                }else if(j==n-1) {
                    if(costMatrix[i - 1][j] > costMatrix[i][j] + M[i - 1][j])
                        costMatrix[i - 1][j] = costMatrix[i][j] + M[i - 1][j];
                    if(costMatrix[i-1][j-1] > costMatrix[i][j] + M[i-1][j-1])
                        costMatrix[i-1][j-1] = costMatrix[i][j] + M[i-1][j-1];
                }
            }
        }
        int min = costMatrix[0][0];
        int minRow = 0;
        int minCol = 0;
        for(i = 1; i<n; i++){
            if(costMatrix[0][i] < min) {
                min = costMatrix[0][i];
                minCol = i;
            }
        }

        ArrayList<Tuple> tupleList = new ArrayList<>();
        tupleList.add(new Tuple(costMatrix[minRow][minCol], -1));
        tupleList.add(new Tuple(minRow, minCol));

        while(minRow<m-1){
            int down = costMatrix[minRow+1][minCol];
            int downRight;
            if(minCol+1>=n)
                downRight = Integer.MAX_VALUE;
            else
                downRight = costMatrix[minRow+1][minCol+1];

            int downLeft;
            if(minCol<=0)
                downLeft = Integer.MAX_VALUE;
            else
                downLeft = costMatrix[minRow+1][minCol-1];

            if(downRight<down){
                if(downRight<downLeft){
                    minRow+=1;
                    minCol+=1;
                }else{
                    minRow+=1;
                    minCol-=1;
                }
            }else {
                if(down<downLeft)
                    minRow+=1;
                else {
                    minRow += 1;
                    minCol -=1;
                }
            }

            tupleList.add(new Tuple(minRow, minCol));
        }

        return tupleList;
    }


    public static ArrayList<Tuple> stitchCut(int[][] M){
        int i, j;
        int m = M.length;
        int n = M[0].length;

        int costMatrix[][]=new int[m][n];


        for(i=0; i<m; i++){
            for(j=0; j<n; j++) {
                costMatrix[i][j] = Integer.MAX_VALUE;
            }
        }

        costMatrix[m-1][n-1] = M[m-1][n-1];

        for(i=m-1; i>=0; i--){
            for(j=n-1; j>=0; j--){
                if(i>0 && j>0){
                    if(costMatrix[i-1][j] > costMatrix[i][j] + M[i-1][j])
                        costMatrix[i-1][j] = costMatrix[i][j] + M[i-1][j];
                    if(costMatrix[i-1][j-1] > costMatrix[i][j] + M[i-1][j-1])
                        costMatrix[i-1][j-1] = costMatrix[i][j] + M[i-1][j-1];
                    if(costMatrix[i][j-1] > costMatrix[i][j] + M[i][j-1])
                        costMatrix[i][j-1] = costMatrix[i][j] + M[i][j-1];
                }else if(i==0 && j>0){
                    if(costMatrix[i][j-1] > costMatrix[i][j] + M[i][j-1])
                        costMatrix[i][j-1] = costMatrix[i][j] + M[i][j-1];
                }else if(i>0 && j==0) {
                    if(costMatrix[i - 1][j] > costMatrix[i][j] + M[i - 1][j])
                        costMatrix[i - 1][j] = costMatrix[i][j] + M[i - 1][j];
                }
            }
        }

        int min = costMatrix[0][0];
        int minRow = 0;
        int minCol = 0;
        for(i = 1; i<n; i++){
            if(costMatrix[0][i] < min) {
                min = costMatrix[0][i];
                minCol = i;
            }
        }

        ArrayList<Tuple> tupleList = new ArrayList<>();
        tupleList.add(new Tuple(costMatrix[minRow][minCol], -1));
        tupleList.add(new Tuple(minRow, minCol));

        while(minRow<m-1 && minCol<n-1){
            int right;
            if(minCol+1>=n) {
                right = Integer.MAX_VALUE;
            }else{
                right = costMatrix[minRow][minCol+1];
            }
            int diag;
            if(minCol+1>=n || minRow+1>=m){
                diag = Integer.MAX_VALUE;
            }else {
                diag = costMatrix[minRow + 1][minCol + 1];
            }
            int down;
            if(minRow+1>=m){
                down =Integer.MAX_VALUE;
            }else{
                down = costMatrix[minRow+1][minCol];
            }

            if(right<diag){
                if(right<down){
                    minCol+=1;
                }else{
                    minRow+=1;
                }
            }else{
                if(diag<down){
                    minCol+=1;
                    minRow+=1;
                }else{
                    minRow+=1;
                }
            }
            tupleList.add(new Tuple(minRow, minCol));
        }

        return tupleList;

    }


    public static void main(String[] args){
        int[][] array = new int[][]{{5,7,9,4,5},{2,3,1,1,2},{2,0,49,46,8},{3,1,1,1,1},{50,51,25,26,1}};
        ArrayList<Tuple> result = widthCut(array);
    }

}
