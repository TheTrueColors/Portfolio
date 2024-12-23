package com.Fattura;


import javafx.beans.property.SimpleStringProperty;

// --- Classe per rappresentare gli articoli nel carrello ---
public class Item {
    private final SimpleStringProperty codice, prodotto, quantita, prezzoTotale, prezzoUnitario;

    public Item(String codice, String prodotto, String quantita, String prezzoTotale, String prezzoUnitario) {
        this.codice = new SimpleStringProperty(codice);
        this.prodotto = new SimpleStringProperty(prodotto);
        this.quantita = new SimpleStringProperty(quantita);
        this.prezzoTotale = new SimpleStringProperty(prezzoTotale);
        this.prezzoUnitario = new SimpleStringProperty(prezzoUnitario);
    }

    public String getCodice() {
        return codice.get();
    }

    public String getProdotto() {
        return prodotto.get();
    }

    public String getQuantita() {
        return quantita.get();
    }

    public String getPrezzoTotale() {
        return prezzoTotale.get();
    }

    public String getPrezzoUnitario() {
        return prezzoUnitario.get();
    }

    public SimpleStringProperty codiceProperty() {
        return codice;
    }

    public SimpleStringProperty prodottoProperty() {
        return prodotto;
    }

    public SimpleStringProperty quantitaProperty() {
        return quantita;
    }

    public SimpleStringProperty prezzoTotaleProperty() {
        return prezzoTotale;
    }

    public SimpleStringProperty prezzoUnitarioProperty() {
        return prezzoUnitario;
    }
}
