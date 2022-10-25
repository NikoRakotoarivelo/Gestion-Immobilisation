/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.web.mvc.immo.model;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author UCER
 */
public class ExportPdfFiche {

    private Immobilisation immobilisation;

    public ExportPdfFiche(Immobilisation immobilisation) {
        this.immobilisation = immobilisation;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.CYAN);
        cell.setPadding(6);

        Font font = FontFactory.getFont(FontFactory.TIMES_ITALIC);
        font.setColor(Color.black);
        font.setSize(15);

        cell.setPhrase(new Phrase("Article", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Prix d'achat", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date d'achat", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date de mise en service", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Type d'ammortissement", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Duree", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Detenteur", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        table.addCell(immobilisation.getArticle());
        table.addCell(immobilisation.getPrixAchat() + " Ar");
        table.addCell(immobilisation.getDateAchat());
        table.addCell(immobilisation.getDateDeMiseEnService());
        table.addCell(immobilisation.getTypeAmmortissement());
        table.addCell(String.valueOf(immobilisation.getDuree()));
        table.addCell(immobilisation.getDetenteur());
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ITALIC);
        font.setSize(17);
        font.setColor(Color.black);

        Paragraph p = new Paragraph("FICHE D' IMMOBILISATION", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{3.4f, 2.4f, 2.0f, 2.0f, 3.0f, 1.7f, 3.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();

    }
}
