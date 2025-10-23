package com.sistema.tarea3;

public class SudokuLogica {

    public static boolean resolver(int[][] t) {
        int[] empty = encontrarVacio(t);
        if (empty == null) return true;

        int r = empty[0], c = empty[1];
        for (int v = 1; v <= 9; v++) {
            if (SudokuValidator.esSeguro(t, r, c, v)) {
                t[r][c] = v;
                if (resolver(t)) return true;
                t[r][c] = 0;
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
