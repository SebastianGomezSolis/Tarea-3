package com.sistema.tarea3;

public class SudokuLogica {
    public static boolean resolver(int[][] board) {
        String msg = SudokuValidator.validacionInicial(board);
        if (msg != null) {
            return false;
        }

        Nodo raiz = new Nodo(null, board, -1, -1, 0);

        if (raiz.esMeta()) {
            copiarEn(board, raiz.estado);
            return true;
        }

        boolean resuelto = dfs(raiz);

        if (resuelto) {
            copiarEn(board, nodoSolucion.estado);
        }
        return resuelto;
    }

    private static Nodo nodoSolucion = null;

    private static boolean dfs(Nodo nodo) {
        if (nodo.esMeta()) {
            nodoSolucion = nodo;
            return true;
        }

        int[] cand = nodo.candidatos();

        for (int i = 0; i < cand.length; i++) {
            int v = cand[i];
            Nodo hijo = nodo.crearHijo(v);
            if (dfs(hijo)) {
                return true; // propaga éxito
            }

        }
        return false;
    }

    private static void copiarEn(int[][] destino, int[][] fuente) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                destino[i][j] = fuente[i][j];
    }
}
