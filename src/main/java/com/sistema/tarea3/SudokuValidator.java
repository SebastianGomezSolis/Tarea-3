package com.sistema.tarea3;

public class SudokuValidator {
    public static String validacionInicial(int[][] board) {
        if (board == null || board.length != 9) return "Tablero inválido (dimensión).";
        for (int i = 0; i < 9; i++) if (board[i] == null || board[i].length != 9) return "Tablero inválido (dimensión).";

        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (board[r][c] < 0 || board[r][c] > 9)
                    return "Error: solo se permiten números 1–9 (o vacío).";

        for (int r = 0; r < 9; r++) {
            boolean[] seen = new boolean[10];
            for (int c = 0; c < 9; c++) {
                int v = board[r][c];
                if (v == 0) continue;
                if (seen[v]) return "Repetición en fila " + (r + 1) + ".";
                seen[v] = true;
            }
        }

        for (int c = 0; c < 9; c++) {
            boolean[] seen = new boolean[10];
            for (int r = 0; r < 9; r++) {
                int v = board[r][c];
                if (v == 0) continue;
                if (seen[v]) return "Repetición en columna " + (c + 1) + ".";
                seen[v] = true;
            }
        }

        for (int br = 0; br < 9; br += 3) {
            for (int bc = 0; bc < 9; bc += 3) {
                boolean[] seen = new boolean[10];
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        int v = board[br + r][bc + c];
                        if (v == 0) continue;
                        if (seen[v]) return "Repetición en subcuadro (" + (br/3 + 1) + "," + (bc/3 + 1) + ").";
                        seen[v] = true;
                    }
                }
            }
        }
        return null;
    }

    public static boolean esSeguro(int[][] board, int row, int col, int val) {
        for (int c = 0; c < 9; c++) if (board[row][c] == val) return false;
        for (int r = 0; r < 9; r++) if (board[r][col] == val) return false;
        int br = (row / 3) * 3, bc = (col / 3) * 3;
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[br + r][bc + c] == val) return false;
        return true;
    }
}
