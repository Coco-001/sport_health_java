package com.coco.utils;

import com.coco.vo.MailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;


@Slf4j
@Component
public class MailUtils {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件
     * @param mail 发送内容
     * @return
     */
    public String sendMail(MailVo mail) {
        try {
            if (mail.isHtml()) {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom(from);
                messageHelper.setTo(mail.getReceivers());
                messageHelper.setSubject(mail.getSubject());
                messageHelper.setText(mail.getContent(), true);
                mailSender.send(mimeMessage);
                log.info("HTML邮件发送成功！收件人---{}---", Arrays.asList(mail.getReceivers()));
            } else {
                // 创建邮件对象
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                // 发件人
                mailMessage.setFrom(from);
                // 收件人
                mailMessage.setTo(mail.getReceivers());
                // 邮件主题
                mailMessage.setSubject(mail.getSubject());
                // 邮件内容
                mailMessage.setText(mail.getContent());
                mailSender.send(mailMessage);
                log.info("普通邮件发送成功! 收件人---{}---", Arrays.asList(mail.getReceivers()));
            }
            return "邮件发送成功！";
        } catch (Exception e) {
            log.error("邮件发送失败--> {}", e.getMessage());
            return "邮件发送失败！";
        }
    }

}
