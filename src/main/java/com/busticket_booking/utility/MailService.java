package com.busticket_booking.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.IOException;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailWithPDF(String toAddress, String subject, String body, String filePath) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(body);

        // Attach the PDF file to the email
        FileSystemResource pdfFile = new FileSystemResource(new File(filePath));
        helper.addAttachment("booking-details.pdf", pdfFile);

        // Send the email
        javaMailSender.send(message);
    }

}

