package pdfgen;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import person.Persons;

import java.io.FileOutputStream;

/**
 * Class representing how the pdf generation works.
 */
public class PdfGeneration {

    /**
     * This void representing the pdf generation.
     * @param fileName
     * @param data
     */
    public void pdfGeneration(String fileName, ObservableList<Persons> data) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();

            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n\n"));

            float[] columnWidths = {2, 4, 4, 6};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("KontaktLista"));
            cell.setBackgroundColor(GrayColor.GRAYWHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(4);
            table.addCell(cell);
            
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Sorszám");
            table.addCell("Vezetéknév");
            table.addCell("Keresztnév");
            table.addCell("Telefonszám");
            table.setHeaderRows(1);
            
            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
            for (int i = 1; i <= data.size(); i++) {
                Persons actualPerson = data.get(i - 1);
                
                table.addCell(""+i);
                table.addCell(actualPerson.getLastName());
                table.addCell(actualPerson.getFirstName());
                table.addCell(actualPerson.getPhoneNumber());
            }
            
            document.add(table);

            Chunk signature = new Chunk("\n\n Generálva a Telefonkönyv alkalmazás segítségével.");
            Paragraph base = new Paragraph(signature);
            document.add(base);

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }


}
