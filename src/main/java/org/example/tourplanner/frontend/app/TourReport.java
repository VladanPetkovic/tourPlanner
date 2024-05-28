package org.example.tourplanner.frontend.app;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.example.tourplanner.frontend.model.Log;
import org.example.tourplanner.frontend.model.Tour;
import java.io.IOException;
import java.util.List;

public class TourReport extends Report<Tour> {
    public TourReport() {
        super();
    }

    @Override
    protected Document buildDocument(Tour tours) {
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
    protected void setContent(Document document, Tour tour) throws IOException {
        setTitle(document, "Tour-Report");

        if (tour == null) {
            return;
        }

        setTable(document, tour);   // it is only one report, so we are directly passing it
    }

    private void setTable(Document document, Tour tour) throws IOException {
        // Define the number of columns for the table
        int numRows = 11;
        int numCols = 2;
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                .useAllAvailableWidth();

        // Add table headers
        addTableHeader(table, "Attribute");
        addTableHeader(table, "Value");


        // add information to the table
        addRow(table, "Name", tour.getName());
        addRow(table, "Description", tour.getDescription());
        addRow(table, "From Place", tour.getFromPlace());
        addRow(table, "To Place", tour.getToPlace());
        addRow(table, "Transport Type", String.valueOf(tour.getTransportType().toString()));
        addRow(table, "Distance", String.valueOf(tour.getDistance()));
        addRow(table, "Estimated Time", String.valueOf(tour.getEstimated_time()));
        addRow(table, "Route Information", tour.getRoute_information());
        if (tour.getPopularity() != null) {
            addRow(table, "Popularity", tour.getPopularity().toString());
        }
        if (tour.getChildFriendliness() != null) {
            addRow(table, "Child Friendliness", tour.getChildFriendliness().toString());
        }

        // Add table to document
        document.add(table);

        Table logTable = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1, 1, 1, 1}))
                .useAllAvailableWidth();

        // Add table headers for log details
        addTableHeader(logTable, "Username");
        addTableHeader(logTable, "Date/Time");
        addTableHeader(logTable, "Comment");
        addTableHeader(logTable, "Difficulty");
        addTableHeader(logTable, "Total Distance");
        addTableHeader(logTable, "Total Time");
        addTableHeader(logTable, "Rating");

        // Add log information to the table
        for (Log log : tour.getLogs()) {
            addRow(logTable, log.getUsername(), log.getDateTime().toString(), log.getComment(),
                    String.valueOf(log.getDifficulty()), String.valueOf(log.getTotalDistance()),
                    String.valueOf(log.getTotalTime()), String.valueOf(log.getRating()));
        }

        if (!tour.getLogs().isEmpty()) {
            document.add(new Paragraph("Logs")); // Add a title for the log table
            document.add(logTable);
        }
    }

    private void addRow(Table table, String attribute, String value) {
        table.addCell(new Cell().add(new Paragraph(attribute)));
        table.addCell(new Cell().add(new Paragraph(value)));
    }

    private void addRow(Table table, String... values) {
        for (String value : values) {
            table.addCell(new Cell().add(new Paragraph(value)));
        }
    }

    private void addTableHeader(Table table, String headerTitle) throws IOException {
        Cell header = new Cell()
                .add(new Paragraph(headerTitle))
                .setFont(PdfFontFactory.createFont())
                .setFontSize(10)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        table.addHeaderCell(header);
    }
}
