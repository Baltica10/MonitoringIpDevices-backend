package com.krasnov.monitoring.service

import com.krasnov.monitoring.model.device.Device
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.*
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.mail.Message
import javax.mail.internet.*

@Service
class NotificationServiceImpl(
        @Value("\${spring.mail.username}")
        private val mailUserName: String,
        private val mailSender: JavaMailSender
) : NotificationService {

    private fun sendEmailTo(sendTo: String, subject: String, text: String) {
        try {
            val mimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(mimeMessage, "utf-8")
            helper.setText(addMsgInPattern(text), true)
            helper.setTo(sendTo)
            helper.setSubject(subject)
            sendEmail(mimeMessage)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    private fun addMsgInPattern(msg: String): String {
        return "<html>" +
                "<head> </head>" +
                "<title> </title>" +
                "<body>" +
                "<style>" +
                "h1 {" +
                "font-family: Arial Bold; /* Гарнитура текста */" +
                "font-size: 12pt; /* Размер шрифта в процентах */" +
                "}" +
                "p {" +
                "font-family: Arial;" +
                "font-size: 9pt; /* Размер шрифта в пунктах */" +
                "}" +
                "</style>" +
                msg +
                "<p>" +
                "<br> <br> " +
                "Это автоматическое письмо, которое не требует ответа." +
                "<br> С уважением, " +
                "<br> Система мониторинга IP оборудования" +
                "<br><br> This letter was automatically created, you shouldn’t answer it." +
                "<br> Kind regards," +
                "<br> IP devices monitoring system" +
                "</p>" +
                "</body>" +
                "</html>"
    }

    private fun sendEmail(mimeMessage: MimeMessage) {
        val sendTo = Arrays.toString(mimeMessage.getRecipients(Message.RecipientType.TO))
        println("Send E-mail to $sendTo, subject: ${mimeMessage.subject}")
        try {
            mimeMessage.setFrom(InternetAddress(mailUserName, "IP devices monitoring system"))
            mailSender.send(mimeMessage)
            println("Success send E-mail to $sendTo, subject: ${mimeMessage.subject}")
        } catch (ex: Exception) {
            println("Fail send E-mail to $sendTo, subject: ${mimeMessage.subject}. Error: $ex.message")
        }
    }

    @Async
    override fun sendDeviceAlert(device: Device) {
        if (device.notifyEmail) {
            val subject = "Device alert [ ${device.brand.name} ${device.model} SN:${device.serialNumber} ] "
            val text = "<h1>Device not available:" +
                    "<br>Name: ${device.brand.name} ${device.model} SN:${device.serialNumber}" +
                    "<br>Description: ${device.description}" +
                    "<br>URL: ${device.getCheckAvailableURL()}" +
                    "<br>Time: ${LocalDateTime.now()}</h1>"

            sendEmailTo(mailUserName, subject, text)
        }
    }
}