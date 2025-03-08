package com.example.fundmatch.shared.util;

import com.example.fundmatch.domain.entities.Event;
import com.example.fundmatch.domain.entities.User;
import com.google.zxing.WriterException;
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
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;

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
             Document document = new Document(pdf, PageSize.A4.rotate())) {

            document.setMargins(20, 20, 20, 20);
            PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont regularFont = PdfFontFactory.createFont("Helvetica");

            Table mainLayout = new Table(2).useAllAvailableWidth();

            Cell leftCell = new Cell().setBorder(new SolidBorder(ColorConstants.BLACK, 1));
            Table infoTable = new Table(1).useAllAvailableWidth();

            Paragraph title = new Paragraph("🎟️ " + event.getTitle())
                    .setFont(boldFont)
                    .setFontSize(18)
                    .setFontColor(ColorConstants.BLACK)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10);
            infoTable.addCell(new Cell().add(title).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));

            Paragraph userInfo = new Paragraph("👤 Attendee: " + user.getFirstName() + " " + user.getLastName())
                    .setFont(regularFont)
                    .setFontSize(12);
            Paragraph eventDate = new Paragraph("📅 Date: " + event.getDate())
                    .setFont(regularFont)
                    .setFontSize(12);
            Paragraph eventLocation = new Paragraph("📍 Location: " + event.getLocation())
                    .setFont(regularFont)
                    .setFontSize(12);

            infoTable.addCell(new Cell().add(userInfo).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));
            infoTable.addCell(new Cell().add(eventDate).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));
            infoTable.addCell(new Cell().add(eventLocation).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));

            Paragraph descriptionTitle = new Paragraph("Event Description")
                    .setFont(boldFont)
                    .setFontSize(14)
                    .setMarginTop(15)
                    .setMarginBottom(5);

            Paragraph description = new Paragraph(
                    "Join us for this special event. Please arrive 30 minutes before the scheduled time. " +
                            "Food and beverages will be available for purchase. No outside food or drinks allowed.")
                    .setFont(regularFont)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.JUSTIFIED);

            infoTable.addCell(new Cell().add(descriptionTitle).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));
            infoTable.addCell(new Cell().add(description).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));

            Image qrCode = QRCodeGenerator.generateQRCodeImage(event.getId() + "-" + user.getId());
            qrCode.setWidth(100)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setMarginTop(20);

            infoTable.addCell(new Cell().add(qrCode).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER));

            Paragraph ticketNumber = new Paragraph("Ticket #: " + generateTicketNumber(event.getId(), user.getId()))
                    .setFont(regularFont)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER);
            infoTable.addCell(new Cell().add(ticketNumber).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));

            leftCell.add(infoTable);

            Cell rightCell = new Cell().setBorder(new SolidBorder(ColorConstants.BLACK, 1));
            String imagePath = "http://localhost:9091/api/files/" + event.getImagePath();
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    ImageData imageData = ImageDataFactory.create(getImageFromURL(imagePath));
                    Image eventImg = new Image(imageData).setWidth(300)
                            .setHorizontalAlignment(HorizontalAlignment.CENTER);
                    rightCell.add(eventImg)
                            .setVerticalAlignment(VerticalAlignment.MIDDLE)
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBackgroundColor(ColorConstants.BLACK);
                } catch (IOException e) {
                    Paragraph noImage = new Paragraph("Event image not available")
                            .setFont(boldFont)
                            .setFontSize(14)
                            .setTextAlignment(TextAlignment.CENTER);
                    rightCell.add(noImage)
                            .setVerticalAlignment(VerticalAlignment.MIDDLE);
                    e.printStackTrace();
                }
            }

            mainLayout.addCell(leftCell);
            mainLayout.addCell(rightCell);

            document.add(mainLayout);

            SolidLine line = new SolidLine(1f);
            line.setColor(ColorConstants.BLACK);
            LineSeparator lineSeparator = new LineSeparator(line);
            lineSeparator.setMarginTop(10);
            document.add(lineSeparator);

            Paragraph footer = new Paragraph("This ticket is not transferable. Present this ticket at the entrance.")
                    .setFont(regularFont)
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(footer);

        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

        return file;
    }

    private static String generateTicketNumber(Long eventId, Long userId) {
        return String.format("%05d-%05d", eventId, userId);
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