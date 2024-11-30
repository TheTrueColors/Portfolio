package com.calculator.calculatorjava;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CalculatorController {
    @FXML
    private Pane display;
    @FXML
    private ImageView close;
    @FXML
    private ImageView minimize;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void handleMousePressed(javafx.scene.input.MouseEvent event) {
        // Memorizza la posizione iniziale del mouse rispetto alla finestra
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void handleMouseDragged(javafx.scene.input.MouseEvent event) {
        // Muove la finestra in base ai movimenti del mouse
        Stage stage = (Stage) display.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }


    @FXML
    private void handleCloseButtonAction(javafx.scene.input.MouseEvent event) {
        Stage primaryStage = (Stage) close.getScene().getWindow();
        primaryStage.close();
        System.exit(0);
    }

    @FXML
    private void handleMinimizeButtonAction(javafx.scene.input.MouseEvent event) {
        Stage primaryStage = (Stage) minimize.getScene().getWindow();
        primaryStage.setIconified(true);
    }

    private boolean isNewEntry = true; // Flag per sapere se si sta inserendo un nuovo numero
    private String currentOperator = ""; // L'operatore corrente
    private double firstOperand = 0; // Primo operando
    private double secondOperand = 0; // Secondo operando

    public void handlePaneClick(javafx.scene.input.MouseEvent event) {
        Pane clickedPane = (Pane) event.getSource();
        Label label = (Label) clickedPane.getChildren().getFirst();
        String labelText = label.getText();

        Label displayLabel = (Label) display.getChildren().getFirst();
        String currentText = displayLabel.getText();

        switch (labelText) {
            case "C":
                displayLabel.setText("0");
                firstOperand = 0;
                secondOperand = 0;
                currentOperator = "";
                isNewEntry = true; // Dopo C si può iniziare una nuova operazione
                break;
            case "=":
                if (currentText.isEmpty()) return; // Non fare nulla se il display è vuoto
                secondOperand = Double.parseDouble(currentText);

                // Calcola il risultato
                switch (currentOperator) {
                    case "+":
                        firstOperand += secondOperand;
                        break;
                    case "-":
                        firstOperand -= secondOperand;
                        break;
                    case "*":
                        firstOperand *= secondOperand;
                        break;
                    case "/":
                        if (secondOperand == 0) {
                            displayLabel.setText("Error");
                            isNewEntry = true; // Impedisce altri calcoli
                            return;
                        }
                        firstOperand /= secondOperand;
                        break;
                    default:
                        return;
                }

                // Mostra il risultato e prepara per un nuovo calcolo
                displayLabel.setText(String.valueOf(firstOperand));
                currentOperator = ""; // Resetta l'operatore
                isNewEntry = true; // Il prossimo numero sarà un nuovo input
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                if (!currentText.isEmpty()) {
                    if (!currentOperator.isEmpty()) {
                        // Se c'è già un primo operando, aggiorna l'operatore senza calcolare
                        currentOperator = labelText; // Cambia l'operatore
                        // Non resettiamo il display, permette l'inserimento del secondo numero
                    } else {
                        // Se non c'è ancora un operatore, memorizza il primo numero
                        firstOperand = Double.parseDouble(currentText);
                        currentOperator = labelText; // Memorizza l'operatore corrente
                        secondOperand = 0; // Resetta il secondo operando
                        isNewEntry = true; // Il prossimo numero sarà un nuovo input
                    }
                }
                break;
            default:
                // Gestisce i numeri e altri caratteri
                if (currentText.equals("0") || isNewEntry) {
                    displayLabel.setText(labelText); // Se è un nuovo numero, lo imposta come primo
                    isNewEntry = false; // Non è più una nuova entry, permetti di concatenare
                } else {
                    displayLabel.setText(currentText + labelText); // Altrimenti lo concatena
                }
                break;
        }
    }


}
