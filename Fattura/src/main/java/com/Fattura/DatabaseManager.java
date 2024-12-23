package com.Fattura;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La classe {@code DatabaseManager} gestisce la connessione a un database MySQL.
 * Fornisce metodi per ottenere e chiudere la connessione al database.
 */
public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/fattura";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    /**
     * Ottiene la connessione al database. Se la connessione non è già aperta o è stata chiusa,
     * viene creata una nuova connessione.
     *
     * @return La connessione al database.
     * @throws SQLException Se si verifica un errore durante l'ottenimento della connessione.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Carica il driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Crea la connessione
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connessione al database riuscita.");
            } catch (ClassNotFoundException e) {
                System.out.println("Errore: Driver JDBC non trovato.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * Chiude la connessione al database, se attualmente aperta.
     * In caso di errore durante la chiusura, vengono stampati i dettagli dell'eccezione.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connessione chiusa.");
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura della connessione.");
                e.printStackTrace();
            }
        }
    }
}
