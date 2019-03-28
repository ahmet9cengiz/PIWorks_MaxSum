import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PIWorks {
/*You will have an orthogonal triangle input from a file and you need to find the maximum sum of the numbers according to given rules below;

1. You will start from the top and move downwards to an adjacent number as in below.
2. You are only allowed to walk downwards and diagonally.
3. You can only walk over NON PRIME NUMBERS.
4. You have to reach at the end of the pyramid as much as possible.
5. You have to treat your input as pyramid.
*/

    public static void main(String[] args) throws IOException {
        int line_count = 0;
        String line ;

        //Checks how many lines we have
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) { //tries to create a buffered reader
            while ((line = br.readLine()) != null) //while  there is more line
                line_count++;
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[][] pyramid = new int[line_count][line_count]; //2d integer array to visualize the input pyramid
        line_count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) { //tries to create a buffered reader

            while ((line = br.readLine()) != null) { //while  there is more line

                String[] data = line.split(" "); //splits the line by the spaces

                for (int j = 0; j < data.length; j++) {
                    pyramid[line_count][j] = Integer.parseInt(data[j]); //parses the Strings as integers, and puts them in the 2d pyramid array
                }
                line_count ++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        /*
        For checking purposes of the 2d array

        for(int i=0; i<pyramid.length; i++) {
            System.out.print("\n");
            for (int j = 0; j < pyramid.length; j++) {
                System.out.printf("%4s ", pyramid[i][j]);
            }
        }

        */

        int[][] memo = new int[pyramid.length][pyramid.length]; //2d array to use for memoization

        for(int i=0; i<memo.length; i++)  //intializes zero for every cell
            for(int j=0; j<memo.length; j++){
                memo[i][j] = 0;
            }

        System.out.print("\n"+getMax(pyramid,0,0,line_count, memo));  //calls the getMax method and prints the output

    }

    private static int getMax(int[][] arr, int i, int j, int line_count, int[][] memo) { //Recursive function with memoization to find the max valued path

        if (j < 0 || j > line_count) { //if the cell is outside of the limits
            return -1 * Integer.MAX_VALUE; //returns a large punishment if the path is illegal, prevents illegal maxSums
        } else { //if the cell is valid
            if (isPrime(arr[i][j])) { //if the cell is a prime number
                return -1 * Integer.MAX_VALUE; //returns a large punishment if the path is illegal, prevents illegal maxSums
            }
            else if (i == line_count - 1) { //if it is the last line
                return arr[i][j]; //returns the cell value
            }
            else { //if it is a valid, non prime value which is not at the last line
                if(memo[i][j] == 0) { //if this is the first time we saw this cell, stores the value of the maxSum from this cell to the last line in a memo array
                    memo[i][j] = arr[i][j] + Math.max(Math.max(getMax(arr, i + 1, j, line_count, memo),
                            getMax(arr, i + 1, j + 1, line_count, memo)),
                            getMax(arr, i + 1, j - 1, line_count, memo)); //makes the recursive method faster
                }
                return memo[i][j];
            }
        }
    }


    private static boolean isPrime(int num){
            // Corner case
            if (num <= 1) {
                return false;
            }
            // Check from 2 to n-1
            for (int i = 2; i < num; i++) {
                if (num % i == 0) {
                    return false;
                }
            }
            return true;
    }
}
