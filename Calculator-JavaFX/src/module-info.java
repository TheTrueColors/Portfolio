module com.calculator.calculatorjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.base;

    opens com.calculator.calculatorjava to javafx.fxml;
    exports com.calculator.calculatorjava;
}
