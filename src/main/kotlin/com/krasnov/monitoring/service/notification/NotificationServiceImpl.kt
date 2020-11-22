package com.krasnov.monitoring.service.notification

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
        private val mailSender: JavaMailSender,
        private val telegramBot: TelegramBot
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
                "<br></span><img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAABNCAIAAABsR7yiAAAACXBIWXMAAAsTAAALEwEAmpwYAAALTGlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDIgNzkuMTYwOTI0LCAyMDE3LzA3LzEzLTAxOjA2OjM5ICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOnBob3Rvc2hvcD0iaHR0cDovL25zLmFkb2JlLmNvbS9waG90b3Nob3AvMS4wLyIgeG1sbnM6ZGM9Imh0dHA6Ly9wdXJsLm9yZy9kYy9lbGVtZW50cy8xLjEvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtbG5zOnRpZmY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vdGlmZi8xLjAvIiB4bWxuczpleGlmPSJodHRwOi8vbnMuYWRvYmUuY29tL2V4aWYvMS4wLyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOCAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDE4LTEwLTMxVDEwOjM1OjM0KzA0OjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDE4LTEwLTMxVDExOjEwOjQ4KzA0OjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAxOC0xMC0zMVQxMToxMDo0OCswNDowMCIgcGhvdG9zaG9wOkxlZ2FjeUlQVENEaWdlc3Q9IjAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAxIiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIiBwaG90b3Nob3A6SUNDUHJvZmlsZT0iIiBkYzpmb3JtYXQ9ImltYWdlL3BuZyIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDpjYjhlN2I2Mi1mN2FjLTAzNGMtYTI5Ni0xMjFkNTFmMTBjZjciIHhtcE1NOkRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDowOTM2OWY4YS1iZGJjLTM2NGEtYWFlZi0yZDhiOTI2NjAzOWUiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDoyNWI1NjY3NS04OTM5LTJkNDctYTQ5My02ZTE0YmE3YzRkZmEiIHRpZmY6SW1hZ2VXaWR0aD0iMjAwIiB0aWZmOkltYWdlTGVuZ3RoPSI3NyIgdGlmZjpQaG90b21ldHJpY0ludGVycHJldGF0aW9uPSIyIiB0aWZmOk9yaWVudGF0aW9uPSIxIiB0aWZmOlNhbXBsZXNQZXJQaXhlbD0iMyIgdGlmZjpYUmVzb2x1dGlvbj0iNzIwMDAwLzEwMDAwIiB0aWZmOllSZXNvbHV0aW9uPSI3MjAwMDAvMTAwMDAiIHRpZmY6UmVzb2x1dGlvblVuaXQ9IjIiIGV4aWY6RXhpZlZlcnNpb249IjAyMjEiIGV4aWY6Q29sb3JTcGFjZT0iNjU1MzUiIGV4aWY6UGl4ZWxYRGltZW5zaW9uPSIyMDAiIGV4aWY6UGl4ZWxZRGltZW5zaW9uPSI3NyI+IDxwaG90b3Nob3A6RG9jdW1lbnRBbmNlc3RvcnM+IDxyZGY6QmFnPiA8cmRmOmxpPjkxNjYyRDE5OUU5OTk0M0U2QTg3RDZFQkYzQTI1OTE4PC9yZGY6bGk+IDwvcmRmOkJhZz4gPC9waG90b3Nob3A6RG9jdW1lbnRBbmNlc3RvcnM+IDx4bXBNTTpIaXN0b3J5PiA8cmRmOlNlcT4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImNyZWF0ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6MjViNTY2NzUtODkzOS0yZDQ3LWE0OTMtNmUxNGJhN2M0ZGZhIiBzdEV2dDp3aGVuPSIyMDE4LTEwLTMxVDEwOjM1OjM0KzA0OjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOCAoV2luZG93cykiLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjE1OTc4NjVlLWQxOTctNTY0OC1iNjRjLThkNzkzNDk1MDEyNCIgc3RFdnQ6d2hlbj0iMjAxOC0xMC0zMVQxMToxMDo0OCswNDowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTggKFdpbmRvd3MpIiBzdEV2dDpjaGFuZ2VkPSIvIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjb252ZXJ0ZWQiIHN0RXZ0OnBhcmFtZXRlcnM9ImZyb20gaW1hZ2UvanBlZyB0byBpbWFnZS9wbmciLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImRlcml2ZWQiIHN0RXZ0OnBhcmFtZXRlcnM9ImNvbnZlcnRlZCBmcm9tIGltYWdlL2pwZWcgdG8gaW1hZ2UvcG5nIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDpjYjhlN2I2Mi1mN2FjLTAzNGMtYTI5Ni0xMjFkNTFmMTBjZjciIHN0RXZ0OndoZW49IjIwMTgtMTAtMzFUMTE6MTA6NDgrMDQ6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE4IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6MTU5Nzg2NWUtZDE5Ny01NjQ4LWI2NGMtOGQ3OTM0OTUwMTI0IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjI1YjU2Njc1LTg5MzktMmQ0Ny1hNDkzLTZlMTRiYTdjNGRmYSIgc3RS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    " +
                "\" alt=\"\"></pre>" +
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

        if (device.notifyTlg) {
            telegramBot.sendMsg(
                    "Device alert [ ${device.brand.name} ${device.model} ]. " +
                            "Description: ${device.description}. " +
                            "URL: ${device.getCheckAvailableURL()}"
            )
        }
    }
}