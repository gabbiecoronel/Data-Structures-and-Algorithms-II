/* COP 3503C Assignment 1
This program is written by: Gabrielle Coronel */

import java.util.Scanner;
import java.util.HashSet;

public class PA1 {

    static Scanner scanner = new Scanner(System.in);

    // getCandidatePair returns the pair of ints from a sorted array A[] that adds up to target
    public static int[] getCandidatePair(int[] A, int target) {

        // initializing the low and high variables
        // low represents index 0 of array A[] and high represents the highest index of array A[]
        int low = 0, high = A.length - 1;

        // while loop continues when the low pointer is less than or equal to the high pointer
        while (low <= high) {

            // the points pair is the sum of the low and high points of array A[]
            int pair = A[low] + A[high];

            // checks if the points pair is the same as our target points
            if (pair == target)
                // returns the points pair
                return new int[] {A[low], A[high]};

                // checks if the points pair is less than our target points
            else if (pair < target)
                // increment the low index by one
                low++;

                // if the points pair is greater than our target points
            else
                // decrement the high index by one
                high--;
        }

        // returns the pair (0, 0) if a pair doesn't exist
        return new int[] {0, 0};

    }

    // getUnsorted CandidatePair returns the pair of ints from an unsorted array A[] that adds up to target
    public static int [] getUnsortedCandidatePair(int[] A, int target) {

        // Initializing a hashset
        HashSet <Integer> hashset = new HashSet<>();

        // for loop runs through all the points in the unsorted array A[]
        for (int point : A) {

            // the smaller point in the pair is calculated from target minus point
            int lowPoint = target - point;

            // checks if the point (lowPoint) is in the hashset
            if (hashset.contains(lowPoint)) {

                // checks if the index at the point we're currently at is smaller than the index of lowPoint
                // (if the bigger point comes first before the smaller point in the input)
                if (point < lowPoint)

                    // returns the points pair
                    // for this if statement, point and lowPoint switch (point represents the smaller point
                    // and lowPoint represents the bigger point)
                    return new int[] {point, lowPoint};

                // returns the points pair
                return new int[] {lowPoint, point};
            }

            // adding the point into the hashset
            hashset.add(point);

        }

        // returns the pair (0, 0) if a pair doesn't exist
        return new int[] {0, 0};
    }

    public static void main(String[] args) {

        // k represents the number of test cases in the inputs
        int k = scanner.nextInt();

        // for loop runs through the number of test cases
        for (int i = 1; i <= k; i++) {

            // status represents the sorted status (0 - unsorted, 1 - sorted)
            int status = scanner.nextInt();

            // n represents the size of the array (the number of games in the Knights arcade)
            int n = scanner.nextInt();

            // creates an array A[] and initializes it based on n number of elements
            int[] A = new int[n];

            // for loop runs through the number of n elements in the array
            for (int j = 0; j < n; j++) {
                A[j] = scanner.nextInt();
            }

            // T represents the number of points in the game card
            int T = scanner.nextInt();

            int[] pointsPair;

            // checks if the status of array A[] is unsorted (0)
            if (status == 0)
                // calls getUnsortedCandidatePair to find the pair of points (pointsPair) from the unsorted array
                pointsPair = getUnsortedCandidatePair(A, T);

                // if the status of array A[] is sorted (1)
            else
                // calls getCandidatePair to find the pair of points (pointsPair) from the sorted array
                pointsPair = getCandidatePair(A, T);

            // checks if the pair of points are not 0
            if (pointsPair[0] != 0 && pointsPair[1] != 0)
                System.out.println("Test case#" + i + ": Spend " + T + " points by playing the games with " + pointsPair[0] + " points and " + pointsPair[1] + " points.");

                // if the pair of points are both 0
            else
                System.out.println("Test case#" + i + ": No way you can spend exactly " + T + " points.");
        }

        // closes the scanner
        scanner.close();
    }
}