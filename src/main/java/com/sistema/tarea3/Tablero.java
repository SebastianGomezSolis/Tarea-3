package com.sistema.tarea3;

public class Tablero {
    private final int[][] matriz;

    public Tablero() {
        this.matriz = new int[9][9];
    }

    public Tablero(int[][] origen) {
        if (origen == null || origen.length != 9) {
            throw new IllegalArgumentException("Dimensión inválida: se requiere 9x9.");
        }
        this.matriz = new int[9][9];
        for (int i = 0; i < 9; i++) {
            if (origen[i] == null || origen[i].length != 9) {
                throw new IllegalArgumentException("Dimensión inválida: se requiere 9x9.");
            }
            for (int j = 0; j < 9; j++) {
                this.matriz[i][j] = origen[i][j];
            }
        }
    }

    public int get(int fila, int col) {
        return matriz[fila][col];
    }

    public void set(int fila, int col, int val) {
        matriz[fila][col] = val;
    }

    public int[][] matriz() {
        return matriz;
    }

    public static int[][] copiar(int[][] m) {
        int[][] r = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                r[i][j] = m[i][j];
            }
        }
        return r;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(9 * 20);
        for (int r = 0; r < 9; r++) {
            sb.append('[');
            for (int c = 0; c < 9; c++) {
                sb.append(matriz[r][c]);
                if (c < 8) sb.append(", ");
            }
            sb.append(']').append('\n');
        }
        return sb.toString();
    }
}



