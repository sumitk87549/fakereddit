package com.fakereddit.demo.service;

import com.fakereddit.demo.exceptions.SpringRedditException;
import com.fakereddit.demo.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendEmail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Email sent successfully...");

        }catch (Exception e) {
            log.error("Unable to sent verification email for <"+notificationEmail.getRecipient()+">");
            throw new SpringRedditException("Exception occurred while sending verification email to <"+notificationEmail.getRecipient()+">  !");
        }
    }
}
