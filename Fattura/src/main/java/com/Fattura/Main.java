/**
 * Classe per gestire le operazioni di fatturazione.
 *
 * @author Giona Tuccitto
 * @version 1.0
 */

package com.Fattura;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.ImageView;
import java.sql.Connection;

/**
 * Classe principale dell'applicazione JavaFX.
 * Questa classe estende {@link Application} e serve come punto di ingresso
 * per l'applicazione. Gestisce il caricamento dell'interfaccia utente da un
 * file FXML e la configurazione dello stage principale.
 */
public class Main extends Application {

    /**
     * Riferimento al componente {@link ImageView} per chiudere la finestra.
     * Questo componente è definito nel file FXML.
     */
    @FXML
    private ImageView close;

    /**
     * Riferimento al componente {@link ImageView} per minimizzare la finestra.
     * Questo componente è definito nel file FXML.
     */
    @FXML
    private ImageView minimize;

    /**
     * Metodo principale che avvia l'applicazione JavaFX.
     * Questo metodo carica il file FXML, configura la scena e mostra lo stage principale.
     *
     * @param primaryStage lo stage principale dell'applicazione.
     * @throws Exception se si verifica un errore durante il caricamento del file FXML.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cashier.fxml"));
        Parent root = loader.load();

        // Configura la scena e lo stage
        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Calcolatrice");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Ottiene una connessione al database
        Connection connection = DatabaseManager.getConnection();
    }

    /**
     * Metodo principale del programma. Lancia l'applicazione JavaFX.
     *
     * @param args gli argomenti della riga di comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
