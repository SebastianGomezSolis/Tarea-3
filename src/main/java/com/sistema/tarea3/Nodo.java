package com.sistema.tarea3;

public class Nodo {
    // Decisión aplicada en este nodo (para la raíz no aplicamos ninguna asignación)
    public final int fila;
    public final int col;
    public final int valor; // 0 en la raíz (sin decisión)

    // Estado del tablero en este punto de la búsqueda (9x9)
    public final int[][] estado;

    // Siguiente hueco a resolver desde este estado (o -1,-1 si ya no hay)
    public final int filaSgte;
    public final int colSgte;

    // Relación padre (útil si quieres reconstruir el camino de decisiones)
    public final Nodo padre;

    public Nodo(Nodo padre, int[][] origen, int fila, int col, int valor) {
        this.padre = padre;
        this.fila = fila;
        this.col = col;
        this.valor = valor;
        this.estado = copiar(origen);

        // Aplica la decisión si hay valor > 0
        if (fila >= 0 && col >= 0 && valor > 0) {
            this.estado[fila][col] = valor;
        }

        // Calcula siguiente hueco vacío
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
            // sin próximos candidatos; devolver arreglo vacío
            return new int[0];
        }
        // máximo 9 candidatos
        int[] tmp = new int[9];
        int count = 0;
        for (int v = 1; v <= 9; v++) {
            if (SudokuValidator.esSeguro(estado, filaSgte, colSgte, v)) {
                tmp[count++] = v;
            }
        }
        // compactar a tamaño count
        int[] res = new int[count];
        for (int i = 0; i < count; i++) res[i] = tmp[i];
        return res;
    }

    //Crea hijo aplicando "valor v" en la siguiente celda vacía.
    public Nodo crearHijo(int v) {
        return new Nodo(this, this.estado, this.filaSgte, this.colSgte, v);
    }

    // ==== utilidades internas (sin Arrays/externos) ====

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
