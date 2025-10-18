module com.sistema.tarea3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sistema.tarea3 to javafx.fxml;
    exports com.sistema.tarea3;
}