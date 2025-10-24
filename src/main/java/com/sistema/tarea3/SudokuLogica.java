package com.sistema.tarea3;

public class SudokuLogica {
    public static boolean resolver(int[][] board) {
        // Validación inicial por si se llama directo desde tests
        String msg = SudokuValidator.validacionInicial(board);
        if (msg != null) {
            return false;
        }

        // Construye la raíz del árbol (sin decisión: fila/col = -1 y valor = 0)
        Nodo raiz = new Nodo(null, board, -1, -1, 0);

        // Si ya es meta, copiar y fin
        if (raiz.esMeta()) {
            copiarEn(board, raiz.estado);
            return true;
        }

        // DFS explícito: expandimos como en el laberinto (probar hijos, backtrack).
        boolean resuelto = dfs(raiz);

        if (resuelto) {
            // Recupera la solución desde el nodo solución (estado guardado en el nodo que retornó true)
            // 'nodoSolucion' se deja en variable estática al encontrar meta (ver abajo).
            copiarEn(board, nodoSolucion.estado);
        }
        return resuelto;
    }

    // === DFS N-ario (idéntico concepto que el laberinto: marcar -> probar -> desmarcar) ===
    private static Nodo nodoSolucion = null;

    private static boolean dfs(Nodo nodo) {
        if (nodo.esMeta()) {
            nodoSolucion = nodo;
            return true;
        }
        // Generar candidatos para el siguiente hueco
        int[] cand = nodo.candidatos();
        // Probar cada hijo
        for (int i = 0; i < cand.length; i++) {
            int v = cand[i];
            Nodo hijo = nodo.crearHijo(v);
            if (dfs(hijo)) {
                return true; // propaga éxito
            }
            // Si falla, retrocedemos implícitamente al volver (como en laberinto)
        }
        return false; // ningún hijo funcionó
    }

    // === utilidades ===

    private static void copiarEn(int[][] destino, int[][] fuente) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                destino[i][j] = fuente[i][j];
    }
}
