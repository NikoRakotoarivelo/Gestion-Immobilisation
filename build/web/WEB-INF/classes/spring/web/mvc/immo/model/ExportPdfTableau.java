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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author UCER
 */
public class ExportPdfTableau {
    private String article;
    private float prixAchat;
    private String dateService;
    private List annee = new ArrayList();
    private List anterieur = new ArrayList();
    private List exercice = new ArrayList();
    private List cumul = new ArrayList();
    private List vnc = new ArrayList();
    

    public ExportPdfTableau(String article, float prixAchat, String dateService, List annee, List anterieur, List exercice, List cumul, List vnc) {
        this.article = article;
        this.prixAchat = prixAchat;
        this.dateService = dateService;
        this.annee = annee;
        this.anterieur = anterieur;
        this.exercice = exercice;
        this.cumul = cumul;
        this.vnc = vnc;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.CYAN);
        cell.setPadding(6);

        Font font = FontFactory.getFont(FontFactory.TIMES_ITALIC);
        font.setColor(Color.black);
        font.setSize(15);

        cell.setPhrase(new Phrase("Annee", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Libelle", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Prix d'achat", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Anterieur", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Exercice", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cumul", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Valeur net", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {

        for(int i=0;i<vnc.size();i++) {
            table.addCell(String.valueOf(annee.get(i)));
            table.addCell(article);
            table.addCell((String.valueOf((float)prixAchat)));
            table.addCell(String.valueOf(anterieur.get(i)));
            table.addCell(String.valueOf(exercice.get(i)));
            table.addCell(String.valueOf(cumul.get(i)));
            table.addCell(String.valueOf(vnc.get(i)));
        }
    }

    public void exportTab(HttpServletResponse response) throws DocumentException, IOException {
        response.reset();
        response.getOutputStream();
        Document documents = new Document(PageSize.A3);
        PdfWriter.getInstance(documents, response.getOutputStream());

        documents.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ITALIC);
        font.setSize(17);
        font.setColor(Color.black);

        Font font2 = FontFactory.getFont(FontFactory.TIMES_ITALIC);
        font2.setSize(10);
        font2.setColor(Color.black);
        
        Paragraph p = new Paragraph("TABLEAU D' AMMORTISSEMENT", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        
        Paragraph p2 = new Paragraph("Article : ",font2);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        p2.add(article);
        documents.add(p);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{3.4f, 2.4f, 2.0f, 2.0f, 3.0f, 1.7f, 3.5f});
        table.setSpacingBefore(10);
        
        writeTableHeader(table);
        writeTableData(table);
        
        documents.add(table);
        documents.close();

    }
}
