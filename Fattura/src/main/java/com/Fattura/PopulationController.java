package com.Fattura;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PopulationController {
    @FXML
    private ChoiceBox<String> prodottoChoiceBox; // Collega il ChoiceBox dall'FXML

    @FXML
    public void initialize() {
        // Chiama il metodo per popolare il ChoiceBox all'avvio
        populateChoiceBox();
    }

    private void populateChoiceBox() {
        try {
            // Ottieni la connessione al database
            Connection connection = DatabaseManager.getConnection();

            // Esegui la query per ottenere i dati
            String query = "SELECT Nome FROM prodotti"; // Sostituisci con la tua tabella e colonna
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Aggiungi i risultati al ChoiceBox
            while (resultSet.next()) {
                String prodotto = resultSet.getString("Nome"); // Colonna della tabella
                prodottoChoiceBox.getItems().add(prodotto);
            }

            // Chiudi risorse
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore durante il caricamento del ChoiceBox.");
        }
    }
}
