package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.entities.Event;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.shared.util.PdfGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Service
@AllArgsConstructor
public class TicketEmailService {
    private final JavaMailSender mailSender;

    public void sendTicketEmail(User user, Event event) throws IOException, MessagingException {
        File pdfFile = PdfGenerator.generateTicketPdf(user, event);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(user.getEmail());
        helper.setSubject("Ticket for Event: " + event.getTitle());
        helper.setText("Dear " + user.getFirstName() + ",\n\nHere is your ticket for " + event.getTitle() + ".\nEnjoy!\n\nBest regards.");

        helper.addAttachment("Ticket_" + event.getTitle() + ".pdf", pdfFile);
        mailSender.send(message);
        pdfFile.delete();
    }

}
