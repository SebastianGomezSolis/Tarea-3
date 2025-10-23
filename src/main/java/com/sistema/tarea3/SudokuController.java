package com.sistema.tarea3;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;


public class SudokuController implements Initializable {
    @FXML private GridPane gridSudoku;
    @FXML private Button btnLimpiar;
    @FXML private Button btnValidar;
    @FXML private Button btnResolver;
    @FXML private Button btnCargarEjemplo;
    @FXML private Label lblEstado;

    private final TextField[][] celdas = new TextField[9][9];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        construirGrilla();
        lblEstado.setText("Listo.");
    }

    private void construirGrilla() {
        gridSudoku.getChildren().clear();
        gridSudoku.setAlignment(Pos.CENTER);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                TextField tf = crearCelda();
                celdas[r][c] = tf;
                tf.setPrefSize(55, 55);
                GridPane.setHalignment(tf, HPos.CENTER);
                gridSudoku.add(tf, c, r);
            }
        }
        aplicarBordesSubcuadros();
    }

    private TextField crearCelda() {
        TextField tf = new TextField();

        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String nuevo = change.getControlNewText();
            if (nuevo.isEmpty()) return change;
            if (nuevo.length() == 1 && nuevo.matches("[1-9]")) return change;
            return null;
        };
        tf.setTextFormatter(new TextFormatter<>(new IntegerStringConverterSafe(), null, filtro));
        tf.setAlignment(Pos.CENTER);

        tf.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            switch (e.getCode()) {
                case UP -> moverFoco(tf, -1, 0);
                case DOWN -> moverFoco(tf, 1, 0);
                case LEFT -> moverFoco(tf, 0, -1);
                case RIGHT -> moverFoco(tf, 0, 1);
            }
        });

        // Estilo base
        tf.setStyle("-fx-background-color: white; -fx-alignment: center; -fx-font-size: 18px; -fx-border-color: #b0b0b0; -fx-border-width: 1;");
        return tf;
    }

    private void moverFoco(TextField actual, int dr, int dc) {
        int rr = -1, cc = -1;
        outer:
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (celdas[r][c] == actual) { rr = r; cc = c; break outer; }

        if (rr < 0) return;
        int nr = Math.max(0, Math.min(8, rr + dr));
        int nc = Math.max(0, Math.min(8, cc + dc));
        celdas[nr][nc].requestFocus();
        celdas[nr][nc].selectAll();
    }

    private void aplicarBordesSubcuadros() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                TextField tf = celdas[r][c];
                String base = "-fx-background-color: white; -fx-alignment: center; -fx-font-size: 18px; "
                        + "-fx-border-color: #b0b0b0; -fx-border-width: 1; ";
                StringBuilder sb = new StringBuilder(base);
                if (c % 3 == 0) sb.append("-fx-border-left-width: 3;");
                if (r % 3 == 0) sb.append("-fx-border-top-width: 3;");
                if (c == 8)    sb.append("-fx-border-right-width: 3;");
                if (r == 8)    sb.append("-fx-border-bottom-width: 3;");
                tf.setStyle(sb.toString());
            }
        }
    }

    @FXML
    private void limpiar() {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                celdas[r][c].setText("");
                celdas[r][c].setStyle(celdas[r][c].getStyle().replace("-fx-background-color: #eaffea;", "white"));
            }
        lblEstado.setText("Tablero limpio.");
    }

    @FXML
    private void validar() {
        Tablero t = leerDesdeUI();
        String msg = SudokuValidator.validacionInicial(t.matriz());
        if (msg == null) {
            lblEstado.setText("Tablero inicial válido.");
        } else {
            lblEstado.setText(msg);
        }
    }

    @FXML
    private void resolver() {
        Tablero t = leerDesdeUI();

        String msg = SudokuValidator.validacionInicial(t.matriz());
        if (msg != null) {
            lblEstado.setText(msg);
            return;
        }

        int[][] copia = Tablero.copiar(t.matriz());
        boolean ok = SudokuSolver.resolver(copia);

        if (ok) {
            pintarSolucion(t.matriz(), copia);
            lblEstado.setText("¡Solución encontrada!");
        } else {
            lblEstado.setText("No existe solución para el tablero ingresado.");
        }
    }

    @FXML
    private void pistas() {
        int[][] ejemplo = {
                {5,3,0, 0,7,0, 0,0,0},
                {6,0,0, 1,9,5, 0,0,0},
                {0,9,8, 0,0,0, 0,6,0},

                {8,0,0, 0,6,0, 0,0,3},
                {4,0,0, 8,0,3, 0,0,1},
                {7,0,0, 0,2,0, 0,0,6},

                {0,6,0, 0,0,0, 2,8,0},
                {0,0,0, 4,1,9, 0,0,5},
                {0,0,0, 0,8,0, 0,7,9}
        };
        escribirEnUI(ejemplo);
        lblEstado.setText("Ejemplo cargado. Puedes validar o resolver.");
    }

    private Tablero leerDesdeUI() {
        int[][] m = new int[9][9];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                String txt = celdas[r][c].getText();
                m[r][c] = (txt == null || txt.isBlank()) ? 0 : Integer.parseInt(txt);
            }
        return new Tablero(m);
    }

    private void escribirEnUI(int[][] m) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                celdas[r][c].setText(m[r][c] == 0 ? "" : Integer.toString(m[r][c]));
                // limpiar colores previos
                celdas[r][c].setStyle(celdas[r][c].getStyle().replace("-fx-background-color: #eaffea;", "white"));
            }
    }

    private void pintarSolucion(int[][] original, int[][] solucion) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                String nuevo = solucion[r][c] == 0 ? "" : Integer.toString(solucion[r][c]);
                celdas[r][c].setText(nuevo);
                if (original[r][c] == 0 && solucion[r][c] != 0) {
                    if (!celdas[r][c].getStyle().contains("#eaffea")) {
                        celdas[r][c].setStyle(celdas[r][c].getStyle() + "-fx-background-color: #eaffea;");
                    }
                }
            }
        }
    }

    private static class IntegerStringConverterSafe extends StringConverter<Integer> {
        private final IntegerStringConverter base = new IntegerStringConverter();
        @Override public String toString(Integer object) { return object == null ? "" : base.toString(object); }
        @Override public Integer fromString(String string) {
            if (string == null || string.isBlank()) return null;
            return base.fromString(string);
        }
    }
}

