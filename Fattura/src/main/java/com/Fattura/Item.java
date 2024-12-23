package com.Fattura;

import javafx.beans.property.SimpleStringProperty;

// --- Classe per rappresentare gli articoli nel carrello ---
/**
 * La classe {@code Item} rappresenta un singolo articolo nel carrello, con informazioni
 * riguardanti il codice, il nome del prodotto, la quantità, il prezzo unitario e il prezzo totale.
 * Ogni oggetto {@code Item} è associato a proprietà di tipo {@link SimpleStringProperty}
 * per facilitare la gestione dei dati nell'interfaccia utente.
 */
public class Item {
    private final SimpleStringProperty codice, prodotto, quantita, prezzoTotale, prezzoUnitario;

    /**
     * Costruisce un oggetto {@code Item} con le informazioni specificate.
     *
     * @param codice        Il codice identificativo dell'articolo.
     * @param prodotto      Il nome del prodotto.
     * @param quantita     La quantità dell'articolo nel carrello.
     * @param prezzoTotale Il prezzo totale dell'articolo (quantità * prezzo unitario).
     * @param prezzoUnitario Il prezzo unitario dell'articolo.
     */
    public Item(String codice, String prodotto, String quantita, String prezzoTotale, String prezzoUnitario) {
        this.codice = new SimpleStringProperty(codice);
        this.prodotto = new SimpleStringProperty(prodotto);
        this.quantita = new SimpleStringProperty(quantita);
        this.prezzoTotale = new SimpleStringProperty(prezzoTotale);
        this.prezzoUnitario = new SimpleStringProperty(prezzoUnitario);
    }

    /**
     * Restituisce il codice dell'articolo.
     *
     * @return Il codice dell'articolo.
     */
    public String getCodice() {
        return codice.get();
    }

    /**
     * Restituisce il nome del prodotto.
     *
     * @return Il nome del prodotto.
     */
    public String getProdotto() {
        return prodotto.get();
    }

    /**
     * Restituisce la quantità dell'articolo nel carrello.
     *
     * @return La quantità dell'articolo.
     */
    public String getQuantita() {
        return quantita.get();
    }

    /**
     * Restituisce il prezzo totale dell'articolo (quantità * prezzo unitario).
     *
     * @return Il prezzo totale dell'articolo.
     */
    public String getPrezzoTotale() {
        return prezzoTotale.get();
    }

    /**
     * Restituisce il prezzo unitario dell'articolo.
     *
     * @return Il prezzo unitario dell'articolo.
     */
    public String getPrezzoUnitario() {
        return prezzoUnitario.get();
    }

    /**
     * Restituisce la proprietà {@link SimpleStringProperty} che rappresenta il codice dell'articolo.
     *
     * @return La proprietà {@code codice}.
     */
    public SimpleStringProperty codiceProperty() {
        return codice;
    }

    /**
     * Restituisce la proprietà {@link SimpleStringProperty} che rappresenta il nome del prodotto.
     *
     * @return La proprietà {@code prodotto}.
     */
    public SimpleStringProperty prodottoProperty() {
        return prodotto;
    }

    /**
     * Restituisce la proprietà {@link SimpleStringProperty} che rappresenta la quantità dell'articolo.
     *
     * @return La proprietà {@code quantita}.
     */
    public SimpleStringProperty quantitaProperty() {
        return quantita;
    }

    /**
     * Restituisce la proprietà {@link SimpleStringProperty} che rappresenta il prezzo totale dell'articolo.
     *
     * @return La proprietà {@code prezzoTotale}.
     */
    public SimpleStringProperty prezzoTotaleProperty() {
        return prezzoTotale;
    }

    /**
     * Restituisce la proprietà {@link SimpleStringProperty} che rappresenta il prezzo unitario dell'articolo.
     *
     * @return La proprietà {@code prezzoUnitario}.
     */
    public SimpleStringProperty prezzoUnitarioProperty() {
        return prezzoUnitario;
    }
}
