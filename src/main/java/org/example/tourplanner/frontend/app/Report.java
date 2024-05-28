package org.example.tourplanner.frontend.app;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.embed.swing.SwingFXUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import com.itextpdf.layout.Document;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Report<T> {
    protected final float MARGIN = 50;
    protected String destinationFolder = "";
    protected String fileName = "";
    protected PdfDocument pdfDocument;

    protected abstract Document buildDocument(T t);
    protected abstract void setContent(Document document, T t) throws IOException;

    public ArrayList<Image> getPreviewImages(T t) {
        ArrayList<Image> images = new ArrayList<>();

        createBinDirectory();
        // Create a temporary pdf document
        PdfWriter writer = null;
        try {
            writer = new PdfWriter("bin/temp.pdf");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        pdfDocument = new PdfDocument(writer);
        Document document = buildDocument(t);
        // Close the PDF document
        document.close();
        pdfDocument.close();

        // converting pdf to images
        File file = new File("bin/temp.pdf");
        try {
            PDDocument pdDocument = Loader.loadPDF(file);
            PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);

            for (int i = 0; i < pdDocument.getNumberOfPages(); i++) {
                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(i, 300);

                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                images.add(image);
            }

            pdDocument.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return images;
    }
    public void export(T t) {
        // Create a new PDF document
        File file = new File(this.destinationFolder + "\\" + this.fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        PdfWriter writer = new PdfWriter(fos);
        pdfDocument = new PdfDocument(writer);
        Document document = buildDocument(t);

        if (document == null) {
            return;
        }

        document.close();
    }
    public void setOptions(String destinationFolder) {
        this.destinationFolder = destinationFolder;
        this.fileName = createFileName();
    }

    /**
     * Creates the fileName with the current DateTime.
     * @return String, for example: "report_2024-05-11_18-38-27.pdf"
     */
    protected String createFileName() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        return String.format("report_%04d-%02d-%02d_%02d-%02d-%02d.pdf",
                currentDateTime.getYear(),
                currentDateTime.getMonthValue(),
                currentDateTime.getDayOfMonth(),
                currentDateTime.getHour(),
                currentDateTime.getMinute(),
                currentDateTime.getSecond());
    }

    protected void setTitle(Document document, String title) throws IOException {
        Paragraph titleHeader = new Paragraph(title)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(titleHeader);
    }

    /**
     * Creates a bin-directory, if not already existing.
     */
    protected void createBinDirectory() {
        File binDirectory = new File("bin");
        if (!binDirectory.exists()) {
            binDirectory.mkdir();
        }
    }
}
