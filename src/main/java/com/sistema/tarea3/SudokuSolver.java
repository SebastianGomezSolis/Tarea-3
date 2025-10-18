package com.sistema.tarea3;

public class SudokuSolver {

    public static boolean resolver(int[][] board) {
        int[] empty = encontrarVacio(board);
        if (empty == null) return true;

        int r = empty[0], c = empty[1];
        for (int v = 1; v <= 9; v++) {
            if (SudokuValidator.esSeguro(board, r, c, v)) {
                board[r][c] = v;
                if (resolver(board)) return true;
                board[r][c] = 0; // backtrack
            }
        }
        return false;
    }

    private static int[] encontrarVacio(int[][] board) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (board[r][c] == 0) return new int[]{r, c};
        return null;
    }
}
