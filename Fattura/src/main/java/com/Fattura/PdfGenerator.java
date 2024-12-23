package com.Fattura;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PdfGenerator {

    /**
     * Questo metodo genera una fattura in formato PDF utilizzando la libreria iText.
     * Prende vari dettagli e li formatta in una fattura strutturata.
     *
     * @param filename Il nome del file PDF da creare.
     * @param productDetails Dettagli dei prodotti acquistati, come stringa.
     * @param buyerFiscalId ID fiscale del compratore.
     * @param buyerTaxCode Codice fiscale del compratore.
     * @param buyerName Nome del compratore.
     * @param buyerAddress Indirizzo del compratore.
     * @param buyerSurname Cognome del compratore.
     * @param buyerCity Città del compratore.
     * @param buyerProvince Provincia del compratore.
     * @param buyerPostalCode CAP del compratore.
     * @param buyerCountry Nazione del compratore.
     * @param buyerDenomination Denominazione dell'acquirente.
     * @param totalAmount Importo totale della fattura.
     */
    public static void createInvoice(String filename,
                                     StringBuilder productDetails,
                                     String buyerFiscalId,
                                     String buyerTaxCode,
                                     String buyerName,
                                     String buyerAddress,
                                     String buyerSurname,
                                     String buyerCity,
                                     String buyerProvince,
                                     String buyerPostalCode,
                                     String buyerCountry,
                                     String buyerDenomination,  // Aggiunto il parametro per la denominazione
                                     Double totalAmount) {
        try {
            // Crea un'istanza di PdfDocument, che è il punto di ingresso per creare il PDF
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
            Document document = new Document(pdfDoc);

            // Carica i font per il testo in grassetto e normale
            PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont regularFont = PdfFontFactory.createFont("Helvetica");

            // Definisce i colori di sfondo per le sezioni
            DeviceRgb sectionBackgroundColor = new DeviceRgb(208, 231, 255);  // Azzurro chiaro
            DeviceRgb tableHeaderColor = new DeviceRgb(204, 204, 255);  // Viola chiaro per le intestazioni delle tabelle

            // Aggiunge il titolo al documento PDF, centrato, con font in grassetto
            document.add(new Paragraph("Fattura Elettronica")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(boldFont)
                    .setFontSize(20));

            // Aggiunge una sezione con i dettagli del compratore, con uno sfondo azzurro chiaro
            Div buyerDetailsDiv = new Div().setBackgroundColor(sectionBackgroundColor).setPadding(10);
            buyerDetailsDiv.add(new Paragraph("Dati Acquirente")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(boldFont)
                    .setFontSize(14));

            // Crea una tabella per visualizzare le informazioni del compratore
            Table buyerDetailsTable = new Table(2).useAllAvailableWidth();  // 2 colonne per coppie chiave-valore

            // Aggiunge l'ID fiscale e il codice fiscale del compratore alla tabella
            buyerDetailsTable.addCell(createCell("Identificatore fiscale ai fini IVA:", boldFont));
            buyerDetailsTable.addCell(createCell(buyerFiscalId, regularFont));

            buyerDetailsTable.addCell(createCell("Codice Fiscale:", boldFont));
            buyerDetailsTable.addCell(createCell(buyerTaxCode, regularFont));

            // Aggiunge il nome e il cognome del compratore alla tabella
            buyerDetailsTable.addCell(createCell("Nome:", boldFont));
            buyerDetailsTable.addCell(createCell(buyerName, regularFont));

            buyerDetailsTable.addCell(createCell("Cognome:", boldFont));
            buyerDetailsTable.addCell(createCell(buyerSurname, regularFont));

            // Aggiunge la denominazione dell'acquirente (nuovo campo)
            buyerDetailsTable.addCell(createCell("Denominazione:", boldFont));
            buyerDetailsTable.addCell(createCell(buyerDenomination, regularFont));

            // Aggiunge la tabella dei dettagli del compratore al documento
            buyerDetailsDiv.add(buyerDetailsTable);
            document.add(buyerDetailsDiv);

            // Aggiunge una sezione per i dettagli della città (indirizzo, città, provincia, ecc.), con sfondo azzurro chiaro
            Div cityDetailsDiv = new Div().setBackgroundColor(sectionBackgroundColor).setPadding(10);
            cityDetailsDiv.add(new Paragraph("Dati Città")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(boldFont)
                    .setFontSize(14));

            Table cityDetailsTable = new Table(2).useAllAvailableWidth();  // 2 colonne per coppie chiave-valore

            // Aggiunge la città e la provincia del compratore alla tabella
            cityDetailsTable.addCell(createCell("Comune:", boldFont));
            cityDetailsTable.addCell(createCell(buyerCity, regularFont));

            cityDetailsTable.addCell(createCell("Provincia:", boldFont));
            cityDetailsTable.addCell(createCell(buyerProvince, regularFont));

            // Aggiunge il CAP e l'indirizzo del compratore alla tabella
            cityDetailsTable.addCell(createCell("CAP:", boldFont));
            cityDetailsTable.addCell(createCell(buyerPostalCode, regularFont));

            cityDetailsTable.addCell(createCell("Indirizzo:", boldFont));
            cityDetailsTable.addCell(createCell(buyerAddress, regularFont));

            // Aggiunge la nazione del compratore alla tabella
            cityDetailsTable.addCell(createCell("Nazione:", boldFont));
            cityDetailsTable.addCell(createCell(buyerCountry, regularFont));

            // Aggiunge la tabella dei dettagli della città al documento
            cityDetailsDiv.add(cityDetailsTable);
            document.add(cityDetailsDiv);

            // Aggiunge i dettagli dei prodotti al documento in una sezione con sfondo azzurro chiaro
            Div productDetailsDiv = new Div().setBackgroundColor(sectionBackgroundColor).setPadding(10);
            productDetailsDiv.add(new Paragraph("Dettagli Prodotti:")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(boldFont)
                    .setFontSize(14));

            // Crea una tabella per i prodotti con 5 colonne
            Table productTable = new Table(5).useAllAvailableWidth();  // 5 colonne per i dettagli dei prodotti
            productTable.addCell(createCell("Codice", boldFont, tableHeaderColor));  // Intestazione per il codice prodotto
            productTable.addCell(createCell("Prodotto", boldFont, tableHeaderColor));  // Intestazione per il nome prodotto
            productTable.addCell(createCell("Quantità", boldFont, tableHeaderColor));  // Intestazione per la quantità
            productTable.addCell(createCell("Prezzo Unitario", boldFont, tableHeaderColor));  // Intestazione per il prezzo unitario
            productTable.addCell(createCell("Prezzo Totale", boldFont, tableHeaderColor));  // Intestazione per il prezzo totale

            // Analizza i dettagli dei prodotti passati come stringa e aggiunge ogni prodotto alla tabella
            String[] products = productDetails.toString().split("\n");
            for (String product : products) {
                String[] productData = product.split(", ");  // Split dei dati per virgola e spazio

                if (productData.length == 5) {
                    productTable.addCell(createCell(productData[0].split(":")[1].trim(), regularFont));  // Codice prodotto
                    productTable.addCell(createCell(productData[1].split(":")[1].trim(), regularFont));  // Nome prodotto
                    productTable.addCell(createCell(productData[2].split(":")[1].trim(), regularFont));  // Quantità
                    productTable.addCell(createCell(productData[3].split(":")[1].trim(), regularFont));  // Prezzo unitario
                    productTable.addCell(createCell(productData[4].split(":")[1].trim(), regularFont));  // Prezzo totale
                } else {
                    System.out.println("Errore nel formato dei dati del prodotto: " + product);
                }
            }

            // Aggiunge la tabella dei prodotti al documento
            productDetailsDiv.add(productTable);
            document.add(productDetailsDiv);

            // Aggiunge la data corrente e l'importo totale in fondo alla fattura
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDate = now.format(formatter);

            // Crea una tabella per visualizzare la data e l'importo totale
            Table totalTable = new Table(2).useAllAvailableWidth();  // 2 colonne per data e totale

            // Aggiunge la data formattata alla prima colonna (allineata a sinistra)
            totalTable.addCell(createCell(formattedDate, regularFont));

            // Aggiunge l'importo totale della fattura alla seconda colonna (allineata a destra)
            Cell totalCell = createCell("Totale Fattura: €" + String.format("%.2f", totalAmount), boldFont);
            totalCell.setTextAlignment(TextAlignment.RIGHT);  // Allinea il totale a destra
            totalTable.addCell(totalCell);

            // Aggiunge la tabella totale al documento
            Div totalDiv = new Div().setBackgroundColor(sectionBackgroundColor).setPadding(10);
            totalDiv.add(totalTable);
            document.add(totalDiv);

            // Chiude il documento PDF dopo aver aggiunto tutto il contenuto
            pdfDoc.close();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();  // Stampa eventuali eccezioni che si verificano durante la creazione del PDF
        }
    }

    /**
     * Metodo di utilità per creare una cella nella tabella con contenuto e font specificati.
     *
     * @param content Il contenuto da visualizzare nella cella.
     * @param font Il font da utilizzare per il testo.
     * @return Una cella stilizzata.
     */
    private static Cell createCell(String content, PdfFont font) {
        return new Cell().add(new Paragraph(content))
                .setPadding(5)
                .setBackgroundColor(new DeviceRgb(240, 248, 255))  // Sfondo azzurro chiaro per la cella
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(font);  // Imposta il font per il testo della cella
    }

    /**
     * Metodo di utilità per creare una cella nella tabella con contenuto, font e colore di sfondo specificati.
     *
     * @param content Il contenuto da visualizzare nella cella.
     * @param font Il font da utilizzare per il testo.
     * @param backgroundColor Il colore di sfondo della cella.
     * @return Una cella stilizzata.
     */
    private static Cell createCell(String content, PdfFont font, DeviceRgb backgroundColor) {
        return new Cell().add(new Paragraph(content))
                .setPadding(5)
                .setBackgroundColor(backgroundColor)  // Imposta il colore di sfondo per la cella
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(font);  // Imposta il font per il testo della cella
    }
}
