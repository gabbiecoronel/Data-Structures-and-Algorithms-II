/* COP 3503C Assignment 2
This program is written by: Gabrielle Coronel */

import java.util.Scanner;

public class PA2 {

    static Scanner scanner = new Scanner(System.in);

    /* isSafe method checks if the letter is in bounds of the matrix, if the current cell is the same as the current letter of the
    word, and if the solution matrix is unchecked (" ") */
    static boolean isSafe(char[][] matrix, String[][] sol, String word, int i, int j, int letter, int M, int N) {
        if (i >= 0 && i < M && j >= 0 && j < N && matrix[i][j] == word.charAt(letter) && sol[i][j].equals(" "))
            return true;
        return false;
    }

    // solve method finds the word in the matrix
    static boolean solve(char[][] matrix, String[][] sol, String word, int i, int j, int letter, int firstRow, int firstCol, int M, int N) {
        // base case and checks if the word was found in the matrix
        if (letter == word.length())
            return true;

        // checks if it's safe to move in the matrix (if we're in bounds)
        if (isSafe(matrix, sol, word, i, j, letter, M, N)) {

            // checks if the current letter is the last letter of the word and if the current cell is the same as the first cell
            if (letter == word.length() - 1 && i == firstRow && j == firstCol)
                // can't reuse the same letter of the same cell for the first and last letter of the word
                return false;

            // set the solution cell to the letter that we're currently at in the matrix
            sol[i][j] = String.valueOf(word.charAt(letter));

            // to move to the left direction
            if (solve(matrix, sol, word, i, j - 1, letter + 1, i, j, M, N))
                return true;

            // to move to the right direction
            if (solve(matrix, sol, word, i, j + 1, letter + 1, i, j, M, N))
                return true;

            // to move up
            if (solve(matrix, sol, word, i - 1, j, letter + 1, i, j, M, N))
                return true;

            // to move down
            if (solve(matrix, sol, word, i + 1, j, letter + 1, i, j, M, N))
                return true;

            // to move to the bottom left diagonal
            if (solve(matrix, sol, word, i + 1, j - 1, letter + 1, i, j, M, N))
                return true;

            // to move to the bottom right diagonal
            if (solve(matrix, sol, word, i + 1, j + 1, letter + 1, i, j, M, N))
                return true;

            // to move to the upper left diagonal
            if (solve(matrix, sol, word, i - 1, j - 1, letter + 1, i, j, M, N))
                return true;

            // to move to the upper right diagonal
            if (solve(matrix, sol, word, i - 1, j + 1, letter + 1, i, j, M, N))
                return true;

            // uncheck the solution cell
            sol[i][j] = " ";
        }
        // if we get here, the word wasn't found in the matrix during the search
        return false;
    }

    // printSolution method prints the solution matrix when the word is found in the matrix
    static void printSolution(String[][] sol, int N, int i) {
        System.out.print("[");
        // for loop runs through the number of columns (N) in the matrix
        for (int j = 0; j < N; j++) {
            System.out.print(sol[i][j]);
            if (j < N - 1)
                System.out.print(", ");
        }
        System.out.print("]");
        System.out.println();
    }

    public static void main(String[] args) {
        /* Initializing the variables
        (M - Number of rows in the matrix, N - Number of columns in the matrix, S - Number of words to find) */
        int M = scanner.nextInt(), N = scanner.nextInt(), S = scanner.nextInt();

        // matrix represents the letters in the matrix
        char[][] matrix = new char[M][N];

        // sol represents the solution matrix (to print the words we successfully find in the matrix)
        String[][] sol = new String[M][N];

        // word represents the words we need to find
        String[] word = new String[S];

        // for loop runs through the number of rows (M) in the matrix
        for (int i = 0; i < M; i++) {
            // for loop runs through the number of columns (N) in the matrix
            for (int j = 0; j < N; j++)
                matrix[i][j] = scanner.next().charAt(0);
        }

        scanner.nextLine();
        // for loop runs through the number of words (S) in the matrix
        for (int i = 0; i < S; i++)
            word[i] = scanner.nextLine();

        // for loop runs through the number of words (S) in the matrix
        for (int l = 0; l < S; l++) {
            System.out.print("Looking for " + word[l] + "\n");
            /* resetting the matrix of solution
            for loop runs through the number of rows (M) in the matrix */
            for (int i = 0; i < M; i++) {
                // for loop runs through the number of columns (N) in the matrix
                for (int j = 0; j < N; j++)
                    sol[i][j] = " ";
            }

            // search represents whether the word is successfully searched in the matrix
            boolean search = false;

            // for loop runs through the number of rows (M) in the matrix
            for  (int i = 0; i < M; i++) {
                // for loop runs through the number of columns (N) in the matrix
                for (int j = 0; j < N; j++) {
                    // checks if the solve method finds the word in the matrix
                    if (solve(matrix, sol, word[l], i, j, 0, i, j, M, N)) {
                        // when search is true, we can print letters of the word
                        search = true;
                        break;
                    }
                }
                // when search is true, we can print letters of the word
                if (search)
                    break;
            }

            // for loop runs through the number of rows (M) in the matrix
            for (int i = 0; i < M; i++) {
                // checks if we can print the letters of the word we're searching for in the matrix
                if (search)
                    // calls printSolution method to print the solution matrix of the word we found
                    printSolution(sol, N, i);
                    // the word was not successfully searched in the matrix
                else {
                    System.out.print(word[l] + " not found!\n");
                    break;
                }
            }
            System.out.println();
        }
        scanner.close();
    }
}