package com.example.fundmatch.shared.util;

import com.example.fundmatch.domain.entities.Event;
import com.example.fundmatch.domain.entities.User;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.borders.SolidBorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfGenerator {

    public static File generateTicketPdf(User user, Event event) throws IOException {
        File file = File.createTempFile("ticket_", ".pdf");

        try (PdfWriter writer = new PdfWriter(new FileOutputStream(file));
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf, PageSize.A6)) { // A6 size for a compact card

            document.setMargins(20, 20, 20, 20); // Set document margins
            PdfFont font = PdfFontFactory.createFont("Helvetica-Bold");

            Table table = new Table(1).useAllAvailableWidth();
            table.setBorder(new SolidBorder(ColorConstants.BLACK, 2)); // Table border
            table.setBackgroundColor(ColorConstants.LIGHT_GRAY); // Background color
            String imagePath = "http://localhost:9091/api/files/" + event.getImagePath();
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    ImageData imageData = ImageDataFactory.create(getImageFromURL(imagePath));
                    Image eventImg = new Image(imageData).setWidth(200).setHeight(100)
                            .setHorizontalAlignment(HorizontalAlignment.CENTER)
                            .setMarginBottom(10);
                    table.addCell(new Cell().add(eventImg).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER)
                            .setTextAlignment(TextAlignment.CENTER));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Paragraph title = new Paragraph("🎟️ Ticket for: " + event.getTitle())
                    .setFont(font)
                    .setFontSize(16)
                    .setFontColor(ColorConstants.BLACK)
                    .setTextAlignment(TextAlignment.CENTER);

            Paragraph userInfo = new Paragraph("👤 User: " + user.getFirstName() + " " + user.getLastName())
                    .setFontSize(12);
            Paragraph eventDate = new Paragraph("📅 Date: " + event.getDate())
                    .setFontSize(12);
            Paragraph eventLocation = new Paragraph("📍 Location: " + event.getLocation())
                    .setFontSize(12);
            table.addCell(new Cell().add(title).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(userInfo).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(eventDate).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(eventLocation).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.LEFT));

            document.add(table);
        }

        return file;
    }

    private static byte[] getImageFromURL(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.connect();

        try (InputStream inputStream = connection.getInputStream()) {
            return inputStream.readAllBytes();
        }
    }
}
