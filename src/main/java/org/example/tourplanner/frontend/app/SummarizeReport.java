package org.example.tourplanner.frontend.app;

import com.itextpdf.layout.Document;
import org.example.tourplanner.frontend.model.Tour;
import java.io.IOException;
import java.util.List;

public class SummarizeReport extends Report {
    public SummarizeReport() {
        super();
    }

    @Override
    protected Document buildDocument(List<Tour> tours) {
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
    protected void setContent(Document document, List<Tour> tours) throws IOException {
        setTitle(document, "Summarize-Report");

        if (tours == null) {
            return;
        }
    }
}