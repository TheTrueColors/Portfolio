module Fattura {
    // Moduli Java richiesti
    requires javafx.controls;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    // Moduli esterni richiesti (iText PDF)
    requires kernel;
    requires layout;

    // Per accesso riflessivo ai controller FXML
    opens com.Fattura to javafx.fxml;

    // Esporta il package principale
    exports com.Fattura;
}
