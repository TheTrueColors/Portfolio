package com.Fattura;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/fattura";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    // Metodo per ottenere la connessione
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

    // Metodo per chiudere la connessione
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
