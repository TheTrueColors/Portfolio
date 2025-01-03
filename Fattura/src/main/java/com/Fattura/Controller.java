package com.Fattura;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller {

    // --- FXML Fields ---
    @FXML
    private Stage stage;  // Riferimento alla finestra principale
    @FXML
    private Pane display;
    @FXML
    private ImageView close, minimize;
    @FXML
    private ChoiceBox<String> prodottoChoiceBox;
    @FXML
    private TextField desc, codice, prezzo_u, s_magazzino, u_misura, quantita, prezzo_t;
    @FXML
    private Button cancella, aggiungi;
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> codiceColumn, prodottoColumn, quantitaColumn, prezzoTotaleColumn;
    @FXML
    private TextField ifi, cf, nome, ind, co, pro, cap, nazione, den, cognome;

    private double xOffset = 0;
    private double yOffset = 0;

    // --- Inizializzazione del Controller ---

    /**
     * Inizializza l'interfaccia utente e carica i dati necessari, come i prodotti nel ChoiceBox.
     */
    @FXML
    public void initialize() {
        initializeUI();  // Inizializza l'interfaccia utente
        loadData();      // Carica i dati (ad esempio, dal database)
    }

    /**
     * Inizializza l'interfaccia utente configurando la TableView e gli EventListener.
     */
    private void initializeUI() {
        configureTableColumns();  // Imposta le colonne della tabella
        configureEventListeners(); // Imposta i listener per gli eventi degli elementi UI
    }

    /**
     * Carica i dati necessari, come la lista dei prodotti, nel ChoiceBox.
     */
    private void loadData() {
        populateChoiceBox();  // Popola il ChoiceBox con i nomi dei prodotti
    }

    // --- Configurazione della TableView ---

    /**
     * Configura le colonne della TableView per visualizzare correttamente i dati degli articoli.
     */
    private void configureTableColumns() {
        codiceColumn.setCellValueFactory(cellData -> cellData.getValue().codiceProperty());
        prodottoColumn.setCellValueFactory(cellData -> cellData.getValue().prodottoProperty());
        quantitaColumn.setCellValueFactory(cellData -> cellData.getValue().quantitaProperty());
        prezzoTotaleColumn.setCellValueFactory(cellData -> cellData.getValue().prezzoTotaleProperty());
    }

    // --- Popolamento del ChoiceBox con i dati dei prodotti ---

    /**
     * Popola il ChoiceBox con i nomi dei prodotti letti dal database.
     */
    private void populateChoiceBox() {
        executeQuery("SELECT Nome FROM prodotti", null, resultSet -> {
            while (resultSet.next()) {
                prodottoChoiceBox.getItems().add(resultSet.getString("Nome"));
            }
        });
    }

    // --- Configurazione degli EventListener ---

    /**
     * Configura gli EventListener per gli elementi dell'interfaccia utente.
     */
    private void configureEventListeners() {
        prodottoChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) populateFieldsWithProductInfo(newValue);
        });

        quantita.textProperty().addListener((observable, oldValue, newValue) -> {
            calculateTotalPrice();
        });

        cancella.setOnMouseClicked(event -> removeSelectedItem());
        aggiungi.setOnMouseClicked(event -> addItemToCart());
    }

    /**
     * Calcola il prezzo totale basato sulla quantità e sul prezzo unitario.
     * Se uno dei campi è errato, cancella il campo del prezzo totale.
     */
    private void calculateTotalPrice() {
        try {
            int qty = Integer.parseInt(quantita.getText());
            double unitPrice = Double.parseDouble(prezzo_u.getText());
            prezzo_t.setText(String.format("%.2f", qty * unitPrice));
        } catch (NumberFormatException e) {
            prezzo_t.clear();
        }
    }

    // --- Popola i campi con le informazioni del prodotto selezionato ---

    /**
     * Popola i campi di descrizione, codice, unità di misura, prezzo unitario e scorte
     * in base al prodotto selezionato nel ChoiceBox.
     *
     * @param productName Il nome del prodotto selezionato.
     */
    private void populateFieldsWithProductInfo(String productName) {
        executeQuery("SELECT Codice, Descrizione, UnitaM, PrezzoU, ScorteM FROM prodotti WHERE Nome = ?",
                preparedStatement -> preparedStatement.setString(1, productName),
                resultSet -> {
                    if (resultSet.next()) {
                        codice.setText(resultSet.getString("Codice"));
                        desc.setText(resultSet.getString("Descrizione"));
                        u_misura.setText(resultSet.getString("UnitaM"));
                        prezzo_u.setText(resultSet.getString("PrezzoU"));
                        s_magazzino.setText(resultSet.getString("ScorteM"));
                        quantita.clear();
                        prezzo_t.clear();
                    }
                });
    }

    // --- Aggiungi un elemento al carrello ---

    /**
     * Aggiunge un prodotto al carrello, aggiornando la TableView e il database.
     */
    private void addItemToCart() {
        if (validateFields()) {
            Item item = new Item(
                    codice.getText(),
                    prodottoChoiceBox.getValue(),
                    quantita.getText(),
                    prezzo_t.getText(),
                    prezzo_u.getText()
            );
            tableView.getItems().add(item);
            adjustStock(item.getCodice(), -Integer.parseInt(item.getQuantita()));
            calculateTotalPrice();
        } else {
            showError("Compila tutti i campi richiesti.");
        }
    }

    // --- Aggiorna le scorte nel database ---

    /**
     * Aggiorna le scorte del prodotto nel database dopo aver aggiunto un prodotto al carrello.
     *
     * @param codiceProdotto Il codice del prodotto.
     * @param quantityChange La quantità da aggiungere o rimuovere (valore negativo per rimuovere).
     */
    private void adjustStock(String codiceProdotto, int quantityChange) {
        executeUpdate("UPDATE prodotti SET ScorteM = ScorteM + ? WHERE Codice = ?",
                preparedStatement -> {
                    preparedStatement.setInt(1, quantityChange);
                    preparedStatement.setString(2, codiceProdotto);
                });
        reloadProductInfo(codiceProdotto);
    }

    // Ricarica le informazioni sulle scorte dal database per un prodotto specifico

    /**
     * Ricarica le informazioni sullo stock di un prodotto specifico nel database.
     *
     * @param codiceProdotto Il codice del prodotto di cui ricaricare le informazioni.
     */
    private void reloadProductInfo(String codiceProdotto) {
        executeQuery("SELECT ScorteM FROM prodotti WHERE Codice = ?",
                preparedStatement -> preparedStatement.setString(1, codiceProdotto),
                resultSet -> {
                    if (resultSet.next()) {
                        int updatedStock = resultSet.getInt("ScorteM");
                        s_magazzino.setText(String.valueOf(updatedStock));
                    }
                });
    }

    // --- Rimuovi un elemento dal carrello ---

    /**
     * Rimuove l'elemento selezionato dal carrello, ripristinando le scorte nel magazzino.
     */
    private void removeSelectedItem() {
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            adjustStock(selectedItem.getCodice(), Integer.parseInt(selectedItem.getQuantita()));
            tableView.getItems().remove(selectedItem);
            calculateTotalPrice();
        } else {
            showError("Seleziona un elemento da rimuovere.");
        }
    }

    // --- Validazione dei campi ---

    /**
     * Verifica che tutti i campi richiesti siano compilati correttamente.
     *
     * @return true se tutti i campi sono validi, false altrimenti.
     */
    private boolean validateFields() {
        return !codice.getText().isEmpty()
                && prodottoChoiceBox.getValue() != null
                && !quantita.getText().isEmpty()
                && !prezzo_t.getText().isEmpty();
    }

    // Resetta tutti i campi dell'interfaccia utente

    /**
     * Resetta tutti i campi dell'interfaccia utente, inclusi quelli relativi al carrello e al cliente.
     */
    private void resetFields() {
        codice.clear();
        prodottoChoiceBox.getSelectionModel().clearSelection();
        quantita.clear();
        prezzo_u.clear();
        s_magazzino.clear();
        prezzo_t.clear();
        ifi.clear();
        cf.clear();
        nome.clear();
        ind.clear();
        co.clear();
        pro.clear();
        cap.clear();
        nazione.clear();
        cognome.clear();
        den.clear();
        u_misura.clear();
        desc.clear();
        tableView.getItems().clear();
    }

    // --- Generazione della fattura in PDF ---

    /**
     * Gestisce la stampa della fattura in formato PDF.
     * Calcola l'importo totale e crea il file PDF.
     *
     * @param event L'evento di azione che ha attivato la generazione della fattura.
     */
    @FXML
    private void handleStampaFattura(ActionEvent event) {
        try {
            String filename = "fattura_" + cf.getText() + ".pdf";

            double totalAmount = tableView.getItems().stream()
                    .mapToDouble(item -> {
                        try {
                            return Double.parseDouble(item.getPrezzoTotale().replace(",", "."));
                        } catch (NumberFormatException e) {
                            return 0.0;
                        }
                    })
                    .sum();

            StringBuilder productDetails = new StringBuilder();
            for (Item item : tableView.getItems()) {
                productDetails.append(String.format("Codice: %s, Prodotto: %s, Quantità: %s, Prezzo Unitario: €%s, Prezzo Totale: €%s\n",
                        item.getCodice(), item.getProdotto(), item.getQuantita(), item.getPrezzoUnitario(), item.getPrezzoTotale()));
            }

            PdfGenerator.createInvoice(
                    filename,
                    productDetails,
                    ifi.getText(),
                    cf.getText(),
                    nome.getText(),
                    ind.getText(),
                    cognome.getText(),
                    co.getText(),
                    pro.getText(),
                    cap.getText(),
                    nazione.getText(),
                    den.getText(),
                    totalAmount
            );

            showAlert("Fattura Generata", "La fattura è stata generata con successo.");
            resetFields();
        } catch (Exception e) {
            logError("Errore durante la generazione della fattura.", e);
        }
    }

    // --- Metodi di utilità per la gestione degli errori e delle operazioni nel database ---

    private void showError(String message) {
        showAlert("Errore", message);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void executeUpdate(String query, QueryPreparer preparer) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (preparer != null) preparer.prepare(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            logError("Errore durante l'operazione di update.", e);
        }
    }

    private void executeQuery(String query, QueryPreparer preparer, ResultSetHandler handler) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (preparer != null) preparer.prepare(preparedStatement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (handler != null) handler.handle(resultSet);
            }
        } catch (Exception e) {
            logError("Errore durante l'operazione di query.", e);
        }
    }

    private void logError(String message, Exception e) {
        System.err.println(message);
        if (e != null) e.printStackTrace();
    }

    // --- Metodi per il controllo della finestra (drag, close, minimize) ---

    @FXML
    private void handleDrag(MouseEvent event) {
        Stage stage = (Stage) this.stage.getScene().getWindow();
        if (event.getSource() != null) {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        }
    }

    @FXML
    private void handleCloseButtonAction(MouseEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleMinimizeButtonAction(MouseEvent event) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) display.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    // --- Interfacce funzionali per preparare e gestire le query ---
    @FunctionalInterface
    private interface QueryPreparer {
        void prepare(PreparedStatement preparedStatement) throws Exception;
    }

    @FunctionalInterface
    private interface ResultSetHandler {
        void handle(ResultSet resultSet) throws Exception;
    }

}
