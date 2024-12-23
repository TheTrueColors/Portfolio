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

/**
 * Classe per la generazione di fatture in formato PDF.
 * Utilizza la libreria iText per creare documenti PDF strutturati con dettagli di compratori, prodotti e importi totali.
 */
public class PdfGenerator {

    /**
     * Genera una fattura in formato PDF con i dettagli forniti.
     *
     * @param filename          Il nome del file PDF da creare.
     * @param productDetails    Dettagli dei prodotti acquistati, come stringa formattata (codice, prodotto, quantita, prezzo unitario, prezzo totale).
     * @param buyerFiscalId     Identificatore fiscale ai fini IVA del compratore.
     * @param buyerTaxCode      Codice fiscale del compratore.
     * @param buyerName         Nome del compratore.
     * @param buyerAddress      Indirizzo del compratore.
     * @param buyerSurname      Cognome del compratore.
     * @param buyerCity         Città del compratore.
     * @param buyerProvince     Provincia del compratore.
     * @param buyerPostalCode   CAP del compratore.
     * @param buyerCountry      Nazione del compratore.
     * @param buyerDenomination Denominazione del compratore.
     * @param totalAmount       Importo totale della fattura.
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
                                     String buyerDenomination,
                                     Double totalAmount) {
        try {
            // Crea un'istanza di PdfDocument e Document per generare il PDF
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
            Document document = new Document(pdfDoc);

            // Carica i font
            PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont regularFont = PdfFontFactory.createFont("Helvetica");

            // Colori di sfondo
            DeviceRgb sectionBackgroundColor = new DeviceRgb(208, 231, 255);
            DeviceRgb tableHeaderColor = new DeviceRgb(204, 204, 255);

            // Titolo della fattura
            document.add(new Paragraph("Fattura Elettronica")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(boldFont)
                    .setFontSize(20));

            // Sezione dettagli compratore
            Div buyerDetailsDiv = new Div().setBackgroundColor(sectionBackgroundColor).setPadding(10);
            buyerDetailsDiv.add(new Paragraph("Dati Acquirente")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(boldFont)
                    .setFontSize(14));

            // Tabella dettagli compratore
            Table buyerDetailsTable = new Table(2).useAllAvailableWidth();
            buyerDetailsTable.addCell(createCell("Identificatore fiscale ai fini IVA:", boldFont));
            buyerDetailsTable.addCell(createCell(buyerFiscalId, regularFont));
            buyerDetailsTable.addCell(createCell("Codice Fiscale:", boldFont));
            buyerDetailsTable.addCell(createCell(buyerTaxCode, regularFont));
            buyerDetailsTable.addCell(createCell("Nome:", boldFont));
            buyerDetailsTable.addCell(createCell(buyerName, regularFont));
            buyerDetailsTable.addCell(createCell("Cognome:", boldFont));
            buyerDetailsTable.addCell(createCell(buyerSurname, regularFont));
            buyerDetailsTable.addCell(createCell("Denominazione:", boldFont));
            buyerDetailsTable.addCell(createCell(buyerDenomination, regularFont));
            buyerDetailsDiv.add(buyerDetailsTable);
            document.add(buyerDetailsDiv);

            // Sezione dettagli città
            Div cityDetailsDiv = new Div().setBackgroundColor(sectionBackgroundColor).setPadding(10);
            cityDetailsDiv.add(new Paragraph("Dati Città")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(boldFont)
                    .setFontSize(14));

            Table cityDetailsTable = new Table(2).useAllAvailableWidth();
            cityDetailsTable.addCell(createCell("Comune:", boldFont));
            cityDetailsTable.addCell(createCell(buyerCity, regularFont));
            cityDetailsTable.addCell(createCell("Provincia:", boldFont));
            cityDetailsTable.addCell(createCell(buyerProvince, regularFont));
            cityDetailsTable.addCell(createCell("CAP:", boldFont));
            cityDetailsTable.addCell(createCell(buyerPostalCode, regularFont));
            cityDetailsTable.addCell(createCell("Indirizzo:", boldFont));
            cityDetailsTable.addCell(createCell(buyerAddress, regularFont));
            cityDetailsTable.addCell(createCell("Nazione:", boldFont));
            cityDetailsTable.addCell(createCell(buyerCountry, regularFont));
            cityDetailsDiv.add(cityDetailsTable);
            document.add(cityDetailsDiv);

            // Dettagli prodotti
            Div productDetailsDiv = new Div().setBackgroundColor(sectionBackgroundColor).setPadding(10);
            productDetailsDiv.add(new Paragraph("Dettagli Prodotti:")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(boldFont)
                    .setFontSize(14));

            Table productTable = new Table(5).useAllAvailableWidth();
            productTable.addCell(createCell("Codice", boldFont, tableHeaderColor));
            productTable.addCell(createCell("Prodotto", boldFont, tableHeaderColor));
            productTable.addCell(createCell("Quantità", boldFont, tableHeaderColor));
            productTable.addCell(createCell("Prezzo Unitario", boldFont, tableHeaderColor));
            productTable.addCell(createCell("Prezzo Totale", boldFont, tableHeaderColor));

            String[] products = productDetails.toString().split("\n");
            for (String product : products) {
                String[] productData = product.split(", ");
                if (productData.length == 5) {
                    productTable.addCell(createCell(productData[0].split(":")[1].trim(), regularFont));
                    productTable.addCell(createCell(productData[1].split(":")[1].trim(), regularFont));
                    productTable.addCell(createCell(productData[2].split(":")[1].trim(), regularFont));
                    productTable.addCell(createCell(productData[3].split(":")[1].trim(), regularFont));
                    productTable.addCell(createCell(productData[4].split(":")[1].trim(), regularFont));
                } else {
                    System.out.println("Errore nel formato dei dati del prodotto: " + product);
                }
            }
            productDetailsDiv.add(productTable);
            document.add(productDetailsDiv);

            // Totale e data
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDate = now.format(formatter);

            Table totalTable = new Table(2).useAllAvailableWidth();
            totalTable.addCell(createCell(formattedDate, regularFont));
            Cell totalCell = createCell("Totale Fattura: €" + String.format("%.2f", totalAmount), boldFont);
            totalCell.setTextAlignment(TextAlignment.RIGHT);
            totalTable.addCell(totalCell);
            Div totalDiv = new Div().setBackgroundColor(sectionBackgroundColor).setPadding(10);
            totalDiv.add(totalTable);
            document.add(totalDiv);

            // Chiude il documento
            pdfDoc.close();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea una cella di una tabella con contenuto e font specificati.
     *
     * @param content Il testo della cella.
     * @param font    Il font da applicare.
     * @return Una {@link Cell} configurata con contenuto e font specificati.
     */
    private static Cell createCell(String content, PdfFont font) {
        return new Cell().add(new Paragraph(content))
                .setPadding(5)
                .setBackgroundColor(new DeviceRgb(240, 248, 255))
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(font);
    }

    /**
     * Crea una cella di una tabella con contenuto, font e colore di sfondo specificati.
     *
     * @param content         Il testo della cella.
     * @param font            Il font da applicare.
     * @param backgroundColor Il colore di sfondo della cella.
     * @return Una {@link Cell} configurata con contenuto, font e colore di sfondo specificati.
     */
    private static Cell createCell(String content, PdfFont font, DeviceRgb backgroundColor) {
        return new Cell().add(new Paragraph(content))
                .setPadding(5)
                .setBackgroundColor(backgroundColor)
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(font);
    }
}
