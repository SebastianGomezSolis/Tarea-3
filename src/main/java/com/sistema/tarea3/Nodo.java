package com.sistema.tarea3;

public class Nodo {
    public final int fila;
    public final int col;
    public final int valor;

    public final int[][] estado;

    public final int filaSgte;
    public final int colSgte;

    public final Nodo padre;

    public Nodo(Nodo padre, int[][] origen, int fila, int col, int valor) {
        this.padre = padre;
        this.fila = fila;
        this.col = col;
        this.valor = valor;
        this.estado = copiar(origen);

        if (fila >= 0 && col >= 0 && valor > 0) {
            this.estado[fila][col] = valor;
        }

        int[] nxt = encontrarVacio(this.estado);
        if (nxt == null) {
            this.filaSgte = -1;
            this.colSgte = -1;
        } else {
            this.filaSgte = nxt[0];
            this.colSgte = nxt[1];
        }
    }

    public boolean esMeta() {
        return filaSgte == -1 && colSgte == -1;
    }

    public int[] candidatos() {
        if (esMeta()) {
            return new int[0];
        }

        int[] tmp = new int[9];
        int count = 0;
        for (int v = 1; v <= 9; v++) {
            if (SudokuLogica.esSeguro(estado, filaSgte, colSgte, v)) {
                tmp[count++] = v;
            }
        }

        int[] res = new int[count];
        for (int i = 0; i < count; i++) res[i] = tmp[i];
        return res;
    }

    public Nodo crearHijo(int v) {
        return new Nodo(this, this.estado, this.filaSgte, this.colSgte, v);
    }

    private static int[][] copiar(int[][] m) {
        int[][] r = new int[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                r[i][j] = m[i][j];
        return r;
    }

    private static int[] encontrarVacio(int[][] board) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (board[r][c] == 0) return new int[]{r, c};
        return null;
    }
}
