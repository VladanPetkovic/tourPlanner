package org.example.tourplanner.frontend.app;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.example.tourplanner.frontend.model.TourAverage;

import java.io.IOException;
import java.util.List;

public class SummarizeReport extends Report<List<TourAverage>> {
    public SummarizeReport() {
        super();
    }

    @Override
    protected Document buildDocument(List<TourAverage> tours) {
        try {
            // create the document
            Document newDocument = new Document(pdfDocument);

            setContent(newDocument, tours);

            return newDocument;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void setContent(Document document, List<TourAverage> tours) throws IOException {
        setTitle(document, "Summarize-Report");

        if (tours == null) {
            return;
        }

        setTable(document, tours);
    }

    private void setTable(Document document, List<TourAverage> tours) throws IOException {
        // Define the number of columns for the table
        float[] columnWidths = {2, 2, 2, 1, 1, 1, 1};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        // Add table headers
        addTableHeader(table, "Tour Name");
        addTableHeader(table, "From Place");
        addTableHeader(table, "To Place");
        addTableHeader(table, "Avg Difficulty");
        addTableHeader(table, "Avg Total Time");
        addTableHeader(table, "Avg Total Distance");
        addTableHeader(table, "Avg Rating");

        // Add rows to the table
        for (TourAverage tour : tours) {
            table.addCell(new Cell().add(new Paragraph(tour.getName())));
            table.addCell(new Cell().add(new Paragraph(tour.getFromPlace())));
            table.addCell(new Cell().add(new Paragraph(tour.getToPlace())));
            table.addCell(new Cell().add(new Paragraph(String.format("%.2f", tour.getAverageDifficulty()))));
            table.addCell(new Cell().add(new Paragraph(String.format("%.2f", tour.getAverageTotalTime()))));
            table.addCell(new Cell().add(new Paragraph(String.format("%.2f", tour.getAverageTotalDistance()))));
            table.addCell(new Cell().add(new Paragraph(String.format("%.2f", tour.getAverageRating()))));
        }

        // Add table to document
        document.add(table);
    }

    private void addTableHeader(Table table, String headerTitle) throws IOException {
        Cell header = new Cell()
                .add(new Paragraph(headerTitle))
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(12)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        table.addHeaderCell(header);
    }
}
